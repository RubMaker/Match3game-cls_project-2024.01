package controller;

import listener.GameListener;
import model.*;
import utils.AudioPlayer;
import view.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Arrays;
import javax.sound.sampled.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class specialGameController implements GameListener {

    private specialChessboard model;
    private specialChessboardComponent view;
    private Saving process;
    private int score = 0;
    private int Step;
    private int shuffleCount = 0;
    private user User;

    private int level;


    // Record whether there is a selected piece before
    //选择的两个位置
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    public void setUser(user User) {
        this.User = User;
    }

    public user getUser() {
        return this.User;
    }

    public specialGameController(specialChessboardComponent view, specialChessboard model) {
        this.view = view;
        this.model = model;
        shuffleCount=3;

        AudioPlayer.playBgm("lib/audio/bgm.wav");
        //Step=10;
        view.registerController(this);
        //initialize();
        view.initiateChessComponent(model);
        //repaint刷新界面
        view.repaint();
        //初始化保存
        process = new Saving(Constant.CHESSBOARD_ROW_SIZE.getNum(), Constant.CHESSBOARD_COL_SIZE.getNum(), 0, 0, model.getGrid());
        //process.print();
    }

    private void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setStep(int step) {
        this.Step = step;
        if(step==10){
            this.level=1;
        }
        else if(step==8){
            this.level=2;
        }
        else{
            this.level=3;
        }
    }

    //洗牌
    public void onPlayershuffleChess() {
        if(shuffleCount==0) {
            ChessGameFrame.FailShuffle();
        }else {
            shuffleCount--;
            ChessGameFrame.setShufflenum(String.valueOf(shuffleCount));
            model.changePieces();
            view.updateChessComponent(model);
            //repaint刷新界面
            view.repaint();
        }
    }

    public void giveMehint() {
        pastMove hint = findAres.getHint(model.getGrid());
        view.showhint(hint.getX1(),hint.getY1(),hint.getX2(),hint.getY2());
        view.repaint();
        System.out.printf("(%d,%d) and (%d,%d)\n", hint.getX1(), hint.getY1(), hint.getX2(), hint.getY2());
        //加入晃动的动画
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // 用鼠标点击一个无图案的格子
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        //若还未选择两个棋子便交换，进行提示
        if (selectedPoint == null || selectedPoint2 == null) {
            specialChessGameFrame.illegalSwap();
            return;
        }
        if (model.checkChessBoard()) {
            specialChessGameFrame.stillNeedToEliminate();
            ChessComponent component1=(ChessComponent) view.getGridComponentAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getComponent(0);
            ChessComponent component2=(ChessComponent) view.getGridComponentAt(new ChessboardPoint(selectedPoint2.getRow(),selectedPoint2.getCol())).getComponent(0);
            component1.setSelected(false);
            component2.setSelected(false);
            component1.repaint();
            component2.repaint();
            selectedPoint=null;
            selectedPoint2=null;
            return;
        }
        if(model.getGrid()[selectedPoint.getRow()][selectedPoint.getCol()].getPiece().getName().equals("☠") || model.getGrid()[selectedPoint2.getRow()][selectedPoint2.getCol()].getPiece().getName().equals("☠")){
            specialChessGameFrame.barrier();
            ChessComponent component1=(ChessComponent) view.getGridComponentAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getComponent(0);
            ChessComponent component2=(ChessComponent) view.getGridComponentAt(new ChessboardPoint(selectedPoint2.getRow(),selectedPoint2.getCol())).getComponent(0);
            component1.setSelected(false);
            component2.setSelected(false);
            component1.repaint();
            component2.repaint();
            selectedPoint=null;
            selectedPoint2=null;
            return;
        }
        ChessPiece chess1 = new ChessPiece(model.getGrid()[selectedPoint.getRow()][selectedPoint.getCol()].getPiece().getName());
        ChessPiece chess2 = new ChessPiece(model.getGrid()[selectedPoint2.getRow()][selectedPoint2.getCol()].getPiece().getName());
        ChessboardPoint point1 = new ChessboardPoint(selectedPoint.getRow(), selectedPoint.getCol());
        ChessboardPoint point2 = new ChessboardPoint(selectedPoint2.getRow(), selectedPoint2.getCol());
        model.swapChessPiece(point1, point2);
        boolean ifEliminateRow1=false;
        boolean ifEliminateCol1=false;
        boolean ifEliminateRow2=false;
        boolean ifEliminateCol2=false;
        //检查交换位置两棋子是否处于可消去的组合中，若可消去则进行消除，若不可消去，再次对换回到原位置
        if (model.checkOnePosition(point1) || model.checkOnePosition(point2)) {
            if (model.checkLeft(point1) + model.checkRight(point1) >= 4) {
                ifEliminateRow1=true;
            }
            if (model.checkUp(point1) + model.checkDown(point1) >= 4) {
                ifEliminateCol1=true;
            }
            if (model.checkLeft(point2) + model.checkRight(point2) >= 4) {
                ifEliminateRow2=true;
            }
            if (model.checkUp(point2) + model.checkDown(point2) >= 4) {
                ifEliminateCol2=true;
            }

            if (model.checkOnePosition(point1)) {
                if (model.checkLeft(point1) + model.checkRight(point1) >= 2) {
                    score += 10;
                    score += (model.checkLeft(point1) + model.checkRight(point1)) * 10;
                }
                if (model.checkUp(point1) + model.checkDown(point1) >= 2) {
                    score += 10;
                    score += (model.checkUp(point1) + model.checkDown(point1)) * 10;
                }
                model.eliminateOnePosition(point1);
            }
            if (model.checkOnePosition(point2)) {
                if (model.checkLeft(point2) + model.checkRight(point2) >= 2) {
                    score += 10;
                    score += (model.checkLeft(point2) + model.checkRight(point2)) * 10;
                }
                if (model.checkUp(point2) + model.checkDown(point2) >= 2) {
                    score += 10;
                    score += (model.checkUp(point2) + model.checkDown(point2)) * 10;
                }
                model.eliminateOnePosition(point2);
            }
            int blanks1= model.countBlanks();
            if(ifEliminateRow1){
                model.eliminateRow(point1.getRow());
            }
            if(ifEliminateCol1){
                model.eliminateCol(point1.getCol());
            }
            if(ifEliminateRow2){
                model.eliminateRow(point2.getRow());
            }
            if(ifEliminateCol2){
                model.eliminateCol(point2.getCol());
            }
            int blanks2= model.countBlanks();
            score+=(blanks2-blanks1)*10;
            List<CellLocation> pointsToRemove =new ArrayList<>();
            for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                    if(model.getGrid()[i][j].getPiece().getName().equals(" ")){
                        pointsToRemove.add(new CellLocation(i,j));
                        System.out.printf("(%d,%d)",i,j);
                        System.out.println();
                    }
                }
            }
            view.swapTwoChess(selectedPoint.getRow(), selectedPoint.getCol(), selectedPoint2.getRow(), selectedPoint2.getCol(), chess1, chess2,pointsToRemove);
            AudioPlayer.playSound("lib/audio/remove.wav");
            Step--;
            specialChessGameFrame.setScore(String.valueOf(score));
            specialChessGameFrame.setStep(String.valueOf(Step));
            System.out.println();
            selectedPoint = null;
            selectedPoint2 = null;
        } else {
            model.swapChessPiece(point1,point2);
            view.onlySwap(selectedPoint.getRow(), selectedPoint.getCol(), selectedPoint2.getRow(), selectedPoint2.getCol(), chess1, chess2);
            selectedPoint = null;
            selectedPoint2 = null;
        }
    }


    @Override
    public void onPlayerNextStep() {
        //当棋盘上午空格也无可以消去的组合，提示该操作无效
        if (!model.checkChessBoard() && model.countBlanks() == 0) {
            //此处还需添加分数要求和分数小于分数要求的条件
            if (Step == 0 && score<1000) {
                AudioPlayer.playBgm("lib/audio/bgm.wav").stop();
                ChessGameFrame.FailTheGame();
                return;
            }
            else if(Step==0 && score>=1000){
                if(level!=3){
                    AudioPlayer.playBgm("lib/audio/bgm.wav").stop();
                    nextLevelFrame nextLevel=new nextLevelFrame(level);
                    nextLevel.setVisible(true);
                    return;
                }
                else{
                    AudioPlayer.playBgm("lib/audio/bgm.wav").stop();
                    ChessGameFrame.pass();
                    return;
                }
            }
            else {
                ChessGameFrame.uselessAction();
                return;
            }
        }
        //当棋盘上午空格但有可以消去的组合，点击按钮消去存在的组合
        if (model.checkChessBoard() && model.countBlanks() == 0) {
            model.eliminateBoard();
            score += model.countBlanks() * 10;
            specialChessGameFrame.setScore(String.valueOf(score));
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessComponent component = (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i, j)).getComponent(0);
                    component.setChessPiece(model.getGrid()[i][j].getPiece());
                    component.repaint();
                    component.updateUI();
                }
            }
            AudioPlayer.playSound("lib/audio/remove.wav");
            return;
        }
        //先进行下落操作，再进行补全操作
        if (model.checkIfFallDown()) {
            ArrayList<Falldownmodel> fallDownCells = new ArrayList<>();
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                int count=0;
                int barriers=0;
                for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--){
                    if(model.getCell(i,j).getPiece().getName().equals(" ")) {
                        count++;
                        continue;
                    }
                    if(model.getCell(i,j).getPiece().getName().equals("☠")) {
                        count=0;
                        continue;
                    }
                    if(count==0) continue;
                    System.out.printf("%s\n",model.getCell(i,j).getPiece().getName());
                    fallDownCells.add(new Falldownmodel(i,j,count*72,model.getCell(i,j).getPiece().getName()));
                }
            }
            for(Falldownmodel item :fallDownCells) {
                //System.out.println(item.toString());
                view.fallDown(item.getX(),item.getY(),item.getDis(),item.getChess());
            }
            model.fallDown();
        } else {
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--) {
                    if(model.getCell(i,j).getPiece().getName().equals(" ")) {
                        model.fillCell(i,j);
                        view.fallDownforinit(i, j,i*72,model.getCell(i,j).getPiece());
                        System.out.println(i);
                    }
                }
            }
            shuffleInDead();
        }
    }

    // 用鼠标点击一个有棋子的格子
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        //当选中的棋子不为null但是已经被消除是“ ”
        if (component.getChessPiece().getName().equals(" ")) {
            return;
        }
        //在已选中两个棋子的情况下再次敲击新的棋子
        if (selectedPoint2 != null) {
            int distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            int distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            ChessComponent point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            ChessComponent point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            //当再次敲击第一个选中的棋子，取消其选中状态，并将第二个选中的棋子定义为第一个被选中的
            if (distance2point1 == 0 && !point1.getChessPiece().getName().equals(" ")) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            }
            //当再次敲击第二个选中的棋子，取消其选中状态
            else if (distance2point2 == 0 && !point2.getChessPiece().getName().equals(" ")) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            }
            //当再次敲击的棋子与第一个选中的棋子相邻，则将此次选中的的棋子定义为第二个选中的棋子
            else if (distance2point1 == 1 && !point2.getChessPiece().getName().equals(" ")) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            //当再次敲击的棋子与第二个选中的棋子相邻，则将原第二个选中的点定义为第一个选中的点，此次选中的的棋子定义为第二个选中的棋子
            else if (distance2point2 == 1 && !point2.getChessPiece().getName().equals(" ")) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }

        //当前敲击的棋子是第一个被选中的棋子
        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        int distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
        //鼠标第二次敲击的棋子与第一次位置重合
        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }
        //鼠标第二次敲击的棋子与第一次位置相邻
        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            ChessComponent grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }


    public void shuffleInDead(){
        if(!findAres.isDeadend(model.getGrid())){
            model.changePieces();
            view.updateChessComponent(model);
            //repaint刷新界面
            view.repaint();
        }
    }
    //导入之前保存的结果
    public void loadGameFromFile(String path) throws IOException {
        //process.Saved(path);
    }

    public void onHammer(){
        if(selectedPoint==null && selectedPoint2==null){
            specialChessGameFrame.noPoint();
            return;
        }
        if(selectedPoint!=null && selectedPoint2!=null){
            specialChessGameFrame.moreThanOnePoint();
            return;
        }
        if(!model.getGrid()[selectedPoint.getRow()][selectedPoint.getCol()].getPiece().getName().equals("☠")){
            specialChessGameFrame.notBarrier();
            return;
        }
        model.getGrid()[selectedPoint.getRow()][selectedPoint.getCol()].removePiece();
        ChessComponent component = (ChessComponent) view.getGridComponentAt(new ChessboardPoint(selectedPoint.getRow(), selectedPoint.getCol())).getComponent(0);
        component.setChessPiece(new ChessPiece(" "));
        component.setSelected(false);
        component.repaint();
        selectedPoint=null;

    }

}
