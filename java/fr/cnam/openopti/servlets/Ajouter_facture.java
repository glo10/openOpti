package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.cnam.openopti.beans.Client;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.beans.Mutuelle;
import fr.cnam.openopti.dao.ClientDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.FactureDao;
import fr.cnam.openopti.dao.OamDao;
import fr.cnam.openopti.fonctions.Fonction;
import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.BeansException;
import fr.cnam.openopti.mesException.DaoException;
import fr.cnam.openopti.beans.Oam;
import fr.cnam.openopti.beans.Secu;
import fr.cnam.openopti.beans.SouscritContrat;

/**
 * Servlet implementation class Ajouter_facture
 */
public class Ajouter_facture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactureDao factureDao;
	private OamDao secuDao;
	private OamDao mutuelleDao;
	private ClientDao clientDao;
	private Hashtable<String,String> msgUser;
	private Map<String,Object> values;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajouter_facture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.factureDao = daoFactory.getFactureDao();
		this.secuDao = daoFactory.getOamDao();
		this.mutuelleDao = daoFactory.getOamDao();
		this.clientDao = daoFactory.getClientDao();
		this.msgUser = new Hashtable<String,String>();
		this.values = new HashMap<String,Object>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//PATTERN POUR ATTRIBUT pattern HTML
		request.setAttribute("date", FormPattern.patternDate);
		request.setAttribute("numero", FormPattern.patternNumero);
		request.setAttribute("identite", FormPattern.patternIdentite);
		request.setAttribute("adresse", FormPattern.patternAdresse);
		request.setAttribute("cp_client", FormPattern.patternCpClient);
		request.setAttribute("prix", FormPattern.patternPrix);
		request.setAttribute("secu", FormPattern.patternSecu);
		request.setAttribute("lot",FormPattern.patternLot);
		request.setAttribute("email", FormPattern.patternEmail);
		request.setAttribute("tel", FormPattern.patternTel);
		
		Map<Integer,Secu> listSecu = new HashMap<Integer, Secu>();
		List<String> attrsSecu = new ArrayList<String>();
		attrsSecu.add("id_secu");
		attrsSecu.add("nom_secu");
		
		Map<Integer,Mutuelle> listMut = new HashMap<Integer, Mutuelle>();
		List<String> attrsMut = new ArrayList<String>();
		attrsMut.add("id_mutuelle");
		attrsMut.add("nom_mutuelle");
		
		try {
			listSecu = secuDao.getList(attrsSecu,"secu");
			request.setAttribute("listSecu", listSecu);
			listMut = mutuelleDao.getList(attrsMut, "mutuelle");
			request.setAttribute("listMut", listMut);
			session.setAttribute("values", values);
			request.getRequestDispatcher("/WEB-INF/ajouter_facture.jsp").forward(request, response);
		}
		catch(DaoException e) {
			msgUser.put("alert alert-danger msgUser", e.getMessage());
			session.setAttribute("msgUser",msgUser);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Client client = new Client();
		Facture newFacture = new Facture();
		SouscritContrat souscrit = new SouscritContrat();
		int id_client = 0;
		
		String secu = (request.getParameter("secu") != null)? request.getParameter("secu"):"";
		String num_lot = (request.getParameter("num_lot") != null)? request.getParameter("num_lot"):"";
		//---DATA POUR LES TABLES SOUSCRIT ET CONTRAT---------------//
		int mutuelle = (request.getParameter("mutuelle") != null)? Integer.parseInt(request.getParameter("mutuelle")):null;
		String num_contrat = (request.getParameter("num_contrat").length() > 0)?request.getParameter("num_contrat"):"";
		String num_adherent = (request.getParameter("num_adherent").length() > 0)?request.getParameter("num_adherent"):"";
		String date_deb = (request.getParameter("date_deb").length() > 0)?request.getParameter("date_deb"):"";
		String date_fin = (request.getParameter("date_fin").length() > 0)?request.getParameter("date_fin"):"";
		
		//-------DATA POUR LA TABLE CLIENT----//
		String nom_client = (request.getParameter("nom_client").length() > 0)?request.getParameter("nom_client"):"";
		String prenom_client = (request.getParameter("prenom_client").length() > 0)?request.getParameter("prenom_client"):"";
		String date_naissance = (request.getParameter("date_naissance").length() > 0)?request.getParameter("date_naissance"):"";
		String num_secu = (request.getParameter("num_secu").length() > 0)?request.getParameter("num_secu"):"";
		String adresse = (request.getParameter("adresse").length() > 0)?request.getParameter("adresse"):"";
		String ville = (request.getParameter("ville").length() > 0)?request.getParameter("ville"):"";
		String cp_client = (request.getParameter("cp_client").length() > 0)?request.getParameter("cp_client"):"";
		String email = (request.getParameter("email").length() > 0)?request.getParameter("email"):"";
		String tel = (request.getParameter("tel").length() > 0)?request.getParameter("tel"):"";
		
		//--DATA POUR LA TABLE FACTURE---/
		String num_facture = (request.getParameter("num_facture").length() > 0)?request.getParameter("num_facture"):"";
		String date_facture = (request.getParameter("date_facture").length() > 0)?request.getParameter("date_facture"):"";
		String date_envoi = (request.getParameter("date_envoi").length() > 0)?request.getParameter("date_envoi"):"";
		String libelle_equipement = (request.getParameter("libelle_equipement").length() > 0)?request.getParameter("libelle_equipement"):"";
		Float montant_total = (float) ((request.getParameter("montant_total") != null)? Float.parseFloat(request.getParameter("montant_total")):0.00);
		Float montant_remb_mut = (float) ((request.getParameter("montant_remb_mut") != null)? Float.parseFloat(request.getParameter("montant_remb_mut")):0.00);
		Float montant_remb_secu = (float) ((request.getParameter("montant_remb_secu") != null)? Float.parseFloat(request.getParameter("montant_remb_secu")):0.00);
		
		try {
			//---RECUPERATION id_client OU CREATION NOUVEAU CLIENT---//
			client.setNom_client(nom_client);
			client.setPrenom_client(prenom_client);
			client.setDate_naissance(date_naissance);
			client.setNum_secu(num_secu);
			client.setAdresse(adresse);
			client.setVille(ville);
			client.setCp_client(cp_client);
			client.setEmail_client(email);
			client.setTel_client(tel);
			
			//-----INSERTION DANS LA TABLE SOUSCRITCONTRAT----//
			souscrit.setClient(client.getId_client());
			souscrit.setMutuelle(mutuelle);
			souscrit.setDate_deb(date_deb);
			souscrit.setDate_fin(date_fin);
			souscrit.setNumero_contrat(num_contrat);
			souscrit.setNum_adherent(num_adherent);
			
			//----CREATION NOUVELLE FACTURE---//
			newFacture.setNum_facture(num_facture);
			newFacture.setDate_facture(date_facture);
			newFacture.setDate_envoi(date_envoi);
			newFacture.setLibelle_equipement(libelle_equipement);
			newFacture.setMontant_total(montant_total);
			newFacture.setMontant_remb_mut(montant_remb_mut);
			newFacture.setMutuelle(mutuelle);
			newFacture.setMontant_remb_secu(montant_remb_secu);
			newFacture.setNum_lot(num_lot);
			newFacture.setSecu(secu);
			
			factureDao.ajouterFacture(client, souscrit, newFacture);
			msgUser.clear();
			values.clear();
			msgUser.put("alert alert-success msgUser", "La facture " + newFacture.getNum_facture() + " a été ajoutée avec succès");
			session.setAttribute("msgUser",msgUser);
			response.sendRedirect("Accueil");
		}
		catch(DaoException | BeansException e) {
			msgUser.clear();
			msgUser.put("alert alert-danger msgUser",e.getMessage());
			session.setAttribute("msgUser",msgUser);
			
			values.put("montant_remb_secu",montant_remb_secu);
			values.put("num_lot",num_lot);
			values.put("secu",secu);
			
			values.put("mutuelle",mutuelle);
			
			values.put("date_deb",date_deb);
			values.put("date_fin",date_fin);
			values.put("numero_contrat",num_contrat);
			values.put("num_adherent",num_adherent);
			
			values.put("nom_client",nom_client);
			values.put("prenom_client",prenom_client);
			values.put("date_naissance",date_naissance);
			values.put("num_secu",num_secu);
			values.put("adresse",adresse);
			values.put("ville",ville);
			values.put("cp_client",cp_client);
			values.put("email_client",email);
			values.put("tel_client",tel);
			
			values.put("num_facture",num_facture);
			values.put("date_facture",date_facture);
			values.put("date_envoi",date_envoi);
			values.put("libelle_equipement",libelle_equipement);
			values.put("montant_total",montant_total);
			values.put("montant_remb_mut",montant_remb_mut);
			session.setAttribute("values",values);
			
			response.sendRedirect("Ajouter_facture");
		}
	}

}
