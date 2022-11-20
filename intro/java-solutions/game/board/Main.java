package game.board;

import game.TwoPlayersGame;
import game.players.*;

import java.util.Scanner;

public class Main {
    public static final int n = 11;
    public static final int m = 11;
    public static final int k = 3;

    public static void main(String[] args) {
        int result;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, choose the game mod. Entry \"T\" or \"H\"");
        String gameMod = scanner.next();
        final TwoPlayersGame game = new TwoPlayersGame(true, new HumanPlayer(), new HumanPlayer());
        while (true) {
            if (gameMod.equals("T")) {
                result = game.play(new TicTacToeBoard(n, m, k));
                break;
            } else if (gameMod.equals("H")) {
                result = game.play(new HexBoard(n, m, k));
                break;
            } else {
                System.out.println("This is not a game mod. Entry mod \"T\" or \"H\"");
                gameMod = scanner.next();
            }
        }
        System.out.println("Game result: " + result);
    }
}