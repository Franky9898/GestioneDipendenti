package sistema;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

import sistema.Dipendenti.Ruolo;

public class Sviluppatori extends Dipendenti {
	// sviluppatori
	// inserimento sviluppatore
	public static int inserimentoSviluppatore(Connection conn, Scanner scanner) {
		String query= "INSERT INTO azienda.dipendenti " + "(nome, cognome, ruolo, stipendio)" + "VALUES (?,?,?,?)";
		String query2= "INSERT INTO azienda.sviluppatori " + "(idDipendente, idProgettoAssegnato)" + "VALUES (?,?)";
		//resultset id generato id=Statement.RETURN_GENERATED_KEYS
		try(PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);//ritorno della key idDipendente foreign key di svilupp.
			PreparedStatement pstmt2 = conn.prepareStatement(query2))
		{
			
			System.out.println("Inserisci nome dipendente: ");
			String nome = scanner.nextLine();
			System.out.println("Inserisci cognome dipendente: ");
			String cognome = scanner.nextLine();
			boolean continua = true;
			String ruolo = "SVILUPPATORE";
			
			while (continua)
			{	
				System.out.println("Inserisci ruolo dipendente: ");
				ruolo = scanner.nextLine().toUpperCase();
				for (Ruolo r : Ruolo.values())
				{
					if (r.name().equals(ruolo))
						continua = false;
				}
			}
			System.out.println("Inserisci stipendio sviluppatore: ");
			String stipendio= scanner.nextLine();
			Double stipendioD=Double.parseDouble(stipendio);
			
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, ruolo);
			pstmt.setDouble(4, stipendioD);
			
			int righe= pstmt.executeUpdate();
			if (righe == -1)
			{
				throw new SQLException("Creazione cliente fallita, nessuna riga aggiunta.");
			}
			System.out.println("Dipendente aggiunto con successo");
			
			// Recupero la chiave generata (ID auto-increment)
			try(ResultSet generatedKeys= pstmt.getGeneratedKeys())
			{
				if(generatedKeys.next()) 
				{
					return generatedKeys.getInt(1);
				}else 
				{
					throw new SQLException ("Creazione cliente fallita, ID non recuperato.");
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return -1; // In caso di errore
	}
	//ti permette di viualizzare nome, cognome degli sviluppatori insieme ai linguaggi che conoscono
	public static void readSviluppatoriLinguaggi(Connection conn) {
		String query = "SELECT dipendenti.idDipendente, dipendenti.nome, dipendenti.cognome, linguaggi.nomeLinguaggio"
				+ "FROM (((azienda.dipendenti INNER JOIN azienda.sviluppatori ON sviluppatori.idDipendente=dipendenti.idDipendente) "
				+ "INNER JOIN azienda.sviluppatori_linguaggi ON sviluppatori_linguaggi.idDipendente = sviluppatori.idDipendente)"
				+ "INNER JOIN azienda.linguaggi ON sviluppatori_linguaggi.idLinguaggio=linguaggi.idLinguaggio )";
		try(Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)){
			
			System.out.println("Linguaggi conosciuti dagli sviluppatori: ");
			while(rs.next()) {
				int idDipendente= rs.getInt("idDipendente");
				String nome= rs.getString("nome");
				String cognome= rs.getString("cognome");
				String nomeLinguaggio= rs.getString("nomeLinguaggio");
				
				System.out.printf("ID Dipendente: %d | Nome: %s | Cognome: %s | Linguaggio: %s",
								idDipendente, nome, cognome, nomeLinguaggio);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
