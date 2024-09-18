package main;

import java.util.Scanner;

import static main.Board.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean game_over_flag, p1_moves_first = false;

        while(true) {
            game_over_flag = false;
            p1_moves_first = !p1_moves_first;
            NewGame();

            if(!p1_moves_first)
                MakeMove();
            PrintGrid();

            while (!game_over_flag) {
                int player_move;
                while (true) {
                    player_move = scanner.nextInt();
                    if (IsValidMove(player_move))
                        break;
                    System.out.println("Invalid move");
                    PrintGrid();
                }

                game_over_flag = GameOverAction(player_move, true);
                MakeMove(player_move, true);

                if(!game_over_flag)
                    game_over_flag = MakeMove();

                PrintGrid();
            }

            char answer;
            do {
                System.out.print("Another game?y/n ");
                answer = scanner.next().charAt(0);
            } while (answer != 'y' && answer != 'n');

            if (answer == 'n')
                break;
        }
    }
}