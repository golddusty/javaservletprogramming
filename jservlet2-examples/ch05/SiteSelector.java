import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SiteSelector extends HttpServlet {

  Vector sites = new Vector();
  Random random = new Random();

  public void init() throws ServletException {
    sites.addElement("http://www.oreilly.com/catalog/jservlet");
    sites.addElement("http://www.servlets.com");
    sites.addElement("http://java.sun.com/products/servlet");
    sites.addElement("http://www.newInstance.com");
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    int siteIndex = Math.abs(random.nextInt()) % sites.size();
    String site = (String)sites.elementAt(siteIndex);

    res.setStatus(res.SC_MOVED_TEMPORARILY);
    res.setHeader("Location", site);
  }
}
