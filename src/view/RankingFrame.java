package view;
;
import network.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RankingFrame extends JFrame implements ActionListener {
    private JButton button;
    private String[] args= new String[5];
    public RankingFrame() throws IOException {
        File infile = new File("data/ranking.txt/");
        Scanner in = new Scanner(infile, "UTF-8");
        String data=new String(in.nextLine());
        String[] args=data.split(" ");
        setLayout(null);
        ImageIcon icon=new ImageIcon("lib/picture/menu.jpg");
        JLabel label=new JLabel(icon);
        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);
        //System.out.println(args.length);
        setTitle("Match-3 Game");
        //不知道为什么，但是不加一个面板就会有问题
        JPanel Panel = new JPanel();
        button = new JButton("Return");
        button.setLocation(1000,10);
        button.setSize(100,50);
        button.addActionListener(this);
        button.setOpaque(false);
        Font newFont = new Font("Arial", Font.ITALIC, 15);
        Font NewFont = new Font("Arial", Font.ITALIC, 30);
        button.setFont(newFont);
        add(button);
        for (int i = 0; i < 5; i++) {
            JLabel userLabel = new JLabel(args[i*2]+ ": "+args[i*2+1]);
            userLabel.setLocation(500,100*(i+1));
            userLabel.setSize(200,100);
            userLabel.setFont(NewFont);
            userLabel.setOpaque(false);
            add(userLabel);
        }
        //必须设置为透明的。否则看不到图片
        Panel.setOpaque(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1150, 820);
        setLocationRelativeTo(null);

        this.add(Panel);
        Panel.setSize(1200,820);
        Panel.setSize(0,0);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            this.dispose();
            ModeSelectionFrame firstFrame=new ModeSelectionFrame();
            firstFrame.setVisible(true);
        }
    }
}