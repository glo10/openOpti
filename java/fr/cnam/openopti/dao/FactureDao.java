package fr.cnam.openopti.dao;
import java.util.Map;

import fr.cnam.openopti.beans.Client;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.beans.SouscritContrat;
import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.mesException.DaoException;

public interface FactureDao {
	Map<String, Facture<String>> listFacture(String filtre1,String filtre2) throws DaoException;
	Map<String,String> factureDetaillee(String facture) throws DaoException;
	void ajouterFacture(Client client,SouscritContrat souscrit,Facture<String> facture) throws DaoException;
	void acquitterFacture(Facture<String> facture, String typeRemboursement,Commentaire commentaire) throws DaoException;
	int totalFactureAttente() throws DaoException;
}
