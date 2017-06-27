import org.webmacro.*;


public class MathTool implements ContextTool {
  /**
    * A new tool object will be instantiated per-request by calling 
    * this method.  A ContextTool is effectively a factory used to 
    * create objects for use in templates.  Some tools may simply return
    * themselves from this method; others may instantiate new objects
    * to hold the per-request state.
    */
  public Object init(Context c) {
    return this;
  }

  public static int add(int x, int y) {
    return x + y;
  }

  public static int subtract(int x, int y) {
    return x - y;
  }

  public static int multiply(int x, int y) {
    return x * y;
  }

  public static int divide(int x, int y) {
    return x / y;
  }

  public static int mod(int x, int y) {
    return x % y;
  }

  public static boolean lessThan(int x, int y) {
    return (x < y);
  }

  public static boolean greaterThan(int x, int y) {
    return (x > y);
  }
}
