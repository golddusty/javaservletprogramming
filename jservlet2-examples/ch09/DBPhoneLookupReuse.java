import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DBPhoneLookupReuse extends HttpServlet {

  private Connection con = null;

  public void init() throws ServletException {
    try {
      // Load (and therefore register) the Sybase driver
      Class.forName("com.sybase.jdbc.SybDriver");
      con = DriverManager.getConnection(
        "jdbc:sybase:Tds:dbhost:7678", "user", "passwd");
    }
    catch (ClassNotFoundException e) {
      throw new UnavailableException("Couldn't load database driver");
    }
    catch (SQLException e) {
      throw new UnavailableException("Couldn't get db connection");
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    out.println("<HTML><HEAD><TITLE>Phonebook</TITLE></HEAD>");
    out.println("<BODY>");

    HtmlSQLResult result =
      new HtmlSQLResult("SELECT NAME, PHONE FROM EMPLOYEES", con);

    // Display the resulting output
    out.println("<H2>Employees:</H2>");
    out.println(result);
    out.println("</BODY></HTML>");
  }

  public void destroy() {
    // Clean up.
    try {
      if (con != null) con.close();
    }
    catch (SQLException ignored) { }
  }
}
