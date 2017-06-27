import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DaytimeClientServlet extends HttpServlet {

  DaytimeServer daytime;

  // Returns a reference to a DaytimeServer, or null if there was a problem.
  protected DaytimeServer getDaytimeServer() {
    // Set the security manager if it hasn't been done already.
    // Provides protection from a malicious DaytimeServer stub.
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }

    try {
      Registry registry =
        LocateRegistry.getRegistry(getRegistryHost(), getRegistryPort());
      return (DaytimeServer)registry.lookup(getRegistryName());
    }
    catch (Exception e) {
      getServletContext().log(e, "Problem getting DaytimeServer reference");
      return null;
    }
  }

  private String getRegistryName() {
    String name = getInitParameter("registryName");
    return (name == null ? "DaytimeServlet" : name);
  }

  private String getRegistryHost() {
    // Return either the hostname given by "registryHost" or
    // if no name was given return null to imply localhost 
    return getInitParameter("registryHost");
  }

  private int getRegistryPort() {
    try { return Integer.parseInt(getInitParameter("registryPort")); }
    catch (NumberFormatException e) { return Registry.REGISTRY_PORT; }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get a daytime object if we haven't before
    if (daytime == null) {
      daytime = getDaytimeServer();
      if (daytime == null) {
        // Couldn't get it, so report we're unavailable.
        throw new UnavailableException(this, "Could not locate daytime");
      }
    }

    // Get and print the current time on the (possibly remote) daytime host
    out.println(daytime.getDate().toString());
  }
}
