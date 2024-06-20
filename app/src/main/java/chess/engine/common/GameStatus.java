package chess.engine.common;

public enum GameStatus {
  NORMAL,
  CAPTURE,
  CASTLING,
  PROMOTION,
  ILLEGAL,
  CHECK,
  CHECKMATE,
  DRAW,
  CHECK_NOT_RESOLVED,
  NOT_STARTED,
  ENDED;
}
