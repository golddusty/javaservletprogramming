import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloSpain extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    res.setHeader("Content-Language", "es");

    Locale locale = new Locale("es", "");
    DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                                    DateFormat.LONG,
                                                    locale);
    fmt.setTimeZone(TimeZone.getDefault());

    out.println("En Espa\u00f1ol:");
    out.println("\u00a1Hola Mundo!");
    out.println(fmt.format(new Date()));
  }
}
