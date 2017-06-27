import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SpecialGetter extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    ServletContext context = getServletContext();
    String burrito = (String)
      context.getAttribute("com.costena.special.burrito");
    Date day = (Date)
      context.getAttribute("com.costena.special.day");

    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
    String today = df.format(day);

    out.println("Our burrito special today (" + today + ") is: " + burrito);
  }
}
