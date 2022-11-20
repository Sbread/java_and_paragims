package game;

import game.players.*;

public class TwoPlayersGame {
    private final boolean log;
    private final Player player1, player2;

    public TwoPlayersGame(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        while (true) {
            final int result1 = move(board, player1, 1, 0);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, player2, 2, 0);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int move(final Board board, final Player player, final int no, final int amountOfRequests) {
        final Move move = player.move(board.getPosition(), amountOfRequests);
        if (move.getResult() != null) {
            if (move.getResult() == 0) {
                log("Player " + no + " gave up");
                log("Player " + no + " lose");
                return 3 - no;
            } else if (move.getResult() == 1) {
                int answer;
                log("Player " + no + " request draw");
                if (player1.equals(player)) {
                    answer = player2.answer();
                } else {
                    answer = player.answer();
                }
                if (answer == 1) {
                    log("Player " + (3 - no) + " accept draw\n");
                    log("Draw");
                    return 0;
                } else {
                    log("Player " + (3 - no) + " decline draw\n");
                    return move(board, player, no, amountOfRequests + 1);
                }
            } else {
                return -1;
            }
        } else {
            final Result result = board.makeMove(move);
            log("Player " + no + " move: " + move);
            log("Position:\n" + board);
            if (result == Result.WIN) {
                log("Player " + no + " won");
                return no;
            } else if (result == Result.LOSE) {
                log("Player " + no + " lose");
                return 3 - no;
            } else if (result == Result.DRAW) {
                log("Draw");
                return 0;
            } else {
                return -1;
            }
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
