package MineSweeper;

public class Cell {
    private boolean isRevealed;
    private boolean isFlagged;

    public Cell() {
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public int toggleFlag() {
        this.isFlagged = !this.isFlagged;
        if (this.isFlagged)
            return 1;
        return -1;
    }

    public int reveal() {
        return 0;
    }

    public String toString() {
        if(this.isFlagged())
            return "\uD83D\uDEA9";
        return "";
    }
}
