import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.*;
import org.w3c.dom.html.*;

import org.enhydra.xml.io.DOMFormatter;

public class SnoopManipulation extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) 
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Get some dynamic data to display
    Enumeration locales = req.getLocales();

    // Create the DOM tree
    Snoop snoop = new Snoop();

    // Get the first "prototype" list item
    // The rest were removed during the xmlc compile
    HTMLLIElement item = snoop.getElementLocale();

    // Get the prototype's parent so we can manage the children
    Node parent = item.getParentNode();

    // Loop over the locales adding a node for each
    while (locales.hasMoreElements()) {
      Locale loc = (Locale)locales.nextElement();
      HTMLLIElement newItem = (HTMLLIElement) item.cloneNode(true);
      Text text = snoop.createTextNode(loc.toString());
      newItem.replaceChild(text, newItem.getLastChild());
      parent.insertBefore(newItem, null);
    }

    // Remove the prototype item
    parent.removeChild(item);

    // Output the document
    DOMFormatter formatter = new DOMFormatter();  // can be heavily tweaked
    formatter.write(snoop, out);
  }
}
