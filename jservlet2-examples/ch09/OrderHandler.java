import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class OrderHandler extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    Connection con = null;
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con = DriverManager.getConnection("jdbc:odbc:ordersdb", "user", "passwd");

      // Turn on transactions
      con.setAutoCommit(false);

      Statement stmt = con.createStatement();
      stmt.executeUpdate(
        "UPDATE INVENTORY SET STOCK = (STOCK - 10) WHERE PRODUCTID = 7");
      stmt.executeUpdate(
        "UPDATE SHIPPING SET SHIPPED = (SHIPPED + 10) WHERE PRODUCTID = 7");

      chargeCard();  // method doesn't actually exist...

      con.commit();
      out.println("Order successful!  Thanks for your business!");
    }
    catch (Exception e) {
      // Any error is grounds for rollback
      try {
        con.rollback();
      }
      catch (SQLException ignored) { }
      out.println("Order failed. Please contact technical support.");
    }
    finally {
      // Clean up.
      try {
        if (con != null) con.close();
      }
      catch (SQLException ignored) { }
    }
  }
}
