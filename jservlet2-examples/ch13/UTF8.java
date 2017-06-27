import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UTF8 extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    try {
      // Get a reader to read the incoming data
      BufferedReader reader = req.getReader();

      // Get a writer to write the data in UTF-8
      res.setContentType("text/html; charset=UTF-8");
      //PrintWriter out = res.getWriter();
      PrintWriter out = new PrintWriter(
        new OutputStreamWriter(res.getOutputStream(), "UTF8"), true);

      // Read and write 4K chars at a time
      // (Far more efficient than reading and writing a line at a time)
      char[] buf = new char[4 * 1024];  // 4Kchar buffer
      int len;
      while ((len = reader.read(buf, 0, buf.length)) != -1) {
        out.write(buf, 0, len);
      }
      out.flush();
    }
    catch (Exception e) {
      getServletContext().log(e, "Problem filtering page to UTF-8");
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                         throws ServletException, IOException {
    doGet(req, res);
  }
}
