import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.imageprocessing.commands.HorizontalFlip;
import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests {@link HorizontalFlip}. Tests that verify that this program properly supports the
 * horizontal-flip command.
 */
public class HorizontalFlipTest extends AbstractCommandTests {

  /**
   * Tests that when given invalid arguments, HorizontalFlip will throw
   * an IllegalArgumentException.
   */
  @Test
  public void testHorizontalFlipInstantiationFails() {
    try {
      new HorizontalFlip(null, "brfjhberfjhwf");
      new HorizontalFlip("jwhrjwkjrbglike", null);
      new HorizontalFlip(null, null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Tests that the HorizontalFlip object will construct when given valid arguments.
   */
  @Test
  public void testHorizontalFlipInstantiation() {
    boolean pass = true;
    try {
      new HorizontalFlip("dkasjf;dlkj", "dalksjf");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that Horizontal Flip can be applied to an input image, and that it does as expected.
   * This method tests the before and after of an input image being horizontally flipped,
   * asserting that it changes in the expected manner.
   */
  @Test
  public void testHorizontalFlip() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "horizontal-flip threeByThree threeByThreeHorizontal");

    PixelData[][] original = this.model.getImageCopy("threeByThree");
    PixelData[][] horizontalResult = this.model.getImageCopy("threeByThreeHorizontal");


    assertTrue(this.model.hasImage("threeByThree"));
    assertTrue(this.model.hasImage("threeByThreeHorizontal"));
    assertFalse(samePixelData(original, horizontalResult));

    PixelData p1 = new PixelData(255, 0, 255, 0);
    PixelData p2 = new PixelData(255, 0, 0, 255);
    PixelData p3 = new PixelData(255, 0, 0, 0);

    PixelData p4 = new PixelData(255, 255, 0, 255);
    PixelData p5 = new PixelData(255, 255, 255, 0);
    PixelData p6 = new PixelData(255, 255, 0, 0);

    PixelData p7 = new PixelData(255, 123, 123, 123);
    PixelData p8 = new PixelData(255, 255, 255, 255);
    PixelData p9 = new PixelData(255, 0, 255, 255);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    // is the result the same as the expected?
    assertTrue(samePixelData(horizontalResult, expected));

    ImageProcessingCommand secondFlip =
            new HorizontalFlip("threeByThreeHorizontal",
                    "threeByThreeReflipped");

    try {
      secondFlip.run(this.model);
    } catch (FileNotFoundException ignored) {

    }

    PixelData[][] horizontalResultReflipped
            = this.model.getImageCopy("threeByThreeReflipped");

    // tests that reflipping the image will return the original value
    assertTrue(samePixelData(horizontalResultReflipped, original));
  }

  /**
   * Testing that Horizontal Flip works correctly after applying multiple other transformations.
   * Also tests that a double flip results in the original image.
   */
  @Test
  public void testMultiCommandFlip() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten -10 threeByThree threeByThreeDarker " +
            "vertical-flip threeByThreeDarker threeByThreeVertical " +
            "horizontal-flip threeByThreeVertical threeByThreeHorizontal");

    PixelData p1 = new PixelData(255, 0, 245, 0);
    PixelData p2 = new PixelData(255, 0, 0, 245);
    PixelData p3 = new PixelData(255, 0, 0, 0);

    PixelData p4 = new PixelData(255, 245, 0, 245);
    PixelData p5 = new PixelData(255, 245, 245, 0);
    PixelData p6 = new PixelData(255, 245, 0, 0);

    PixelData p7 = new PixelData(255, 113, 113, 113);
    PixelData p8 = new PixelData(255, 245, 245, 245);
    PixelData p9 = new PixelData(255, 0, 245, 245);

    PixelData[] row1 = new PixelData[]{p7, p8, p9};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p1, p2, p3};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expected,
            this.model.getImageCopy("threeByThreeHorizontal")));

    assertFalse(samePixelData(expected,
            this.model.getImageCopy("threeByThreeVertical")));


    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThreeRevert " +
            "horizontal-flip threeByThreeRevert threeByThreeHoriz " +
            "horizontal-flip threeByThreeHoriz threeByThreeHorizontal2");

    assertTrue(samePixelData(this.model.getImageCopy("threeByThreeRevert"),
            this.model.getImageCopy("threeByThreeHorizontal2")));
    assertFalse(samePixelData(this.model.getImageCopy("threeByThreeRevert"),
            this.model.getImageCopy("threeByThreeHoriz")));
  }

  /**
   * Testing that horizontal flip works successfully for PNGs horizontal flip.
   */
  @Test
  public void testHorizFlipPNG() {
    setUp("load src/cs3500/imageprocessing/res/bacon-burger.png burg " +
            "horizontal-flip burg horiburg " +
            "horizontal-flip horiburg regburg");
    assertFalse(samePixelData(this.model.getImageCopy("burg"),
            this.model.getImageCopy("horiburg")));
    assertTrue(samePixelData(this.model.getImageCopy("burg"),
            this.model.getImageCopy("regburg")));
  }

  /**
   * Tests that horizontal flip is successful for JPGs.
   */
  @Test
  public void testHorizFlipJPG() {
    setUp("load src/cs3500/imageprocessing/res/testmeme.jpg meme " +
            "horizontal-flip meme horimeme " +
            "horizontal-flip horimeme regmeme");
    assertFalse(samePixelData(this.model.getImageCopy("meme"),
            this.model.getImageCopy("horimeme")));
    assertTrue(samePixelData(this.model.getImageCopy("meme"),
            this.model.getImageCopy("regmeme")));
  }

  /**
   * Tests that horizontal flip is successful for BMPs.
   */
  @Test
  public void testHorizFlipBMP() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp field " +
            "horizontal-flip field horifield " +
            "horizontal-flip horifield regfield");
    assertFalse(samePixelData(this.model.getImageCopy("field"),
            this.model.getImageCopy("horifield")));
    assertTrue(samePixelData(this.model.getImageCopy("field"),
            this.model.getImageCopy("regfield")));
  }

}
