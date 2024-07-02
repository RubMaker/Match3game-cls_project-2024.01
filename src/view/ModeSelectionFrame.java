package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.GameController;
import controller.autoGameController;
import model.Chessboard;
import model.Restart;
import model.user;
import network.Ranked;

public class ModeSelectionFrame extends JFrame implements ActionListener{
    private JButton manualModeButton;
    private JButton autoModeButton;

    private JButton rankingButton;

    public ModeSelectionFrame() {
        super("Mode Selection");
        Ranked ranking = new Ranked();
        setLayout(null);

        ImageIcon icon=new ImageIcon("lib/picture/modeSelection.png");
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,-50,icon.getIconWidth(),icon.getIconHeight());

        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);

        // 创建按钮
        manualModeButton = new JButton("Manual Mode");
        autoModeButton = new JButton("Auto Mode");
        rankingButton =new JButton("Ranking");
        manualModeButton.setLocation(200,300);
        manualModeButton.setSize(200,100);
        manualModeButton.setBackground(Color.lightGray);
        autoModeButton.setLocation(600,300);
        autoModeButton.setSize(200,100);
        autoModeButton.setBackground(Color.lightGray);
        rankingButton.setSize(200,100);
        rankingButton.setBackground(Color.lightGray);
        rankingButton.setLocation(450,600);
        rankingButton.setBorderPainted(false);
        manualModeButton.addActionListener(this);
        autoModeButton.addActionListener(this);
        rankingButton.addActionListener(this);
        JPanel Panel = new JPanel();

        Font newFont = new Font("Arial", Font.ITALIC, 27);
        manualModeButton.setFont(newFont);
        autoModeButton.setFont(newFont);
        rankingButton.setFont(newFont);

        Panel.setOpaque(false);
        manualModeButton.setOpaque(false);
        autoModeButton.setOpaque(false);
        rankingButton.setOpaque(false);

        manualModeButton.setBorderPainted(false);
        autoModeButton.setBorderPainted(false);


        // 添加按钮到窗口
        add(manualModeButton);
        add(autoModeButton);
        add(rankingButton);


        // 设置窗口属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 810);
        setLocationRelativeTo(null); // 将窗口放置在屏幕中央

        this.add(Panel);
        Panel.setSize(1100,810);
        Panel.setSize(0,0);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == manualModeButton){
            this.dispose();
            Menu secondFrame=new Menu();
            secondFrame.setVisible(true);
        }
        if(e.getSource() == autoModeButton){
            this.dispose();
            try {
                autoChessGameFrame secondFrame=new autoChessGameFrame(1100, 810,10);
                autoGameController controller=new autoGameController(secondFrame.getChessboardComponent(), new Chessboard(0));
                controller.setStep(10);
                secondFrame.setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == rankingButton) {
            this.dispose();
            try {
                //System.out.println("wgwgwgwgw");
                RankingFrame rankingFrame=new RankingFrame();
                rankingFrame.setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
