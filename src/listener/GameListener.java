package listener;
//listener是监听器，实现model和view的连接
import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);

    public void onPlayerSwapChess();

    public void onPlayerNextStep();

}
