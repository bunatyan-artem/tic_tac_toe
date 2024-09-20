package main;

import java.io.*;
import java.net.Socket;

public class Client{

    static Socket clientSocket;
    static BufferedReader reader;
    static BufferedReader in;
    static BufferedWriter out;
    static boolean leave_flag = false;

    public static void main(String[] args) throws IOException {
        try {
            try {
                clientSocket = new Socket(Server.IP, Server.PORT);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                new CommOut();
                new CommIn();
                while(!leave_flag)
                    Thread.sleep(100);

            } finally {
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}