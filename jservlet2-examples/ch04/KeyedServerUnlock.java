import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class KeyedServerUnlock extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    PrintWriter out = res.getWriter();

    // Get the host and port
    String host = req.getParameter("host");
    String port = req.getParameter("port");

    // If no host, use the current host
    if (host == null) {
      host = req.getServerName();
    }

    // Convert the port to an integer, if none use current port
    int numericPort;
    try {
      numericPort = Integer.parseInt(port);
    }
    catch (NumberFormatException e) {
      numericPort = req.getServerPort();
    }

    // Generate and print the key
    // Any KeyGenerationException is caught and displayed
    try {
      long key = generateKey(host, numericPort);
      out.println(host + ":" + numericPort + " has the key " + key);
    }
    catch (KeyGenerationException e) {
      out.println("Could not generate key: " + e.getMessage());
    }
  }

  // This method contains the algorithm used to match a key with
  // a server host and port. This example implementation is extremely
  // weak and should not be used by commercial sites.
  //
  // Throws a KeyGenerationException because anything more specific
  // would be tied to the chosen algorithm.
  //
  private long generateKey(String host, int port) throws KeyGenerationException {

    // The key must be a 64-bit number equal to the logical not (~)
    // of the 32-bit IP address concatenated by the 32-bit port number.

    byte hostIP[];
    try {
      hostIP = InetAddress.getByName(host).getAddress();

    }
    catch (UnknownHostException e) {
      throw new KeyGenerationException(e.getMessage());
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

    // The key is the logical not
    return ~servercode;
  }
}

class KeyGenerationException extends Exception {

  public KeyGenerationException() {
    super();
  }

  public KeyGenerationException(String msg) {
    super(msg);
  }
}
