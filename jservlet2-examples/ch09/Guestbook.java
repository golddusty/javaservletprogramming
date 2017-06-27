import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.CacheHttpServlet;

public class Guestbook extends CacheHttpServlet {

  static final String SELECT_ALL =
    "SELECT name, email, cmt, id FROM guestlist ORDER BY id DESC";

  static final String INSERT = 
    "INSERT INTO guestlist (id, name, email, cmt) " + 
    "VALUES (?, ?, ?, ?)";

  private long lastModified = 0;  // Time database last changed
  private ConnectionPool pool;
  
  // Get a pointer to a connection pool
  public void init() throws ServletException {
    try {
      ServletContext context = getServletContext();
      synchronized (context) {
        // A pool may already be saved as a context attribute
        pool = (ConnectionPool) context.getAttribute("pool");
        if (pool == null) {
          // Construct a pool using our context init parameters
          // connection.driver, connection.url, user, password, etc
          pool = new ConnectionPool(new ContextProperties(context), 3);
          context.setAttribute("pool", pool);
        }
      }
    }
    catch (Exception e) {
      throw new UnavailableException(
      "Failed to fetch a connection pool from the context: " + e.getMessage());
    }
  }

  // Display the current entries, then ask for a new entry
  public void doGet(HttpServletRequest req, HttpServletResponse res) 
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    printHeader(out);
    printForm(out);
    printMessages(out);
    printFooter(out);
  }

  // Add a new entry, then dispatch back to doGet()
  public void doPost(HttpServletRequest req, HttpServletResponse res) 
                                throws ServletException, IOException {
    handleForm(req, res);
    doGet(req, res);
  }

  private void printHeader(PrintWriter out) {
    out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
    out.println("<BODY>");
  }

  private void printForm(PrintWriter out) {
    out.println("<FORM METHOD=POST>");  // posts to itself
    out.println("<B>Please submit your feedback:</B><BR>");
    out.println("Your name: <INPUT TYPE=TEXT NAME=name><BR>");
    out.println("Your email: <INPUT TYPE=TEXT NAME=email><BR>");
    out.println("Comment: <INPUT TYPE=TEXT SIZE=50 NAME=comment><BR>");
    out.println("<INPUT TYPE=SUBMIT VALUE=\"Send Feedback\"><BR>");
    out.println("</FORM>");
    out.println("<HR>");
  }

  // Read the messages from the database, and print
  private void printMessages(PrintWriter out) throws ServletException {
    String name, email, comment;

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      con = pool.getConnection();
      stmt = con.createStatement();
      rs = stmt.executeQuery(SELECT_ALL);

      while (rs.next()) {
        name = rs.getString(1);
        if (rs.wasNull() || name.length() == 0) name = "Unknown user";
        email = rs.getString(2);
        if (rs.wasNull() || email.length() == 0) name = "Unknown email";
        comment = rs.getString(3);
        if (rs.wasNull() || comment.length() == 0) name = "No comment";
        out.println("<DL>");
        out.println("<DT><B>" + name + "</B> (" + email + ") says");
        out.println("<DD><PRE>" + comment + "</PRE>");
        out.println("</DL>");
      }
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
    finally {
      try {
        if (stmt != null) stmt.close();
      }
      catch (SQLException ignored) { }
      pool.returnConnection(con);
    }
  }

  private void printFooter(PrintWriter out) {
    out.println("</BODY>");
  }

  // Save the new comment to the database
  private void handleForm(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException {
    String name = req.getParameter("name");
    String email = req.getParameter("email");
    String comment = req.getParameter("comment");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = pool.getConnection();
      // Use a prepared statement for automatic string escaping
      pstmt = con.prepareStatement(INSERT);
      long time = System.currentTimeMillis();
      pstmt.setString(1, Long.toString(time));
      pstmt.setString(2, name);
      pstmt.setString(3, email);
      pstmt.setString(4, comment);
      pstmt.executeUpdate();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
      }
      catch (SQLException ignored) { }
      pool.returnConnection(con);
    }

    // Make note we have a new last modified time
    lastModified = System.currentTimeMillis();
  }

  public long getLastModified(HttpServletRequest req) {
    return lastModified;  // supports CacheHttpServlet
  }
}
