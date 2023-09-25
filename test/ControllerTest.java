import org.junit.Test;

import java.io.StringReader;

import cs3500.imageprocessing.controller.Controller;
import cs3500.imageprocessing.controller.IController;
import cs3500.imageprocessing.controller.MockAppendable;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Controller}. Test that this program properly implements the use of a controller
 * to take, read and execute user inputs.
 */
public class ControllerTest extends AbstractCommandTests {

  /**
   * Tests that the controller properly instantiates with valid inputs.
   */
  @Test
  public void testControllerInstantiates() {
    setUp("load src/cs3500/imageprocessing/res/Square.ppm threeByThree");
    assertTrue(model.hasImage("threeByThree"));
  }

  /**
   * Test that the constructor for the controller fails to initialize a new instance when
   * given invalid inputs.
   */
  @Test
  public void testControllerInitializationFails() {
    try {
      new Controller(null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the constructor for the controller successfully initializes a new instance when
   * given valid inputs.
   */
  @Test
  public void testControllerInitializes() {
    boolean pass = true;
    try {
      new Controller(new IMGModel());
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Test that the controller's print methods successfully catch IOExceptions when trying to append
   * to the output appendable, and throw IllegalStateExceptions instead.
   */
  @Test(expected = IllegalStateException.class)
  public void testIOThrowsWithMock() {
    IModel fakeModel = new IMGModel();
    Readable fakeIn = new StringReader("");
    Appendable fakeOut = new MockAppendable();
    IController fakeController = new Controller(fakeModel, fakeIn, fakeOut);
    fakeController.run();
  }

  /**
   * Test that controller throws IllegalStateException instead of IOException when trying to
   * append output with an input of q.
   */
  @Test(expected = IllegalStateException.class)
  public void testIOThrowsWithMockWithQ() {
    IModel fakeModel = new IMGModel();
    Readable fakeIn = new StringReader("q");
    Appendable fakeOut = new MockAppendable();
    IController fakeController = new Controller(fakeModel, fakeIn, fakeOut);
    fakeController.run();
  }

  /**
   * Test that the controller properly prints the welcome message when the progran is initially
   * run.
   */
  @Test
  public void testWelcomePrints() {
    IModel outMod = new IMGModel();
    Readable in = new StringReader("");
    Appendable out = new StringBuilder();
    IController controllerWithOutput = new Controller(outMod, in, out);
    controllerWithOutput.run();
    assertEquals("\n\nWelcome to the image processing program!\n"
            + "Type \"m\" or \"menu\" to see the list of supported functions or "
            + "type \"q\" or \"quit\" to quit this program.\n\n"
            + "Type instruction: \n\n", out.toString());
  }

  /**
   * Tests that the controller properly prints the menu when the user enters "m" or "menu".
   */
  @Test
  public void testMenuPrints() {
    IModel outMod = new IMGModel();
    Readable in = new StringReader("menu");
    Appendable out = new StringBuilder();
    IController controllerWithOutput = new Controller(outMod, in, out);

    controllerWithOutput.run();
    assertEquals("\n\nWelcome to the image processing program!\n"
            + "Type \"m\" or \"menu\" to see the list of supported functions "
            + "or type \"q\" or \"quit\" to quit this program.\n"
            + "\nType instruction: \n"
            + "\n\n\nSUPPORTED USER FUNCTIONS INCLUDE: \n"
            + "\n\n☆ load image-path image-name \n"
            + "\n\n☆ save image-path image-name \n"
            + "\n\n☆ red-component image-name dest-image-name \n"
            + "\n\n☆ blue-component image-name dest-image-name \n"
            + "\n\n☆ green-component image-name dest-image-name \n"
            + "\n\n☆ value-component image-name dest-image-name \n"
            + "\n\n☆ luma-component image-name dest-image-name \n"
            + "\n\n☆ intensity-component image-name dest-image-name \n"
            + "\n\n☆ sepia image-name dest-image-name \n"
            + "\n\n☆ greyscale image-name dest-image-name \n"
            + "\n\n☆ blur image-name dest-image-name \n"
            + "\n\n☆ sharpen image-name dest-image-name \n"
            + "\n\n☆ menu (print supported instruction list)\n"
            + "\n\n☆ q or quit (quit the program) \n"
            + "\nType instruction: \n\n", out.toString());
  }

  /**
   * Testing that the controller handles bad inputs as expected.  Designed such that when a
   * controller receives a bad command, it ignores all inputs after the bad command.  If a given
   * argument is invalid, it displays a relevant error in that case.
   */
  @Test
  public void testControllerBadInputs() {
    IModel outMod = new IMGModel();
    Readable in = new StringReader("kdas;lfjads 12 asdfasdfasd asdfa");
    Appendable out = new StringBuilder();
    IController controllerWithOutput = new Controller(outMod, in, out);

    controllerWithOutput.run();

    // should output bad command on first input, then ignore all inputs given after (design dec.)
    assertEquals("\n" +
            "\n" +
            "Welcome to the image processing program!\n" +
            "Type \"m\" or \"menu\" to see the list of supported functions or " +
            "type \"q\" or \"quit\" to quit this program.\n" +
            "\n" +
            "Type instruction: \n" +
            "\n" +
            "\n" +
            "\n" +
            "Undefined instruction: kdas;lfjads\n" +
            "\n", out.toString());


    IModel outMod2 = new IMGModel();
    Readable in2 = new StringReader("brighten 10 asdfa dafdsaf");
    Appendable out2 = new StringBuilder();
    IController controllerWithOutput2 = new Controller(outMod2, in2, out2);

    controllerWithOutput2.run();

    assertEquals("\n" +
            "\n" +
            "Welcome to the image processing program!\n" +
            "Type \"m\" or \"menu\" to see the list of " +
            "supported functions or type \"q\" or \"quit\" to quit this program.\n" +
            "\n" +
            "Type instruction: \n" +
            "\n" +
            "\n" +
            "brighten failed: There is no image by " +
            "that name in this program! Please check that you loaded it in properly.\n" +
            "\n", out2.toString());


    IModel outMod3 = new IMGModel();
    Readable in3 = new StringReader("blur asdfasdfasd asdfa dafdsaf");
    Appendable out3 = new StringBuilder();
    IController controllerWithOutput3 = new Controller(outMod3, in3, out3);

    controllerWithOutput3.run();

    assertEquals("\n" +
            "\n" +
            "Welcome to the image processing program!\n" +
            "Type \"m\" or \"menu\" to see the list of supported functions or " +
            "type \"q\" or \"quit\" to quit this program.\n" +
            "\n" +
            "Type instruction: \n" +
            "\n" +
            "\n" +
            "blur failed: There is no image by that name in this program! Please " +
            "check that you loaded it in properly.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Undefined instruction: dafdsaf\n" +
            "\n", out3.toString());
  }


}
