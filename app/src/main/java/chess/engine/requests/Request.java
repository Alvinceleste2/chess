package chess.engine.requests;

public abstract class Request extends Exception {
  public Request(String string) {
    super(string);
  }
}
