import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ParameterSnoop extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Query String:");
    out.println(req.getQueryString());
    out.println();

    out.println("Request Parameters:");
    Enumeration enum = req.getParameterNames();
    while (enum.hasMoreElements()) {
      String name = (String) enum.nextElement();
      String values[] = req.getParameterValues(name);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          out.println(name + " (" + i + "): " + values[i]);
        }
      }
    }
  }
} 
