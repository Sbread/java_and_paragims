package game.players;

import game.*;
import java.util.Random;

public class SequentialPlayer implements Player {
    int n, m;
    final Random random = new Random();

    public SequentialPlayer(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public int answer() {
        return random.nextInt(2);
    }

    @Override
    public Move move(final Position position, final int amountOfRequests) {
        int action = random.nextInt(10);
        if (action == 0){
            return new Move(-1,-1, position.getTurn(), 0);
        }
        if (action == 1 && amountOfRequests < 1){
            return new Move(-2, -2, position.getTurn(), 1);
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                final Move move = new Move(r, c, position.getTurn(), null);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
