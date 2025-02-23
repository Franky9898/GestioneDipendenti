package sistema;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class Sviluppatori extends Dipendenti
{
	/*
	 * Metodo per inserire uno sviluppatore
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void inserimentoSviluppatore(Connection conn, Scanner scanner)
	{
		String query = " INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + " VALUES (?,?,?,?)";
		String query2 = " INSERT INTO azienda.sviluppatori " + "(idDipendente)" + " VALUES (?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS); // ritorno della key idDipendente foreign key di svilupp.
				PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			String ruolo = "SVILUPPATORE";
			double stipendio = FunzUtili.getDouble(scanner, "Inserire stipendio: \n");
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendio);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Aggiunta sviluppatore fallita, nessuna riga aggiunta.");
			}
			System.out.println("Sviluppatore aggiunto con successo");
			int idDipendente;
			// Recupero la chiave generata (ID auto-increment)
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					idDipendente = generatedKeys.getInt(1);
				} else
				{
					throw new SQLException("qualcosa è andato storto");
				}
			}
			pstmt2.setInt(1, idDipendente);
			int righe2 = pstmt2.executeUpdate();
			if (righe2 < 1)
			{
				throw new SQLException("Aggiunta sviluppatore fallita, nessuna riga aggiunta.");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per visualizzare uno sviluppatore con le relative informazioni tra cui i linguaggi che conosce
	 * 
	 * @param conn: connessione con il database
	 * 
	 */
	public static void selezioneSviluppatoriLinguaggi(Connection conn)
	{
		String query =  "SELECT dipendenti.idDipendente, dipendenti.nome, dipendenti.cognome, linguaggi.nomeLinguaggio"
				+ " FROM (((azienda.dipendenti INNER JOIN azienda.sviluppatori ON sviluppatori.idDipendente=dipendenti.idDipendente) "
				+ " INNER JOIN azienda.sviluppatori_linguaggi ON sviluppatori_linguaggi.idDipendente = sviluppatori.idDipendente)"
				+ " INNER JOIN azienda.linguaggi ON sviluppatori_linguaggi.idLinguaggio=linguaggi.idLinguaggio) ORDER BY dipendenti.idDipendenti;";
				
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query))
		{

			System.out.println("Linguaggi conosciuti dagli sviluppatori: ");
			while (rs.next())
			{
				int idDipendente = rs.getInt("idDipendente");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String nomeLinguaggio = rs.getString("nomeLinguaggio");

				System.out.printf("ID Dipendente: %d | Nome: %s | Cognome: %s | Linguaggio: %s%n", idDipendente, nome, cognome, nomeLinguaggio);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per eliminare uno sviluppatore
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void cancellaSviluppatori(Connection conn, Scanner scanner)
	{   //bisogna cancellare prima il record nella tabella sviluppagtori con foreign key iddipendente
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente=?;";
		String query2 = "DELETE FROM azienda.sviluppatori WHERE idDipendente=?;";
		try (PreparedStatement pstmt = conn.prepareStatement(query); PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			int idDipendenti = FunzUtili.getInt(scanner, "Inserisci ID dipendente da cancellare: \n");
			pstmt.setInt(1, idDipendenti);
			pstmt2.setInt(1, idDipendenti);
			int righe = pstmt2.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Prima query boh");
			}
			int righe2 = pstmt.executeUpdate();
			if (righe2 < 1)
			{
				throw new SQLException("Seconda query boh");
			}
			System.out.println("Sviluppatore cancellato AH");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Metodo per inserire un linguaggio a uno sviluppatore
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void aggiungiLinguaggioSviluppatore(Connection conn, Scanner scanner)
	{
		String query = "INSERT INTO azienda.sviluppatori_linguaggi (idDipendente, idLinguaggio) VALUES (?,?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idDipendente = FunzUtili.getInt(scanner, "Inserisci ID dipendente dello sviluppatore a cui assegnare il linguaggio: \n");
			int idLinguaggio = FunzUtili.getInt(scanner, "Inserisci ID linguaggio: \n");
			pstmt.setInt(1, idDipendente);
			pstmt.setInt(2, idLinguaggio);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("errore inserimento linguaggio nuovo");
			}
			System.out.println("Aggiornamento sviluppatore-linguaggio");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
