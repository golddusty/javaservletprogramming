import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchView extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get the search results from a request attribute
    String[] results = (String[]) req.getAttribute("results");

    if (results == null) {
      out.println("No results.");
      out.println("Did you accidentally access this servlet directly?");
    }
    else {
      out.println("Results:");
      for (int i = 0; i < results.length; i++) {
        out.println(results[i]);
      }
    }

    out.println();
    out.println("Request URI: " + req.getRequestURI());
    out.println("Context Path: " + req.getContextPath());
    out.println("Servlet Path: " + req.getServletPath());
    out.println("Path Info: " + req.getPathInfo());
    out.println("Query String: " + req.getQueryString());
  }
}
