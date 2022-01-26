package Utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**This class provides methods for establishing database connection.*/
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08A3y";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    //Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    //Username and password
    private static final String username =  "U08A3y";
    private static final String password = "53689229563";

    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e)
        {
            //System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            //System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch(SQLException e)
        {
          //Do nothing
        }
        }

    public static Connection getConnection()
    {
        return conn;
    }

    }


