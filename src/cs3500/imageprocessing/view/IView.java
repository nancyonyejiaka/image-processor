package cs3500.imageprocessing.view;

import java.awt.image.BufferedImage;

import cs3500.imageprocessing.controller.Features;

/**
 * View interface representing the contents of the program's GUI.
 */
public interface IView {

  /**
   * Get the int from the text field and return it.
   */
  int getIncrementInt();

  /**
   * Get the desired width for downscaling.
   * @return width of new image.
   */
  int getDownsizeWidth();

  /**
   * Get the desired height for downscaling.
   * @return height of new image.
   */
  int getDownsizeHeight();

  /**
   * Clear the text field. Note that a more general "setInputString" would work for this purpose but
   * would be incorrect. This is because the text field is not set programmatically in general but
   * input by the user.
   */
  void clearIncrementInt();

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Connects the buttons in the GUI to available feature methods from the Features object.
   * @param features The Features object containing the desired methods.
   */
  void addFeatures(Features features);

  /**
   * Update the main image being displayed by the GUI.
   * @param img The BufferedImage we wish to display on the screen.
   */
  void updateMainImage(BufferedImage img);

  /**
   * Sets the Histogram image currently being displayed by the GUI.
   * @param img The BufferedImage of the Histogram we wish to display.
   */
  void setHist(BufferedImage img);

  /**
   * Get the Histogram currently displayed by the screen, to update it correctly upon image
   * modification.
   * @return  A String representing the currently viewed Histogram.
   */
  String getHistFocus();

  /**
   * Display a pop-up warning
   * @param title The title of the pop-up box warning.
   * @param message The message contained within the pop-up box.
   */
  void showWarning(String title, String message);

}
