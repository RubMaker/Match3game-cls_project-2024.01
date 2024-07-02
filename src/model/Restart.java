package model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.BufferedReader;
public class Restart {
    private int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
    private int col=Constant.CHESSBOARD_COL_SIZE.getNum();
    private int[] Pi =new int[row*col];
    private int curScore;
    private int moveCount;
    private int level;
    public Restart() {
        curScore=0;moveCount=0;level=0;
    }
    public int getCurScore() {return curScore;}
    public int getMoveCount() {return moveCount;}
    public int getLevel() {return level;}
    public boolean check1(String filename) throws IOException{
        File file = new File(filename);
        String filePath = "data/data.json"; // Specify the path to your file
        File fi = new File(filePath);
        if (fi.exists()) return true;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // 读取文件的第一行
            if (line != null && line.startsWith("{") && line.endsWith("}")) {
                return true;
            } else if(line != null && line.startsWith("[") && line.endsWith("]")){
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean check2(String filename) {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filename))){
            lineNumberReader.skip(Long.MAX_VALUE);
            int lineNumber = lineNumberReader.getLineNumber();
            if((lineNumber + 1 )==68) return false;
        } catch (IOException e) {
            return true;
        }
        return true;
    }
    public boolean check3(String filename) throws IOException {
        File infile = new File(filename);
        Scanner in = new Scanner(infile, "UTF-8");

        for(int i=0;i<row*col;i++) {
            int num=in.nextInt();
            if(num<0) return true;
            if(num>4) return true;
        }
        in.close();
        return false;
    }

    public void cin(String filename) throws IOException {
        File infile = new File(filename);
        Scanner in = new Scanner(infile, "UTF-8");

        for(int i=0;i<row*col;i++) {
            Pi[i]=in.nextInt();
            //System.out.printf("%d: %d\n",i,Pi[i]);
        }
        //System.out.println(row*col);
        curScore=in.nextInt();
        moveCount=in.nextInt();
        level=in.nextInt();
        // 释放资源
        in.close();
    }
    public Cell[][] groupTogrid() {
        Cell[][] Grid= new Cell[row][col];
        System.out.println(row*col);
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                Grid[i][j] =new Cell();
                switch (Pi[i*col+j]) {
                    case 1:
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
                    case 0:
                        Grid[i][j].setPiece(new ChessPiece(" "));
                        break;
                    default:

                }
                //System.out.printf("%s ",Grid[i][j].getPiece().getName());
            }
        return Grid;
    }
}
