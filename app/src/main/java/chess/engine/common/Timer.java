package chess.engine.common;

import java.security.InvalidParameterException;

import chess.engine.boards.Board;

public class Timer implements Runnable {
  private int seconds, elapsed, increment;
  private boolean active;
  private final int mode; // 0 for Fischer; 1 for Bronstein

  public Timer(int seconds, int increment, int mode) {
    if ((seconds < 1) || (mode != 0 && mode != 1 && mode != 2)) {
      throw new InvalidParameterException();
    }

    this.seconds = seconds;
    this.elapsed = 0;
    this.increment = increment;
    this.active = false;
    this.mode = mode;
  }

  @Override
  public void run() {
    while (this.seconds > this.elapsed) {
      if (this.active) {
        this.elapsed++;
        System.err.println(this);
      }

      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        System.err.println(e);
      }
    }

    Board.getInstance().endGame();
  }

  @Override
  public String toString() {
    return String.format("%02d", (this.seconds - this.elapsed) / 60) + ":"
        + String.format("%02d", (this.seconds - this.elapsed) % 60);
  }

  public void stop() {
    this.active = false;

    switch (this.mode) {
      case 0:
        this.elapsed = this.elapsed - increment;
        break;

      case 1:
        this.elapsed = Math.max(this.elapsed - increment, 0);
        break;
    }

    System.err.println(this.active ? "Timer resumed" : "Timer stopped");
  }

  public void resume() {
    this.active = true;
  }
}
