package processing.core.app;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processing.core.PApplet;

/** 存放PApplet的工具方法 */

public class PAppletTools extends PAppletCore{
  // INT NUMBER FORMATTING

  public static String nf(float num) {
    int inum=(int)num;
    if(num==inum) {
      return str(inum);
    }
    return str(num);
  }

  public static String[] nf(float[] nums) {
    String[] outgoing=new String[nums.length];
    for(int i=0;i<nums.length;i++) {
      outgoing[i]=nf(nums[i]);
    }
    return outgoing;
  }

  /**
   * Integer number formatter.
   */
  static private NumberFormat int_nf;
  static private int int_nf_digits;
  static private boolean int_nf_commas;

  /**
   * Utility function for formatting numbers into strings. There are two versions: one for
   * formatting floats, and one for formatting ints. The values for the <b>digits</b> and
   * <b>right</b> parameters should always be positive integers. The <b>left</b> parameter should
   * be positive or 0. If it is zero, only the right side is formatted.<br />
   * <br />
   * As shown in the above example, <b>nf()</b> is used to add zeros to the left and/or right of a
   * number. This is typically for aligning a list of numbers. To <em>remove</em> digits from a
   * floating-point number, use the <b>int()</b>, <b>ceil()</b>, <b>floor()</b>, or <b>round()</b>
   * functions.
   *
   * @webref data:string_functions
   * @webBrief Utility function for formatting numbers into strings
   * @param nums   the numbers to format
   * @param digits number of digits to pad with zero
   * @see PApplet#nfs(float, int, int)
   * @see PApplet#nfp(float, int, int)
   * @see PApplet#nfc(float, int)
   * @see <a href= "https://processing.org/reference/intconvert_.html">int(float)</a>
   */
  public static String[] nf(int[] nums,int digits) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nf(nums[i],digits);
    }
    return formatted;
  }

  /**
   * @param num the number to format
   */
  public static String nf(int num,int digits) {
    if((int_nf!=null)&&
      (int_nf_digits==digits)&&
      !int_nf_commas) {
      return int_nf.format(num);
    }

    int_nf=NumberFormat.getInstance();
    int_nf.setGroupingUsed(false); // no commas
    int_nf_commas=false;
    int_nf.setMinimumIntegerDigits(digits);
    int_nf_digits=digits;
    return int_nf.format(num);
  }

  /**
   * Utility function for formatting numbers into strings and placing appropriate commas to mark
   * units of 1000. There are four versions: one for formatting ints, one for formatting an array
   * of ints, one for formatting floats, and one for formatting an array of floats.<br />
   * <br />
   * The value for the <b>right</b> parameter should always be a positive integer.<br />
   * <br />
   * For a non-US locale, this will insert periods instead of commas, or whatever is appropriate
   * for that region.
   *
   * @webref data:string_functions
   * @webBrief Utility function for formatting numbers into strings and placing appropriate commas
   *           to mark units of 1000
   * @param nums the numbers to format
   * @see PApplet#nf(float, int, int)
   * @see PApplet#nfp(float, int, int)
   * @see PApplet#nfs(float, int, int)
   */
  public static String[] nfc(int[] nums) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfc(nums[i]);
    }
    return formatted;
  }

  /**
   * @param num the number to format
   */
  public static String nfc(int num) {
    if((int_nf!=null)&&
      (int_nf_digits==0)&&
      int_nf_commas) {
      return int_nf.format(num);
    }

    int_nf=NumberFormat.getInstance();
    int_nf.setGroupingUsed(true);
    int_nf_commas=true;
    int_nf.setMinimumIntegerDigits(0);
    int_nf_digits=0;
    return int_nf.format(num);
  }

  /**
   * Utility function for formatting numbers into strings. Similar to <b>nf()</b> but leaves a
   * blank space in front of positive numbers, so they align with negative numbers in spite of the
   * minus symbol. There are two versions, one for formatting floats and one for formatting ints.
   * The values for the <b>digits</b>, <b>left</b>, and <b>right</b> parameters should always be
   * positive integers.
   *
   * @webref data:string_functions
   * @webBrief Utility function for formatting numbers into strings
   * @param num    the number to format
   * @param digits number of digits to pad with zeroes
   * @see PApplet#nf(float, int, int)
   * @see PApplet#nfp(float, int, int)
   * @see PApplet#nfc(float, int)
   */
  public static String nfs(int num,int digits) {
    return (num<0)?nf(num,digits):(' '+nf(num,digits));
  }

  /**
   * @param nums the numbers to format
   */
  public static String[] nfs(int[] nums,int digits) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfs(nums[i],digits);
    }
    return formatted;
  }

  /**
   * Utility function for formatting numbers into strings. Similar to <b>nf()</b> but puts a "+"
   * in front of positive numbers and a "-" in front of negative numbers. There are two versions:
   * one for formatting floats, and one for formatting ints. The values for the <b>digits</b>,
   * <b>left</b>, and <b>right</b> parameters should always be positive integers.
   *
   * @webref data:string_functions
   * @webBrief Utility function for formatting numbers into strings
   * @param num    the number to format
   * @param digits number of digits to pad with zeroes
   * @see PApplet#nf(float, int, int)
   * @see PApplet#nfs(float, int, int)
   * @see PApplet#nfc(float, int)
   */
  public static String nfp(int num,int digits) {
    return (num<0)?nf(num,digits):('+'+nf(num,digits));
  }

  /**
   * @param nums the numbers to format
   */
  public static String[] nfp(int[] nums,int digits) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfp(nums[i],digits);
    }
    return formatted;
  }

  //////////////////////////////////////////////////////////////

  // FLOAT NUMBER FORMATTING

  static private NumberFormat float_nf;
  static private int float_nf_left,float_nf_right;
  static private boolean float_nf_commas;

  /**
   * @param left  number of digits to the left of the decimal point
   * @param right number of digits to the right of the decimal point
   */
  public static String[] nf(float[] nums,int left,int right) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nf(nums[i],left,right);
    }
    return formatted;
  }

  public static String nf(float num,int left,int right) {
    if((float_nf!=null)&&
      (float_nf_left==left)&&
      (float_nf_right==right)&&
      !float_nf_commas) {
      return float_nf.format(num);
    }

    float_nf=NumberFormat.getInstance();
    float_nf.setGroupingUsed(false);
    float_nf_commas=false;

    if(left!=0) float_nf.setMinimumIntegerDigits(left);
    if(right!=0) {
      float_nf.setMinimumFractionDigits(right);
      float_nf.setMaximumFractionDigits(right);
    }
    float_nf_left=left;
    float_nf_right=right;
    return float_nf.format(num);
  }

  /**
   * @param right number of digits to the right of the decimal point
   */
  public static String[] nfc(float[] nums,int right) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfc(nums[i],right);
    }
    return formatted;
  }

  public static String nfc(float num,int right) {
    if((float_nf!=null)&&
      (float_nf_left==0)&&
      (float_nf_right==right)&&
      float_nf_commas) {
      return float_nf.format(num);
    }

    float_nf=NumberFormat.getInstance();
    float_nf.setGroupingUsed(true);
    float_nf_commas=true;

    if(right!=0) {
      float_nf.setMinimumFractionDigits(right);
      float_nf.setMaximumFractionDigits(right);
    }
    float_nf_left=0;
    float_nf_right=right;
    return float_nf.format(num);
  }

  /**
   * @param left  the number of digits to the left of the decimal point
   * @param right the number of digits to the right of the decimal point
   */
  public static String[] nfs(float[] nums,int left,int right) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfs(nums[i],left,right);
    }
    return formatted;
  }

  public static String nfs(float num,int left,int right) {
    return (num<0)?nf(num,left,right):(' '+nf(num,left,right));
  }

  /**
   * @param left  the number of digits to the left of the decimal point
   * @param right the number of digits to the right of the decimal point
   */
  public static String[] nfp(float[] nums,int left,int right) {
    String[] formatted=new String[nums.length];
    for(int i=0;i<formatted.length;i++) {
      formatted[i]=nfp(nums[i],left,right);
    }
    return formatted;
  }

  public static String nfp(float num,int left,int right) {
    return (num<0)?nf(num,left,right):('+'+nf(num,left,right));
  }

  //////////////////////////////////////////////////////////////

  // HEX/BINARY CONVERSION

  /**
   *
   * Converts an <b>int</b>, <b>byte</b>, <b>char</b>, or <b>color</b> to a <b>String</b>
   * containing the equivalent hexadecimal notation. For example, the <b>color</b> value produced
   * by <b>color(0, 102, 153)</b> will convert to the <b>String</b> value <b>"FF006699"</b>. This
   * function can help make your geeky debugging sessions much happier.<br />
   * <br />
   * Note that the maximum number of digits is 8, because an <b>int</b> value can only represent
   * up to 32 bits. Specifying more than 8 digits will not increase the length of the
   * <b>String</b> further.
   *
   * @webref data:conversion
   * @webBrief Converts a <b>byte</b>, <b>char</b>, <b>int</b>, or <b>color</b> to a <b>String</b>
   *           containing the equivalent hexadecimal notation
   * @param value the value to convert
   * @see PApplet#unhex(String)
   * @see PApplet#binary(byte)
   * @see PApplet#unbinary(String)
   */
  static final public String hex(byte value) {
    return hex(value,2);
  }

  static final public String hex(char value) {
    return hex(value,4);
  }

  static final public String hex(int value) {
    return hex(value,8);
  }
  /**
   * @param digits the number of digits (maximum 8)
   */
  static final public String hex(int value,int digits) {
    String stuff=Integer.toHexString(value).toUpperCase();
    if(digits>8) {
      digits=8;
    }

    int length=stuff.length();
    if(length>digits) {
      return stuff.substring(length-digits);

    }else if(length<digits) {
      return "00000000".substring(8-(digits-length))+stuff;
    }
    return stuff;
  }

  /**
   *
   * Converts a <b>String</b> representation of a hexadecimal number to its equivalent integer
   * value.
   *
   * @webref data:conversion
   * @webBrief Converts a <b>String</b> representation of a hexadecimal number to its equivalent
   *           integer value
   * @param value String to convert to an integer
   * @see PApplet#hex(int, int)
   * @see PApplet#binary(byte)
   * @see PApplet#unbinary(String)
   */
  static final public int unhex(String value) {
    // has to parse as a Long so that it'll work for numbers bigger than 2^31
    return (int)(Long.parseLong(value,16));
  }

  //

  /**
   * Returns a String that contains the binary value of a byte. The returned value will always
   * have 8 digits.
   */
  static final public String binary(byte value) {
    return binary(value,8);
  }

  /**
   * Returns a String that contains the binary value of a char. The returned value will always
   * have 16 digits because chars are two bytes long.
   */
  static final public String binary(char value) {
    return binary(value,16);
  }

  /**
   * Returns a String that contains the binary value of an int. The length depends on the size of
   * the number itself. If you want a specific number of digits use binary(int what, int digits)
   * to specify how many.
   */
  static final public String binary(int value) {
    return binary(value,32);
  }

  /*
   * Returns a String that contains the binary value of an int. The digits parameter determines
   * how many digits will be used.
   */

  /**
   *
   * Converts an <b>int</b>, <b>byte</b>, <b>char</b>, or <b>color</b> to a <b>String</b>
   * containing the equivalent binary notation. For example, the <b>color</b> value produced by
   * <b>color(0, 102, 153, 255)</b> will convert to the <b>String</b> value
   * <b>"11111111000000000110011010011001"</b>. This function can help make your geeky debugging
   * sessions much happier.<br />
   * <br />
   * Note that the maximum number of digits is 32, because an <b>int</b> value can only represent
   * up to 32 bits. Specifying more than 32 digits will have no effect.
   *
   * @webref data:conversion
   * @webBrief Converts an <b>int</b>, <b>byte</b>, <b>char</b>, or <b>color</b> to a
   *           <b>String</b> containing the equivalent binary notation
   * @param value  value to convert
   * @param digits number of digits to return
   * @see PApplet#unbinary(String)
   * @see PApplet#hex(int,int)
   * @see PApplet#unhex(String)
   */
  static final public String binary(int value,int digits) {
    String stuff=Integer.toBinaryString(value);
    if(digits>32) {
      digits=32;
    }

    int length=stuff.length();
    if(length>digits) {
      return stuff.substring(length-digits);

    }else if(length<digits) {
      int offset=32-(digits-length);
      return "00000000000000000000000000000000".substring(offset)+stuff;
    }
    return stuff;
  }

  /**
   *
   * Converts a <b>String</b> representation of a binary number to its equivalent integer value.
   * For example, <b>unbinary("00001000")</b> will return <b>8</b>.
   *
   * @webref data:conversion
   * @webBrief Converts a <b>String</b> representation of a binary number to its equivalent
   *           <b>integer</b> value
   * @param value String to convert to an integer
   * @see PApplet#binary(byte)
   * @see PApplet#hex(int,int)
   * @see PApplet#unhex(String)
   */
  static final public int unbinary(String value) {
    return Integer.parseInt(value,2);
  }

  /**
   *
   * The <b>print()</b> function writes to the console area, the black rectangle at the bottom of
   * the Processing environment. This function is often helpful for looking at the data a program
   * is producing. The companion function <b>println()</b> works like <b>print()</b>, but creates
   * a new line of text for each call to the function. More than one parameter can be passed into
   * the function by separating them with commas. Alternatively, individual elements can be
   * separated with quotes ("") and joined with the addition operator (+).<br />
   * <br />
   * Using <b>print()</b> on an object will output <b>null</b>, a memory location that may look
   * like "@10be08," or the result of the <b>toString()</b> method from the object that's being
   * printed. Advanced users who want more useful output when calling <b>print()</b> on their own
   * classes can add a <b>toString()</b> method to the class that returns a String.<br />
   * <br />
   * Note that the console is relatively slow. It works well for occasional messages, but does not
   * support high-speed, real-time output (such as at 60 frames per second). It should also be
   * noted, that a print() within a for loop can sometimes lock up the program, and cause the
   * sketch to freeze.
   *
   * @webref output:text area
   * @webBrief Writes to the console area of the Processing environment
   * @usage IDE
   * @param what data to print to console
   * @see PApplet#println()
   * @see PApplet#printArray(Object)
   * @see PApplet#join(String[], char)
   */
  public static void print(byte what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(boolean what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(char what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(int what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(long what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(float what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(double what) {
    System.out.print(what);
    System.out.flush();
  }

  public static void print(String what) {
    System.out.print(what);
    System.out.flush();
  }

  /**
   * @param variables list of data, separated by commas
   */
  public static void print(Object... variables) {
    StringBuilder sb=new StringBuilder();
    for(Object o:variables) {
      if(sb.length()!=0) {
        sb.append(" ");
      }
      if(o==null) {
        sb.append("null");
      }else {
        sb.append(o);
      }
    }
    System.out.print(sb);
  }

  /**
   *
   * The <b>println()</b> function writes to the console area, the black rectangle at the bottom
   * of the Processing environment. This function is often helpful for looking at the data a
   * program is producing. Each call to this function creates a new line of output. More than one
   * parameter can be passed into the function by separating them with commas. Alternatively,
   * individual elements can be separated with quotes ("") and joined with the addition operator
   * (+).<br />
   * <br />
   * Before Processing 2.1, <b>println()</b> was used to write array data to the console. Now, use
   * <b>printArray()</b> to write array data to the console.<br />
   * <br />
   * Note that the console is relatively slow. It works well for occasional messages, but does not
   * support high-speed, real-time output (such as at 60 frames per second). It should also be
   * noted, that a println() within a for loop can sometimes lock up the program, and cause the
   * sketch to freeze.
   *
   * @webref output:text area
   * @webBrief Writes to the text area of the Processing environment's console
   * @usage IDE
   * @see PApplet#print(byte)
   * @see PApplet#printArray(Object)
   */
  public static void println() {
    System.out.println();
  }

  /**
   * @param what data to print to console
   */
  public static void println(byte what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(boolean what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(char what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(int what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(long what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(float what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(double what) {
    System.out.println(what);
    System.out.flush();
  }

  public static void println(String what) {
    System.out.println(what);
    System.out.flush();
  }

  /**
   * @param variables list of data, separated by commas
   */
  public static void println(Object... variables) {
    //    System.out.println("got " + variables.length + " variables");
    print(variables);
    println();
  }

  /*
   * // Breaking this out since the compiler doesn't know the difference between // Object... and
   * just Object (with an array passed in). This should take care // of the confusion for at least
   * the most common case (a String array). // On second thought, we're going the printArray()
   * route, since the other // object types are also used frequently. public static void
   * println(String[] array) { for (int i = 0; i < array.length; i++) { System.out.println("[" + i
   * + "] \"" + array[i] + "\""); } System.out.flush(); }
   */

  /**
   * For arrays, use printArray() instead. This function causes a warning because the new
   * print(Object...) and println(Object...) functions can't be reliably bound by the compiler.
   */
  public static void println(Object what) {
    if(what==null) {
      System.out.println("null");
    }else if(what.getClass().isArray()) {
      printArray(what);
    }else {
      System.out.println(what);
      System.out.flush();
    }
  }

  /**
   *
   * The <b>printArray()</b> function writes array data to the text area of the Processing
   * environment's console. A new line is put between each element of the array. This function can
   * only print one dimensional arrays. Note that the console is relatively slow. It works well
   * for occasional messages, but does not support high-speed, real-time output (such as at 60
   * frames per second).
   *
   * @webref output:text area
   * @webBrief Writes array data to the text area of the Processing environment's console.
   * @param what one-dimensional array
   * @usage IDE
   * @see PApplet#print(byte)
   * @see PApplet#println()
   */
  public static void printArray(Object what) {
    if(what==null) {
      // special case since this does fugly things on > 1.1
      System.out.println("null");

    }else {
      String name=what.getClass().getName();
      if(name.charAt(0)=='[') {
        switch(name.charAt(1)) {
          case '['->
            // don't even mess with multidimensional arrays (case '[')
            // or anything else that's not int, float, boolean, char
            System.out.println(what);
          case 'L'-> {
            // print a 1D array of objects as individual elements
            Object[] poo=(Object[])what;
            for(int i=0;i<poo.length;i++) {
              if(poo[i] instanceof String) {
                System.out.println("["+i+"] \""+poo[i]+"\"");
              }else {
                System.out.println("["+i+"] "+poo[i]);
              }
            }
          }
          case 'Z'-> { // boolean
            boolean[] zz=(boolean[])what;
            for(int i=0;i<zz.length;i++) {
              System.out.println("["+i+"] "+zz[i]);
            }
          }
          case 'B'-> { // byte
            byte[] bb=(byte[])what;
            for(int i=0;i<bb.length;i++) {
              System.out.println("["+i+"] "+bb[i]);
            }
          }
          case 'C'-> { // char
            char[] cc=(char[])what;
            for(int i=0;i<cc.length;i++) {
              System.out.println("["+i+"] '"+cc[i]+"'");
            }
          }
          case 'I'-> { // int
            int[] ii=(int[])what;
            for(int i=0;i<ii.length;i++) {
              System.out.println("["+i+"] "+ii[i]);
            }
          }
          case 'J'-> { // int
            long[] jj=(long[])what;
            for(int i=0;i<jj.length;i++) {
              System.out.println("["+i+"] "+jj[i]);
            }
          }
          case 'F'-> { // float
            float[] ff=(float[])what;
            for(int i=0;i<ff.length;i++) {
              System.out.println("["+i+"] "+ff[i]);
            }
          }
          case 'D'-> { // double
            double[] dd=(double[])what;
            for(int i=0;i<dd.length;i++) {
              System.out.println("["+i+"] "+dd[i]);
            }
          }
          default->System.out.println(what);
        }
      }else { // not an array
        System.out.println(what);
      }
    }
    System.out.flush();
  }

  //////////////////////////////////////////////////////////////

  // URL ENCODING

  public static String urlEncode(String str) {
    return URLEncoder.encode(str,StandardCharsets.UTF_8);
  }

  // DO NOT use for file paths, URLDecoder can't handle RFC2396
  // "The recommended way to manage the encoding and decoding of
  // URLs is to use URI, and to convert between these two classes
  // using toURI() and URI.toURL()."
  // https://docs.oracle.com/javase/8/docs/api/java/net/URL.html
  public static String urlDecode(String str) {
    return URLDecoder.decode(str,StandardCharsets.UTF_8);
  }

  //////////////////////////////////////////////////////////////

  // SORT

  /**
   *
   * Sorts an array of numbers from smallest to largest, or puts an array of words in alphabetical
   * order. The original array is not modified; a re-ordered array is returned. The <b>count</b>
   * parameter states the number of elements to sort. For example, if there are 12 elements in an
   * array and <b>count</b> is set to 5, only the first 5 elements in the array will be sorted.
   * <!--As of release 0126, the alphabetical ordering is case insensitive.-->
   *
   * @webref data:array functions
   * @webBrief Sorts an array of numbers from smallest to largest and puts an array of words in
   *           alphabetical order
   * @param list array to sort
   * @see PApplet#reverse(boolean[])
   */
  public static byte[] sort(byte[] list) {
    return sort(list,list.length);
  }

  /**
   * @param count number of elements to sort, starting from 0
   */
  public static byte[] sort(byte[] list,int count) {
    byte[] outgoing=new byte[list.length];
    System.arraycopy(list,0,outgoing,0,list.length);
    Arrays.sort(outgoing,0,count);
    return outgoing;
  }

  public static char[] sort(char[] list) {
    return sort(list,list.length);
  }

  public static char[] sort(char[] list,int count) {
    char[] outgoing=new char[list.length];
    System.arraycopy(list,0,outgoing,0,list.length);
    Arrays.sort(outgoing,0,count);
    return outgoing;
  }

  public static int[] sort(int[] list) {
    return sort(list,list.length);
  }

  public static int[] sort(int[] list,int count) {
    int[] outgoing=new int[list.length];
    System.arraycopy(list,0,outgoing,0,list.length);
    Arrays.sort(outgoing,0,count);
    return outgoing;
  }

  public static float[] sort(float[] list) {
    return sort(list,list.length);
  }

  public static float[] sort(float[] list,int count) {
    float[] outgoing=new float[list.length];
    System.arraycopy(list,0,outgoing,0,list.length);
    Arrays.sort(outgoing,0,count);
    return outgoing;
  }

  public static String[] sort(String[] list) {
    return sort(list,list.length);
  }

  public static String[] sort(String[] list,int count) {
    String[] outgoing=new String[list.length];
    System.arraycopy(list,0,outgoing,0,list.length);
    Arrays.sort(outgoing,0,count);
    return outgoing;
  }

  //////////////////////////////////////////////////////////////

  // ARRAY UTILITIES

  /**
   *
   * Copies an array (or part of an array) to another array. The <b>src</b> array is copied to the
   * <b>dst</b> array, beginning at the position specified by <b>srcPosition</b> and into the
   * position specified by <b>dstPosition</b>. The number of elements to copy is determined by
   * <b>length</b>. Note that copying values overwrites existing values in the destination array.
   * To append values instead of overwriting them, use <b>concat()</b>.<br />
   * <br />
   * The simplified version with only two arguments &mdash; <b>arrayCopy(src, dst)</b> &mdash;
   * copies an entire array to another of the same size. It is equivalent to <b>arrayCopy(src, 0,
   * dst, 0, src.length)</b>.<br />
   * <br />
   * Using this function is far more efficient for copying array data than iterating through a
   * <b>for()</b> loop and copying each element individually. This function only copies
   * references, which means that for most purposes it only copies one-dimensional arrays (a
   * single set of brackets). If used with a two (or three or more) dimensional array, it will
   * only copy the references at the first level, because a two-dimensional array is simply an
   * "array of arrays". This does not produce an error, however, because this is often the desired
   * behavior. Internally, this function calls Java's <a href=
   * "https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#arraycopy-java.lang.Object-int-java.lang.Object-int-int-">System.arraycopy()</a>
   * method, so most things that apply there are inherited.
   *
   * @webref data:array functions
   * @webBrief Copies an array (or part of an array) to another array
   * @param src         the source array
   * @param srcPosition starting position in the source array
   * @param dst         the destination array of the same data type as the source array
   * @param dstPosition starting position in the destination array
   * @param length      number of array elements to be copied
   * @see PApplet#concat(boolean[], boolean[])
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static void arrayCopy(Object src,int srcPosition,
    Object dst,int dstPosition,
    int length) {
    System.arraycopy(src,srcPosition,dst,dstPosition,length);
  }

  /**
   * Convenience method for arraycopy(). Identical to
   * <CODE>arraycopy(src, 0, dst, 0, length);</CODE>
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static void arrayCopy(Object src,Object dst,int length) {
    System.arraycopy(src,0,dst,0,length);
  }

  /**
   * Shortcut to copy the entire contents of the source into the destination array. Identical to
   * <CODE>arraycopy(src, 0, dst, 0, src.length);</CODE>
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static void arrayCopy(Object src,Object dst) {
    System.arraycopy(src,0,dst,0,Array.getLength(src));
  }

  /**
   * Use arrayCopy() instead.
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  @Deprecated
  public static void arraycopy(Object src,int srcPosition,
    Object dst,int dstPosition,
    int length) {
    System.arraycopy(src,srcPosition,dst,dstPosition,length);
  }

  /**
   * Use arrayCopy() instead.
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  @Deprecated
  public static void arraycopy(Object src,Object dst,int length) {
    System.arraycopy(src,0,dst,0,length);
  }

  /**
   * Use arrayCopy() instead.
   */
  @SuppressWarnings("SuspiciousSystemArraycopy")
  @Deprecated
  public static void arraycopy(Object src,Object dst) {
    System.arraycopy(src,0,dst,0,Array.getLength(src));
  }

  /**
   *
   * Increases the size of a one-dimensional array. By default, this function doubles the size of
   * the array, but the optional <b>newSize</b> parameter provides precise control over the
   * increase in size.
   * <p/>
   * When using an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[])
   * expand(originalArray)</em>
   *
   * @webref data:array functions
   * @webBrief Increases the size of an array
   * @param list the array to expand
   * @see PApplet#shorten(boolean[])
   */
  public static boolean[] expand(boolean[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  /**
   * @param newSize new size for the array
   */
  public static boolean[] expand(boolean[] list,int newSize) {
    boolean[] temp=new boolean[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static byte[] expand(byte[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static byte[] expand(byte[] list,int newSize) {
    byte[] temp=new byte[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static char[] expand(char[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static char[] expand(char[] list,int newSize) {
    char[] temp=new char[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static int[] expand(int[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static int[] expand(int[] list,int newSize) {
    int[] temp=new int[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static long[] expand(long[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static long[] expand(long[] list,int newSize) {
    long[] temp=new long[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static float[] expand(float[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static float[] expand(float[] list,int newSize) {
    float[] temp=new float[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static double[] expand(double[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static double[] expand(double[] list,int newSize) {
    double[] temp=new double[newSize];
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  public static String[] expand(String[] list) {
    return expand(list,list.length>0?list.length<<1:1);
  }

  public static String[] expand(String[] list,int newSize) {
    String[] temp=new String[newSize];
    // in case the new size is smaller than list.length
    System.arraycopy(list,0,temp,0,Math.min(newSize,list.length));
    return temp;
  }

  /**
   * @nowebref
   */
  public static Object expand(Object array) {
    int len=Array.getLength(array);
    return expand(array,len>0?len<<1:1);
  }

  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static Object expand(Object list,int newSize) {
    Class<?> type=list.getClass().getComponentType();
    Object temp=Array.newInstance(type,newSize);
    System.arraycopy(list,0,temp,0,
      Math.min(Array.getLength(list),newSize));
    return temp;
  }

  // contract() has been removed in revision 0124, use subset() instead.
  // (expand() is also functionally equivalent)

  /**
   *
   * Expands an array by one element and adds data to the new position. The datatype of the
   * <b>element</b> parameter must be the same as the datatype of the array. <br/>
   * <br/>
   * When using an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[])
   * append(originalArray, element)</em>.
   *
   * @webref data:array functions
   * @webBrief Expands an array by one element and adds data to the new position
   * @param array array to append
   * @param value new data for the array
   * @see PApplet#shorten(boolean[])
   * @see PApplet#expand(boolean[])
   */
  public static byte[] append(byte[] array,byte value) {
    array=expand(array,array.length+1);
    array[array.length-1]=value;
    return array;
  }

  public static char[] append(char[] array,char value) {
    array=expand(array,array.length+1);
    array[array.length-1]=value;
    return array;
  }

  public static int[] append(int[] array,int value) {
    array=expand(array,array.length+1);
    array[array.length-1]=value;
    return array;
  }

  public static float[] append(float[] array,float value) {
    array=expand(array,array.length+1);
    array[array.length-1]=value;
    return array;
  }

  public static String[] append(String[] array,String value) {
    array=expand(array,array.length+1);
    array[array.length-1]=value;
    return array;
  }

  public static Object append(Object array,Object value) {
    int length=Array.getLength(array);
    array=expand(array,length+1);
    Array.set(array,length,value);
    return array;
  }

  /**
   *
   * Decreases an array by one element and returns the shortened array. <br/>
   * <br/>
   * When using an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[])
   * shorten(originalArray)</em>.
   *
   * @webref data:array functions
   * @webBrief Decreases an array by one element and returns the shortened array
   * @param list array to shorten
   * @see PApplet#append(byte[], byte)
   * @see PApplet#expand(boolean[])
   */
  public static boolean[] shorten(boolean[] list) {
    return subset(list,0,list.length-1);
  }

  public static byte[] shorten(byte[] list) {
    return subset(list,0,list.length-1);
  }

  public static char[] shorten(char[] list) {
    return subset(list,0,list.length-1);
  }

  public static int[] shorten(int[] list) {
    return subset(list,0,list.length-1);
  }

  public static float[] shorten(float[] list) {
    return subset(list,0,list.length-1);
  }

  public static String[] shorten(String[] list) {
    return subset(list,0,list.length-1);
  }

  public static Object shorten(Object list) {
    int length=Array.getLength(list);
    return subset(list,0,length-1);
  }

  /**
   *
   * Inserts a value or an array of values into an existing array. The first two parameters must
   * be arrays of the same datatype. The first parameter specifies the initial array to be
   * modified, and the second parameter defines the data to be inserted. The third parameter is an
   * index value which specifies the array position from which to insert data. (Remember that
   * array index numbering starts at zero, so the first position is 0, the second position is 1,
   * and so on.)<br />
   * <br />
   * When splicing an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[]) splice(array1,
   * array2, index)</em>
   *
   * @webref data:array functions
   * @webBrief Inserts a value or array of values into an existing array
   * @param list  array to splice into
   * @param value value to be spliced in
   * @param index position in the array from which to insert data
   * @see PApplet#concat(boolean[], boolean[])
   * @see PApplet#subset(boolean[], int, int)
   */
  static final public boolean[] splice(boolean[] list,
    boolean value,int index) {
    boolean[] outgoing=new boolean[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public boolean[] splice(boolean[] list,
    boolean[] value,int index) {
    boolean[] outgoing=new boolean[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  static final public byte[] splice(byte[] list,
    byte value,int index) {
    byte[] outgoing=new byte[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public byte[] splice(byte[] list,
    byte[] value,int index) {
    byte[] outgoing=new byte[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  static final public char[] splice(char[] list,
    char value,int index) {
    char[] outgoing=new char[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public char[] splice(char[] list,
    char[] value,int index) {
    char[] outgoing=new char[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  static final public int[] splice(int[] list,
    int value,int index) {
    int[] outgoing=new int[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public int[] splice(int[] list,
    int[] value,int index) {
    int[] outgoing=new int[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  static final public float[] splice(float[] list,
    float value,int index) {
    float[] outgoing=new float[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public float[] splice(float[] list,
    float[] value,int index) {
    float[] outgoing=new float[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  static final public String[] splice(String[] list,
    String value,int index) {
    String[] outgoing=new String[list.length+1];
    System.arraycopy(list,0,outgoing,0,index);
    outgoing[index]=value;
    System.arraycopy(list,index,outgoing,index+1,
      list.length-index);
    return outgoing;
  }

  static final public String[] splice(String[] list,
    String[] value,int index) {
    String[] outgoing=new String[list.length+value.length];
    System.arraycopy(list,0,outgoing,0,index);
    System.arraycopy(value,0,outgoing,index,value.length);
    System.arraycopy(list,index,outgoing,index+value.length,
      list.length-index);
    return outgoing;
  }

  @SuppressWarnings("SuspiciousSystemArraycopy")
  static final public Object splice(Object list,Object value,int index) {
    Class<?> type=list.getClass().getComponentType();
    Object outgoing;
    int length=Array.getLength(list);

    // check whether item being spliced in is an array
    if(value.getClass().getName().charAt(0)=='[') {
      int vlength=Array.getLength(value);
      outgoing=Array.newInstance(type,length+vlength);
      System.arraycopy(list,0,outgoing,0,index);
      System.arraycopy(value,0,outgoing,index,vlength);
      System.arraycopy(list,index,outgoing,index+vlength,length-index);

    }else {
      outgoing=Array.newInstance(type,length+1);
      System.arraycopy(list,0,outgoing,0,index);
      Array.set(outgoing,index,value);
      System.arraycopy(list,index,outgoing,index+1,length-index);
    }
    return outgoing;
  }

  public static boolean[] subset(boolean[] list,int start) {
    return subset(list,start,list.length-start);
  }

  /**
   *
   * Extracts an array of elements from an existing array. The <b>list</b> parameter defines the
   * array from which the elements will be copied, and the <b>start</b> and <b>count</b>
   * parameters specify which elements to extract. If no <b>count</b> is given, elements will be
   * extracted from the <b>start</b> to the end of the array. When specifying the <b>start</b>,
   * remember that the first array element is 0. This function does not change the source
   * array.<br />
   * <br />
   * When using an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[])
   * subset(originalArray, 0, 4)</em>
   *
   * @webref data:array functions
   * @webBrief Extracts an array of elements from an existing array
   * @param list  array to extract from
   * @param start position to begin
   * @param count number of values to extract
   * @see PApplet#splice(boolean[], boolean, int)
   */
  public static boolean[] subset(boolean[] list,int start,int count) {
    boolean[] output=new boolean[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static byte[] subset(byte[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static byte[] subset(byte[] list,int start,int count) {
    byte[] output=new byte[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static char[] subset(char[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static char[] subset(char[] list,int start,int count) {
    char[] output=new char[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static int[] subset(int[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static int[] subset(int[] list,int start,int count) {
    int[] output=new int[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static long[] subset(long[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static long[] subset(long[] list,int start,int count) {
    long[] output=new long[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static float[] subset(float[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static float[] subset(float[] list,int start,int count) {
    float[] output=new float[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static double[] subset(double[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static double[] subset(double[] list,int start,int count) {
    double[] output=new double[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static String[] subset(String[] list,int start) {
    return subset(list,start,list.length-start);
  }

  public static String[] subset(String[] list,int start,int count) {
    String[] output=new String[count];
    System.arraycopy(list,start,output,0,count);
    return output;
  }

  public static Object subset(Object list,int start) {
    int length=Array.getLength(list);
    return subset(list,start,length-start);
  }

  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static Object subset(Object list,int start,int count) {
    Class<?> type=list.getClass().getComponentType();
    Object outgoing=Array.newInstance(type,count);
    System.arraycopy(list,start,outgoing,0,count);
    return outgoing;
  }

  /**
   *
   * Concatenates two arrays. For example, concatenating the array { 1, 2, 3 } and the array { 4,
   * 5, 6 } yields { 1, 2, 3, 4, 5, 6 }. Both parameters must be arrays of the same datatype.
   * <br />
   * <br />
   * When using an array of objects, the data returned from the function must be cast to the
   * object array's data type. For example: <em>SomeClass[] items = (SomeClass[]) concat(array1,
   * array2)</em>.
   *
   * @webref data:array functions
   * @webBrief Concatenates two arrays
   * @param a first array to concatenate
   * @param b second array to concatenate
   * @see PApplet#splice(boolean[], boolean, int)
   * @see PApplet#arrayCopy(Object, int, Object, int, int)
   */
  public static boolean[] concat(boolean[] a,boolean[] b) {
    boolean[] c=new boolean[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  public static byte[] concat(byte[] a,byte[] b) {
    byte[] c=new byte[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  public static char[] concat(char[] a,char[] b) {
    char[] c=new char[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  public static int[] concat(int[] a,int[] b) {
    int[] c=new int[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  public static float[] concat(float[] a,float[] b) {
    float[] c=new float[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  public static String[] concat(String[] a,String[] b) {
    String[] c=new String[a.length+b.length];
    System.arraycopy(a,0,c,0,a.length);
    System.arraycopy(b,0,c,a.length,b.length);
    return c;
  }

  @SuppressWarnings("SuspiciousSystemArraycopy")
  public static Object concat(Object a,Object b) {
    Class<?> type=a.getClass().getComponentType();
    int alength=Array.getLength(a);
    int blength=Array.getLength(b);
    Object outgoing=Array.newInstance(type,alength+blength);
    System.arraycopy(a,0,outgoing,0,alength);
    System.arraycopy(b,0,outgoing,alength,blength);
    return outgoing;
  }

  //

  /**
   *
   * Reverses the order of an array.
   *
   * @webref data:array functions
   * @webBrief Reverses the order of an array
   * @param list booleans[], bytes[], chars[], ints[], floats[], or Strings[]
   * @see PApplet#sort(String[], int)
   */
  public static boolean[] reverse(boolean[] list) {
    boolean[] outgoing=new boolean[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static byte[] reverse(byte[] list) {
    byte[] outgoing=new byte[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static char[] reverse(char[] list) {
    char[] outgoing=new char[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static int[] reverse(int[] list) {
    int[] outgoing=new int[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static float[] reverse(float[] list) {
    float[] outgoing=new float[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static String[] reverse(String[] list) {
    String[] outgoing=new String[list.length];
    int length1=list.length-1;
    for(int i=0;i<list.length;i++) {
      outgoing[i]=list[length1-i];
    }
    return outgoing;
  }

  public static Object reverse(Object list) {
    Class<?> type=list.getClass().getComponentType();
    int length=Array.getLength(list);
    Object outgoing=Array.newInstance(type,length);
    for(int i=0;i<length;i++) {
      Array.set(outgoing,i,Array.get(list,(length-1)-i));
    }
    return outgoing;
  }

  //////////////////////////////////////////////////////////////

  // STRINGS

  /**
   *
   * Removes whitespace characters from the beginning and end of a String. In addition to standard
   * whitespace characters such as space, carriage return, and tab, this function also removes the
   * Unicode "nbsp" (U+00A0) character and the zero width no-break space (U+FEFF) character.
   *
   * @webref data:string_functions
   * @webBrief Removes whitespace characters from the beginning and end of a <b>String</b>
   * @param str any string
   * @see PApplet#split(String, String)
   * @see PApplet#join(String[], char)
   */
  public static String trim(String str) {
    if(str==null) {
      return null;
    }
    // remove nbsp *and* zero width no-break space
    return str.replace('\u00A0',' ').replace('\uFEFF',' ').trim();
  }

  /**
   * @param array a String array
   */
  public static String[] trim(String[] array) {
    if(array==null) {
      return null;
    }
    String[] outgoing=new String[array.length];
    for(int i=0;i<array.length;i++) {
      if(array[i]!=null) {
        outgoing[i]=trim(array[i]);
      }
    }
    return outgoing;
  }

  /**
   *
   * Combines an array of Strings into one String, each separated by the character(s) used for the
   * <b>separator</b> parameter. To join arrays of ints or floats, it's necessary to first convert
   * them to Strings using <b>nf()</b> or <b>nfs()</b>.
   *
   * @webref data:string_functions
   * @webBrief Combines an array of <b>Strings</b> into one <b>String</b>, each separated by the
   *           character(s) used for the <b>separator</b> parameter
   * @param list      array of Strings
   * @param separator char or String to be placed between each item
   * @see PApplet#split(String, String)
   * @see PApplet#trim(String)
   * @see PApplet#nf(float, int, int)
   * @see PApplet#nfs(float, int, int)
   */
  public static String join(String[] list,char separator) {
    return join(list,String.valueOf(separator));
  }

  public static String join(String[] list,String separator) {
    StringBuilder sb=new StringBuilder();
    for(int i=0;i<list.length;i++) {
      if(i!=0) sb.append(separator);
      sb.append(list[i]);
    }
    return sb.toString();
  }

  public static String[] splitTokens(String value) {
    return splitTokens(value,WHITESPACE);
  }

  /**
   *
   * The <b>splitTokens()</b> function splits a <b>String</b> at one or many character delimiters
   * or "tokens". The <b>delim</b> parameter specifies the character or characters to be used as a
   * boundary.<br />
   * <br />
   * If no <b>delim</b> characters are specified, any whitespace character is used to split.
   * Whitespace characters include tab (&#92;t), line feed (&#92;n), carriage return (&#92;r),
   * form feed (&#92;f), and space.<br />
   * <br />
   * After using this function to parse incoming data, it is common to convert the data from
   * Strings to integers or floats by using the datatype conversion functions <b>int()</b> and
   * <b>float()</b>.
   *
   * @webref data:string_functions
   * @webBrief The <b>splitTokens()</b> function splits a <b>String</b> at one or many character
   *           "tokens"
   * @param value the String to be split
   * @param delim list of individual characters that will be used as separators
   * @see PApplet#split(String, String)
   * @see PApplet#join(String[], String)
   * @see PApplet#trim(String)
   */
  public static String[] splitTokens(String value,String delim) {
    StringTokenizer toker=new StringTokenizer(value,delim);
    String[] pieces=new String[toker.countTokens()];

    int index=0;
    while(toker.hasMoreTokens()) {
      pieces[index++]=toker.nextToken();
    }
    return pieces;
  }

  /**
   *
   * The <b>split()</b> function breaks a String into pieces using a character or string as the
   * delimiter. The <b>delim</b> parameter specifies the character or characters that mark the
   * boundaries between each piece. A String[] array is returned that contains each of the pieces.
   * <br />
   * <br />
   * If the result is a set of numbers, you can convert the String[] array to a float[] or int[]
   * array using the datatype conversion functions <b>int()</b> and <b>float()</b>. (See the
   * second example above.) <br />
   * <br />
   * The <b>splitTokens()</b> function works in a similar fashion, except that it splits using a
   * range of characters instead of a specific character or sequence. <!-- <br />
   * <br />
   * This function uses regular expressions to determine how the <b>delim</b> parameter divides
   * the <b>str</b> parameter. Therefore, if you use characters such parentheses and brackets that
   * are used with regular expressions as a part of the <b>delim</b> parameter, you'll need to put
   * two backslashes (\\\\) in front of the character (see example above). You can read more about
   * <a href="http://en.wikipedia.org/wiki/Regular_expression">regular expressions</a> and
   * <a href="http://en.wikipedia.org/wiki/Escape_character">escape characters</a> on Wikipedia.
   * -->
   *
   * @webref data:string_functions
   * @webBrief The <b>split()</b> function breaks a string into pieces using a character or string
   *           as the divider
   * @usage web_application
   * @param value the String to be split
   * @param delim the character or String used to separate the data
   */
  public static String[] split(String value,char delim) {
    // do this so that the exception occurs inside the user's
    // program, rather than appearing to be a bug inside split()
    if(value==null) return null;
    //return split(what, String.valueOf(delim));  // huh

    char[] chars=value.toCharArray();
    int splitCount=0; //1;
    for(char ch:chars) {
      if(ch==delim) splitCount++;
    }
    // make sure that there is something in the input string
    //if (chars.length > 0) {
    // if the last char is a delimiter, get rid of it..
    //if (chars[chars.length-1] == delim) splitCount--;
    // on second thought, i don't agree with this, will disable
    //}
    if(splitCount==0) {
      String[] splits=new String[1];
      splits[0]=value;
      return splits;
    }
    //int pieceCount = splitCount + 1;
    String[] splits=new String[splitCount+1];
    int splitIndex=0;
    int startIndex=0;
    for(int i=0;i<chars.length;i++) {
      if(chars[i]==delim) {
        splits[splitIndex++]=new String(chars,startIndex,i-startIndex);
        startIndex=i+1;
      }
    }
    //if (startIndex != chars.length) {
    splits[splitIndex]=new String(chars,startIndex,chars.length-startIndex);
    //}
    return splits;
  }

  public static String[] split(String value,String delim) {
    List<String> items=new ArrayList<>();
    int index;
    int offset=0;
    while((index=value.indexOf(delim,offset))!=-1) {
      items.add(value.substring(offset,index));
      offset=index+delim.length();
    }
    items.add(value.substring(offset));
    String[] outgoing=new String[items.size()];
    items.toArray(outgoing);
    return outgoing;
  }

  static protected LinkedHashMap<String,Pattern> matchPatterns;

  static Pattern matchPattern(String regexp) {
    Pattern p=null;
    if(matchPatterns==null) {
      matchPatterns=new LinkedHashMap<>(16,0.75f,true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String,Pattern> eldest) {
          // Limit the number of match patterns at 10 most recently used
          return size()==10;
        }
      };
    }else {
      p=matchPatterns.get(regexp);
    }
    if(p==null) {
      p=Pattern.compile(regexp,Pattern.MULTILINE|Pattern.DOTALL);
      matchPatterns.put(regexp,p);
    }
    return p;
  }

  /**
   *
   * This function is used to apply a regular expression to a piece of text, and return matching
   * groups (elements found inside parentheses) as a String array. If there are no matches, a
   * <b>null</b> value will be returned. If no groups are specified in the regular expression, but
   * the sequence matches, an array of length 1 (with the matched text as the first element of the
   * array) will be returned.<br />
   * <br />
   * To use the function, first check to see if the result is <b>null</b>. If the result is null,
   * then the sequence did not match at all. If the sequence did match, an array is
   * returned.<br />
   * <br />
   * If there are groups (specified by sets of parentheses) in the regular expression, then the
   * contents of each will be returned in the array. Element [0] of a regular expression match
   * returns the entire matching string, and the match groups start at element [1] (the first
   * group is [1], the second [2], and so on).<br />
   * <br />
   * The syntax can be found in the reference for Java's
   * <a href= "https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html">Pattern</a>
   * class. For regular expression syntax, read the
   * <a href="https://docs.oracle.com/javase/tutorial/essential/regex/">Java Tutorial</a> on the
   * topic.
   *
   * @webref data:string_functions
   * @webBrief The function is used to apply a regular expression to a piece of text, and return
   *           matching groups (elements found inside parentheses) as a <b>String</b> array
   * @param str    the String to be searched
   * @param regexp the regexp to be used for matching
   * @see PApplet#matchAll(String, String)
   * @see PApplet#split(String, String)
   * @see PApplet#splitTokens(String, String)
   * @see PApplet#join(String[], String)
   * @see PApplet#trim(String)
   */
  public static String[] match(String str,String regexp) {
    Pattern p=matchPattern(regexp);
    Matcher m=p.matcher(str);
    if(m.find()) {
      int count=m.groupCount()+1;
      String[] groups=new String[count];
      for(int i=0;i<count;i++) {
        groups[i]=m.group(i);
      }
      return groups;
    }
    return null;
  }

  /**
   *
   * This function is used to apply a regular expression to a piece of text, and return a list of
   * matching groups (elements found inside parentheses) as a two-dimensional String array. If
   * there are no matches, a <b>null</b> value will be returned. If no groups are specified in the
   * regular expression, but the sequence matches, a two-dimensional array is still returned, but
   * the second dimension is only of length one.<br />
   * <br />
   * To use the function, first check to see if the result is <b>null</b>. If the result is null,
   * then the sequence did not match at all. If the sequence did match, a 2D array is
   * returned.<br />
   * <br />
   * If there are groups (specified by sets of parentheses) in the regular expression, then the
   * contents of each will be returned in the array. Assuming a loop with counter variable i,
   * element [i][0] of a regular expression match returns the entire matching string, and the
   * match groups start at element [i][1] (the first group is [i][1], the second [i][2], and so
   * on).<br />
   * <br />
   * The syntax can be found in the reference for Java's
   * <a href= "https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html">Pattern</a>
   * class. For regular expression syntax, read the
   * <a href="https://docs.oracle.com/javase/tutorial/essential/regex/">Java Tutorial</a> on the
   * topic.
   *
   * @webref data:string_functions
   * @webBrief This function is used to apply a regular expression to a piece of text
   * @param str    the String to be searched
   * @param regexp the regexp to be used for matching
   * @see PApplet#match(String, String)
   * @see PApplet#split(String, String)
   * @see PApplet#splitTokens(String, String)
   * @see PApplet#join(String[], String)
   * @see PApplet#trim(String)
   */
  public static String[][] matchAll(String str,String regexp) {
    Pattern p=matchPattern(regexp);
    Matcher m=p.matcher(str);
    List<String[]> results=new ArrayList<>();
    int count=m.groupCount()+1;
    while(m.find()) {
      String[] groups=new String[count];
      for(int i=0;i<count;i++) {
        groups[i]=m.group(i);
      }
      results.add(groups);
    }
    if(results.isEmpty()) {
      return null;
    }
    String[][] matches=new String[results.size()][count];
    for(int i=0;i<matches.length;i++) {
      matches[i]=results.get(i);
    }
    return matches;
  }

  //////////////////////////////////////////////////////////////

  // CASTING FUNCTIONS, INSERTED BY PREPROC

  /**
   * <p>
   * Convert an integer to a boolean. Because of how Java handles upgrading numbers, this will
   * also cover byte and char (as they will upgrade to an int without any sort of explicit cast).
   * </p>
   * <p>
   * The preprocessor will convert boolean(what) to parseBoolean(what).
   * </p>
   * 
   * @return false if 0, true if any other number
   */
  static final public boolean parseBoolean(int what) {
    return (what!=0);
  }

  /**
   * Convert the string "true" or "false" to a boolean.
   * 
   * @return true if 'what' is "true" or "TRUE", false otherwise
   */
  static final public boolean parseBoolean(String what) {
    return Boolean.parseBoolean(what);
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  /**
   * Convert an int array to a boolean array. An int equal to zero will return false, and any
   * other value will return true.
   * 
   * @return array of boolean elements
   */
  static final public boolean[] parseBoolean(int[] what) {
    boolean[] outgoing=new boolean[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(what[i]!=0);
    }
    return outgoing;
  }

  static final public boolean[] parseBoolean(String[] what) {
    boolean[] outgoing=new boolean[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=Boolean.parseBoolean(what[i]);
    }
    return outgoing;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public byte parseByte(boolean what) {
    return what?(byte)1:0;
  }

  static final public byte parseByte(char what) {
    return (byte)what;
  }

  static final public byte parseByte(int what) {
    return (byte)what;
  }

  static final public byte parseByte(float what) {
    return (byte)what;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public byte[] parseByte(boolean[] what) {
    byte[] outgoing=new byte[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=what[i]?(byte)1:0;
    }
    return outgoing;
  }

  static final public byte[] parseByte(char[] what) {
    byte[] outgoing=new byte[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(byte)what[i];
    }
    return outgoing;
  }

  static final public byte[] parseByte(int[] what) {
    byte[] outgoing=new byte[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(byte)what[i];
    }
    return outgoing;
  }

  static final public byte[] parseByte(float[] what) {
    byte[] outgoing=new byte[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(byte)what[i];
    }
    return outgoing;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public char parseChar(byte what) {
    return (char)(what&0xff);
  }

  static final public char parseChar(int what) {
    return (char)what;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public char[] parseChar(byte[] what) {
    char[] outgoing=new char[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(char)(what[i]&0xff);
    }
    return outgoing;
  }

  static final public char[] parseChar(int[] what) {
    char[] outgoing=new char[what.length];
    for(int i=0;i<what.length;i++) {
      outgoing[i]=(char)what[i];
    }
    return outgoing;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public int parseInt(boolean what) {
    return what?1:0;
  }

  /**
   * Note that parseInt() will un-sign a signed byte value.
   */
  static final public int parseInt(byte what) {
    return what&0xff;
  }

  /**
   * Note that parseInt('5') is unlike String in the sense that it won't return 5, but the ascii
   * value. This is because ((int) someChar) returns the ascii value, and parseInt() is just
   * longhand for the cast.
   */
  static final public int parseInt(char what) {
    return what;
  }

  /**
   * Same as floor(), or an (int) cast.
   */
  static final public int parseInt(float what) {
    return (int)what;
  }

  /**
   * Parse a String into an int value. Returns 0 if the value is bad.
   */
  static final public int parseInt(String what) {
    return parseInt(what,0);
  }

  /**
   * Parse a String to an int, and provide an alternate value that should be used when the number
   * is invalid. If there's a decimal place, it will be truncated, making this more of a toInt()
   * than parseInt() function. This is because the method is used internally for casting. Not
   * ideal, but the name was chosen before that clarification was made.
   */
  static final public int parseInt(String what,int otherwise) {
    try {
      int offset=what.indexOf('.');
      if(offset==-1) {
        return Integer.parseInt(what);
      }else {
        return Integer.parseInt(what.substring(0,offset));
      }
    }catch(NumberFormatException e) {
      return otherwise;
    }
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public int[] parseInt(boolean[] what) {
    int[] list=new int[what.length];
    for(int i=0;i<what.length;i++) {
      list[i]=what[i]?1:0;
    }
    return list;
  }

  static final public int[] parseInt(byte[] what) { // note this un-signs
    int[] list=new int[what.length];
    for(int i=0;i<what.length;i++) {
      list[i]=(what[i]&0xff);
    }
    return list;
  }

  static final public int[] parseInt(char[] what) {
    int[] list=new int[what.length];
    for(int i=0;i<what.length;i++) {
      list[i]=what[i];
    }
    return list;
  }

  public static int[] parseInt(float[] what) {
    int[] inties=new int[what.length];
    for(int i=0;i<what.length;i++) {
      inties[i]=(int)what[i];
    }
    return inties;
  }

  /**
   * Make an array of int elements from an array of String objects. If the String can't be parsed
   * as a number, it will be set to zero.
   * 
   * <pre>
   * String s[]= {"1","300","44"};
   * int numbers[]=parseInt(s);
   * // numbers will contain { 1, 300, 44 }
   * </pre>
   */
  public static int[] parseInt(String[] what) {
    return parseInt(what,0);
  }

  /**
   * Make an array of int elements from an array of String objects. If the String can't be parsed
   * as a number, its entry in the array will be set to the value of the "missing" parameter.
   * 
   * <pre>
   * String s[]= {"1","300","apple","44"};
   * int numbers[]=parseInt(s,9999);
   * // numbers will contain { 1, 300, 9999, 44 }
   * </pre>
   */
  public static int[] parseInt(String[] what,int missing) {
    int[] output=new int[what.length];
    for(int i=0;i<what.length;i++) {
      try {
        output[i]=Integer.parseInt(what[i]);
      }catch(NumberFormatException e) {
        output[i]=missing;
      }
    }
    return output;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  /**
   * Convert an int to a float value. Also handles bytes because of Java's rules for upgrading
   * values.
   */
  static final public float parseFloat(int what) { // also handles byte
    return what;
  }

  static final public float parseFloat(String what) {
    return parseFloat(what,Float.NaN);
  }

  static final public float parseFloat(String what,float otherwise) {
    try {
      return Float.parseFloat(what);
    }catch(NumberFormatException ignored) {}

    return otherwise;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public float[] parseFloat(byte[] what) {
    float[] floaties=new float[what.length];
    for(int i=0;i<what.length;i++) {
      floaties[i]=what[i];
    }
    return floaties;
  }

  static final public float[] parseFloat(int[] what) {
    float[] floaties=new float[what.length];
    for(int i=0;i<what.length;i++) {
      floaties[i]=what[i];
    }
    return floaties;
  }

  static final public float[] parseFloat(String[] what) {
    return parseFloat(what,Float.NaN);
  }

  static final public float[] parseFloat(String[] what,float missing) {
    float[] output=new float[what.length];
    for(int i=0;i<what.length;i++) {
      try {
        output[i]=Float.parseFloat(what[i]);
      }catch(NumberFormatException e) {
        output[i]=missing;
      }
    }
    return output;
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public String str(boolean value) {
    return String.valueOf(value);
  }

  static final public String str(byte value) {
    return String.valueOf(value);
  }

  static final public String str(char value) {
    return String.valueOf(value);
  }

  static final public String str(int value) {
    return String.valueOf(value);
  }

  static final public String str(float value) {
    return String.valueOf(value);
  }

  // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

  static final public String[] str(boolean[] values) {
    String[] s=new String[values.length];
    for(int i=0;i<values.length;i++) s[i]=String.valueOf(values[i]);
    return s;
  }

  static final public String[] str(byte[] values) {
    String[] s=new String[values.length];
    for(int i=0;i<values.length;i++) s[i]=String.valueOf(values[i]);
    return s;
  }

  static final public String[] str(char[] values) {
    String[] s=new String[values.length];
    for(int i=0;i<values.length;i++) s[i]=String.valueOf(values[i]);
    return s;
  }

  static final public String[] str(int[] values) {
    String[] s=new String[values.length];
    for(int i=0;i<values.length;i++) s[i]=String.valueOf(values[i]);
    return s;
  }

  static final public String[] str(float[] values) {
    String[] s=new String[values.length];
    for(int i=0;i<values.length;i++) s[i]=String.valueOf(values[i]);
    return s;
  }

  //////////////////////////////////////////////////////////////

}
