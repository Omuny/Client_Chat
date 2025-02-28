package chat;

import chat.packet.Packet;
import chat.packet.PacketAuthorize;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientLoader {

    private static Socket socket;

    public static void main(String[] args) {
        connect();
        handle();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
        end();
    }

    public static void sendPacket(Packet packet) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeShort(packet.getId());
            packet.write(dos);
            dos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void connect() {
        try {
            socket = new Socket("localhost", 8888);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void handle() {
        sendPacket(new PacketAuthorize("Ali " + System.currentTimeMillis()));
    }

    private static void end() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}