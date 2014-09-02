package com.captainbern.minecraft.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class PacketType implements Serializable {
    public static final int UNKNOWN_PACKET = -1;

    public static class HandShaking {
        private static final Protocol PROTOCOL = Protocol.HANDSHAKING;

        public static class Client extends FieldIterator<PacketType>{
            private static final Sender SENDER = Sender.CLIENT;

            public static final PacketType SET_PROTOCOL =       new PacketType(PROTOCOL, SENDER, 0x00);

            private static final Client INSTANCE = new Client();

            private Client() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Client getInstance() {
                return INSTANCE;
            }
        }

        public static class Server extends FieldIterator<PacketType> {
            private static final Sender SENDER = Sender.SERVER;

            private static final Server INSTANCE = new Server();

            private Server() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Server getInstance() {
                return INSTANCE;
            }
        }

        public static Protocol getProtocol() {
            return PROTOCOL;
        }
    }

    public static class Play {
        private static final Protocol PROTOCOL = Protocol.PLAY;

        public static class Client extends FieldIterator<PacketType>{
            private static final Sender SENDER = Sender.CLIENT;

            public static final PacketType KEEP_ALIVE =         new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType CHAT =               new PacketType(PROTOCOL, SENDER, 0x01);
            public static final PacketType USE_ENTITY =         new PacketType(PROTOCOL, SENDER, 0x02);
            public static final PacketType FLYING =             new PacketType(PROTOCOL, SENDER, 0x03);
            public static final PacketType POSITION =           new PacketType(PROTOCOL, SENDER, 0x04);
            public static final PacketType LOOK =               new PacketType(PROTOCOL, SENDER, 0x05);
            public static final PacketType POSITION_LOOK =      new PacketType(PROTOCOL, SENDER, 0x06);
            public static final PacketType BLOCK_DIG =          new PacketType(PROTOCOL, SENDER, 0x07);
            public static final PacketType BLOCK_PLACE =        new PacketType(PROTOCOL, SENDER, 0x08);
            public static final PacketType HELDITEM =           new PacketType(PROTOCOL, SENDER, 0x09);
            public static final PacketType ANIMATION =          new PacketType(PROTOCOL, SENDER, 0x0A);
            public static final PacketType ENTITY_ACTION =      new PacketType(PROTOCOL, SENDER, 0x0B);
            public static final PacketType STEER_VEHICLE =      new PacketType(PROTOCOL, SENDER, 0x0C);
            public static final PacketType CLOSE_WINDOW =       new PacketType(PROTOCOL, SENDER, 0x0D);
            public static final PacketType WINDOW_CLICK =       new PacketType(PROTOCOL, SENDER, 0x0E);
            public static final PacketType TRANSACTION =        new PacketType(PROTOCOL, SENDER, 0x0F);
            public static final PacketType SET_CREATIVE_SLOT =  new PacketType(PROTOCOL, SENDER, 0x10);
            public static final PacketType ENCHANT_ITEM =       new PacketType(PROTOCOL, SENDER, 0x11);
            public static final PacketType UPDATE_SIGN =        new PacketType(PROTOCOL, SENDER, 0x12);
            public static final PacketType ABILITIES =          new PacketType(PROTOCOL, SENDER, 0x13);
            public static final PacketType TAB_COMPLETE =       new PacketType(PROTOCOL, SENDER, 0x14);
            public static final PacketType CLIENT_SETTINGS =    new PacketType(PROTOCOL, SENDER, 0x15);
            public static final PacketType CLIENT_COMMAND =     new PacketType(PROTOCOL, SENDER, 0x16);
            public static final PacketType CUSTOM_PAYLOAD =     new PacketType(PROTOCOL, SENDER, 0x17);
            // 1.8...
            public static final PacketType SPECTATE =           new PacketType(PROTOCOL, SENDER, 0x18);
            public static final PacketType RESOURCE_PACK_STATUS =
                                                                new PacketType(PROTOCOL, SENDER, 0x19);

            private static final Client INSTANCE = new Client();

            private Client() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Client getInstance() {
                return INSTANCE;
            }
        }

        public static class Server extends FieldIterator<PacketType> {
            private static final Sender SENDER = Sender.SERVER;

            public static final PacketType KEEP_ALIVE =         new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType LOGIN =              new PacketType(PROTOCOL, SENDER, 0x01);
            public static final PacketType CHAT =               new PacketType(PROTOCOL, SENDER, 0x02);
            public static final PacketType UPDATE_TIME =        new PacketType(PROTOCOL, SENDER, 0x03);
            public static final PacketType ENTITY_EQUIPMENT =   new PacketType(PROTOCOL, SENDER, 0x04);
            public static final PacketType SPAWN_POSITION =     new PacketType(PROTOCOL, SENDER, 0x05);
            public static final PacketType UPDATE_HEALTH =      new PacketType(PROTOCOL, SENDER, 0x06);
            public static final PacketType RESPAWN =            new PacketType(PROTOCOL, SENDER, 0x07);
            public static final PacketType POSITION =           new PacketType(PROTOCOL, SENDER, 0x08);
            public static final PacketType HELD_ITEM_SLOT =     new PacketType(PROTOCOL, SENDER, 0x09);
            public static final PacketType BED =                new PacketType(PROTOCOL, SENDER, 0x0A);
            public static final PacketType ANIMATION =          new PacketType(PROTOCOL, SENDER, 0x0B);
            public static final PacketType NAMED_ENTITY_SPAWN = new PacketType(PROTOCOL, SENDER, 0x0C);
            public static final PacketType COLLECT =            new PacketType(PROTOCOL, SENDER, 0x0D);
            public static final PacketType SPAWN_ENTITY =       new PacketType(PROTOCOL, SENDER, 0x0E);
            public static final PacketType SPAWN_ENTITY_LIVING =
                                                                new PacketType(PROTOCOL, SENDER, 0x0F);
            public static final PacketType SPAWN_ENTITY_PAINTING =
                                                                new PacketType(PROTOCOL, SENDER, 0x10);
            public static final PacketType SPAWN_ENTITY_EXPERIENCE_ORB =
                                                                new PacketType(PROTOCOL, SENDER, 0x11);
            public static final PacketType ENTITY_VELOCITY =    new PacketType(PROTOCOL, SENDER, 0x12);
            public static final PacketType ENTITY_DESTROY =     new PacketType(PROTOCOL, SENDER, 0x13);
            public static final PacketType ENTITY =             new PacketType(PROTOCOL, SENDER, 0x14);
            public static final PacketType REL_ENTITY_MOVE =    new PacketType(PROTOCOL, SENDER, 0x15);
            public static final PacketType REL_ENTITY_LOOK =    new PacketType(PROTOCOL, SENDER, 0x16);
            public static final PacketType REL_ENTITY_MOVE_LOOK =
                                                                new PacketType(PROTOCOL, SENDER, 0x17);
            public static final PacketType ENTITY_TELEPORT =    new PacketType(PROTOCOL, SENDER, 0x18);
            public static final PacketType ENTITY_HEAD_ROTATION =
                                                                new PacketType(PROTOCOL, SENDER, 0x19);
            public static final PacketType ETITY_STATUS =       new PacketType(PROTOCOL, SENDER, 0x1A);
            public static final PacketType ATTACH_ENTITY =      new PacketType(PROTOCOL, SENDER, 0x1B);
            public static final PacketType ENTITY_METADATA =    new PacketType(PROTOCOL, SENDER, 0x1C);
            public static final PacketType ENTITY_EFFECT =      new PacketType(PROTOCOL, SENDER, 0x1D);
            public static final PacketType REMOVE_ENTITY_EFFECT =
                                                                new PacketType(PROTOCOL, SENDER, 0x1E);
            public static final PacketType SET_EXPERIENCE =     new PacketType(PROTOCOL, SENDER, 0x1F);
            public static final PacketType UPDATE_ATTRIBUTES =  new PacketType(PROTOCOL, SENDER, 0x20);
            public static final PacketType MAP_CHUNK =          new PacketType(PROTOCOL, SENDER, 0x21);
            public static final PacketType MULTI_BLOCK_CHANGE = new PacketType(PROTOCOL, SENDER, 0x22);
            public static final PacketType BLOCK_CHANGE =       new PacketType(PROTOCOL, SENDER, 0x23);
            public static final PacketType BLOCK_ACTION =       new PacketType(PROTOCOL, SENDER, 0x24);
            public static final PacketType BLOCK_BREAK_ANIMATION =
                                                                new PacketType(PROTOCOL, SENDER, 0x25);
            public static final PacketType MAP_CHUNK_BULK =     new PacketType(PROTOCOL, SENDER, 0x26);
            public static final PacketType EXPLOSION =          new PacketType(PROTOCOL, SENDER, 0x27);
            public static final PacketType WORLD_EVENT =        new PacketType(PROTOCOL, SENDER, 0x28);
            public static final PacketType NAMED_SOUND_EFFECT = new PacketType(PROTOCOL, SENDER, 0x29);
            public static final PacketType WORLD_PARTICLES =    new PacketType(PROTOCOL, SENDER, 0x2A);
            public static final PacketType CHANGE_GAMESTATE =   new PacketType(PROTOCOL, SENDER, 0x2B);
            public static final PacketType SPAWN_ENTITY_WEATHER =
                                                                new PacketType(PROTOCOL, SENDER, 0x2C);
            public static final PacketType OPEN_WINDOW =        new PacketType(PROTOCOL, SENDER, 0x2D);
            public static final PacketType CLOSE_WINDOW =       new PacketType(PROTOCOL, SENDER, 0x2E);
            public static final PacketType SET_SLOT =           new PacketType(PROTOCOL, SENDER, 0x2F);
            public static final PacketType WINDOW_ITEMS =       new PacketType(PROTOCOL, SENDER, 0x30);
            public static final PacketType CRAFT_PROGRESS_BAR = new PacketType(PROTOCOL, SENDER, 0x31);
            public static final PacketType TRANSACTION =        new PacketType(PROTOCOL, SENDER, 0x32);
            public static final PacketType UPDATE_SIGN =        new PacketType(PROTOCOL, SENDER, 0x33);
            public static final PacketType MAP =                new PacketType(PROTOCOL, SENDER, 0x34);
            public static final PacketType OPEN_TILE_ENTITY_DATA =
                                                                new PacketType(PROTOCOL, SENDER, 0x35);
            public static final PacketType OPEN_SIGN_EDITOR =   new PacketType(PROTOCOL, SENDER, 0x36);
            public static final PacketType STATISTIC =          new PacketType(PROTOCOL, SENDER, 0x37);
            public static final PacketType PLAYER_INFO =        new PacketType(PROTOCOL, SENDER, 0x38);
            public static final PacketType PLAYER_ABILITIES =   new PacketType(PROTOCOL, SENDER, 0x39);
            public static final PacketType TAB_COMPLETE =       new PacketType(PROTOCOL, SENDER, 0x3A);
            public static final PacketType SCOREBOARD_OBJECTIVE =
                                                                new PacketType(PROTOCOL, SENDER, 0x3B);
            public static final PacketType SCOREBOARD_SCORE =   new PacketType(PROTOCOL, SENDER, 0x3C);
            public static final PacketType SCOREBOARD_DISPLAY_SCORE =
                                                                new PacketType(PROTOCOL, SENDER, 0x3D);
            public static final PacketType SCOREBOARD_TEAM =    new PacketType(PROTOCOL, SENDER, 0x3E);
            public static final PacketType CUSTOM_PAYLOAD =     new PacketType(PROTOCOL, SENDER, 0x3F);
            public static final PacketType KICK_DISCONNECT =    new PacketType(PROTOCOL, SENDER, 0x40);

            //1.8
            public static final PacketType SERVER_DIFFICULTY =  new PacketType(PROTOCOL, SENDER, 0x41);
            public static final PacketType COMBAT_EVENT =       new PacketType(PROTOCOL, SENDER, 0x42);
            public static final PacketType CAMERA =             new PacketType(PROTOCOL, SENDER, 0x43);
            public static final PacketType WORLD_BORDER =       new PacketType(PROTOCOL, SENDER, 0x44);
            public static final PacketType TITLE =              new PacketType(PROTOCOL, SENDER, 0x45);
            public static final PacketType SET_COMPRESSION =    new PacketType(PROTOCOL, SENDER, 0x46);
            public static final PacketType PLAYER_LIST_HEADER_FOOTER =
                                                                new PacketType(PROTOCOL, SENDER, 0x47);
            public static final PacketType RESOURCE_PACK_SEND = new PacketType(PROTOCOL, SENDER, 0x48);
            public static final PacketType UPDATE_ENTITY_NBT =  new PacketType(PROTOCOL, SENDER, 0x49);

            private static final Server INSTANCE = new Server();

            private Server() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Server getInstance() {
                return INSTANCE;
            }
        }

        public static Protocol getProtocol() {
            return PROTOCOL;
        }
    }

    public static class Status {
        private static final Protocol PROTOCOL = Protocol.STATUS;

        public static class Client extends FieldIterator<PacketType>{
            private static final Sender SENDER = Sender.CLIENT;

            public static final PacketType START =              new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType PING =               new PacketType(PROTOCOL, SENDER, 0x01);

            private static final Client INSTANCE = new Client();

            private Client() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Client getInstance() {
                return INSTANCE;
            }
        }

        public static class Server extends FieldIterator<PacketType> {
            private static final Sender SENDER = Sender.SERVER;

            public static final PacketType SERVER_INFO =        new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType PONG =               new PacketType(PROTOCOL, SENDER, 0x01);

            private static final Server INSTANCE = new Server();

            private Server() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Server getInstance() {
                return INSTANCE;
            }
        }

        public static Protocol getProtocol() {
            return PROTOCOL;
        }
    }

    public static class Login {
        private static final Protocol PROTOCOL = Protocol.LOGIN;

        public static class Client extends FieldIterator<PacketType>{
            private static final Sender SENDER = Sender.CLIENT;

            public static final PacketType LOGIN_START =        new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType ENCRYPTION_BEGIN =   new PacketType(PROTOCOL, SENDER, 0x01);

            private static final Client INSTANCE = new Client();

            private Client() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Client getInstance() {
                return INSTANCE;
            }
        }

        public static class Server extends FieldIterator<PacketType> {
            private static final Sender SENDER = Sender.SERVER;

            public static final PacketType DISCONNECT =         new PacketType(PROTOCOL, SENDER, 0x00);
            public static final PacketType ENCRYPTION_BEGIN =   new PacketType(PROTOCOL, SENDER, 0x01);
            public static final PacketType ENCRYPTION_SUCCESS = new PacketType(PROTOCOL, SENDER, 0x02);

            private static final Server INSTANCE = new Server();

            private Server() {
                super(PacketType.class);
            }

            public static Sender getSender() {
                return SENDER;
            }

            public static Server getInstance() {
                return INSTANCE;
            }
        }

        public static Protocol getProtocol() {
            return PROTOCOL;
        }
    }

    /**
     * Actual PacketType class starts here
     */



    private static PacketTypeLookup LOOKUP;
    private static PacketRegistry REGISTRY;

    private Protocol protocol;
    private Sender sender;
    private int id;

    public static Iterable<PacketType> values() {
        List<Iterable<? extends PacketType>> sources = Lists.newArrayList();
        sources.add(HandShaking.Client.getInstance());
        sources.add(HandShaking.Server.getInstance());
        sources.add(Play.Client.getInstance());
        sources.add(Play.Server.getInstance());
        sources.add(Status.Client.getInstance());
        sources.add(Status.Server.getInstance());
        sources.add(Login.Client.getInstance());
        sources.add(Login.Server.getInstance());
        return Iterables.concat(sources);
    }

    private static PacketTypeLookup getLookup() {
        if(LOOKUP == null) {
            LOOKUP = new PacketTypeLookup()
                    .addPacketType(HandShaking.Client.getInstance())
                    .addPacketType(HandShaking.Server.getInstance())
                    .addPacketType(Play.Client.getInstance())
                    .addPacketType(Play.Server.getInstance())
                    .addPacketType(Status.Client.getInstance())
                    .addPacketType(Status.Server.getInstance())
                    .addPacketType(Login.Client.getInstance())
                    .addPacketType(Login.Server.getInstance());
        }
        return LOOKUP;
    }

    private static PacketRegistry getRegistry() {
        if(REGISTRY == null) {
            REGISTRY = new PacketRegistry();
        }
        return REGISTRY;
    }

    public PacketType(Protocol protocol, Sender sender, int id) {
        this.protocol = Preconditions.checkNotNull(protocol, "Protocol can't be NULL!");
        this.sender = Preconditions.checkNotNull(sender, "Sender can't be NULL!");
        this.id = id;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Sender getSender() {
        return this.sender;
    }

    public int getId() {
        return this.id;
    }

    public boolean isClient() {
        return this.sender == Sender.CLIENT;
    }

    public boolean isServer() {
        return this.sender == Sender.SERVER;
    }

    public Class<?> getPacketClass() {
        return getRegistry().getClassLookup().get(this);
    }

    public static PacketType getTypeFrom(Protocol protocol, Sender sender, int id) {
        return getLookup().getPacketType(protocol, sender, id);
    }

    public static PacketType getTypeFrom(Class<?> clazz) {
        return getRegistry().getTypeLookup().get(clazz);
    }

    public String name() {
        return getFieldIterator(this).getName(this);
    }

    public static FieldIterator<PacketType> getFieldIterator(final PacketType packetType) {
        switch (packetType.getProtocol()) {
            case HANDSHAKING: return packetType.isClient() ? HandShaking.Client.getInstance() : HandShaking.Server.getInstance();
            case PLAY:  return packetType.isClient() ? Play.Client.getInstance() : Play.Server.getInstance();
            case STATUS: return packetType.isClient() ? Status.Client.getInstance() : Status.Server.getInstance();
            case LOGIN: return packetType.isClient() ? Login.Client.getInstance() : Login.Server.getInstance();
            default:
                throw new IllegalArgumentException("Unexpected protocol: " + packetType.getProtocol());
        }
    }
}
