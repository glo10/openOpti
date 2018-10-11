package fr.cnam.openopti.daoImpls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.cnam.openopti.beans.Mutuelle;
import fr.cnam.openopti.beans.Oam;
import fr.cnam.openopti.beans.Secu;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OamDao;
import fr.cnam.openopti.mesException.DaoException;

public class OamDaoImpl<T> implements OamDao {
	private DaoFactory daoFactory;

	public OamDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer,T> getList(List attrs, String table) throws DaoException {
		Map<Integer,T> listT = new HashMap<Integer, T>();
		Connection cnx = null;
		Statement stmt = null;
		ResultSet result = null;

		try {
			String select = "SELECT ";
			for (int i = 0; i < attrs.size(); i++) {
				select += attrs.get(i) + ",";
			}
			select = select.substring(0, select.length() - 1);
			select += " FROM " + table;

			System.out.println(select);
			cnx = daoFactory.getConnection();
			stmt = cnx.createStatement();
			result = stmt.executeQuery(select);

			System.out.println(select);
			while (result.next()) {
				Integer id_t = result.getInt(1);
				String nom_t = result.getString(2);
				Oam oam = new Oam();
				oam.setId_t(id_t);
				oam.setNom_t(nom_t);
				listT.put(oam.getId_t(), (T) oam);
			}
		}
		catch(SQLException e) {
			throw new DaoException("Impossible de récupérer les informations sur les organismes de santé !");
		}
		finally {
			DaoFactory.closeDbConnexion(result, stmt,cnx);
		}

		return listT;
	}

	@Override
	public void ajouter(Object t, String table) throws DaoException {
		Oam oam;
		String q = "INSERT INTO " + table + "( nom_" + table + ", adresse_" + table + ", ville_" + table + ", cp_"
				+ table + ", tel_" + table + ", email_" + table + ", fax_" + table + ")"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection cnx = null;
		PreparedStatement stmt = null;
		int resultat = -1;
		if (table == "mutuelle") {
			oam = (Mutuelle) t;
		} else {
			oam = (Secu) t;
		}

		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(q);
			stmt.setString(1, oam.getNom_t());
			stmt.setString(2, oam.getAdresse_t());
			stmt.setString(3, oam.getVille_t());
			stmt.setString(4, oam.getCp_t());
			stmt.setString(5, oam.getTel_t());
			stmt.setString(6, oam.getEmail_t());
			stmt.setString(7, oam.getFax_t());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void supprimer(Object t, String table) throws DaoException {
		String q = "DELETE FROM " + table + " WHERE id_" + table + " = ?";
		Connection cnx = null;
		PreparedStatement stmt = null;

		try {
			cnx = DaoFactory.getInstance().getConnection();
			stmt = cnx.prepareStatement(q);
			stmt.setInt(1, ((Oam) t).getId_t());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("null")
	@Override

	public String getOamName(int  id_oam, String table) throws DaoException{
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String nom = null;

		try {
			String select = "SELECT nom_" + table + " FROM " + table + " WHERE id_" + table + "= ?";

			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(select);
			stmt.setInt(1, id_oam);
			result = stmt.executeQuery();
			while (result.next()) {
				nom = result.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}

		return (nom.length() > 10) ? nom.substring(0, 7) + "..." : nom;
	}

	public List<Oam> getAll(String table) throws DaoException {
		String q = "SELECT *" + " FROM " + table;
		System.out.println(q);
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		List<Oam> retour = new ArrayList<>();
		Oam oam = null;

		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(q);
			result = stmt.executeQuery();
			while (result.next()) {
				if (table == "mutuelle") {
					oam = new Mutuelle();
				} else {
					oam = new Secu();
				}
				oam.setId_t(result.getInt("Id_" + table));
				oam.setAdresse_t(result.getString("adresse_" + table));
				oam.setNom_t(result.getString("nom_" + table));
				oam.setEmail_t(result.getString("email_" + table));
				oam.setCp_t(result.getString("cp_" + table));
				oam.setVille_t(result.getString("ville_" + table));
				oam.setFax_t(result.getString("fax_" + table));
				oam.setTel_t(result.getString("tel_" + table));

				retour.add(oam);
			}
		}catch(SQLException e) {
			throw new DaoException("Impossible de récupérer le nom de l'organisme de santé !");
		}finally {
			DaoFactory.closeDbConnexion(result, stmt,cnx);
		}

		return retour;
	}

	@Override
	public boolean modifier(Oam oam, String table) {

		String q = "UPDATE " + table + " SET nom_" + table + " = ?," + " adresse_" + table + " = ?," + "ville_" + table
				+ " = ?," + "  cp_" + table + " = ?," + " tel_" + table + " = ?," + " email_" + table + " = ?,"
				+ " fax_" + table + " = ? WHERE " + table + ".id_" + table + " = ?";

		Connection cnx = null;
		PreparedStatement stmt = null;
		int retour= 0;

		try {
			cnx = DaoFactory.getInstance().getConnection();
			stmt = cnx.prepareStatement(q);
			stmt.setString(1, oam.getNom_t());
			stmt.setString(2, oam.getAdresse_t());
			stmt.setString(3, oam.getVille_t());
			stmt.setString(4, oam.getCp_t());
			stmt.setString(5, oam.getTel_t());
			stmt.setString(6, oam.getEmail_t());
			stmt.setString(7, oam.getFax_t());
			stmt.setInt(8, oam.getId_t());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return (retour == 1);
	}

	@Override
	public Oam getById(int id, String table) {
		String q = "Select * from "+table+" where id_"+table+" = ?";
		Connection cnx = null;
		PreparedStatement stmt = null;
		Oam oam = null;
		
		try {
			cnx = DaoFactory.getInstance().getConnection();
			stmt = cnx.prepareStatement(q);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (table.equals("secu")) {
				oam = new Secu();
			}else {
				oam = new Mutuelle();
			}
			while (result.next()) {
				oam.setId_t(result.getInt("Id_" + table));
				oam.setAdresse_t(result.getString("adresse_" + table));
				oam.setNom_t(result.getString("nom_" + table));
				oam.setEmail_t(result.getString("email_" + table));
				oam.setCp_t(result.getString("cp_" + table));
				oam.setVille_t(result.getString("ville_" + table));
				oam.setFax_t(result.getString("fax_" + table));
				oam.setTel_t(result.getString("tel_" + table));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oam;
	}
}
