package com.captainbern.minecraft.protocol;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class PacketTypeLookup {
    public final HashMap<Integer, PacketType> HANDSHAKE_CLIENT = Maps.newHashMap();
    public final HashMap<Integer, PacketType> HANDSHAKE_SERVER = Maps.newHashMap();

    public final HashMap<Integer, PacketType> PLAY_CLIENT = Maps.newHashMap();
    public final HashMap<Integer, PacketType> PLAY_SERVER = Maps.newHashMap();

    public final HashMap<Integer, PacketType> STATUS_CLIENT = Maps.newHashMap();
    public final HashMap<Integer, PacketType> STATUS_SERVER = Maps.newHashMap();

    public final HashMap<Integer, PacketType> LOGIN_CLIENT = Maps.newHashMap();
    public final HashMap<Integer, PacketType> LOGIN_SERVER = Maps.newHashMap();

    public HashMap<Integer, PacketType> getLookup(Protocol protocol, Sender sender) {
        switch(protocol) {
            case HANDSHAKING :
                return sender == Sender.CLIENT ? HANDSHAKE_CLIENT : HANDSHAKE_SERVER;
            case PLAY :
                return sender == Sender.CLIENT ? PLAY_CLIENT : PLAY_SERVER;
            case STATUS :
                return sender == Sender.CLIENT ? STATUS_CLIENT : STATUS_SERVER;
            case LOGIN :
                return sender == Sender.CLIENT ? LOGIN_CLIENT : LOGIN_SERVER;
            default :
                System.err.print("Error: Unable to identify protocol: " + protocol);
                return null;
        }
    }

    public PacketTypeLookup addPacketType(Iterable<? extends PacketType> types) {
        for(PacketType type : types) {
            if(type.getId() != PacketType.UNKNOWN_PACKET) {
                getLookup(type.getProtocol(), type.getSender()).put(type.getId(), type);
            }
        }
        return this;
    }

    public PacketType getPacketType(Protocol protocol, Sender sender, int id) {
        return getLookup(protocol, sender).get(id);
    }
}
