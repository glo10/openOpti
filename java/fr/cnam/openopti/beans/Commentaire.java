package fr.cnam.openopti.beans;

import java.util.regex.Pattern;

import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.BeansException;

public class Commentaire {
	private int id_com;
	private String titre;
	private String com;
	private String date_com;
	private String opticien;
	private String facture;
	
	public Commentaire() {
		super();
	}

	public int getId_com() {
		return id_com;
	}

	public void setId_com(int id_com) {
		this.id_com = id_com;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) throws BeansException {
		if(titre.length() > 80)
			throw new BeansException("Le titre ne peut pas contenir plus de 80 caractères !");
		else if(titre.length() > 0 && !Pattern.matches(FormPattern.patternTitre, titre))
			throw new BeansException("Le titre ne correspond pas au format attendu !");	
		else
			this.titre = titre;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) throws BeansException{
		if(com.length() > 1500)
			throw new BeansException("Le commentaire ne doit pas contenir plus de 1500 caractères !");
		else if(com.length() > 0 && !Pattern.matches(FormPattern.patternCom, com))
			throw new BeansException("Le nom ne correspond pas au format classique d'un nom");	
		else
			this.com = com;
	}

	public String getDate_com() {
		return date_com;
	}

	public void setDate_com(String date_com) throws BeansException {
		if(date_com.length() > 21 || !Pattern.matches(FormPattern.patternDateTime, date_com))
			throw new BeansException("La date du commentaire ne correspond au format jj/mm/aaaa � hh:mm:ss");
		else
			this.date_com = date_com;
	}

	public String getOpticien() {
		return opticien;
	}

	public void setOpticien(String opticien) throws BeansException{
		if(opticien.length() > 80)
			throw new BeansException("L'email de l'opticien ne doit pas dépasser 80 caractères");
		else if(opticien.length() > 0 && !Pattern.matches(FormPattern.patternEmail, opticien))
			throw new BeansException("l'email de l'opticien n'est pas au format classique d'un email ex : jean_robert@gmail.com");	
		else
			this.opticien = opticien;
	}
	
	public void setOpticien(String prenom,String nom) throws BeansException{
		if(prenom.length() > 80 || nom.length() > 80)
			throw new BeansException("Le nom ou le prénom de l'opticien ne doivent pas dépasser 80 caractères");
		else if(nom.length() > 0 && prenom.length() > 0 && !Pattern.matches(FormPattern.patternNomPrenom, prenom + " " + nom))
			throw new BeansException("Le nom ou le prénom de l'opticien ne correspondent pas au format attendu ex: Glodie Tshimini");	
		else
			this.opticien = prenom + " " + nom ;
	}

	public String getFacture() {
		return facture;
	}

	public void setFacture(String facture) throws BeansException{
		if(facture.length() > 80)
			throw new BeansException("Le numéro de la facture ne doit pas dépasser 80 caractères !");
		else if(!Pattern.matches(FormPattern.patternNumero, facture))
			throw new BeansException("LE numéro de la facture n'est pas au format au format attendu ex : F128569, 128569, 685485");	
		else
			this.facture = facture;
	}

	@Override
	public String toString() {
		return "Commentaire [id_com=" + id_com + ", titre=" + titre + ", com=" + com + ", date_com=" + date_com
				+ ", opticien=" + opticien + ", facture=" + facture + "]";
	}
}
