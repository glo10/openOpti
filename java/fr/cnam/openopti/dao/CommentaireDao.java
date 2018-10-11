package fr.cnam.openopti.dao;

import java.sql.SQLException;
import java.util.Map;

import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.mesException.DaoException;

public interface CommentaireDao {
	Map<Integer,Commentaire> getAllCommentaire(String num_facture) throws DaoException;
	void ajouterCom(Commentaire commentaire) throws DaoException;
}
