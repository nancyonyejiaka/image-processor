import org.junit.Test;

import java.io.File;

import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertTrue;

/**
 * Tests that verify that this program properly supports file format conversion via the
 * load and save commands.  Note that the JPG conversions cannot be compared for equality, and
 * therefore we prove only that the two files exist after saving. See LoadTest -> testJPGSlightLoss
 * to see how averages can be taken of the pixel data to check their difference is below a margin.
 */
public class FormatConversionTest extends AbstractCommandTests {

  /**
   * Test that the program supports loading a ppm then saving that ppm as a png.
   */
  @Test
  public void testPPMtoPNG() {
    setUp("load src/cs3500/imageprocessing/res/2x2square.ppm squareppm " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.png squareppm " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.png squarepng");

    assertTrue(samePixelData(model.getImageCopy("squareppm"),
            model.getImageCopy("squarepng")));
  }

  /**
   * Test that the program supports loading a ppm then saving that ppm as a jpg.
   */
  @Test
  public void testPPMtoJPG() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.ppm sammie " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.jpg sammie " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.jpg sammiejpg");

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.jpg");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a ppm then saving that ppm as a bmp.
   */
  @Test
  public void testPPMtoBMP() {
    setUp("load src/cs3500/imageprocessing/res/2x2square.ppm squareppm " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.bmp squareppm " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.bmp squarebmp");

    assertTrue(samePixelData(model.getImageCopy("squareppm"),
            model.getImageCopy("squarebmp")));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.bmp");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a bmp then saving that bmp as a ppm.
   */
  @Test
  public void testBMPtoPPM() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp sammiebmp " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.ppm sammiebmp " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.ppm samppm");

    PixelData[][] sammieppm = this.model.getImageCopy("samppm");
    PixelData[][] newSammieBMP = this.model.getImageCopy("sammiebmp");

    assertTrue(samePixelData(sammieppm, newSammieBMP));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.ppm");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a bmp then saving that bmp as a png.
   */
  @Test
  public void testBMPtoPNG() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp sammiebmp " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.png sammiebmp " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.png sampng");

    PixelData[][] sampng = this.model.getImageCopy("sampng");
    PixelData[][] newSammieBMP = this.model.getImageCopy("sammiebmp");

    assertTrue(samePixelData(sampng, newSammieBMP));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.png");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a bmp then saving that bmp as a jpg.
   */
  @Test
  public void testBMPtoJPG() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp sammiebmp " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.jpg sammiebmp " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.jpg samjpg");

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.bmp");
    assertTrue(file.canRead());

    File file2 = new File("src/cs3500/imageprocessing/res/new-small-sammie.jpg");
    assertTrue(file2.canRead());
  }

  /**
   * Test that the program supports loading a png then saving that png as a bmp.
   */
  @Test
  public void testPNGtoBMP() {
    setUp("load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.bmp sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.bmp sammiebmp");


    PixelData[][] originalSammie = this.model.getImageCopy("sandwich");
    PixelData[][] newSammieBMP = this.model.getImageCopy("sammiebmp");


    assertTrue(samePixelData(originalSammie, newSammieBMP));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.bmp");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a png then saving that png as a ppm.
   */
  @Test
  public void testPNGtoPPM() {
    setUp("load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.ppm sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.ppm sammieppm");


    PixelData[][] originalSammie = this.model.getImageCopy("sandwich");
    PixelData[][] newSammiePPM = this.model.getImageCopy("sammieppm");


    assertTrue(samePixelData(originalSammie, newSammiePPM));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.ppm");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a png then saving that png as a jpg.
   */
  @Test
  public void testPNGtoJPG() {
    setUp("load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.jpg sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.jpg sammiejpg");

    // cannot assert sameness ofJPG

    File file = new File("src/cs3500/imageprocessing/res/small-sammie-submit.png");
    assertTrue(file.canRead());

    File file2 = new File("src/cs3500/imageprocessing/res/new-small-sammie.jpg");
    assertTrue(file2.canRead());
  }

  /**
   * Test that the program supports loading a jpg then saving that jpg as a ppm.
   */
  @Test
  public void testJPGtoPPM() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.jpg sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.ppm sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.ppm sammieppm");


    PixelData[][] originalSammie = this.model.getImageCopy("sandwich");
    PixelData[][] newSammiePPM = this.model.getImageCopy("sammieppm");


    assertTrue(samePixelData(originalSammie, newSammiePPM));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.ppm");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a jpg then saving that jpg as a png.
   */
  @Test
  public void testJPGtoPNG() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.jpg sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.png sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.png sammiepng");


    PixelData[][] originalSammie = this.model.getImageCopy("sandwich");
    PixelData[][] newSammiePNG = this.model.getImageCopy("sammiepng");


    assertTrue(samePixelData(originalSammie, newSammiePNG));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.png");
    assertTrue(file.canRead());
  }

  /**
   * Test that the program supports loading a jpg then saving that jpg as a bmp.
   */
  @Test
  public void testJPGtoBMP() {
    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.jpg sandwich " +
            "save src/cs3500/imageprocessing/res/new-small-sammie.bmp sandwich " +
            "load src/cs3500/imageprocessing/res/new-small-sammie.bmp sammiebmp");

    PixelData[][] originalSammie = this.model.getImageCopy("sandwich");
    PixelData[][] newSammieBMP = this.model.getImageCopy("sammiebmp");


    assertTrue(samePixelData(originalSammie, newSammieBMP));

    File file = new File("src/cs3500/imageprocessing/res/new-small-sammie.bmp");
    assertTrue(file.canRead());
  }
}
