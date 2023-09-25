package cs3500.imageprocessing.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

/**
 * The load method for non-ppm files, using the built-in using ImageIO methods.
 */
public class LoadIMG extends ALoad {

  /**
   * Constructor a new instance of the load command for file types other than PPMs.
   *
   * @param pathName the path to the file
   * @param keyName  the key to store the data under
   */
  public LoadIMG(String pathName, String keyName) {
    super(pathName, keyName);
  }


  /**
   * Execute this image processing command on the given image source.
   *
   * @param model the image storage unit that images will be retrieved from and stored to
   * @throws IllegalArgumentException if the model is null.
   */
  @Override
  public void run(IModel model) throws IllegalArgumentException {
    BufferedImage img;
    int pathLength = pathName.length();
    String imgType = pathName.substring(pathLength - 3, pathLength).toLowerCase();
    if (imgType.equals("ppm")) {
      try {
        loadPPM(model);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File cannot be found.");
      }
    } else {
      try {
        img = ImageIO.read(new File(pathName));
      } catch (IOException io) {
        throw new IllegalArgumentException("Unable to read file!");
      }


      // resulting data array
      PixelData[][] result = new PixelData[img.getHeight()][img.getWidth()];

      System.out.print(img.getHeight() + " is the height" + "\n");
      System.out.print(img.getWidth() + " is the width" + "\n");


      for (int i = 0; i < img.getHeight(); i++) {
        for (int j = 0; j < img.getWidth(); j++) {
          int rgb = img.getRGB(j, i);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;

          result[i][j] = new PixelData(255, r, g, b);
//          System.out.print(result[i][j].toString());
        }
      }
      model.addImage(keyName, result);
    }
  }

  private void loadPPM(IModel model) throws FileNotFoundException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(pathName));
    } catch (FileNotFoundException e) {
      System.out.println("Uh oh! No file found at the " + pathName + "path!");
      throw new FileNotFoundException("Signal controller error!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return;
    }


    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    PixelData[][] data = new PixelData[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        r = r > maxValue ? r = maxValue : r;
        int g = sc.nextInt();
        g = g > maxValue ? g = maxValue : g;
        int b = sc.nextInt();
        b = b > maxValue ? b = maxValue : b;
        data[i][j] = new PixelData(maxValue, r, g, b);
      }
    }
    model.addImage(keyName, data);
  }
}
