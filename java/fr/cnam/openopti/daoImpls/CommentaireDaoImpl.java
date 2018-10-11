package fr.cnam.openopti.daoImpls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.dao.CommentaireDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.BeansException;
import fr.cnam.openopti.mesException.DaoException;

public class CommentaireDaoImpl implements CommentaireDao {
	private DaoFactory daoFactory;
	
	public CommentaireDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public Map<Integer, Commentaire> getAllCommentaire(String num) throws DaoException{
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Map<Integer,Commentaire> coms = new LinkedHashMap<Integer,Commentaire>();
		
		try {
			cnx = daoFactory.getConnection();
			String select = "SELECT		   C.id_com,"
										+ "C.titre,"
										+ "C.com,"
										+ "DATE_FORMAT(C.date_com,'%d/%m/%Y � %H:%i:%s'),"
										+ "O.prenom_opticien,"
										+ "O.nom_opticien,"
										+ "C.facture "
										+ "FROM commentaire C "
										+ "LEFT JOIN opticien O "
										+ "ON C.opticien = O.login "
										+ "WHERE facture = ? ORDER BY date_com DESC";
			stmt = cnx.prepareStatement(select,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, num);
			result = stmt.executeQuery();
			while(result.next()) {
				Commentaire newCom = new Commentaire();
				int id_com = result.getInt(1);
				String titre = result.getString(2);
				String com = result.getString(3);
				String date_com = result.getString(4);
				String prenom_opticien = result.getString(5);
				String nom_opticien = result.getString(6);
				String facture = result.getString(7);
				newCom.setId_com(id_com);
				newCom.setTitre(titre);
				newCom.setCom(com);
				newCom.setDate_com(date_com);
				newCom.setOpticien(prenom_opticien,nom_opticien);
				newCom.setFacture(facture);
				coms.put(newCom.getId_com(), newCom);
			}
		}
		catch(BeansException e) {
			throw new DaoException("Veuillez vérifier les informations saisies sur le commentaire !" + e.getMessage());
		}
		catch(SQLException e) {
			throw new DaoException("Le commentaire n'a pas été ajouté");
		}
		finally {
			DaoFactory.closeDbConnexion(result, stmt,cnx);
		}
		return coms;
	}

	@Override
	public void ajouterCom(Commentaire commentaire) throws DaoException {
		Connection cnx = null;
		PreparedStatement stmt = null;
		try {
			String insert = "INSERT INTO commentaire(titre,com,opticien,facture) VALUES(?,?,?,?)";
			cnx = daoFactory.getConnection();
			
			stmt = cnx.prepareStatement(insert);
			stmt.setString(1, commentaire.getTitre());
			stmt.setString(2, commentaire.getCom());
			stmt.setString(3, commentaire.getOpticien());
			stmt.setString(4, commentaire.getFacture());
			
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			throw new DaoException("Le commentaire n'a pas été ajouté, Veuillez ressayer");
		}
		finally {
			DaoFactory.closeDbConnexion(stmt,cnx);
		}
	}
}
