package com.captainbern.minecraft;

import com.captainbern.minecraft.protocol.PacketType;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.wrapper.WrappedDataWatcher;
import com.captainbern.minecraft.wrapper.WrappedPacket;
import com.captainbern.minecraft.wrapper.nbt.NbtFactory;
import com.captainbern.minecraft.wrapper.nbt.NbtTagCompound;
import com.captainbern.minecraft.wrapper.nbt.NbtTagList;
import com.captainbern.minecraft.wrapper.nbt.NbtType;
import com.captainbern.minecraft.wrapper.nbt.io.NbtSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(MinecraftReflection.getCraftServerClass().getCanonicalName());
        getLogger().info(MinecraftReflection.getMinecraftServerClass().getCanonicalName());
        getLogger().info(MinecraftReflection.getPacketClass().getCanonicalName());
        getLogger().info(PacketType.Play.Server.ANIMATION.getPacketClass().getCanonicalName());

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
        dataWatcher.setObject(0, "test");
        getLogger().info(dataWatcher.getString(0));

        WrappedPacket packet = new WrappedPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        packet.getDataWatchers().write(0, new WrappedDataWatcher());
        getLogger().info(packet.getDataWatchers().read(0).getHandle().getClass().getCanonicalName());

        NbtTagCompound compound = NbtFactory.toCompound(NbtFactory.createTag(NbtType.TAG_COMPOUND));
        NbtTagList<?> list = NbtFactory.toList(NbtFactory.createTag(NbtType.TAG_LIST));

        list.add(0);
        list.add(5);

        compound.put("list", list);

        for (Object base : compound.getList("list")) {
            getLogger().info(base.toString());
        }

        Bukkit.getPluginManager().registerEvents(this, this);


        getLogger().info("Testing NBT-Stream tools!");
        byte[] data = NbtSerializer.toBytes(compound);
        NbtTagCompound returned = NbtFactory.toCompound(NbtSerializer.toTag(data));

        for (Object base : returned.getList("list")) {
            getLogger().info(base.toString());
        }
        //getLogger().info(PrettyPrinter.print(Bukkit.getServer().getConsoleSender()));
    }

    public static Main getInstance() {
        return instance;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getItemInHand();

        if (stack.getType().equals(Material.AIR))
            return;

        NbtTagCompound compound = NbtFactory.readFromItemStack(stack);

        for (String key : compound.getKeys()) {
            player.sendMessage(key + " = " + compound.getValue(key));
        }
    }
}
