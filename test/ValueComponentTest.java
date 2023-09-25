import org.junit.Test;

import cs3500.imageprocessing.commands.ValueComponent;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests {@link ValueComponent}. Tests that verify that this program properly supports the
 * value-component command.
 */
public class ValueComponentTest extends AbstractCommandTests {
  /**
   * Test that the constructor for the luma component command properly initializes a new instance
   * of the value component command.
   */
  @Test
  public void testLumaComponentInitializes() {
    boolean pass = true;
    try {
      new ValueComponent("tffthd", "jhbdxhs");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the luma component command fails to initialize a new instance
   * of the value component command when given invalid inputs.
   */
  @Test
  public void testLumaComponentInitializationFails() {
    try {
      new ValueComponent(null, "tyujyjthgf");
      new ValueComponent("rtsyhguj", null);
      new ValueComponent(null, null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * value component of each pixel.
   */
  @Test
  public void testValueComponent() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "value-component threeByThree threeByThreeVal");
    assertTrue(model.hasImage("threeByThreeVal"));
    PixelData[][] val3x3 = model.getImageCopy("threeByThreeVal");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 255, 255, 255);

    PixelData p4 = new PixelData(255, 255, 255, 255);
    PixelData p5 = new PixelData(255, 255, 255, 255);
    PixelData p6 = new PixelData(255, 255, 255, 255);

    PixelData p7 = new PixelData(255, 255, 255, 255);
    PixelData p8 = new PixelData(255, 255, 255, 255);
    PixelData p9 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};


    PixelData[][] expectedVal = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(val3x3, expectedVal));

    // asserts original is unchanged
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }
}
