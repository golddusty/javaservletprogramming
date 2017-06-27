import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchLogic extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    // We don't set the content type or get a writer

    // Get the string to search for
    String search = req.getParameter("search");

    // Calculate the URLs containing the string
    String[] results = getResults(search);

    // Specify the results as a request attribute
    req.setAttribute("results", results);

    // Forward to a display page
    String display = "/servlet/SearchView";
    RequestDispatcher dispatcher = req.getRequestDispatcher(display);
    dispatcher.forward(req, res);
  }

  // In real use this method would call actual search engine logic
  // and return more information about each result than a URL
  String[] getResults(String search) {
    return new String[] { "http://www.abc.com",
                          "http://www.xyz.com" };
  }
}
