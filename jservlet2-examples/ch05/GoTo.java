import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GoTo extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    // Determine the site where they want to go
    String site = req.getPathInfo();
    String query = req.getQueryString();

    // Handle a bad request
    if (site == null) {
      res.sendError(res.SC_BAD_REQUEST, "Extra path info required");
    }

    // Cut off the leading "/" and append the query string
    // We're assuming the path info URL is always absolute
    String url = site.substring(1) + (query == null ? "" : "?" + query);

    // Log the requested URL and redirect
    log(url);  // or write to a special file
    res.sendRedirect(url);
  }
}
