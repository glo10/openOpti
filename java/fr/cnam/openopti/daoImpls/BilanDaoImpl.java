package fr.cnam.openopti.daoImpls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.cnam.openopti.dao.BilanDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.fonctions.Fonction;
import fr.cnam.openopti.mesException.DaoException;

public class BilanDaoImpl implements BilanDao {
	private DaoFactory daoFactory;

	public BilanDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public LinkedHashMap<String, String[]> getBilan(String filtre) throws DaoException {
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		LinkedHashMap<String, String[]> reporting = new LinkedHashMap<String,String[]>();
		
		try {
			cnx = daoFactory.getConnection();
			String select = "SELECT		nom_mutuelle,"
									 + "SUM(total_dossier) AS total_dossier,"
									 + "AVG(panier_moyen) AS panier_moyen,"
									 + "SUM(total_encaissee) AS total_encaissee,"
									 + "SUM(total_a_encaisser) AS total_a_encaisser,"
									 + "(SUM(total_a_encaisser) + SUM(total_encaissee)) AS total,"
									 + "(total_encaissee/(SUM(total_a_encaisser) + SUM(total_encaissee))) AS taux "
							+ "FROM ("
										+ "SELECT		M.nom_mutuelle,"
													 + "count(F.num_facture) AS total_dossier,"
													 + "AVG(F.montant_remb_mut) AS panier_moyen,"
													 + "SUM(F.montant_remb_mut) AS total_encaissee,"
													 + "0 as total_a_encaisser "
										+ "FROM facture F "
										+ "JOIN mutuelle M "
										+ "ON F.mutuelle = M.id_mutuelle  "
										+ "WHERE F.etat = ? OR F.etat = ? "
										+ "GROUP BY nom_mutuelle "
										+ "UNION "
										+ "SELECT		M.nom_mutuelle,"
													 + "count(F.num_facture) AS total_dossier,"
													 + "AVG(F.montant_remb_mut) AS panier_moyen,"
													 + "0 as total_encaissee,"
													 + "SUM(F.montant_remb_mut) AS total_encaissee "
										+ "FROM facture F "
										+ "JOIN mutuelle M "
										+ "ON F.mutuelle = M.id_mutuelle "
										+ "WHERE F.etat = ? OR F.etat = ? "
										+ "GROUP BY nom_mutuelle"
							+ ") AS R "
							+ "GROUP BY nom_mutuelle "
							+ "ORDER BY " + filtre;
			
			stmt = cnx.prepareStatement(select);
			
			stmt.setString(1, "acquitt�e");
			stmt.setString(2, "remb mut ok");
			stmt.setString(3, "remb secu ok");
			stmt.setString(4, "envoy�e");
			
			System.out.println(stmt);
			
			result = stmt.executeQuery();
			
			while(result.next()) {
				String[] data = new String[7];
				
				data[0] = result.getString("nom_mutuelle");
				data[1] = result.getString("total_dossier");
				data[2] = Fonction.arrondir(result.getString("panier_moyen"), 2);
				data[3] = result.getString("total_encaissee");
				data[4] = result.getString("total_a_encaisser");
				data[5] = Fonction.arrondir(result.getString("total"),0);
				double taux = Double.parseDouble(result.getString("taux"))*100.00;
				data[6] = Fonction.arrondir(String.valueOf(taux), 2);
				
				reporting.put(result.getString("nom_mutuelle"), data);
			}
			
		}
		catch(SQLException e) {
			System.out.println("Erreur " + e);
			//throw new  DaoException("La requête n'a pas été effectuée");
		}
		return reporting;
	}

	@Override
	public void exportData(String dateDeb, String dateFin) throws DaoException {
		// TODO Auto-generated method stub

	}

}
