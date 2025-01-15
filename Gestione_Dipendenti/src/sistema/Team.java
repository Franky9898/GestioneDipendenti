package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Team
{
	/*
	 * CRUD 
	 * metodo per inserire un team con Connection e scanner per connettersi al DB 
	 * e leggere l'input dell'utente è un controllo perl'ID inserito da parte dell'utente
	 * 
	 * 
	 */
    public static void InserireTeam(Connection conn, Scanner scanner)
    {
    	String query ="INSERT INTO azienda.team " + " (nomeTeam,TeamLeader) " + " VALUES (?,?)";
    	try(PreparedStatement pstmt= conn.prepareStatement(query))
    	{
    		System.out.println("Inserisci nome del Team");
    		String nomeTeam = scanner.nextLine();
    		
    		int teamLeader=-1;
    		boolean validNumber=false;
    		while(!validNumber)
    		{
    			System.out.println("Inserisci l'ID del Teamleader");
        		String inserito = scanner.nextLine();
        		try
        		{
        		 teamLeader=Integer.parseInt(inserito);
        		 validNumber=true;
        		}catch(NumberFormatException e) 
        		{
        			System.out.println("ID non valido! PER FAVORE PER FAVORE ISCIERCI UN NUMERO INTERO " );
        		}
    		}
    		pstmt.setString(1,nomeTeam);
    		pstmt.setInt(2, teamLeader);
    		
    		int righe=pstmt.executeUpdate();
    		if(righe < 1) {
    			System.out.println("Errore! vedi cosa hai fatto e ritorna");
    		}
    		else
    		{
    		System.out.println("TeamLeader aggiunto con successo");	
    		}
    	} 
    	catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    public static void readAllTeam(Connection conn, Scanner scanner) 
    {
    String query = "SELECT * FROM azienda.team";
    try(PreparedStatement pstmt=conn.prepareStatement(query);
    		ResultSet rs = pstmt.executeQuery())
    {
    	
    	while(rs.next()) {
    		int idTeam=rs.getInt("idTeam");
    		String nomeTeam=rs.getString("nomeTeam");
    		int teamLeader = rs.getInt("idTeam");
    		System.out.printf("ID team : %d | TeamName : %s  | Team_Leader : %d%n,", idTeam,nomeTeam,teamLeader);
    		
    	}
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    public static void upDateTeam(Connection conn, Scanner scanner) 
    {
    	String query = "UPDATE azienda.team SET nomeTeam=? , teamLeader = ? WHERE idTeam=?";
    	try(PreparedStatement pstmt=conn.prepareStatement(query))
    	{
    		System.out.println("Inserisci ID del team aggiornato");
    		int idTeam = Integer.parseInt(scanner.nextLine());
    		
    		System.out.println("Inserisci il nuovo team");
    		String nuovoNome=scanner.nextLine();
    		
    		System.out.println("Inserisci l'ID nuovodel team Leader ");
    		int nuovoTeamLeader=scanner.nextInt();
    		
    		pstmt.setString(1,nuovoNome);
    		pstmt.setInt(2, nuovoTeamLeader);
    		pstmt.setInt(3,idTeam);
    		
    		int righe = pstmt.executeUpdate();
    		if(righe < 1)
    		{
    			System.out.println("Nessuno team trovato!");
    		}
    		else 
    		{
    			System.out.println("Team aggiornato con successo! ");
    		}
    		
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void CancellazioneTeam(Connection conn, Scanner scanner) 
    {
    String query = "DELETE FROM azienda.team WHERE idTeam= ?";
    try(PreparedStatement pstmt = conn.prepareStatement(query))
    {
    	System.out.println("Inserisci l'ID del team da eliminare ");
    	int idTeam=Integer.parseInt(scanner.nextLine());
    	pstmt.setInt(1, idTeam);
    	int righe=pstmt.executeUpdate();
    	if(righe < 1 ) 
    	{
    		System.out.println("Nessuno Team Trovato Con Questo l'ID");
    	}
    	else 
    	{
    		System.out.println("Team Eliminato Con Successo");
    	}

    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
