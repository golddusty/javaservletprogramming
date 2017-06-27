import com.go.teaservlet.*;
import com.go.trove.log.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;

public class ToolApp implements Application {

  private Log log;
  private Tool[] tools;

  public void init(ApplicationConfig config) throws ServletException {
    // Keep a log of events specific to this application
    log = config.getLog();

    // Load the tool data in our init for simplicity
    String toolsFile = config.getInitParameter("toolsFile");
    if (toolsFile == null) {
      throw new ServletException(
        "A tools data file must be specified as the toolsFile init parameter");
    }
    log.debug("Loading tools from " + toolsFile);
    try {
      tools = Tool.loadTools(toolsFile);
      if (tools.length == 0) {
        log.warn("No tools found in " + toolsFile);
      }
    }
    catch (Exception e) {
      log.error(e);
      throw new ServletException(e);
    }
  }

  public Object createContext(ApplicationRequest request,
                              ApplicationResponse response) {
    return new ToolContext(request, response, this);
  }

  public Class getContextType() {
    return ToolContext.class;
  }

  public void destroy() {
  }

  public Tool[] getTools() {
    // Normally the "application" would maintain or have access to a 
    // pre-existing database connection.  Here, for simplicity, we use XML.
    return tools;
  }

  public Tool[] getTools(String state) {
    // Return only tools of a given state
    // (submitted, live, rejected, or dead)
    List list = new LinkedList();
    for (int i = 0; i < tools.length; i++) {
      if (tools[i].getStateFlag().equalsIgnoreCase(state)) {
        list.add(tools[i]);
      }
    }
    return (Tool[]) list.toArray(new Tool[0]);
  }
}
