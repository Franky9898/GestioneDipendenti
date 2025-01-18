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
		int idDipendente = -1;
		String query = "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + "VALUES (?,?,?,?)";
		String query1 = "INSERT INTO azienda.manager" + "(idDipendente, bonus)" + "VALUES (?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			String ruolo = "MANAGER";
			double stipendio = FunzUtili.getDouble(scanner, "Inserisci stipendio dipendente: \n");
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendio);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Creazione dipendente fallita, nessuna riga aggiunta.");
			}
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

			double bonus = FunzUtili.getDouble(scanner, "Inserisci bonus: \n");
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
		String queryMan = "DELETE FROM azienda.manager WHERE idDipendente = ? ; ";
		String query = "DELETE FROM azienda.dipendenti WHERE idDipendente = ? ; ";
		int idDipendente = 0;
		try (PreparedStatement pstmt2 = conn.prepareStatement(queryMan))
		{
			idDipendente = FunzUtili.getInt(scanner, "Inserire ID dipendente del manager da cancellare: \n");
			pstmt2.setInt(1, idDipendente);
			int righe = pstmt2.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Cancellazione manager in tabella manager fallita, nessuna riga rimossa.");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			pstmt.setInt(1, idDipendente);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
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
		String query = "SELECT manager.id, manager.idDipendente, nome, cognome" + " FROM azienda.dipendenti" + " INNER JOIN azienda.manager" + " ON  dipendenti.idDipendente = manager.idDipendente;";
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					int id = rs.getInt("id");
					int idDipendente = rs.getInt("idDipendente");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					System.out.printf(" id: %d | id Dip: %d | nome: %s | cognome: %s%n", id, idDipendente, nome, cognome);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void visualizzaManagerConID(Connection conn, Scanner scanner)
	{

		String query = "SELECT dipendenti.idDipendente, nome, cognome, team.nomeTeam, progetti.nomeProgetto" + " FROM azienda.dipendenti" + " INNER JOIN azienda.manager"
				+ " ON dipendenti.idDipendente = manager.idDipendente" + " LEFT JOIN azienda.team" + " ON dipendenti.idDipendente = team.idTeamLeader" + " LEFT JOIN azienda.progetti"
				+ " ON manager.idDipendente = progetti.idProjectManager" + " WHERE manager.idDipendente = ? ;";

		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{

			int id = FunzUtili.getInt(scanner, "Inserire id Dipendente del manager da cercare: \n");
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String nomeTeam = rs.getString("nomeTeam");
					String nomeProgetto = rs.getString("nomeProgetto");
					int idDipendente = rs.getInt("idDipendente");
					System.out.printf("idDipendente: %d | nome: %s | cognome: %s | team: %s | progetto: %s ", idDipendente, nome, cognome, nomeTeam, nomeProgetto);
				}

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void modificaBonusManager(Connection conn, Scanner scanner)
	{
		String query = "UPDATE azienda.manager" + " SET bonus = ?" + " WHERE idDipendente = ?;";

		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{

			int id = FunzUtili.getInt(scanner, "Inserire ID manager: \n");
			Double bonus = FunzUtili.getDouble(scanner, "Inserire valore bonus: \n");
			pstmt.setDouble(1, bonus);
			pstmt.setInt(2, id);
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Operazione non riuscita.");
			} else
			{
				System.out.println("Bonus aggiornato con successo.");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void calcolaStipendioManager(Connection conn)
	{
		String query = "SELECT nome, cognome, stipendio, manager.bonus" + " FROM azienda.dipendenti" + " INNER JOIN azienda.manager" + " ON dipendenti.idDipendente = manager.idDipendente;";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query))
		{

			System.out.println("Stipendi manager:");

			while (rs.next())
			{
				double stipendio = rs.getDouble("Stipendio");
				double bonus = rs.getDouble("bonus");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				System.out.printf("nome %s | cognome %s | stipendio %.2f \n", nome, cognome, stipendio + bonus);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
