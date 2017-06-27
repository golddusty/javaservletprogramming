import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.*;
import org.w3c.dom.html.*;

import org.enhydra.xml.io.DOMFormatter;

public class ToolViewServlet extends HttpServlet {

  private Tool[] tools;

  public void init() throws ServletException {
    // Load the tool data in our init for simplicity
    String toolsFile = getInitParameter("toolsFile"); // from web.xml
    if (toolsFile == null) {
      throw new ServletException(
        "A tools data file must be specified as the toolsFile init parameter");
    }
    log("Loading tools from " + toolsFile);
    try {
      tools = Tool.loadTools(toolsFile);
      if (tools.length == 0) {
        log("No tools found in " + toolsFile);
      }
      else {
        log(tools.length + " tools found in " + toolsFile);
      }
    }
    catch (Exception e) {
      throw new ServletException(e);
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) 
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Create the DOM tree for the full document
    Template template = new Template();

    // Create the DOM Tree that contains the internal content
    ToolView toolview = new ToolView();

    // Get the prototype tool view record
    HTMLDivElement record = toolview.getElementRecord();

    // Get a reference to the insertion point for the tool list
    HTMLDivElement insertionPoint = template.getElementContent();
    Node insertionParent = insertionPoint.getParentNode();

    // Set the template title, deck, and desc
    // Pull the data from the toolview.html file
    String title = ((Text)toolview.getElementTitle().getFirstChild()).getData();
    String deck = ((Text)toolview.getElementDeck().getFirstChild()).getData();
    String desc = ((Text)toolview.getElementDesc().getFirstChild()).getData();
    template.setTitle(title);     // the page title
    template.setTextTitle(title); // the element marked "title"
    template.setTextDeck(deck);   // the element marked "deck"
    template.setTextDesc(desc);   // the element marked "desc"

    // Loop over the tools adding a record for each
    for (int i = 0; i < tools.length; i++) {
      Tool tool = tools[i];

      toolview.setTextToolName(tool.name);
      toolview.setTextToolComments(tool.comments);

      if (tool.isNewWithin(45)) {
        toolview.setTextToolStatus(" (New!) ");
      }
      else if (tool.isUpdatedWithin(45)) {
        toolview.setTextToolStatus(" (Updated!) ");
      }
      else {
        toolview.setTextToolStatus("");
      }

      HTMLAnchorElement link = toolview.getElementToolLink();
      link.setHref(tool.homeURL);
      Text linkText = toolview.createTextNode(tool.homeURL);
      link.replaceChild(linkText, link.getLastChild());

      // importNode() is DOM Level 2
      insertionParent.insertBefore(template.importNode(record, true), null);
    }

    // Remove insertion placeholder
    insertionParent.removeChild(insertionPoint);
    
    // Output the document
    DOMFormatter formatter = new DOMFormatter();  // can be heavily tweaked
    formatter.write(template, out);
  }
}
