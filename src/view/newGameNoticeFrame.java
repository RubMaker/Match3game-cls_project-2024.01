package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.GameController;
import controller.autoGameController;
import model.Chessboard;

public class newGameNoticeFrame extends JFrame implements ActionListener{
    private JButton Menu;
    private JButton Resart;
    private int presentLevel;

    public newGameNoticeFrame(int level) {
        super("New Game");

        // 设置布局管理器为FlowLayout
        setLayout(null);

        ImageIcon icon=new ImageIcon("lib/picture/newGame.png");
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);

        // 创建按钮
        Menu= new JButton("Menu");
        Resart = new JButton("Restart");
        Menu.setLocation(80,100);
        Menu.setSize(100,45);
        Resart.setLocation(250,100);
        Resart.setSize(100,45);
        Menu.addActionListener(this);
        Resart.addActionListener(this);

        Font newFont = new Font("Arial", Font.ITALIC, 20);
        Menu.setFont(newFont);
        Resart.setFont(newFont);


        Menu.setBackground(Color.black);
        Menu.setOpaque(false);
        Menu.setBorderPainted(false);
        Resart.setBackground(Color.black);
        Resart.setOpaque(false);
        Resart.setBorderPainted(false);
        // 添加按钮到窗口
        add(Menu);
        add(Resart);
        presentLevel=level;

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocation(0,0);
        setLocationRelativeTo(null); // 将窗口放置在屏幕中央
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Menu){
            this.dispose();
            ModeSelectionFrame newGame=new ModeSelectionFrame();
            newGame.setVisible(true);
        }
        if(e.getSource() == Resart){
            this.dispose();
            if(presentLevel==1){
                try {
                    ChessGameFrame secondFrame=new ChessGameFrame(1100, 810,10);
                    GameController controller=new GameController(secondFrame.getChessboardComponent(), new Chessboard(0));
                    controller.setStep(8);
                    secondFrame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(presentLevel==2){
                try {
                    ChessGameFrame secondFrame=new ChessGameFrame(1100, 810,8);
                    GameController controller=new GameController(secondFrame.getChessboardComponent(), new Chessboard(0));
                    controller.setStep(5);
                    secondFrame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(presentLevel==3){
                try {
                    ChessGameFrame secondFrame=new ChessGameFrame(1100, 810,5);
                    GameController controller=new GameController(secondFrame.getChessboardComponent(), new Chessboard(0));
                    controller.setStep(5);
                    secondFrame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


}
