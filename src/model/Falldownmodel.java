package model;

public class Falldownmodel {
    private int x;
    private int y;
    private int dis;
    private ChessPiece chess;
    public Falldownmodel(int x,int y,int dis,String name) {
        this.x=x;
        this.y=y;
        this.dis=dis;
        this.chess = new ChessPiece(name);
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public int getDis() {return dis;}
    public ChessPiece getChess() {return chess;}
    public String toString() {
        return x+" "+y+" "+dis+" "+chess.getName();
    }
}
