package model;

import view.ChessComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.Arrays;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;


    public Chessboard(int randomSeed) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces(randomSeed);
    }

    public Chessboard(Cell[][] grid) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                this.grid[i][j]=new Cell();
                this.grid[i][j].setPiece(new ChessPiece(grid[i][j].getPiece().getName()));
            }
        }
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces(int randomSeed) {
        Random random = new Random(randomSeed);

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"■", "○", "△", "◆"})));
            }
        }
        while(checkChessBoard()) {
            eliminateBoard();
            fallDown();
            fillBlank();
        }

    }
    //洗牌
    public void changePieces() {
        Random random = new Random(0);
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"■", "○", "△", "◆"})));
            }
        }
        while(checkChessBoard()) {
            eliminateBoard();
            fallDown();
            fillBlank();
        }

    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        ChessPiece p1 = getChessPieceAt(point1);
        ChessPiece p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }


    public Cell[][] getGrid() {
        return grid;
    }

    //checkOnePosition方法用来检查一个点否可消除;checkChessBoard方法用来检查整张棋盘是否还可以消除；checkLeft Right Up Down用于获取该点在各个方向上可消去的棋子数量
    public boolean checkOnePosition(ChessboardPoint point) {
        int sameOnRow = checkLeft(point) + checkRight(point);
        int sameOmCol = checkUp(point) + checkDown(point);
        return (sameOmCol >= 2 || sameOnRow >= 2);
    }

    public boolean checkChessBoard() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (checkOnePosition(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int checkLeft(ChessboardPoint point) {
        int row = point.getRow();
        int col = point.getCol();
        int left = 0;
        ChessPiece piece = new ChessPiece(grid[row][col].getPiece().getName());
        //判断左边与该位置图形相同的数量
        for (int i = col - 1; i >= 0; i--) {
            if (grid[row][i].getPiece().getName().equals(piece.getName())) {
                left += 1;
            } else {
                return left;
            }
        }
        return left;
    }

    public int checkRight(ChessboardPoint point) {
        int row = point.getRow();
        int col = point.getCol();
        int right = 0;
        ChessPiece piece = new ChessPiece(grid[row][col].getPiece().getName());
        //判断右边
        for (int i = col + 1; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            if (grid[row][i].getPiece().getName().equals(piece.getName())) {
                right += 1;
            } else {
                break;
            }
        }
        return right;
    }

    public int checkUp(ChessboardPoint point) {
        int row = point.getRow();
        int col = point.getCol();
        int up = 0;
        ChessPiece piece = new ChessPiece(grid[row][col].getPiece().getName());
        //判断上面
        for (int i = row - 1; i >=0; i--) {
            if ( grid[i][col].getPiece().getName().equals(piece.getName())) {
                up += 1;
            } else {
                break;
            }
        }
        return up;
    }

    public int checkDown(ChessboardPoint point) {
        int row = point.getRow();
        int col = point.getCol();
        int down = 0;
        ChessPiece piece = new ChessPiece(grid[row][col].getPiece().getName());
        //判断左边与该位置图形相同的数量
        //判断下面
        for (int i = row + 1; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            if (grid[i][col].getPiece().getName().equals(piece.getName())) {
                down += 1;
            } else {
                break;
            }
        }
        return down;
    }

    //eliminateOnePosition方法用于对一个可消除的点进行消除,eliminateBoard方法用于对整个棋盘上可消除的组合进行消除
    public void eliminateOnePosition(ChessboardPoint point) {
        int row = point.getRow();
        int col = point.getCol();
        int left = checkLeft(point);
        int right = checkRight(point);
        int up = checkUp(point);
        int down = checkDown(point);
        removeChessPiece(point);
        if(right+left>=2){
            for (int i = 1; i <= left; i++) {
                ChessboardPoint pointToBeEliminated = new ChessboardPoint(row, col - i);
                removeChessPiece(pointToBeEliminated);
            }
            for (int i = 1; i <= right; i++) {
                ChessboardPoint pointToBeEliminated = new ChessboardPoint(row, col + i);
                removeChessPiece(pointToBeEliminated);
            }
        }
        if(up+down>=2){
            for (int i = 1; i <= up; i++) {
                ChessboardPoint pointToBeEliminated = new ChessboardPoint(row - i, col);
                removeChessPiece(pointToBeEliminated);
            }
            for (int i = 1; i <= down; i++) {
                ChessboardPoint pointToBeEliminated = new ChessboardPoint(row + i, col);
                removeChessPiece(pointToBeEliminated);
            }
        }
    }

    public void eliminateBoard() {
        List<CellLocation> pointsToRemove=new ArrayList<>();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (this.checkOnePosition(point)) {
                    int row = point.getRow();
                    int col = point.getCol();
                    int left = this.checkLeft(point);
                    int right = this.checkRight(point);
                    int up = this.checkUp(point);
                    int down = this.checkDown(point);
                    if(left+right>=2){
                        for (int p = 1; p <= left; p++) {
                            pointsToRemove.add(new CellLocation(row,col-p));
                        }
                        for (int p = 1; p <= right; p++) {
                            pointsToRemove.add(new CellLocation(row,col+p));
                        }
                    }
                    if(up+down>=2){
                        for (int p = 1; p <= up; p++) {
                            pointsToRemove.add(new CellLocation(row-p,col));
                        }
                        for (int p = 1; p <= down; p++) {
                            pointsToRemove.add(new CellLocation(row+p,col));
                        }
                    }
                }
            }
        }
        for(int i=0;i<pointsToRemove.size();i++){
            removeChessPiece(new ChessboardPoint(pointsToRemove.get(i).getX(),pointsToRemove.get(i).getY()));
        }
    }

    //eliminateRow用于在special模式中消除一行的全部图形
    public void eliminateRow(int index){
        for(int i=0;i<Constant.CHESSBOARD_COL_SIZE.getNum();i++){
            grid[index][i].setPiece(new ChessPiece(" "));
        }
    }

    //eliminateCol用于在special模式中消除一列的全部图形
    public void eliminateCol(int index){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            grid[i][index].setPiece(new ChessPiece(" "));
        }
    }
    public Cell getCell(int x,int y) {
        return grid[x][y];
    }

    //fallDown方法用于在eliminate之后，棋子落下填充空格
    public void fallDown() {
        //创建tempGrids数组并对其进行初始化，将其中所有的piece均设为blank
        Cell[][] tempGrids = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                tempGrids[i][j]=new Cell();
                tempGrids[i][j].setPiece(new ChessPiece(" "));
            }
        }
        //将grid数组中不为blank的部分保存到tempGrids中，最后再吧tempGrids中复制到grid中
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            int n=Constant.CHESSBOARD_ROW_SIZE.getNum() - 1;
            for (int j = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; j >=0; j--) {
                if (!grid[j][i].getPiece().getName().equals(" ")) {
                    tempGrids[n][i] = grid[j][i];
                    n -= 1;
                }
            }
            for (int p = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; p>= 0; p--) {
                grid[p][i] = tempGrids[p][i];
            }
        }
    }

    //checkIfFallDown方法用于判断此时是否需要进行fallDown操作
    public boolean checkIfFallDown(){
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = Constant.CHESSBOARD_ROW_SIZE.getNum()-2; j >=0; j--) {
                if(grid[j+1][i].getPiece().getName().equals(" ") && !grid[j][i].getPiece().getName().equals(" ")){
                    return true;
                }
            }
        }
        return false;
    }

    //fillBlank方法用于在fallDown之后填充null的grid
    public void fillBlank() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece().getName().equals(" ")) {
                    grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"■", "○", "△", "◆"})));
                }
            }
        }
    }
    public void fillCell(int i,int j) {
        grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"■", "○", "△", "◆"})));
    }

    //countBlanks方法用于对目前棋盘上的空格数进行计数
    public int countBlanks() {
        int n=0;
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].getPiece().getName().equals(" ")){
                    n+=1;
                }
            }
        }
        return n;
    }

    //countFalldown方法用于对于该棋子下方可下落的空格数量进行计数
    public int countFalldown(int x,int y){
        int counter=0;
        for(int i=y+1;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            if(grid[x][i].getPiece().getName().equals(" ")){
                counter+=1;
            }
        }
        return counter;
    }

}