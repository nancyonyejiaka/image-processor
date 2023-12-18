# USEME for Image Processor - Version 3.0.0

## Graphical User Interface (GUI) Operations

### Loading an Image
1. Navigate to "How would you like to process this image?" in the GUI.
2. Proceed to "Choose a new image to edit or save your current image."
3. Click the "Load" button.
4. In the file opener, navigate to the desired image's directory and select it.
5. Double-click the file or click "Open" in the pop-up's bottom right.

*Note: Large images may take a moment to load. Once loaded, the image appears under "Currently editing this image," next to "Color Distribution Histogram."*

### Saving an Image
1. Go to the same section as for loading.
2. Click the "Save" button.
3. In the pop-up, navigate to your desired save location.
4. Enter a file name and select a format (.jpg, .png, .bmp, or .ppm) in the "Save As:" text box.
5. Click "Save" in the pop-up's bottom right corner.

### Applying Image Modification Commands
1. Load an image as described above.
2. Scroll to "What effects would you like to add to your current image."
3. Click the button corresponding to the desired effect (e.g., sepia, blur).

### Brightening or Darkening an Image
1. After loading the image, go to the effect section.
2. Enter a valid integer (positive to brighten, negative to darken) in the textbox.
3. Press 'brighten' or 'darken' as needed.

### Selecting a Histogram View
1. Load an image; the default histogram for all color components will display.
2. Use the buttons under the histogram to select a different color component view.

### Using the JAR File
- Double-click the JAR file to open the GUI version without additional arguments.

---

## Running Scripts via Command Line

### Supported Commands
- Load, Save, Modify (e.g., brighten, flip, color component), and others like sepia, greyscale, blur, sharpen.

### Script Execution
- Scripts can be executed via CLI by passing a text file with commands using the `-file <script-name.txt>` argument.
- Ensure the script file is accessible from the main class's location or provide the full path.

#### Setting Up Script Execution
- For scripts within the main project folder (e.g., IME), use: `-file <script-name.txt>`.
- For scripts in a subdirectory, include the relative path: `-file <subdir/script-name.txt>`.
- To execute the the provided sample script, use: `-file supported-scripts.txt`.

### Script Examples
# Example 1
load src/cs3500/imageprocessing/res/Square.ppm square3x3
red-component square3x3 red3x3
horizontal-flip red3x3 red3x3horiz
vertical-flip red3x3horiz red3x3vert
brighten 20 red3x3vert red3x3bright
save src/cs3500/imageprocessing/res/sqrredflip.ppm red3x3bright

# Example 2
load src/cs3500/imageprocessing/res/small-sammie-submit.png sandwich
blur sandwich sandwichBlurred
sepia sandwichBlurred sandywichBlurred
save src/cs3500/imageprocessing/res/finalSandwich.bmp sandywichBlurred

In these examples, images are loaded, modified, and saved using the specified paths and commands.
