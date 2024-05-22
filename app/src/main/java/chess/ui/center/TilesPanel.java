package chess.ui.center;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import chess.exceptions.InvalidPositionException;
import chess.table.Board;
import chess.table.pieces.Piece;
import chess.utils.Position;

public class TilesPanel extends JPanel {
  private static TilesPanel tilesPanel;
  private static Tile selectedTile = null;
  private static List<Tile> selectedTileMovements;
  private static Tile[][] tiles;

  private TilesPanel() {
    this.setLayout(new GridBagLayout());
    this.setOpaque(false);
    this.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

    tiles = new Tile[Board.maxX][Board.maxY];
    selectedTileMovements = new ArrayList<>();

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // TODO enforceAspectRatio
        // enforceAspectRatio();
      }
    });
  }

  public static TilesPanel getInstance() {
    if (tilesPanel == null) {
      tilesPanel = new TilesPanel();
    }

    return tilesPanel;
  }

  public static void setTiles() {
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

          tilesPanel.add(tile, gbc);
        }
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }
  };

  @Override
  public void add(Component comp, Object constraints) {
    Tile tile = (Tile) comp;
    tiles[tile.x][tile.y] = (Tile) comp;
    super.add(comp, constraints);
  }

  private void enforceAspectRatio() {
    this.setSize(this.getParent().getWidth(), this.getParent().getHeight());
    this.setBorder(
        BorderFactory.createEmptyBorder((int) (getWidth() / 18), (int) (getWidth() / 18), (int) (getWidth() / 18),
            (int) (getWidth() / 18)));
  }

  public static void refresh() {
    tilesPanel.removeAll();
    setTiles();
    tilesPanel.revalidate();
    tilesPanel.repaint();
  }

  public static Tile getSelectedTile() {
    return selectedTile;
  }

  public static Tile setSelectedTile(Tile t) {
    if (selectedTile != null) {
      selectedTile.selected = false;
    }

    Tile aux = selectedTile;

    selectedTile = t;

    if (t != null) {
      t.selected = true;
    }

    for (Tile tile : selectedTileMovements) {
      tile.movement = false;
      tile.revalidate();
      tile.repaint();
    }

    selectedTileMovements.clear();

    return aux;
  }

  public static Tile[][] getTiles() {
    return tiles;
  }

  public static void showMovements(List<Position> positions) {
    for (Position p : positions) {
      tiles[p.x][p.y].movement = true;
      selectedTileMovements.add(tiles[p.x][p.y]);
      tiles[p.x][p.y].revalidate();
      tiles[p.x][p.y].repaint();
    }
  }
}
