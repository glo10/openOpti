package fr.cnam.openopti.beans;

public class Opticien {
	String nom;
	String prenom;
	String login;
	String motDePasse;
	String fonction;
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	
	@Override
	public String toString() {
		return "Opticien [nom=" + nom + ", prenom=" + prenom + ", login=" + login + ", motDePasse=" + motDePasse
				+ ", fonction=" + fonction + "]";
	}
	

}
