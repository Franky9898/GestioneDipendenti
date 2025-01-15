package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Manager extends Dipendenti {
	private int id;
	private int idDipendente;
	private double bonus;
	private int idTeamGestito;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDipendente() {
		return idDipendente;
	}
	public void setIdDipendente(int idDipendente) {
		this.idDipendente = idDipendente;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	public int getIdTeamGestito() {
		return idTeamGestito;
	}
	public void setIdTeamGestito(int idTeamGestito) {
		this.idTeamGestito = idTeamGestito;
	}
	
	public static void aggiungiManager(Connection conn, Scanner scanner) {
		int idDipendente;
		int idManager;
		String query = "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + "VALUES (?,?,?,?)";
		String query1 = "INSERT INTO azienda.team(idDipendente, bonus)"+ "VALUES (?,?)";
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
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idDipendente = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creazione cliente fallita, ID non recuperato.");
                }
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try (PreparedStatement pstmt = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS))
		{
			System.out.println("Inserisci id dipendente: ");
			int idDip = scanner.nextInt();
			System.out.println("Inserisci bonus: ");
			double bonus = scanner.nextDouble();
			pstmt.setInt(1, idDip);
			pstmt.setDouble(2, bonus);
			
			int righe = pstmt.executeUpdate();
			if (righe < 1)
			{
				throw new SQLException("Creazione manager fallita, nessuna riga aggiunta.");
			}
			System.out.println("Manager aggiunto con successo");
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idDipendente = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creazione manager fallita, ID non recuperato.");
                }
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void visualizzaManager(Connection conn, Scanner scanner) {
		
		String query = "SELECT nome, cognome, manager.id, manager.idTeamGestito AS team\n"
				+ "FROM azienda.dipendenti\n"
				+ "LEFT JOIN azienda.manager\n"
				+ "ON  dipendenti.idDipendente = manager.idDipendente\n"
				+ "WHERE dipendenti.ruolo ='manager';"; 
		
		try (PreparedStatement pstmt = conn.prepareStatement(query))
		{
			try (ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
					System.out.println("Non ci sono manager.");
				while (rs.next())
				{
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					int id = rs.getInt("id");
					int idTeamGestito = rs.getInt("idTeamGestito");
					System.out.printf("nome: %s | cognome: %s | id: %d%n | id team gestito: %d%n", nome, cognome, id, idTeamGestito);
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
