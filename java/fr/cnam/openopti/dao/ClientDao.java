package fr.cnam.openopti.dao;


import fr.cnam.openopti.beans.Client;
import fr.cnam.openopti.mesException.DaoException;

public interface ClientDao {
	int getClient(Client client) throws DaoException;
	String getClientNomPrenom(int id_client) throws DaoException;
}
