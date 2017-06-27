import java.util.*;

public class ToolBean {

  private Tool[] tools;
  private String state;

  public ToolBean() { }

  public void setToolsFile(String toolsFile) throws Exception {
    // No way to gain access to the application context directly from a bean
    tools = Tool.loadTools(toolsFile);
  }

  public Tool[] getTools(String state) throws Exception {
    if (tools == null) {
      throw new IllegalStateException(
        "You must always set the toolsFile property on a ToolBean");
    }

    if (state == null) {
      return tools;
    }
    else {
      // Return only tools matching the given "state"
      List list = new LinkedList();
      for (int i = 0; i < tools.length; i++) {
        if (tools[i].getStateFlag().equalsIgnoreCase(state)) {
          list.add(tools[i]);
        }
      }
      return (Tool[]) list.toArray(new Tool[0]);
    }
  }
}
