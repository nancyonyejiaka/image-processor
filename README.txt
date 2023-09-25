IMAGE PROCESSOR
PIXELDATA[HEIGHT][WIDTH]
Designed and implemented by Duncan Mayer and Nancy Onyejiaka.


IMPLEMENTATION DETAILS VERSION 3.0.0:

    HOW TO USE OUR PROGRAM:
    When designing the user interface for our program, user accessibility and ease of use were
    our highest priority whent came to making visual design choiced. As a result, most of the
    functionality of our program has been implemented through button. Structurally, our program is
    divided into three sections: (1) the image display panel, followed by (2) the save and load
    panel, with (3) the image processing command panels at the buttom.

    The image display is further divided into two sections; the section on the left displays the
    image the user is currently working on while the section on the right displays the histogram
    for pixels and frequencies for the image the user is currently working on. The section that
    houses the histogram also includes buttons to change views between data for the red, green,
    blue, and all color components. Below the image display pane is the save and load panel. As the
    name suggests, this section of the program interface allows users to load new images, and save
    the images after making edits with this program.

    Beneath the save and load panel is the image processing command panel, which includes buttons
    for the all the commands currently supported by this program. These commands include:
    horizontal flip, vertical flip, red component, blue component, green component, luma component,
    value component, intensity component, blur, sharpen, sepia, brighten darken. They are all
    implement as buttons, with tHe exception for bright and darken which have an accompanying text
    box where the user can input an increment that the image will be brightened/darkened by. For
    ease of use, the brighten/darken text box support negative integers as well. Brightening by a
    negative increment will darken the image, while darkening by a negative image will brighten the
    image.

    If the user attempts to enter commands in an inappropriate order (e.g. attempting to edit or
    save when no image has been loaded yet) there are several warning pops up that will instruct
    the user on what to do.

    Please consult the USEME for more detailed instructions on how to our program.

    WHAT'S COMPLETE:
    Our program involved: creating a fully functional GUI, creating a connection between Buttons
    pressed and their corresponding commands, creating an updated Histogram with Pixel data pulled
    from the image being observed, the ability to save and load an image through a file choosing
    system within the GUI, etc.  We have also created a JAR file that can accept scripts, such as
    one to open the GUI.

    DESIGN CHANGES AND JUSTIFICATION:
    We combined the code for saving and loading a PNG/JPG/BMP and the code for loading a PPM into
    one class.  Whereas before we had these separated, it made more logical sense them to be the
    same thing, in order to reduce the number of checks in other parts of our code for the various
    file extensions.

    CITATIONS:
    One image that we used was a jpg of the painting "Eleanor" by Frank Weston Benson from the MFA.
    This image was obtained for free off of the MFA website, and is free for personal use as per
    the MFA website at https://www.mfa.org/collections/mfa-images/web-use-and-gallery-photography
    We believe that using this image for this project constitutes personal or educational use,
    as no profit comes of us sharing this image.  This image is used in our screenshot showing how
    the GUI looks when running.




IMPLEMENTATION DETAILS VERSION 1.0.0:

We chose a Command Pattern implementation for our Image Processing program. It is written such that
the model has a field, 'storage', that is a HashMap<String, PixelData[][]> that stores the images
uploaded to our program as a 2D array of PixelData. The model extends an interface, IModel, such
that it the model can be expanded upon if users wished to implement greater functionality.

Images are read and loaded into the program by the Load command, called by
the controller when it receives the proper input String. Reading the PPM makes use of
the provided code we got at the start of this project, with our own mods.

Various commands can be called on within the controller such as any of the color-component
commands,either flip command, or brighten. These commands are implemented such that a copy is
made of the original hashmap entry, and then mutated by the command and placed back into the
hashmap under a new key value. Images are saved to the computer by writing the data contained
within the PixelData[][] and adding that to a FileWriter, which is then written to the path name
passed in. The commands are created under the umbrella of an interface named
ImageProcessingCommand. This allows for easy additions to the Command Pattern, while ensuring that
each command implements the basic necessary methods for functioning within our code.

PixelData is a class with fields for the red, green, and blue values of each pixel as well
as a maximum value. All the fields are private, but only the maximum value is final. The other
values, representing the colors of the pixel, were mutated by separate commands within our command
pattern and it was a decision by us to allow mutation.

We chose to make many of the pixel mutation methods present in the PixelData class protected because
they were only needed by methods that modified the model, meaning that access needed only to have
been granted to other classes in the Model package, meaning the PixelData void methods were set to
have protected status.  Therefore, we can only test the higher level implementations of many of
these methods, through testing the effects of Blur, Sharpen, etc, rather than testing the lowest
levels of implementation that mutate the pixel itself.


DESIGN DECISIONS:

Several design decisions were made for this program. One of such decisions regards the handling of
invalid inputs. Our current design relies on the knowledge that many commands entered by a user
are fundamentally related to one another if entered together in a long string.  As such, our design
will record to a user when a command has failed and skip all subsequent commands, clearing the slate
(so to speak) to allow for new commands to be entered accurately.

Another design decision made was regarding the handling of pixel values that are above the maximum
value for the file. In such cases, the values are simply replaced with the maximum value of the
PPM file.

One decision we made to reduce code duplication was the implementation of the Transform and Filter
Command classes.  Both rely on matrix math, but there are differences.  Transform is used in the
case where the pixel can be modified completely by only the information it stores itself and
the matrix.  For example, RedComponent or BlueComponent can be written as Transform Commands, as
their pixels can be converted successfully with a simple matrix.  Filter Commands, though, rely on
both the data of the pixel and surrounding pixels to determine its final value.

One important decision we made was in allowing slight loss of precision in our average calculations
by multiplying each of the pixels by 1/3 and summing them for the average, rather than adding them
together and dividing that sum by 3.  This allows a major reduction in code duplication by allowing
such calculations to be representable by matrix multiplication, but as such it reduces accuracy by
2 points on the pixel values.

We decided to design our code for earlier stage of this project such that instead of extending or
implementing new Model implementations to add functionality, we instead added them to our IModel
interface.  Our justification for this is that while these methods were used to reduce code
duplication in old code, they do not limit or detract from previous implementations' functionality.
Because of this, we decided it was fair to add to our interface as any theoretical client would not
be negatively impacted, they would simply have unknowing access to even more functionality.


IMAGE FILE CITATIONS:

All the PPM files included in the "res" file of this project were created by hand using a text
editor. No third-party images were included.

The files title "square[COMMAND]" (e.g. squareRedComponent, squareBlueComponent, etc.) are all
PPM files created by this program using the supported scripts and the original 2x2square.ppm file
included in the "res" packaged. One of such images is provided for each of the commands supported
by this program.

All other images of various image types (jpg, png, bmp) were sourced from Freepik.com, a website
that provides free vector, stock images and PSD. These images are offered under a free license.
The complete license summary has been copied from the website as follows:

"License Summary
Our license allows you to use the content
For commercial and personal projects
On digital or printed media
For an unlimited number of times and without any time limits
From anywhere in the world
To make modifications and create derivative works"

The bmp image included in the res package was sourced from https://filesamples.com/formats/bmp.

https://www.mfa.org/collections/mfa-images/web-use-and-gallery-photography
