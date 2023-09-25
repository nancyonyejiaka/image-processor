package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;

/**
 * This class represents a command that brightens an image.
 */
public class Brighten extends ACommand {
  private final int increment;

  /**
   * Construct a new instance of this command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public Brighten(int increment, String keyName, String newName) throws IllegalArgumentException {
    super(keyName, newName);
    this.increment = increment;
  }

  /**
   * Run this command on the specified model, and change the values of each color component of
   * every pixel based on the specified increment.
   *
   * @param model the image storage unit
   * @throws IllegalArgumentException if the specified model is null
   */
  @Override
  public void run(IModel model) {
    model.changePixelVal(this.keyName, this.newName, increment);
  }
}
