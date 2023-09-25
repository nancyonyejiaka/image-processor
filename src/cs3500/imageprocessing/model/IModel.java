package cs3500.imageprocessing.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Interface for the Model of our Image Processing program.
 */
public interface IModel {

  /**
   * Returns a 2D array copy of the image stored in the map by that keyName.
   *
   * @param storedName (The keyname at which the data we wish to copy is stored.)
   * @return the copied 2D array.
   */
  PixelData[][] getImageCopy(String storedName);

  /**
   * Checks if the storage map contains an image by the given name.
   *
   * @param storedName (The name being looked for within the Model.)
   * @return true if the image exists within storage.
   */
  boolean hasImage(String storedName);

  /**
   * Helper method for Command Pattern.  Mutates a pixel based on the given function object being
   * passed into it.
   *
   * @param keyName (The key under which the image data to be copied and mutated is stored.)
   * @param newName (The new keyname under which to store the modified data.)
   * @param color   (The function object passed in to mutate the pixel.)
   */
  void colorComponent(String keyName, String newName, Function<PixelData, Integer> color);

  /**
   * Helper method for the Command Pattern.  Horizontally flips an image.
   * @param keyName The key under which the data to be copied and mutated is stored.
   * @param newName The new key name under which to store the modified data.
   */
  void horizontalFlip(String keyName, String newName);

  /**
   * Helper method for the Command Pattern.  Vertically flips an image.
   * @param keyName The key under which the data to be copied and mutated is stored.
   * @param newName The new key name under which to store the modified data.
   */
  void verticalFlip(String keyName, String newName);

  /**
   * Changes the PixelData array's values by a specific increment.
   *
   * @param keyName   the key under which the image data to be copied and mutated is stored
   * @param newName   the new keyName under which to store the mutated data
   * @param increment the amount you're increasing or decreasing a pixel's values
   */
  void changePixelVal(String keyName, String newName, int increment);

  /**
   * Checks if the object passed in is null.
   *
   * @param params the object(s) being validated
   * @throws IllegalArgumentException if the object passed in is null.
   */
  void validate(Object ... params) throws IllegalArgumentException;

  /**
   * Filters each pixel in the image using the given kernel.
   *
   * @param keyName the key name under which the data is stored
   * @param newName the new name under which to store the transformed data
   * @param kernel  the kernel applied to each pixel
   */
  void filter(String keyName, String newName, double[][] kernel);

  /**
   * Transforms a pixel using the given kernel, using just the value of the specific pixel and the
   * kernel.  This is as opposed to filter, which requires more information than just the one pixel.
   *
   * @param keyName the key name under which the data is stored
   * @param newName the new name under which to store the transformed data
   * @param kernel  the kernel applied to each pixel
   */
  void transform(String keyName, String newName, double[][] kernel);

  /**
   * Convenience method used to add an image to the storage map.  Used only by the load commands.
   *
   * @param keyName the key to store the image data under in the map.
   * @param result the image data being stored.
   */
  void addImage(String keyName, PixelData[][] result);

  /**
   * Generates the Array of Hashmaps representing a Histogram's data.
   * @param keyName The keyName of the image analyzed for data.
   * @return The data Array of Hashmaps.
   * @throws IllegalArgumentException if the keyName is invalid.
   */
  HashMap<Integer, Integer>[] generateHistogramData (String keyName)
          throws IllegalArgumentException;

  /**
   * Generate a Buffered Image of an image.
   * @param keyName The keyName of the image being translated.
   * @param out An Appendable for checking with tests.
   * @return A BufferedImage of the stored image.
   */
  BufferedImage generateIMG(String keyName, Appendable out);
}
