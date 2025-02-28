package chat.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

    public abstract short getId();

    public abstract void write(DataOutputStream dos) throws IOException;

    public abstract  void read(DataInputStream dis) throws IOException;


}
