import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

public class HelloRosetta extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    Locale locale;
    DateFormat full;

    try {
      res.setContentType("text/plain; charset=UTF-8");
      //PrintWriter out = res.getWriter();
      PrintWriter out = new PrintWriter(
        new OutputStreamWriter(res.getOutputStream(), "UTF8"), true);

      locale = new Locale("en", "US");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG, 
                                            DateFormat.LONG,
                                            locale);
      out.println("In English appropriate for the US:");
      out.println("Hello World!");
      out.println(full.format(new Date()));
      out.println();

      locale = new Locale("es", "");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG, 
                                            DateFormat.LONG,
                                            locale);
      out.println("En Espa\u00f1ol:");
      out.println("\u00a1Hola Mundo!");
      out.println(full.format(new Date()));
      out.println();

      locale = new Locale("ja", "");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                            DateFormat.LONG,
                                            locale);
      out.println("In Japanese:");
      out.println("\u4eca\u65e5\u306f\u4e16\u754c");
      out.println(full.format(new Date()));
      out.println();

      locale = new Locale("zh", "");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                            DateFormat.LONG,
                                            locale);
      out.println("In Chinese:");
      out.println("\u4f60\u597d\u4e16\u754c");
      out.println(full.format(new Date()));
      out.println();

      locale = new Locale("ko", "");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                            DateFormat.LONG,
                                            locale);
      out.println("In Korean:");
      out.println("\uc548\ub155\ud558\uc138\uc694\uc138\uacc4");
      out.println(full.format(new Date()));
      out.println();

      locale = new Locale("ru", "");
      full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                            DateFormat.LONG,
                                            locale);
      out.println("In Russian (Cyrillic):");
      out.print("\u0417\u0434\u0440\u0430\u0432\u0441\u0442");
      out.println("\u0432\u0443\u0439, \u041c\u0438\u0440");
      out.println(full.format(new Date()));
      out.println();
    }
    catch (Exception e) {
      log(ServletUtils.getStackTraceAsString(e));
    }
  }
}
