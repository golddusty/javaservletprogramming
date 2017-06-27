import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HomePageForward extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/index.html");
    dispatcher.forward(req, res);
  }
}
