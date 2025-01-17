package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Manager extends Dipendenti
{

	public static void inserisciManager(Connection conn, Scanner scanner)
	{
		int idDipendente=-1;
		String query = "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + "VALUES (?,?,?,?)";
		String query1 = "INSERT INTO azienda.manager" + "(idDipendente, bonus)" + "VALUES (?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			String ruolo = "MANAGER";
			double stipendio = FunzUtili.getDouble(scanner, "Inserisci stipendio dipendente: ");
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendio);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Creazione dipendente fallita, nessuna riga aggiunta.");
			}
			System.out.println("Dipendente aggiunto con successo");
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					idDipendente = generatedKeys.getInt(1);
				} else
				{
					throw new SQLException("Creazione cliente fallita, ID non recuperato.");
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try (PreparedStatement pstmt = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS))
		{

			double bonus = FunzUtili.getDouble(scanner, "Inserisci bonus: ");
			pstmt.setInt(1, idDipendente);// il problema è qui!
			pstmt.setDouble(2, bonus);

			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Creazione manager fallita, nessuna riga aggiunta.");
			}
			System.out.println("Manager aggiunto con successo");
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys())
			{
				if (!generatedKeys.next())
				{
					throw new SQLException("Creazione manager fallita, ID non recuperato.");
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void cancellaManager(Connection conn, Scanner scanner)
	{
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente = ? ; ";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int idDipendente = FunzUtili.getInt(scanner, "Inserire ID dipendente del manager da cancellare: ");
			pstmt.setInt(1, idDipendente);
			int righe = pstmt.executeUpdate();
			if (righe == 0)
			{
				throw new SQLException("Cancellazione manager fallita, nessuna riga rimossa.");
			}
			System.out.println("Manager cancellato con successo");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void visualizzaManager(Connection conn, Scanner scanner)
	{

		String query = "SELECT idDipendente, nome, cognome" 
		+ "FROM azienda.dipendenti" 
		+ "INNER JOIN azienda.manager" 
		+ "ON  dipendenti.idDipendente = manager.idDipendente;";

		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("Non ci sono manager.");
				while (rs.next())
				{
					int idDipendente = rs.getInt("idDipendente");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					System.out.printf("idDipendente: %d | nome: %s | cognome: %s%n ", idDipendente, nome, cognome);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void visualizzaManagerConID(Connection conn, Scanner scanner)
	{

		String query = "SELECT nome, cognome, manager.idDipendente" 
				+ "FROM azienda.dipendenti" 
				+ "INNER JOIN azienda.manager" 
				+ "ON  dipendenti.idDipendente = manager.idDipendente"
				+ "WHERE idDipendente = ?;";

		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			int id = FunzUtili.getInt(scanner, "Inserire id manager da cercare: ");
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("Non ci sono manager con questo id.");
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					id = rs.getInt("idDipendente");
					System.out.printf("nome: %s | cognome: %s | id: %d%n", nome, cognome, id);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
