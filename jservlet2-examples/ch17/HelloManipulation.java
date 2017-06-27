import java.io.*;
import org.w3c.dom.*;
import org.w3c.dom.html.*;

import org.enhydra.xml.io.DOMFormatter;

public class HelloManipulation {

  public static void main(String[] args) {
    // Some pseudo-dynamic content
    String username = "Mark Diekhans";
    int numMessages = 43;

    // Create the DOM tree
    Hello hello = new Hello();

    // Set the title, using a standard DOM method
    hello.setTitle("Hello XMLC!");

    // Set the value for "greeting"
    hello.setTextGreeting("Hello, " + username);

    // Set the value for "messages"
    hello.setTextMessages("" + numMessages);

    try {
      DOMFormatter formatter = new DOMFormatter();  // can be heavily tweaked
      formatter.write(hello, System.out);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
