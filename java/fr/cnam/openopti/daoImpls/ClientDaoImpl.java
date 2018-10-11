package fr.cnam.openopti.daoImpls;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import fr.cnam.openopti.beans.Client;
import fr.cnam.openopti.dao.ClientDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.fonctions.Fonction;
import fr.cnam.openopti.mesException.DaoException;

public class ClientDaoImpl implements ClientDao {
	private DaoFactory daoFactory;
	
	public ClientDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public int getClient(Client client) throws DaoException {
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			cnx = daoFactory.getConnection();
			String select = "SELECT id_client"
						 + " FROM client ";
			String where = " WHERE "
									+ "nom_client = ? "
									+ "AND prenom_client = ? "
									+ "AND date_naissance = ? "
									+ "AND num_secu = ? ";
			
			String sql = select + where;
			System.out.println(sql);
			
			stmt = cnx.prepareStatement(sql);
			stmt.setString(1, client.getNom_client());
			stmt.setString(2, client.getPrenom_client());
			stmt.setString(3,client.getDate_naissance());
			stmt.setString(4,client.getNum_secu());
			
			result = stmt.executeQuery();
			
			while(result.next()) {
				client.setId_client(result.getInt(1));
			}
			
		}
		catch(SQLException e) {
			throw new DaoException("Impossible de récupérer les informations sur le client !");
			
		}
		finally {
			DaoFactory.closeDbConnexion(result, stmt,cnx);
		}
		
		return client.getId_client();
	}

	@Override
	public String getClientNomPrenom(int id_client) throws DaoException {
			Connection cnx = null;
			PreparedStatement stmt = null;
			ResultSet result = null;
			String nomPrenom = null;
			
			try {
				String select = "SELECT nom_client, prenom_client FROM client WHERE id_client = ?";
				
				cnx = daoFactory.getConnection();
				stmt = cnx.prepareStatement(select);
				stmt.setInt(1, id_client);
				result = stmt.executeQuery();
				while(result.next()) {
					nomPrenom = result.getString(1) + " " + result.getString(2);
				}
			}
			catch(SQLException e) {
				throw new DaoException("Impossible de récupérer le nom et le prénom du client !");
			}
			finally {
				DaoFactory.closeDbConnexion(result, stmt,cnx);
			}
			
			return nomPrenom;
	}

}
