package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final String IP = ;
    public static final int PORT = ;
    public static LinkedList<CSInteraction> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started (PORT " + PORT + ")");
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = server.accept();
                System.out.println("New connection from " + socket.getRemoteSocketAddress());
                try {
                    serverList.add(new CSInteraction(socket));

                } catch (IOException e) {
                    socket.close();
                }
            }
        }
    }
}
