package dashboard;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {
	
	
	public static Connection doConnect() 
	{
		Connection con=null;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/ newspaper","root","Temp#23213126");
			
			System.out.println("connected...............");
		}
		
		catch(Exception exp) {
			System.out.println(exp);
		}
	
		return con ; 
	}
	public static void main(String [] args)
	{
		
		doConnect();
	}	

	

}
