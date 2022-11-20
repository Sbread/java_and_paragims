//package game.players;
//
//import game.*;
//
//import java.util.Random;
//
//public class CheatingPlayer implements Player {
//    private final int m, n;
//    final Random random = new Random();
//
//    public CheatingPlayer(int n, int m) {
//        this.m = m;
//        this.n = n;
//    }
//
//    @Override
//    public int answer() {
//        return random.nextInt(2);
//    }
//
//    @Override
//    public Move move(Position position, int amountOfRequests) {
//        final TicTacToeBoard board = (TicTacToeBoard) position;
//        Move first = null;
//        for (int r = 1; r <= m; r++) {
//            for (int c = 1; c <= n; c++) {
//                final Move move = new Move(r, c, board.getTurn());
//                if (position.isValid(move)) {
//                    if (first == null) {
//                        first = move;
//                    } else {
//                        board.makeMove(move);
//                    }
//                }
//            }
//        }
//        return first;
//    }
//}