package cs3500.imageprocessing.commands;

import java.io.FileNotFoundException;
import java.util.Objects;

import cs3500.imageprocessing.model.IModel;

/**
 * Abstract Load class, representing the Command classes that load in to the program files of
 * various image types.
 */
public abstract class ALoad implements ImageProcessingCommand {
  protected final String pathName;
  protected final String keyName;

  /**
   * The abstract constructor for the ALoad abstract class.
   *
   * @param pathName The pathname the file is sourced from.
   * @param keyName  The key name under which to store the image data.
   */
  ALoad(String pathName, String keyName) {
    validate(pathName);
    validate(keyName);
    this.pathName = pathName;
    this.keyName = keyName;
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
   * Abstract method run, to be implemented by all extending classes.
   *
   * @param model the image storage unit that images will be retrieved from and stored to
   * @throws FileNotFoundException if the path given is invalid or the file is not found.
   */
  public abstract void run(IModel model) throws FileNotFoundException;

}
