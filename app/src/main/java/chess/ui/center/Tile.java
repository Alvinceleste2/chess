package chess.ui.center;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import chess.engine.GameEngine;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.table.Board;
import chess.table.Square;
import chess.utils.Position;

public class Tile extends JPanel {
  private BufferedImage backgroundImage;
  protected int x, y;
  protected boolean hover, selected, blink, movement;
  public static final Color hoverColor = new Color(128, 128, 128, 250);
  public static final Color blinkingColor = new Color(200, 0, 0, 200);
  public static final Color movementColor = new Color(150, 150, 150, 250);

  public Tile(String imagePath, int x, int y) {
    try {
      backgroundImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setOpaque(false);
    this.x = x;
    this.y = y;
    this.hover = false;
    this.blink = false;
    this.movement = false;

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        enforceAspectRatio();
      }
    });

    this.addMouseMotionListener(new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent arg0) {
      }

      @Override
      public void mouseMoved(MouseEvent arg0) {
      }

    });

    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Tile tile = (Tile) e.getSource();

        if (TilesPanel.getSelectedTile() == null) {
          Square sq = Board.getInstance().getSquare(Position.createPosition(tile.x, tile.y));
          if (sq.isEmpty()) {
            return;
          }

          if (!GameEngine.getInstance().checkTurn(sq.getPiece())) {
            return;
          }

          TilesPanel.setSelectedTile(tile);
          TilesPanel.showMovements(
              Board.getInstance().getPieceAtSquare(Position.createPosition(tile.x, tile.y)).calculateMovements());
          tile.repaint();

        } else if (TilesPanel.getSelectedTile().equals(tile)) {
          TilesPanel.setSelectedTile(null);
          tile.repaint();

        } else {
          try {
            Position startPos = new Position(TilesPanel.getSelectedTile().x, TilesPanel.getSelectedTile().y);
            Position finalPos = new Position(tile.x, tile.y);

            Board.getInstance().getPieceAtSquare(startPos).move(finalPos);
            GameEngine.getInstance().next();

            TablePanel.refresh();
            TilesPanel.setSelectedTile(null);

          } catch (InvalidPositionException ep) {

          } catch (InvalidMovementException em) {
            TilesPanel.setSelectedTile(null).blinking();
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        tile.repaint();
        System.out.println("entra");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        tile.repaint();
        System.out.println("sale");
      }

      @Override
      public void mouseReleased(MouseEvent e) {
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImage != null) {
      if (this.blink) {
        g.setColor(blinkingColor);
        g.fillRect(0, 0, getWidth(), getHeight());

      } else if (this.selected) {
        g.setColor(hoverColor);
        g.fillRect(0, 0, getWidth(), getHeight());
      } else if (this.movement) {
        g.setColor(movementColor);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (Board.getInstance().getSquare(Position.createPosition(this.x, this.y)).isEmpty()) {
          int r = Math.min(getWidth(), getHeight()) / 6;

          g.fillOval(centerX - r, centerY - r, r * 2, r * 2);

        } else {
          int r = (int) (Math.min(getWidth(), getHeight()) / 2.5);

          g.fillOval(centerX - r, centerY - r, r * 2, r * 2);
        }
      }

      g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
  }

  private void blinking() {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // First iteration
    this.blink = true;
    this.revalidate();
    this.repaint();

    executor.schedule(() -> {
      this.blink = false;
      this.revalidate();
      this.repaint();
    }, 250, TimeUnit.MILLISECONDS);
  }

  private void enforceAspectRatio() {
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }
}
