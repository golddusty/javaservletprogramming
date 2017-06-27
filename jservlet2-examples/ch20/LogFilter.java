import javax.servlet.*;
import javax.servlet.http.*;

public class LogFilter implements Filter {

  FilterConfig config;

  public void setFilterConfig(FilterConfig config) {
    this.config = config;
  }

  public FilterConfig getFilterConfig() {
    return config;
  }

  public void doFilter(ServletRequest req,
                       ServletResponse res,
                       FilterChain chain) {
    ServletContext context = getFilterConfig().getServletContext();
    long bef = System.currentTimeMillis();
    chain.doFilter(req, res); // no chain parameter needed here
    long aft = System.currentTimeMillis();
    context.log("Request to " + req.getRequestURI() + ": " +
                                                     (aft-bef));
  }
}
