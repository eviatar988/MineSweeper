package MineSweeper;

public class EmptyCell extends Cell{

        private int adjacentMines;

        public EmptyCell() {
            adjacentMines = 0;
        }

        public int getAdjacentMines() {
            return adjacentMines;
        }

        public void addMine() {
            adjacentMines++;
        }

        public int reveal() {
            if (this.isRevealed()) {
                System.out.println("this cell is already revealed!");
                return 0;
            }
            this.setRevealed(true);
            return 0;
        }

        public String toString() {
            if (this.isRevealed())
                return (" " + this.adjacentMines);
            return super.toString();
        }
}

