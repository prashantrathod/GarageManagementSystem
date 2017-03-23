package gmsis;

import java.sql.*;

public class SQLiteJDBC
{
  
    Connection c = null;
   
    public static Connection dbConnector(){
    
    try {
      Class.forName("org.sqlite.JDBC");
      Connection c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Prashant\\Desktop\\1E.db");
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
     
    }
    System.out.println("Opened database successfully");
    return null;
  }
    
}
