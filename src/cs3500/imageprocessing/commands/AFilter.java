package cs3500.imageprocessing.commands;

import java.util.HashMap;
import java.util.Map;

import cs3500.imageprocessing.model.IModel;

/**
 * Abstract class representing all the classes that filter using a kernel.
 */
public abstract class AFilter extends ACommand {
  protected double[][] kernel;

  /**
   * Construct a new instance of this abstract command class.
   *
   * @param keyName the key for the file in the storage hashmap
   * @param newName the new name for the file for after the command has been applied
   * @throws IllegalArgumentException if keyName or newName are null
   */
  public AFilter(String keyName, String newName, String type) throws IllegalArgumentException {
    super(keyName, newName);
    validate(type);
    setKernel(type);
  }

  /**
   * Set the kernel based on the type of object being instantiated.
   */
  private void setKernel(String type) {
    Map<String, double[][]> filterMap = new HashMap<>();

    filterMap.put("blur",
            new double[][]{{.0625, .125, .0625}, {.125, .25, .125}, {.0625, .125, .0625}});
    filterMap.put("sepia",
            new double[][]{{.393, .769, 0.189}, {.349, .686, .168}, {.272, .534, .131}});
    filterMap.put("sharpen",
            new double[][]{{-.125, -.125, -.125, -.125, -.125}, {-.125, .25, .25, .25, -.125},
                {-.125, .25, 1.0, .25, -.125}, {-.125, .25, .25, .25, -.125},
                {-.125, -.125, -.125, -.125, -.125}});
    filterMap.put("greyscale",
            new double[][]{{.2126, .7152, .0722}, {.2126, .7152, .0722}, {.2126, .7152, .0722}});
    filterMap.put("intensity",
            new double[][]{{.3333, .3333, .3333}, {.3333, .3333, .3333}, {.3333, .3333, .3333}});
    filterMap.put("luma", filterMap.get("greyscale"));
    filterMap.put("blue",
            new double[][]{{0.0, 0.0, 1.0}, {0.0, 0.0, 1.0}, {0.0, 0.0, 1.0}});
    filterMap.put("green",
            new double[][]{{0.0, 1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 1.0, 0.0}});
    filterMap.put("red",
            new double[][]{{1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}});

    kernel = filterMap.get(type);
  }

  /**
   * Run this command on the specified model, and change the pixel values based on the
   * color component specified by the pixelComponent function object.
   *
   * @param model the image storage unit
   */
  @Override
  public abstract void run(IModel model);
}
