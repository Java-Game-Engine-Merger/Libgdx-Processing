package processing.core.app;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/** 存放PApplet的公共成员变量 */
public class PAppletCore implements PConstants{
  //public class PApplet extends PSketch {  // possible in the next alpha

  /**
   * Current platform in use, one of the PConstants WINDOWS, MACOS, LINUX or OTHER.
   */
  public static int platform;

  static {
    final String name=System.getProperty("os.name");

    if(name.contains("Mac")) {
      platform=MACOS;

    }else if(name.contains("Windows")) {
      platform=WINDOWS;

    }else if(name.equals("Linux")) { // true for the ibm vm
      platform=LINUX;

    }else {
      platform=OTHER;
    }
  }

  /**
   * Whether to use native (AWT) dialogs for selectInput and selectOutput. The native dialogs on
   * some platforms can be ugly, buggy, or missing features. For 3.3.5, this defaults to true on
   * all platforms.
   */
  public static boolean useNativeSelect=true;

  /** The PGraphics renderer associated with this PApplet */
  public PGraphics g;

  /**
   * System variable that stores the width of the computer screen. For example, if the current
   * screen resolution is 1920x1080, <b>displayWidth</b> is 1920 and <b>displayHeight</b> is 1080.
   *
   * @webref environment
   * @webBrief Variable that stores the width of the computer screen
   * @see PApplet#displayHeight
   * @see PApplet#size(int, int)
   */
  public int displayWidth;

  /**
   * System variable that stores the height of the computer screen. For example, if the current
   * screen resolution is 1920x1080, <b>displayWidth</b> is 1920 and <b>displayHeight</b> is 1080.
   *
   * @webref environment
   * @webBrief Variable that stores the height of the computer screen
   * @see PApplet#displayWidth
   * @see PApplet#size(int, int)
   */
  public int displayHeight;

  public int windowX;
  public int windowY;

  /** A leech graphics object that is echoing all events. */
  public PGraphics recorder;

  /**
   * Command line options passed in from main(). This does not include the arguments passed in to
   * PApplet itself.
   * 
   * @see PApplet#main
   */
  public String[] args;

  /** Default width and height for sketch when not specified */
  public static final int DEFAULT_WIDTH=100;
  public static final int DEFAULT_HEIGHT=100;

  /**
   * The <b>pixels[]</b> array contains the values for all the pixels in the display window. These
   * values are of the color datatype. This array is defined by the size of the display window.
   * For example, if the window is 100 x 100 pixels, there will be 10,000 values and if the window
   * is 200 x 300 pixels, there will be 60,000 values. When the pixel density is set to higher
   * than 1 with the <b>pixelDensity()</b> function, these values will change. See the reference
   * for <b>pixelWidth</b> or <b>pixelHeight</b> for more information. <br />
   * <br />
   * Before accessing this array, the data must be loaded with the <b>loadPixels()</b> function.
   * Failure to do so may result in a NullPointerException. Subsequent changes to the display
   * window will not be reflected in <b>pixels</b> until <b>loadPixels()</b> is called again.
   * After <b>pixels</b> has been modified, the <b>updatePixels()</b> function must be run to
   * update the content of the display window.
   *
   * @webref image:pixels
   * @webBrief Array containing the values for all the pixels in the display window
   * @see PApplet#loadPixels()
   * @see PApplet#updatePixels()
   * @see PApplet#get(int, int, int, int)
   * @see PApplet#set(int, int, int)
   * @see PImage
   * @see PApplet#pixelDensity(int)
   * @see PApplet#pixelWidth
   * @see PApplet#pixelHeight
   */
  public int[] pixels;

  /**
   *
   * System variable which stores the width of the display window. This value is set by the first
   * parameter of the <b>size()</b> function. For example, the function call <b>size(320, 240)</b>
   * sets the <b>width</b> variable to the value 320. The value of <b>width</b> defaults to 100 if
   * <b>size()</b> is not used in a program.
   *
   * @webref environment
   * @webBrief System variable which stores the width of the display window
   * @see PApplet#height
   * @see PApplet#size(int, int)
   */
  public int width=DEFAULT_WIDTH;

  /**
   *
   * System variable which stores the height of the display window. This value is set by the
   * second parameter of the <b>size()</b> function. For example, the function call <b>size(320,
   * 240)</b> sets the <b>height</b> variable to the value 240. The value of <b>height</b>
   * defaults to 100 if <b>size()</b> is not used in a program.
   *
   * @webref environment
   * @webBrief System variable which stores the height of the display window
   * @see PApplet#width
   * @see PApplet#size(int, int)
   */
  public int height=DEFAULT_HEIGHT;

  /**
   *
   * When <b>pixelDensity(2)</b> is used to make use of a high resolution display (called a Retina
   * display on OS X or high-dpi on Windows and Linux), the width and height of the sketch do not
   * change, but the number of pixels is doubled. As a result, all operations that use pixels
   * (like <b>loadPixels()</b>, <b>get()</b>, <b>set()</b>, etc.) happen in this doubled space. As
   * a convenience, the variables <b>pixelWidth</b> and <b>pixelHeight</b> hold the actual width
   * and height of the sketch in pixels. This is useful for any sketch that uses the
   * <b>pixels[]</b> array, for instance, because the number of elements in the array will be
   * <b>pixelWidth*pixelHeight</b>, not <b>width*height</b>.
   *
   * @webref environment
   * @webBrief The actual pixel width when using high resolution display
   * @see PApplet#pixelHeight
   * @see #pixelDensity(int)
   * @see #displayDensity()
   */
  public int pixelWidth;

  /**
   * When <b>pixelDensity(2)</b> is used to make use of a high resolution display (called a Retina
   * display on OS X or high-dpi on Windows and Linux), the width and height of the sketch do not
   * change, but the number of pixels is doubled. As a result, all operations that use pixels
   * (like <b>loadPixels()</b>, <b>get()</b>, <b>set()</b>, etc.) happen in this doubled space. As
   * a convenience, the variables <b>pixelWidth</b> and <b>pixelHeight</b> hold the actual width
   * and height of the sketch in pixels. This is useful for any sketch that uses the
   * <b>pixels[]</b> array, for instance, because the number of elements in the array will be
   * <b>pixelWidth*pixelHeight</b>, not <b>width*height</b>.
   *
   * @webref environment
   * @webBrief The actual pixel height when using high resolution display
   * @see PApplet#pixelWidth
   * @see #pixelDensity(int)
   * @see #displayDensity()
   */
  public int pixelHeight;

  /**
   * Version of mouseX/mouseY to use with windowRatio().
   */
  public int rmouseX;
  public int rmouseY;

  /**
   * Version of width/height to use with windowRatio().
   */
  public int rwidth;
  public int rheight;

  /** Offset from left when windowRatio is in use. */
  public float ratioLeft;

  /** Offset from the top when windowRatio is in use. */
  public float ratioTop;

  /** Amount of scaling to be applied for the window ratio. */
  public float ratioScale;

  /**
   * The system variable <b>mouseX</b> always contains the current horizontal coordinate of the
   * mouse. <br />
   * <br />
   * Note that Processing can only track the mouse position when the pointer is over the current
   * window. The default value of <b>mouseX</b> is <b>0</b>, so <b>0</b> will be returned until
   * the mouse moves in front of the sketch window. (This typically happens when a sketch is first
   * run.) Once the mouse moves away from the window, <b>mouseX</b> will continue to report its
   * most recent position.
   *
   * @webref input:mouse
   * @webBrief The system variable that always contains the current horizontal coordinate of the
   *           mouse
   * @see PApplet#mouseY
   * @see PApplet#pmouseX
   * @see PApplet#pmouseY
   * @see PApplet#mousePressed
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseButton
   * @see PApplet#mouseWheel(MouseEvent)
   */
  public int mouseX;

  /**
   * The system variable <b>mouseY</b> always contains the current vertical coordinate of the
   * mouse. <br />
   * <br />
   * Note that Processing can only track the mouse position when the pointer is over the current
   * window. The default value of <b>mouseY</b> is <b>0</b>, so <b>0</b> will be returned until
   * the mouse moves in front of the sketch window. (This typically happens when a sketch is first
   * run.) Once the mouse moves away from the window, <b>mouseY</b> will continue to report its
   * most recent position.
   *
   * @webref input:mouse
   * @webBrief The system variable that always contains the current vertical coordinate of the
   *           mouse
   * @see PApplet#mouseX
   * @see PApplet#pmouseX
   * @see PApplet#pmouseY
   * @see PApplet#mousePressed
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseButton
   * @see PApplet#mouseWheel(MouseEvent)
   *
   */
  public int mouseY;

  /**
   * The system variable <b>pmouseX</b> always contains the horizontal position of the mouse in
   * the frame previous to the current frame.<br />
   * <br />
   * You may find that <b>pmouseX</b> and <b>pmouseY</b> have different values when referenced
   * inside of <b>draw()</b> and inside of mouse events like <b>mousePressed()</b> and
   * <b>mouseMoved()</b>. Inside <b>draw()</b>, <b>pmouseX</b> and <b>pmouseY</b> update only once
   * per frame (once per trip through the <b>draw()</b> loop). But inside mouse events, they
   * update each time the event is called. If these values weren't updated immediately during
   * events, then the mouse position would be read only once per frame, resulting in slight delays
   * and choppy interaction. If the mouse variables were always updated multiple times per frame,
   * then something like <b>line(pmouseX, pmouseY, mouseX, mouseY)</b> inside <b>draw()</b> would
   * have lots of gaps, because <b>pmouseX</b> may have changed several times in between the calls
   * to <b>line()</b>.<br />
   * <br />
   * If you want values relative to the previous frame, use <b>pmouseX</b> and <b>pmouseY</b>
   * inside <b>draw()</b>. If you want continuous response, use <b>pmouseX</b> and <b>pmouseY</b>
   * inside the mouse event functions.
   *
   * @webref input:mouse
   * @webBrief The system variable that always contains the horizontal position of the mouse in
   *           the frame previous to the current frame
   * @see PApplet#mouseX
   * @see PApplet#mouseY
   * @see PApplet#pmouseY
   * @see PApplet#mousePressed
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseButton
   * @see PApplet#mouseWheel(MouseEvent)
   */
  public int pmouseX;

  /**
   * The system variable <b>pmouseY</b> always contains the vertical position of the mouse in the
   * frame previous to the current frame. More detailed information about how <b>pmouseY</b> is
   * updated inside of <b>draw()</b> and mouse events is explained in the reference for
   * <b>pmouseX</b>.
   *
   * @webref input:mouse
   * @webBrief The system variable that always contains the vertical position of the mouse in the
   *           frame previous to the current frame
   * @see PApplet#mouseX
   * @see PApplet#mouseY
   * @see PApplet#pmouseX
   * @see PApplet#mousePressed
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseButton
   * @see PApplet#mouseWheel(MouseEvent)
   */
  public int pmouseY;

  /**
   * Previous mouseX/Y for the draw loop, separated out because this is separate from the
   * pmouseX/Y when inside the mouse event handlers. See emouseX/Y for an explanation.
   */
  protected int dmouseX,dmouseY;

  /**
   * The pmouseX/Y for the event handlers (mousePressed(), mouseDragged() etc) these are different
   * because mouse events are queued to the end of draw, so the previous position has to be
   * updated on each event, as opposed to the pmouseX/Y that's used inside draw, which is expected
   * to be updated once per trip through draw().
   */
  protected int emouseX,emouseY;

  /**
   * Used to set pmouseX/Y to mouseX/Y the first time mouseX/Y are used, otherwise pmouseX/Y are
   * always zero, causing a nasty jump.
   * <p>
   * Just using (frameCount == 0) won't work since mouseXxxxx() may not be called until a couple
   * frames into things.
   * <p>
   * 
   * @deprecated Please refrain from using this variable, it will be removed from future releases
   *             of Processing because it cannot be used consistently across platforms and input
   *             methods.
   */
  @Deprecated
  public boolean firstMouse=true;

  /**
   * When a mouse button is pressed, the value of the system variable <b>mouseButton</b> is set to
   * either <b>LEFT</b>, <b>RIGHT</b>, or <b>CENTER</b>, depending on which button is pressed. (If
   * no button is pressed, <b>mouseButton</b> may be reset to <b>0</b>. For that reason, it's best
   * to use <b>mousePressed</b> first to test if any button is being pressed, and only then test
   * the value of <b>mouseButton</b>, as shown in the examples above.)
   *
   * <h3>Advanced:</h3>
   *
   * If running on macOS, a ctrl-click will be interpreted as the right-hand mouse button (unlike
   * Java, which reports it as the left mouse).
   * 
   * @webref input:mouse
   * @webBrief Shows which mouse button is pressed
   * @see PApplet#mouseX
   * @see PApplet#mouseY
   * @see PApplet#pmouseX
   * @see PApplet#pmouseY
   * @see PApplet#mousePressed
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseWheel(MouseEvent)
   */
  public int mouseButton;

  /**
   * The <b>mousePressed</b> variable stores whether a mouse button has been pressed. The
   * <b>mouseButton</b> variable (see the related reference entry) can be used to determine which
   * button has been pressed. <br />
   * <br />
   * Mouse and keyboard events only work when a program has <b>draw()</b>. Without <b>draw()</b>,
   * the code is only run once and then stops listening for events.
   *
   * @webref input:mouse
   * @webBrief Variable storing if a mouse button is pressed
   * @see PApplet#mouseX
   * @see PApplet#mouseY
   * @see PApplet#pmouseX
   * @see PApplet#pmouseY
   * @see PApplet#mousePressed()
   * @see PApplet#mouseReleased()
   * @see PApplet#mouseClicked()
   * @see PApplet#mouseMoved()
   * @see PApplet#mouseDragged()
   * @see PApplet#mouseButton
   * @see PApplet#mouseWheel(MouseEvent)
   */
  public boolean mousePressed;

  /** @deprecated Use a mouse event handler that passes an event instead. */
  @Deprecated
  public MouseEvent mouseEvent;

  /**
   * The system variable <b>key</b> always contains the value of the most recent key on the
   * keyboard that was used (either pressed or released). <br/>
   * <br/>
   * For non-ASCII keys, use the <b>keyCode</b> variable. The keys included in the ASCII
   * specification (BACKSPACE, TAB, ENTER, RETURN, ESC, and DELETE) do not require checking to see
   * if they key is coded, and you should simply use the <b>key</b> variable instead of
   * <b>keyCode</b> If you're making cross-platform projects, note that the ENTER key is commonly
   * used on PCs and Unix and the RETURN key is used instead on Macintosh. Check for both ENTER
   * and RETURN to make sure your program will work for all platforms. <br />
   * <br />
   * There are issues with how <b>keyCode</b> behaves across different renderers and operating
   * systems. Watch out for unexpected behavior as you switch renderers and operating systems.
   *
   * <h3>Advanced</h3>
   *
   * Last key pressed.
   * <p>
   * If it's a coded key, i.e. UP/DOWN/CTRL/SHIFT/ALT, this will be set to CODED (0xffff or
   * 65535).
   *
   * @webref input:keyboard
   * @webBrief The system variable that always contains the value of the most recent key on the
   *           keyboard that was used (either pressed or released)
   * @see PApplet#keyCode
   * @see PApplet#keyPressed
   * @see PApplet#keyPressed()
   * @see PApplet#keyReleased()
   */
  public char key;

  /**
   * The variable <b>keyCode</b> is used to detect special keys such as the UP, DOWN, LEFT, RIGHT
   * arrow keys and ALT, CONTROL, SHIFT. <br />
   * <br />
   * When checking for these keys, it can be useful to first check if the key is coded. This is
   * done with the conditional <b>if (key == CODED)</b>, as shown in the example above. <br/>
   * <br/>
   * The keys included in the ASCII specification (BACKSPACE, TAB, ENTER, RETURN, ESC, and DELETE)
   * do not require checking to see if the key is coded; for those keys, you should simply use the
   * <b>key</b> variable directly (and not <b>keyCode</b>). If you're making cross-platform
   * projects, note that the ENTER key is commonly used on PCs and Unix, while the RETURN key is
   * used on Macs. Make sure your program will work on all platforms by checking for both ENTER
   * and RETURN. <br/>
   * <br/>
   * For those familiar with Java, the values for UP and DOWN are simply shorter versions of
   * Java's <b>KeyEvent.VK_UP</b> and <b>KeyEvent.VK_DOWN</b>. Other <b>keyCode</b> values can be
   * found in the Java
   * <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html">KeyEvent</a>
   * reference. <br />
   * <br />
   * There are issues with how <b>keyCode</b> behaves across different renderers and operating
   * systems. Watch out for unexpected behavior as you switch renderers and operating systems, and
   * also whenever you are using keys not mentioned in this reference entry. <br />
   * <br />
   * If you are using P2D or P3D as your renderer, use the <a href=
   * "https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/com/jogamp/newt/event/KeyEvent.html">NEWT
   * KeyEvent constants</a>.
   *
   * <h3>Advanced</h3> When "key" is set to CODED, this will contain a Java key code.
   * <p>
   * For the arrow keys, keyCode will be one of UP, DOWN, LEFT and RIGHT. ALT, CONTROL and SHIFT
   * are also available. A full set of constants can be obtained from java.awt.event.KeyEvent,
   * from the VK_XXXX variables.
   *
   * @webref input:keyboard
   * @webBrief Used to detect special keys such as the UP, DOWN, LEFT, RIGHT arrow keys and ALT,
   *           CONTROL, SHIFT
   * @see PApplet#key
   * @see PApplet#keyPressed
   * @see PApplet#keyPressed()
   * @see PApplet#keyReleased()
   */
  public int keyCode;

  /**
   * The boolean system variable <b>keyPressed</b> is <b>true</b> if any key is pressed and
   * <b>false</b> if no keys are pressed. <br />
   * <br />
   * Note that there is a similarly named function called <b>keyPressed()</b>. See its reference
   * page for more information.
   *
   * @webref input:keyboard
   * @webBrief The boolean system variable that is <b>true</b> if any key is pressed and
   *           <b>false</b> if no keys are pressed
   * @see PApplet#key
   * @see PApplet#keyCode
   * @see PApplet#keyPressed()
   * @see PApplet#keyReleased()
   */
  public boolean keyPressed;

  /**
   * The last KeyEvent object passed into a mouse function.
   * 
   * @deprecated Use a key event handler that passes an event instead.
   */
  @Deprecated
  public KeyEvent keyEvent;

  /**
   *
   * Confirms if a Processing program is "focused", meaning that it is active and will accept
   * input from mouse or keyboard. This variable is <b>true</b> if it is focused and <b>false</b>
   * if not.
   *
   * @webref environment
   * @webBrief Confirms if a Processing program is "focused"
   */
  public boolean focused=false;

  /**
   *
   * The system variable <b>frameRate</b> contains the approximate frame rate of the software as
   * it executes. The initial value is 10 fps and is updated with each frame. The value is
   * averaged (integrated) over several frames. As such, this value won't be valid until after
   * 5-10 frames.
   *
   * @webref environment
   * @webBrief The system variable that contains the approximate frame rate of the software as it
   *           executes
   * @see PApplet#frameRate(float)
   * @see PApplet#frameCount
   */
  public float frameRate=60;

  protected boolean looping=true;

  /** flag set to true when redraw() is called by the user */
  protected boolean redraw=true;

  /**
   * The system variable <b>frameCount</b> contains the number o frames displayed since the
   * program started. Inside <b>setup()</b> the value is 0 and during the first iteration of draw
   * it is 1, etc.
   *
   * @webref environment
   * @webBrief The system variable that contains the number of frames displayed since the program
   *           started
   * @see PApplet#frameRate(float)
   * @see PApplet#frameRate
   */
  public int frameCount;

  /** true if the sketch has stopped permanently. */
  public volatile boolean finished;

  // messages to send if attached as an external vm

  /**
   * Position of the upper left-hand corner of the editor window that launched this sketch.
   */
  public static final String ARGS_EDITOR_LOCATION="--editor-location";

  public static final String ARGS_EXTERNAL="--external";

  /**
   * Location for where to position the sketch window on screen.
   * <p>
   * This is used by the editor to when saving the previous sketch location, or could be used by
   * other classes to launch at a specific position on-screen.
   */
  public static final String ARGS_LOCATION="--location";

  /** Used by the PDE to suggest a display (set in prefs, passed on Run) */
  public static final String ARGS_DISPLAY="--display";

  /** Disable AWT so that LWJGL and others can run */
  public static final String ARGS_DISABLE_AWT="--disable-awt";

  //  public static final String ARGS_SPAN_DISPLAYS = "--span";

  public static final String ARGS_BGCOLOR="--bgcolor";

  public static final String ARGS_FULL_SCREEN="--full-screen";

  public static final String ARGS_WINDOW_COLOR="--window-color";

  public static final String ARGS_PRESENT="--present";

  public static final String ARGS_STOP_COLOR="--stop-color";

  public static final String ARGS_HIDE_STOP="--hide-stop";

  /**
   * Allows the user or PdeEditor to set a specific sketch folder path.
   * <p>
   * Used by PdeEditor to pass in the location where saveFrame() and all that stuff should write
   * things.
   */
  public static final String ARGS_SKETCH_FOLDER="--sketch-path";

  public static final String ARGS_UI_SCALE="--ui-scale";

  /**
   * When run externally to a PdeEditor, this is sent by the sketch when it quits.
   */
  public static final String EXTERNAL_STOP="__STOP__";

  /**
   * When run externally to a PDE Editor, this is sent by the sketch whenever the window is moved.
   * <p>
   * This is used so that the editor can re-open the sketch window in the same position as the
   * user last left it.
   */
  public static final String EXTERNAL_MOVE="__MOVE__";
}
