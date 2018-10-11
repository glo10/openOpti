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
import fr.cnam.openopti.dao.CommentaireDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.FactureDao;
import fr.cnam.openopti.mesException.BeansException;
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class Commenter
 */
public class Commenter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentaireDao commentaireDao;
	private FactureDao factureDao;
	private Hashtable<String, String> msgUser;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Commenter() { 
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException{
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.commentaireDao = daoFactory.getCommentaireDao();
		this.factureDao = daoFactory.getFactureDao();
		this.msgUser = new Hashtable<String,String>();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> factureDetaillee;
		Map<Integer, Commentaire> coms;
		String num_facture = request.getParameter("num_facture");
		try {
			factureDetaillee = factureDao.factureDetaillee(num_facture);
			request.setAttribute("factureDetaillee", factureDetaillee);
			coms = commentaireDao.getAllCommentaire(num_facture);
			request.setAttribute("coms",coms);
		} catch (DaoException e) {
			System.out.println("Erreur " + e.getMessage());
		}
		
		request.getRequestDispatcher("/WEB-INF/commenter.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String titre = request.getParameter("titre");
		String com = request.getParameter("textCom");
		String facture = request.getParameter("num_facture");
		//@todo remplacer par l'email de l'opticien en session
		String opticien = "glodie.tshimini@gmail.com";
		
		Commentaire newCom = new Commentaire();
		
		try {
			newCom.setTitre(titre);
			newCom.setCom(com);
			newCom.setFacture(facture);
			newCom.setOpticien(opticien);
			
			commentaireDao.ajouterCom(newCom);
			System.out.println(newCom.toString());
			msgUser.put("alert alert-success msgUser","Un commentaire a été ajouté pour la facture " + newCom.getFacture());
			session.setAttribute("msgUser", msgUser);
		} catch (DaoException | BeansException e) {
			System.out.println("erreur " + e.getMessage());
			msgUser.put("alert alert-danger msgUser",e.getMessage());
			session.setAttribute("msgUser", msgUser);
		}
		
		response.sendRedirect("Accueil");
	}

}
