import java.io.*;
import java.util.*;
import java.security.cert.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class X509Snoop extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    X509Certificate[] certs = (X509Certificate[])
      req.getAttribute("javax.servlet.request.X509Certificate");
    if (certs != null) {
      for (int i = 0; i < certs.length; i++) {
        out.println("Client Certificate [" + i + "] = "
                        + certs[i].toString());
      }
    }
    else {
      if ("https".equals(req.getScheme())) {
        out.println("This was an HTTPS request, " +
                    "but no client certificate is available");
      }
      else {
        out.println("This was not an HTTPS request, " +
                    "so no client certificate is available");
      }
    }
  }
}
