package fr.cnam.openopti.beans;

public class SouscritContrat {
	private int mutuelle;
	private int client;
	private String date_deb;
	private String date_fin;
	private String numero_contrat;
	private String num_adherent;
	
	public SouscritContrat() {
		super();
	}

	public int getMutuelle() {
		return mutuelle;
	}

	public void setMutuelle(int mutuelle) {
		this.mutuelle = mutuelle;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public String getDate_deb() {
		return date_deb;
	}

	public void setDate_deb(String date_deb) {
		this.date_deb = date_deb;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public String getNumero_contrat() {
		return numero_contrat;
	}

	public void setNumero_contrat(String numero_contrat) {
		this.numero_contrat = numero_contrat;
	}

	public String getNum_adherent() {
		return num_adherent;
	}

	public void setNum_adherent(String num_adherent) {
		this.num_adherent = num_adherent;
	}

	@Override
	public String toString() {
		return "SouscritContrat [mutuelle=" + mutuelle + ", client=" + client + ", date_deb=" + date_deb + ", date_fin="
				+ date_fin + ", numero_contrat=" + numero_contrat + ", num_adherent=" + num_adherent + "]";
	}
	
}
