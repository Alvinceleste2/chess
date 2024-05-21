package chess.ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import chess.exceptions.InvalidPositionException;
import chess.table.Board;
import chess.table.pieces.Piece;
import chess.utils.Position;

public class TilesPanel extends JPanel {
  private static Tile selectedTile = null;

  public TilesPanel() {
    this.setLayout(new GridBagLayout());
    this.setOpaque(false);
    this.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // TODO enforceAspectRatio
        // enforceAspectRatio();
      }
    });

    this.setTiles();
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
            tile = new Tile("./../assets/blank.png", i, j);
          } else {
            tile = new Tile(board.getPieceAtSquare(new Position(i, j)).getImagePath(), i, j);
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

  public static Tile getSelectedTile() {
    return selectedTile;
  }

  public static void setSelectedTile(Tile t) {
    selectedTile = t;
  }
}
