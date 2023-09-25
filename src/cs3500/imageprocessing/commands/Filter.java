package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;

/**
 * Class for Commands which filter over pixels and use neighboring pixel data to inform changes.
 */
public class Filter extends AFilter {

  /**
   * Construct a new instance of this abstract command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public Filter(String keyName, String newName, String type) throws IllegalArgumentException {
    super(keyName, newName, type);
  }

  /**
   * Run this command on the specified model, and change the pixel values based on the
   * color component specified by the pixelComponent function object.
   *
   * @param model the image storage unit
   */
  @Override
  public void run(IModel model) {
    model.filter(this.keyName, this.newName, this.kernel);
  }
}
