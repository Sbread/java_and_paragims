package game.players;

import game.*;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;
    int n, m;

    public RandomPlayer(final Random random, int n, int m) {
        this.random = random;
        this.n = n;
        this.m = m;
    }

    public RandomPlayer(int n, int m) {
        this(new Random(), n, m);
    }

    @Override
    public int answer() {
        return random.nextInt(2);
    }

    @Override
    public Move move(final Position position, final int amountOfRequests) {
        int action = random.nextInt(100);
        if (action == 0){
            return new Move(-1,-1, position.getTurn(), 0);
        }
        if (action == 1 && amountOfRequests < 1){
            return new Move(-2, -2, position.getTurn(), 1);
        }
        while (true) {
            int r = random.nextInt(n);
            int c = random.nextInt(m);
            final Move move = new Move(r, c, position.getTurn(), null);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}