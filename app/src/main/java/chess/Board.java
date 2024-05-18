package chess;

import java.util.List;

import chess.exceptions.InvalidPositionToAddPieceException;
import chess.pieces.Piece;
import chess.utils.Position;

public class Board {
  public static int maxX = 7, maxY = 7;
  private static Board boardInstance = null;

  private Square[][] squares;
  private List<Piece> pieces;

  private Board() {
    this.squares = new Square[maxX][maxY];

    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        this.squares[i][j] = new Square(null);
      }
    }
  }

  public static Board getInstance() {
    if (boardInstance == null) {
      boardInstance = new Board();
    }
    return boardInstance;
  }

  public void boardInit() {

  }

  public Piece getPieceAtSquare(Position position) {
    return this.squares[position.x][position.y].getPiece();
  }

  public Square getSquare(Position position) {
    return this.squares[position.x][position.y];
  }

  public void addPiece(Piece piece, Position position) throws InvalidPositionToAddPieceException {
    if (this.getPieceAtSquare(position) != null) {
      throw new InvalidPositionToAddPieceException();
    }

    this.squares[position.x][position.y].changePiece(piece);
    piece.setPosition(position);
    this.pieces.add(piece);
  }

  public List<Piece> getPieces() {
    return this.pieces;
  }

  public void delPiece(Piece piece) {
    this.pieces.remove(piece);
  }
}
