package cs3500.imageprocessing.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

/**
 * Class for Save Command that works for non-ppm files.
 */
public class SaveIMG extends ASave {

  /**
   * Constructor for the more broadly applicable Save Command.
   *
   * @param pathName (The path to where we are storing the file.)
   * @param keyName  (The key under which data is stored.)
   * @param out      (The Appendable to check appendability for testing.)
   */
  public SaveIMG(String pathName, String keyName, Appendable out) {
    super(pathName, keyName, out);
  }

  @Override
  public void run(IModel model) {
    validate(model);
    int pathLength = pathName.length();
    String imgType = pathName.substring(pathLength - 3, pathLength).toLowerCase();
    String jpeg = pathName.substring(pathLength - 4, pathLength).toLowerCase();
    if (imgType.equals("ppm")) {
        savePPM(model);
    } else {
      if (!model.hasImage(keyName)) {
        System.out.print("\nNo file by that name!\n");
        return;
      }

      BufferedImage img = model.generateIMG(keyName, out);


      try {
        ImageIO.write(img, (jpeg.equals("jpeg") ? "jpeg" : imgType), new File(pathName));
      } catch (FileNotFoundException fnf) {
        appendToOut(pathName + " path not valid.");
      } catch (IOException io) {
        System.out.print("Writing to file failed!");
      }
    }
  }

  public void savePPM(IModel model) {
    // needs to be get or Default if your filename isn't in hashmap
    if (!model.hasImage(keyName)) {
      System.out.print("\nNo file by that name!\n");
      return;
    }

    PixelData[][] dataToWrite = model.getImageCopy(keyName);

    appendToOut("P3\n" + dataToWrite[0].length + "\n"
            + dataToWrite.length + "\n" + dataToWrite[0][0].getMaxValue() + "\n");

    for (PixelData[] row : dataToWrite) {
      for (PixelData pixel : row) {
        appendToOut(pixel.toString());
      }
      appendToOut("\n");
    }

    try {
      FileOutputStream fileOut = new FileOutputStream(pathName);
      fileOut.write(out.toString().getBytes());
      fileOut.close();
    } catch (FileNotFoundException fnf) {
      appendToOut(pathName + " path not valid.");
    } catch (IOException io) {
      System.out.print("Writing to file failed!");
    }
  }
}
