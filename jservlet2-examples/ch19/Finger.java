import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

public class Finger extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    String command = "finger";

    Runtime runtime = Runtime.getRuntime();
    Process process = null;
    try {
      process = runtime.exec(command);
      DataInputStream in = new DataInputStream(process.getInputStream());

      // Read and print the output
      String line = null;
      while ((line = in.readLine()) != null) {
        out.println(line);
      }
    }
    catch (Exception e) {
      out.println("Problem with finger: " +
                  ServletUtils.getStackTraceAsString(e));
    }
  }
}
