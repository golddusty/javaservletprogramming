import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class NumberCustomizer implements TableCustomizer {

  NumberFormat fmt;

  public NumberCustomizer(Locale loc) {
    fmt = NumberFormat.getNumberInstance(loc);
  }

  public boolean accept(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    return (columnType == Types.TINYINT ||
            columnType == Types.SMALLINT ||
            columnType == Types.INTEGER ||
            columnType == Types.BIGINT ||
            columnType == Types.REAL ||
            columnType == Types.FLOAT ||
            columnType == Types.DOUBLE);
  }

  public Element display(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    // Print the number using the specified locale
    if (columnType == Types.TINYINT ||
        columnType == Types.SMALLINT ||
        columnType == Types.INTEGER ||
        columnType == Types.BIGINT) {
      return new StringElement(fmt.format(rs.getLong(index)));
    }
    else {
      return new StringElement(fmt.format(rs.getDouble(index)));
    }
  }
}
