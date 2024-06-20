package chess.engine.common;

public class TimerConstraints {
  protected int seconds, increment, mode;

  public TimerConstraints(int seconds, int increment, int mode) {
    this.seconds = seconds;
    this.increment = increment;
    this.mode = mode;
  }
}
