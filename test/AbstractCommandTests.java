import org.junit.Test;

import java.io.StringReader;

import cs3500.imageprocessing.commands.Filter;
import cs3500.imageprocessing.commands.Transform;
import cs3500.imageprocessing.controller.Controller;
import cs3500.imageprocessing.controller.IController;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertTrue;


/**
 * Represents an abstract test class for the commands supported by this project.
 */
public class AbstractCommandTests {
  IModel model;
  IController controller;

  PixelData p1 = new PixelData(255, 10, 10, 10);
  PixelData p2 = new PixelData(255, 10, 10, 255);
  PixelData p3 = new PixelData(255, 255, 10, 10);
  PixelData p4 = new PixelData(255, 255, 255, 10);
  PixelData[] row1 = new PixelData[]{p1, p2};
  PixelData[] row2 = new PixelData[]{p3, p4};

  PixelData[][] pixel2x2 = new PixelData[][]{row1, row2};

  PixelData p5 = new PixelData(255, 0, 0, 0);
  PixelData p6 = new PixelData(255, 0, 0, 255);
  PixelData p7 = new PixelData(255, 0, 255, 0);

  PixelData p8 = new PixelData(255, 255, 0, 0);
  PixelData p9 = new PixelData(255, 255, 255, 0);
  PixelData p10 = new PixelData(255, 255, 0, 255);

  PixelData p11 = new PixelData(255, 0, 255, 255);
  PixelData p12 = new PixelData(255, 255, 255, 255);
  PixelData p13 = new PixelData(255, 123, 123, 123);

  PixelData[] row3 = new PixelData[]{p5, p6, p7};
  PixelData[] row4 = new PixelData[]{p8, p9, p10};
  PixelData[] row5 = new PixelData[]{p11, p12, p13};
  PixelData[][] pixel3x3 = new PixelData[][]{row3, row4, row5};


  PixelData p14 = new PixelData(255, 10, 10, 10);
  PixelData p15 = new PixelData(255, 10, 10, 255);
  PixelData p16 = new PixelData(255, 10, 255, 10);

  PixelData p17 = new PixelData(255, 255, 10, 10);
  PixelData p18 = new PixelData(255, 255, 255, 10);
  PixelData p19 = new PixelData(255, 255, 10, 255);

  PixelData p20 = new PixelData(255, 10, 255, 255);
  PixelData p21 = new PixelData(255, 255, 255, 255);
  PixelData p22 = new PixelData(255, 133, 133, 133);

  PixelData[] row6 = new PixelData[]{p14, p15, p16};
  PixelData[] row7 = new PixelData[]{p17, p18, p19};
  PixelData[] row8 = new PixelData[]{p20, p21, p22};

  PixelData[][] brightPixel3x3 = new PixelData[][]{row6, row7, row8};

  PixelData p14d = new PixelData(255, 0, 0, 0);
  PixelData p15d = new PixelData(255, 0, 0, 245);
  PixelData p16d = new PixelData(255, 0, 245, 0);

  PixelData p17d = new PixelData(255, 245, 0, 0);
  PixelData p18d = new PixelData(255, 245, 245, 0);
  PixelData p19d = new PixelData(255, 245, 0, 245);

  PixelData p20d = new PixelData(255, 0, 245, 245);
  PixelData p21d = new PixelData(255, 245, 245, 245);
  PixelData p22d = new PixelData(255, 113, 113, 113);

  PixelData[] row6d = new PixelData[]{p14d, p15d, p16d};
  PixelData[] row7d = new PixelData[]{p17d, p18d, p19d};
  PixelData[] row8d = new PixelData[]{p20d, p21d, p22d};
  PixelData[][] darkPixel3x3 = new PixelData[][]{row6d, row7d, row8d};

  /**
   * Sets up the Model and Controller such that the commands passed in are applied by the
   * controller to the model.
   *
   * @param inCmd the String of valid commands executed on the model
   */
  public void setUp(String inCmd) {
    this.model = new IMGModel();
    Readable input = new StringReader(inCmd);
    this.controller = new Controller(model, input, System.out);
    this.controller.run();
  }

  /**
   * Testing that running a command through the setup will result in the expected change.
   */
  @Test
  public void testSetup() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm twoByTwo");
    assertTrue(this.model.hasImage("twoByTwo"));
  }

  /**
   * Checks if the two PixelData 2D arrays have the same contents.
   *
   * @param original the original array being tested for sameness
   * @param newArray the recently created array being compared against the original
   * @return true if the two 2D arrays are the same
   */
  public static boolean samePixelData(PixelData[][] original, PixelData[][] newArray) {
    // checks if they have the same dimensions first
    if (original.length != newArray.length || original.length > 0
            && original[0].length != newArray[0].length) {
      return false;
    }

    boolean testPass = true;

    // loop iterates over each loop and compares their items
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[i].length; j++) {
        System.out.print("\n(" + original[i][j].toString() + newArray[i][j] + ")");
        if (!original[i][j].equals(newArray[i][j])) {
          testPass = false;
        }
      }
    }
    return testPass;
  }

  /**
   * Test that the constructor for a filter class fails when given invalid arguments.
   *
   * @param keyName the name that the image will be stored under in this program
   * @param newName the new name that the image will ve stored under
   *                after the filter has been applied
   * @param type    the type of filter than the constructor will be creating
   * @return true if the instantiation of the filter class fails, false otherwise
   */
  public static boolean testFilterInstantiationFails(String keyName,
                                                     String newName,
                                                     String type) {
    boolean testPass = false;
    try {
      new Filter(keyName, newName, type);
    } catch (IllegalArgumentException ie) {
      testPass = ie.getMessage().equals("Arguments cannot be null!");
    }
    return testPass;
  }

  /**
   * Test that the constructor for a transformation class fails when given invalid arguments.
   *
   * @param keyName the name that the image will be stored under in this program
   * @param newName the new name that the image will ve stored under
   *                after the color transformation has been applied
   * @param type    the type of transformation than the constructor will be creating
   * @return true if the instantiation of the transformation class fails, false otherwise
   */
  public static boolean testTransformInstantiationFails(String keyName,
                                                        String newName,
                                                        String type) {
    boolean testPass = false;
    try {
      new Transform(keyName, newName, type);
    } catch (IllegalArgumentException ie) {
      testPass = ie.getMessage().equals("Arguments cannot be null!");
    }
    return testPass;
  }
}
