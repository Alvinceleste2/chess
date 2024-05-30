package chess.table.pieces;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import chess.exceptions.InvalidMovementException;
import chess.table.Board;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public abstract class Piece {
  protected Position position;
  protected boolean dead;
  protected List<Position> movements;
  protected Color color;
  protected String imagePath = Assets.piecesPath;

  public Piece(Color color) {
    this.color = color;
  }

  public void die() {
    this.dead = true;
    Board.getInstance().delPiece(this);
    this.position = null;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return this.position;
  }

  public String getImagePath() {
    return this.imagePath;
  }

  public void move(Position position) throws InvalidMovementException {
    if (!this.calculateMovements().contains(position)) {
      try {
        File soundFile = new File("./../assets/audio/illegal.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start(); // Start playing the sound
      } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
        ea.printStackTrace();
      }
      throw new InvalidMovementException();
    }

    try {
      File soundFile = new File("./../assets/audio/move-self.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }

    Board.getInstance().getSquare(this.position).setEmpty();
    Board.getInstance().getSquare(position).setPiece(this);
    this.position = position;
  }

  public abstract List<Position> calculateMovements();
}
