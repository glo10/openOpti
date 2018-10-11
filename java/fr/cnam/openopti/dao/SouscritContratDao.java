package fr.cnam.openopti.dao;

import fr.cnam.openopti.beans.SouscritContrat;
import fr.cnam.openopti.mesException.DaoException;

public interface SouscritContratDao {
	void ajouterSouscrit(SouscritContrat souscrit) throws DaoException;

}
