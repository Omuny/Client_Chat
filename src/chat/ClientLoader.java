package chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientLoader {

    private static Socket socket;

    public static void main(String[] args) {
        connect();
        handle();
        end();
    }

    private static void connect() {
        try {
            socket = new Socket("localhost", 1446);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void handle() {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(117);
            dos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void end() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}