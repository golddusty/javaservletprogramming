import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ToolServlet extends HttpServlet {

  Tool[] tools = null;

  public void init() throws ServletException {
    // Load the tool data in our init for simplicity
    String toolsFile = 
      getServletContext().getInitParameter("toolsFile"); // from web.xml
    if (toolsFile == null) {
      throw new ServletException("A tools data file must be specified as " +
                                 "the toolsFile context init parameter");
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
    Tool[] tools = null;

    // Place an appropriate "tools" attribute in the request
    String state = req.getParameter("state");
    if (state == null) {
      req.setAttribute("tools", getTools());
    }
    else {
      req.setAttribute("tools", getTools(state));
    }

    // Send the request to the JSP for processing
    RequestDispatcher disp = req.getRequestDispatcher("/toolview-tag.jsp");
    disp.forward(req, res);
  }

  public Tool[] getTools() {
    return tools;
  }

  public Tool[] getTools(String state) {
    List list = new LinkedList();
    for (int i = 0; i < tools.length; i++) {
      if (tools[i].getStateFlag().equalsIgnoreCase(state)) {
        list.add(tools[i]);
      }
    }
    return (Tool[]) list.toArray(new Tool[0]);
  }
}
