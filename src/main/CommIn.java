package main;

import java.io.BufferedReader;
import java.io.IOException;

public class CommIn extends Thread{
    BufferedReader in;
    CommIn(){
        start();
    }

    @Override
    public void run(){
        String word;
        while(!Client.leave_flag){
            try {
                word = Client.in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(word);
        }
    }
}
