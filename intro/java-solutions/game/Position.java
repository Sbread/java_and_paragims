package game;

public interface Position {
    boolean isValid(Move move);
    Cell getTurn();
    Cell getCell(int r, int c);
}
