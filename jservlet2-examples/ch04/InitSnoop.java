import java.io.*;
import java.util.*;
import javax.servlet.*;

public class InitSnoop extends GenericServlet {

  // No init() method needed

  public void service(ServletRequest req, ServletResponse res)
                             throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Init Parameters:");
    Enumeration enum = getInitParameterNames();
    while (enum.hasMoreElements()) {
      String name = (String) enum.nextElement();
      out.println(name + ": " + getInitParameter(name));
    }
  }
}
