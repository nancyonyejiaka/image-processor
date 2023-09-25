package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;

/**
 * A class representing the Vertical Flip command.
 */
public class VerticalFlip extends ACommand {

  /**
   * Constructor that uses the Abstract Constructor for its keyName and newName.
   *
   * @param keyName (The keyName under which the data we wish to copy and modify is stored.)
   * @param newName (The newName under which to store the modified data.)
   * @throws IllegalArgumentException if either argument is null.
   */
  public VerticalFlip(String keyName, String newName) throws IllegalArgumentException {
    super(keyName, newName);
  }

  /**
   * Run this command on the specified model, and change the pixel values based on the
   * color component specified by the pixelComponent function object.
   *
   * @param model the image storage unit
   */
  @Override
  public void run(IModel model) {
    validate(model);
    model.verticalFlip(keyName, newName);
  }
}
