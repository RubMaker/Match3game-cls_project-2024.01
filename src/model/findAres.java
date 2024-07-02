package model;

public class findAres {
    //从下往上
    //再竖着搜，搜到两个一起的联通块然后开始搜两端的周围六个联通块，先搜两边的，再搜同一个行、列的
    //另一种解法，所以在三消游戏中检查游戏是否是死局是非常有必要的。检测游戏死局的思路 主要是当游戏到底生成稳定状态的时候，给游戏中的每个块都跟其右边的块和下面的块进行交换。交换之后然后判定是否存在有三个颜色相同的情况，如果有就说明还有可以消除的块，如果不存在三个颜色相同的块，则说明游戏已经到达死局状态，不能再继续游戏了。具体代码如下：首先是给检测是否有三个颜色相同的块封装成一个函数。当满足某个条件的时候返回一个值。当调用这个函数的时候就返回这个值。

    static private int row=Constant.CHESSBOARD_ROW_SIZE.getNum(), col=Constant.CHESSBOARD_COL_SIZE.getNum();
    static private pastMove hint=new pastMove();
    public findAres(int row,int col) {
        this.row=row;
        this.col=col;
    }
    static public boolean isInrange(int x,int y) {
        if(x<0) return false;
        if(y<0) return false;
        if(x>=row) return false;
        if(y>=col) return false;
        return true;
    }
    static public boolean isRow(Cell[][] gird,int x,int y) {
        if(y>=col-2) return false;
        if(gird[x][y].getPiece().getName()!=gird[x][y+1].getPiece().getName()) return false;
        if(isInrange(x-1,y-1)&&gird[x][y].getPiece().getName()==gird[x-1][y-1].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y-1);
            hint.setX2(x-1);
            hint.setY2(y-1);
            return true;
        }
        if(isInrange(x-1,y+2)&&gird[x][y].getPiece().getName()==gird[x-1][y+2].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y+2);
            hint.setX2(x-1);
            hint.setY2(y+2);
            return true;
        }
        if(isInrange(x+1,y-1)&&gird[x][y].getPiece().getName()==gird[x+1][y-1].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y-1);
            hint.setX2(x+1);
            hint.setY2(y-1);
            return true;
        }
        if(isInrange(x+1,y+2)&&gird[x][y].getPiece().getName()==gird[x+1][y+2].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y+2);
            hint.setX2(x+1);
            hint.setY2(y+2);
            return true;
        }
        if(isInrange(x,y-2)&&gird[x][y].getPiece().getName()==gird[x][y-2].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y-1);
            hint.setX2(x);
            hint.setY2(y-2);
            return true;
        }
        if(isInrange(x,y+3)&&gird[x][y].getPiece().getName()==gird[x][y+3].getPiece().getName()){
            hint.setX1(x);
            hint.setY1(y+2);
            hint.setX2(x);
            hint.setY2(y+3);
            return true;
        }
        return false;
    }
    static public boolean isCol(Cell[][] gird,int x,int y) {
        if(x>=row-2) return false;
        if(gird[x][y].getPiece().getName()!=gird[x+1][y].getPiece().getName()) return false;
        if(isInrange(x-1,y-1)&&gird[x][y].getPiece().getName()==gird[x-1][y-1].getPiece().getName()){
            hint.setX1(x-1);
            hint.setY1(y);
            hint.setX2(x-1);
            hint.setY2(y-1);
            return true;
        }
        if(isInrange(x-1,y+1)&&gird[x][y].getPiece().getName()==gird[x-1][y+1].getPiece().getName()){
            hint.setX1(x-1);
            hint.setY1(y);
            hint.setX2(x-1);
            hint.setY2(y+1);
            return true;
        }
        if(isInrange(x+2,y-1)&&gird[x][y].getPiece().getName()==gird[x+2][y-1].getPiece().getName()){
            hint.setX1(x+2);
            hint.setY1(y);
            hint.setX2(x+2);
            hint.setY2(y-1);
            return true;
        }
        if(isInrange(x+2,y+1)&&gird[x][y].getPiece().getName()==gird[x+2][y+1].getPiece().getName()){
            hint.setX1(x+2);
            hint.setY1(y);
            hint.setX2(x+2);
            hint.setY2(y+1);
            return true;
        }
        if(isInrange(x-2,y)&&gird[x][y].getPiece().getName()==gird[x-2][y].getPiece().getName()){
            hint.setX1(x-1);
            hint.setY1(y);
            hint.setX2(x-2);
            hint.setY2(y);
            return true;
        }
        if(isInrange(x+3,y)&&gird[x][y].getPiece().getName()==gird[x+3][y].getPiece().getName()){
            hint.setX1(x+2);
            hint.setY1(y);
            hint.setX2(x+3);
            hint.setY2(y);
            return true;
        }
        return false;

    }

    //图像每次变更都要看,就是判断是否陷入了死局
    static public boolean isDeadend(Cell[][] gird) {
        boolean fl=false;
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                if(isRow(gird,i,j)) {
                    fl=true;break;
                }
                if(isCol(gird,i,j)) {
                    fl=true;break;
                }
            }
        return fl;
    }
    static public pastMove getHint(Cell[][] gird) {
        boolean fl=false;
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                if(isRow(gird,i,j)) {
                    fl=true;break;
                }
                if(isCol(gird,i,j)) {
                    fl=true;break;
                }
            }
        return hint;
    }
}
