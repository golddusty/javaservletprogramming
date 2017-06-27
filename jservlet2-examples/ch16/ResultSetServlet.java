import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.ecs.*;
import org.apache.ecs.html.*;

import com.oreilly.servlet.*;

public class ResultSetServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    String url = req.getParameter("url");
    String driver = req.getParameter("driver");
    String sql = req.getParameter("sql");

    // Quickly verify url/driver/sql exist
    ParameterParser parser = new ParameterParser(req);
    String[] required = { "url", "driver", "sql" };
    String[] missing = parser.getMissingParameters(required);
    if (missing != null && missing.length > 0) {
      res.sendError(res.SC_BAD_REQUEST,
                    "URL, Driver, and SQL string must all be provided");
      return;
    }

    String param1 = req.getParameter("param1");
    String param2 = req.getParameter("param2");
    String param3 = req.getParameter("param3");
    String param4 = req.getParameter("param4");
    String param5 = req.getParameter("param5");
    String param6 = req.getParameter("param6");
    String val1 = req.getParameter("val1");
    String val2 = req.getParameter("val2");
    String val3 = req.getParameter("val3");
    String val4 = req.getParameter("val4");
    String val5 = req.getParameter("val5");
    String val6 = req.getParameter("val6");

    Properties props = new Properties();
    if (param1 != null && val1 != null) { props.put(param1, val1); }
    if (param2 != null && val2 != null) { props.put(param2, val2); }
    if (param3 != null && val3 != null) { props.put(param3, val3); }
    if (param4 != null && val4 != null) { props.put(param4, val4); }
    if (param5 != null && val5 != null) { props.put(param5, val5); }
    if (param6 != null && val6 != null) { props.put(param6, val6); }

    Connection con = null;

    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, props);

      Statement stmt = con.createStatement();
      boolean gotResultSet = stmt.execute(sql);
  
      if (!gotResultSet) {
        out.println(stmt.getUpdateCount() + " rows updated.");
      }
      else {
        TableCustomizer[] customizers = {
          new NullCustomizer(),
          new DateCustomizer(req.getLocale()),
          new BugIdCustomizer(req.getContextPath() + "/servlet/BugView"),
          new NumberCustomizer(req.getLocale()),
        };
        out.println(new ResultSetTable(stmt.getResultSet(), customizers));
      }
    }
    catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
