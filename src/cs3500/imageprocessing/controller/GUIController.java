package cs3500.imageprocessing.controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
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
import cs3500.imageprocessing.view.IView;
import cs3500.imageprocessing.view.PixelHistogram;

/**
 * Controller for the GUI, connects with View using the addFeatures method to connect low level
 * user input to higher level program commands.
 */
public class GUIController implements Features {
  private final IModel model;
  private final IView view;
  private final Map<String, Function<String, ImageProcessingCommand>> buttonActionMap;
  private final Map<String, Supplier<BufferedImage>> histogramDataMap;
  private String filePath;
  private String keyName;

  /**
   * Constructor for the GUI Controller.
   *
   * @param model The model being passed in.
   * @throws IllegalArgumentException if the constructor arguments are null.
   */
  public GUIController(IModel model, IView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model cannot be null");
    } else {
      this.model = model;
      this.view = view;

      this.buttonActionMap = new HashMap<>();
      initialButtonActionMap();

      this.histogramDataMap = new HashMap<>();
      initialHistogramDataMap();

      this.view.addFeatures(this);
    }
  }

  /**
   * Initialize the ButtonMap, where the key is the String received by the View, and the Value
   * is the Lambda constructing the necessary object.
   */
  private void initialButtonActionMap() {
    this.buttonActionMap.put("horizontal", (String s) -> new HorizontalFlip(s, s));
    this.buttonActionMap.put("vertical", (String s) -> new VerticalFlip(s, s));

    this.buttonActionMap.put("redComp", (String s) -> new Transform(s, s, "red"));
    this.buttonActionMap.put("greenComp", (String s) -> new Transform(s, s, "green"));
    this.buttonActionMap.put("blueComp", (String s) -> new Transform(s, s, "blue"));
    this.buttonActionMap.put("luma", (String s) -> new Transform(s, s, "luma"));
    this.buttonActionMap.put("intensity", (String s) -> new Transform(s, s, "intensity"));
    this.buttonActionMap.put("value", (String s) -> new ValueComponent(s, s));

    this.buttonActionMap.put("sepia", (String s) -> new Transform(s, s, "sepia"));
    this.buttonActionMap.put("greyScale", (String s) -> new Transform(s, s, "greyscale"));

    this.buttonActionMap.put("blur", (String s) -> new Filter(s, s, "blur"));
    this.buttonActionMap.put("sharpen", (String s) -> new Filter(s, s, "sharpen"));

    this.buttonActionMap.put("brighten", (String s) ->
            new Brighten(this.view.getIncrementInt(), s, s));
    this.buttonActionMap.put("darken", (String s) ->
            new Brighten(-1 * this.view.getIncrementInt(), s, s));

    this.buttonActionMap.put("downscale", (String s) ->
            new DownScale(this.view.getDownsizeWidth(), this.view.getDownsizeHeight(), s, s));
  }

  /**
   * Build the map for Histogram buttons, to be called on when displaying the data.
   */
  private void initialHistogramDataMap() {
    this.histogramDataMap.put("redData", () -> this.getHistData().drawRed());
    this.histogramDataMap.put("blueData", () -> this.getHistData().drawBlue());
    this.histogramDataMap.put("greenData", () -> this.getHistData().drawGreen());
    this.histogramDataMap.put("intensityData", () -> this.getHistData().drawIntensity());
    this.histogramDataMap.put("allData", () -> this.getHistData().drawAll());

  }

  /**
   * Sets the file path of the Controller.
   *
   * @param filePath The path that's being stored in the Controller.
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Loads an image at the file path to the program.
   *
   * @param filePath The filepath from which to pull the image.
   */
  @Override
  public void loadIMG(String filePath) {
    this.filePath = filePath;
    this.keyName = filePath;
    try {
      new LoadIMG(filePath, keyName).run(model);
      this.view.updateMainImage(model.generateIMG(this.filePath, new StringBuilder()));

      // if the Histogram button has been previously clicked by a user with one image, when loading
      // another in the Histogram should update.
      try {
        this.setHist(this.view.getHistFocus());
      } catch (NullPointerException e) {
        // do nothing
      }


      // if this is reached, it means the load was attempted with an unsupported image type.  This
      // error can only be reached by renaming an image to one of the supported formats without
      // actually exporting it, meaning the file pane lets you select it but cannot read it.
    } catch (NullPointerException n) {
      this.view.showWarning("Unsupported Image Type",
              "This program does not supported images of that extension. Please try again"
                      + " using a JPG, PNG, BMP, or PPM file.");
    }
  }

  /**
   * Saves image data to the computer.
   *
   * @param filePath The file path being saved to.
   */
  @Override
  public void saveIMG(String filePath) {
    try {
      this.filePath = filePath;
      new SaveIMG(this.filePath, this.keyName, new StringBuilder()).run(model);
    } catch (IllegalArgumentException e) {
      this.view.showWarning("No Image Imported",
              "Please load an image before attempting to save changes.");
    }
  }

  /**
   * Applies the command from the command map to the image stored at the key name.
   *
   * @param cmd A String corresponding to a command in the Controller.
   */
  @Override
  public void apply(String cmd) {
    try {
      Function<String, ImageProcessingCommand> command =
              this.buttonActionMap.getOrDefault(cmd, null);
      ImageProcessingCommand c;

      try {
        c = command.apply(this.keyName);
        c.run(model);
        this.view.updateMainImage(model.generateIMG(this.keyName, new StringBuilder()));
        this.setHist(this.view.getHistFocus());

      } catch (FileNotFoundException e) {
        this.view.showWarning("File Not Found",
                "Please try to load another image.");
      }

    } catch (IllegalArgumentException i) {
      this.view.showWarning("No Image Imported",
              "Please load an image before attempting to make edits.");
    }
  }

  /**
   * Instantiate a new instance of the PixelHistogram class, building the information necessary for
   * the drawing of the Histogram.
   *
   * @return an instance of the PixelHistogram object.
   */
  private PixelHistogram getHistData() {
    System.out.print("Getting hist data from model with keyname: " + keyName);
    return new PixelHistogram(model.generateHistogramData(keyName));
  }

  /**
   * Set the Histogram ImageIcon in the view with the type of Histogram specified.
   *
   * @param type A String associated with the type of the Histogram.
   */
  public void setHist(String type) {
    try {
      this.view.setHist(this.histogramDataMap.get(type).get());
    } catch (IllegalArgumentException e) {
      this.view.showWarning("No Image Imported",
              "Please load an image before attempting to view pixel data frequencies.");
    }
  }
}