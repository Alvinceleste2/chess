package chess.ui.view;

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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.table.Board;
import chess.utils.Position;

public class Tile extends JPanel {
  private BufferedImage backgroundImage;
  private int x, y;

  public Tile(String imagePath, int x, int y) {
    try {
      backgroundImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setOpaque(false);
    this.x = x;
    this.y = y;

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // TODO enforceAspectRatio
        // enforceAspectRatio();
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
          tile.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
          TilesPanel.setSelectedTile(tile);

        } else if (TilesPanel.getSelectedTile().equals(tile)) {
          tile.setBorder(new EmptyBorder(0, 0, 0, 0));
          TilesPanel.setSelectedTile(null);

        } else {
          try {
            Position startPos = new Position(TilesPanel.getSelectedTile().x, TilesPanel.getSelectedTile().y);
            Position finalPos = new Position(tile.x, tile.y);

            System.out.println(startPos);
            System.out.println(finalPos);

            Board.getInstance().getPieceAtSquare(startPos).move(finalPos);
            System.out.println("Movimiento efectuado");
            TablePanel.refresh();
            TilesPanel.setSelectedTile(null);

          } catch (InvalidPositionException ep) {

          } catch (InvalidMovementException em) {
            JOptionPane.showMessageDialog(null, "Movimiento no válido", "Movimiento inválido",
                JOptionPane.ERROR_MESSAGE);
            TilesPanel.setSelectedTile(null);
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
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
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }
}
