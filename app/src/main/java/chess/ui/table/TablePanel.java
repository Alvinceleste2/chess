package chess.ui.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import chess.utils.Assets;

public class TablePanel extends JPanel {
  private static final double ASPECT_RATIO = 1.0 / 1.0;
  private static BufferedImage backgroundImage;

  public TablePanel() {
    try {
      backgroundImage = ImageIO.read(new File(Assets.boardPath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(TilesPanel.getInstance());
    TilesPanel.setTiles();

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // enforceAspectRatio();
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
      this.setSize(expectedWidth, height);
    } else {
      this.setSize(width, expectedHeight);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
      this.setBackground(Color.RED);
    }
  }

  public static void refresh() {
    TilesPanel.refresh();
  }
}
