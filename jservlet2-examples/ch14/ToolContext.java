import com.go.teaservlet.*;

public class ToolContext {

  ApplicationRequest request;
  ApplicationResponse response;
  ToolApp app;

  public ToolContext(ApplicationRequest request,
                     ApplicationResponse response,
                     ToolApp app) {
    this.request = request;
    this.response = response;
    this.app = app;
  }

  public Tool[] getTools() {
    return app.getTools();
  }

  public Tool[] getTools(String state) {
    return app.getTools(state);
  }
}
