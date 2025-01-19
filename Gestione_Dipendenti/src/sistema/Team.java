package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Team
{
	/*
	 * Metodo per inserire un team
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void inserireTeam(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.team " + " (nomeTeam, idTeamLeader) " + " VALUES (?,?)";
		String query2 = "INSERT INTO azienda.team_dipendentiassegnati (idTeam, idDipendente) VALUES (?,?);";
		int idTeam = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			System.out.println("Inserisci nome del Team");
			String nomeTeam = scanner.nextLine();
			int idTeamLeader = FunzUtili.getInt(scanner, "Inserisci l'ID del TeamLeader: \n");
			pstmt.setString(1, nomeTeam);
			pstmt.setInt(2, idTeamLeader);

			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Creazione team fallita, nessuna riga aggiunta.");
			}
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					idTeam = generatedKeys.getInt(1);
				} else
				{
					throw new SQLException("Creazione relazione team-manager fallita, ID non recuperato.");
				}
			}

			pstmt2.setInt(1, idTeam);
			pstmt2.setInt(2, idTeamLeader);
			int righe2 = pstmt2.executeUpdate();
			if (righe2 < 1)
			{
				throw new SQLException("Inserimento team-manager fallito, nessuna riga aggiunta.");
			}
			System.out.println("Team aggiunto con successo.");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per visualizzare i team con relativo team leader
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void readAllTeam(Connection conn, Scanner scanner)
	{
		String query = "SELECT * FROM azienda.team;";
		try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next())
			{
				int idTeam = rs.getInt("idTeam");
				String nomeTeam = rs.getString("nomeTeam");
				int idTeamLeader = rs.getInt("idTeamLeader");
				System.out.printf("ID team : %d | Nome : %s  | ID Team Leader : %d%n", idTeam, nomeTeam, idTeamLeader);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per inserire un dipendente in un team
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void inserireDipendenteInTeam(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.team_dipendentiassegnati (idTeam, idDipendente) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idTeam = FunzUtili.getInt(scanner, "Inserisci id team: \n");
			int idDip = FunzUtili.getInt(scanner, "Inserisci id del dipendente: \n");
			pstmt.setInt(1, idTeam);
			pstmt.setInt(2, idDip);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Errorerrimo");
			}
			System.out.println("Inserimento riuscito");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per cambiare informazioni di un team
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void updateTeam(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.team SET nomeTeam=? , idTeamLeader = ? WHERE idTeam=?";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{

			int idTeam = FunzUtili.getInt(scanner, "Inserisci ID del team da aggiornare: \n");

			System.out.println("Inserisci il nuovo nome team");
			String nuovoNome = scanner.nextLine();

			int nuovoTeamLeader = FunzUtili.getInt(scanner, "Inserisci l'ID del nuovo team Leader: \n");

			pstmt.setString(1, nuovoNome);
			pstmt.setInt(2, nuovoTeamLeader);
			pstmt.setInt(3, idTeam);

			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Update Team fallito");
			}
			System.out.println("Team aggiornato con successo! ");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per cancellare un team
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cancellazioneTeam(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.team WHERE idTeam= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idTeam = FunzUtili.getInt(scanner, "Inserisci l'ID del team da eliminare: \n");
			pstmt.setInt(1, idTeam);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Nessuno Team Trovato Con Questo l'ID");
			}
			System.out.println("Team Eliminato Con Successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per visualizzare i membri di un team
	 * 
	 * @param conn: connessione con il database
	 * 
	 */
	public static void visualizzaMembriTeam(Connection conn)
	{
		String query = "SELECT team.idTeam, nomeTeam, dipendenti.nome, dipendenti.cognome, dipendenti.ruolo FROM ((azienda.team INNER JOIN azienda.team_dipendentiassegnati ON team.idTeam = team_dipendentiassegnati.idTeam) INNER JOIN azienda.dipendenti ON dipendenti.idDipendente = team_dipendentiassegnati.idDipendente) ORDER BY team.idTeam;   ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try(ResultSet rs = pstmt.executeQuery()) 
			{
				while(rs.next()) 
				{
					int idTeam = rs.getInt("idTeam");
					String nomeTeam = rs.getString("nomeTeam");
					String nomeDip = rs.getString("nome");
					String cognomeDip = rs.getString("cognome");
					String ruolo = rs.getString("ruolo");
					
					System.out.printf("ID Dipendente: %d | Nome team: %s | Nome: %s | Cognome: %s | Ruolo: %s%n", idTeam, nomeTeam, nomeDip, cognomeDip, ruolo);
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
