import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
          
	  static Connection con;
	  public static Connection getConnection()
	  {
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","root");
		      } 
		  catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Unable to Connect To Database");
		}	
			
		  return con;
	  }
}
