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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.engine.boards.Board;
import chess.engine.common.Square;
import chess.engine.requests.CastlingReq;
import chess.engine.requests.PromoteReq;
import chess.engine.requests.Request;
import chess.exceptions.CheckException;
import chess.exceptions.CheckMateException;
import chess.exceptions.CheckNotResolvedException;
import chess.exceptions.IllegalMovementException;
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
          try {
            Position startPos = new Position(TilesPanel.getSelectedTile().x, TilesPanel.getSelectedTile().y);
            Position finalPos = new Position(tile.x, tile.y);

            if (Board.getInstance().move(startPos, finalPos)) {
              Sound.playCapture();
            } else {
              Sound.playMove();
            }

          } catch (IllegalMovementException em) {
            Sound.playIllegal();
            TilesPanel.setSelectedTile(null).blinking();
          } catch (CheckNotResolvedException cr) {
            Sound.playIllegal();
            TilesPanel.setSelectedTile(null);
            TilesPanel.getTiles()[cr.getKing().getPosition().x][cr.getKing().getPosition().y].blinking();
          } catch (CheckMateException cm) {
            Sound.playEnd();
            JOptionPane.showMessageDialog(null, "CHECKMATE!");
          } catch (CastlingReq cr) {
            Sound.playCastle();
          } catch (PromoteReq pr) {
            Sound.playPromote();
          } catch (CheckException ce) {
            Sound.playCheck();
          } catch (Request r) {

          } finally {
            TablePanel.refresh();
            TilesPanel.setSelectedTile(null);
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
        // System.out.println("entra");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        Tile tile = (Tile) e.getSource();
        tile.repaint();
        // System.out.println("sale");
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
    }, 250, TimeUnit.MILLISECONDS);
  }

  private void enforceAspectRatio() {
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }
}
