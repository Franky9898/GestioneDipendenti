package sistema;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String risposta;
        boolean uscita = false; // condizione di ripetizione del ciclo

        System.out.println("Benvenuto!");

        while (!uscita) {
            System.out.println("\nMenu:");
            System.out.println("1. Opzione 1");
            System.out.println("2. Opzione 2");
            System.out.println("3. Opzione 3");
            System.out.println("4. Opzione 4");
            System.out.println("5. Esci");
            System.out.print("Inserisci comando: ");

            risposta = scanner.nextLine();

            switch (risposta) {
                case "1":
                    System.out.println("opzione 1.");
                    break;
                case "2":
                    System.out.println("opzione 2.");
                    break;
                case "3":
                    System.out.println("opzione 3.");
                    break;
                case "4":
                    System.out.println("opzione 4.");
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

        scanner.close();
    }
}