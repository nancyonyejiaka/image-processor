package cs3500.imageprocessing.commands;

import java.io.FileNotFoundException;

import cs3500.imageprocessing.model.IModel;

/**
 * This interface represents commands supported by this image processing program.
 */
public interface ImageProcessingCommand {

  /**
   * Execute this image processing command on the given image source.
   *
   * @param model the image storage unit that images will be retrieved from and stored to
   * @throws FileNotFoundException    if the given pathname to a file is invalid.
   * @throws IllegalArgumentException if the model is null.
   */
  void run(IModel model) throws FileNotFoundException, IllegalArgumentException;
}
