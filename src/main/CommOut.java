package main;

public class CommOut extends Thread{
    CommOut() {
        System.out.print("Your name: ");
        start();
    }

    @Override
    public void run(){
        try{
            String word;
            while(!Client.leave_flag){
                do {
                    word = Client.reader.readLine();
                }while(word == null);
                Client.out.write(word + "\n");
                Client.out.flush();
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
