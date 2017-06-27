import java.sql.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

public interface TableCustomizer {
  public boolean accept(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException;

  public Element display(int columnType, String columnTypeName,
                        String columnName, ResultSet rs, int index)
                                                 throws SQLException;
}
