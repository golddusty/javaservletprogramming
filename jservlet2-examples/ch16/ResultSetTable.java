import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class ResultSetTable extends Table {

  public ResultSetTable(ResultSet rs) throws SQLException {
    this(rs, null);
  }

  public ResultSetTable(ResultSet rs, TableCustomizer[] customizers)
                                      throws SQLException {
    setBorder(1);

    if (customizers == null) {
      customizers = new TableCustomizer[0];
    }

    ResultSetMetaData rsmd = rs.getMetaData();
    int colCount = rsmd.getColumnCount();

    TR row = new TR();
    for (int i = 1; i <= colCount; i++) {
      addElement(new TH().addElement(rsmd.getColumnName(i)));
    }
    addElement(row);

    while (rs.next()) {
      row = new TR();
      for (int i = 1; i <= colCount; i++) {
        TD td = new TD();
        int columnType = rsmd.getColumnType(i);
        String columnTypeName = rsmd.getColumnTypeName(i);
        String columnName = rsmd.getColumnName(i);

        // Give each customizer a chance to control output
        boolean customized = false;
        for (int c = 0; c < customizers.length; c++) {
          TableCustomizer customizer = customizers[c];
          if (customizer.accept(columnType, columnTypeName,
                                columnName, rs, i)) {
            td.addElement(customizer.display(columnType, columnTypeName,
                                             columnName, rs, i));
            customized = true;
            break;
          }
        }

        // If no customizer wanted the job, display the value as a String
        if (!customized) {
          td.addElement(rs.getString(i));
        }

        addElement(td);
      }
      addElement(row);
    }
  }
}
