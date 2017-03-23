/**
 * Created by NeNe on 20/03/15.
 */

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.Properties;

public class SQLiteDB {

    public Connection connectDB() {


        Connection connection = null;
        Properties connectionProperties = new Properties();
        String connectionString = "jdbc:sqlite:gsys.db";

        ResultSet rs = null;


        try {
            // Class.forName("org.sqlite.JDBC");
            //  c = DriverManager.getConnection("jdbc:sqlite:gsys.db")

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connectionProperties = config.toProperties();
            connection = DriverManager.getConnection(connectionString, connectionProperties);
            System.out.println("Opened database successfully");
      /*      DatabaseMetaData meta = connection.getMetaData();

            rs = meta.getExportedKeys(connection.getCatalog(), null, "warranty");
            while (rs.next()) {
                String fkTableName = rs.getString("FKTABLE_NAME");
                String fkColumnName = rs.getString("FKCOLUMN_NAME");
                int fkSequence = rs.getInt("KEY_SEQ");
                System.out.println("getExportedKeys(): fkTableName=" + fkTableName);
                System.out.println("getExportedKeys(): fkColumnName=" + fkColumnName);
                System.out.println("getExportedKeys(): fkSequence=" + fkSequence);
*/

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return connection;
    }


    public void writeDB(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            c = connectDB();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }


   /* public void configureDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            c = connectDB();
            System.out.println("Opened database successfully");
            System.out.println("Table Creation");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Vehicle "
                    + " (vid            INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " vehicle_type    TEXT    NOT NULL, "
                    + " model           TEXT    NOT NULL, "
                    + " make            TEXT    NOT NULL, "
                    + " engine_size     INT     NOT NULL, "
                    + " fuel_type       TEXT    NOT NULL, "
                    + " colour          TEXT    NOT NULL, "
                    + " mot_date        TEXT    NOT NULL, "
                    + " ls_date         TEXT    NOT NULL, "
                    + " mileage_history INT     NOT NULL, "
                    + " isWarranty      TEXT    NOT NULL, "
                    + " w_name          TEXT    NOT NULL, "
                    + " w_address       TEXT    NOT NULL, "
                    + " w_expire_date   TEXT    NOT NULL)";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Table created successfully");
    }*/
}
