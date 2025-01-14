package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.ResultSet;

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
					if (r.name().equals(ruolo))
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
					if (r.name().equals(ruolo))
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

	public static void cancellaDipendente(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Selezionare ID dipendente da cancellare: ");
			String id = scanner.nextLine();
			int idI = Integer.parseInt(id);
			pstmt.setInt(1, idI);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Cancellazione cliente fallita, nessuna riga rimossa.");
			}
			System.out.println("Dipendente cancellato con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void selezioneDipendente(Connection conn, Scanner scanner)
	{
		String query = "SELECT nome, cognome FROM azienda.dipendenti WHERE ruolo='DIPENDENTE'; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					System.out.printf("Nome: %s | Cognome: %s%n", nome, cognome);
				}
				if (!rs.next())
					System.out.println("Nessun dipendente.");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void selezioneDipendenteTutti(Connection conn)
	{
		String query = "SELECT nome, cognome FROM azienda.dipendenti; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					System.out.printf("Nome: %s | Cognome: %s%n", nome, cognome);
				}
				if (!rs.next())
					System.out.println("C'è il nulla.");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void cambioTeamDipendente(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET idTeam = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Selezionare ID dipendente da aggiungere al team: ");
			String id = scanner.nextLine();
			int idI = Integer.parseInt(id);
			System.out.println("Inserire ID del team: ");
			String idTeam = scanner.nextLine();
			int idTeamI = Integer.parseInt(idTeam);
			pstmt.setInt(1, idTeamI);
			pstmt.setInt(2, idI);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Fallimento.");
			}
			System.out.println("Dipendente inserito nel team con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void cambioStipendio(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET stipendio = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Selezionare ID dipendente a cui si vuole cambiare lo stipendio: ");
			String id = scanner.nextLine();
			int idI = Integer.parseInt(id);
			System.out.println("Inserire nuovo stipendio: ");
			String stipendio = scanner.nextLine();
			double stipendioD = Double.parseDouble(stipendio);
			pstmt.setDouble(1, stipendioD);
			pstmt.setInt(2, idI);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Il destino ha deciso che il dipendente non avrà una busta pagata.");
			}
			System.out.println("Stipendio aggiornato con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void promozioneInManager(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET roulo = 'MANAGER' WHERE idDipendente = ?;";
		String query2 = "INSERT INTO azienda.manager (idDipendente, bonus, idTeamGestito) VALUES (?,?,?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query); PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			System.out.println("Selezionare ID del fortunato: ");
			String id = scanner.nextLine();
			int idI = Integer.parseInt(id);
			pstmt.setInt(1, idI);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Il destino ha deciso che il dipendente rimarrà tale.");
			}
			System.out.println("Ruolo aggiornato con successo");
			System.out.println("Inserire bonus: ");
			String bonus = scanner.nextLine();
			double bonusD = Double.parseDouble(bonus);
			System.out.println("Inserire idTeamGestito: ");
			String idTeam = scanner.nextLine();
			int idTeamI = Integer.parseInt(idTeam);
			pstmt2.setInt(1, idI);
			pstmt2.setDouble(2, bonusD);
			pstmt2.setInt(3, idTeamI);
			int righe2 = pstmt.executeUpdate();
			if (righe2 == 0)
			{
				throw new SQLException("Something's wrong.");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
