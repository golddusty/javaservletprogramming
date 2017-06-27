import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DBGifReader extends HttpServlet {

  Connection con;

  public void init() throws ServletException {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con = DriverManager.getConnection("jdbc:odbc:imagedb", "user", "passwd");
    }
    catch (ClassNotFoundException e) {
      throw new UnavailableException("Couldn't load JdbcOdbcDriver");
    }
    catch (SQLException e) {
      throw new UnavailableException("Couldn't get db connection");
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    try {
      res.setContentType("image/gif");
      ServletOutputStream out = res.getOutputStream();

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(
        "SELECT IMAGE FROM PICTURES WHERE PID = " + req.getParameter("PID"));

      if (rs.next()) {
        BufferedInputStream gifData =
          new BufferedInputStream(rs.getBinaryStream("image"));
        byte[] buf = new byte[4 * 1024];  // 4K buffer
        int len;
        while ((len = gifData.read(buf, 0, buf.length)) != -1) {
          out.write(buf, 0, len);
        }
      }
      else {
        res.sendError(res.SC_NOT_FOUND);
      }
    }
    catch(SQLException e) {
      // Report it
    }
  }
}
