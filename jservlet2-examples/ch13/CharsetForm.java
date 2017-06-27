import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.LocaleNegotiator;
import com.oreilly.servlet.ServletUtils;

public class CharsetForm extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    try {
      String bundleName = "CharsetForm";
      String acceptLanguage = req.getHeader("Accept-Language");
      String acceptCharset = req.getHeader("Accept-Charset");

      LocaleNegotiator negotiator =
        new LocaleNegotiator(bundleName, acceptLanguage, acceptCharset);

      Locale locale = negotiator.getLocale();
      String charset = negotiator.getCharset();
      ResourceBundle bundle = negotiator.getBundle();  // may be null

      res.setContentType("text/html; charset=" + charset);
      res.setHeader("Content-Language", locale.getLanguage());
      res.setHeader("Vary", "Accept-Language");

      PrintWriter out = res.getWriter();

      if (bundle != null) {
        out.println("<HTML><HEAD><TITLE>");
        out.println(bundle.getString("title"));
        out.println("</TITLE></HEAD>");
        out.println("<BODY>");
        out.println(bundle.getString("header"));
        out.println("<FORM ACTION=/servlet/CharsetAction METHOD=GET>");
        out.println("<INPUT TYPE=HIDDEN NAME=charset VALUE=" + charset + ">");
        out.println(bundle.getString("prompt"));
        out.println("<INPUT TYPE=TEXT NAME=TEXT>");
        out.println("</FORM>");
        out.println("</BODY></HTML>");
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
