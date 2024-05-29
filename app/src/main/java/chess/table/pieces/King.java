package chess.table.pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.exceptions.InvalidRealocationException;
import chess.table.Board;
import chess.utils.Color;
import chess.utils.Position;

public class King extends Piece {

  private boolean firstMoved;

  public King(Color color) {
    super(color);

    this.firstMoved = false;

    switch (color) {
      case WHITE:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-white-classic/King.png";
        break;
      default:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-black-classic/King.png";
        break;
    }
  }

  @Override
  public void move(Position position) throws InvalidMovementException {
    if (!this.checkCastling(position)) {
      System.err.println("Unable to castling");
      super.move(position);

      if (!this.firstMoved) {
        this.firstMoved = true;
      }
    } else {
      System.err.println("Conditions to castling reached");
      try {
        this.castling(position);
        try {
          File soundFile = new File("./../assets/audio/castle.wav");
          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
          Clip clip = AudioSystem.getClip();
          clip.open(audioIn);
          clip.start(); // Start playing the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
          ea.printStackTrace();
        }
      } catch (InvalidPositionToAddPieceException e) {
        System.err.println("Unable to castling");
      }
    }
  }

  private boolean checkCastling(Position position) {
    // We check castling conditions
    Piece piece = Board.getInstance().getPieceAtSquare(position);

    if (!(piece instanceof Rook) || piece.color != this.color) {
      return false;
    }

    Rook r = (Rook) piece;
    double posX = Math.ceil((this.position.x + r.position.x) / 2);

    if (r.getFirstMoved() || this.firstMoved
        || this.isDangerousPosition(Position.createPosition((int) posX, this.position.y))) {
      return false;
    }

    for (int i = Math.min(this.position.x, r.position.x) + 1; i < Math.max(this.position.x, r.position.x); i++) {
      if (!Board.getInstance().getSquare(Position.createPosition(i, this.position.y)).isEmpty()) {
        return false;
      }
    }

    if (this.isInCheck()) {
      return false;
    }

    return true;
  }

  public void castling(Position position) throws InvalidPositionToAddPieceException {
    Board board = Board.getInstance();
    double posXK = Math.ceil(((double) this.position.x + (double) position.x) / 2);
    double posXR = (this.position.x > board.getPieceAtSquare(position).position.x) ? posXK + 1 : posXK - 1;

    System.out.println(posXK);

    try {
      board.realocatePiece(this.position, new Position((int) posXK, this.position.y));
      board.realocatePiece(position, new Position((int) posXR, this.position.y));
    } catch (InvalidPositionException | InvalidRealocationException e) {

    }
  }

  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();

    list.addAll(this.getMovements());
    list.removeAll(this.getDanger());
    list.addAll(this.getCastling());

    return list;
  }

  public List<Position> getMovements() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();

    // First, we include every posible movement

    // U
    try {
      Position posU = new Position(this.position.x, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posU);

      if (piece == null || piece.color != this.color) {
        list.add(posU);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UR
    try {
      Position posUR = new Position(this.position.x + 1, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posUR);

      if (piece == null || piece.color != this.color) {
        list.add(posUR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // R
    try {
      Position posR = new Position(this.position.x + 1, this.position.y);
      Piece piece = board.getPieceAtSquare(posR);

      if (piece == null || piece.color != this.color) {
        list.add(posR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DR
    try {
      Position posDR = new Position(this.position.x + 1, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posDR);

      if (piece == null || piece.color != this.color) {
        list.add(posDR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // D
    try {
      Position posD = new Position(this.position.x, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posD);

      if (piece == null || piece.color != this.color) {
        list.add(posD);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DL
    try {
      Position posDL = new Position(this.position.x - 1, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posDL);

      if (piece == null || piece.color != this.color) {
        list.add(posDL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // L
    try {
      Position posL = new Position(this.position.x - 1, this.position.y);
      Piece piece = board.getPieceAtSquare(posL);

      if (piece == null || piece.color != this.color) {
        list.add(posL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UL
    try {
      Position posUL = new Position(this.position.x - 1, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posUL);

      if (piece == null || piece.color != this.color) {
        list.add(posUL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }

  public List<Position> getDanger() {
    // The King can only move where there is no danger
    List<Position> posToErase = new ArrayList<>();

    for (Piece p : Board.getInstance().getPieces()) {
      if (p.color != this.color) {
        if (p instanceof King) {
          King k = (King) p;
          posToErase.addAll(k.getMovements());

        } else if (p instanceof Pawn) {
          Pawn pa = (Pawn) p;
          posToErase.addAll(pa.getAttack());

        } else {
          posToErase.addAll(p.calculateMovements());
        }
      }
    }

    return posToErase;
  }

  public List<Position> getCastling() {
    List<Position> list = new ArrayList<>();

    // We check if castling is possible

    Position posI = Position.createPosition(0, this.position.y),
        posD = Position.createPosition(Board.maxX - 1, this.position.y);

    if (checkCastling(posI)) {
      list.add(posI);
    }

    if (checkCastling(posD)) {
      list.add(posD);
    }

    return list;
  }

  public boolean isDangerousPosition(Position position) {
    return this.getDanger().contains(position);
  }

  public boolean isInCheck() {
    for (Position p : this.getDanger()) {
      if (p.equals(this.position)) {
        return true;
      }
    }

    return false;
  }
}
