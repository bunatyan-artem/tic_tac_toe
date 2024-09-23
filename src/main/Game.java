package main;

import java.io.IOException;

public class Game{
    Board board;
    CSInteraction p1, p2;
    char status = '0';  //'0' - игра идет, '1' - первый победил(this.p1), '2' - второй победил, '3' - ничья, '4' - первый сдался, '5' - второй сдался
    Game(final CSInteraction p1, final CSInteraction p2, boolean p1_move_flag) throws IOException {
        if(p1_move_flag) {
            this.p1 = p1;
            this.p2 = p2;
        }
        else{
            this.p1 = p2;
            this.p2 = p1;
        }

        board = new Board();
        p1_move_flag = true;
        while(status == '0'){
            if (p1_move_flag)
                move(this.p1, p1_move_flag, this.p2);
            else
                move(this.p2, p1_move_flag, this.p1);

            switch (status){
                case '1':
                    this.p1.send("You win. Another game? y/n");
                    this.p2.send("You lose. Another game? y/n");
                    break;
                case '2':
                    this.p2.send("You win. Another game? y/n");
                    this.p1.send("You lose. Another game? y/n");
                    break;
                case '3':
                    this.p1.send("Draw. Another game? y/n");
                    this.p2.send("Draw. Another game? y/n");
                    break;
                case '4':
                    this.p2.send("You win(ff). Another game? y/n");
                    this.p1.send("You lose(ff). Another game? y/n");
                    break;
                case '5':
                    this.p1.send("You win(ff). Another game? y/n");
                    this.p2.send("You lose(ff). Another game? y/n");
                    break;
            }
            p1_move_flag = !p1_move_flag;
        }
        p1.inGame = false;
    }

    void move(CSInteraction p, boolean p1_move_flag, CSInteraction p2) throws IOException {
        p2.send("Waiting for the opponent's move");
        p2.send(board.GetGrid());

        p.send("Your move");
        p.send(board.GetGrid());
        int player_move;
        while (true) {
            player_move = p.in.readLine().charAt(0) - '0';
            if(player_move == 9){
                status = p1_move_flag ? '4' : '5';
                return;
            }
            if (board.IsValidMove(player_move))
                break;
            p.send("Invalid move");
            p.send(board.GetGrid());
        }

        if (board.IsWinMove(player_move, p1_move_flag))
            status = p1_move_flag ? '1' : '2';
        else if(board.GetPossibleMoves().length() == 1)
            status = '3';
        board.MakeMove(player_move, p1_move_flag);
    }
}
