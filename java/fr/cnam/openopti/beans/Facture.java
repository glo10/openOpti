package fr.cnam.openopti.beans;

public class Facture <T>{
	private String num_facture;
	private String date_facture;
	private String libelle_equipement;
	private String date_creation;
	private String date_envoi;
	private Float montant_total;
	private String date_paiement_mut;
	private Float montant_remb_mut;
	private Float montant_remb_secu;
	private String date_paiement_secu;
	private T num_lot;
	private T mutuelle;
	private T secu;
	private T client;
	private String etat;
	
	public Facture() {
		super();
	}
	

	public String getNum_facture() {
		return num_facture;
	}
	
	public String getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(String date_facture) {
		this.date_facture = date_facture;
	}

	public void setNum_facture(String num_facture) {
		this.num_facture = num_facture;
	}
	
	public String getLibelle_equipement() {
		return libelle_equipement;
	}
	
	public void setLibelle_equipement(String libelle_equipement) {
		this.libelle_equipement = libelle_equipement;
	}
	
	public String getDate_creation() {
		return date_creation;
	}
	
	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}
	
	public String getDate_envoi() {
		return date_envoi;
	}

	public void setDate_envoi(String date_envoi) {
		this.date_envoi = date_envoi;
	}

	public Float getMontant_total() {
		return montant_total;
	}
	
	public void setMontant_total(Float montant_total) {
		this.montant_total = montant_total;
	}
	
	public String getDate_paiement_mut() {
		return date_paiement_mut;
	}
	
	public void setDate_paiement_mut(String date_paiement_mut) {
		this.date_paiement_mut = date_paiement_mut;
	}
	
	public Float getMontant_remb_mut() {
		return montant_remb_mut;
	}
	
	public void setMontant_remb_mut(Float montant_remb_mut) {
		this.montant_remb_mut = montant_remb_mut;
	}
	
	public Float getMontant_remb_secu() {
		return montant_remb_secu;
	}
	
	public void setMontant_remb_secu(Float montant_remb_secu) {
		this.montant_remb_secu = montant_remb_secu;
	}
	
	public String getDate_paiement_secu() {
		return date_paiement_secu;
	}
	
	public void setDate_paiement_secu(String date_paiement_secu) {
		this.date_paiement_secu = date_paiement_secu;
	}
	

	public T getNum_lot() {
		return num_lot;
	}


	public void setNum_lot(T num_lot) {
		this.num_lot = num_lot;
	}


	public T getMutuelle() {
		return mutuelle;
	}


	public void setMutuelle(T mutuelle) {
		this.mutuelle = mutuelle;
	}


	public T getSecu() {
		return secu;
	}


	public void setSecu(T secu) {
		this.secu = secu;
	}


	public T getClient() {
		return client;
	}


	public void setClient(T client) {
		this.client = client;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}


	@Override
	public String toString() {
		return "Facture [num_facture=" + num_facture + ", date_facture=" + date_facture + ", libelle_equipement="
				+ libelle_equipement + ", date_creation=" + date_creation + ", date_envoi=" + date_envoi
				+ ", montant_total=" + montant_total + ", date_paiement_mut=" + date_paiement_mut
				+ ", montant_remb_mut=" + montant_remb_mut + ", montant_remb_secu=" + montant_remb_secu
				+ ", date_paiement_secu=" + date_paiement_secu + ", num_lot=" + num_lot + ", mutuelle=" + mutuelle
				+ ", secu=" + secu + ", client=" + client + ", etat=" + etat + "]";
	}
	
}
