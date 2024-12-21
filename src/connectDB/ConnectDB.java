package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.main;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect()  {
		String url = "jdbc:sqlserver://localhost:1433;databasename=Quanlyquancaphe;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String password = "sapassword";
		try {
			 con = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
	}
	public static Connection getConnection() {
		return con;
		
	}
	
}
