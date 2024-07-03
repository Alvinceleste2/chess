package chess.engine.boards;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chess.engine.common.GameStatus;
import chess.engine.common.Movement;
import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.common.Timer;
import chess.engine.common.TimerConstraints;
import chess.engine.pieces.Bishop;
import chess.engine.pieces.King;
import chess.engine.pieces.Knight;
import chess.engine.pieces.Pawn;
import chess.engine.pieces.Rook;
import chess.exceptions.InvalidThemeException;
import chess.ui.info.MovementHistoryPanel;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class ChaturajiBoard extends Board {
  public ChaturajiBoard() {
    super();
    this.maxX = 8;
    this.maxY = 8;
    this.squares = new Square[maxX][maxY];

    for (int i = 0; i < this.maxX; i++) {
      for (int j = 0; j < this.maxY; j++) {
        this.squares[i][j] = new Square(null);
      }
    }

    try {
      Assets.buildPaths("chaturaji");
    } catch (InvalidThemeException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void init(TimerConstraints tc) {
    // Creation of the players and their kings
    this.initPlayers(tc);
    this.initPieces();
  }

  private void initPlayers(TimerConstraints tc) {
    // We create the Kings
    King redKing = new King(Color.RED);
    King blueKing = new King(Color.BLUE);
    King yellowKing = new King(Color.YELLOW);
    King greenKing = new King(Color.GREEN);

    this.players.add(new Player(Color.RED, redKing, new Timer(tc)));
    this.players.add(new Player(Color.BLUE, blueKing, new Timer(tc)));
    this.players.add(new Player(Color.YELLOW, yellowKing, new Timer(tc)));
    this.players.add(new Player(Color.GREEN, greenKing, new Timer(tc)));

    this.turn = this.players.get(0);

    this.players.get(0).getTimer().resume();
    Thread redThread = new Thread(this.players.get(0).getTimer());
    redThread.start();
    Thread blueThread = new Thread(this.players.get(1).getTimer());
    blueThread.start();
    Thread yellowThread = new Thread(this.players.get(2).getTimer());
    yellowThread.start();
    Thread greenThread = new Thread(this.players.get(3).getTimer());
    greenThread.start();
  }

  private void initPieces() {
    // We initialize the pieces map
    for (Player p : this.players) {
      this.pieces.put(p.getColor(), new ArrayList<>());
    }

    // Red pieces
    for (int i = 0; i < 4; i++) {
      this.addPiece(new Pawn(Color.RED), new Position(i, 1));
    }

    this.addPiece(new Rook(Color.RED, false), new Position(0, 0));
    this.addPiece(new Knight(Color.RED), new Position(1, 0));
    this.addPiece(new Bishop(Color.RED), new Position(2, 0));
    this.addPiece(this.players.get(0).getKing(), new Position(3, 0));

    // Blue pieces
    for (int i = 4; i < maxY; i++) {
      this.addPiece(new Pawn(Color.BLUE), new Position(1, i));
    }

    this.addPiece(new Rook(Color.BLUE, false), new Position(0, 7));
    this.addPiece(new Knight(Color.BLUE), new Position(0, 6));
    this.addPiece(new Bishop(Color.BLUE), new Position(0, 5));
    this.addPiece(this.players.get(1).getKing(), new Position(0, 4));

    // Yellow pieces
    for (int i = 4; i < maxX; i++) {
      this.addPiece(new Pawn(Color.YELLOW), new Position(i, 6));
    }

    this.addPiece(new Rook(Color.YELLOW, false), new Position(7, 7));
    this.addPiece(new Knight(Color.YELLOW), new Position(6, 7));
    this.addPiece(new Bishop(Color.YELLOW), new Position(5, 7));
    this.addPiece(this.players.get(2).getKing(), new Position(4, 7));

    // Green pieces
    for (int i = 0; i < 4; i++) {
      this.addPiece(new Pawn(Color.GREEN), new Position(6, i));
    }

    this.addPiece(new Rook(Color.GREEN, false), new Position(7, 0));
    this.addPiece(new Knight(Color.GREEN), new Position(7, 1));
    this.addPiece(new Bishop(Color.GREEN), new Position(7, 2));
    this.addPiece(this.players.get(3).getKing(), new Position(7, 3));
  }

  @Override
  public GameStatus move(Position start, Position end) {
    // Initial checks
    if (this.status == GameStatus.ENDED) {
      return GameStatus.ENDED;
    }

    if (this.getSquare(start).isEmpty() || !this.isInTurn(this.getPieceAtSquare(start))) {
      return GameStatus.ILLEGAL;
    }

    GameStatus ret = this.getPieceAtSquare(start).isValidMove(end);

    if (ret == GameStatus.ILLEGAL) {
      return GameStatus.ILLEGAL;
    }

    switch (ret) {
      case GameStatus.NORMAL:
        this.performMove(start, end);
        break;

      case GameStatus.CAPTURE:
        this.performMove(start, end);
        break;

      default:
        break;
    }

    this.performNext();

    this.movements
        .add(new Movement(this.getPieceAtSquare(end), start, end, Collections.singletonList(GameStatus.NORMAL)));
    MovementHistoryPanel.refreshMovements();

    return ret;
  }

  private void performMove(Position start, Position end) {
    this.getSquare(end).setPiece(this.getPieceAtSquare(start));
    this.getSquare(start).setEmpty();
    this.getPieceAtSquare(end).constraintsRefresh();
  }

  private void performNext() {
    this.next();
  }

  @Override
  public List<Position> getLegalMovements(Position position) {
    return this.getPieceAtSquare(position).moveSet();
  }
}
