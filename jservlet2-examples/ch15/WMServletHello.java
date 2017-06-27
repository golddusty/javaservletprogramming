import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.webmacro.*;
import org.webmacro.servlet.*;

public class WMServletHello extends WMServlet {

  public Template handle(WebContext context) throws HandlerException {
    try {
      context.put("date", new Date());
      return getTemplate("hello.wm");
    }
    catch (NotFoundException e) {
      throw new HandlerException(e.getMessage());
    }
  }
}
