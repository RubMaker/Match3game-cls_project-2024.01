package view;


import controller.GameController;
import controller.specialGameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

public class specialChessboardComponent extends JComponent {
    private CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private int CHESS_SIZE=72;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private specialGameController gameController;

    public specialGameController getGameController() {return gameController;}
    public specialChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        //所有面板加起来的大小，CHESS_SIZE是72
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(specialChessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    //System.out.printf("%s ",chessPiece.getName());
                    //gridComponents[i][j].removeAll();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece,0,0));
                }
            }//System.out.println();
        }
    }
    //洗牌
    public void updateChessComponent(specialChessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the update checkerboard
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].removeAll();
                    gridComponents[i][j].repaint();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece,0,0));
                    gridComponents[i][j].updateUI();
                }
            }
        }
    }


    public void initiateGridComponents() {

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(specialGameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }
    public void fallDownforinit(int x,int y,int dis,ChessPiece chess) {
        //将目标格子移动的x,y
        //清空xy
        gridComponents[x][y].removeAll();
        gridComponents[x][y].setLocation(y*72,0);
        gridComponents[x][y].add(new ChessComponent(CHESS_SIZE, chess,0,0));
        gridComponents[x][y].repaint();
        gridComponents[x][y].updateUI();
        if(dis==0) return;
        Runnable updateAComponent = new Runnable() {
            public void run() {
                if(chess==null) return;
                int xx=0,yy=0,dx=0,dy=0;
                xx=8;
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int num=(dis-40)/8;
                for(int i=1;i<=num;i++) {
                    dx+=xx;dy+=yy;
                    gridComponents[x][y].setLocation(y*72+dy,dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread= new Thread(updateAComponent);
        thread.start();
    }

    public void fallDown(int x,int y,int dis,ChessPiece chess) {
        int X=x+dis/72;
        int Y=y;
        //将目标格子移动的x,y
        //清空xy
        gridComponents[x][y].removeAll();
        gridComponents[x][y].add(new ChessComponent(CHESS_SIZE, new ChessPiece(" "),0,0));
        gridComponents[x][y].repaint();
        gridComponents[x][y].updateUI();
        gridComponents[X][Y].removeAll();
        gridComponents[X][Y].setLocation(y*72,x*72);
        //System.out.println(chess.getName());
        gridComponents[X][Y].add(new ChessComponent(CHESS_SIZE,chess,0,0));
        gridComponents[X][Y].repaint();
        gridComponents[X][Y].updateUI();
        Runnable updateAComponent = new Runnable() {
            public void run() {
                if(chess==null) return;
                int xx=0,yy=0,dx=0,dy=0;
                xx=8;
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[X][Y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[X][Y].repaint();
                    gridComponents[X][Y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[X][Y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[X][Y].repaint();
                    gridComponents[X][Y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int num=(dis-40)/8;
                for(int i=1;i<=num;i++) {
                    dx+=xx;dy+=yy;
                    gridComponents[X][Y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[X][Y].repaint();
                    gridComponents[X][Y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[X][Y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[X][Y].repaint();
                    gridComponents[X][Y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[X][Y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[X][Y].repaint();
                    gridComponents[X][Y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread= new Thread(updateAComponent);
        thread.start();
    }
    public void move(int x,int y,int op,ChessPiece chess,ChessPiece newChess,List<CellLocation> cells) {
        Runnable updateAComponent = new Runnable() {
            public void run() {
                if(chess==null) return;
                int xx=0,yy=0,dx=0,dy=0;
                switch(op) {
                    case 1:
                        xx=8;break;
                    case -1:
                        xx=-8;break;
                    case 2:
                        yy=8;break;
                    default:
                        yy=-8;
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx*2;dy+=yy*2;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gridComponents[x][y].removeAll();
                gridComponents[x][y].setLocation(y*72,x*72);
                gridComponents[x][y].add(new ChessComponent(CHESS_SIZE, newChess,0,0));
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();

                for(CellLocation item:cells) {
                    gridComponents[item.getX()][item.getY()].removeAll();
                    gridComponents[item.getX()][item.getY()].add(new ChessComponent(CHESS_SIZE, new ChessPiece(" "),item.getX(),item.getY()));
                    gridComponents[item.getX()][item.getY()].repaint();
                    gridComponents[item.getX()][item.getY()].updateUI();
                }
            }
        };
        Thread thread= new Thread(updateAComponent);
        thread.start();
    }
    public void move(int x,int y,int op,ChessPiece chess,ChessPiece newChess) {
        Runnable updateAComponent = new Runnable() {
            public void run() {
                if(chess==null) return;
                int xx=0,yy=0,dx=0,dy=0;
                switch(op) {
                    case 1:
                        xx=8;break;
                    case -1:
                        xx=-8;break;
                    case 2:
                        yy=8;break;
                    default:
                        yy=-8;
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx*2;dy+=yy*2;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gridComponents[x][y].removeAll();
                gridComponents[x][y].setLocation(y*72,x*72);
                gridComponents[x][y].add(new ChessComponent(CHESS_SIZE, newChess,0,0));
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
            }
        };
        Thread thread= new Thread(updateAComponent);
        thread.start();
    }
    public void reverseMove(int x,int y,int X,int Y,int dis,int op,ChessPiece chess) {
        Runnable updateAComponent = new Runnable() {
            public void run() {
                if(chess==null) return;
                int xx=0,yy=0,dx=0,dy=0;
                switch(op) {
                    case 1:
                        xx=8;break;
                    case -1:
                        xx=-8;break;
                    case 2:
                        yy=8;break;
                    default:
                        yy=-8;
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx*2;dy+=yy*2;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(y*72+dy,x*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //反向运动
                dx=dy=0;
                xx=-xx;yy=-yy;
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx*2;dy+=yy*2;
                gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dx+=xx;dy+=yy;
                gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                gridComponents[x][y].repaint();
                gridComponents[x][y].updateUI();
                try{
                    //System.out.println("新线程来了！！！");
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=1;i<=3;i++) {
                    dx+=xx/2;dy+=yy/2;
                    gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=1;i<=4;i++) {
                    dx+=xx/4;dy+=yy/4;
                    gridComponents[x][y].setLocation(Y*72+dy,X*72+dx);
                    gridComponents[x][y].repaint();
                    gridComponents[x][y].updateUI();
                    try{
                        //System.out.println("新线程来了！！！");
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread= new Thread(updateAComponent);
        thread.start();
    }
    //交换+删除操作
    public void swapTwoChess(int x,int y,int xx,int yy,ChessPiece chess,ChessPiece chess2,List<CellLocation> removeCell) {
        int opt=0;
        if(xx>x) opt=1;
        if(xx<x) opt=-1;
        if(yy>y) opt=2;
        if(yy<y) opt=-2;
        //System.out.println(opt);
        System.out.printf("%d %d %d %d %d \n",x,y,xx,yy,opt);
        move(x,y,opt, chess,chess2);
        move(xx,yy,-opt,chess2,chess,removeCell);
    }
    //仅仅是交换
    public void onlySwap(int x,int y,int xx,int yy,ChessPiece chess,ChessPiece chess2) {
        int opt=0;
        if(xx>x) opt=1;
        if(xx<x) opt=-1;
        if(yy>y) opt=2;
        if(yy<y) opt=-2;
        gridComponents[x][y].removeAll();
        gridComponents[x][y].setLocation(y*72,x*72);
        gridComponents[x][y].add(new ChessComponent(CHESS_SIZE, chess,0,0));
        gridComponents[x][y].repaint();
        gridComponents[x][y].updateUI();
        gridComponents[xx][yy].removeAll();
        gridComponents[xx][yy].setLocation(y*72,x*72);
        gridComponents[xx][yy].add(new ChessComponent(CHESS_SIZE, chess2,0,0));
        gridComponents[xx][yy].repaint();
        gridComponents[xx][yy].updateUI();
        reverseMove(x,y,xx,yy,72,opt,chess);
        reverseMove(xx,yy,x,y,72,-opt,chess2);
    }
    public void showHint(int x,int y) {
        Runnable showhint = new Runnable() {
            public void run() {
                for(int i=1;i<=5;i++) {
                    gridComponents[x][y].setVisible(false);
                    try{
                        System.out.println("新线程来了1！！！");
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("why not show update?");
                    gridComponents[x][y].setVisible(true);
                    try{
                        System.out.println("新线程来了2！！！");
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread= new Thread(showhint);
        thread.start();
    }
    public void showhint(int x,int y,int xx,int yy) {
        showHint(x,y);
        showHint(xx,yy);
    }
    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess(){
        gameController.onPlayerSwapChess();
    }
    public void hintChess() {
        gameController.giveMehint();
    }

    public void nextStep(){
        gameController.onPlayerNextStep();
    }
    //洗牌，并更新洗牌次数
    public void shuffleChess() {
        gameController.onPlayershuffleChess();
    }
    //交换两个相邻的格子
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void hammer(){
        gameController.onHammer();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }


}
