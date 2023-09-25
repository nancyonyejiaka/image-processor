import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.commands.VerticalFlip;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests for {@link VerticalFlip}. Tests that verify that this program properly supports the
 * vertical-flip command.
 */
public class VerticalFlipTest extends AbstractCommandTests {

  /**
   * Tests that constructing VerticalFlip objects fail when given invalid arguments.
   */
  @Test
  public void testInstantiationFails() {
    try {
      new VerticalFlip(null, "szertbh6xdr5t6jaesgv");
      new VerticalFlip("erhrshtbszedrbh", null);
      new VerticalFlip(null, null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Tests a VerticalFlip object constructs when given valid arguments.
   */
  @Test
  public void testInstantiation() {
    boolean pass = true;
    try {
      new VerticalFlip("dkasjf;dlkj", "dalksjf");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that using the Vertical Flip command results in a 2D array of image data distinct from
   * the image it is flipping, as well as tests that it conforms to the expected results from
   * flipping the original image.  This also tests that when flipped again, the result of that
   * second flip is identical to the original image.
   */
  @Test
  public void testVerticalFlip() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "vertical-flip threeByThree threeByThreeVertical");

    PixelData[][] original = this.model.getImageCopy("threeByThree");
    PixelData[][] verticalResult = this.model.getImageCopy("threeByThreeVertical");


    assertTrue(this.model.hasImage("threeByThree"));
    assertTrue(this.model.hasImage("threeByThreeVertical"));
    assertFalse(samePixelData(original, verticalResult));

    PixelData p1 = new PixelData(255, 0, 255, 255);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData p4 = new PixelData(255, 255, 0, 0);
    PixelData p5 = new PixelData(255, 255, 255, 0);
    PixelData p6 = new PixelData(255, 255, 0, 255);

    PixelData p7 = new PixelData(255, 0, 0, 0);
    PixelData p8 = new PixelData(255, 0, 0, 255);
    PixelData p9 = new PixelData(255, 0, 255, 0);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};


    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    // is the result the same as the expected?
    assertTrue(samePixelData(verticalResult, expected));

    ImageProcessingCommand secondFlip =
            new VerticalFlip("threeByThreeVertical", "threeByThreeReflipped");

    try {
      secondFlip.run(this.model);
    } catch (FileNotFoundException ignored) {

    }

    PixelData[][] verticalResultReflipped = this.model.getImageCopy("threeByThreeReflipped");

    // tests that reflipping the image will return the original value
    assertTrue(samePixelData(verticalResultReflipped, original));

  }

  /**
   * Tests that vertical flip returns correctly after multiple inputs.
   */
  @Test
  public void testCompositeVertical() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "vertical-flip threeByThree threeByThreeVertical " +
            "vertical-flip threeByThreeVertical threeByThreeVertical2");

    // flipping vertically twice should revert it back to normal.
    assertTrue(samePixelData(model.getImageCopy("threeByThree"),
            model.getImageCopy("threeByThreeVertical2")));
  }
}