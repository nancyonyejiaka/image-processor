package cs3500.imageprocessing.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An implementation of the IModel interface, representing the program's image storage capacity.
 */
public class IMGModel implements IModel {
  private final HashMap<String, PixelData[][]> storage;

  /**
   * Constructs a new instance of the PPMModel class.  New instances of this class default to
   * having empty storage instances.
   */
  public IMGModel() {
    this.storage = new HashMap<>();
  }

  /**
   * Verifies that object(s) given to the method are not null.
   *
   * @param params the parameters being validated.
   */
  public void validate(Object... params) {
    for (Object o : params) {
      try {
        Objects.requireNonNull(o);
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("Arguments cannot be null!");
      }
    }
  }


  /**
   * Creates a copy of the image storage, then retrieves the requested image from that copy.
   *
   * @param keyName the key value of the image to be retrieved
   * @return the data stored at the desired key value
   * @throws IllegalArgumentException if the given argument is null
   */
  public PixelData[][] getImageCopy(String keyName) throws IllegalArgumentException {
    validate(keyName);
    if (!storage.containsKey(keyName)) {
      throw new IllegalArgumentException("No image by the name: " + keyName);
    }

    PixelData[][] original = storage.get(keyName);
    PixelData[][] copy = new PixelData[original.length][original[0].length];
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        copy[i][j] = new PixelData(original[i][j]);
      }
    }
    return copy;
  }

  /**
   * Returns true if the image storage contains the given file.
   *
   * @param keyName the key searched for
   * @return a true boolean if the key exists in the map, false if not
   * @throws IllegalArgumentException if the given argument is null
   */
  public boolean hasImage(String keyName) throws IllegalArgumentException {
    validate(keyName);
    return this.storage.containsKey(keyName);
  }

  /**
   * Add a new key value pair to the storage map based on the given name and PixelData array to
   * represent a new image.
   *
   * @param keyName the key to store the image data under in the map.
   * @param data the image data being stored.
   */
  public void addImage(String keyName, PixelData[][] data) {
    validate(keyName, data);
    if (this.storage.containsKey(keyName)) {
      System.out.print("Overwriting previously saved data!");
    }
    this.storage.put(keyName, data);
  }

  /**
   * Changes the values of the pixel data by the given increment.  Will fall back to max or min
   * pixel value, if attempts are made to exceed or go below the maximum or minimum.
   *
   * @param keyName   the key value the data to be modified is stored under.
   * @param newName   the new name under which to store the modified copy.
   * @param increment the value with which to increase or decrease the pixel value.
   */
  @Override
  public void changePixelVal(String keyName, String newName, int increment) {
    editPixels(keyName, newName, (PixelData) -> PixelData.changePixelVal(increment));
  }

  /**
   * Flips the image, specified by the key name, horizontally.
   *
   * @param keyName the key under which the data to be copied and mutated is stored.
   * @param newName the new key name under which to store the modified data.
   */
  @Override
  public void horizontalFlip(String keyName, String newName) {

    PixelData[][] copy = this.getImageCopy(keyName);
    int rowLength = copy[0].length - 1;

    for (int height = 0; height < copy.length; height++) {
      for (int width = 0; width < copy[height].length / 2; width++) {
        PixelData original = new PixelData(copy[height][width]);
        copy[height][width] = copy[height][rowLength - width];
        copy[height][rowLength - width] = original;
      }
    }
    this.storage.put(newName, copy);
  }

  /**
   * Flips the image, specified by the key name, vertically.
   *
   * @param keyName the key under which the data to be copied and mutated is stored.
   * @param newName the new key name under which to store the modified data.
   */
  public void verticalFlip(String keyName, String newName) {
    PixelData[][] copy = this.getImageCopy(keyName);

    for (int i = 0, j = copy.length - 1; j >= 0; j--) {
      if (i >= j) {
        this.storage.put(newName, copy);
        return;
      } else {
        PixelData[] oldRow = copy[i];
        copy[i] = copy[j];
        copy[j] = oldRow;
        i++;
      }
    }
  }

  /**
   * Modifies the given pixel's red, green, and blue values based on the given color char.
   *
   * @param keyName the key name the desired data is stored under.
   * @param newName the new name under which to store the modified data.
   * @param color   the char that decided which formula modifies the pixel's color data.
   */
  @Override
  public void colorComponent(String keyName, String newName, Function<PixelData, Integer> color) {
    editPixels(keyName, newName, (PixelData) -> PixelData.colorComponent(color));
  }

  /**
   * Mutates the double[][] for each color component of a pixel, using the kernel to get the
   * pixel color component values after the filter has been applied.
   *
   * @param kernel the matrix of values to be applied to the color component values in order to
   *               apply the filter
   */
  public void filter(String keyName, String newName, double[][] kernel) {

    validate(keyName, newName, kernel);

    PixelData[][] original = this.getImageCopy(keyName);
    PixelData[][] mod = this.getImageCopy(keyName);

    for (int i = 0; i < mod.length; i++) {
      for (int j = 0; j < mod[0].length; j++) {
        mod[i][j].applyFilter(i, j, kernel, original);
      }
    }
    this.storage.put(newName, mod);
  }

  /**
   * Transforms a pixel's values based on the kernel given.  Needs only the kernel and pixel data
   * to determine the final value.
   *
   * @param keyName the key under which the pixel data is stored.
   * @param newName the new name under which to store the modified data.
   * @param kernel  the kernel being applied to each pixel.
   */
  public void transform(String keyName, String newName, double[][] kernel) {
    editPixels(keyName, newName, (PixelData) -> PixelData.applyTransform(kernel));
  }

  /**
   * Edit the pixels of the image specified by the key name, then saves it to the specified new
   * name.
   * @param keyName the key under which the data to copy and modify is stored.
   * @param newName the new name under which to store the modified data.
   * @param func the Consumer used to modify the pixels.
   */
  private void editPixels(String keyName, String newName, Consumer<PixelData> func) {
    validate(keyName, newName, func);
    
    PixelData[][] mod = this.getImageCopy(keyName);

    for (PixelData[] pixelData : mod) {
      for (int j = 0; j < mod[0].length; j++) {
        func.accept(pixelData[j]);
      }
    }
    this.storage.put(newName, mod);
  }

  public HashMap<Integer, Integer>[] generateHistogramData (String keyName)
          throws IllegalArgumentException {
    HashMap<Integer, Integer> redPixels = new HashMap<>();
    HashMap<Integer, Integer> greenPixels = new HashMap<>();
    HashMap<Integer, Integer> bluePixels = new HashMap<>();
    HashMap<Integer, Integer> intensityPixels = new HashMap<>();

    populatePixelMaps(redPixels, greenPixels, bluePixels, intensityPixels);

    if (keyName == null || !this.storage.containsKey(keyName)) {
      throw new IllegalArgumentException("Key Name is invalid.");
    }

    PixelData[][] image = this.storage.get(keyName);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        int redVal = image[i][j].getR();
        int greenVal = image[i][j].getG();
        int blueVal = image[i][j].getB();
        int intensityVal = (redVal + greenVal + blueVal) / 3;
        redPixels.put(redVal, redPixels.get(redVal) + 1);
        greenPixels.put(greenVal, greenPixels.get(greenVal) + 1);
        bluePixels.put(blueVal, bluePixels.get(blueVal) + 1);
        intensityPixels.put(intensityVal, intensityPixels.get(intensityVal) + 1);
      }
    }

    return new HashMap[]{redPixels, greenPixels, bluePixels, intensityPixels};
  }

  /**
   * Populates the map of data used for the histogram with values from 0-255 to reduce
   * code duplication in parent method.
   * @param map The map being added to.
   */
  @SafeVarargs
  private void populatePixelMaps(HashMap<Integer, Integer>... map) {
    for (HashMap<Integer, Integer> hash : map) {
      for (int i = 0; i < 256; i++) {
        hash.put(i, 0);
      }
    }
  }

  /**
   * Generates a BufferedImage of the PixelData[][] stored at the keyName.
   * @param keyName The key under which the PixelData[][] is stored.
   * @param out The Appendable we test the outputs with.
   * @return The BufferedImage representation of the PixelData[][]
   */
  public BufferedImage generateIMG(String keyName, Appendable out) {
    PixelData[][] data = this.storage.get(keyName);

    BufferedImage img = new BufferedImage(data[0].length, data.length,
            BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        int red = data[i][j].getR();
        int green = data[i][j].getG();
        int blue = data[i][j].getB();

        int rgb = ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff);
        img.setRGB(j, i, rgb);

        try {
          out.append(" (" + red + ", " + green + ", " + blue + ") ");
        } catch (IOException ie) {
          System.out.print("appending to out fails in Model's generateIMG.");
        }

      }
      try {
        out.append("\n");
      } catch (IOException e) {
        System.out.print("appending to out fails in Model's generateIMG.");
      }
    }
    return img;
  }
}