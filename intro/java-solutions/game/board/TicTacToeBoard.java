package game.board;

import game.*;

import java.util.Arrays;
import java.util.Map;

class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final int n;
    private final int m;
    private final int k;
    private int empty;
    private final Cell[][] cells;
    private Cell turn;

    TicTacToeBoard(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.empty = n * m;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    private boolean checkWin(int row, int column, int shiftRow, int shiftColumn) {
        int inRow = 1;
        int i = 1;
        while (0 <= row + i * shiftRow && row + i * shiftRow < n
                && 0 <= column + i * shiftColumn && column + i * shiftColumn < m) {
            if (cells[row + i * shiftRow][column + i * shiftColumn] == cells[row][column]) {
                i++;
                inRow++;
                if (inRow >= k) {
                    return true;
                }
            } else {
                break;
            }
        }
        i = -1;
        while (0 <= row + i * shiftRow && row + i * shiftRow < n
                && 0 <= column + i * shiftColumn && column + i * shiftColumn < m) {
            if (cells[row + i * shiftRow][column + i * shiftColumn] == cells[row][column]) {
                i--;
                inRow++;
                if (inRow >= k) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow() - 1][move.getColumn() - 1] = move.getValue();

        int row = move.getRow() - 1;
        int column = move.getColumn() - 1;
        if (checkWin(row, column, 1, 0)
            || checkWin(row, column, 0, 1)
            || checkWin(row, column, 1, 1)
                || checkWin(row, column, -1, 1)){
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 < move.getRow() && move.getRow() <= n
                && 0 < move.getColumn() && move.getColumn() <= m
                && cells[move.getRow() - 1][move.getColumn() - 1] == Cell.E
                && turn == getTurn();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int i = 1; i <= n; i++) {
            sb.append(String.format("%4d", i));
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(String.format("%4d", r + 1));
            for (int c = 0; c < m; c++) {
                sb.append(String.format("%4s", SYMBOLS.get(cells[r][c])));
            }
        }
        return sb.toString();
    }
}
