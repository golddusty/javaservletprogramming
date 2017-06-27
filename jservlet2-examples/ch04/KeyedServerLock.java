import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;

public class KeyedServerLock extends GenericServlet {

  // This servlet has no class or instance variables
  // associated with the locking, so as to simplify
  // synchronization issues.

  public void service(ServletRequest req, ServletResponse res)
                             throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // The piracy check shouldn't be done in init
    // because name/port are part of request.
    String key = getInitParameter("key");
    String host = req.getServerName();
    int port = req.getServerPort();

    // Check if the init parameter "key" unlocks this server.
    if (! keyFitsServer(key, host, port)) {
      // Explain, condemn, threaten, etc.
      out.println("Pirated!");
    }
    else {
      // Give 'em the goods
      out.println("Valid");
      // etc...
    }
  }

  // This method contains the algorithm used to match a key with
  // a server host and port. This example implementation is extremely
  // weak and should not be used by commercial sites.
  //
  private boolean keyFitsServer(String key, String host, int port) {

    if (key == null) return false;

    long numericKey = 0;
    try {
      numericKey = Long.parseLong(key);
    }
    catch (NumberFormatException e) {
      return false;
    }

    // The key must be a 64-bit number equal to the logical not (~)
    // of the 32-bit IP address concatenated with the 32-bit port number.

    byte hostIP[];
    try {
      hostIP = InetAddress.getByName(host).getAddress();
    }
    catch (UnknownHostException e) {
      return false;
    }

    // Get the 32-bit IP address
    long servercode = 0;
    for (int i = 0; i < 4; i++) {
      servercode <<= 8;
      servercode |= hostIP[i];
    }

    // Concatentate the 32-bit port number
    servercode <<= 32;
    servercode |= port;

    // Logical not
    long accesscode = ~numericKey;

    // The moment of truth: Does the key match?
    return (servercode == accesscode);
  }
}
