import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProtectedResource extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get the session
    HttpSession session = req.getSession();

    // Does the session indicate this user already logged in?
    Object done = session.getAttribute("logon.isDone");  // marker object
    if (done == null) {
      // No logon.isDone means she hasn't logged in.
      // Save the request URL as the true target and redirect to the login page.
      session.setAttribute("login.target",
                           HttpUtils.getRequestURL(req).toString());
      res.sendRedirect("/login.html");
      return;
    }

    // If we get here, the user has logged in and can see the goods
    out.println("Unpublished O'Reilly book manuscripts await you!");
  }
}
