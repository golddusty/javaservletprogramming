import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.webmacro.*;
import org.webmacro.servlet.*;

public class WMHello extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    FastWriter out = new FastWriter(res.getOutputStream(),
                                    res.getCharacterEncoding());

    try {
      WebMacro wm = new WM(); // optionally WM("/path/to/config/file")
      Context c = wm.getWebContext(req, res);
      c.put("date", new Date());
      Template tmpl = wm.getTemplate("hello.wm");
      tmpl.write(out, c);
      out.flush();
    }
    catch (WebMacroException e) {
      throw new ServletException(e);
    }
  }
}
