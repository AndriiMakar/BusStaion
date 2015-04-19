package mainPac;
import java.sql.*;
import javax.swing.*;

public class MySQLConnection {
	
	Connection conn=null;
	static String login="root";
	static String pas="root";
	static String url="jdbc:mysql://localhost:3306/bus_station";
	
	public static Connection dbConnector()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,login,pas);
			JOptionPane.showMessageDialog(null, "Connection good");
			return conn;
		}catch(Exception e)
		
		{
		JOptionPane.showMessageDialog(null, e);
		return null;
		}

	}
}

