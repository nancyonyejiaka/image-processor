import org.junit.Before;
import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JTextField;

import cs3500.imageprocessing.controller.Features;
import cs3500.imageprocessing.controller.GUIController;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;
import cs3500.imageprocessing.view.IView;
import cs3500.imageprocessing.view.MockIMEView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Represent a class for testing the controller for the GUI for this image processing program.
 */
public class UserInputTest {
  Features controller;
  IModel model;
  IView mockView;
  Appendable log;
  JButton vertical;
  JButton brighten;
  JTextField input;

  /**
   * Initializes an instance of a controller for all tests.
   */
  @Before
  public void setup() {
    this.model = new IMGModel();
    this.log = new StringBuilder();

    this.vertical = new JButton("vertical flip");
    this.vertical.setActionCommand("vertical");

    this.brighten = new JButton("brighten");
    this.input = new JTextField(10);

    this.mockView = new MockIMEView(this.log, this.vertical, this.brighten, this.input);
    this.controller = new GUIController(this.model, this.mockView);

  }

  /**
   * Test that the controller properly responds to the user's input when the horizontal flip
   * button is pressed.
   */
  @Test
  public void testHorizontalFlipButton() {
    // original pixel data before user input
    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p1};
    PixelData[] row2 = new PixelData[]{p1, p1, p2};
    PixelData[] row3 = new PixelData[]{p2, p2, p3};

    PixelData[][] data = new PixelData[][]{row1, row2, row3};

    // the pixel data after user input
    PixelData[] flipRow1 = new PixelData[]{p2, p2, p3};
    PixelData[] flipRow2 = new PixelData[]{p1, p1, p2};
    PixelData[] flipRow3 = new PixelData[]{p1, p2, p1};

    PixelData[][] dataFlipped = new PixelData[][]{flipRow1, flipRow2, flipRow3};


    // add the image data to the model, then
    this.model.addImage("testIMG", data);
    assertEquals("", this.log.toString());
    assertArrayEquals(data, this.model.getImageCopy("testIMG"));
    assertNotEquals(dataFlipped, this.model.getImageCopy("testIMG"));

    // manually click the button
    this.controller.setFilePath("testIMG");
    this.vertical.doClick();

    // check that the pixel data for the image has been
    assertEquals("vertical flip is pressed. ", this.log.toString());
    assertNotEquals(data, this.model.getImageCopy("testIMG"));
    assertArrayEquals(dataFlipped, this.model.getImageCopy("testIMG"));
  }

  /**
   * Test that the controller properly responds to the user's input when the brighten
   * button is pressed.
   */
  @Test
  public void testBrightButton() {
    // original pixel data before user input
    PixelData p1 = new PixelData(255, 0, 0, 0);
    PixelData p2 = new PixelData(255, 255, 255, 255);
    PixelData p3 = new PixelData(255, 123, 123, 123);

    PixelData[] row1 = new PixelData[]{p1, p2, p1};
    PixelData[] row2 = new PixelData[]{p1, p1, p2};
    PixelData[] row3 = new PixelData[]{p2, p2, p3};

    PixelData[][] data = new PixelData[][]{row1, row2, row3};

    // the pixel data after user input
    PixelData bp1 = new PixelData(255, 10, 10, 10);
    PixelData bp2 = new PixelData(255, 255, 255, 255);
    PixelData bp3 = new PixelData(255, 133, 133, 133);

    PixelData[] brow1 = new PixelData[]{bp1, bp2, bp1};
    PixelData[] brow2 = new PixelData[]{bp1, bp1, bp2};
    PixelData[] brow3 = new PixelData[]{bp2, bp2, bp3};

    PixelData[][] bData = new PixelData[][]{brow1, brow2, brow3};


    // add the image data to the model, then
    this.model.addImage("brightTestIMG", data);
    assertEquals("", this.log.toString());
    assertArrayEquals(data, this.model.getImageCopy("brightTestIMG"));
    assertNotEquals(bData, this.model.getImageCopy("brightTestIMG"));

    // manually click the button
    this.controller.setFilePath("brightTestIMG");
    this.input.setText("10");
    this.brighten.doClick();

    // check that the pixel data for the image has been
    assertEquals("brighten is pressed. ", this.log.toString());
    assertNotEquals(data, this.model.getImageCopy("brightTestIMG"));
    assertArrayEquals(bData, this.model.getImageCopy("brightTestIMG"));
  }
}