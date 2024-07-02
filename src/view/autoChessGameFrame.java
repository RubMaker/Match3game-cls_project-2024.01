package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class autoChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int ONE_CHESS_SIZE;

    private int initialStep;

    private autoChessboardComponent chessboardComponent;
    
    static private JLabel scoreLabel;
    static private JLabel stepLabel;
    static private JButton shuffleButton;

    public autoChessGameFrame(int width, int height,int initialStep) throws IOException {
        setTitle("Match-3 Game");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.initialStep=initialStep;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon icon=new ImageIcon("lib/picture/auto.png");
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(-20,-20,icon.getIconWidth(),icon.getIconHeight());

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);

        
        addChessboard();
        addLabel();
        addNewGameButton();
        addStartButton();
        addLoadButton();
        addScore();
        addStep();
        addSijuButton();
        addHintButton();
    }


    public autoChessboardComponent getChessboardComponent(){
        return chessboardComponent;
    }
    private void addChessboard() {
        chessboardComponent = new autoChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardComponent);
        //add(chessboardComponent.getMovePanel(),1);
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("auto");
        statusLabel.setLocation(HEIGHT+60, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addScore() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setLocation(HEIGHT / 3, HEIGHT / 25);
        scoreLabel.setSize(120, 30);
        scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(scoreLabel);

    }

    static public void setScore(String str) {
        scoreLabel.setText("Score: " + str);
    }

    private void addStep() {
        stepLabel = new JLabel("Steps: "+String.valueOf(initialStep));
        stepLabel.setLocation(HEIGHT / 2, HEIGHT / 25);
        stepLabel.setSize(120, 30);
        stepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(stepLabel);

    }

    static public void setStep(String str) {
        stepLabel.setText("Steps: " + str);
    }

    private void addNewGameButton() {
        JButton button = new JButton("New Game");
        button.addActionListener((e) ->
        {
            ModeSelectionFrame newGame=new ModeSelectionFrame();
            newGame.setVisible(true);
            this.setVisible(false);
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }

    private void addSijuButton() {
        shuffleButton = new JButton("Shuffle 3");
        shuffleButton.addActionListener((e) -> chessboardComponent.shuffleChess());
        shuffleButton.setLocation(5, HEIGHT/3*2-100);
        shuffleButton.setSize(150,50);
        shuffleButton.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(shuffleButton);
    }
    static public void setShufflenum(String str) {
        shuffleButton.setText("Shuffle " + str);
    }

    private void addHintButton() {
        JButton button = new JButton("Hint");
        button.addActionListener((e) -> chessboardComponent.hintChess());
        button.setLocation(5, HEIGHT/3);
        button.setSize(150, 50);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }

    private void addStartButton() {
        JButton button = new JButton("Start");
        button.addActionListener((e) -> chessboardComponent.startGame());
        button.setLocation(HEIGHT, HEIGHT / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);
    }


    private void addLoadButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.ITALIC, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = "data/data.txt";
            try {
                chessboardComponent.getGameController().loadGameFromFile(path);
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
    public static void FailShuffle() {
        //可以加入一个是否重新开一局的按钮
        JOptionPane.showMessageDialog(null, "Your shuffle count has been used up.");
    }


}