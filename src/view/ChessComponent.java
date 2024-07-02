package view;


import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
public class ChessComponent extends JComponent {

    private boolean selected;

    private ChessPiece chessPiece;
    private int dx,dy,x,y;

    public ChessComponent(int size, ChessPiece chessPiece,int x,int y) {
        dx=x;dy=y;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
        this.chessPiece = chessPiece;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setChessPiece(ChessPiece piece){
        this.chessPiece=piece;
    }

    public ChessPiece getChessPiece(){
        return this.chessPiece;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (this.chessPiece != null && this.chessPiece.getImage() != null) {
            g2.drawImage(this.chessPiece.getImage().getImage(), getWidth() / 4 + dx, getHeight() * 5 / 8 + dy-20,45,45, this);
        }

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.gray);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
