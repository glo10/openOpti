package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.FactureDao;
import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.BeansException;
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class Acquitter
 */
public class Acquitter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactureDao factureDao;
	private Hashtable<String,String> msgUser;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Acquitter() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	DaoFactory daoFactory = DaoFactory.getInstance();
    	this.factureDao = daoFactory.getFactureDao();
    	this.msgUser = new Hashtable<String,String>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num_facture = request.getParameter("num_facture");
		String url ="acquitter";
		request.setAttribute("url", url);
		request.setAttribute("date", FormPattern.patternDate);
		Map<String,String> facture;
		
		try {
			facture = factureDao.factureDetaillee(num_facture);
			String etatKey = "État";
			String equipementKey = "Équipement";
			if(facture.containsKey(etatKey)) {
				String etatValue = facture.get(etatKey);
				if(etatValue.equals("acquittée")){
					request.setAttribute("msg", "La facture numéro " + request.getParameter("num_facture") + " a déjà été acquittée !");
					request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
				}
				request.setAttribute("etat", etatValue);
			}
			
			if(facture.containsKey(equipementKey)) {
				String equipementValue = facture.get(equipementKey);
				request.setAttribute("equipement", equipementValue);
			}
			
			request.setAttribute("factureDetaillee", facture);
		} catch (DaoException e) {
			
			System.out.println("erreur " + e.getMessage());
		}
		
		request.getRequestDispatcher("/WEB-INF/acquitter.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String num_facture = request.getParameter("num_facture");
		String typeRemboursement = request.getParameter("typeRemboursement");
		String titre = request.getParameter("titre");
		String com = request.getParameter("textCom");
		
		String date_paiement_secu = null;
		String date_paiement_mutuelle = null;
		Facture<String> myFacture = new Facture<String>();
		Commentaire myCom = new Commentaire();
		myFacture.setNum_facture(num_facture);
		
		try {
			
			if(request.getParameter("date_paiement_secu") != null) {
				date_paiement_secu = request.getParameter("date_paiement_secu");
				myFacture.setDate_paiement_secu(date_paiement_secu);
			}
			
			if(request.getParameter("date_paiement_mutuelle") != null) {
				date_paiement_mutuelle = request.getParameter("date_paiement_mutuelle");
				myFacture.setDate_paiement_mut(date_paiement_mutuelle);
			}
			
			myCom.setTitre(titre);
			myCom.setCom(com);
			myCom.setFacture(num_facture);
			//@todo à modifier une fois que l'espace membre sera développé
			myCom.setOpticien("glodie.tshimini@gmail.com");
			
			factureDao.acquitterFacture(myFacture, typeRemboursement, myCom);
			msgUser.put("alert alert-success", "le reglèment " + typeRemboursement + " a bien été ajouté pour la facture " + myFacture.getNum_facture());
			session.setAttribute("msgUser", msgUser);
			response.sendRedirect("Accueil");
		} catch (DaoException | BeansException e) {
			msgUser.put("alert alert-danger msgUser", e.getMessage());
			session.setAttribute("msgUser", msgUser);
		}
	}

}
