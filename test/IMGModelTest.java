import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.imageprocessing.commands.Brighten;
import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.commands.LoadIMG;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link IMGModel}.
 */
public class IMGModelTest extends AbstractCommandTests {
  PixelData p1 = new PixelData(255, 10, 10, 10);
  PixelData p2 = new PixelData(255, 10, 10, 255);
  PixelData p3 = new PixelData(255, 255, 10, 10);
  PixelData p4 = new PixelData(255, 255, 255, 10);

  PixelData[] row1 = new PixelData[]{p1, p2};
  PixelData[] row2 = new PixelData[]{p3, p4};

  PixelData[][] original = new PixelData[][]{row1, row2};

  /**
   * Tests that a PPMModel object instantiates when called / given valid arguments.
   */
  @Test
  public void testPPMModelInstantiation() {
    boolean pass = true;
    try {
      new IMGModel();
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that the copy method accurately copies an image, as well as tests the copy method
   * returns a 2D array decoupled from the original image's 2D array.
   */
  @Test
  public void testCopy() {
    ImageProcessingCommand load =
            new LoadIMG("src/cs3500/imageprocessing/res/2x2square.ppm",
                    "2x2sqr");

    IModel newModel = new IMGModel();

    try {
      load.run(newModel);
    } catch (FileNotFoundException ignored) {
    }
    PixelData[][] newPixels = newModel.getImageCopy("2x2sqr");

    // asserts that the newPixels copy is identical to what's expected
    assertTrue(samePixelData(newPixels, pixel2x2));

    // brightening the original, creating a new hashmap entry distinct from original
    ImageProcessingCommand brighten = new Brighten(10,
            "2x2sqr", "2x2brighter");
    try {
      brighten.run(newModel);
    } catch (FileNotFoundException ignored) {
      // do nothing
    }

    // newPixels is copy of ORIGINAL.  This test shows that when newPixels is brightened,
    // it does not affect the original (as the two now have different pixel data.)
    assertFalse(samePixelData(newPixels, newModel.getImageCopy("2x2brighter")));
  }
}
