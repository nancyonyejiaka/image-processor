import org.junit.Test;

import cs3500.imageprocessing.commands.Transform;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Parent test class for all Commands that use Transform.
 */
public class TransformTest extends AbstractCommandTests {
  /**
   * Test that the constructor for the blue component command properly initializes a new instance
   * of the blue component command.
   */
  @Test
  public void testBlueComponentInitializes() {
    boolean pass = true;
    try {
      new Transform("e5gryjntae", "ntyjgah", "blue");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the blue component command fails to initialize a new instance
   * of the blue component command when given invalid inputs.
   */
  @Test
  public void testBlueComponentInitializationFails() {
    assertTrue(testTransformInstantiationFails(null, null, "blue"));
    assertTrue(testTransformInstantiationFails("asdfa", null, "blue"));
    assertTrue(testTransformInstantiationFails(null, "dafs", "blue"));
    assertTrue(testTransformInstantiationFails("jadkl", "kladsfj;a", null));
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * blue component of each pixel.
   */
  @Test
  public void testBlueComponentPPM() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree "
            + "blue-component threeByThree threeByThreeBlue");
    assertTrue(model.hasImage("threeByThreeBlue"));
    PixelData[][] blue3x3 = model.getImageCopy("threeByThreeBlue");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p1};
    PixelData[] row2 = new PixelData[]{p1, p1, p2};
    PixelData[] row3 = new PixelData[]{p2, p2, p3};

    PixelData[][] expectedBlue = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(blue3x3, expectedBlue));

    // asserts original is unchanged
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Test that the blue component command works after other transformations are executed.
   */
  @Test
  public void testBlueComponentAfterTransformationPPM() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree "
            + "horizontal-flip threeByThree threeByThreeFlipped "
            + "blue-component threeByThreeFlipped threeByThreeBlue");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p1};
    PixelData[] row2 = new PixelData[]{p2, p1, p1};
    PixelData[] row3 = new PixelData[]{p3, p2, p2};

    PixelData[][] expectedBlueFlip = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expectedBlueFlip, model.getImageCopy("threeByThreeBlue")));

    assertFalse(samePixelData(expectedBlueFlip,
            model.getImageCopy("threeByThreeFlipped")));
    assertFalse(samePixelData(expectedBlueFlip,
            model.getImageCopy("threeByThree")));
  }


  /**
   * Test that the constructor for the green component command properly initializes a new instance
   * of the green component command.
   */
  @Test
  public void testGreenComponentInitializes() {
    boolean pass = true;
    try {
      new Transform("srthrxthr", "hhrtbste", "green");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the green component command fails to initialize a new instance
   * of the green component command when given invalid inputs.
   */
  @Test
  public void testGreenComponentInitializationFails() {
    try {
      new Transform(null, "gdrgr", "green");
      new Transform("htfghr", null, "green");
      new Transform(null, null, "green");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * green component of each pixel.
   */
  @Test
  public void testGreenComponent() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "green-component threeByThree threeByThreeGreen");
    assertTrue(model.hasImage("threeByThreeGreen"));
    PixelData[][] green3x3 = model.getImageCopy("threeByThreeGreen");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p1, p2};
    PixelData[] row2 = new PixelData[]{p1, p2, p1};
    PixelData[] row3 = new PixelData[]{p2, p2, p3};

    PixelData[][] expectedGreen = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(green3x3, expectedGreen));

    // asserts original is unchanged
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Tests that you can still get the green components as expected after multiple transformations.
   */
  @Test
  public void testGreenComponentAfterTransformation() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree "
            + "vertical-flip threeByThree threeByThreeFlipped "
            + "horizontal-flip threeByThreeFlipped threeByThreeFlipped2 "
            + "green-component threeByThreeFlipped2 threeByThreeGreen");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p3, p2, p2};
    PixelData[] row2 = new PixelData[]{p1, p2, p1};
    PixelData[] row3 = new PixelData[]{p2, p1, p1};

    PixelData[][] expectedGreenFlip = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expectedGreenFlip,
            model.getImageCopy("threeByThreeGreen")));

    assertFalse(samePixelData(expectedGreenFlip,
            model.getImageCopy("threeByThreeFlipped")));
    assertFalse(samePixelData(expectedGreenFlip,
            model.getImageCopy("threeByThreeFlipped2")));
    assertFalse(samePixelData(expectedGreenFlip,
            model.getImageCopy("threeByThree")));
  }

  /**
   * Test that the constructor for the luma component command properly initializes a new instance
   * of the intensity component command.
   */
  @Test
  public void testIntComponentInitializes() {
    boolean pass = true;
    try {
      new Transform("tffthd", "jhbdxhs", "intensity");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the luma component command fails to initialize a new instance
   * of the intensity component command when given invalid inputs.
   */
  @Test
  public void testIntComponentInitializationFails() {
    try {
      new Transform(null, "tyujyjthgf", "intensity");
      new Transform("rtsyhguj", null, "intensity");
      new Transform(null, null, "intensity");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * maximum the color components of each pixel.
   */
  @Test
  public void testIntensityComponent() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "intensity-component threeByThree threeByThreeIntense");
    assertTrue(model.hasImage("threeByThreeIntense"));
    PixelData[][] intense3x3 = model.getImageCopy("threeByThreeIntense");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 84, 84, 84);
    PixelData p3 = new PixelData(255, 168, 168, 168);
    PixelData p4 = new PixelData(255, 252, 252, 252);
    PixelData p5 = new PixelData(255, 120, 120, 120);

    PixelData[] row1 = new PixelData[]{p1, p2, p2};
    PixelData[] row2 = new PixelData[]{p2, p3, p3};
    PixelData[] row3 = new PixelData[]{p3, p4, p5};

    PixelData[][] expectedIntense = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(intense3x3, expectedIntense));

    // asserts original is unchanged
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Testing that Intensity Component returns correctly after multiple transformations.
   */
  @Test
  public void testIntensityComponentAfterTransformations() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "horizontal-flip threeByThree threeByThreeFlipped " +
            "intensity-component threeByThreeFlipped threeByThreeIntense");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 84, 84, 84);
    PixelData p3 = new PixelData(255, 168, 168, 168);
    PixelData p4 = new PixelData(255, 252, 252, 252);
    PixelData p5 = new PixelData(255, 120, 120, 120);

    PixelData[] row1 = new PixelData[]{p2, p2, p1};
    PixelData[] row2 = new PixelData[]{p3, p3, p2};
    PixelData[] row3 = new PixelData[]{p5, p4, p3};

    PixelData[][] expectedIntense = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expectedIntense,
            this.model.getImageCopy("threeByThreeIntense")));
    assertFalse(samePixelData(expectedIntense, this.model.getImageCopy("threeByThree")));
    assertFalse(samePixelData(expectedIntense,
            this.model.getImageCopy("threeByThreeFlipped")));
  }

  /**
   * Test that the constructor for the luma component command properly initializes a new instance
   * of the luma component command.
   */
  @Test
  public void testLumaComponentInitializes() {
    boolean pass = true;
    try {
      new Transform("tffthd", "jhbdxhs", "luma");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the luma component command fails to initialize a new instance
   * of the luma component command when given invalid inputs.
   */
  @Test
  public void testLumaComponentOriginalInitializationFails() {
    try {
      new Transform(null, "tyujyjthgf", "luma");
      new Transform("rtsyhguj", null, "luma");
      new Transform(null, null, "luma");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * weighted sum of the color components of each pixel.
   */
  @Test
  public void testLumaComponent() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "luma-component threeByThree threeByThreeLuma");
    assertTrue(model.hasImage("threeByThreeLuma"));
    PixelData[][] luma3x3 = model.getImageCopy("threeByThreeLuma");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 18, 18, 18);
    PixelData p3 = new PixelData(255, 182, 182, 182);

    PixelData p4 = new PixelData(255, 54, 54, 54);
    PixelData p5 = new PixelData(255, 236, 236, 236);
    PixelData p6 = new PixelData(255, 72, 72, 72);

    PixelData p7 = new PixelData(255, 200, 200, 200);
    PixelData p8 = new PixelData(255, 254, 254, 254);
    PixelData p9 = new PixelData(255, 121, 121, 121);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expectedLuma = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(luma3x3, expectedLuma));

    // asserts original is unchanged
    assertTrue(samePixelData(model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Test that the luma command functions accurately with other commands called in conjunction.
   */
  @Test
  public void testLumaComposite() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "vertical-flip threeByThree threeByThreeFlipped " +
            "luma-component threeByThreeFlipped threeByThreeLuma");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 18, 18, 18);
    PixelData p3 = new PixelData(255, 182, 182, 182);

    PixelData p4 = new PixelData(255, 54, 54, 54);
    PixelData p5 = new PixelData(255, 236, 236, 236);
    PixelData p6 = new PixelData(255, 72, 72, 72);

    PixelData p7 = new PixelData(255, 200, 200, 200);
    PixelData p8 = new PixelData(255, 254, 254, 254);
    PixelData p9 = new PixelData(255, 121, 121, 121);

    PixelData[] row1 = new PixelData[]{p7, p8, p9};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p1, p2, p3};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expected, model.getImageCopy("threeByThreeLuma")));
    assertFalse(samePixelData(expected, model.getImageCopy("threeByThreeFlipped")));
  }

  /**
   * Test that the constructor for the red component command properly initializes a new instance
   * of the red component command.
   */
  @Test
  public void testRedComponentInitializes() {
    boolean pass = true;
    try {
      new Transform("oijfjnjfbkjf", "tgreflinhy", "red");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the constructor for the blue component command fails to initialize a new instance
   * of the red component command when given invalid inputs.
   */
  @Test
  public void testRedComponentInitializationFails() {
    try {
      new Transform(null, "eshtethj6", "red");
      new Transform("drthryh", null, "red");
      new Transform(null, null, "red");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the color component command properly creates a grayscale image based on the
   * red component of each pixel.
   */
  @Test
  public void testRedComponent() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "red-component threeByThree threeByThreeRed");
    assertTrue(model.hasImage("threeByThreeRed"));
    PixelData[][] red3x3 = model.getImageCopy("threeByThreeRed");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p1, p1};
    PixelData[] row2 = new PixelData[]{p2, p2, p2};
    PixelData[] row3 = new PixelData[]{p1, p2, p3};


    PixelData[][] expectedRed = new PixelData[][]{row1, row2, row3};

    // asserts it creates the expected data array
    assertTrue(samePixelData(red3x3, expectedRed));

    // asserts original is unchanged
    assertTrue(samePixelData(this.model.getImageCopy("threeByThree"), this.pixel3x3));
  }

  /**
   * Tests that the red command functions correctly with multiple other methods called together.
   */
  @Test
  public void testCompositeRedCommand() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "value-component threeByThree threeByThreeVal " +
            "red-component threeByThreeVal threeByThreeRed");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p2};
    PixelData[] row2 = new PixelData[]{p2, p2, p2};
    PixelData[] row3 = new PixelData[]{p2, p2, p3};

    PixelData[][] expectedRed = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(this.model.getImageCopy("threeByThreeRed"), expectedRed));
  }


  /**
   * Tests that when given valid (but not necessarily correct!) arguments, the Transform object
   * can successfully instantiate.
   */
  @Test
  public void testSepiaInstantiates() {
    boolean pass = true;
    try {
      new Transform("srthrxthr", "hhrtbste", "sepia");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }


  /**
   * Tests that instantiation fails when given invalid (null) components.
   */
  @Test
  public void testSepiaInstantiationFails() {
    assertTrue(testTransformInstantiationFails(null, null, "sepia"));
    assertTrue(testTransformInstantiationFails("jasdkl;f", null, "sepia"));
    assertTrue(testTransformInstantiationFails(null, "jadlfk", "sepia"));
    assertTrue(testTransformInstantiationFails("ajsdklf", "asdjkfl", null));
  }

  /**
   * Tests that the Sepia command, when invoked, transforms an image as expected by hand our
   * by-hand calculations.
   */
  @Test
  public void testSepia() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm square "
            + "sepia square sqrsep ");

    PixelData[][] original = this.model.getImageCopy("square");
    PixelData[][] newSqr = this.model.getImageCopy("sqrsep");

    // calculated by hand expected values
    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 48, 42, 33);
    PixelData p3 = new PixelData(255, 196, 174, 136);

    PixelData p4 = new PixelData(255, 100, 88, 69);
    PixelData p5 = new PixelData(255, 255, 255, 205);
    PixelData p6 = new PixelData(255, 148, 130, 102);

    PixelData p7 = new PixelData(255, 244, 216, 169);
    PixelData p8 = new PixelData(255, 255, 255, 238);
    PixelData p9 = new PixelData(255, 165, 146, 114);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    assertFalse(samePixelData(original, expected));
    assertFalse(samePixelData(original, newSqr));

    assertTrue(samePixelData(newSqr, expected));
  }

  /**
   * Tests that Greyscale instantiates when given valid arguments.
   */
  @Test
  public void testGSInitializes() {
    boolean pass = true;
    try {
      new Transform("e5gryjntae", "ntyjgah", "greyscale");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that greyscale instantiation fails when given invalid arguments.
   */
  @Test
  public void testGSInstantiationFails() {
    try {
      new Transform(null, "yjnyrsrt", "greyscale");
      new Transform("wsthbte", null, "greyscale");
      new Transform(null, null, "greyscale");
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Tests that the greyscale command works as intended.
   */
  @Test
  public void testGrayScale() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "greyscale threeByThree threeByThreeGrey ");

    PixelData[][] original = model.getImageCopy("threeByThree");
    PixelData[][] grey = model.getImageCopy("threeByThreeGrey");

    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 18, 18, 18);
    PixelData p3 = new PixelData(255, 182, 182, 182);

    PixelData p4 = new PixelData(255, 54, 54, 54);
    PixelData p5 = new PixelData(255, 236, 236, 236);
    PixelData p6 = new PixelData(255, 72, 72, 72);

    PixelData p7 = new PixelData(255, 200, 200, 200);
    PixelData p8 = new PixelData(255, 254, 254, 254);
    PixelData p9 = new PixelData(255, 121, 121, 121);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    assertFalse(samePixelData(original, grey));
    assertFalse(samePixelData(original, expected));
    assertTrue(samePixelData(expected, grey));
  }


}
