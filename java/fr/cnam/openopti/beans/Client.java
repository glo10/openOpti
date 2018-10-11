package fr.cnam.openopti.beans;

import java.util.regex.Pattern;

import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.BeansException;

public class Client {
	private int id_client;
	private String nom_client;
	private String prenom_client;
	private String date_naissance;
	private String num_secu;
	private String adresse;
	private String ville;
	private String cp_client;
	private String email_client;
	private String tel_client;
	
	public Client() {
		super();
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getNom_client() {
		return nom_client;
	}

	public void setNom_client(String nom_client) throws BeansException {
		if(nom_client.length() > 80)
			throw new BeansException("Le nom ne peut pas contenir plus de 80 caractères !");
		else if(!Pattern.matches(FormPattern.patternIdentite, nom_client))
			throw new BeansException("Le nom ne correspond pas au format classique d'un nom");	
		else
			this.nom_client = nom_client;
	}

	public String getPrenom_client() {
		return prenom_client;
	}

	public void setPrenom_client(String prenom_client) throws BeansException{
		if(prenom_client.length() > 80)
			throw new BeansException("Le prenom du client ne doit pas dépasser 80 caractères");
		else if(!Pattern.matches(FormPattern.patternIdentite, prenom_client))
			throw new BeansException("Le prénom ne correspond pas au format classique d'un prénom");	
		else
			this.prenom_client = prenom_client;
	}

	public String getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(String date_naissance) throws BeansException {
		if(date_naissance.length() > 10 || !Pattern.matches(FormPattern.patternDate,date_naissance))
			throw new BeansException("La date de naissance n'est pas au format jj/mm/aaaa");
		else
			this.date_naissance = date_naissance;
	}

	public String getNum_secu() {
		return num_secu;
	}

	public void setNum_secu(String num_secu) throws BeansException{
		if(num_secu.length() > 15 || !Pattern.matches(FormPattern.patternSecu, num_secu))
			throw new BeansException("Le numéro de sécurité sociale doit avoir exactement 15 chiffres, commçant par 1 ou 2 !");
		else
			this.num_secu = num_secu;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) throws BeansException{
		if(adresse.length() > 150)
			throw new BeansException("L'adresse du client ne doit pas dépasser plus de 150 caractères !");
		else if(adresse.length() > 0 && !Pattern.matches(FormPattern.patternAdresse, adresse))
			throw new BeansException("L'adresse du client ne correspond pas au format entendu !");	
		else
			this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) throws BeansException{
		if(ville.length() > 150)
			throw new BeansException("La ville ne doit pas dépasser plus de 150 caractères");
		else if(ville.length() > 0 && !Pattern.matches(FormPattern.patternAdresse, ville))
			throw new BeansException("La ville du client ne correspond pas au format entendu !");
		else
			this.ville = ville;
	}

	public String getCp_client() {
		return cp_client;
	}

	public void setCp_client(String cp_client) throws BeansException{
		String error = "Le code postal du client doit avoir exactement 5 chiffres !";
		if(cp_client.length() > 5)
			throw new BeansException(error);	
		else if(cp_client.length() > 0 && !Pattern.matches(FormPattern.patternCpClient, cp_client))
			throw new BeansException(error);
		else
			this.cp_client = cp_client;
	}

	public String getEmail_client() {
		return email_client;
	}

	public void setEmail_client(String email_client) throws BeansException{
		if(email_client.length() > 80)
			throw new BeansException("L'email du client ne doit pas dépasser 80 caractères");
		else if(email_client.length() > 0 && !Pattern.matches(FormPattern.patternEmail, email_client))
			throw new BeansException("l'email du client n'est pas au format classique d'un email ex : jean_robert@gmail.com");	
		else
			this.email_client = email_client;
	}
	
	public String getTel_client() {
		return tel_client;
	}

	public void setTel_client(String tel_client) throws BeansException{
		String error = "Le télephone doit contenir exactement 10 chiffres sans espaces, ni tirets !";
		if(tel_client.length() > 10)
			throw new BeansException(error);
		else if(tel_client.length() > 0 && !Pattern.matches(FormPattern.patternTel, tel_client))
			throw new BeansException(error);
		else
			this.tel_client = tel_client;
	}

	@Override
	public String toString() {
		return "Client [id_client=" + id_client + ", nom_client=" + nom_client + ", prenom_client=" + prenom_client
				+ ", date_naissance=" + date_naissance + ", num_secu=" + num_secu + ", adresse=" + adresse + ", ville="
				+ ville + ", cp_client=" + cp_client + ", email_client=" + email_client + "]";
	}
}
