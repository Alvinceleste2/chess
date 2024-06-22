package chess.engine.common;

import java.util.List;

import chess.engine.boards.Board;
import chess.engine.pieces.Piece;
import chess.utils.Position;

public class Movement {
  private Piece piece;
  private Position startPos, endPos;
  private List<GameStatus> status;

  public Movement(Piece piece, Position startPos, Position endPos, List<GameStatus> status) {
    this.piece = piece;
    this.startPos = startPos;
    this.endPos = endPos;
    this.status = status;
  }

  public String toString() {
    int num = Board.getInstance().getMovements().indexOf(this) + 1;

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(num);
    stringBuilder.append(". ");

    if (this.status.contains(GameStatus.CASTLING)) {
      stringBuilder.append((Board.getInstance().getTurn().getKing().getPosition().x == 2) ? "O-O-O" : "O-O");
      return String.valueOf(stringBuilder);
    }

    stringBuilder.append(this.piece.toString());

    if (this.status.contains(GameStatus.CAPTURE)) {
      stringBuilder.append("x");
    }

    stringBuilder.append(this.endPos.toString());

    if (this.status.contains(GameStatus.CHECK)) {
      stringBuilder.append("+");
      return String.valueOf(stringBuilder);
    }

    if (this.status.contains(GameStatus.CHECKMATE)) {
      stringBuilder.append("#");
    }

    return String.valueOf(stringBuilder);
  }
}
