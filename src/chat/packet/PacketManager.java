package chat.packet;

import java.util.HashMap;
import java.util.Map;

public class PacketManager {

    private static final Map<Short, Class<? extends Packet>> packets = new HashMap<>();

    static {
        packets.put((short) 1, PacketAuthorize.class);
        packets.put((short) 2, PacketMessage.class);
    }

    public static Packet getPacket(short id) {
        try {
            return packets.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return  null;
        }
    }
}