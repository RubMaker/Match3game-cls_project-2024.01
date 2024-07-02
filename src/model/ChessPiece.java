package model;


import javax.swing.*;
import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private ImageIcon image;

    public ChessPiece(String name) {
        this.name = name;
        this.image = Constant.imageIconMap.get(name);
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage(){return image;}



}
