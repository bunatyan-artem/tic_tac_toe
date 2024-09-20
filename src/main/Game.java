package main;

public class Game{
    CSInteraction p1, p2;
    boolean p1_first_move;
    Game(final CSInteraction p1, final CSInteraction p2, final boolean p1_first_move) {
        this.p1 = p1;
        this.p2 = p2;
        this.p1_first_move = p1_first_move;

        p1.send("GAME.....");
        p2.send("GAME.....");

        p1.send("You win. Another game? y/n");
        p2.send("You loose. Another game? y/n");

        p1.inGame = false;
    }
}
