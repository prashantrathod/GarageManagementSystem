package gmsis;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
   Connection conn=null;
   ResultSet rs=null;
   PreparedStatement stmt=null;  
   String sql= null;

   public DBConnection(){
   }

   public void openConn(){
       try {
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
       } catch ( ClassNotFoundException | SQLException e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
       }
   }

   public void closeConn(){
       if (rs != null) {
           try {
               rs.close();
           } catch (SQLException e) { /* ignored */}
       }
       if (stmt != null) {
           try {
               stmt.close();
           } catch (SQLException e) { /* ignored */}
       }
       if (conn != null) {
           try {
               conn.close();
           } catch (SQLException e) { /* ignored */}
       }
   }

   public void prepStmt(String sql){
       try {
           stmt = conn.prepareStatement(sql);
       } catch (SQLException ex) {
           Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

   public ResultSet exQuery(){
       try {
           rs = stmt.executeQuery();
       } catch (SQLException ex) {
           Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
       }
       return rs;
   }
   
   public void exUpdate(){
       try {
           stmt.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
       }

   }
  
}
