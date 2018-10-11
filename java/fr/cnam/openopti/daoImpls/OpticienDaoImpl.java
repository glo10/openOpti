package fr.cnam.openopti.daoImpls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.cnam.openopti.beans.Opticien;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OpticienDao;
import fr.cnam.openopti.fonctions.Fonction;

public class OpticienDaoImpl implements OpticienDao {
	private DaoFactory daoFactory;
	private static final String selectByLoginMdp = "SELECT * FROM opticien WHERE login = ? and mdp =?";
	private static final String selectByLogin = "SELECT * FROM opticien WHERE login = ?";
	private static final String selectAll = "SELECT * FROM opticien";
	private static final String update = "Update opticien set mdp =?, nom_opticien =?, prenom_opticien =?, fonction_opticien =? where login = ?";
	private static final String delete = "DELETE FROM opticien WHERE login = ?";
	private static final String insert = "Insert into opticien (login, mdp, nom_opticien, prenom_opticien, fonction_opticien) "
			+ "						values (?,?,?,?,?);";
	
	public OpticienDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	public Opticien getOpticienByLogin(String login, String motDePasse) {
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Opticien opticien = null;
		motDePasse = Fonction.mdpHash(motDePasse);
		System.out.println(motDePasse);
		
		try {
			
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(selectByLoginMdp);
			stmt.setString(1, login);
			stmt.setString(2, motDePasse);
			result = stmt.executeQuery();
			while(result.next()) {
				opticien = new Opticien();
				opticien.setLogin(login);
				opticien.setMotDePasse(motDePasse);
				opticien.setFonction(result.getString("fonction_opticien"));
				opticien.setNom(result.getString("nom_opticien"));
				opticien.setPrenom(result.getString("prenom_opticien"));
				opticien.setFonction(result.getString("fonction_opticien"));
			}
		}
		catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return opticien;
	}

	public Opticien getOpticienById(String login) {
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Opticien opticien = null;
		
		try {
			
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(selectByLogin);
			stmt.setString(1, login);
			result = stmt.executeQuery();
			while(result.next()) {
				opticien = new Opticien();
				opticien.setLogin(login);
				opticien.setMotDePasse(result.getString("mdp"));
				opticien.setFonction(result.getString("fonction_opticien"));
				opticien.setNom(result.getString("nom_opticien"));
				opticien.setPrenom(result.getString("prenom_opticien"));
				opticien.setFonction(result.getString("fonction_opticien"));
			}
		}
		catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return opticien;
	}
	
	public List<Opticien> getAll() {
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Opticien opticien = null;
		List<Opticien> opticiens = new ArrayList<Opticien>();
		
		
		try {
			
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(selectAll);
			result = stmt.executeQuery();
			while(result.next()) {
				opticien = new Opticien();
				opticien.setLogin(result.getString("login"));
				opticien.setMotDePasse(result.getString("mdp"));
				opticien.setFonction(result.getString("fonction_opticien"));
				opticien.setNom(result.getString("nom_opticien"));
				opticien.setPrenom(result.getString("prenom_opticien"));
				opticien.setFonction(result.getString("fonction_opticien"));
				
				opticiens.add(opticien);
			}
		}
		catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return opticiens;
	}

	public Boolean insertOpticien(Opticien opticien) {
		Connection cnx = null;
		PreparedStatement stmt = null;
		int resultat = -1;
		
		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(insert);
			stmt.setString(1, opticien.getLogin());
			stmt.setString(2, Fonction.mdpHash(opticien.getMotDePasse()));
			stmt.setString(3, opticien.getNom());
			stmt.setString(4, opticien.getPrenom());
			stmt.setString(5, opticien.getFonction());
			resultat = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (resultat == 1);
	}

	public Boolean deletOpticien(String login) {
		Connection cnx = null;
		PreparedStatement stmt = null;
		int resultat = -1;
		try {
			
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(delete);
			stmt.setString(1, login);
			resultat = stmt.executeUpdate();
			
		}
		catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return (resultat == 1);
	}

	public Boolean updateOpticien(Opticien opticien) {
		Connection cnx = null;
		PreparedStatement stmt = null;
		int resultat = -1;
		
		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.prepareStatement(update);
			stmt.setString(5, opticien.getLogin());
			stmt.setString(1, Fonction.mdpHash(opticien.getMotDePasse()));
			stmt.setString(2, opticien.getNom());
			stmt.setString(3, opticien.getPrenom());
			stmt.setString(4, opticien.getFonction());
			resultat = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (resultat == 1);
	}
	
	
}
