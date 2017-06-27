import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.*;

import org.apache.regexp.*;

public class Links extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // We accept the URL to process as extra path info
    // http://localhost:8080/servlet/Links/http://www.servlets.com/
    String url = req.getPathInfo();
    if (url == null || url.length() == 0) {
      res.sendError(res.SC_BAD_REQUEST,
                    "Please pass a URL to read from as extra path info");
      return;
    }
    url = url.substring(1);  // cut off leading '/'

    String page = null;
    try {
      // Request the page
      HttpMessage msg = new HttpMessage(new URL(url));
      BufferedReader in =
        new BufferedReader(new InputStreamReader(msg.sendGetMessage()));
  
      // Read the entire response into a String
      StringBuffer buf = new StringBuffer(10240);
      char[] chars = new char[10240];
      int charsRead = 0;
      while ((charsRead = in.read(chars, 0, chars.length)) != -1) {
        buf.append(chars, 0, charsRead);
      }
      page = buf.toString();
    }
    catch (IOException e) {
      res.sendError(res.SC_NOT_FOUND,
                    "Link Extractor could not read from " + url + ":<BR>" +
                    ServletUtils.getStackTraceAsString(e));
      return;
    }

    out.println("<HTML><HEAD><TITLE>Link Extractor</TITLE>");

    try {
      // We need to specify a <BASE> so relative links work correctly
      // If the page already has one, we can use that
      RE re = new RE("<base[^>]*>", RE.MATCH_CASEINDEPENDENT);
      boolean hasBase = re.match(page);

      if (hasBase) {
        // Use the existing <BASE>
        out.println(re.getParen(0));
      }
      else {
        // Calculate the base from the URL, use everything up to last '/'
        re = new RE("http://.*/", RE.MATCH_CASEINDEPENDENT);
        boolean extractedBase = re.match(url);
        if (extractedBase) {
          // Success, print the calculated base
          out.println("<BASE HREF=\"" + re.getParen(0) + "\">");
        }
        else {
          // No trailing slash, add one ourselves
          out.println("<BASE HREF=\"" + url + "/" + "\">");
        }
      }

      out.println("</HEAD><BODY>");

      out.println("The links on <A HREF=\"" + url + "\">" + url + "</A>" +
                  " are: <BR>");
      out.println("<UL>");

      String search = "<a\\s+[^<]*</a\\s*>";
      re = new RE(search, RE.MATCH_CASEINDEPENDENT);

      int index = 0;
      while (re.match(page, index)) {
        String match = re.getParen(0);
        index = re.getParenEnd(0);
        out.println("<LI>" + match + "<BR>");
      }

      out.println("</UL>");
      out.println("</BODY></HTML>");
    }
    catch (RESyntaxException e) {
      // Should never happen as the search strings are hard coded
      e.printStackTrace(out);
    }
  }
}
