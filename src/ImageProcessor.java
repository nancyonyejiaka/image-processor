import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs3500.imageprocessing.controller.Controller;
import cs3500.imageprocessing.controller.GUIController;
import cs3500.imageprocessing.controller.IController;
import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.view.IMEView;

/**
 * Main method for running the program with active user input.
 */
public class ImageProcessor {

  /**
   * The main method that reads input arguments.
   *
   * @param args (The inputs being read by the program.)
   */
  public static void main(String[] args) {
    IModel model = new IMGModel();
    IController controller = new Controller(model, new InputStreamReader(System.in), System.out);

    if (args.length == 1 && args[0].equals("-text")) {
      controller.run();
    }

    if (args.length != 0) {
      if (args[0].equals("-file")) {
        try {
          File file = new File(args[1]);
          InputStream fileIS = new FileInputStream(file);
          controller = new Controller(model, new InputStreamReader(fileIS), System.out);
          controller.run();
        } catch (FileNotFoundException ignored) {
          System.out.print("file not found");
        }
      }
    } else {
      new GUIController(model, new IMEView());
    }
  }
}