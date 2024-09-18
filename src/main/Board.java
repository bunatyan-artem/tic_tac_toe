package main;

import java.util.Random;

public class Board {
    static int[][] grid = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    static char[] _grid = new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static char[] keyboard = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8'};

    static boolean IsValidMove(final int x, final int y){
        return grid[x][y] == 0;
    }

    static boolean IsValidMove(final int... xy){
        return grid[xy[0]][xy[1]] == 0;
    }

    static boolean IsValidMove(final int x){
        return _grid[x] == ' ';
    }

    static boolean IsWinMove(final int x, final int y, final boolean p1_move_flag){
        if(!IsValidMove(x, y))return false;

        grid[x][y] = p1_move_flag ? 1 : 2;

        if(grid[x][0] == grid[x][1] && grid[x][2] == grid[x][1] && grid[x][0] != 0){
            grid[x][y] = 0;
            return true;
        }

        if(grid[0][y] == grid[1][y] && grid[2][y] == grid[1][y] && grid[0][y] != 0){
            grid[x][y] = 0;
            return true;
        }

        if((x + y) % 2 == 0 && grid[1][1] != 0 && (grid[0][0] == grid[1][1] && grid[2][2] == grid[1][1] ||
                                                   grid[0][2] == grid[1][1] && grid[2][0] == grid[1][1])){
            grid[x][y] = 0;
            return true;
        }

        grid[x][y] = 0;
        return false;
    }

    static boolean IsWinMove(final int[] xy, final boolean p1_move_flag){
        return IsWinMove(xy[0], xy[1], p1_move_flag);
    }

    static boolean IsWinMove(final int x, final boolean p1_move_flag){
        return switch (x){
            case 0 -> IsWinMove(0, 0, p1_move_flag);
            case 1 -> IsWinMove(0, 1, p1_move_flag);
            case 2 -> IsWinMove(0, 2, p1_move_flag);
            case 3 -> IsWinMove(1, 0, p1_move_flag);
            case 4 -> IsWinMove(1, 1, p1_move_flag);
            case 5 -> IsWinMove(1, 2, p1_move_flag);
            case 6 -> IsWinMove(2, 0, p1_move_flag);
            case 7 -> IsWinMove(2, 1, p1_move_flag);
            case 8 -> IsWinMove(2, 2, p1_move_flag);
            default -> false;
        };
    }

    static int[] GetXYByNum(final int num){
        return switch (num) {
            case 0 -> new int[]{0, 0};
            case 1 -> new int[]{0, 1};
            case 2 -> new int[]{0, 2};
            case 3 -> new int[]{1, 0};
            case 4 -> new int[]{1, 1};
            case 5 -> new int[]{1, 2};
            case 6 -> new int[]{2, 0};
            case 7 -> new int[]{2, 1};
            case 8 -> new int[]{2, 2};
            default -> new int[]{-1, -1};
        };
    }

    static int GetNumByXY(final int x, final int y){
        return 3 * x + y;
    }

    static int GetNumByXY(final int... xy){
        return 3 * xy[0] + xy[1];
    }

    static String GetPossibleMoves(){
        String answer = "";
        for(int i = 0; i < 9; ++i)
            if(IsValidMove(GetXYByNum(i)))
                answer += i;

        return answer;
    }

    static void MakeMove(final int x, final int y, final boolean p1_move_flag){
        grid[x][y] = p1_move_flag ? 1 : 2;
        _grid[GetNumByXY(x, y)] = p1_move_flag ? 'X' : 'O';
        keyboard[GetNumByXY(x, y)] = ' ';
    }

    static void MakeMove(final int[] xy, final boolean p1_move_flag){
        MakeMove(xy[0], xy[1], p1_move_flag);
    }

    static void MakeMove(final int x, final boolean p1_move_flag){
        MakeMove(GetXYByNum(x), p1_move_flag);
    }

    static boolean MakeMove(){
        String moves = GetPossibleMoves();
        String needed_moves = "";
        for(char c : moves.toCharArray()){
            if(IsWinMove(c - '0', true))
                needed_moves += c;
        }
        if(!needed_moves.isEmpty())
            moves = needed_moves;

        int rand = new Random().nextInt(moves.length());
        boolean game_over_flag = GameOverAction(moves.charAt(rand) - '0', false);
        MakeMove(moves.charAt(rand) - '0', false);
        return game_over_flag;
    }

    static void PrintGrid(){
        System.out.printf("  %s  |  %s  |  %s         %s  |  %s  |  %s  %n-----------------     -----------------%n  %s  |  %s  |  %s         %s  |  %s  |  %s  %n-----------------     -----------------%n  %s  |  %s  |  %s         %s  |  %s  |  %s  %n%n",
                _grid[0], _grid[1], _grid[2], keyboard[0], keyboard[1], keyboard[2], _grid[3], _grid[4], _grid[5], keyboard[3], keyboard[4], keyboard[5], _grid[6], _grid[7], _grid[8], keyboard[6], keyboard[7], keyboard[8]);
    }

    static boolean GameOverAction(final int x, final boolean p1_move_flag){
        if(IsWinMove(x, p1_move_flag))
            System.out.println(p1_move_flag ? "You win!" : "You lost!");
        else if(GetPossibleMoves().length() == 1)
            System.out.println("draw");
        else return false;
        return true;
    }

    static void NewGame(){
        grid = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        _grid = new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        keyboard = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8'};
    }
}
