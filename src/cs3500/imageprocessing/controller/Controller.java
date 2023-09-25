package cs3500.imageprocessing.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import cs3500.imageprocessing.commands.Brighten;
import cs3500.imageprocessing.commands.DownScale;
import cs3500.imageprocessing.commands.Filter;
import cs3500.imageprocessing.commands.HorizontalFlip;
import cs3500.imageprocessing.commands.ImageProcessingCommand;
import cs3500.imageprocessing.commands.LoadIMG;
import cs3500.imageprocessing.commands.SaveIMG;
import cs3500.imageprocessing.commands.Transform;
import cs3500.imageprocessing.commands.ValueComponent;
import cs3500.imageprocessing.commands.VerticalFlip;
import cs3500.imageprocessing.model.IModel;

/**
 * The class represents the controller for this image processing program. This controller class
 * reads input from the specified input source and processes the input as necessary.
 */
public class Controller implements IController {
  private final IModel model;
  private final Readable input;
  private final Appendable output;

  /**
   * Construct a new instance of this controller.
   *
   * @param model the image storage unit to manipulate based on user input
   * @throws IllegalArgumentException if the input is null.
   */
  public Controller(IModel model) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(model);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Arguments cannot be null!");
    }
    this.model = model;
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
  }

  /**
   * Construct a new instance of this controller.
   *
   * @param model the image storage unit to manipulate based on user input
   * @param in    the source of user input
   * @param out   the output source that feedback from this program should be displayed to
   * @throws IllegalArgumentException if any of arguments are null.
   */
  public Controller(IModel model, Readable in, Appendable out) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(model);
      Objects.requireNonNull(in);
      Objects.requireNonNull(out);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Arguments cannot be null!");
    }
    this.model = model;
    this.input = in;
    this.output = out;
  }

  /**
   * Run this image processing program in order to take and process user input appropriately.
   */
  public void run() {
    Scanner sc = new Scanner(input);

    Map<String, Function<Scanner, ImageProcessingCommand>> commandMap = new HashMap<>();
    initializeCommandMap(commandMap);

    welcomeMessage();

    while (sc.hasNext()) {
      String userIn = sc.next().toLowerCase();
      ImageProcessingCommand c;
      int errorFlag = 0;

      if (userIn.equalsIgnoreCase("q")
              || userIn.equalsIgnoreCase("quit")) {
        farewellMessage();
        return;
      } else if (userIn.equalsIgnoreCase("m")
              || userIn.equalsIgnoreCase("menu")) {
        writeMenu();
      } else {
        Function<Scanner, ImageProcessingCommand> cmd =
                commandMap.getOrDefault(userIn, null);
        if (cmd == null) {
          writeMessage("\nUndefined instruction: " + userIn + System.lineSeparator());
          try {
            sc.nextLine();
          } catch (NoSuchElementException nse) {
            // do nothing
          }
        } else {
          try {
            c = cmd.apply(sc);
            try {
              c.run(this.model);
            } catch (FileNotFoundException f) {
              writeMessage("Cannot find that file!\n");
              errorFlag++;
            } catch (IllegalArgumentException ia) {
              writeMessage(userIn + " failed: There is no image by that name in this program! "
                      + "Please check that you loaded it in properly.\n");
              errorFlag++;
            }
          } catch (InputMismatchException ime) {
            writeMessage("\n" + userIn + " operation successful! \n");
            errorFlag++;
          }
          if (errorFlag == 0) {
            writeMessage("\n" + userIn + " operation successful! \n");
            writeMessage("Type instruction: ");
          }
        }
      }
    }
  }

  /**
   * Populates the command map that the controller uses to verify user instructions with the
   * commands currently supported by this program.
   *
   * @param commandMap the has map of commands currently supported by this program
   */
  private static void initializeCommandMap(Map<String,
          Function<Scanner, ImageProcessingCommand>> commandMap) {
    commandMap.put("load", (Scanner s) -> new LoadIMG(s.next(), s.next()));
    commandMap.put("save", (Scanner s) -> new SaveIMG(s.next(), s.next(), new StringBuilder()));
    commandMap.put("horizontal-flip", (Scanner s) -> new HorizontalFlip(s.next(), s.next()));
    commandMap.put("vertical-flip", (Scanner s) -> new VerticalFlip(s.next(), s.next()));
    commandMap.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), s.next(), s.next()));
    commandMap.put("value-component", (Scanner s) -> new ValueComponent(s.next(), s.next()));
    commandMap.put("red-component", (Scanner s) -> new Transform(s.next(), s.next(), "red"));
    commandMap.put("green-component",
        (Scanner s) -> new Transform(s.next(), s.next(), "green"));
    commandMap.put("blue-component", (Scanner s) -> new Transform(s.next(), s.next(), "blue"));
    commandMap.put("luma-component", (Scanner s) -> new Transform(s.next(), s.next(), "luma"));
    commandMap.put("intensity-component",
        (Scanner s) -> new Transform(s.next(), s.next(), "intensity"));
    commandMap.put("sepia", (Scanner s) -> new Transform(s.next(), s.next(), "sepia"));
    commandMap.put("greyscale", (Scanner s) -> new Transform(s.next(), s.next(), "greyscale"));
    commandMap.put("blur", (Scanner s) -> new Filter(s.next(), s.next(), "blur"));
    commandMap.put("sharpen", (Scanner s) -> new Filter(s.next(), s.next(), "sharpen"));
    commandMap.put("downscale", (Scanner s) -> new DownScale(s.nextInt(), s.nextInt(),
            s.next(), s.next()));
  }

  /**
   * Render the specified message onto this controller's appendable.
   *
   * @param message the message to be rendered
   * @throws IllegalStateException if the attempt to append to the appendable fails
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {

      output.append(System.lineSeparator() + message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Print the menu for this controller, that specifies functionality and supported operations.
   *
   * @throws IllegalStateException if the attempt to append to the appendable fails
   */
  private void writeMenu() throws IllegalStateException {
    writeMessage(System.lineSeparator() + "SUPPORTED USER FUNCTIONS INCLUDE: "
            + System.lineSeparator());
    writeMessage("☆ load image-path image-name "
            + System.lineSeparator());
    writeMessage("☆ save image-path image-name "
            + System.lineSeparator());
    writeMessage("☆ red-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ blue-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ green-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ value-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ luma-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ intensity-component image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ sepia image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ greyscale image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ blur image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ sharpen image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ downscale new-width new-height image-name dest-image-name "
            + System.lineSeparator());
    writeMessage("☆ menu (print supported instruction list)"
            + System.lineSeparator());
    writeMessage("☆ q or quit (quit the program) "
            + System.lineSeparator()
            + System.lineSeparator()
            + "Type instruction: "
            + System.lineSeparator());
  }

  /**
   * Render a welcome message to the user when the controller if first used and dispatched.
   *
   * @throws IllegalStateException if the attempt to append to the appendable fails
   */
  private void welcomeMessage() throws IllegalStateException {
    writeMessage(System.lineSeparator() + "Welcome to the image processing program!"
            + System.lineSeparator()
            + "Type \"m\" or \"menu\" to see the list of supported functions or "
            + "type \"q\" or \"quit\" to quit this program."
            + System.lineSeparator()
            + System.lineSeparator()
            + "Type instruction: "
            + System.lineSeparator());
  }

  /**
   * Render a goodbye message to the user when the user quits the controller.
   *
   * @throws IllegalStateException if the attempt to append to the appendable fails
   */
  private void farewellMessage() throws IllegalStateException {
    writeMessage(System.lineSeparator()
            + "Thank you for using this image processing program! "
            + System.lineSeparator()
            + System.lineSeparator());
  }
}