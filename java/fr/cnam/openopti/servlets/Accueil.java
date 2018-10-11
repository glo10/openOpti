package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.beans.Opticien;
import fr.cnam.openopti.dao.*;
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class Accueil
 */
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactureDao factureDao;
	private Map<String, Facture<String>> listeFacture;
	private Hashtable<String,String> msgUser;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException{
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.factureDao = daoFactory.getFactureDao();
		this.msgUser = new Hashtable<String,String>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int totalAttente = 0;
		try{
			listeFacture = factureDao.listFacture(null,null);
			totalAttente = factureDao.totalFactureAttente();
			session.setAttribute("totalAttente",totalAttente);
			request.setAttribute("listeFacture",listeFacture);
			request.setAttribute("fonction",((Opticien)(session.getAttribute("opticien"))).getFonction());
		
		}
		catch(DaoException e) {
			msgUser.put("alert alert-danger msgUser",e.getMessage());
			session.setAttribute("msgUser", msgUser);
			System.out.println("Erreur  " + e.getMessage());
		}
		
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String filtre1 = (request.getParameter("filtre1") != null)?request.getParameter("filtre1"):null;
		String filtre2 = (request.getParameter("filtre2") != null)?request.getParameter("filtre2"):null;
		String reset = request.getParameter("reset");
		session.setAttribute("filtre1", filtre1);
		session.setAttribute("filtre2", filtre2);
		
		if(reset != null) {
			session.removeAttribute("filtre1");
			session.removeAttribute("filtre2");
		}
		
		try {
			if(session.getAttribute("filtre2") == "")
				listeFacture = factureDao.listFacture((String) session.getAttribute("filtre1"),null);
			else
				listeFacture = factureDao.listFacture((String) session.getAttribute("filtre1"),(String) session.getAttribute("filtre2"));
			request.setAttribute("listeFacture", listeFacture);
			
		} catch (DaoException e) {
			msgUser.put("alert alert-danger msgUser",e.getMessage());
			session.setAttribute("msgUser", msgUser);
			System.out.println("Erreur  " + e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

}
