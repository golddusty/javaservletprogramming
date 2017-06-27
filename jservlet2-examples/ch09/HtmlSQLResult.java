import java.sql.*;

public class HtmlSQLResult {
  private String sql;
  private Connection con;

  public HtmlSQLResult(String sql, Connection con) {
    this.sql = sql;
    this.con = con;
  }

  public String toString() {  // can be called at most once
    StringBuffer out = new StringBuffer();

    // Uncomment the following line to display the SQL command at start of table
    // out.append("Results of SQL Statement: " + sql + "<P>\n");

    try {
      Statement stmt = con.createStatement();

      if (stmt.execute(sql)) {
        // There's a ResultSet to be had
        ResultSet rs = stmt.getResultSet();
        out.append("<TABLE>\n");

        ResultSetMetaData rsmd = rs.getMetaData();

        int numcols = rsmd.getColumnCount();
		
        // Title the table with the result set's column labels
        out.append("<TR>");
        for (int i = 1; i <= numcols; i++)
          out.append("<TH>" + rsmd.getColumnLabel(i));
        out.append("</TR>\n");

        while(rs.next()) {
          out.append("<TR>");  // start a new row
          for(int i = 1; i <= numcols; i++) {
            out.append("<TD>");  // start a new data element
            Object obj = rs.getObject(i);
            if (obj != null)
              out.append(obj.toString());
            else
              out.append("&nbsp;");
            }
          out.append("</TR>\n");
        }

        // End the table
        out.append("</TABLE>\n");
      }
      else {
        // There's a count to be had
        out.append("<B>Records Affected:</B> " + stmt.getUpdateCount());
      }
    }
    catch (SQLException e) {
      out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage());
    }
		
    return out.toString();
  }
}
