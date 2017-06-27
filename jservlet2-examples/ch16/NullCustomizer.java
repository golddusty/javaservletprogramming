import java.sql.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class NullCustomizer implements TableCustomizer {
  public boolean accept(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    rs.getObject(index);
    return rs.wasNull();
  }

  public Element display(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException {
    // Print an "N/A" for null entries
    return new StringElement("N/A");
  }
}
