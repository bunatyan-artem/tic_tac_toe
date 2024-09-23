package main;

import java.io.*;
import java.net.Socket;

class CSInteraction extends Thread{

    String name;
    final BufferedReader in;
    private final BufferedWriter out;
    boolean inGame = false;
    boolean invite_flag = false;
    char answer;
    boolean answer_flag = false;
    boolean leave_flag = false;

    CSInteraction(Socket socket) throws IOException{
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run(){
        String s;
        try {
            name = in.readLine();
            sendListsOfPlayers();

            while (true) {
                s = in.readLine();

                if(s.equals("refresh"))continue;
                else if(s.equals("leave")){
                    Server.serverList.remove(this);
                    sendListsOfPlayers();

                    leave_flag = true;
                    answer_flag = true;
                    invite_flag = false;

                    break;
                }
                else if(invite_flag){
                    answer = s.charAt(0);
                    answer_flag = true;
                    do{
                        Thread.sleep(100);
                    }while(inGame);
                }
                else
                    createGame(this, usrFromName(s));
                sendListOfPlayers();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }

    public void send(String msg){
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }

    private void sendListOfPlayers(){
        this.send("List of players:");
        String s = "";
        for(CSInteraction usr: Server.serverList) {
            if(usr == this || usr.name == null) continue;
            s += usr.name;
            if(usr.inGame)
                s += "(in game) ";
            else s += " ";
        }
        if(s.isEmpty())s = "You are the only user online";
        this.send(s);
    }

    private static void sendListsOfPlayers(){
        for(CSInteraction usr: Server.serverList)
            if(usr.name != null && !usr.inGame)
                usr.sendListOfPlayers();
    }

    static CSInteraction usrFromName(final String name){
        for(CSInteraction usr: Server.serverList)
            if(name.equals(usr.name))
                return usr;
        return null;
    }

    static void createGame(final CSInteraction p1, final CSInteraction p2) throws IOException, InterruptedException {
        if(p2 == null) {
            p1.send("Something went wrong");
            p1.sendListOfPlayers();
            return;
        }
        if(p2.inGame) {
            p1.send(p2.name + " now in game");
            p1.sendListOfPlayers();
            return;
        }

        while(p2.invite_flag)
            Thread.sleep(100);
        if(p2.leave_flag){
            p1.send(p2.name + " left");
            p1.sendListOfPlayers();
            return;
        }
        p2.invite_flag = true;

        p2.send(p1.name + " invited you. Do you want to play? y/n ");

        while(!p2.answer_flag)
            Thread.sleep(100);
        if(p2.leave_flag){
            p1.send(p2.name + " left");
            p1.sendListOfPlayers();
            return;
        }
        p2.invite_flag = false;
        p2.answer_flag = false;

        String s = String.valueOf(p2.answer), s2;

        if(s.equals("n")){
            p1.send(p2.name + " refused yor invite");
            return;
        }
        else if(p1.inGame) {
            p2.send(p1.name + " now in game");
            p2.sendListOfPlayers();
        }
        else p1.send("Game accepted");

        boolean p1_first_move = false;
        p1.inGame = true;
        p2.inGame = true;
        do {
            new Game(p1, p2, p1_first_move);

            s = p1.in.readLine();
            s2 = p2.in.readLine();
            p1_first_move = !p1_first_move;
        } while(s.equals("y") && s2.equals("y") && !p1.leave_flag && !p2.leave_flag);
        p1.inGame = false;
        p2.inGame = false;
    }
}
