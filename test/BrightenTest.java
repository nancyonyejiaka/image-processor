import org.junit.Test;

import cs3500.imageprocessing.commands.Brighten;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Brighten}. Tests that verify that this program properly supports the
 * brighten command.
 */
public class BrightenTest extends AbstractCommandTests {

  /**
   * Tests that different argument combinations for instantiating the brighten command fail when
   * they are given disallowed values, such as null.
   */
  @Test
  public void instantiationFails() {
    try {
      new Brighten(-10, null, null);
      new Brighten(20, "a", null);
      new Brighten(20, null, "a");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Testing that the brighten object instantiates correctly by checking its effects on other
   * aspects of the design when ran.
   */
  @Test
  public void testBrightenInstantiates() {
    boolean pass = true;
    try {
      new Brighten(10, "dalksjf", "jhfkseg");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * This test verifies that the brighten command properly brightens the PPM image by the
   * specified increment, without mutating the original source PPM.
   */
  @Test
  public void testBrightenNormal() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten 10 threeByThree threeByThreeBrighter");
    assertTrue(model.hasImage("threeByThreeBrighter"));
    PixelData[][] brighter3x3 = model.getImageCopy("threeByThreeBrighter");

    // tests custom-made brighter array matches the array created by command
    assertTrue(samePixelData(brighter3x3, brightPixel3x3));
    // tests that original array has NOT been mutated
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Tests that when brightening an image by an amount such that each pixel would exceed the
   * maximum value, the pixel instead remains capped at the max value.
   */
  @Test
  public void testBrightenNonOverflow() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten 500 threeByThree threeByThreeBrighter");
    assertTrue(model.hasImage("threeByThreeBrighter"));
    PixelData[][] brighter3x3 = model.getImageCopy("threeByThreeBrighter");

    PixelData[][] expected = new PixelData[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        expected[i][j] = new PixelData(255, 255, 255, 255);
      }
    }

    assertTrue(samePixelData(brighter3x3, expected));

    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * This test verifies that the brighten command properly brightens the PPM image by the
   * specified increment, without mutating the original source PPM.
   */
  @Test
  public void testDarkenNormal() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten -10 threeByThree threeByThreeDarker");
    assertTrue(model.hasImage("threeByThreeDarker"));
    PixelData[][] darker3x3 = model.getImageCopy("threeByThreeDarker");

    // tests custom made brighter array matches the array created by command
    assertTrue(samePixelData(darker3x3, darkPixel3x3));
    // tests that original array has NOT been mutated
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Tests that when given a value to brighten an image by that exceeds the max value, it will
   * remain at the minimum value, rather than allowing a pixel value to be negative.
   */
  @Test
  public void testDarkenNonOverflow() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten -500 threeByThree threeByThreeDarker");
    assertTrue(model.hasImage("threeByThreeDarker"));
    PixelData[][] darker3x3 = model.getImageCopy("threeByThreeDarker");

    PixelData[][] expected = new PixelData[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        expected[i][j] = new PixelData(255, 0, 0, 0);
      }
    }

    assertTrue(samePixelData(darker3x3, expected));

    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Test that brighten returns the expected after being applied to a already transformed array.
   */
  @Test
  public void testBrightenAfterTransformation() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "horizontal-flip threeByThree threeByThreeFlipped "
            + "brighten 10 threeByThreeFlipped threeByThreeBrighter ");

    PixelData p1 = new PixelData(255, 10, 10, 10);
    PixelData p2 = new PixelData(255, 10, 10, 255);
    PixelData p3 = new PixelData(255, 10, 255, 10);

    PixelData p4 = new PixelData(255, 255, 10, 10);
    PixelData p5 = new PixelData(255, 255, 255, 10);
    PixelData p6 = new PixelData(255, 255, 10, 255);

    PixelData p7 = new PixelData(255, 10, 255, 255);
    PixelData p8 = new PixelData(255, 255, 255, 255);
    PixelData p9 = new PixelData(255, 133, 133, 133);

    PixelData[] row1 = new PixelData[]{p3, p2, p1};
    PixelData[] row2 = new PixelData[]{p6, p5, p4};
    PixelData[] row3 = new PixelData[]{p9, p8, p7};

    PixelData[][] expectedBrighten = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expectedBrighten,
            model.getImageCopy("threeByThreeBrighter")));

    assertFalse(samePixelData(expectedBrighten,
            model.getImageCopy("threeByThreeFlipped")));

    assertFalse(samePixelData(expectedBrighten, model.getImageCopy("threeByThree")));
  }
}

