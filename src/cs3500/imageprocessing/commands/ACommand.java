package cs3500.imageprocessing.commands;

import java.util.Objects;

import cs3500.imageprocessing.model.IModel;

/**
 * This class represents an abstract class for image processing commands.
 */
public abstract class ACommand implements ImageProcessingCommand {
  protected final String keyName; // key for hashmap
  protected final String newName; // place we're saving to

  /**
   * Construct a new instance of this abstract command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public ACommand(String keyName, String newName) throws IllegalArgumentException {
    validate(keyName, newName);
    this.keyName = keyName;
    this.newName = newName;
  }

  /**
   * Run this command on the specified model, and change the pixel values based on the
   * color component specified by the pixelComponent function object.
   *
   * @param model the image storage unit
   */
  public abstract void run(IModel model);

  /**
   * Verifies that object(s) given to the method are not null.
   *
   * @param params the parameters being validated.
   */
  public void validate(Object... params) {
    for (Object o : params) {
      try {
        Objects.requireNonNull(o);
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("Arguments cannot be null!");
      }
    }
  }
}
