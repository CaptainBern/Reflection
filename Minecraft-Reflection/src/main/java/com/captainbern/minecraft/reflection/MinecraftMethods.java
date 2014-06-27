package com.captainbern.minecraft.reflection;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.google.common.collect.MapMaker;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MinecraftMethods {

    private static FieldAccessor<Channel> CHANNEL_ACCESSOR;

    protected static volatile ConcurrentMap<Player, Channel> channelCache = new MapMaker().weakKeys().makeMap();

    public static void sendPacket(Player player, Object handle) {
        Channel channel = channelCache.get(player);

        if (channel == null) {
            if (CHANNEL_ACCESSOR == null) {
                Class<?> networkManager = MinecraftReflection.getNetworkManagerClass();

                try {

                    SafeField<Channel> candidate = new Reflection().reflect(networkManager).getSafeFieldByType(Channel.class);

                    if (candidate == null)
                        throw new RuntimeException("Failed to find the Channel field!");

                    CHANNEL_ACCESSOR = candidate.getAccessor();

                } catch (Exception e) {
                    throw new RuntimeException("Failed to find the channel field!", e);
                }
            }

            channel = CHANNEL_ACCESSOR.get(MinecraftFields.getNetworkManager(player));
            channelCache.put(player, channel);
        }

        if (!MinecraftReflection.getPacketClass().isAssignableFrom(handle.getClass()))
            throw new IllegalArgumentException(handle + " is not a valid packet class!");

        channel.writeAndFlush(handle);
    }
}
