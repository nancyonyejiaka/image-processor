package cs3500.imageprocessing.commands;

import cs3500.imageprocessing.model.IModel;

/**
 * The command class commands that apply a kernel, but do not rely on neighboring data.
 */
public class Transform extends AFilter {

  /**
   * Construct a new instance of this abstract command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public Transform(String keyName, String newName, String type) throws IllegalArgumentException {
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
    System.out.print("this transform was attempted");
    model.transform(this.keyName, this.newName, this.kernel);
    System.out.print("this transform was executed");


  }
}
