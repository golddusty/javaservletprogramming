import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SalaryServer extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Top-secret information:");
    out.println("Everyone else gets paid more than you!");
  }
}
