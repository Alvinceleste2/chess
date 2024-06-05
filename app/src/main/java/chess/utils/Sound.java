package chess.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Sound {

  public static void playMove() {
    try {
      File soundFile = new File("./../assets/audio/move-self.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }
  }

  public static void playCapture() {
    try {
      File soundFile = new File("./../assets/audio/capture.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }
  }

  public static void playIllegal() {
    try {
      File soundFile = new File("./../assets/audio/illegal.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }
  }

  public static void playCastle() {
    try {
      File soundFile = new File("./../assets/audio/castle.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }
  }

  public static void playEnd() {
    try {
      File soundFile = new File("./../assets/audio/game-end.wav");
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start(); // Start playing the sound
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ea) {
      ea.printStackTrace();
    }
  }
}
