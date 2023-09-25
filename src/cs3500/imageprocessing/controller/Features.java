package cs3500.imageprocessing.controller;

/**
 * Interface for the public facing GUI Features.  This is the interface that will connect with
 * the View for developing functionality between the two.
 */
public interface Features {

  /**
   * Command that triggers the loading of an image into the program.
   * @param filePath The filepath from which to pull the image.
   */
  void loadIMG(String filePath);

  /**
   * Command that triggers the saving of an image to the operating device.
   * @param filePath The file path being saved to.
   */
  void saveIMG(String filePath);

  /**
   * Sets the stored file path of the Controller to the given String.
   * @param filePath The given file path.
   */
  void setFilePath(String filePath);

  /**
   * Applies the command associated with the input String.
   * @param cmd A String corresponding to a command in the Controller.
   */
  void apply(String cmd);

  /**
   * Sets the Histogram displayed to the GUI based on the String passed into it.
   * @param type A String associated with the type of the Histogram.
   */
  void setHist(String type);
}