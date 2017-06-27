import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionBindings extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get the current session object, create one if necessary
    HttpSession session = req.getSession();

    // Add a CustomBindingListener
    session.setAttribute("bindings.listener",
                         new CustomBindingListener(getServletContext()));

    out.println("This page intentionally left blank");
  }
}

class CustomBindingListener implements HttpSessionBindingListener {

  // Save a ServletContext to be used for its log() method
  ServletContext context;

  public CustomBindingListener(ServletContext context) {
    this.context = context;
  }

  public void valueBound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] BOUND as " + event.getName() +
                " to " + event.getSession().getId());
  }

  public void valueUnbound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] UNBOUND as " + event.getName() +
                " from " + event.getSession().getId());
  }
}
