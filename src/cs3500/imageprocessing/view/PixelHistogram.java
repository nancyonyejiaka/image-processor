package cs3500.imageprocessing.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Class for creating the PixelData Histogram.
 */
public class PixelHistogram {
  private final HashMap<Integer, Integer> redData;
  private final HashMap<Integer, Integer> blueData;
  private final HashMap<Integer, Integer> greenData;
  private final HashMap<Integer, Integer> intensityData;

  /**
   * Constructor for the  PixelHistogram object.
   *
   * @param allData The 4 element Array of Data Hashmaps.
   * @throws IllegalArgumentException if the Data passed in is not valid for construction.
   */
  public PixelHistogram(HashMap<Integer, Integer>[] allData) throws IllegalArgumentException {
    if (allData == null || allData.length != 4) {
      throw new IllegalArgumentException("Data array passed in is invalid");
    }
    //Invariant:  allData must be structured according to the order of R,G,B,I
    this.redData = allData[0];
    this.greenData = allData[1];
    this.blueData = allData[2];
    this.intensityData = allData[3];
  }

  /**
   * Draws a Line Graph based on the data passed in to it, with the corresponding color.
   *
   * @param data  The data being used for drawing the graph.
   * @param color The color to draw the graph with.
   * @return A BufferedImage of the Line Graph.
   */
  private BufferedImage drawGraph(HashMap<Integer, Integer> data, String color) {

    // computes the maximum value contained within the data.  Used for scaling Histogram.
    double max = 0.0;
    for (int i = 0; i < 256; i++) {
      if (data.get(i) > max) {
        max = data.get(i);
      }
    }
//    System.out.print("\n\n this is the max of the function: " + max + "\n\n");


    Dimension dimens = new Dimension(255, 384);
    BufferedImage histogramIMG = new BufferedImage(dimens.width, dimens.height,
            BufferedImage.TYPE_INT_RGB);

    // create the basic axes for the Graph.
    Graphics2D g = histogramIMG.createGraphics();
    g.setBackground(Color.WHITE);
    g.fillRect(0, 0, dimens.width, dimens.height);
    g.setColor(Color.BLACK);
    g.drawLine(1, dimens.height - 1, dimens.width - 1, dimens.height - 1);
    g.drawLine(1, 1, 1, dimens.height - 1);

    // What color should the lines be drawn with?
    switch (color) {
      case ("red"):
        g.setColor(Color.RED);
        break;
      case ("green"):
        g.setColor(Color.GREEN);
        break;
      case ("blue"):
        g.setColor(Color.BLUE);
        break;
      case ("intensity"):
        g.setColor(Color.MAGENTA);
        break;
      default:
        System.out.print("Invalid Graph color!");
        break;
    }


    // x value to place point at (j represents item in hashmap we're on
    for (int j = 1, x = (int) (dimens.getWidth() / 255) + 2; j < 256; j++) {
      double ratio1 = data.get(j - 1) / max;
      int y1 = (int) (ratio1 * dimens.height);

      double ratio2 = data.get(j) / max;
      int y2 = (int) (ratio2 * dimens.height);
//      System.out.print("\n starting coordinate: (" + (x - 1) + "," + y1 + ")\n");
//      System.out.print("\n ending coordinate: (" + (x) + "," + y2 + ")\n");

      g.drawLine(x - 1, (dimens.height + 1) - y1, x, (dimens.height + 1) - y2);
      x += (int) (dimens.getWidth() / 255);
    }

    return histogramIMG;
  }


  /**
   * Draw the red Histogram.
   *
   * @return The red Histogram BufferedImage.
   */
  public BufferedImage drawRed() {
    return this.drawGraph(this.redData, "red");
  }

  /**
   * Draw the green Histogram.
   *
   * @return The green Histogram BufferedImage.
   */
  public BufferedImage drawGreen() {
    return this.drawGraph(this.greenData, "green");
  }

  /**
   * Draw the blue Histogram.
   *
   * @return The blue Histogram BufferedImage.
   */
  public BufferedImage drawBlue() {
    return this.drawGraph(this.blueData, "blue");
  }

  /**
   * Draw the intensity Histogram.
   *
   * @return The intensity Histogram BufferedImage.
   */
  public BufferedImage drawIntensity() {
    return this.drawGraph(this.intensityData, "intensity");
  }

  /**
   * Draw each Histogram.
   *
   * @return The compiled Histogram BufferedImage.
   */
  public BufferedImage drawAll() {
    BufferedImage red = this.drawRed();
    BufferedImage green = this.drawGreen();
    BufferedImage blue = this.drawBlue();
    BufferedImage intensity = this.drawIntensity();

    return overlay(overlay(red, green), overlay(blue, intensity));
  }

  /**
   * Overlay two BufferedImages of Histograms on top of each other.
   *
   * @param img1 The first Histogram.
   * @param img2 The second Histogram.
   * @return The BufferedImage of two Histograms overlaid.
   * @throws IllegalArgumentException if the images are incompatible for overlay.
   */
  private BufferedImage overlay(BufferedImage img1, BufferedImage img2)
          throws IllegalArgumentException {
    if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
      throw new IllegalArgumentException("Overlaid images must be the same size.");
    }

    BufferedImage result = new BufferedImage(img1.getWidth(),
            img1.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < img1.getWidth(); i++) {
      for (int j = 0; j < img1.getHeight(); j++) {
        // if both are white, no data is lost by setting the result pixel as white.
        if (Color.WHITE.equals(new Color(img2.getRGB(i, j)))
                || img1.getRGB(i, j) == img2.getRGB(i, j)) {
          result.setRGB(i, j, img1.getRGB(i, j));
          // if JUST img1's Pixel is white, set the value as img2's pixel.
        } else if (Color.WHITE.equals(new Color(img1.getRGB(i, j)))) {
          result.setRGB(i, j, img2.getRGB(i, j));
        }
      }
    }

    return result;
  }
}

