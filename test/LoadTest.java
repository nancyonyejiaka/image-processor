import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.commands.LoadIMG;
import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Test {@link LoadIMG}. Tests that verify that this program properly supports the load command
 * for the PPM file format.
 */
public class LoadTest extends AbstractCommandTests {

  /**
   * Tests that Load command instantiation succeeds when given valid arguments.
   */
  @Test
  public void testLoadInstantiation() {
    boolean pass = true;
    try {
      new LoadIMG("dkasjf;dlkj", "dalksjf");
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that a Load command object fails instantiation when given invalid arguments.
   */
  @Test
  public void testLoadInstantiationFails() {
    try {
      new LoadIMG(null,"fewfuchbwjrgu");
      new LoadIMG("qaeoigjneij",null);
      new LoadIMG(null,null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Tests that a file does not exist within the storage map before loading, while afterward it
   * does.  It also tests that the image loaded into the map is identical to what's expected.
   */
  @Test
  public void testLoad() {
    setUp("");
    assertFalse(this.model.hasImage("threeByThree"));
    ImageProcessingCommand load =
            new LoadIMG("src/cs3500/imageprocessing/res/Square.ppm",
                    "threeByThree");
    try {
      load.run(this.model);
    } catch (FileNotFoundException ignored) {

    }
    assertTrue(this.model.hasImage("threeByThree"));
    assertTrue(samePixelData(this.model.getImageCopy("threeByThree"), pixel3x3));


    PixelData p1 = new PixelData(255,0,0,0);
    PixelData p2 = new PixelData(255,0,0,255);
    PixelData p3 = new PixelData(255,0,255,0);

    PixelData p4 = new PixelData(255,255,0,0);
    PixelData p5 = new PixelData(255,255,255,0);
    PixelData p6 = new PixelData(255,255,0,255);

    PixelData p7 = new PixelData(255,0,255,255);
    PixelData p8 = new PixelData(255,255,255,255);
    PixelData p9 = new PixelData(255,123,123,123);

    PixelData[] row1 = new PixelData[]{p1, p2, p3};
    PixelData[] row2 = new PixelData[]{p4, p5, p6};
    PixelData[] row3 = new PixelData[]{p7, p8, p9};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3};

    assertTrue(samePixelData(expected, this.model.getImageCopy("threeByThree")));
  }

  /**
   * Testing an image is the same after loading it in, saving it unchanged, then loading the new
   * file in again.
   */
  @Test
  public void testLoadSaveLoad() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm firstLoad " +
            "save src/cs3500/imageprocessing/res/firstLoad.ppm firstLoad " +
            "load src/cs3500/imageprocessing/res/firstLoad.ppm secondLoad");

    assertTrue(this.model.hasImage("firstLoad"));
    assertTrue(this.model.hasImage("secondLoad"));

    assertTrue(samePixelData(this.model.getImageCopy("firstLoad"),
            this.model.getImageCopy("secondLoad")));
  }

  /**
   * Test that this program can properly load and read jpg files.
   */
  @Test
  public void testLoadJPG() {
    setUp("");
    assertFalse(this.model.hasImage("sammie"));
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.jpg sammie");
    assertTrue(this.model.hasImage("sammie"));
  }

  /**
   * Test that this program can properly load and read png files.
   */
  @Test
  public void testLoadPNG() {
    setUp("");
    assertFalse(this.model.hasImage("burg-child"));
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.png burg-child");
    assertTrue(this.model.hasImage("burg-child"));

    setUp("load src/cs3500/imageprocessing/res/brick-wall-small.png bricks");

    PixelData p1 = new PixelData(255, 210, 208, 213);
    PixelData p2 = new PixelData(255, 211, 209, 214);
    PixelData p3 = new PixelData(255, 214, 212, 217);
    PixelData p4 = new PixelData(255, 215, 213, 218);
    PixelData p5 = new PixelData(255, 213, 210, 217);

    PixelData p6 = new PixelData(255, 218, 208, 207);
    PixelData p7 = new PixelData(255, 200, 190, 189);
    PixelData p8 = new PixelData(255, 194, 184, 183);
    PixelData p9 = new PixelData(255, 204, 194, 194);
    PixelData p10 = new PixelData(255, 210, 200, 201);

    PixelData p11 = new PixelData(255, 132, 108, 96);
    PixelData p12 = new PixelData(255, 110, 86, 74);
    PixelData p13 = new PixelData(255, 106, 82, 70);
    PixelData p14 = new PixelData(255, 125, 101, 90);
    PixelData p15 = new PixelData(255, 140, 116, 106);

    PixelData p16 = new PixelData(255, 117, 83, 63);
    PixelData p17 = new PixelData(255, 105, 71, 51);
    PixelData p18 = new PixelData(255, 103, 69, 49);
    PixelData p19 = new PixelData(255, 114, 80, 61);
    PixelData p20 = new PixelData(255, 121, 87, 69);

    PixelData p21 = new PixelData(255, 146, 107, 83);
    PixelData p22 = new PixelData(255, 137, 98, 74);
    PixelData p23 = new PixelData(255, 130, 91, 67);
    PixelData p24 = new PixelData(255, 127, 88, 65);
    PixelData p25 = new PixelData(255, 124, 85, 62);

    PixelData[] row1 = new PixelData[]{p1, p2, p3, p4, p5};
    PixelData[] row2 = new PixelData[]{p6, p7, p8, p9, p10};
    PixelData[] row3 = new PixelData[]{p11, p12, p13, p14, p15};
    PixelData[] row4 = new PixelData[]{p16, p17, p18, p19, p20};
    PixelData[] row5 = new PixelData[]{p21, p22, p23, p24, p25};

    PixelData[][] expected = new PixelData[][]{row1, row2, row3, row4, row5};

    PixelData[][] brickLoad = model.getImageCopy("bricks");

    assertTrue(samePixelData(brickLoad, expected));
  }

  /**
   * Test that this program can properly load and read bmp files.
   */
  @Test
  public void testLoadBPM() {
    setUp("");
    assertFalse(this.model.hasImage("field"));
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp field");
    assertTrue(this.model.hasImage("field"));
  }


  /**
   * Tests the margin of error in pixel values when dealing with jpg image files, since jpg
   * files are lossy  while png and bmp files are not.
   */
  @Test
  public void testJPGSlightLoss() {
    setUp("load src/cs3500/imageprocessing/res/testmeme.jpg originalMeme " +
            "load src/cs3500/imageprocessing/res/testmeme-child.jpg newJPG");

    PixelData[][] originalJPG = this.model.getImageCopy("originalMeme");
    PixelData[][] newJPG = this.model.getImageCopy("newJPG");

    // checking that the average differnece in pixel vals for the two JPGs is less than 10.  It is
    // NOT lossless, but they're still similar.
    int sum = 0;
    int numVals = 0;
    for (int i = 0; i < originalJPG.length; i++) {
      for (int j = 0; j < originalJPG[0].length; j++) {
        numVals = numVals + 3;
        sum += Math.abs(originalJPG[i][j].getR() - newJPG[i][j].getR());
        sum += Math.abs(originalJPG[i][j].getG() - newJPG[i][j].getG());
        sum += Math.abs(originalJPG[i][j].getB() - newJPG[i][j].getB());
      }
    }
    assertTrue(sum / numVals < 4);
  }
}
