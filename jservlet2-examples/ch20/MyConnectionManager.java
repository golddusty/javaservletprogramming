import javax.servlet.*;
import javax.servlet.http.*;

public class MyConnectionManager implements ServletContextListener {

  public void contextInitialized(ServletContextEvent e) {
    Connection con =     // create connection
      e.getServletContext().setAttribute("con", con);
  }

  public void contextDestroyed(ServletContextEvent e) {
    Connection con =
       (Connection) e.getServletContext().getAttribute("con");
    try { con.close(); } 
    catch (SQLException ignored) { } // close connection
  }
}
