package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.GameController;
import controller.autoGameController;
import model.Chessboard;

public class nextLevelFrame extends JFrame implements ActionListener{
    private JButton End;
    private JButton New;
    private int presentLevel;

    public nextLevelFrame(int level) {

        super("Choice");

        // 设置布局管理器为FlowLayout
        setLayout(null);

        ImageIcon icon=new ImageIcon("lib/picture/nextLevel.png");
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,30,icon.getIconWidth(),icon.getIconHeight());

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);

        // 创建按钮
        End = new JButton("End");
        New = new JButton("New");
        End.setLocation(100,120);
        End.setSize(100,60);
        New.setLocation(300,120);
        New.setSize(100,60);
        End.addActionListener(this);
        New.addActionListener(this);

        Font newFont = new Font("Arial", Font.ITALIC, 20);
        New.setFont(newFont);
        End.setFont(newFont);

        New.setBackground(Color.black);
        New.setOpaque(false);
        New.setBorderPainted(false);
        End.setBackground(Color.black);
        End.setOpaque(false);
        End.setBorderPainted(false);
        // 添加按钮到窗口
        add(End);
        add(New);
        presentLevel=level;

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // 将窗口放置在屏幕中央
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == End){
            this.dispose();
            ModeSelectionFrame newGame=new ModeSelectionFrame();
            newGame.setVisible(true);
        }
        if(e.getSource() == New){
            this.dispose();
            if(presentLevel==1){
                try {
                    ChessGameFrame secondFrame=new ChessGameFrame(1100, 810,8);
                    GameController controller=new GameController(secondFrame.getChessboardComponent(), new Chessboard(0));
                    controller.setStep(8);
                    secondFrame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(presentLevel==2){
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