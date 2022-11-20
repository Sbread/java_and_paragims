package game;

public final class Move {
    private final int row;
    private final int column;
    private final Cell value;
    private Integer result = null;

    public Move(final int row, final int column, final Cell value, final Integer result) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.result = result;
    }

    public int getRow() {
        return row;
    }

    public int getDiag() {
        return column;
    }

    public int getColumn() {
        return column;
    }

    public Cell getValue() {
        return value;
    }

    public Integer getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}
