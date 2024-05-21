package chess.table;

import java.util.HashSet;
import java.util.Set;

import chess.exceptions.InvalidPieceSwitchException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.exceptions.InvalidRealocationException;
import chess.table.pieces.Bishop;
import chess.table.pieces.King;
import chess.table.pieces.Knight;
import chess.table.pieces.Pawn;
import chess.table.pieces.Piece;
import chess.table.pieces.Queen;
import chess.table.pieces.Rook;
import chess.utils.Color;
import chess.utils.Position;

public class Board {
  public static int maxX = 8, maxY = 8;
  private static Board boardInstance = null;

  private Square[][] squares;
  private Set<Piece> pieces;

  private Board() {
    this.pieces = new HashSet<>();
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
    if (!this.getSquare(position).isEmpty()) {
      throw new InvalidPositionToAddPieceException();
    }

    this.squares[position.x][position.y].setPiece(piece);
    piece.setPosition(position);
    this.pieces.add(piece);
  }

  public void switchPieces(Piece newPiece, Position position) throws InvalidPieceSwitchException {
    if (newPiece == null || this.getSquare(position).isEmpty()) {
      throw new InvalidPieceSwitchException();
    }

    this.getPieceAtSquare(position).die();

    try {
      this.addPiece(newPiece, position);
    } catch (InvalidPositionToAddPieceException e) {

    }
  }

  public void realocatePiece(Position start, Position end) throws InvalidRealocationException {
    if (this.getSquare(start).isEmpty()) {
      throw new InvalidRealocationException();
    }

    Piece p = this.getPieceAtSquare(start);

    try {
      this.addPiece(p, end);
      this.getSquare(start).setEmpty();
    } catch (InvalidPositionToAddPieceException e) {
      throw new InvalidRealocationException();
    }
  }

  public Set<Piece> getPieces() {
    return this.pieces;
  }

  public void delPiece(Piece piece) {
    this.getSquare(piece.getPosition()).setEmpty();
    this.pieces.remove(piece);
  }
}
