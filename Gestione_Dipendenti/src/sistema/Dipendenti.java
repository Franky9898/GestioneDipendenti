package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class Dipendenti
{
	enum Ruolo
	{
		DIPENDENTE, MANAGER, SVILUPPATORE
	}

	/*
	 * Metodo per inserire un dipendente
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
			String ruolo = "DIPENDENTE";
			double stipendio = FunzUtili.getDouble(scanner, "Inserisci stipendio dipendente: ");
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendio);
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
	 * Metodo per cancellare un dipendente con un determinato id
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cancellaDipendente(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner,"Selezionare ID dipendente da cancellare: " );
			pstmt.setInt(1, id);
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

	/*
	 * Metodo per selezionare gli impiegati con ruolo=DIPENDENTE
	 * @param conn: connessione con il database
	 */
	public static void selezioneDipendente(Connection conn)
	{
		String query = "SELECT nome, cognome, idTeam FROM azienda.dipendenti"
				+ "INNER JOIN azienda.team_dipendentiassegnati ON dipendenti.idDipendente=team_dipendentiassegnati.idDipendente "
				+ "WHERE ruolo='DIPENDENTE';";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("Nessun dipendente.");
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					int idTeam = rs.getInt("idTeam");
					System.out.printf("Nome: %s | Cognome: %s | idTeam: %d%n", nome, cognome, idTeam);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * Metodo per selezionare gli impiegati con ruolo=DIPENDENTE
	 * @param conn: connessione con il database
	 */
	public static void selezioneImpiegati(Connection conn)
	{
		String query = "SELECT nome, cognome, idTeam FROM azienda.dipendenti"
				+ "INNER JOIN azienda.team_dipendentiassegnati ON dipendenti.idDipendente=team_dipendentiassegnati.idDipendente;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("C'è il nulla.");
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					int idTeam = rs.getInt("idTeam");
					System.out.printf("Nome: %s | Cognome: %s | idTeam: %d%n", nome, cognome, idTeam);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per cambiare team a un dipendente con un determinato id
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioTeamDipendente(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET idTeam = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID dipendente da aggiungere al team: ");
			System.out.println();
			int idTeam = FunzUtili.getInt(scanner, "Inserire ID del team: ");
			pstmt.setInt(1, idTeam);
			pstmt.setInt(2, id);
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
	/*
	 * Metodo per cambiare stipendio a un dipendente con un determinato id
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioStipendio(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET stipendio = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID dipendente a cui si vuole cambiare lo stipendio: " );
			double stipendio = FunzUtili.getDouble(scanner, "Inserire nuovo stipendio: ");
			pstmt.setDouble(1, stipendio);
			pstmt.setInt(2, id);
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

	/*
	 * Metodo per cambiare ruolo in MANAGER a un dipendente con un determinato id
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void promozioneInManager(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET ruolo = 'MANAGER' WHERE idDipendente = ?;";
		String query2 = "INSERT INTO azienda.manager (idDipendente, bonus) VALUES (?,?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query); PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID del fortunato: ");
			pstmt.setInt(1, id);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Il destino ha deciso che il dipendente rimarrà tale.");
			}
			System.out.println("Ruolo aggiornato con successo");
			double bonus = FunzUtili.getDouble(scanner, "Inserire bonus: ");
			System.out.println("Inserire idTeamGestito: ");
			String idTeam = scanner.nextLine();
			int idTeamI = Integer.parseInt(idTeam);
			pstmt2.setInt(1, id);
			pstmt2.setDouble(2, bonus);
			pstmt2.setInt(3, idTeamI);
			int righe2 = pstmt2.executeUpdate();
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
