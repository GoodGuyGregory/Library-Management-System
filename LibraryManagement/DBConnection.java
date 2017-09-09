import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
          
	  static Connection con=null;
	  public static Connection getConnection()
	  {
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		      } 
		  catch (Exception e) {
			e.printStackTrace();
		}	
			
		  return con;
	  }
}
