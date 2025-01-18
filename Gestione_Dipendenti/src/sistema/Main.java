package sistema;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public class Main
{
	private static final String URL = "jdbc:mysql://localhost:3306/azienda";
	private static final String USER = "root";
	private static final String PASSWORD = "3804645479Hr";
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD))
		{
			Menu.menuGenerale(conn, scanner);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		scanner.close();
	}
}