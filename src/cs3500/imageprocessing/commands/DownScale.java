package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

/**
 * Class representing the DownScale functionality.
 */
public class DownScale extends ACommand {
  private final int newHeight;
  private final int newWidth;

  /**
   * Constructor that takes in relevant information for downscaling.
   * @param newWidth The new width of the image being generated.
   * @param newHeight The new height of the image being generated.
   * @param keyName The key name under which the old image is stored.
   * @param newName The new name under which to store the created image.
   * @throws IllegalArgumentException if the new dimensions are invalid.
   */
  public DownScale(int newWidth, int newHeight, String keyName, String newName)
          throws IllegalArgumentException {
    super(keyName, newName);

    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("New Dimensions cannot be 0 or below.");
    }

    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }


  @Override
  public void run(IModel model) throws IllegalArgumentException {
    PixelData[][] currentIMG = model.getImageCopy(keyName);
    PixelData[][] result = new PixelData[this.newHeight][this.newWidth];

    if (this.newHeight > currentIMG.length || this.newWidth > currentIMG[0].length) {
      throw new IllegalArgumentException("Cannot increase size of images!");
    }

    int oldToNewHeight = currentIMG.length / this.newHeight;
    int oldToNewWidth = currentIMG[0].length / this.newWidth;

    System.out.println("height ratio is " + oldToNewHeight);
    System.out.println("width ratio is " + oldToNewWidth);

    for (int row = 0; row < result.length; row++) {
      for (int col = 0; col < result[0].length; col++) {

        double red = 0;
        double green = 0;
        double blue = 0;

        for (int r = 0; r < oldToNewHeight; r++) {
          for (int c = 0; c < oldToNewWidth; c++) {
            red += currentIMG[r + (row * oldToNewHeight)][c + (col * oldToNewWidth)].getR();
            green += currentIMG[r + (row * oldToNewHeight)][c + (col * oldToNewWidth)].getG();
            blue += currentIMG[r + (row * oldToNewHeight)][c + (col * oldToNewWidth)].getB();
          }
        }

        red = red / (oldToNewHeight * oldToNewWidth);
        green = green / (oldToNewHeight * oldToNewWidth);
        blue = blue / (oldToNewHeight * oldToNewWidth);

        result[row][col] = new PixelData(255, (int)red, (int)green, (int)blue);

      }
    }
    model.addImage(newName, result);
  }
}
