package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;

/**
 * This class represents a command that horizontally flips an image.
 */
public class HorizontalFlip extends ACommand {

  /**
   * Construct a new instance of the Horizontal Flip command.
   *
   * @param keyName the hashmap key for the image to be flipped
   * @param newName the new key to store the mutated image under
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public HorizontalFlip(String keyName, String newName) throws IllegalArgumentException {
    super(keyName, newName);
  }

  /**
   * Apply the Horizontal Flip functionality to a copy of the original photo, storing it at the
   * new key value after mutation is complete.
   *
   * @param model the image storage unit
   * @throws IllegalArgumentException when the model passed in is null.
   */
  public void run(IModel model) throws IllegalArgumentException {
    validate(model);
    model.horizontalFlip(keyName, newName);
  }
}
