package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class debateGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int ONE_CHESS_SIZE;

    private int initialStep;

    private debateChessboardComponent debatechessboardComponent;
    private BoardforGUI board;

    private JPanel backgroundPanel;
    static private JLabel MyscoreLabel;
    static private JLabel MystepLabel;
    static private JLabel HescoreLabel;
    static private JLabel HestepLabel;
    static private JButton shuffleButton;

    public debateGameFrame(int width, int height,int initialStep) throws IOException {
        setTitle("Match-3 Game");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.initialStep=initialStep;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        // Create background panel
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw background (you can customize this)
                g.setColor(Color.PINK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WIDTH, HEIGHT);
        add(backgroundPanel);
        addChessboard();
        addLabel();
        addHelloButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addScore();
        addStep();
        addSijuButton();
        addHintButton();
    }
    public BoardforGUI getBoard() {return board;}

    public debateChessboardComponent getdebateChessboardComponent(){
        return debatechessboardComponent;
    }
    private void addChessboard() {
        debatechessboardComponent = new debateChessboardComponent(ONE_CHESS_SIZE);
        debatechessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        backgroundPanel.add(debatechessboardComponent);
        board = new BoardforGUI(ONE_CHESS_SIZE);
        System.out.println(HEIGHT);
        board.setLocation(HEIGHT *7/ 5, HEIGHT / 10);
        backgroundPanel.add(board);
        //backgroundPanel.add(chessboardComponent.getMovePanel(),1);
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(statusLabel);
    }

    private void addScore() {
        MyscoreLabel = new JLabel("MyScore: 0");
        MyscoreLabel.setLocation(HEIGHT / 4, HEIGHT / 25);
        MyscoreLabel.setSize(120, 30);
        MyscoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(MyscoreLabel);

        HescoreLabel = new JLabel("HisScore: 0");
        HescoreLabel.setLocation(HEIGHT*4/3, HEIGHT / 25);
        HescoreLabel.setSize(120, 30);
        HescoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(HescoreLabel);

    }

    static public void setMyScore(String str) {
        MyscoreLabel.setText("Score: " + str);
    }
    static public void setHeScore(String str) {
        HescoreLabel.setText("Score: " + str);
    }

    private void addStep() {
        MystepLabel = new JLabel("MySteps: "+String.valueOf(initialStep));
        MystepLabel.setLocation(HEIGHT*2 / 3, HEIGHT / 25);
        MystepLabel.setSize(120, 30);
        MystepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(MystepLabel);

        HestepLabel = new JLabel("HisSteps: "+String.valueOf(initialStep));
        HestepLabel.setLocation(HEIGHT*5 / 3, HEIGHT / 25);
        HestepLabel.setSize(120, 30);
        HestepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(HestepLabel);

    }
    static public void setMyStep(String str) {
        MystepLabel.setText("Steps: " + str);
    }

    static public void setHeStep(String str) {
        HestepLabel.setText("Steps: " + str);
    }

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(button);
    }

    private void addSijuButton() {
        shuffleButton = new JButton("Shuffle 3");
        shuffleButton.addActionListener((e) -> debatechessboardComponent.shuffleChess());
        shuffleButton.setLocation(0, 10);
        shuffleButton.setSize(150, 30);
        shuffleButton.setFont(new Font("Shuffle", Font.BOLD, 15));
        backgroundPanel.add(shuffleButton);
    }
    static public void setShufflenum(String str) {
        shuffleButton.setText("Shuffle " + str);
    }

    private void addHintButton() {
        JButton button = new JButton("Hint");
        button.addActionListener((e) -> debatechessboardComponent.hintChess());
        button.setLocation(0, 50);
        button.setSize(100, 20);
        button.setFont(new Font("Hint", Font.BOLD, 10));
        backgroundPanel.add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> debatechessboardComponent.swapChess());
        button.setLocation(HEIGHT, HEIGHT / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> debatechessboardComponent.nextStep());
        button.setLocation(HEIGHT, HEIGHT / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        backgroundPanel.add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = "data/data.txt";
            try {
                debatechessboardComponent.getGameController().loadGameFromFile(path);
            } catch (Exception ex) {
                System.out.println("The data has been corrupted.");
                ex.printStackTrace();
            }
        });
    }

    //当交换操作无法消除，进行弹窗提示
    public static void failSwap() {
        JOptionPane.showMessageDialog(null, "This swap can't work. Try again.");
    }

    //当敲击nextStep按钮已无法进行下一步操作，进行弹窗提示
    public static void uselessAction(){
        JOptionPane.showMessageDialog(null, "Time to swap again.");
    }

    //当交换操作违法时，进行弹窗提示
    public static void illegalSwap(){
        JOptionPane.showMessageDialog(null, "You haven't selected two chesses.");
    }

    //当试图进行交换操作时，棋盘上存在3-matches,进行弹窗提示
    public static void stillNeedToEliminate(){
        JOptionPane.showMessageDialog(null, "There are still 3-matches the chessboard. Click Next Step to eliminate them.");
    }

    //当操作步数达到上限时，进行弹窗提示并终止游戏
    public static void FailTheGame(){
        JOptionPane.showMessageDialog(null, "You didn't get enough scores. Game Fail.");
    }
    public static void HinttoShufft(){
        JOptionPane.showMessageDialog(null, "You didn't get enough scores. Game Fail.");
    }
    public static void FailShuffle() {
        //可以加入一个是否重新开一局的按钮
        JOptionPane.showMessageDialog(null, "Your shuffle count has been used up.");
    }
    public static void Failed() {
        //可以加入一个是否重新开一局的按钮
        JOptionPane.showMessageDialog(null, "You failed！！！");
    }
    public static void Win() {
        //可以加入一个是否重新开一局的按钮
        JOptionPane.showMessageDialog(null, "Congratulations！！！");
    }
}
