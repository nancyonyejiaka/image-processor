package cs3500.imageprocessing.model;

import java.util.Objects;
import java.util.function.Function;

/**
 * A class representing a specific pixel of an image file.
 */
public class PixelData {
  private final int maxValue;
  private int r;
  private int g;
  private int b;

  /**
   * Constructor for the pixel class.
   *
   * @param maxValue the maximum value allowed for the pixels
   * @param r        the red color component
   * @param g        the green color component
   * @param b        the blue color component
   */
  public PixelData(int maxValue, int r, int g, int b) {
    validate(maxValue);
    validate(r);
    validate(g);
    validate(b);

    this.maxValue = maxValue;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Constructor for a pixel that takes in some other previously defined pixel.
   *
   * @param pixel the pixel being copied
   */
  public PixelData(PixelData pixel) {
    validate(pixel);
    this.maxValue = pixel.maxValue;
    this.r = pixel.r;
    this.g = pixel.g;
    this.b = pixel.b;
  }


  /**
   * Getter method for the red value.
   *
   * @return the integer component for red.
   */
  public int getR() {
    return this.r;
  }

  /**
   * Getter method for the green value.
   *
   * @return the integer representing the green component.
   */
  public int getG() {
    return this.g;
  }

  /**
   * Getter method for the blue value.
   *
   * @return the integer representing the blue component.
   */
  public int getB() {
    return this.b;
  }

  /**
   * Returns the value integer, the maximum value of the three pixels.
   *
   * @return the maximum value between the three pixels.
   */
  public int getValue() {
    return Math.max(Math.max(this.r, this.g), Math.max(this.g, this.b));
  }

  /**
   * Creates a representation of the pixel as a String.
   *
   * @return the pixel represented as a String.
   */
  @Override
  public String toString() {
    return " " + r + " " + g + " " + b + " ";
  }

  /**
   * Applies the given function object to determine which color should be emphasized to create
   * a grayscale image from this pixelData object.
   *
   * @param color the Function object being applied to the pixel
   */
  public void colorComponent(Function<PixelData, Integer> color) {
    validate(color);
    int val = color.apply(this);

    this.r = val;
    this.g = val;
    this.b = val;
  }

  /**
   * Changes the pixel by a given increment, defaulting to a max or min value if appropriate.
   *
   * @param increment the integer being added to each pixel. It can be negative
   */
  protected void changePixelVal(int increment) {
    int max = this.maxValue;

    if (increment >= 0) {
      this.r = Math.min(max, this.r + increment);
      this.g = Math.min(max, this.g + increment);
      this.b = Math.min(max, this.b + increment);
    } else {
      this.r = Math.max(0, this.r + increment);
      this.g = Math.max(0, this.g + increment);
      this.b = Math.max(0, this.b + increment);
    }
  }

  /**
   * Overrides equals so as to accurately compare to PixelData objects.
   *
   * @param that the object being compared to this
   * @return true if the two objects equal one another
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof PixelData)) {
      return false;
    }

    return ((PixelData) that).maxValue == this.maxValue
            && ((PixelData) that).r == this.r
            && ((PixelData) that).g == this.g
            && ((PixelData) that).b == this.b;

  }

  /**
   * Overrides the hashCode method.
   *
   * @return an int representing the hashcode for this object
   */
  @Override
  public int hashCode() {
    return this.r * 2 + this.g * 3 + this.b * 4;
  }

  /**
   * Verifies that object(s) given to the method are not null.
   *
   * @param params the parameters being validated
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
   * Returns the integer value of the max of the file.
   *
   * @return the maximum integer value for each piece of pixel data
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Applies a kernel to a matrix of original values taken from the image.  These are then summed
   * to find the final value of the pixel.
   *
   * @param heightPos the height position of the pixel being modified
   * @param widthPos  the width position of the pixel being modified
   * @param kernel    the kernel used for modification
   * @param original  the original array of pixel data being used to get the modified values
   */
  protected void applyFilter(int heightPos, int widthPos, double[][] kernel,
                             PixelData[][] original) {
    double[][] red = new double[kernel.length][kernel[0].length];
    double[][] green = new double[kernel.length][kernel[0].length];
    double[][] blue = new double[kernel.length][kernel[0].length];

    // double for loop that gets the pixel data in a square surrounding the target pixel
    for (int i = -1 * kernel.length / 2, kernelHeight = 0; i <= kernel.length / 2; i++) {
      for (int j = -1 * kernel[0].length / 2, kernelWidth = 0; j <= kernel.length / 2; j++) {
        int redInt;
        int greenInt;
        int blueInt;

        // try to get the pixels surrounding our target, ignore if out of bounds because results
        // array defaults to 0.0 as its value regardless, meaning the final summation works anyways
        try {
          redInt = original[heightPos + i][widthPos + j].getR();
          greenInt = original[heightPos + i][widthPos + j].getG();
          blueInt = original[heightPos + i][widthPos + j].getB();

          red[kernelHeight][kernelWidth] = redInt * kernel[kernelHeight][kernelWidth];
          green[kernelHeight][kernelWidth] = greenInt * kernel[kernelHeight][kernelWidth];
          blue[kernelHeight][kernelWidth] = blueInt * kernel[kernelHeight][kernelWidth];
        } catch (ArrayIndexOutOfBoundsException ignored) {
          // do nothing
        }
        kernelWidth += 1;
      }
      kernelHeight += 1;
    }
    this.r = (int) sumResults(red);
    this.g = (int) sumResults(green);
    this.b = (int) sumResults(blue);
  }

  /**
   * Applies a transformation to the pixel using the kernel.  All information needed for the
   * transformation exists within the pixel itself.
   *
   * @param kernel the 2D array of values used in calculating the final pixel values
   */
  protected void applyTransform(double[][] kernel) {
    int[] rgb = new int[]{this.r, this.g, this.b};
    int[] results = new int[3];

    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel[0].length; j++) {
        results[i] += kernel[i][j] * rgb[j];
        results[i] = results[i] > this.maxValue ? results[i] = this.maxValue : results[i];
      }
    }

    this.r = results[0];
    this.g = results[1];
    this.b = results[2];
  }

  /**
   * Sums the 2D array of doubles representing a kernel sum.
   *
   * @param result the 2D array returned by doing matrix multiplication of the kernel
   * @return the sum of the 2D array as a double
   */
  private double sumResults(double[][] result) {
    double sum = 0;

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        sum += result[i][j];
      }
    }

    sum = sum < 0 ? sum = 0 : sum;
    sum = sum > this.maxValue ? sum = this.maxValue : sum;
    return sum;
  }
}