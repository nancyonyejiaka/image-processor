import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.commands.LoadIMG;
import cs3500.imageprocessing.controller.MockAppendable;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.commands.SaveIMG;
import cs3500.imageprocessing.model.IModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

// TODO: make and test with mock appendable to make sure IOExceptions are
//  caught and illegal argument exceptions are thrown instead

/**
 * Tests {@link SaveIMG}. Tests that verify that this program properly
 * supports the save command for PPM file formats.
 */
public class SaveTest extends AbstractCommandTests {

  ImageProcessingCommand save;
  Appendable mockOut;

  /**
   * Sets up the Appendable needed to assess whether save actually appends what's expected to the
   * files it writes to.
   */
  @Before
  public void setUp() {
    this.mockOut = new StringBuilder();
  }

  /**
   * Test that the constructor for the save command properly initializes a new instance
   * of the red component command.
   */
  @Test
  public void testSaveInitializes() {
    boolean pass = true;
    try {
      new SaveIMG("oijfjnjfbkjf", "tgreflinhy", new StringBuilder());
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that the Save object fails instantiating if given invalid arguments.
   */
  @Test
  public void testSaveInitializationFails() {
    try {
      new SaveIMG(null, "brfjhberfjhwf", new StringBuilder());
      new SaveIMG("jwhrjwkjrbglike", null, new StringBuilder());
      new SaveIMG("yebrjbejrbf", "brfjhberfjhwf", null);
      new SaveIMG(null, null, null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Tests via adding to an Appendable the contents being written to the file that the Save
   * command writes the correct contents to the file.
   */
  @Test
  public void testSave() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree " +
            "brighten 500 threeByThree threeByThreeBrighter ");
    this.save = new SaveIMG("src/cs3500/imageprocessing/res/SquareBright.ppm",
            "threeByThreeBrighter", mockOut);

    try {
      save.run(this.model);
    } catch (FileNotFoundException ignored) {

    }

    assertEquals("P3\n" + "3\n" + "3\n" + "255\n" +
            " 255 255 255  255 255 255  255 255 255 \n" +
            " 255 255 255  255 255 255  255 255 255 \n" +
            " 255 255 255  255 255 255  255 255 255 \n", this.mockOut.toString());
  }


  /**
   * Tests that the Save command writes a file not found message when the path is invalid.
   */
  @Test
  public void testFileNotFound() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree");
    ImageProcessingCommand save
            = new SaveIMG("x/y/z", "threeByThree", this.mockOut);

    try {
      save.run(this.model);
    } catch (FileNotFoundException ignored) {

    }
    assertEquals(" (0, 0, 0)  (0, 0, 255)  (0, 255, 0) \n" +
            " (255, 0, 0)  (255, 255, 0)  (255, 0, 255) \n" +
            " (0, 255, 255)  (255, 255, 255)  (123, 123, 123) \n", this.mockOut.toString());

  }

  /**
   * Test that the appendToOut method properly catches IOExceptions and
   * throws IllegalStateExceptions instead.
   */
  @Test(expected = IllegalStateException.class)
  public void testSaveFailingAppendable() {
    IModel dummyModel = new IMGModel();
    ImageProcessingCommand load = new LoadIMG("src/cs3500/imageprocessing/res/Square.ppm",
            "threeByThree");
    try {
      load.run(dummyModel);
    } catch (FileNotFoundException ignored) {

    }
    Appendable fakeOut = new MockAppendable();
    ImageProcessingCommand fakeSave = new SaveIMG("src/cs3500/imageprocessing/res/Square.ppm",
            "threeByThree", fakeOut);
    try {
      fakeSave.run(dummyModel);
    } catch (FileNotFoundException ignored) {

    }
  }

  /**
   * Test that this program can properly load and read jpg files.
   */
  @Test
  public void testSaveJPG() {
    String path = "src/cs3500/imageprocessing/res/new-small-sammie-bright.jpg";
    Path filePath = Paths.get(path);
    assertFalse(Files.exists(filePath));

    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.jpg sammie "
            + "brighten 10 sammie bright-sammie "
            + "save src/cs3500/imageprocessing/res/new-small-sammie-bright.jpg bright-sammie");

    File f = new File(path);
    assertTrue(Files.exists(filePath));
    assertTrue(f.delete());
    assertFalse(Files.exists(filePath));
  }

  /**
   * Test that this program can properly load and read png files.
   */
  @Test
  public void testSavePNG() {
    String path = "src/cs3500/imageprocessing/res/new-small-sammie-bright.png";
    Path filePath = Paths.get(path);
    assertFalse(Files.exists(filePath));

    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.png burg-child "
            + "brighten 10 burg-child bright-child "
            + "save src/cs3500/imageprocessing/res/new-small-sammie-bright.png bright-child");

    File f = new File(path);
    assertTrue(Files.exists(filePath));
    assertTrue(f.delete());
    assertFalse(Files.exists(filePath));
  }

  /**
   * Test that this program can properly load and read bmp files.
   */
  @Test
  public void testSaveBPM() {
    String path = "src/cs3500/imageprocessing/res/new-small-sammie-bright.bmp";
    Path filePath = Paths.get(path);
    assertFalse(Files.exists(filePath));

    setUp("load src/cs3500/imageprocessing/res/new-small-sammie.bmp field "
            + "brighten 10 field bright-field "
            + "save src/cs3500/imageprocessing/res/new-small-sammie-bright.bmp bright-field");

    File f = new File(path);
    assertTrue(Files.exists(filePath));
    assertTrue(f.delete());
    assertFalse(Files.exists(filePath));
  }
}
