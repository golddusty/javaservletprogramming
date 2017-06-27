import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExportRestriction extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // ...Some introductory HTML...

    // Get the client's hostname
    String remoteHost = req.getRemoteHost();

    // See if the client is allowed
    if (! isHostAllowed(remoteHost)) {
      out.println("Access <BLINK>denied</BLINK>");
    }
    else {
      out.println("Access granted");
      // Display download links, etc...
    }
  }

  // Disallow hosts ending with .cu, .ir, .iq, .kp, .ly, .sy, and .sd.
  private boolean isHostAllowed(String host) {
    return (!host.endsWith(".cu") &&
            !host.endsWith(".ir") &&
            !host.endsWith(".iq") &&
            !host.endsWith(".kp") &&
            !host.endsWith(".ly") &&
            !host.endsWith(".sy") &&
            !host.endsWith(".sd"));
  }
}
