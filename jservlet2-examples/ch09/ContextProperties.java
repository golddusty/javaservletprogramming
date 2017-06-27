import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ContextProperties extends Properties {

  public ContextProperties(ServletContext context) {
    Enumeration props = context.getInitParameterNames();
    while (props.hasMoreElements()) {
      String name = (String) props.nextElement();
      String value = (String) context.getInitParameter(name);
      put(name, value);
    }
  }
}
