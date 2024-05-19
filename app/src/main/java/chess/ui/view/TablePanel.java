package chess.ui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TablePanel extends JPanel {
  private static final double ASPECT_RATIO = 1.0 / 1.0;
  private static BufferedImage backgroundImage;
  private static TilesPanel tilesPanel;

  public TablePanel() {
    try {
      backgroundImage = ImageIO.read(new File("./../assets/ChessSet/Classic/Board/Board-classic2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    tilesPanel = new TilesPanel();
    this.add(Box.createHorizontalGlue());
    this.add(tilesPanel);
    this.add(Box.createHorizontalGlue());

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

  public static void refresh() {
    tilesPanel.refresh();
  }
}
