import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SpecialSetter extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    ServletContext context = getServletContext();
    context.setAttribute("com.costena.special.burrito", "Pollo Adobado");
    context.setAttribute("com.costena.special.day", new Date());

    out.println("The burrito special has been set.");
  }
}
