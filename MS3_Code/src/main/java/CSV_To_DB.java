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
        Statement st; // SQL Statement object used for executing any static SQL statement, we can use this initialized object to evoke other method and 
        // libraries of SQL and SQLLite
        String query = "CREATE TABLE c(" // Create the Table and store the information in a String, each column Header is made from the first row information
        		// in our case the letters, and each column is further detailed by the SQL data type, since column G is decimal number it is the only one column
        		// that does need to be stored as a text block and instead SQL will  process it as a DOUBLE, decimal type. Also Our CSV has no primary key
        		// to uniquely ID the each row of data, so non was included. Which is not best practice. We would need to create a primary before parsing the CSV
        		// or while making the Table.
                + "A  TEXT   NOT NULL, "
                + "B  TEXT   NOT NULL, "
                + "C  TEXT   NOT NULL, "
                + "D  TEXT   NOT NULL, "
                + "E  TEXT   NOT NULL, "
                + "F  TEXT   NOT NULL, "
                + "G  DOUBLE   NOT NULL, "
                + "H  TEXT   NOT NULL, "
                + "I  TEXT   NOT NULL, "
                + "J  TEXT   NOT NULL, "
                + ");";
        System.out.println("QUERY: " + query);
        try 
        {
            st = conn.createStatement(); // Using our connection object, tells the SQLLite DB that we are creating an SQL statement object for sending commands to our DB
            st.executeUpdate(query); // Update the statement object, which will update the SQL Database with the Table we just made
        } catch (SQLException e) { e.printStackTrace();} // Else throw an SQL exception, since our Object is a Database Object, and then 
        // print the printStackTrace of Stack Java.lang.Throwable class to print detail where and what type of error occurred      
   }
	
   public static void add_To_DB(Connection conn, String csv) // Method for adding data by rows from the CSV to the DB, arguments passed are the connection object and our CSV
   {
	    BufferedReader r = null;  // Initialize a BufferReader request to to read a character or byte stream
	    int jdbcbatch = 0;  
	    int tot = 0; 
	    PreparedStatement pt = null; // Initialize an SQL precomplied object and store that null object in a variable. We will use this object multiple times later
	    
	    String sql = "INSERT INTO c(" // Sets up a format for how each row of CSV information is to be added to the CSV, here we are using prepared statements to protect the data
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
	        pt = conn.prepareStatement(sql); // Use the connection object and tell SQLLite DB that we want to INSERT into our created table formated values.
	        r = new BufferedReader(new InputStreamReader(new FileInputStream(csv), "UTF-8")); // Read in the raw CSV data from the file system using 
	        // FileInputStream, then decode the bytes into the 8-bit Unicode character set before passing it to our BufferReader

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
          Connection conn = null; //Set initial connection object to null before up updating later
          try 
          {
              Class.forName("org.sqlite.JDBC");
              conn = DriverManager.getConnection(url); //Update our connection object using the Driver Manager which is where JDBC Drivers used by their applications
              // are found, basically it uses JDBC to establish a link with the DB
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
