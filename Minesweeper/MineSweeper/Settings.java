package MineSweeper;

public class Settings {
    private int numOfBombs;
    private int difficulty;
    private int boardSize;
    
    public Settings(int difficulty) {
        this.difficulty = difficulty;
        switch (difficulty) {
            case 1 -> {
                numOfBombs = 10;
                boardSize = 9;
            }
            case 2 -> {
                numOfBombs = 40;
                boardSize = 16;
            }
            case 3 -> {
                numOfBombs = 99;
                boardSize = 16;
            }
        }
    }
    
    public int getNumOfBombs() {
        return numOfBombs;
    }
    
    public int getBoardSize() {
        return boardSize;
    }
}
