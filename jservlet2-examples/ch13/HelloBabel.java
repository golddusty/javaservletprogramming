import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.LocaleNegotiator;
import com.oreilly.servlet.ServletUtils;

public class HelloBabel extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    try {
      String bundleName = "HelloBabel";
      String acceptLanguage = req.getHeader("Accept-Language");
      String acceptCharset = req.getHeader("Accept-Charset");

      LocaleNegotiator negotiator =
        new LocaleNegotiator(bundleName, acceptLanguage, acceptCharset);

      Locale locale = negotiator.getLocale();
      String charset = negotiator.getCharset();
      ResourceBundle bundle = negotiator.getBundle();  // may be null

      res.setContentType("text/plain; charset=" + charset);
      res.setHeader("Content-Language", locale.getLanguage());
      res.setHeader("Vary", "Accept-Language");

      PrintWriter out = res.getWriter();

      DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                                      DateFormat.LONG,
                                                      locale);
      if (bundle != null) {
        out.println("In " + locale.getDisplayLanguage() + ":");
        out.println(bundle.getString("greeting"));
        out.println(fmt.format(new Date()));
      }
      else {
        out.println("Bundle could not be found.");
      }
    }
    catch (Exception e) {
      log(ServletUtils.getStackTraceAsString(e));
    }
  }
}
