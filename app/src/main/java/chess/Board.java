package chess;

import java.util.ArrayList;
import java.util.List;

import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import chess.utils.Color;
import chess.utils.Position;

public class Board {
  public static int maxX = 8, maxY = 8;
  private static Board boardInstance = null;

  private Square[][] squares;
  private List<Piece> pieces;

  private Board() {
    this.pieces = new ArrayList<>();
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

  public void boardInit() throws InvalidPositionToAddPieceException, InvalidPositionException {

    // We add the pawns
    for (int i = 0; i < maxX; i++) {
      boardInstance.addPiece(new Pawn(Color.WHITE), new Position(i, 1));
    }
    for (int i = 0; i < maxY; i++) {
      boardInstance.addPiece(new Pawn(Color.BLACK), new Position(i, 6));
    }

    // And now the rest of Pieces

    // White
    boardInstance.addPiece(new Rook(Color.WHITE), new Position(0, 0));
    boardInstance.addPiece(new Rook(Color.WHITE), new Position(7, 0));

    boardInstance.addPiece(new Knight(Color.WHITE), new Position(1, 0));
    boardInstance.addPiece(new Knight(Color.WHITE), new Position(6, 0));

    boardInstance.addPiece(new Bishop(Color.WHITE), new Position(2, 0));
    boardInstance.addPiece(new Bishop(Color.WHITE), new Position(5, 0));

    boardInstance.addPiece(new Queen(Color.WHITE), new Position(3, 0));
    boardInstance.addPiece(new King(Color.WHITE), new Position(4, 0));

    // Black
    boardInstance.addPiece(new Rook(Color.BLACK), new Position(0, 7));
    boardInstance.addPiece(new Rook(Color.BLACK), new Position(7, 7));

    boardInstance.addPiece(new Knight(Color.BLACK), new Position(1, 7));
    boardInstance.addPiece(new Knight(Color.BLACK), new Position(6, 7));

    boardInstance.addPiece(new Bishop(Color.BLACK), new Position(2, 7));
    boardInstance.addPiece(new Bishop(Color.BLACK), new Position(5, 7));

    boardInstance.addPiece(new Queen(Color.BLACK), new Position(3, 7));
    boardInstance.addPiece(new King(Color.BLACK), new Position(4, 7));
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
