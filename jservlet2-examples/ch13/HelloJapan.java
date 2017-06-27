import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloJapan extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain; charset=Shift_JIS");
    PrintWriter out = res.getWriter();
    res.setHeader("Content-Language", "ja");

    Locale locale = new Locale("ja", "");
    DateFormat full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                                     DateFormat.LONG,
                                                     locale);
    out.println("In Japanese:");
    out.println("\u4eca\u65e5\u306f\u4e16\u754c");  // Hello World
    out.println(full.format(new Date()));
  }
}
