package MineSweeper;

public class Player {
    private String name;
    private double score;


    public Player(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public double getScore() {
        return this.score;
    }
}
