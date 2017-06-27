import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class ECSHello extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    Document doc = new Document();
    doc.appendTitle("Testing ECS");
    doc.appendBody(new Big("Hello!"))
       .appendBody(new P())
       .appendBody("The current time is " + new Date());
    doc.output(out);
  }
}
