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
			if (righe <1)
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
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cancellaDipendente(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID dipendente da cancellare: ");
			pstmt.setInt(1, id);
			int righe = pstmt.executeUpdate();
			if (righe <1)
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
	 * Metodo per selezionare gli impiegati con ruolo=DIPENDENTE, mostra anche in quale team è assegnato
	 * 
	 * @param conn: connessione con il database
	 */
	public static void selezioneDipendente(Connection conn)
	{
		String query = "SELECT dipendenti.idDipendente, nome, cognome, idTeam FROM azienda.dipendenti" + " LEFT JOIN azienda.team ON dipendenti.idDipendente = team.idTeamLeader "
				+ " WHERE ruolo='DIPENDENTE';";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				//if (!rs.next())
					//System.out.println("Nessun dipendente.");
				while (rs.next())
				{
					int id = rs.getInt("idDipendente");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					int idTeam = rs.getInt("idTeam");
					System.out.printf("ID: %d | Nome: %s | Cognome: %s | idTeam: %d%n", id, nome, cognome, idTeam);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per selezionare tutti gli impiegati con relativi team
	 * 
	 * @param conn: connessione con il database
	 */
	public static void selezioneImpiegati(Connection conn)
	{
		String query = "SELECT dipendenti.idDipendente, nome, cognome, idTeam FROM azienda.dipendenti"
				+ " LEFT JOIN azienda.team_dipendentiassegnati ON dipendenti.idDipendente=team_dipendentiassegnati.idDipendente;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("C'è il nulla.");
				while (rs.next())
				{
					int id = rs.getInt("idDipendente");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					int idTeam = rs.getInt("idTeam");
					System.out.printf("ID: %d | Nome: %s | Cognome: %s | idTeam: %d%n", id, nome, cognome, idTeam);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per cambiare team a un dipendente con un determinato id
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioTeamDipendente(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.team_dipendentiassegnati SET idTeam = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID dipendente da aggiungere al team: ");
			System.out.println();
			int idTeam = FunzUtili.getInt(scanner, "Inserire ID del team: ");
			pstmt.setInt(1, idTeam);
			pstmt.setInt(2, id);
			int righe = pstmt.executeUpdate();
			if (righe <1)
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
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioStipendio(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET stipendio = ? WHERE idDipendente = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID dipendente a cui si vuole cambiare lo stipendio: ");
			double stipendio = FunzUtili.getDouble(scanner, "Inserire nuovo stipendio: ");
			pstmt.setDouble(1, stipendio);
			pstmt.setInt(2, id);
			int righe = pstmt.executeUpdate();
			if (righe <1)
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
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void promozioneInManager(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.dipendenti SET ruolo = 'MANAGER' WHERE idDipendente = ?;";
		String query2 = "INSERT INTO azienda.manager (idDipendente, bonus) VALUES (?,?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query); PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID del fortunato: ");
			pstmt.setInt(1, id);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Il destino ha deciso che il dipendente rimarrà tale.");
			}
			double bonus = FunzUtili.getDouble(scanner, "Inserire bonus: ");
			System.out.println("Ruolo aggiornato con successo");
			pstmt2.setInt(1, id);
			pstmt2.setDouble(2, bonus);
			int righe2 = pstmt2.executeUpdate();
			if (righe2 < 1)
			{
				throw new SQLException("Something's wrong.");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
