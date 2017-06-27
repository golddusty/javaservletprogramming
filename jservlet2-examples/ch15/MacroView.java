import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.webmacro.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.broker.*;

// Extending com.oreilly.servlet.CacheHttpServlet can improve response time

public class MacroView extends HttpServlet {

  WebMacro wm;  // WebMacro main hook

  public void init() throws ServletException {
    try {
      wm = new WM();
    }
    catch (InitException e) {
      throw new ServletException(e);
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    FastWriter out = new FastWriter(res.getOutputStream(),
                                    res.getCharacterEncoding());

    // The template name comes as extra path info
    //   /servlet/MacroView/templ.wm
    // or as servlet path via a *.wm rule
    //   /templ.wm
    String template = req.getPathInfo();
    if (template == null) {
      template = req.getServletPath();
      template = template.substring(1);  // cut off leading "/"
    }

    // If template is still null, we have a problem
    if (template == null) {
      throw new ServletException(
        "No template specified as extra path info or servlet path");
    }

    try {
      Template tmpl = wm.getTemplate(template);
      WebContext context = wm.getWebContext(req, res); 
      tmpl.write(out, context);
    }
    catch (WebMacroException e) {
      throw new ServletException(e);
    }
    finally {
      out.flush();
    }
  }

  public void destroy() {
    super.destroy();
    if (wm != null) wm.destroy();
  }
}
