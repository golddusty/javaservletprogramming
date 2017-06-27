import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class DateCustomizer implements TableCustomizer {

  DateFormat fmt;

  public DateCustomizer(Locale loc) {
    fmt = DateFormat.getDateTimeInstance(
                     DateFormat.SHORT, DateFormat.SHORT, loc);
  }

  public boolean accept(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    return (columnType == Types.DATE || columnType == Types.TIMESTAMP);
  }

  public Element display(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    // Print a short date and time using the specified locale
    return new StringElement(fmt.format(rs.getDate(index)));
  }
}
