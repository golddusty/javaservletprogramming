import java.io.*;
import java.net.*;
import java.util.*;

public class HttpClient {

  private static void printUsage() {
    System.out.println("usage: java HttpClient host port");
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      printUsage();
      return;
    }

    // Host is the first parameter, port is the second
    String host = args[0];
    int port;
    try {
      port = Integer.parseInt(args[1]);
    }
    catch (NumberFormatException e) {
      printUsage();
      return;
    }

    try {
      // Open a socket to the server
      Socket s = new Socket(host, port);

      // Start a thread to send keyboard input to the server
      new KeyboardInputManager(System.in, s).start();

      // Now print everything we receive from the socket 
      BufferedReader in = 
        new BufferedReader(new InputStreamReader(s.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class KeyboardInputManager extends Thread {

  InputStream in;
  Socket s;

  public KeyboardInputManager(InputStream in, Socket s) {
    this.in = in;
    this.s = s;
    setPriority(MIN_PRIORITY);  // socket reads should have a higher priority
                                // Wish I could use a select() !
    setDaemon(true);  // let the app die even when this thread is running
  }

  public void run() {
    try {
      BufferedReader keyb = new BufferedReader(new InputStreamReader(in));
      PrintWriter server = new PrintWriter(s.getOutputStream());

      String line;
      System.out.println("Connected... Type your manual HTTP request");
      System.out.println("------------------------------------------");
      while ((line = keyb.readLine()) != null) {
        server.print(line);
        server.print("\r\n");  // HTTP lines end with \r\n
        server.flush();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
