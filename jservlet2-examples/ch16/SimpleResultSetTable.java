import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.ecs.*;
import org.apache.ecs.html.*;

public class SimpleResultSetTable extends Table {

  public SimpleResultSetTable(ResultSet rs) throws SQLException {
    setBorder(1);

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
        addElement(new TD().addElement(rs.getString(i)));
      }
      addElement(row);
    }
  }
}
