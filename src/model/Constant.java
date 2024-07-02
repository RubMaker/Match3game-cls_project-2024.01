package model;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {
    CHESSBOARD_ROW_SIZE(8), CHESSBOARD_COL_SIZE(8);

    private final int num;

    Constant(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    static Map<String, ImageIcon> imageIconMap = new HashMap<String, ImageIcon>() {{
        put("■", new ImageIcon("lib\\picture\\p1.png"));   // 替换为正方形
        put("○", new ImageIcon("lib\\picture\\p2.png"));   // 替换为圆形
        put("△", new ImageIcon("lib\\picture\\p3.png")); // 替换为三角形
        put("◆", new ImageIcon("lib\\picture\\p4.png"));  // 替换为菱形
        put(" ", new ImageIcon("lib\\picture\\p5.png"));                          // 空格保持不变
        put("☠", new ImageIcon("lib\\picture\\p6.png"));
    }};


}
