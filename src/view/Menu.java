package view;
import controller.GameController;
import controller.specialGameController;
import model.Chessboard;
import model.Restart;
import model.specialChessboard;
import model.user;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    //定义两个按钮
    private JButton Game1;
    private JButton Game2;
    private JButton Game3;
    private JButton Game4;
    private JButton Savedgame;

    public Menu() {
        ImageIcon icon = new ImageIcon("lib/picture/menu.jpg");
        JLabel label = new JLabel(icon);

        //设置label的大小
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) this.getContentPane();
        j.setOpaque(false);

        Game1 = new JButton("Easy");
        Game1.setSize(200, 60);
        Game2 = new JButton("Normal");
        Game2.setSize(200, 60);
        Game3 = new JButton("Difficult");
        Game3.setSize(200, 60);
        Game4 = new JButton("Surprise!");
        Game4.setSize(200, 60);
        Savedgame = new JButton("Load");
        Savedgame.setSize(200, 60);
        Font newFont = new Font("Arial", Font.ITALIC, 25);
        Game1.setFont(newFont);
        Game2.setFont(newFont);
        Game3.setFont(newFont);
        Game4.setFont(newFont);
        Savedgame.setFont(newFont);

        //不知道为什么，但是不加一个面板就会有问题
        JPanel Panel = new JPanel();

        Game1.setLocation(500, 120);
        Game2.setLocation(500, 240);
        Game3.setLocation(500, 360);
        Game4.setLocation(500, 480);
        Savedgame.setLocation(500, 600);
        Game1.addActionListener(this);
        Game2.addActionListener(this);
        Game3.addActionListener(this);
        Game4.addActionListener(this);
        Savedgame.addActionListener(this);
        //必须设置为透明的。否则看不到图片
        Panel.setOpaque(false);

        Game1.setBackground(Color.black);
        Game2.setBackground(Color.black);
        Game3.setBackground(Color.black);
        Game4.setBackground(Color.black);
        Savedgame.setBackground(Color.black);

        Game1.setBorderPainted(false);
        Game2.setBorderPainted(false);
        Game3.setBorderPainted(false);
        Game4.setBorderPainted(false);
        Savedgame.setBorderPainted(false);


        Game1.setOpaque(false);
        Game2.setOpaque(false);
        Game3.setOpaque(false);
        Game4.setOpaque(false);
        Savedgame.setOpaque(false);
        this.add(Game1);
        this.add(Game2);
        this.add(Game3);
        this.add(Game4);
        this.add(Savedgame);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1150, 850);
        setLocationRelativeTo(null);

        this.add(Panel);
        Panel.setSize(1100, 810);
        Panel.setLocation(0, 0);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Game1) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810, 10);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                    gameController.setStep(10);
                    String inputUserName = JOptionPane.showInputDialog("Tell me your username:");
                    user newUser = new user(inputUserName);
                    gameController.setUser(newUser);
                    mainFrame.setVisible(true);
                } catch (Exception E) {
                    System.out.println("The data has been corrupted.");
                    E.printStackTrace();//打印异常的栈
                }
            });
        }
        if (e.getSource() == Game2) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810, 8);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                    gameController.setStep(8);
                    String inputUserName = JOptionPane.showInputDialog("Tell me your username:");
                    user newUser = new user(inputUserName);
                    gameController.setUser(newUser);
                    mainFrame.setVisible(true);
                } catch (Exception E) {
                    System.out.println("The data has been corrupted.");
                    E.printStackTrace();//打印异常的栈
                }
            });
        }
        if (e.getSource() == Game3) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810, 5);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                    gameController.setStep(5);
                    String inputUserName = JOptionPane.showInputDialog("Tell me your username:");
                    user newUser = new user(inputUserName);
                    gameController.setUser(newUser);
                    mainFrame.setVisible(true);
                } catch (Exception E) {
                    System.out.println("The data has been corrupted.");
                    E.printStackTrace();//打印异常的栈
                }
            });
        }
        if (e.getSource() == Game4) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    specialChessGameFrame mainFrame = new specialChessGameFrame(1100, 810, 10);
                    specialGameController gameController = new specialGameController(mainFrame.getChessboardComponent(), new specialChessboard(0));
                    gameController.setStep(10);
                    String inputUserName = JOptionPane.showInputDialog("Tell me your username:");
                    user newUser = new user(inputUserName);
                    gameController.setUser(newUser);
                    mainFrame.setVisible(true);
                } catch (Exception E) {
                    System.out.println("The data has been corrupted.");
                    E.printStackTrace();//打印异常的栈
                }
            });
        }
        if (e.getSource() == Savedgame) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Restart restart = new Restart();
                    if(restart.check1("data/data.txt")) {
                        JOptionPane.showMessageDialog(null, "101", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(restart.check2("data/data.txt")) {
                        JOptionPane.showMessageDialog(null, "102", "Error", JOptionPane.ERROR_MESSAGE);
                    }else if(restart.check3("data/data.txt")) {
                        JOptionPane.showMessageDialog(null, "103", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        this.dispose();
                        restart.cin("data/data.txt");
                        ChessGameFrame mainFrame = new ChessGameFrame(1100, 810, 0);
                        GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(restart.groupTogrid()));
                        gameController.setInit(restart.getMoveCount(), restart.getCurScore(), restart.getLevel());
                        mainFrame.setVisible(true);
                    }
                } catch (Exception E) {

                    System.out.println("The data has been corrupted.");
                    E.printStackTrace();//打印异常的栈
                }
            });
        }

    }

}