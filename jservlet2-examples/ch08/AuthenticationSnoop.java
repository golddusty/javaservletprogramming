import java.io.*;
import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthenticationSnoop extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    out.println("<HTML><HEAD><TITLE>AuthenticationSnoop</TITLE></HEAD><BODY>");

    out.println("<H1>This is a password protected resource</H1>");
    out.println("<PRE>");
    out.println("User Name: " + req.getRemoteUser());
    String name = (req.getUserPrincipal() == null) ? 
                   null : req.getUserPrincipal().getName();
    out.println("Principal Name: " + name);
    out.println("Authentication Type: " + req.getAuthType());
    out.println("Is a Manager: " + req.isUserInRole("manager"));
    out.println("</PRE>");
    out.println("</BODY></HTML>");
  }
}
