package cs3500.imageprocessing.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs3500.imageprocessing.controller.Features;

/**
 * Class representing an implementation of the IView interface.  Instantiates GUI on creation.
 */
public class IMEView extends JFrame implements IView {
  private String loadFilePath;
  private String saveFilePath;
  private JPanel mainPanel;
  private JPanel imagePanel;
  private JPanel histPanel;
  private JLabel imageLabel;
  private JLabel histLabel;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JPanel commandPanel;
  private JTextField input;
  private JTextField width;
  private JTextField height;
  private JButton horizontal;
  private JButton vertical;
  private JButton redComp;
  private JButton blueComp;
  private JButton greenComp;
  private JButton lumaComp;
  private JButton intensityComp;
  private JButton valueComp;
  private JButton greyScale;
  private JButton sepia;
  private JButton blur;
  private JButton sharpen;
  private JButton brighten;
  private JButton darken;
  private JButton downscale;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton intensityData;
  private JButton redData;
  private JButton blueData;
  private JButton greenData;
  private JButton allData;
  private final int screenWidth;
  private final int screenHeight;
  private String histFocus;

  /**
   * Constructor for the IMEView Object.
   */
  public IMEView() {
    super();
    this.loadFilePath = ".";

    this.histFocus = "allData";

    // configure the dimension for the application screen
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.screenWidth = (int) screenSize.getWidth();
    this.screenHeight = (int) screenSize.getHeight();
    // 1440 x 900

    setTitle("Graphical Image Manipulation and Enhancement");
    setSize(this.screenWidth, this.screenHeight);

    configureImageEditingAndDataPanel();

    configureCommandPanel();
    configureSaveLoadPanel();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /*
      In order to make this frame respond to keyboard events, it must be within strong focus.
      Since there could be multiple components on the screen that listen to keyboard events,
      we must set one as the "currently focussed" one so that all keyboard events are
      passed to that component. This component is said to have "strong focus".

      We do this by first making the component focusable and then requesting focus to it.
      Requesting focus makes the component have focus AND removes focus from whoever had it
      before.
       */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Get the int from the text field and return it.
   */
  @Override
  public int getIncrementInt() {
    int incr = 0;
    try {
      System.out.print("\n value of local variable: " + incr + "\n");
      System.out.print("\n value of attempted parse: " + input.getText() + "\n");

      incr = Integer.parseInt(this.input.getText());
    } catch (NumberFormatException e) {
      this.showWarning("Invalid Increment", "Please enter an integer in "
              + "this text box.");
    }
    clearIncrementInt();
    resetFocus();
    System.out.print("\n value of local variable after parse: " + incr + "\n");
    return incr;
  }

  @Override
  public int getDownsizeWidth() {
    int incr = 0;
    try {
      incr = Integer.parseInt(this.width.getText());
    } catch (NumberFormatException e) {
      this.showWarning("Invalid Width", "Please re-enter the width.");
    }
    this.width.setText("");
    resetFocus();
    return incr;
  }

  @Override
  public int getDownsizeHeight() {
    int incr = 0;
    try {
      incr = Integer.parseInt(this.height.getText());
    } catch (NumberFormatException e) {
      this.showWarning("Invalid Height", "Please re-enter the height.");
    }
    this.height.setText("");
    resetFocus();
    return incr;
  }

  /**
   * Clear the increment text box.
   */
  @Override
  public void clearIncrementInt() {
    input.setText("");
  }

  /**
   * Add the buttons as ActionListeners for the methods passed in via the Features object.
   * Connects the Controller and View.
   * @param features The Features object containing the desired methods.
   */
  public void addFeatures(Features features) {
    this.horizontal.addActionListener(evt -> features.apply("horizontal"));
    this.vertical.addActionListener(evt -> features.apply("vertical"));
    this.redComp.addActionListener(evt -> features.apply("redComp"));
    this.greenComp.addActionListener(evt -> features.apply("greenComp"));
    this.blueComp.addActionListener(evt -> features.apply("blueComp"));
    this.lumaComp.addActionListener(evt -> features.apply("luma"));
    this.intensityComp.addActionListener(evt -> features.apply("intensity"));
    this.valueComp.addActionListener(evt -> features.apply("value"));
    this.greyScale.addActionListener(evt -> features.apply("greyScale"));
    this.sepia.addActionListener(evt -> features.apply("sepia"));
    this.blur.addActionListener(evt -> features.apply("blur"));
    this.sharpen.addActionListener(evt -> features.apply("sharpen"));
    this.brighten.addActionListener(evt -> features.apply("brighten"));
    this.darken.addActionListener(evt -> features.apply("darken"));
    this.downscale.addActionListener(evt -> features.apply("downscale"));

    // these lambdas are more involved than simple method calls.
    fileOpenButton.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JP(E)G, BMP, PPM, PNG Types", "jpg", "jpeg", "ppm", "bmp", "png");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(IMEView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        this.fileOpenDisplay.setText(f.getAbsolutePath());
        this.loadFilePath = f.getAbsolutePath();

        features.loadIMG(this.loadFilePath);
        features.setFilePath(this.loadFilePath);
      }
    });

    // these lambdas are more involved than simple method calls.
    fileSaveButton.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser();
      int retvalue = fchooser.showSaveDialog(IMEView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        this.fileSaveDisplay.setText(f.getAbsolutePath());
        this.saveFilePath = f.getAbsolutePath();
        features.saveIMG(this.saveFilePath);
      }
    });

    // keeps track of the focus histogram.
    redData.addActionListener(evt -> {
      this.histFocus = "redData";
      features.setHist("redData");
    });
    greenData.addActionListener(evt -> {
      this.histFocus = "greenData";
      features.setHist("greenData");
    });
    blueData.addActionListener(evt -> {
      this.histFocus = "blueData";
      features.setHist("blueData");
    });
    intensityData.addActionListener(evt -> {
      this.histFocus = "intensityData";
      features.setHist("intensityData");
    });
    allData.addActionListener(evt -> {
      this.histFocus = "allData";
      features.setHist("allData");
    });


  }

  /**
   * Updates the main image of the program (the one being edited) based on the BufferedImage passed
   * into it.
   *
   * @param img the BufferedImage argument
   */
  public void updateMainImage(BufferedImage img) {
    this.imageLabel.setIcon(new ImageIcon(img));
  }

  /**
   * Returns a String representing the current Histogram displayed in the GUI, to accurately
   * update upon modification of the image.
   *
   * @return a String representing the currently viewing Histogram.
   */
  public String getHistFocus() {
    return this.histFocus;
  }


  /**
   * Helper method to display an error message to the user
   *
   * @param title   The title of the error box.
   * @param message The message within the error box.
   */
  public void showWarning(String title, String message) {
    JOptionPane.showMessageDialog(IMEView.this, message, title,
            JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Render the main JPanel that houses the image editing software. Set the panel to scroll if the
   * panel is larger than the screen size.
   */
  private void configureMainPanel() {
    this.mainPanel = new JPanel();
//    this.mainPanel.setMaximumSize(new Dimension(this.screenWidth, this.screenHeight));
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(this.mainPanel);
    mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    add(mainScrollPane);
  }

  /**
   * Render the JPanels that illustrate the image the user is currently working on, along with a
   * scrollbar if the image to too big to fit in the JPanel.
   */
  private void configureImagePanel() {
    this.imagePanel = new JPanel();
    imagePanel.setBorder(setTitleBorderMargin("Currently editing this image: "));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    imagePanel.setPreferredSize(new Dimension(screenWidth, (7 * screenHeight) / 10));

    this.imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);

    this.imageLabel.setIcon(new ImageIcon(this.loadFilePath));
    imageScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    imageScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    imageScrollPane.setPreferredSize(new Dimension(screenHeight / 2, screenHeight / 3));
    imagePanel.add(imageScrollPane);
  }

  /**
   * Render the JPanels that illustrate controls for the histogram that summarizes the
   * frequencies of pixel values in the image the user is currently working on.
   */
  private void configureHistogramPanel() {


    // Panel for creating image of histogram
    this.histPanel = new JPanel();
    this.histPanel.setBorder(setTitleBorderMargin("Color Distribution Histogram"));
    this.histPanel.setLayout(new GridLayout(2, 1, 10, 10));
    imagePanel.add(this.histPanel);

    this.histLabel = new JLabel();
//    JScrollPane histScroll = new JScrollPane(this.histLabel);

    this.histLabel.setIcon(new ImageIcon());
//    histScroll.getHorizontalScrollBar().setUnitIncrement(16);
    this.histLabel.setPreferredSize(new Dimension((screenWidth * 7),
            screenHeight / 2));

//    histLabel.add(histScroll);
    this.histLabel.setHorizontalAlignment(SwingConstants.LEFT);
  }

  /**
   * Render the buttons for changing the view of the pixel value histogram data view.
   */
  private void configureHistogramControls() {
    JPanel colorDataPanel = new JPanel();

    colorDataPanel.setBorder(setTitleBorderMargin("Which pixel property would you "
            + "like to see the histogram distribution for?"));
    colorDataPanel.setLayout(new GridLayout(0, 3));

    this.intensityData = new JButton("Intensity ");
    this.redData = new JButton("Red Component ");
    this.blueData = new JButton("Blue Component ");
    this.greenData = new JButton("Green Component  ");
    this.allData = new JButton("All  ");

    colorDataPanel.add(this.redData);
    colorDataPanel.add(this.blueData);
    colorDataPanel.add(this.greenData);
    colorDataPanel.add(this.intensityData);
    colorDataPanel.add(this.allData);

    this.histPanel.add(this.histLabel);
    this.histPanel.add(colorDataPanel);
    this.histPanel.setLayout(new BoxLayout(this.histPanel, BoxLayout.PAGE_AXIS));

    colorDataPanel.setPreferredSize(new Dimension(colorDataPanel.getWidth(), 100));
  }

  /**
   * Add the panel that displays the image, and histogram for the image currently being
   * edited to the main panel.
   */
  private void configureImageEditingAndDataPanel() {
    configureMainPanel();
    configureImagePanel();
    configureHistogramPanel();
    configureHistogramControls();

    JPanel imageDisplayPanel = new JPanel();
    imageDisplayPanel.add(imagePanel);
    imageDisplayPanel.setLayout(new FlowLayout());
    imageDisplayPanel.setPreferredSize(new Dimension(screenWidth,
            3 * (screenHeight / 4)));

    this.mainPanel.add(imageDisplayPanel);
  }


  /**
   * Render the JPanel that houses the buttons for the image processing commands available in this
   * program.
   */
  private void configureCommandPanel() {
    this.commandPanel = new JPanel();
    commandPanel.setBorder(setTitleBorderMargin("What effects would you like to add to"
            + " your current image: "));
    commandPanel.setLayout(new GridLayout(0, 3));
    commandPanel.setPreferredSize(new Dimension(2 * (screenWidth / 3),
            screenHeight / 2));

    JPanel brightenPanel = new JPanel();
    brightenPanel.setLayout(new FlowLayout());
    JLabel increment = new JLabel("increment: ");


    JPanel downscalePanel = new JPanel();
    downscalePanel.setLayout(new FlowLayout());
    JLabel wxh = new JLabel("width and height: ");
    downscalePanel.add(wxh);
    this.width = new JTextField(10);
    this.height = new JTextField(10);
    downscalePanel.add(this.width);
    downscalePanel.add(this.height);
    this.downscale = new JButton("downscale");



    brightenPanel.add(increment);

    input = new JTextField(10);
    brightenPanel.add(input);

    this.brighten = new JButton("brighten");
    this.darken = new JButton("darken");
    brightenPanel.add(this.brighten);
    brightenPanel.add(this.darken);

    this.horizontal = new JButton("horizontal flip");
    this.horizontal.setActionCommand("horizontal");
    this.vertical = new JButton("vertical flip");
    this.vertical.setActionCommand("vertical");

    this.redComp = new JButton("red component");
    this.redComp.setActionCommand("redComp");
    this.blueComp = new JButton("blue component");
    this.blueComp.setActionCommand("blueComp");
    this.greenComp = new JButton("green component");
    this.greenComp.setActionCommand("greenComp");
    this.lumaComp = new JButton("luma");
    this.lumaComp.setActionCommand("lumaComp");
    this.valueComp = new JButton("value component");
    this.valueComp.setActionCommand("valueComp");
    this.intensityComp = new JButton("intensity component");
    this.intensityComp.setActionCommand("intensity");

    this.greyScale = new JButton("grey-scale");
    this.greyScale.setActionCommand("greyScale");
    this.sepia = new JButton("sepia");
    this.sepia.setActionCommand("sepia");
    this.blur = new JButton("blur");
    this.blur.setActionCommand("blur");
    this.sharpen = new JButton("sharpen");
    this.sharpen.setActionCommand("sharpen");


    commandPanel.add(this.blur);
    commandPanel.add(this.sepia);
    commandPanel.add(this.sharpen);

    commandPanel.add(this.redComp);
    commandPanel.add(this.blueComp);
    commandPanel.add(this.greenComp);

    commandPanel.add(this.valueComp);
    commandPanel.add(this.lumaComp);
    commandPanel.add(this.intensityComp);

    commandPanel.add(this.greyScale);
    commandPanel.add(this.downscale);


    commandPanel.add(downscalePanel);
    commandPanel.add(this.horizontal);
    commandPanel.add(this.vertical);
    commandPanel.add(brightenPanel);


  }

  /**
   * Render the panel that houses the buttons that allows the user to load images into the program,
   * and allows them to save the image they are currently working on.
   */
  private void configureSaveLoadPanel() {
    JPanel saveLoadPanel = new JPanel();
    saveLoadPanel.setBorder(setTitleBorderMargin("Choose a new image to edit or save "
            + "your current image: "));
    saveLoadPanel.setLayout(new BoxLayout(saveLoadPanel, BoxLayout.PAGE_AXIS));
    this.mainPanel.add(saveLoadPanel);


    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    fileOpenButton = new JButton("Load");
    fileOpenButton.setActionCommand("load");

    fileOpenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileOpenPanel.add(fileOpenDisplay);

    //file save
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    fileSaveButton = new JButton("Save");
    fileSaveButton.setActionCommand("save");

    fileSavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    fileSavePanel.add(fileSaveDisplay);

    saveLoadPanel.add(fileOpenPanel);
    saveLoadPanel.add(fileSavePanel);
    configureImageProcessingPanel(saveLoadPanel);
  }

  /**
   * Combine the loading and saving panel with the panel that houses the different buttons for
   * image editing commands.
   *
   * @param saveLoadPanel the JPanel with teh load and save buttons
   */
  private void configureImageProcessingPanel(JPanel saveLoadPanel) {
    JPanel imageProcessingPanel = new JPanel();
    imageProcessingPanel.setBorder(setTitleBorderMargin("How would you like to "
            + "process this image?"));
    imageProcessingPanel.setLayout(new BoxLayout(imageProcessingPanel, BoxLayout.PAGE_AXIS));
    imageProcessingPanel.setPreferredSize(new Dimension(this.screenWidth,
            this.screenHeight / 2));

    imageProcessingPanel.add(saveLoadPanel);
    imageProcessingPanel.add(this.commandPanel);
    this.mainPanel.add(imageProcessingPanel);
  }

  /**
   * Sets the title with a given border margin.
   *
   * @param text The title text.
   * @return a CompoundBorder with the desired information.
   */
  private CompoundBorder setTitleBorderMargin(String text) {
    Border title = BorderFactory.createTitledBorder(text);
    Border margin = new EmptyBorder(10, 10, 10, 10);
    return new CompoundBorder(title, margin);
  }

  /**
   * Sets the Histogram image with as the BufferedImage passed into it.
   *
   * @param graphImg The graph image being passed in.
   */
  public void setHist(BufferedImage graphImg) {
    Image histIcon = graphImg.getScaledInstance(255,
            this.histLabel.getHeight(), Image.SCALE_SMOOTH);
    this.histLabel.setIcon(new ImageIcon(histIcon));
  }
}
