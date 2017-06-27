import java.sql.*;
import java.text.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class BugIdCustomizer implements TableCustomizer {

  String bugViewServlet;

  public BugIdCustomizer(String bugViewServlet) {
    this.bugViewServlet = bugViewServlet;
  }

  public boolean accept(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    return ((columnType == Types.CHAR ||
             columnType == Types.VARCHAR ||
             columnType == Types.LONGVARCHAR) &&
            "bugid".equalsIgnoreCase(columnName));
  }

  public Element display(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    // Create a link to a servlet to display this bug
    String bugid = rs.getString(index);
    return new A(bugViewServlet + "?bugid=" + bugid, bugid);
  }
}
