package fr.cnam.openopti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.cnam.openopti.daoImpls.*;
import fr.cnam.openopti.mesException.DaoException;

public class DaoFactory {
	private String url;
	private String user;
	private String mdp;
	
	public DaoFactory(String url, String user, String mdp) {
		super();
		this.url = url;
		this.user = user;
		this.mdp = mdp;
	}

	public static DaoFactory getInstance(){
		//Charge le driver et se connecte au système de stockage & return une instance de DaoFactory
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			//TODO
			System.out.println("Erreur de connexion � la BDD");
		}
		
		return new DaoFactory("jdbc:mysql://localhost/openopti?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","openOpti","Acsid2018*");
	} 	
	
	public Connection getConnection() throws SQLException{
		//Recupère à tout moment une connexion à la base de données & retourne un objet Connection
		return DriverManager.getConnection(url,user,mdp);
	}
	
	
	public static void closeStatement(PreparedStatement stmt) throws SQLException {
		if(stmt != null) 
			stmt.close();
	}
	
	public static void closeStatement(Statement stmt) throws SQLException {
		if(stmt != null) 
			stmt.close();
	}
	
	public static void closeResult(ResultSet result) throws SQLException {
		if(result != null) 
			result.close();
	}
	
	public static void closeConnexion(Connection cnx) throws SQLException {
		if(cnx != null) 
			cnx.close();
	}
	
	public static void closeDbConnexion(ResultSet result,PreparedStatement stmt,Connection cnx) throws DaoException {
		try {
			closeResult(result);
			closeStatement(stmt);
			closeConnexion(cnx);

		} catch (SQLException e) {
			throw new DaoException("Impossible d'établir la connexion avec la base de données !");
		}
	}
	
	public static void closeDbConnexion(ResultSet result,Statement stmt,Connection cnx) throws DaoException{
		try {
			closeResult(result);
			closeStatement(stmt);
			closeConnexion(cnx);
		} catch (SQLException e) {
			throw new DaoException("Impossible d'établir la connexion avec la base de données !");
		}
	}
	
	public static void closeDbConnexion(PreparedStatement stmt,Connection cnx) throws DaoException{
		try {
			closeStatement(stmt);
			closeConnexion(cnx);
		} catch (SQLException e) {
			throw new DaoException("Impossible d'établir la connexion avec la base de données !");
		}
	}
	
	public static void closeDbConnexion(Statement stmt,Connection cnx) throws DaoException{
		try {
			closeStatement(stmt);
			closeConnexion(cnx);
		} catch (SQLException e) {
			throw new DaoException("Impossible d'établir la connexion avec la base de données !");
		}
	}
	
	public ClientDao getClientDao() {
		return new ClientDaoImpl(this);
	}
	
	public CommentaireDao getCommentaireDao() {
		return new CommentaireDaoImpl(this);
	}
	
	public FactureDao getFactureDao() {
		return new FactureDaoImpl(this);
	}
	
	public OamDao getOamDao() {
		return new OamDaoImpl(this);
	}
	
	public OpticienDao getOpticienDao() {
		return new OpticienDaoImpl(this);
	}
	
	public SouscritContratDao getSouscritContrat() {
		return new SouscritContratDaoImpl(this);
	}
	
	public BilanDao getBilanDao() {
		return new BilanDaoImpl(this);
	}
}
