package chess.ui.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import chess.Board;
import chess.exceptions.InvalidPositionException;
import chess.pieces.Piece;
import chess.utils.Position;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TablePanel extends JPanel {
  private BufferedImage backgroundImage;

  public TablePanel() {
    try {
      backgroundImage = ImageIO.read(new File("./../assets/ChessSet/Classic/Board/Board-classic2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setLayout(new BorderLayout());
    this.add(new TilesPanel(), BorderLayout.CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImage != null) {
      int panelWidth = getWidth();
      int panelHeight = getHeight();

      double scaleX = (double) panelWidth / backgroundImage.getWidth();
      double scaleY = (double) panelHeight / backgroundImage.getHeight();

      double scale = Math.max(scaleX, scaleY);

      int scaledWidth = (int) (scale * backgroundImage.getWidth());
      int scaledHeight = (int) (scale * backgroundImage.getHeight());

      int x = (panelWidth - scaledWidth) / 2;
      int y = (panelHeight - scaledHeight) / 2;

      g.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
    }
  }
}

class TilesPanel extends JPanel {
  public TilesPanel() {
    this.setLayout(new GridBagLayout());
    this.setSize(256, 256);
    this.setOpaque(false);
    this.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
    this.setTiles();
  }

  public void setTiles() {
    GridBagConstraints gbc = new GridBagConstraints();
    Board board = Board.getInstance();

    try {
      for (int i = 0; i < Board.maxX; i++) {
        for (int j = 0; j < Board.maxY; j++) {

          gbc.gridx = i;
          gbc.gridy = j;

          Piece piece = board.getPieceAtSquare(new Position(i, j));
          JLabel label = new JLabel();

          if (piece == null) {
            ImageIcon icon = new ImageIcon("./../assets/blank.png");
            label.setIcon(icon);
          } else {
            ImageIcon icon = new ImageIcon(board.getPieceAtSquare(new Position(i, j)).getImagePath());
            label.setIcon(icon);
          }

          this.add(label, gbc);
        }
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }
  }
}
