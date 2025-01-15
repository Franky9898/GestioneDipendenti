package sistema;

import java.util.Scanner;

import sistema.Dipendenti.Ruolo;

public class FunzUtili
{
	public static double getDouble(Scanner scanner, String stringa)
	{
		while (true)
		{
			System.out.print(stringa);
			String input = scanner.nextLine();
			try
			{
				return Double.parseDouble(input); // Prova a convertire l'input in double
			} catch (NumberFormatException e)
			{
				System.out.println("Perfavore inserisci un numero valido.");
			}
		}
	}
	
	public static int getInt(Scanner scanner, String stringa)
	{
		while (true)
		{
			System.out.print(stringa);
			String input = scanner.nextLine();
			try
			{
				return Integer.parseInt(input); // Prova a convertire l'input in int
			} catch (NumberFormatException e)
			{
				System.out.println("Perfavore inserisci un numero intero.");
			}
		}
	}
	
	public static boolean getBoolean(Scanner scanner, String stringa)
	{
		while (true)
		{
			System.out.print(stringa);
			String input = scanner.nextLine();
			try
			{
				return Boolean.parseBoolean(input); // Prova a convertire l'input in boolean
			} catch (NumberFormatException e)
			{
				System.out.println("Perfavore inserisci TRUE o FALSE.");
			}
		}
	}
	
	public static String inserisciRuolo(Scanner scanner) 
	{
		while (true) // Converte la stringa passata in console in enum Ruolo. Diventerà un metodo
		{
			System.out.println("Inserisci ruolo dipendente: ");
			String ruolo = scanner.nextLine().toUpperCase();
			for (Ruolo r : Ruolo.values())
			{
				if (r.name().equals(ruolo))
					return ruolo;
			}
		}
	}
}
