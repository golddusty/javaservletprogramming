import java.sql.*;
import java.util.*; 

public class ConnectionPool {
  private Hashtable connections = new Hashtable();
  private Properties props;

  public ConnectionPool(Properties props, int initialConnections)
                   throws SQLException, ClassNotFoundException {
    this.props = props;
    initializePool(props, initialConnections);
  }

  public ConnectionPool(String driverClassName, String dbURL,
                        String user, String password,
                        int initialConnections)
                   throws SQLException, ClassNotFoundException {
    props = new Properties();
    props.put("connection.driver", driverClassName);
    props.put("connection.url", dbURL);
    props.put("user", user);
    props.put("password", password);
    initializePool(props, initialConnections);
  }                                                                  
                                                                     
  public Connection getConnection() throws SQLException {            
    Connection con = null;                                           
                                                                     
    Enumeration cons = connections.keys();                           
                                                                     
    synchronized (connections) {                                    
      while(cons.hasMoreElements()) {                                
        con = (Connection)cons.nextElement();                        
                                                                     
        Boolean b = (Boolean)connections.get(con);                   
        if (b == Boolean.FALSE) {                                    
          // So we found an unused connection.                       
          // Test its integrity with a quick setAutoCommit(true) call.
          // For production use, more testing should be performed,   
          // such as executing a simple query.                       
          try {                                                      
            con.setAutoCommit(true);                                 
          }                                                          
          catch(SQLException e) {                                    
            // Problem with the connection, replace it.              
            connections.remove(con);                                 
            con = getNewConnection();
          }                                                          
          // Update the Hashtable to show this one's taken           
          connections.put(con, Boolean.TRUE);                        
          // Return the connection                                   
          return con;                                                
        }                                                            
      }                                                              
                                                                     
      // If we get here, there were no free connections.  Make one more.
      // A more robust connection pool would have a maximum size limit,
      // and would reclaim connections after some timeout period
      con = getNewConnection();
      connections.put(con, Boolean.FALSE);
      return con;
    }                                                                
  }                                                                  
                                                                     
  public void returnConnection(Connection returned) {                
    if (connections.containsKey(returned)) {                         
      connections.put(returned, Boolean.FALSE);                      
    }                                                                
  }                                                                  

  private void initializePool(Properties props, int initialConnections)
                   throws SQLException, ClassNotFoundException {
    // Load the driver
    Class.forName(props.getProperty("connection.driver"));

    // Put our pool of Connections in the Hashtable                  
    // The FALSE value indicates they're unused                      
    for(int i = 0; i < initialConnections; i++) {                    
      Connection con = getNewConnection();
      connections.put(con, Boolean.FALSE);                                
    }                                                                
  }

  private Connection getNewConnection() throws SQLException {
    return DriverManager.getConnection(
      props.getProperty("connection.url"), props);
  }
}                                                                    
