package com.captainbern.minecraft.reflection;

import com.captainbern.minecraft.conversion.BukkitUnwrapper;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;
import org.bukkit.entity.Player;

public class MinecraftFields {

    protected static volatile FieldAccessor<Object> PLAYER_CONNECTION;
    protected static volatile FieldAccessor<Object> NETWORK_MANAGER;

    public static Object getPlayerConnection(Player player) {
        if (PLAYER_CONNECTION == null) {
            try {
                Class<?> type = MinecraftReflection.getPlayerConnectionClass();

                PLAYER_CONNECTION = (FieldAccessor<Object>) new Reflection().reflect(MinecraftReflection.getEntityPlayerClass()).getSafeFieldByType(type).getAccessor();
            } catch (Exception e) {
                throw new RuntimeException("Failed to get the PlayerConnection field!");
            }
        }

        final Object nmsHandle = BukkitUnwrapper.getInstance().unwrap(player);

        if (nmsHandle != null)
            return PLAYER_CONNECTION.get(nmsHandle);

        return null;
    }

    public static Object getNetworkManager(Player player) {
        if (NETWORK_MANAGER == null) {
            try {
                Class<?> type = MinecraftReflection.getNetworkManagerClass();

                NETWORK_MANAGER = (FieldAccessor<Object>) new Reflection().reflect(MinecraftReflection.getPlayerConnectionClass()).getSafeFieldByType(type).getAccessor();
            } catch (Exception e) {
                throw new RuntimeException("Failed to get the NetworkManager field!");
            }
        }

        final Object playerConnection = getPlayerConnection(player);

        if (playerConnection != null)
            return NETWORK_MANAGER.get(playerConnection);

        return null;
    }
}
