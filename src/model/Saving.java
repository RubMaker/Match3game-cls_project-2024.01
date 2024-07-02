package model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//可以添加障碍物功能，如果形状不规则就用障碍物补成规则的，switch改一下
public class Saving {
    private int row;
    private int col;
    private List<Integer> curScore,moveCount;
    private List<pastMove> pastMoveList;
    private ArrayList<Picture> Group;
    static private Cell[][] Grid;
    private int getId(int x,int y) {return x*Constant.CHESSBOARD_COL_SIZE.getNum()+y;}
    private void gridTogroup(Cell[][] grid) {
        Picture Pi = new Picture();
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                switch (grid[i][j].getPiece().getName()) {
                    case "■":
                        Pi.setPointById(getId(i,j),1);break;
                    case "○":
                        Pi.setPointById(getId(i,j),3);break;
                    case "△":
                        Pi.setPointById(getId(i,j),2);break;
                    case "◆":
                        Pi.setPointById(getId(i,j),4);break;
                    case " ":
                        Pi.setPointById(getId(i,j),0);break;
                    default:
                        Pi.setPointById(getId(i,j),5);break;
                }
            }
        Group.add(Pi);
    }
    private Cell[][] groupTogrid(Picture Pi) {
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                switch (Pi.getPointById(getId(i,j))) {
                    case 5:
                        Grid[i][j].setPiece(new ChessPiece("■"));
                        break;
                    case 3:
                        Grid[i][j].setPiece(new ChessPiece("○"));
                        break;
                    case 2:
                        Grid[i][j].setPiece(new ChessPiece("△"));
                        break;
                    case 4:
                        Grid[i][j].setPiece(new ChessPiece("◆"));
                        break;
                    default:
                        Grid[i][j].setPiece(new ChessPiece(" "));
                }
            }
        return Grid;
    }
    public Saving(int row,int col,int Score,int Count,Cell[][] grid) {
        this.row=row;
        this.col=col;
        curScore=new ArrayList<>();
        moveCount =new ArrayList<>();
        this.curScore.add(Score);
        this.moveCount.add(Count);
        pastMoveList= new ArrayList<>();
        Group = new ArrayList<>();
        gridTogroup(grid);
        Grid=new Cell[row][col];
        System.out.println(Score);
        System.out.println(Count);
    }
    public int getScore() {return curScore.get(curScore.size()-1);}
    public int getCount() {return moveCount.get(moveCount.size()-1);}
    public Picture getGroup() {return Group.get(Group.size()-1);}
    public void set(Cell[][] grid,int x1,int y1,int x2,int y2,int Score,int Count) {
        int opt=0;//左是1，上是2，右是3，下是4
        gridTogroup(grid);
        pastMoveList.add(new pastMove(x1,y1,x2,y2));
        curScore.add(Score);
        moveCount.add(Count);
        System.out.println("Score: "+Score);
        System.out.println("Score: "+Count);
    }
    //撤销上一步
    public int reSetsore() {
        int x=curScore.get(curScore.size()-1);
        curScore.remove(curScore.size()-1);
        return x;
    }
    public int reSetcount() {
        int x=moveCount.get(moveCount.size()-1);
        moveCount.remove(moveCount.size()-1);
        return x;
    }
    public Cell[][] reSetgroup() {
        Group.remove(Group.size()-1);
        pastMoveList.remove(pastMoveList.size()-1);
        return groupTogrid(Group.get(Group.size()-1));
    }
    //将文件保存到特定位置，重启从特定位置获取文件。如何获取如何存储，要学。（从文件里输入输出，那构造函数就不能这么写了）
    //有一个问题，就是要输出各个操作的具体步骤吗
    private void cout(String filename,int level) throws IOException{
        File outfile = new File(filename);
        PrintWriter out = new PrintWriter(outfile, "UTF-8");
        Picture Pi=Group.get(Group.size()-1);
        //暂时用64代替，以后要改
        for(int i=0;i<64;i++)
            out.println(Pi.getPointById(i));
        //System.out.println();
        System.out.println(curScore.get(curScore.size()-1));
        System.out.println(curScore.size());
        out.println(curScore.get(curScore.size()-1));
        out.println(moveCount.get(moveCount.size()-1));
        out.println(level);
        System.out.println("The data has been saved!");
        // 释放资源
        out.close();
    }

    public void Saved(String path,int level) throws IOException{
        cout(path,level);
    }
    public void print() {
        for(int i=0;i<64;i++)
            System.out.printf("%d ",Group.get(0).getPointById(i));
    }
}
