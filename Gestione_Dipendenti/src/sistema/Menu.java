package sistema;

import java.util.Scanner;
import java.sql.Connection;

public class Menu
{
	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari sottomenù
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuGenerale(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo

		System.out.println("Benvenuto!");

		while (!uscita)
		{
			System.out.println("\nMenù: \n" + "1. Menù gestione Dipendenti\n" + "2. Menù gestione Manager\n" + "3. Menù gestione Sviluppatore\n" +"5. Menù gestione Team\n" + "6. Esci\n" + "Inserisci comando:");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				menuDipendente(conn, scanner);
				uscita = true;
				break;
			case "2":
				// menuManager(conn,scanner);
				break;
			case "3":
				// menuSviluppatore(conn,scanner);
				break;
			case "4":
               // menuTeam(conn, scanner); 
                break;
			case "5": // caso exit
				System.out.println("Arrivederci!");
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}

	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i dipendenti
	 * @param conn: connessione con il database
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuDipendente(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println("\nMenu:\n"
					+ "1. Inserisci dipendente.\n"
					+ "2. Cancella dipendente.\n"
					+ "3. Seleziona i dipendenti.\n"
					+ "4. Seleziona tutti gli impiegati.\n"
					+ "5. Cambiare team a un dipendente.\n"
					+ "6. Cambiare stipendio a un dipendente.\n"
					+ "7. Promuovere in manager un dipendente.\n"
					+ "8. Torna al menù principale.\n"
					+ "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				Dipendenti.inserimentoDipendente(conn, scanner);
				break;
			case "2":
				Dipendenti.cancellaDipendente(conn, scanner);
				break;
			case "3":
				Dipendenti.selezioneDipendente(conn);
				break;
			case "4":
				Dipendenti.selezioneImpiegati(conn);
				break;
			case "5":
				Dipendenti.cambioTeamDipendente(conn, scanner);
				break;
			case "6":
				Dipendenti.cambioStipendio(conn, scanner);
				break;
			case "7":
				Dipendenti.promozioneInManager(conn, scanner);
				break;
			case "8":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}
	
	public static void menuProgetti(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println("\nMenu:\n"
					+ "1. Inserisci progetto.\n"
					+ "2. Cancella progetto.\n"
					+ "3. Seleziona i progetti.\n"
					+ "4. Cambia nome a un progetto.\n"
					+ "5. Sostituisci team assegnato a un progetto.\n"
					+ "6. Cambia project manager di un progetto\n"
					+ "7. Torna al menù principale.\n"
					+ "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				Progetti.inserimentoProgetto(conn, scanner);
				break;
			case "2":
				Progetti.cancellaProgetto(conn, scanner);
				break;
			case "3":
				Progetti.selezioneProgetto(conn);
				break;
			case "4":
				Progetti.cambioNomeProgetto(conn, scanner);
				break;
			case "5":
				Progetti.cambioIdTeamAssegnato(conn, scanner);
				break;
			case "6":
				Progetti.cambioProjectManager(conn, scanner);
				break;
			case "7":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}
	public static void menuTeam(Connection conn, Scanner scanner) {
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
		System.out.println("\nMenù: "
				+"1. Inserisci Team\n"
				+"2. Leggi tutti i team\n" 
                +"3. Aggiorna Team\n" 
                +"4. Elimina Team\n" 
                +"5. Torna al menù principale\n" 
               + "Inserisci comando: ");
		 risposta = scanner.nextLine();
	        switch (risposta)
	        {
	            case "1":
	                Team.InserireTeam(conn, scanner);  // Chiama il metodo per inserire il team
	                break;
	            case "2":
	                Team.readAllTeam(conn, scanner);  // Chiama il metodo per leggere tutti i team
	                break;
	            case "3":
	                Team.upDateTeam(conn, scanner);  // Chiama il metodo per aggiornare il team
	                break;
	            case "4":
	                Team.CancellazioneTeam(conn, scanner);  // Chiama il metodo per cancellare il team
	                break;
	            case "5":
	                menuGenerale(conn, scanner);  // Torna al menù principale
	                uscita = true;
	                break;
	            default:
	                System.out.println("Comando non valido. Riprova.");
	                break;
	        }
		}
	}
}
