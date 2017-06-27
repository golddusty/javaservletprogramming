import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.*;

public class EnvEntrySnoop extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    try {
      Context initCtx = new InitialContext();
      NamingEnumeration enum = initCtx.listBindings("java:comp/env");

      // We're using JDK 1.2 methods; that's OK since J2EE requires JDK 1.2
      while (enum.hasMore()) {
        Binding binding = (Binding) enum.next();
        out.println("Name: " + binding.getName());
        out.println("Type: " + binding.getClassName());
        out.println("Value: " + binding.getObject());
        out.println();
      }
    }
    catch (NamingException e) {
      e.printStackTrace(out);
    }
  }
}
