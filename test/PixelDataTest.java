import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import cs3500.imageprocessing.model.PixelData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link PixelData}.
 */
public class PixelDataTest extends AbstractCommandTests {
  PixelData pixel;
  PixelData pixel1;
  PixelData pixel2;
  Function<PixelData, Integer> cmd;

  /**
   * Sets the initial conditions for testing.
   */
  @Before
  public void setUp() {
    this.pixel = new PixelData(255, 0, 0, 0);
    this.pixel1 = new PixelData(255, 10, 10, 10);
    this.pixel2 = new PixelData(255, 30, 246, 10);
  }

  /**
   * Test that new instances of PixelData properly initialize with valid inputs.
   */
  @Test
  public void testPixelDataInstantiation() {
    boolean pass = true;
    try {
      new PixelData(255, 20, 10, 30);
    } catch (IllegalArgumentException e) {
      pass = false;
    }
    assertTrue(pass);
  }

  /**
   * Tests that instantiating a pixel with invalid arguments fail.
   */
  @Test
  public void testPixelDataInstantiationFails() {
    try {
      new PixelData(null);
      fail();
    } catch (IllegalArgumentException ia) {
      assertTrue(ia.getMessage().equals("Arguments cannot be null!"));
    }
  }

  /**
   * Test that the toString method for PixelData works properly.
   */
  @Test
  public void testToString() {
    assertEquals(" 0 0 0 ", pixel.toString());
    assertEquals(" 10 10 10 ", pixel1.toString());
    assertEquals(" 30 246 10 ", pixel2.toString());
  }

  /**
   * Test that the colorComponent method properly changes the pixel to gray scale based on the
   * red component.
   */
  @Test
  public void testColorComponentRed() {
    PixelData pixel2R = new PixelData(255, 30, 30, 30);
    this.cmd = PixelData::getR;

    assertNotEquals(pixel2R, pixel2);
    pixel2.colorComponent(this.cmd);
    assertEquals(pixel2R, pixel2);
  }

  /**
   * Test that the colorComponent method properly changes the pixel to gray scale based on the
   * blue component.
   */
  @Test
  public void testColorComponentGreen() {
    PixelData pixel2B = new PixelData(255, 246, 246, 246);
    this.cmd = PixelData::getG;

    assertNotEquals(pixel2B, pixel2);
    pixel2.colorComponent(this.cmd);
    assertEquals(pixel2B, pixel2);
  }

  /**
   * Test that the colorComponent method properly changes the pixel to gray scale based on the
   * green component.
   */
  @Test
  public void testColorComponentBlue() {
    PixelData pixel2G = new PixelData(255, 10, 10, 10);
    this.cmd = PixelData::getB;

    assertNotEquals(pixel2G, pixel2);
    pixel2.colorComponent(cmd);
    assertEquals(pixel2G, pixel2);
  }

  /**
   * Test that the colorComponent method properly changes the pixel to gray scale based on the
   * maximum value of the red, blue, and green components.
   */
  @Test
  public void testColorComponentValue() {
    PixelData pixel2V = new PixelData(255, 246, 246, 246);
    this.cmd = PixelData::getValue;

    assertNotEquals(pixel2V, pixel2);
    pixel2.colorComponent(cmd);
    assertEquals(pixel2V, pixel2);
  }

  /**
   * Test that the equals method of PixelData properly asserts when two pixels have the same value.
   */
  @Test
  public void testEquals() {
    assertTrue(this.pixel.equals(this.pixel));
    assertFalse(this.pixel.equals(this.pixel1));
    assertTrue(this.pixel.equals(new PixelData(255, 0, 0, 0)));
  }
}
