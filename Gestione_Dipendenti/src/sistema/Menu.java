package sistema;

import java.util.Scanner;
import java.sql.Connection;

public class Menu
{
	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari sottomenù
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuGenerale(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo

		System.out.println("Benvenuto!");

		while (!uscita)
		{

			System.out.println("\nMenù: \n" + "1. Menù gestione Dipendenti\n" + "2. Menù gestione Manager\n" + "3. Menù gestione Sviluppatore\n" 
					+ "4. Menù gestione Progetti\n" + "5. Menù gestione Team\n" + "6. Menù gestione Linguaggi\n"
					+ "99. Esci\n" + "Inserisci comando:");

			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				menuDipendente(conn, scanner);
				uscita = true;
				break;
			case "2":
				menuManager(conn,scanner);
				uscita = true;
				break;
			case "3":
				menuSviluppatore(conn,scanner);
				uscita = true;
				break;
			case "4":
				menuProgetti(conn,scanner);
				uscita = true;
				break;
			case "5":
				menuTeam(conn, scanner);
				uscita = true;
				break;
			case "6":
				menuLinguaggi(conn, scanner);
				uscita = true;
				break;
			case "99":
				System.out.println("Arrivederci!");
				uscita = true;
				break; // caso exit

			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}

	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i dipendenti
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuDipendente(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println(
					"\nMenu:\n" + "1. Inserisci dipendente.\n" + "2. Cancella dipendente.\n" + "3. Seleziona i dipendenti.\n" + "4. Seleziona tutti gli impiegati.\n" + "5. Cambiare team a un dipendente.\n"
							+ "6. Cambiare stipendio a un dipendente.\n" + "7. Promuovere in manager un dipendente.\n" + "99. Torna al menù principale.\n" + "Inserisci comando: ");
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
			case "99":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}

	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari sviluppatori
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */

	public static void menuSviluppatore(Connection conn, Scanner scanner) 
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println(
					"\nMenu:\n" + "1. Inserisci sviluppatore.\n" + "2. Cancella sviluppatore.\n" + "3. Selezione sviluppatore e relativi linguaggi conosciuti.\n" + "4. Aggiungi un linguaggio a uno sviluppatore. \n" + "99. Torna al menù principale.\n" + "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				Sviluppatori.inserimentoSviluppatore(conn, scanner);
				break;
			case "2":
				Sviluppatori.cancellaSviluppatori(conn, scanner);
				break;
			case "3":
				Sviluppatori.selezioneSviluppatoriLinguaggi(conn);
				break;
			case "4":
				Sviluppatori.aggiungiLinguaggioSviluppatore(conn, scanner);
				break;
			case "99":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}
	
	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari progetti
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuProgetti(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println("\nMenu:\n" + "1. Inserisci progetto.\n" + "2. Cancella progetto.\n" + "3. Seleziona i progetti.\n" + "4. Cambia nome a un progetto.\n"
					+"5. Assegna team a un progetto.\n" +"6. Rimuovi team da un progetto.\n"  +"7. Sostituisci team assegnato a un progetto.\n" + "8. Cambia project manager di un progetto\n" + "99. Torna al menù principale.\n" + "Inserisci comando: ");
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
			case "7":
				Progetti.cambioIdTeamAssegnato(conn, scanner);
				break;
			case "8":
				Progetti.cambioProjectManager(conn, scanner);
				break;
			case "99":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			case "5":
				Progetti.inserireTeamInProgetto(conn, scanner);
				break;
			case "6":
				Progetti.rimuovereTeamInProgetto(conn, scanner);
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
			
		}
	}

	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari linguaggi
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuLinguaggi(Connection conn, Scanner scanner)
	{
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
			System.out.println("\nMenu:\n"
					+ "1. Inserisci linguaggio.\n"
					+ "2. Cancella linguaggio.\n"
					+ "3. Seleziona linguaggi.\n"
					+ "99. Torna al menù principale.\n"
					+ "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				Linguaggi.inserisciLinguaggio(conn, scanner);
				break;
			case "2":
				Linguaggi.cancellaLinguaggio(conn, scanner);
				break;
			case "3":
				Linguaggi.selezioneLinguaggio(conn);
				break;
			case "99":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			} 
		} 
	}
	
	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i manager
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuManager(Connection conn, Scanner scanner) {
		String risposta;
		boolean uscita = false;
		while(!uscita) {
			System.out.println("\nMenu:\n"
					+ "1. Inserisci manager\n"
					+ "2. Cancella manager\n"
					+ "3. Visualizza manager\n"
					+ "4. Cerca manager con id\n"
					+ "5. Modifica bonus\n"
					+ "6. Calcola stipendio\n"
					+ "99. Torna al menu principale\n"
					+ "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				Manager.inserisciManager(conn, scanner);
				break;
			case "2":
				Manager.cancellaManager(conn, scanner);
				break;
			case "3":
				Manager.visualizzaManager(conn);
				break;
			case "4":
				Manager.visualizzaManagerConID(conn, scanner);
				break;
			case "5" :
				Manager.modificaBonusManager(conn, scanner);
				break;
			case "6" : 
				Manager.calcolaStipendioManager(conn);
				break;
			case "99" :
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}

	/*
	 * Metodo che stampa in console un menù con diverse opzioni per gestire i vari team
	 * 
	 * @param conn: connessione con il database
	 * 
	 * @scanner: scanner per prendere testo da console
	 */
	public static void menuTeam(Connection conn, Scanner scanner) {
		String risposta;
		boolean uscita = false; // condizione di ripetizione del ciclo
		while (!uscita)
		{
		System.out.println("\nMenù:\n"
				+"1. Inserisci Team\n"
				+"2. Leggi tutti i team\n" 
                +"3. Aggiorna Team\n" 
                +"4. Elimina Team\n"
                +"5. Inserisci impiegato in un Team\n"
                +"6. Visualizza team con membri \n"
                +"99. Torna al menù principale\n" 
               + "Inserisci comando: ");
		 risposta = scanner.nextLine();
	        switch (risposta)
	        {
	            case "1":
	                Team.inserireTeam(conn, scanner);  // Chiama il metodo per inserire il team
	                break;
	            case "2":
	                Team.readAllTeam(conn, scanner);  // Chiama il metodo per leggere tutti i team
	                break;
	            case "3":
	                Team.updateTeam(conn, scanner);  // Chiama il metodo per aggiornare il team
	                break;
	            case "4":
	                Team.cancellazioneTeam(conn, scanner);  // Chiama il metodo per cancellare il team
	                break;
	            case "5":
	            	Team.inserireDipendenteInTeam(conn, scanner);
	            	break;
	            case "99":
	                menuGenerale(conn, scanner);  // Torna al menù principale
	                uscita = true;
	                break;
	            case "6":
	            	Team.visualizzaMembriTeam(conn);
	            	break;
	            default:
	                System.out.println("Comando non valido. Riprova.");
	                break;
	        }
		}
	}
}
