package MineSweeper;

import java.util.Random;

public class Board {
    private Cell[][] cells;
    private int size;
    private int mineCount;
    private int flagCount;
    private int revealCount;

    Board(Settings settings) {
        flagCount = 0;
        revealCount = 0;
        size = settings.getBoardSize();
        mineCount = settings.getNumOfBombs();
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                cells[i][j] = new EmptyCell();
            }
        putMines();
    }

    public String[][] getBoard(){
        String[][] stringBoard = new String[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                stringBoard[i][j] = cells[i][j].toString();
            }
        }
        return stringBoard;
    }

    private void putMines(){
        Random rand = new Random();
        int minesToPut = mineCount;
        while (minesToPut > 0) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            if (cells[row][col] instanceof EmptyCell) {
                int damage = rand.nextInt(1, 4);
                cells[row][col] = new MinedCell(damage);
                System.out.println("mine put at: " + row + ", " + col);
                updateAroundMine(row, col);
                minesToPut--;
            }
        }
    }

    private boolean inBoundries(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            return false;
        return true;
    }


    private void updateAroundMine(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++) {
                if (inBoundries(i, j) && !(i == row && j == col) && cells[i][j] instanceof EmptyCell) {
                    ((EmptyCell) cells[i][j]).addMine();
                }
            }
    }

    public void printBoard() {
        System.out.println("needed to win: "+(size * size - mineCount));
        System.out.print("  ");
        for (int i = 0; i < size; i++)
            System.out.print(i + "  ");
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++)
                System.out.print(cells[i][j].toString() + " ");
            System.out.println();
        }
    }

    public void toggleFlag(int row, int col) {
        if (!cells[row][col].isFlagged() && flagCount == mineCount) {
            System.out.println("You can't put more flags!");
        }
        else {
            flagCount += cells[row][col].toggleFlag();
        }
    }

    public int revealCell(int row, int col) {
        if (cells[row][col].isFlagged()) {
            System.out.println("You can't reveal a flagged cell!");
            return 0;
        }
        int damage = cells[row][col].reveal();
        if (damage == 0) {
            revealCount++;
            if (((EmptyCell) cells[row][col]).getAdjacentMines() == 0)
                revealAround(row, col);
        }
        return damage;
    }

    private void revealAround(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++) {
                if (inBoundries(i, j)) {
                    if (!cells[i][j].isRevealed()) {
                        cells[i][j].reveal();
                        revealCount++;
                        if (((EmptyCell) cells[i][j]).getAdjacentMines() == 0)
                            revealAround(i, j);
                    }
                }
            }
    }

    public boolean checkIfWon() {
        return revealCount == size * size - mineCount;
    }

    public int getRevealCount() {
        return revealCount;
    }
}
