package cs3500.imageprocessing.commands;

import java.util.function.Function;

import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.model.PixelData;

/**
 * A class representing the Value Component command.
 */
public class ValueComponent extends ACommand {
  Function<PixelData, Integer> pixelComponent;


  /**
   * Construct a new instance of this abstract command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public ValueComponent(String keyName, String newName)
          throws IllegalArgumentException {
    super(keyName, newName);
    this.pixelComponent = PixelData::getValue;
  }

  /**
   * Run this color command on the specified model, and change the pixel values based on the
   * color component specified by the pixelComponent function object.
   *
   * @param model the image storage unit
   * @throws IllegalArgumentException if model is null.
   */
  @Override
  public void run(IModel model) throws IllegalArgumentException {
    model.colorComponent(this.keyName, this.newName, pixelComponent);
  }
}
