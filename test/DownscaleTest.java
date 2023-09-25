import org.junit.Test;

import java.io.IOException;

import cs3500.imageprocessing.commands.DownScale;
import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the downscale function object.
 */
public class DownscaleTest extends AbstractCommandTests {

  /**
   * Tests that different argument combinations for instantiating the Downscale command fail when
   * they are given disallowed values, such as null.
   */
  @Test
  public void instantiationFails() {
    try {
      new DownScale(-10, -10, null, null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }

    try {
      new DownScale(-50, -50, "alksdjf", "jads;kfj");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("New Dimensions cannot be 0 or below."));
    }
  }

  /**
   * Tests that downscaling an image creates an image of the passed in width and height.
   */
  @Test
  public void testDownScale() {
    setUp("load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich " +
            "downscale 50 20 sandwich sandwichSmall " +
            "save src/cs3500/imageprocessing/res/sandwichSmall.png sandwichSmall " +
            "load src/cs3500/imageprocessing/res/sandwichSmall.png newSandwich");

    PixelData[][] original = model.getImageCopy("sandwich");
    PixelData[][] newIMG = model.getImageCopy("sandwichSmall");

    assertFalse(samePixelData(original, newIMG));

    assertNotEquals(original.length, newIMG.length);
    assertNotEquals(original[0].length, newIMG[0].length);

    // flipped because inputs are given in width height rather than height width
    assertEquals(newIMG.length, 20);
    assertEquals(newIMG[0].length, 50);
  }

  /**
   * Tests that downscaling to dimensions greater than the original image will fail.
   */
  @Test
  public void testDownScaleThrowsWithTooManyPixels() {
    setUp("load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich");

    ImageProcessingCommand downScale = new DownScale(500, 500,
            "sandwich", "newSandwich");

    try {
      downScale.run(model);
    } catch (IOException io) {
      System.out.print("File not found.");
    } catch (IllegalArgumentException ie) {
      assertTrue(ie.getMessage().equals("Cannot increase size of images!"));
    }
  }
}
