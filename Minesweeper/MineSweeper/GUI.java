package MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    GameManager gameManager;

    public GUI() {
        // get level()
        frame = new JFrame("MineSweeper");
        startScreen();
    }

    private void startScreen(){
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(220, 140);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to MineSweeper!");
        label.setBounds(10, 20, 400, 40);
        panel.add(label);

        JLabel levelLabel = new JLabel("Choose the level of difficulty:");
        levelLabel.setBounds(10, 110, 300, 25);
        panel.add(levelLabel);

        JRadioButton easyButton = new JRadioButton("Easy");
        easyButton.setBounds(10, 140, 75, 25);
        panel.add(easyButton);

        JRadioButton mediumButton = new JRadioButton("Medium");
        mediumButton.setBounds(10, 170, 75, 25);
        panel.add(mediumButton);

        JRadioButton hardButton = new JRadioButton("Hard");
        hardButton.setBounds(10, 200, 75, 25);
        panel.add(hardButton);

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        JButton startButton = new JButton("Start");
        startButton.setBounds(10, 230, 75, 25);
        startButton.addActionListener(e -> {
            if(easyButton.isSelected()){
                gameManager = new GameManager(1);
            }
            else if(mediumButton.isSelected()){
                gameManager = new GameManager(2);
            }
            else if(hardButton.isSelected()){
                gameManager = new GameManager(3);
            }
            gameScreen();
        });
        panel.add(startButton);

        frame.add(panel);
        frame.setVisible(true);
        centerWindow();
    }

    private void gameScreen(){
        frame.remove(panel);
        updateTable();
        frame.setTitle("Lives: "+gameManager.getHealth()+", Time Passed: "+gameManager.getTime());
        // Add MouseListener for left-click action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    // Perform left-click action for the cell at (row, column)
                    // Implement your custom left-click action here
                    leftClick(row, column);
                }
            }
        });
        // Add MouseListener and PopupMenuListener for right-click action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    // Display popup menu for the cell at (row, column)
                    // Implement your custom right-click action here
                    rightClick(row, column);
                }
            }
        });

        frame.add(table);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        table.setBackground(Color.lightGray);
        centerWindow();
    }

    private void rightClick(int row, int column) {
        gameManager.rightClick(row, column);

        gameScreen();
    }

    private void leftClick(int row, int column) {
        int result = gameManager.leftClick(row, column);
        if(result == 0){
            // player lost
            lossWindow();
        }
        else if(result == 1){
            // player won
            winWindow();
            // gameManager.saveWinner();
        }
        else
            gameScreen();
    }

    private void lossWindow(){
        frame.setVisible(false);
        JFrame lossFrame = new JFrame("You Lost");
        lossFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lossFrame.setSize(300, 300);
        lossFrame.setLayout(new BorderLayout());
        JLabel label = new JLabel("You Lost!");
        label.setFont(new Font("Arial", Font.PLAIN, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        lossFrame.add(label, BorderLayout.CENTER);
        lossFrame.setVisible(true);
        centerWindow(lossFrame);
    }

    private void winWindow(){
        frame.setVisible(false);
        JFrame winFrame = new JFrame("You Won!");
        winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winFrame.setSize(300, 300);
        winFrame.setLayout(new BorderLayout());
        JLabel label = new JLabel("You Won!");
        label.setFont(new Font("Arial", Font.PLAIN, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        winFrame.add(label, BorderLayout.CENTER);
        winFrame.setVisible(true);
        centerWindow(winFrame);
    }

    private void updateTable(){
        String[][] data = gameManager.getBoard();
        String[] headers = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            headers[i] = Integer.toString(i);
        }
        table = new JTable(data, headers);
        table.setRowHeight(40);
        for(int i=0;i<data.length;i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(40);
        }
        table.setFont(new Font("Arial", Font.PLAIN, 32));
    }

    private void centerWindow(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,
                dim.height/2-frame.getSize().height/2);
    }
    private void centerWindow(JFrame frame){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,
                dim.height/2-frame.getSize().height/2);
    }
}
