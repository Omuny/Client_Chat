package chat;

import chat.packet.Packet;
import chat.packet.PacketAuthorize;
import chat.packet.PacketManager;
import chat.packet.PacketMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientLoader {

    private static Socket socket;
    private static boolean sentNickname = false;

    public static void main(String[] args) {
        connect();
        handle();
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
        Thread handler = new Thread() {

            @Override
            public void run() {
                while(true) {
                    try {
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        if(dis.available() <= 0) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {}
                            continue;
                        }
                        short id = dis.readShort();
                        Packet packet = new PacketManager().getPacket(id);
                        packet.read(dis);
                        packet.handle();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        handler.start();
        readChat();
    }

    private static void readChat() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if(line.equals("/end")) {
                    end();
                }
                if(!sentNickname) {
                    sentNickname = true;
                    sendPacket(new PacketAuthorize(line));
                    continue;
                }
                sendPacket(new PacketMessage(null, line));
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private static void end() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}