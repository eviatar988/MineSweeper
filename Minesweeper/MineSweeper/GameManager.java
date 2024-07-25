package MineSweeper;


import java.util.Scanner;

public class GameManager {
    private int health;
    private Board board;
    private long startTime;
    private String dbpath;


    public GameManager(int level) {
        Settings settings = new Settings(level);
        dbpath = ScoreBoard.createScoreDB();
        board = new Board(settings);
        health = 10 - 3 * level;
        startTime = System.currentTimeMillis();
    }

    public int leftClick(int row, int col){
        int damage = board.revealCell(row, col);
        health -= damage;

        //check if game ended:
        if (health <= 0) {
            System.out.println("you lost!");
            return 0;
        }
        if (board.checkIfWon()) {
            System.out.println("you won!");
            return 1;
        }
        return -1;
    }

    public void rightClick(int row, int col){
        board.toggleFlag(row, col);
    }

    public double getTime() {
        long currentTime = System.currentTimeMillis() - startTime;
        return currentTime / 1000.0;
    }

    public void saveWinner(String name) {
        Player player = new Player(name, getTime());
        if(dbpath == null) {
            dbpath = ScoreBoard.createScoreDB();
        }
        ScoreBoard SB = new ScoreBoard(dbpath);
        SB.addScore(player);
        this.useScoreboard(SB);
    }

    public void useScoreboard(ScoreBoard SB) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("would you like to use ScoreBoard functions? [y/n]");
        while("y" == scanner.nextLine())
        {
            System.out.println("1. Show top 3");
            System.out.println("2. del a player's score");
            int input = scanner.nextInt();
            switch (input) {
                case 1 -> {
                    SB.top3Scores.toString();
                }
                case 2 -> {
                    System.out.println("input name to be deleted");
                    String victim = scanner.nextLine();
                    SB.delScore(victim);
                }
            }
            System.out.println("would you like to continue to use ScoreBoard functions? [y/n]");
        }
    }

    public String[][] getBoard(){
        return board.getBoard();
    }

    public int getHealth(){
        return health;
    }
}
