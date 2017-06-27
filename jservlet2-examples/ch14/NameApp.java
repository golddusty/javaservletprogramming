import com.go.teaservlet.*;
import javax.servlet.*;

public class NameApp implements Application {

  // Both init(ApplicationConfig) and destroy() must be implemented because
  // they are declared in the Application interface.  They can be left empty.
  public void init(ApplicationConfig config) throws ServletException {
  }

  // Creating a context provides functions accesible from the templates.
  public Object createContext(ApplicationRequest request,
                              ApplicationResponse response) {
    // You often pass on the request and response even if
    // they are not used, since they may be used later
    return new NameContext(request, response);
  }

  // This method must be implemented to return the class of the object
  // returned by createContext()
  public Class getContextType() {
    return NameContext.class;
  }

  public void destroy() {
  }
}
