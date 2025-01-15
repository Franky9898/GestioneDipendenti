package sistema;

import java.sql.Connection;
import java.util.Scanner;

public class Menu{

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
			System.out.println("\nMenù: \n" + "1. Menù gestione Dipendenti\n" + "2. Menù gestione Manager\n" + "3. Menù gestione Sviluppatore\n" + "4. Esci\n" + "Inserisci comando:");
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
			case "4": // caso exit
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
					+ "1. Inserisci dipendente con team.\n"
					+ "2. Inserisci dipendente.\n"
					+ "3. Cancella dipendente.\n"
					+ "4. Seleziona i dipendenti.\n"
					+ "5. Seleziona tutti gli impiegati.\n"
					+ "6. Cambiare team a un dipendente.\n"
					+ "7. Cambiare stipendio a un dipendente.\n"
					+ "8. Promuovere in manager un dipendente.\n"
					+ "9. Torna al menù principale.\n"
					+ "Inserisci comando: ");
			risposta = scanner.nextLine();
			switch (risposta)
			{
			case "1":
				//Dipendenti.inserimentoDipendenteConTeam(conn, scanner);
				break;
			case "2":
				Dipendenti.inserimentoDipendente(conn, scanner);
				break;
			case "3":
				Dipendenti.cancellaDipendente(conn, scanner);
				break;
			case "4":
				Dipendenti.selezioneDipendente(conn, scanner);
				break;
			case "5":
				Dipendenti.selezioneDipendenteTutti(conn,scanner);
				break;
			case "6":
				Dipendenti.cambioTeamDipendente(conn, scanner);
				break;
			case "7":
				Dipendenti.cambioStipendio(conn, scanner);
				break;
			case "8":
				Dipendenti.promozioneInManager(conn, scanner);
				break;
			case "9":
				menuGenerale(conn, scanner);
				uscita = true;
				break;
			default:
				System.out.println("Comando non valido. Riprova.");
				break;
			}
		}
	}
}