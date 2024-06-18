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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.engine.boards.Board;
import chess.engine.common.Square;
import chess.utils.Assets;
import chess.utils.Position;
import chess.utils.Sound;

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
        // enforceAspectRatio();
      }
    });

    this.addMouseMotionListener(new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent e) {
      }

      @Override
      public void mouseMoved(MouseEvent e) {
      }

    });

    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Tile tile = (Tile) e.getSource();

        if (TilesPanel.getSelectedTile() == null) {
          Square sq = Board.getInstance().getSquare(new Position(tile.x, tile.y));
          if (sq.isEmpty()) {
            return;
          }

          if (!Board.getInstance().isInTurn(sq.getPiece())) {
            return;
          }

          TilesPanel.setSelectedTile(tile);
          TilesPanel
              .showMovements(Board.getInstance().getMovements(new Position(tile.x, tile.y)));
          tile.repaint();

        } else if (TilesPanel.getSelectedTile().equals(tile)) {
          TilesPanel.setSelectedTile(null);
          tile.repaint();

        } else {
          Position startPos = new Position(TilesPanel.getSelectedTile().x, TilesPanel.getSelectedTile().y);
          Position finalPos = new Position(tile.x, tile.y);

          int res = Board.getInstance().move(startPos, finalPos);
          TilesPanel.refresh();

          switch (res) {
            case Board.ILLEGAL:
              Sound.playIllegal();
              TilesPanel.getSelectedTile().blinking();
              break;

            case Board.NORMAL:
              Sound.playMove();
              break;

            case Board.CAPTURE:
              Sound.playCapture();
              break;

            case Board.CASTLING:
              Sound.playCastle();
              break;

            case Board.PROMOTION:
              Sound.playPromote();
              break;

            case Board.CHECK_NOT_RESOLVED:
              Sound.playIllegal();
              TilesPanel.getTiles()[Board.getInstance().getTurn().getKing().getPosition().x][Board.getInstance()
                  .getTurn().getKing()
                  .getPosition().y].blinking();
              break;

            case Board.CHECK:
              Sound.playCheck();
              break;

            case Board.CHECKMATE:
              Sound.playEnd();
              TilesPanel.setSelectedTile(null);
              JOptionPane.showMessageDialog(null, "CHECKMATE!");
              break;
          }

          TilesPanel.setSelectedTile(null);
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseReleased(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        tile.repaint();
        // System.out.println("entra");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        tile.repaint();
        // System.out.println("sale");
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

        if (Board.getInstance().getSquare(new Position(this.x, this.y)).isEmpty()) {
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
    }, 150, TimeUnit.MILLISECONDS);

    executor.schedule(() -> {
      this.blink = true;
      this.revalidate();
      this.repaint();
    }, 300, TimeUnit.MILLISECONDS);

    executor.schedule(() -> {
      this.blink = false;
      this.revalidate();
      this.repaint();
      return;
    }, 450, TimeUnit.MILLISECONDS);
  }

  private void enforceAspectRatio() {
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }

  public void setBackgroundImage() throws IOException {
    if (!Board.getInstance().getSquare(new Position(x, y)).isEmpty()) {
      this.backgroundImage = ImageIO
          .read(new File(Board.getInstance().getPieceAtSquare(new Position(x, y)).getImagePath()));
    } else {
      this.backgroundImage = ImageIO.read(new File(Assets.blankPath));
    }
  }
}
