package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Linguaggi
{
	/*
	 * Metodo per inserire un linguaggio
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @param scanner: scanner per prendere input utente
	 */
	public static void inserisciLinguaggio(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.linguaggi (nomeLinguaggio) VALUES (?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Inserisci il nome del linguaggio di programmazione: ");
			String nome = scanner.nextLine();
			pstmt.setString(1, nome);
			int righe=pstmt.executeUpdate();
			if(righe <1)
				throw new SQLException("Creazione linguaggio fallita, nessuna riga aggiunta.");
			System.out.println("Success.");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per cancellare un linguaggio con un determinato id
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @param scanner: scanner per prendere input utente
	 */
	public static void cancellaLinguaggio(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.linguaggi WHERE idLinguaggio = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID linguaggio da cancellare: \n");
			pstmt.setInt(1, id);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Cancellazione linguaggio fallita, nessuna riga rimossa.");
			}
			System.out.println("Linguaggio cancellato con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per selezionare i linguaggi e le loro informazioni
	 * 
	 * @param conn: connessione con il database
	 */
	public static void selezioneLinguaggio(Connection conn)
	{
		String query = "SELECT * FROM azienda.linguaggi;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					int idLing = rs.getInt("idLinguaggio");
					String nomeLing = rs.getString("nomeLinguaggio");
					System.out.printf("ID: %d | Nome: %s %n", idLing, nomeLing);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
