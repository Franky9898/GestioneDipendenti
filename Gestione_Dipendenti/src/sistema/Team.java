package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Team {
	/*
	 * CRUD metodo per inserire un team con Connection e scanner per connettersi al
	 * DB e leggere l'input dell'utente è un controllo perl'ID inserito da parte
	 * dell'utente
	 * 
	 * 
	 */
	public static void InserireTeam(Connection conn, Scanner scanner) {
		int idTeam = 0;
		String query = "INSERT INTO azienda.team " + " (nomeTeam, idTeamLeader, idProgetto) " + " VALUES (?,?,?)";
		String query1 = "INSERT INTO azienda.team_dipendentiassegnati (idTeam, idDipendente)" + " VALUES (?,?);";
		String query2 = "INSERT INTO azienda.teamassegnati_progetti (idTeam, idProgetto)" + " VALUES (?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			System.out.println("Inserisci nome del Team: ");
			String nomeTeam = scanner.nextLine();

			int idTeamLeader = FunzUtili.getInt(scanner, "Inserisci l'ID del TeamLeader: ");
			int idProgetto = FunzUtili.getInt(scanner, "Inserire ID del progetto da assegnare a questo team :");
			pstmt.setString(1, nomeTeam);
			pstmt.setInt(2, idTeamLeader);
			pstmt.setInt(3,  idProgetto);
			

			int righe = pstmt.executeUpdate();
			if (righe < 1) {
				throw new SQLException("Creazione dipendente fallita, nessuna riga aggiunta.");
			} else {
				System.out.println("Team aggiunto con successo");
			}
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					idTeam = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creazione cliente fallita, ID non recuperato.");
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try (PreparedStatement pstmt = conn.prepareStatement(query1)) {

			int idDipendente = FunzUtili.getInt(scanner, "Inserire ID dipendente da aggiungere al team: ");
			pstmt.setInt(1, idTeam);
			pstmt.setDouble(2, idDipendente);

			int righe = pstmt.executeUpdate();
			if (righe < 1) {
				throw new SQLException("Operazione fallita.");
			}
			System.out.println("Dipendente aggiunto al team.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (PreparedStatement pstmt = conn.prepareStatement(query2)) {

			int idProgetto = FunzUtili.getInt(scanner, "Inserire ID del progetto da associare al team: ");
			pstmt.setInt(1, idTeam);
			pstmt.setDouble(2, idProgetto);

			int righe = pstmt.executeUpdate();
			if (righe < 1) {
				throw new SQLException("Operazione fallita.");
			}
			System.out.println("Team aggiornato.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readAllTeam(Connection conn, Scanner scanner) {
		String query = "SELECT * FROM azienda.team";
		try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int idTeam = rs.getInt("idTeam");
				String nomeTeam = rs.getString("nomeTeam");
				int teamLeader = rs.getInt("idTeam");
				System.out.printf("ID team : %d | TeamName : %s  | Team_Leader : %d \n", idTeam, nomeTeam, teamLeader);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateTeam(Connection conn, Scanner scanner) {
		String query = "UPDATE azienda.team SET nomeTeam=? , teamLeader = ? WHERE idTeam=?";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			int idTeam = FunzUtili.getInt(scanner, "Inserisci ID del team aggiornato");

			System.out.println("Inserisci il nuovo team");
			String nuovoNome = scanner.nextLine();

			int nuovoTeamLeader = FunzUtili.getInt(scanner, "Inserisci l'ID nuovodel team Leader ");

			pstmt.setString(1, nuovoNome);
			pstmt.setInt(2, nuovoTeamLeader);
			pstmt.setInt(3, idTeam);

			int righe = pstmt.executeUpdate();
			if (righe < 1) {
				System.out.println("Nessuno team trovato!");
			} else {
				System.out.println("Team aggiornato con successo! ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CancellazioneTeam(Connection conn, Scanner scanner) {
		String query = "DELETE FROM azienda.team WHERE idTeam= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			int idTeam = FunzUtili.getInt(scanner, "Inserisci l'ID del team da eliminare ");
			pstmt.setInt(1, idTeam);
			int righe = pstmt.executeUpdate();
			if (righe < 1) {
				System.out.println("Nessuno Team Trovato Con Questo l'ID");
			} else {
				System.out.println("Team Eliminato Con Successo");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
