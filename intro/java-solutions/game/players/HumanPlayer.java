package game.players;

import game.*;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public int answer() {
        out.println("Enter \"accept\" or \"decline\"");
        String answer = in.next();
        if (answer.equals("accept")){
            return 1;
        } else if (answer.equals("decline")){
            return 0;
        } else {
            out.println("answer " + answer + " is not a valid answer" + "\n" +"Enter new answer");
            return answer();
        }
    }

    @Override
    public Move move(final Position position, final int amountOfRequests) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(position.getTurn() + "'s move");
            out.println("Enter row and column or write \"giveUp\" or \"requestDraw\"" );
            Move move;
            do {
                String action = in.next();
                if (action.equals("giveUp")) {
                    return new Move(-1, -1, position.getTurn(), 0);
                } else if (action.equals("requestDraw")){
                    if (amountOfRequests < 1) {
                        return new Move(-2, -2, position.getTurn(), 1);
                    } else {
                        out.println("You can't request draw more than 1 time per move");
                    }
                } else {
                    try {
                        move = new Move(Integer.parseInt(action), in.nextInt(), position.getTurn(), null);
                        if (position.isValid(move)) {
                            return move;
                        } else {
                            out.println("Move " + move + " is invalid" + "\n" + "Enter new move");
                        }
                    }
                    catch (NumberFormatException e){
                        out.println("This is not an action! Enter new move");
                    }
                }
            } while(true);
        }
    }
}
