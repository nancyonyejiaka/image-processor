package cs3500.imageprocessing.commands;

import java.io.IOException;
import java.util.Objects;

import cs3500.imageprocessing.model.IModel;

/**
 * Abstract save class, represents the action of saving to a file the data stored at a key.
 */
public abstract class ASave implements ImageProcessingCommand {
  protected final String pathName;
  protected final String keyName;
  protected final Appendable out;

  /**
   * Creates a new instance of a save command using the given file path, name, and appendable.
   *
   * @param pathName the file path being saved to
   * @param keyName  the key the data to be saved is stored under
   * @param out      an output log for testing
   */
  ASave(String pathName, String keyName, Appendable out) {
    validate(pathName, keyName, out);
    this.pathName = pathName;
    this.keyName = keyName;
    this.out = out;
  }


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


  /**
   * Append a String to the output.
   *
   * @param toAppend the String being appended to the output
   * @throws IllegalArgumentException if appending to the output fails
   */
  public void appendToOut(String toAppend) throws IllegalArgumentException {
    try {
      this.out.append(toAppend);
    } catch (IOException e) {
      throw new IllegalStateException("\nUh oh! Something went wrong! Appending to "
              + "output failed!\n");
    }
  }

  /**
   * Abstract method run to be implemented by the extending classes.
   *
   * @param model the image storage unit that images will be retrieved from and stored to
   */
  public abstract void run(IModel model);
}
