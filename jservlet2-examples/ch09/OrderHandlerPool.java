import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class OrderHandlerPool extends HttpServlet {
  private ConnectionPool pool;

  public void init() throws ServletException {
    try {
      pool = new ConnectionPool("oracle.jdbc.driver.OracleDriver",
                             "jdbc:oracle:oci7:orders", "user", "passwd", 5);
    }
    catch (Exception e) {
      throw new UnavailableException("Couldn't create connection pool");
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    Connection con = null;

    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    try {
      con = pool.getConnection();

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
      catch (Exception ignored) { }
      out.println("Order failed. Please contact technical support.");
    }
    finally {
      if (con != null) pool.returnConnection(con);
    }
  }
}
