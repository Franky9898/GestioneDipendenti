package sistema;

import java.sql.Connection;
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
	
	public static void visualizzaManager(Connection conn, Scanner scanner) {
		
		String query ="SELECT nome, cognome, manager.idTeamGestito AS team\n"
				+ "FROM azienda.dipendenti\n"
				+ "INNER JOIN azienda.manager\n"
				+ "ON  dipendenti.idDipendente = manager.idDipendente\n"
				+ "WHERE dipendenti.ruolo ='manager';"; 
		
		
	}
}
