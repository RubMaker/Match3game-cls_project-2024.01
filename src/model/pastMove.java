package model;
//使用一个三元组存储每一次的操作信息
public class pastMove {
    private int x1,y1,x2,y2;
    public pastMove(int X,int Y,int XX,int YY) {
        x1=X;y1=Y;x2=XX;y2=YY;
    }
    public pastMove() {
        x1=y1=x2=y2=0;
    }
    public void setX1(int X) {x1=X;}
    public void setY1(int Y) {y1=Y;}
    public void setX2(int X) {x2=X;}
    public void setY2(int Y) {y2=Y;}
    public int getX1() {return x1;}
    public int getY1() {return y1;}
    public int getX2() {return x2;}
    public int getY2() {return y2;}

}
