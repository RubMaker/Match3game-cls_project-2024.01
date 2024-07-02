package model;

public class Picture {
    private int[] point= new int[Constant.CHESSBOARD_COL_SIZE.getNum()*Constant.CHESSBOARD_ROW_SIZE.getNum()];
    private int last;
    public Picture(){
        last=0;
    }
    public int getPointById(int id) {return point[id];}
    public void setPointById(int id,int val) {point[id]=val;}
}
