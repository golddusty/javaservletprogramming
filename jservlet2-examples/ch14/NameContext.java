import com.go.teaservlet.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NameContext {

  ApplicationRequest request;
  ApplicationResponse response;
  String name;

  public NameContext(ApplicationRequest request,
                     ApplicationResponse response) {
    this.request = request;
    this.response = response;
  }

  public String getName() {
    // If we already determined the user's name, return it
    if (name != null) { 
      return name;
    }

    // Try to determine the name of the user
    name = request.getRemoteUser();

    // If the login name isn't available, try reading a parameter
    if (name == null) {
      name = request.getParameter("name");
    }

    // If the name isn't available as a parameter, try the session
    if (name == null) {
      name = (String) request.getSession().getAttribute("name");
    }

    // If the name isn't in the session, try a cookie
    if (name == null) {
      Cookie[] cookies = request.getCookies();
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("name")) {
          name = cookies[i].getValue();
        }
      }
    }

    // If the name isn't in a cookie either, give up
    return name;
  }
}
