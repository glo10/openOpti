package fr.cnam.openopti.daoImpls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.cnam.openopti.beans.Client;
import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.beans.SouscritContrat;
import fr.cnam.openopti.dao.ClientDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.FactureDao;
import fr.cnam.openopti.fonctions.Fonction;
import fr.cnam.openopti.mesException.DaoException;

public class FactureDaoImpl implements FactureDao {
	private DaoFactory daoFactory;
	
	public FactureDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public Connection connection() throws SQLException {
		return daoFactory.getConnection();
	}
	
	public Map<String, Facture<String>> listFacture(String filtre1,String filtre2) throws DaoException { 
		Map<String, Facture<String>> factures = new LinkedHashMap<String,Facture<String>>();
		Connection cnx = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.createStatement();
			String request = "SELECT   F.num_facture,"
									+ "DATE_FORMAT(F.date_facture,'%d/%m/%Y')	AS date_facture,"
									+ "DATE_FORMAT(F.date_envoi,'%d/%m/%Y')		AS date_envoi,"
									+ "F.libelle_equipement,"
									+ "F.montant_total,"
									+ "F.montant_remb_mut,"
									+ "F.montant_remb_secu,"

									+ "M.nom_mutuelle							AS mutuelle,"
									+ "S.nom_secu								AS secu,"
									+ "C.nom_client								AS client,"
									+ "F.etat "
									+ "FROM										facture F LEFT JOIN secu S ON F.secu = S.id_secu JOIN mutuelle M ON M.id_mutuelle = F.mutuelle JOIN client C ON F.client = C.id_client "
									+ "WHERE									LOWER(F.etat) <> \"acquitt�e\"";
			
			if(filtre2 != null) {
				request +=	" AND (M.nom_mutuelle='" + filtre2 + "' OR F.num_facture = '" + filtre2 + "' OR S.nom_secu='" + filtre2 + "')";
			}
			
			if(filtre1 != null) {
				switch(filtre1) {
					case "date_facture ASC":
						request += " ORDER BY				DATE_FORMAT(date_facture,'%Y-%m-%d') ASC";
					break;
					case "date_facture DESC":
						request += " ORDER BY				DATE_FORMAT(date_facture,'%Y-%m-%d') DESC";
					break;
					case "montant_remb_mut ASC":
					case "montant_remb_mut DESC":
						request += " ORDER BY				" + filtre1;
					break;
				}
			}
			
			
			result = stmt.executeQuery(request);
			System.out.println(request);
			
			while(result.next()) {
				String num_facture = result.getString("num_facture");
				String date_facture = result.getString("date_facture");
				String date_envoi = result.getString("date_envoi");
				String libelle_equipement = result.getString("libelle_equipement");
				Float montant_total = result.getFloat("montant_total");
				Float montant_remb_mut = result.getFloat("montant_remb_mut");
				Float montant_remb_secu = result.getFloat("montant_remb_secu");
				String id_mutuelle = Fonction.showLessString(result.getString("mutuelle"),10);
				String id_secu = Fonction.showLessString(result.getString("secu"),10);
				String id_client = Fonction.showLessString(result.getString("client"),10);
				String etat = result.getString("etat");
				
				Facture<String> facture = new Facture<String>();
				
				facture.setNum_facture(num_facture);
				facture.setDate_facture(date_facture);
				facture.setLibelle_equipement(libelle_equipement);
				facture.setDate_envoi(date_envoi);
				facture.setMontant_total(montant_total);
				facture.setMontant_remb_mut(montant_remb_mut);
				facture.setMontant_remb_secu(montant_remb_secu);
				facture.setMutuelle(id_mutuelle);
				facture.setSecu(id_secu);
				facture.setClient(id_client);
				facture.setEtat(etat);
				factures.put(num_facture,facture);
			}
		}
		catch(SQLException e) {
			throw new DaoException("Impossible d'établir la connexion avec la base de données, veuillez réessayer ultérieurement");
		}
		finally {
			DaoFactory.closeDbConnexion(result, stmt, cnx);
		}
		
		return factures;
	}

	@Override
	public void ajouterFacture(Client client,SouscritContrat souscrit,Facture<String> facture) throws DaoException {
		Connection cnx = null;
		PreparedStatement stmtClient = null;
		PreparedStatement stmtSouscrit = null;
		PreparedStatement stmtFacture = null;
		ResultSet resultClient = null;
		int id_client = 0;
		ClientDao clientDao = daoFactory.getClientDao();
		
		try {
			cnx = daoFactory.getConnection();
			cnx.setAutoCommit(false);
			
			//--------RECUPERATION ID_CLIENT DANS LA TABLE CLIENT----//
			String insertClient = "INSERT INTO client("
					+ "nom_client,"
					+ "prenom_client,"
					+ "date_naissance,"
					+ "num_secu";
			String valuesClient = " VALUES(?,?,DATE(STR_TO_DATE(?,'%Y-%m-%d')),?";
			int i = 4;
			if(client.getAdresse().length() > 0) {
						insertClient += ",adresse_client";
						valuesClient +=",?";
			}
			
			if(client.getCp_client().length() > 0) {
						insertClient += ",cp_client";
						valuesClient +=",?";
			}
			
			if(client.getVille().length() > 0) {
						insertClient += ",ville_client";
						valuesClient +=",?";
			}
			
			if(client.getEmail_client().length() > 0) {
						insertClient += ",email_client";
						valuesClient +=",?";
			}
			
			if(client.getTel_client().length() > 0) {
				insertClient += ",tel_client";
				valuesClient +=",?";
			}
			
			insertClient +=")";
			valuesClient +=") ON DUPLICATE KEY UPDATE id_client = id_client";
			String sqlClient = insertClient + valuesClient;
			
			id_client = clientDao.getClient(client);
			if(id_client == 0) {
			
				stmtClient = cnx.prepareStatement(sqlClient,PreparedStatement.RETURN_GENERATED_KEYS);
				
				stmtClient.setString(1, client.getNom_client());
				stmtClient.setString(2, client.getPrenom_client());
				stmtClient.setString(3, client.getDate_naissance());
				stmtClient.setString(4, client.getNum_secu());
				
				if(client.getAdresse().length() > 0)
					stmtClient.setString(++i, client.getAdresse());
				if(client.getCp_client().length() > 0)
					stmtClient.setString(++i,client.getCp_client());
				if(client.getVille().length() > 0)
					stmtClient.setString(++i, client.getVille());
				if(client.getEmail_client().length() > 0)
					stmtClient.setString(++i, client.getEmail_client());
				if(client.getTel_client().length() > 0)
					stmtClient.setString(++i, client.getTel_client());
				
				stmtClient.executeUpdate();
				resultClient = stmtClient.getGeneratedKeys();
				
				if(resultClient.next()) {
					id_client = resultClient.getInt(1);
				}
			}
			
			client.setId_client(id_client);

			//------INSERTION DANS LA TABLE SOUSCRIT_CONTRAT-------//
			
			String insertContrat = "INSERT INTO souscrit_contrat("
																	+ "id_mutuelle,"
																	+ "id_client,"
																	+ "date_deb,"
																	+ "date_fin";
			String valuesContrat = "VALUES(?,?,DATE(STR_TO_DATE(?,'%Y-%m-%d')),DATE(STR_TO_DATE(?,'%Y-%m-%d')) ";
			int j = 4;
			if(souscrit.getNumero_contrat().length() > 0) {
				insertContrat 										+= ",num_contrat";
				valuesContrat += ",?";
			}
			
			if(souscrit.getNum_adherent().length() > 0) {
				insertContrat 										+= ",num_adherent";
				valuesContrat += ",?";
			}
			
			insertContrat += ") ";
			valuesContrat += ") ON DUPLICATE KEY UPDATE id_mutuelle = id_mutuelle,id_client=id_client,date_deb=date_deb";
			String sqlContrat = insertContrat + valuesContrat;
			System.out.println(sqlContrat);
			System.out.println(souscrit.toString());
			stmtSouscrit = cnx.prepareStatement(sqlContrat);
			
			stmtSouscrit.setInt(1, souscrit.getMutuelle());
			stmtSouscrit.setInt(2, client.getId_client());
			stmtSouscrit.setString(3, souscrit.getDate_deb());
			stmtSouscrit.setString(4, souscrit.getDate_fin());
			
			if(souscrit.getNumero_contrat().length() > 0)
				stmtSouscrit.setString(++j,souscrit.getNumero_contrat());
			
			if(souscrit.getNum_adherent().length() > 0)
				stmtSouscrit.setString(++j, souscrit.getNum_adherent());
			
			stmtSouscrit.executeUpdate();
			
			//------INSERTION DANS LA TABLE FACTURE---//
			String insertFacture = "INSERT INTO facture("
												+ "num_facture,"
												+ "date_facture,"
												+ "libelle_equipement,"
												+ "date_envoi,"
												+ "montant_total,"
												+ "montant_remb_mut,"
												+ "montant_remb_secu,"
												+ "mutuelle,"
												+ "client";
			String valuesFacture =  " VALUES(?,DATE(STR_TO_DATE(?,'%Y-%m-%d')),?,DATE(STR_TO_DATE(?,'%Y-%m-%d')),?,?,?,?,?";
			int k = 9;
			if(facture.getNum_lot().length() > 0) {
				insertFacture += ",num_lot";
				valuesFacture +=",?";
			}
			
			if(facture.getSecu().length()  > 0) {
				insertFacture += ",secu";
				valuesFacture +=",?";
			}
			
			String sqlFacture = insertFacture + ")" + valuesFacture + ")";
			stmtFacture = cnx.prepareStatement(sqlFacture);
			
			stmtFacture.setString(1, facture.getNum_facture());
			stmtFacture.setString(2, facture.getDate_facture());
			stmtFacture.setString(4, facture.getDate_envoi());
			stmtFacture.setString(3, facture.getLibelle_equipement());
			stmtFacture.setFloat(5, facture.getMontant_total());
			stmtFacture.setFloat(6, facture.getMontant_remb_mut());
			stmtFacture.setFloat(7, facture.getMontant_remb_secu());
			stmtFacture.setObject(8, facture.getMutuelle());
			stmtFacture.setInt(9, client.getId_client());
			
			if(facture.getNum_lot().length() > 0)
				stmtFacture.setString(++k, facture.getNum_lot());
			if(facture.getSecu().length() > 0)
				stmtFacture.setString(++k,facture.getSecu());
			
			stmtFacture.executeUpdate();
			cnx.commit();
		}
		catch(SQLException e) {
			if(cnx != null) {
				try {
					cnx.rollback();
				}
				catch(SQLException ex) {
					//throw new DaoException("Impossible de communiquer avec la base de données !");
				}
			}
			System.out.println("veritable erreur " + e.getMessage());
			throw new DaoException("La facture n'a pas été ajouté, Veuillez vérifier les données saisies en particulier que le numéro de la facture" + e.getMessage());
		}
		finally {
			try {
				DaoFactory.closeResult(resultClient);
				DaoFactory.closeStatement(stmtFacture);
				DaoFactory.closeStatement(stmtSouscrit);
				DaoFactory.closeStatement(stmtClient);
				DaoFactory.closeConnexion(cnx);
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données !");
			}
				
		}
	}

	@Override
	public Map<String, String> factureDetaillee(String num) throws DaoException{
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Map<String,String> data = new LinkedHashMap<String,String>();
			
		try {
			cnx = daoFactory.getConnection();
			String select = "SELECT    F.num_facture,"
									+ "DATE_FORMAT(F.date_facture,'%d/%m/%Y') AS date_facture,"
									+ "F.libelle_equipement,"
									+ "F.etat,F.date_envoi,"
									+ "DATE_FORMAT(F.date_creation,'%d/%m/%Y') AS date_creation,"
									+ "C.num_secu,"
									+ "C.nom_client,"
									+ "C.prenom_client,"
									+ "DATE_FORMAT(C.date_naissance,'%d/%m/%Y') AS date_naissance,"
									+ "C.tel_client,"
									+ "C.adresse_client,"
									+ "C.ville_client,"
									+ "C.cp_client,"
									+ "S.num_adherent,"
									+ "S.num_contrat,"
									+ "DATE_FORMAT(S.date_deb,'%d/%m/%Y') AS date_deb,"
									+ "DATE_FORMAT(S.date_fin,'%d/%m/%Y') AS date_fin,"
									+ "M.nom_mutuelle,"
									+ "M.tel_mutuelle,"
									+ "M.fax_mutuelle,"
									+ "M.email_mutuelle,"
									+ "R.nom_secu,"
									+ "R.tel_secu,"
									+ "F.montant_total,"
									+ "F.montant_remb_mut,"
									+ "F.montant_remb_secu,"
									+ "F.num_lot,"
									+ "DATE_FORMAT(F.date_paiement_secu,'%d/%m/%Y') AS date_paiement_secu,"
									+ "DATE_FORMAT(F.date_paiement_mut,'%d/%m/%Y') AS date_paiement_mut "
							+ "FROM facture F "
							+ "LEFT JOIN client C "
							+ "ON F.client = C.id_client "
							+ "LEFT JOIN mutuelle M "
							+ "ON F.mutuelle = M.id_mutuelle "
							+ "LEFT JOIN secu R "
							+ "ON F.secu = R.id_secu "
							+ "LEFT JOIN souscrit_contrat S "
							+ "ON F.mutuelle = S.id_mutuelle AND F.client = S.id_client "
							+ "WHERE F.num_facture = ?";
			stmt = cnx.prepareStatement(select);
			stmt.setString(1,num);
			System.out.println(select);
			result = stmt.executeQuery();
			
			while(result.next()) {
				String num_facture = result.getString("num_facture");
				String date_facture = result.getString("date_facture");
				String libelle_equipement = result.getString("libelle_equipement");
				String etat = result.getString("etat");
				String date_envoi = result.getString("date_envoi");
				String date_creation = result.getString("date_creation");
				String num_secu = result.getString("num_secu");
				String nom_client = result.getString("nom_client");
				String prenom_client = result.getString("prenom_client");
				String date_naissance = result.getString("date_naissance");
				String tel_client = result.getString("tel_client");
				String adresse_client = result.getString("adresse_client");
				String ville_client = result.getString("ville_client");
				String cp_client = result.getString("cp_client");
				String num_adherent = result.getString("num_adherent");
				String num_contrat = result.getString("num_contrat");
				String date_deb = result.getString("date_deb");
				String date_fin = result.getString("date_fin");
				String nom_mutuelle = result.getString("nom_mutuelle");
				String tel_mutuelle = result.getString("tel_mutuelle");
				String fax_mutuelle = result.getString("fax_mutuelle");
				String email_mutuelle = result.getString("email_mutuelle");
				String nom_secu = result.getString("nom_secu");
				String tel_secu = result.getString("tel_secu");
				String montant_total = result.getString("montant_total") + " euros";
				String montant_remb_mut = result.getString("montant_remb_mut") + " euros";
				String montant_remb_secu = result.getString("montant_remb_secu") + " euros";
				String num_lot = result.getString("num_lot");
				String date_paiement_secu = result.getString("date_paiement_secu");
				String date_paiement_mut = result.getString("date_paiement_mut");
				
				data.put("Num�ro",num_facture);
 				data.put("Date",date_facture);
 				data.put("�quipement",libelle_equipement);
 				data.put("�tat",etat);
 				data.put("Date d'envoie",date_envoi);
 				data.put("Date de cr�ation",date_creation);
 				data.put("Num�ro de s�curit� sociale",num_secu);
 				data.put("Nom",nom_client);
 				data.put("Pr�nom",prenom_client);
 				data.put("Date de naissance",date_naissance);
 				data.put("T�lephone client",tel_client);
 				data.put("Adresse client",adresse_client);
 				data.put("Ville client",ville_client);
 				data.put("Code postale client",cp_client);
 				data.put("Num�ro adh�rent",num_adherent);
 				data.put("Num�ro du contrat",num_contrat);
 				data.put("Date du d�but du contrat",date_deb);
 				data.put("Date de fin du contrat",date_fin);
 				data.put("Mutuelle",nom_mutuelle);
 				data.put("T�lephone mutuelle",tel_mutuelle);
 				data.put("Fax mutuelle",fax_mutuelle);
 				data.put("Email mutuelle",email_mutuelle);
 				data.put("S�curit� sociale",nom_secu);
 				data.put("T�lephone s�curit� sociale",tel_secu);
 				data.put("Montant total",montant_total);
 				data.put("Montant de remboursement de la mutuelle",montant_remb_mut);
 				data.put("Montant de remboursement de la s�curit� sociale",montant_remb_secu);
 				data.put("Num�ro du lot de t�l�transmission",num_lot);
 				data.put("Date de paiement de la s�curit� sociale",date_paiement_secu);
 				data.put("Date de paiement de la mutuelle",date_paiement_mut);
			}
			
		}
		catch(SQLException e) {
			throw new DaoException("Impossible de se connecter à la base de données, veuillez recommencer ultérieurement");
		}
		finally {
			DaoFactory.closeDbConnexion(result, stmt, cnx);
		}
		
		return data;
	}

	public void acquitterFacture(Facture<String> facture, String typeRemboursement, Commentaire commentaire) throws DaoException {
		Connection cnx = null;
		PreparedStatement stmtFacture = null;
		PreparedStatement stmtCom = null;
		String update = "UPDATE facture SET etat = ?,";
		try {
			switch(typeRemboursement) {
				case "mutuelle":
					update += "date_paiement_mut= DATE(STR_TO_DATE(?, '%Y-%m-%d')) ";
					
				break;
				case "secu":
					update += "date_paiement_secu = DATE(STR_TO_DATE(?, '%Y-%m-%d')) ";
				break;
				case "mutuelle_et_secu":
					update += "date_paiement_mut = DATE(STR_TO_DATE(?, '%Y-%m-%d')), date_paiement_secu = DATE(STR_TO_DATE(?, '%Y-%m-%d')) ";
				break;
					
			}
			
			update += "WHERE num_facture = ?";
			cnx = daoFactory.getConnection();
			cnx.setAutoCommit(false);
			stmtFacture = cnx.prepareStatement(update);
			String date_secu = facture.getDate_paiement_secu();
			String date_mut = facture.getDate_paiement_mut();
			switch(typeRemboursement) {
				case "secu":
					stmtFacture.setString(1, "remb secu ok");
					stmtFacture.setString(2, date_secu);
					stmtFacture.setString(3, facture.getNum_facture());
				break;
				case "mutuelle":
					stmtFacture.setString(1, "remb mut ok");
					stmtFacture.setString(2, date_mut);
					stmtFacture.setString(3, facture.getNum_facture());
				break;
				case "mutuelle_et_secu":
					stmtFacture.setString(1, "acquitt�e");
					stmtFacture.setString(2, date_mut);
					stmtFacture.setString(3, date_secu);
					stmtFacture.setString(4, facture.getNum_facture());
				break;
			}
			
			System.out.println(stmtFacture);
			stmtFacture.executeUpdate();
			String insert = "INSERT INTO commentaire(titre,com,opticien,facture) VALUES(?,?,?,?)";
			
			if(commentaire.getTitre().length() > 0 && commentaire.getCom().length() > 0) {
				stmtCom = cnx.prepareStatement(insert);
				stmtCom.setString(1, commentaire.getTitre());
				stmtCom.setString(2, commentaire.getCom());
				stmtCom.setString(3, commentaire.getOpticien());
				stmtCom.setString(4, commentaire.getFacture());
				System.out.println(commentaire.toString());
				
				stmtCom.executeUpdate();
			}
			cnx.commit();
		}
		catch(SQLException e) {
			try {
				cnx.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("erreur " + e.getMessage());
			throw new DaoException("le reglèment " + typeRemboursement + " n'a pas été ajouté pour la facture " + facture.getNum_facture());
		}
		finally {
			try {
				DaoFactory.closeStatement(stmtCom);
				DaoFactory.closeStatement(stmtFacture);
				DaoFactory.closeConnexion(cnx);
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données !");
			}
		}
	}

	@Override
	public int totalFactureAttente() throws DaoException{
		int total = 0;
		Connection cnx = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			cnx = daoFactory.getConnection();
			stmt = cnx.createStatement();
			String select = "SELECT count(*) AS total "
					 	  + "FROM facture "
					 	  + "WHERE etat <> 'acquitt�e'";
			
			result = stmt.executeQuery(select);
			
			while(result.next()){
				total = result.getInt("total");
			}
			
		}
		catch(SQLException e) {
			throw new DaoException("Impossible de récupérer le nombre total des factures en attente de paiement !");
		}
		finally {
			DaoFactory.closeDbConnexion(result,stmt,cnx);
		}
		
		return total;
	}
}
