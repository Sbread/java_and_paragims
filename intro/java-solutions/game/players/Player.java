package game.players;

import game.*;

public interface Player {
    Move move(Position position, int amountOfRequests);
    int answer();
}
