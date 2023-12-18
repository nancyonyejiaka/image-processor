package cs3500.imageprocessing.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;


import cs3500.imageprocessing.controller.Features;

/**
 * Mock View class for testing.  Used to assess that buttons are clicked as expected, and that
 * modifications are subsequently performed.
 */
public class MockIMEView extends JFrame implements IView {
  public final Appendable out;
  public final JButton vertical;
  public JButton brighten;
  private JTextField input;


  /**
   * Construct a new instance of a JTextField.
   *
   * @param out      the appendable to log transmissions to this view
   * @param vertical the button to trigger a vertical flip
   * @param input    the input text field for brighten
   * @throws IllegalArgumentException if the arguments are null
   */
  public MockIMEView(Appendable out, JButton vertical, JButton brighten, JTextField input)
          throws IllegalArgumentException {
    if (out == null || vertical == null || input == null || brighten == null) {
      throw new IllegalArgumentException("args cannot be null!!");
    }
    this.out = out;
    this.vertical = vertical;
    this.brighten = brighten;
    this.input = input;
  }

  /**
   * Get the int from the text field and return it.  Used for testing, where we knowingly input
   * only valid integers.
   */
  @Override
  public int getIncrementInt() {
    return Integer.parseInt(this.input.getText());
  }

  /**
   * Set the int from the text field.  Used to mock a user inputting text through a keyboard.
   */
  public void setIncrementInt(String increment) {
    this.input.setText(increment);
  }

  /**
   * Clear the text field. Note that a more general "setInputString" would work for this purpose but
   * would be incorrect. This is because the text field is not set programmatically in general but
   * input by the user.
   */
  @Override
  public void clearIncrementInt() {
    // do nothing, this is a mock View
  }

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  @Override
  public void resetFocus() {
    // do nothing, this is a mock View
  }

  /**
   * Connects the buttons in the GUI to available feature methods from the Features object.
   *
   * @param features The Features object containing the desired methods.
   */
  @Override
  public void addFeatures(Features features) {
    this.vertical.addActionListener(evt -> {
      try {
        out.append("vertical flip is pressed. ");
      } catch (IOException ignored) {
      }
      features.apply("vertical");
    });
    this.brighten.addActionListener(evt -> {
      try {
        out.append("brighten is pressed. ");
      } catch (IOException ignored) {
      }
      features.apply("brighten");
    });
  }

  /**
   * Update the main image being displayed by the GUI.
   *
   * @param img The BufferedImage we wish to display on the screen.
   */
  @Override
  public void updateMainImage(BufferedImage img) {
    // do nothing, this is a mock View
  }

  /**
   * Sets the Histogram image currently being displayed by the GUI.
   *
   * @param img The BufferedImage of the Histogram we wish to display.
   */
  @Override
  public void setHist(BufferedImage img) {
    // do nothing, this is a mock View
  }

  /**
   * Get the Histogram currently displayed by the screen, to update it correctly upon image
   * modification.
   *
   * @return A String representing the currently viewed Histogram.
   */
  @Override
  public String getHistFocus() {
    return "allData";
  }

  /**
   * Display a pop-up warning to the user.
   *
   * @param title   The title of the pop-up box warning.
   * @param message The message contained within the pop-up box.
   */
  @Override
  public void showWarning(String title, String message) {
    // do nothing, this is a mock View
  }
}
