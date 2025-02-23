package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Progetti
{
	/*
	 * Metodo per inserire un nuovo progetto
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void inserimentoProgetto(Connection conn, Scanner scanner) 
	{
		String query = "INSERT INTO azienda.progetti " + "(nomeProgetto, idProjectManager)" + " VALUES (?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			System.out.println("Inserisci nome progetto: ");
			String nome = scanner.nextLine();
			int idPM = FunzUtili.getInt(scanner, "Inserisci ID del project manager: \n");
			pstmt.setString(1, nome);
			pstmt.setInt(2, idPM);
			int righe = pstmt.executeUpdate();
			if (righe <1)
			{
				throw new SQLException("Creazione progetto fallita, nessuna riga aggiunta.");
			}
			System.out.println("Progetto aggiunto con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per cancellare un progetto con un determinato id
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cancellaProgetto(Connection conn, Scanner scanner) 
	{
		String query = "DELETE FROM azienda.progetti WHERE idProgetto = ?; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Selezionare ID progetto da cancellare: \n");
			pstmt.setInt(1, id);
			int righe = pstmt.executeUpdate();
			if (righe <1)
			{
				throw new SQLException("Cancellazione progetto fallita, nessuna riga rimossa.");
			}
			System.out.println("Progetto cancellato con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per selezionare i progetti con le loro informazioni, tra cui i team che vi lavorano
	 * 
	 * @param conn: connessione con il database
	 */
	public static void selezioneProgetto(Connection conn)
	{
		String query = "SELECT progetti.idProgetto, nomeProgetto, idProjectManager, teamassegnati_progetti.idTeam" + " FROM azienda.progetti" + " LEFT JOIN azienda.teamassegnati_progetti "
				+ " ON teamassegnati_progetti.idProgetto=progetti.idProgetto; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				//if (!rs.next())
					//System.out.println("Nessun progetto attivo.");
				while (rs.next())
				{
					int idProgetto = rs.getInt("idProgetto");
					String nomeProgetto = rs.getString("nomeProgetto");
					int idPM= rs.getInt("idProjectManager");
					int idTeam = rs.getInt("idTeam");
					System.out.printf("ID progetto: %d | nome progetto: %s | ID P.manager: %d | idTeam: %d \n", idProgetto, nomeProgetto, idPM, idTeam);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per cambiare il nome di un progetto con un determinato id
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioNomeProgetto(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.progetti SET nomeProgetto = ? WHERE idProgetto = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Inserisci id del progetto a cui modificare il nome: \n");
			System.out.println("Inserisci nuovo nome progetto: ");
			String nome = scanner.nextLine();
			pstmt.setString(1, nome);
			pstmt.setInt(2, id);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
				throw new SQLException("Errore");
			System.out.println("Nome progetto cambiato con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per inserire un team in un progetto
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void inserireTeamInProgetto(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.teamassegnati_progetti (idTeam,idProgetto) VALUES (?,?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idProgetto = FunzUtili.getInt(scanner, "Inserisci id del progetto: \n");
			int idTeam = FunzUtili.getInt(scanner, "Inserisci id del team da assegnare al progetto: \n");
			pstmt.setInt(2, idProgetto);
			pstmt.setInt(1, idTeam);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
				throw new SQLException("Errore nell'assegnazione del team");
			System.out.println("Team assegnato con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per rimuovere un team in un progetto
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void rimuovereTeamInProgetto(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.teamassegnati_progetti WHERE idProgetto=? AND idTeam=?;;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idProgetto = FunzUtili.getInt(scanner, "Inserisci id del progetto: \n");
			int idTeam = FunzUtili.getInt(scanner, "Inserisci id del team da rimuovere dal progetto: \n");
			pstmt.setInt(1, idProgetto);
			pstmt.setInt(2, idTeam);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
				throw new SQLException("Errore nell'eliminazione del team");
			System.out.println("Team rimosso con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per sostituire un team assegnato a un progetto, conoscendo l'id del team da sostituire
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioIdTeamAssegnato(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.teamassegnati_progetti SET idTeam = ? WHERE idTeam = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idSostituito = FunzUtili.getInt(scanner, "Inserisci id del team da sostituire: \n");
			int idNuovo = FunzUtili.getInt(scanner, "Inserisci id del nuovo team che lavorerà al progetto: \n");
			pstmt.setInt(1, idNuovo);
			pstmt.setInt(2, idSostituito);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
				throw new SQLException("Errore nella sostituzione del team");
			System.out.println("Team sostituito con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo per sostituire il team manager di un progetto
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cambioProjectManager(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.progetti SET idProjectManager = ? WHERE idProgetto = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Inserisci id del progetto a cui modificare il project manager: \n");
			int idPM = FunzUtili.getInt(scanner, "Inserisci id del nuovo project manager: \n");
			pstmt.setInt(1, idPM);
			pstmt.setInt(2, id);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
				throw new SQLException("Errore, controllare se l'id inserito è esistente");
			System.out.println("Project manager cambiato con successo");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
