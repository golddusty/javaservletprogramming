import org.webmacro.*;
import org.webmacro.servlet.*;
import org.webmacro.util.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;

public class ToolServlet extends WMServlet {

  private Log log;
  private Tool[] tools;

  public void start() throws ServletException {
    // Load the tool data in our init for simplicity
    String toolsFile = getInitParameter("toolsFile"); // from web.xml
    if (toolsFile == null) {
      throw new ServletException(
        "A tools data file must be specified as the toolsFile init parameter");
    }
    log = new Log(getServletName(), "Tool example debugging log");
    log.debug("Loading tools from " + toolsFile);
    try {
      tools = Tool.loadTools(toolsFile);
      if (tools.length == 0) {
        log.warning("No tools found in " + toolsFile);
      }
      else {
        log.info(tools.length + " tools found in " + toolsFile);
      }
    }
    catch (Exception e) {
      log.error(e);
      throw new ServletException(e);
    }
  }

  // Creating a context provides functions accesible from the templates.
  public Template handle(WebContext context) throws HandlerException {
    // You often pass on the request, response, and application even if
    // not all the objects are used, since they may be used later
    try {
      Template view = getTemplate("toolview.wm");
      String state = context.getRequest().getParameter("state");
      if (state == null) {
        state = (String)view.getParam("defaultState");
      }

      if (state == null) {
        context.put("tools", getTools()); 
      }
      else {
        context.put("tools", getTools(state));
      }
      return view;
    }
    catch (WebMacroException e) {
      log.exception(e);
      throw new HandlerException(e.getMessage());
    }
    catch (IOException e) {
      log.exception(e);
      throw new HandlerException(e.getMessage());
    }
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
