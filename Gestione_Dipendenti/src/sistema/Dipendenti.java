package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Dipendenti
{
	enum Ruolo
	{
		DIPENDENTE, MANAGER, SVILUPPATORE
	}

	/*
	 * Metodo per inserire un dipendente quando si è consapevoli del team di cui farà parte
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @param scanner: scanner per prendere input utente
	 */

	public static void inserimentoDipendenteConTeam(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio, idTeam)" + "VALUES (?,?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			boolean continua = true;
			String ruolo = "DIPENDENTE";
			while (continua) // Converte la stringa passata in console in enum Ruolo. Diventerà un metodo
			{
				System.out.println("Inserisci ruolo dipendente: ");
				ruolo = scanner.nextLine().toUpperCase();
				for (Ruolo r : Ruolo.values()) 
				{
					if(r.name().equals(ruolo))
						continua = false;
				}
			} 
			System.out.println("Inserisci stipendio dipendente: ");
			String stipendio = scanner.nextLine();
			double stipendioD = Double.parseDouble(stipendio);
			System.out.println("Inserisci idTeam dipendente: ");
			String idTeam = scanner.nextLine();
			int idTeamI = Integer.parseInt(idTeam);

			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendioD);
			pstmt.setInt(5, idTeamI);

			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Creazione cliente fallita, nessuna riga aggiunta.");
			}
			System.out.println("Dipendente aggiunto con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per inserire un dipendente quando NON si è consapevoli del team di cui farà parte
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @param scanner: scanner per prendere input utente
	 */

	public static void inserimentoDipendente(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + "VALUES (?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			boolean continua = true;
			String ruolo = "DIPENDENTE";
			while (continua) // Converte la stringa passata in console in enum Ruolo. Diventerà un metodo
			{
				System.out.println("Inserisci ruolo dipendente: ");
				ruolo = scanner.nextLine().toUpperCase();
				for (Ruolo r : Ruolo.values()) 
				{
					if(r.name().equals(ruolo))
						continua = false;
				}
			} 
			System.out.println("Inserisci stipendio dipendente: ");
			String stipendio = scanner.nextLine();
			double stipendioD = Double.parseDouble(stipendio);
			System.out.println("Inserisci idTeam dipendente: ");

			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendioD);

			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Creazione cliente fallita, nessuna riga aggiunta.");
			}
			System.out.println("Dipendente aggiunto con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
