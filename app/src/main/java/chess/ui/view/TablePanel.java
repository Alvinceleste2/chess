package chess.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

public class TablePanel extends JPanel {
  private static final double ASPECT_RATIO = 1.0 / 1.0;
  private BufferedImage backgroundImage;
  private TilesPanel tilesPanel;

  public TablePanel() {
    try {
      backgroundImage = ImageIO.read(new File("./../assets/ChessSet/Classic/Board/Board-classic2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setLayout(new BorderLayout());
    this.tilesPanel = new TilesPanel();
    this.add(tilesPanel, BorderLayout.CENTER);

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        enforceAspectRatio();
      }
    });
  }

  private void enforceAspectRatio() {
    int width = getWidth();
    int height = getHeight();

    // Calculate the expected height based on the width and aspect ratio
    int expectedHeight = (int) (width / ASPECT_RATIO);
    int expectedWidth = (int) (height / ASPECT_RATIO);

    // If the calculated height is different from the actual height, set the new
    // height

    if (expectedWidth < expectedHeight) {
      setSize(expectedWidth, height);
    } else {
      setSize(width, expectedHeight);
    }
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
      this.setBackground(Color.RED);
    }
  }

  public void refresh() {
    this.tilesPanel.refresh();
  }
}

class TilesPanel extends JPanel {

  public TilesPanel() {
    this.setLayout(new GridBagLayout());
    this.setOpaque(false);
    this.setTiles();

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        enforceAspectRatio();
      }
    });
  }

  public void setTiles() {
    GridBagConstraints gbc = new GridBagConstraints();
    Board board = Board.getInstance();

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    try {
      for (int i = 0; i < Board.maxX; i++) {
        for (int j = 0; j < Board.maxY; j++) {

          gbc.gridx = i;
          gbc.gridy = j;

          Piece piece = board.getPieceAtSquare(new Position(i, j));
          Tile tile;

          if (piece == null) {
            tile = new Tile("./../assets/blank.png");
          } else {
            tile = new Tile(board.getPieceAtSquare(new Position(i, j)).getImagePath());
          }

          this.add(tile, gbc);
        }
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }
  };

  private void enforceAspectRatio() {
    this.setSize(this.getParent().getWidth(), this.getParent().getHeight());
    this.setBorder(
        BorderFactory.createEmptyBorder((int) (getWidth() / 18), (int) (getWidth() / 18), (int) (getWidth() / 18),
            (int) (getWidth() / 18)));
  }

  public void refresh() {
    this.removeAll();
    this.setTiles();
    this.revalidate();
    this.repaint();
  }
}

class Tile extends JPanel {
  private BufferedImage backgroundImage;

  public Tile(String imagePath) {
    try {
      backgroundImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setOpaque(false);

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        enforceAspectRatio();
      }
    });
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
      this.enforceAspectRatio();
    }
  }

  private void enforceAspectRatio() {
    System.out.println(this.getParent());
    System.out.println(this);
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }
}
