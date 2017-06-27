import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

public class ViewResource extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    // Use a ServletOutputStream because we may pass binary information
    ServletOutputStream out = res.getOutputStream();
    res.setContentType("text/plain");  // sanity default

    // Get the resource to view
    URL url = null;
    try {
      url = ServletUtils.getResource(getServletContext(), req.getPathInfo());
    }
    catch (IOException e) {
      res.sendError(
        res.SC_NOT_FOUND,
        "Extra path info must point to a valid resource to view: " +
        e.getMessage());
      return;
    }

    // Connect to the resource
    URLConnection con = url.openConnection();
    con.connect();

    // Get and set the type of the resource
    String contentType = con.getContentType();
    res.setContentType(contentType);

    // Return the resource
    try {
      ServletUtils.returnURL(url, out);
    }
    catch (IOException e) {
      res.sendError(res.SC_INTERNAL_SERVER_ERROR,
              "Problem sending resource: " + e.getMessage());
    }
  }
}
