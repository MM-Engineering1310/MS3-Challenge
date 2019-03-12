import java.sql.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CSV_To_DB
{
	public static void main(String[] args) // Main Method which class other functions of the program
	{
        Connection conn = connect("C:/Users/Master/.m2/repository/org/xerial/sqlite-jdbc/3.16.1/sqlite-jdbc-3.16.1-sources.jar"); // Connection is a object session
        // that establishes a connection to SQL databases, and the connect variable here is followed by the host local path
        createT(conn); // Calls the method for creating the Table in our Database
        add_To_DB(conn, "C:\\Users\\Master\\Downloads\\ms3Interview (2).csv"); // Calls the method for adding the CSV data to the DB, here the 2nd argument
        //passed is the local path to the CSV File
        disconn(conn); // Calls the method to close the connection with the SQLLite dependency 
    }
	public static void createT(Connection conn) // Creates the Table in using the SQLLite Library by passing in the connection argument from our Main Method
	{
        Statement st; // SQL Statement object used for executing any static SQL statement, we can use this initialized object to enokve other method and 
        // libraries of SQL and SQLLite
        String query = "CREATE TABLE c(" 
                + "A  TEXT   NOT NULL, "
                + "B  TEXT   NOT NULL, "
                + "C  TEXT   NOT NULL, "
                + "D  TEXT   NOT NULL, "
                + "E  TEXT   NOT NULL, "
                + "F  TEXT   NOT NULL, "
                + "G  INT   NOT NULL, "
                + "H  TEXT   NOT NULL, "
                + "I  TEXT   NOT NULL, "
                + "J  TEXT   NOT NULL, "
                + ");";
        System.out.println("QUERY: " + query);
        try 
        {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) { e.printStackTrace();} // Else throw an SQL exception, since our Object is a Database Object, and then 
        // print the printStackTrace of Stack Java.lang.Throwable class to print detail where and what type of error occurred      
   }
	
   public static void add_To_DB(Connection conn, String csv)
   {
	    BufferedReader r = null;
	    int jdbcbatch = 0;
	    int tot = 0;
	    PreparedStatement pt = null;
	    
	    String sql = "INSERT INTO c("
               + "A, "
               + "B, "
               + "C, "
               + "D, "
               + "E, "
               + "F, "
               + "G, "
               + "H, "
               + "I, "
               + "J, "
               + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	    
	    try 
	    {
	        pt = conn.prepareStatement(sql);
	        r = new BufferedReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"));

            for(String line; (line = r.readLine()) != null;) 
            {
             
              String[] cols = line.split(",");
              String a = cols[0];
              String b = cols[1];
              String c = cols[0];
              String d = cols[1];
              String e = cols[0];
              String f = cols[1];
              String g = cols[0];
              String h = cols[1];
              String i = cols[0];
              String j = cols[1];
              
              String q = "INSERT INTO c VALUES (" +
                      "'" + a + "', " +
                      "'" + b + "', " +
                      "'" + c +  "', " +
                      "'" + d + "', " +
                      "'" + e + "', " +
                      "'" + f +  "', " +
                      "'" + g + "', " +
                      "'" + h + "', " +
                      "'" + i +  "', " +
                      "'" + j + "');";
          
	          pt.addBatch(q);
              ++jdbcbatch;
              ++tot;
	        
              if (jdbcbatch == 1000) 
              {
                pt.executeBatch(); 
                System.out.println("Total: " + tot);
                jdbcbatch = 0;
              }
            }
            if (jdbcbatch > 0) 
            {
              pt.executeBatch(); 
              System.out.println("Total: " + tot);
            }

        } catch (Exception e) { e.printStackTrace();} 
	    finally 
        {
          try
          { 
            if(pt != null) { pt.close(); }
          } catch (SQLException e) { e.printStackTrace(); } // Else throw an SQL exception, since our Object is a Database Object, and then 
          // print the printStackTrace of Stack Java.lang.Throwable class to print detail where and what type of error occurred
        }  
   }
    
   private static Connection connect(String path) // Method for establishing a connection to the SQLLite libraries and DB, argument passed is the connection object with
   // local path to the SQLLite dependency
   {

          String url = "jdbc:sqlite:" + path;
          Connection conn = null;
          try 
          {
              Class.forName("org.sqlite.JDBC");
              conn = DriverManager.getConnection(url);
          } catch (Exception e) { e.printStackTrace(); }
          return conn;
   }

   private static void disconn(Connection conn) // Method to disconnect from the SQLlite framework, argument passed is our connection object
   {
          try 
          { 
        	  if(conn != null){ conn.close(); // If our connection object is not null call close method on connection object
          }
          } catch(SQLException e){ e.printStackTrace(); } // Else throw an SQL exception, since our Object is a Database Object, and then 
          // print the printStackTrace of Stack Java.lang.Throwable class to print detail where and what type of error occurred
   }
}
