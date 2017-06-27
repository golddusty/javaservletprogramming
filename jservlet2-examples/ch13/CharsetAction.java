import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CharsetAction extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    try {
      res.setContentType("text/plain; charset=UTF-8");
      PrintWriter out = res.getWriter();

      String charset = req.getParameter("charset");

      // Get the text parameter
      String text = req.getParameter("text");

      // Now convert it from an array of bytes to an array of characters.
      // Do this using the charset that was sent as a hidden field.
      // Here we only bother to read the first line.
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(new StringBufferInputStream(text), charset));
      text = reader.readLine();

      out.println("Received charset: " + charset);
      out.println("Received text: " + text);
      out.println("Received text (escaped): " + toUnicodeEscapeString(text));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    doGet(req, res);
  }

  private static String toUnicodeEscapeString(String str) {
    // Modeled after the code in java.util.Properties.save()
    StringBuffer buf = new StringBuffer();
    int len = str.length();
    char ch;
    for (int i = 0; i < len; i++) {
      ch = str.charAt(i);
      switch (ch) {
        case '\\': buf.append("\\\\"); break;
        case '\t': buf.append("\\t"); break;
        case '\n': buf.append("\\n"); break;
        case '\r': buf.append("\\r"); break;
    
        default:
          if (ch >= ' ' && ch <= 127) {
            buf.append(ch);
          }
          else {
            buf.append('\\');
            buf.append('u');
            buf.append(toHex((ch >> 12) & 0xF));
            buf.append(toHex((ch >>  8) & 0xF));
            buf.append(toHex((ch >>  4) & 0xF));
            buf.append(toHex((ch >>  0) & 0xF));
          }
      }
    }
    return buf.toString();
  }

  private static char toHex(int nibble) {
    return hexDigit[(nibble & 0xF)];
  }

  private static char[] hexDigit = {
    '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
  };
}
