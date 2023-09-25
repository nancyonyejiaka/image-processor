import org.junit.Test;

import cs3500.imageprocessing.commands.Filter;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Parent test class for all Commands that utilize Filter.
 */
public class FilterTest extends AbstractCommandTests {

  /**
   * Tests that Blur instantiates when given valid arguments.
   */
  @Test
  public void testBlurInitializes() {
    boolean pass = true;
    try {
      new Filter("e5gryjntae", "ntyjgah", "blur");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that blur instantiation fails when given invalid arguments.
   */
  @Test
  public void testBlurInstantiationFails() {
    assertTrue(testFilterInstantiationFails("2341", null, "blur"));
    assertTrue(testFilterInstantiationFails(null, "daklsfj", "blur"));
    assertTrue(testFilterInstantiationFails(null, null, "blur"));
    assertTrue(testFilterInstantiationFails("jkdlas", "jdslkafj", null));
  }

  /**
   * Test that the Blur command functions as expected.
   */
  @Test
  public void testBlur() {
    setUp("load src/cs3500/imageprocessing/res/bacon-burger.png burger " +
            "blur burger burger-blurred blur burger-blurred burger-blurred-2");

    PixelData[][] origBurg = model.getImageCopy("burger");
    PixelData[][] newBurg = model.getImageCopy("burger-blurred");
    PixelData[][] secondBlur = model.getImageCopy("burger-blurred-2");

    assertFalse(samePixelData(origBurg, newBurg));
    assertFalse(samePixelData(secondBlur, newBurg));
    assertFalse(samePixelData(secondBlur, origBurg));

    setUp("load src/cs3500/imageprocessing/res/brick-wall-small.png bricks " +
            "blur bricks bricks-blur");

    PixelData p1 = new PixelData(255, 118, 116, 117);
    PixelData p2 = new PixelData(255, 156, 153, 155);
    PixelData p3 = new PixelData(255, 156, 152, 155);
    PixelData p4 = new PixelData(255, 157, 154, 157);
    PixelData p5 = new PixelData(255, 119, 116, 118);

    PixelData p6 = new PixelData(255, 142, 133, 132);
    PixelData p7 = new PixelData(255, 183, 171, 169);
    PixelData p8 = new PixelData(255, 180, 168, 166);
    PixelData p9 = new PixelData(255, 186, 174, 173);
    PixelData p10 = new PixelData(255, 143, 134, 134);

    PixelData p11 = new PixelData(255, 107, 90, 82);
    PixelData p12 = new PixelData(255, 134, 111, 100);
    PixelData p13 = new PixelData(255, 131, 108, 97);
    PixelData p14 = new PixelData(255, 141, 118, 107);
    PixelData p15 = new PixelData(255, 111, 94, 87);

    PixelData p16 = new PixelData(255, 92, 68, 53);
    PixelData p17 = new PixelData(255, 116, 84, 65);
    PixelData p18 = new PixelData(255, 113, 81, 62);
    PixelData p19 = new PixelData(255, 119, 86, 68);
    PixelData p20 = new PixelData(255, 93, 68, 55);

    PixelData p21 = new PixelData(255, 74, 53, 41);
    PixelData p22 = new PixelData(255, 95, 67, 50);
    PixelData p23 = new PixelData(255, 92, 64, 47);
    PixelData p24 = new PixelData(255, 91, 63, 47);
    PixelData p25 = new PixelData(255, 69, 48, 36);

    PixelData[] row1 = new PixelData[]{p1, p2, p3, p4, p5};
    PixelData[] row2 = new PixelData[]{p6, p7, p8, p9, p10};
    PixelData[] row3 = new PixelData[]{p11, p12, p13, p14, p15};
    PixelData[] row4 = new PixelData[]{p16, p17, p18, p19, p20};
    PixelData[] row5 = new PixelData[]{p21, p22, p23, p24, p25};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3, row4, row5};

    PixelData[][] brickBlur = model.getImageCopy("bricks-blur");

    assertTrue(samePixelData(brickBlur, expected));
  }

  /**
   * Test that the constructor for the sharpen command properly initialize an instance of the
   * command when given valid arguments.
   */
  @Test
  public void testSharpenInstantiates() {
    boolean pass = true;
    try {
      new Filter("srthrxthr", "hhrtbste", "sharpen");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the sharpen command fails to initialize an instance of the
   * command when given invalid arguments.
   */
  @Test
  public void testSharpenInstantiationFails() {
    assertTrue(testFilterInstantiationFails(null, null, "sharpen"));
    assertTrue(testFilterInstantiationFails("jasdkl;f", null, "sharpen"));
    assertTrue(testFilterInstantiationFails(null, "jadlfk", "sharpen"));
    assertTrue(testFilterInstantiationFails("ajsdklf", "asdjkfl", null));
  }

  /**
   * Test that the sharpen command properly changes pixel values to sharpen an image.
   */
  @Test
  public void testSharpen() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm square "
            + "sharpen square sqrsharp "
            + "save src/cs3500/imageprocessing/res/sqrsharp.png sqrsharp ");

    // expected values after applying filter by hand on paper
    PixelData p1 = new PixelData(255, 48, 0, 0);
    PixelData p2 = new PixelData(255, 144, 48, 239);
    PixelData p3 = new PixelData(255, 48, 239, 48);

    PixelData p4 = new PixelData(255, 255, 144, 144);
    PixelData p5 = new PixelData(255, 255, 255, 255);
    PixelData p6 = new PixelData(255, 255, 190, 255);

    PixelData p7 = new PixelData(255, 144, 255, 239);
    PixelData p8 = new PixelData(255, 255, 255, 255);
    PixelData p9 = new PixelData(255, 255, 186, 186);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};
    PixelData[][] actual = this.model.getImageCopy("sqrsharp");

    assertTrue(samePixelData(expected, actual));
  }
}
