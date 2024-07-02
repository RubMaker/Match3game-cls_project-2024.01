package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 * 这相当于 Cell 类，但此类只关心如何在 ChessboardComponent 上绘制 Cells
 */

public class CellComponent extends JPanel /*implements ActionListener*/ {
    private Color background;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        setOpaque(false);
        this.background = background;
    }

}
