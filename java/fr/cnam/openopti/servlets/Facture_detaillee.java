package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cnam.openopti.beans.Commentaire;
import fr.cnam.openopti.beans.Facture;
import fr.cnam.openopti.dao.CommentaireDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.FactureDao;
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class Facture_detaillee
 */
public class Facture_detaillee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactureDao factureDao;
	private CommentaireDao commentaireDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Facture_detaillee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.factureDao = daoFactory.getFactureDao();
		this.commentaireDao = daoFactory.getCommentaireDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> facture;
		Map<Integer,Commentaire> coms;
		String num_facture = request.getParameter("num_facture");
		System.out.println(num_facture);
		try {
			facture = factureDao.factureDetaillee(num_facture);
			coms = commentaireDao.getAllCommentaire(num_facture);
			
			request.setAttribute("factureDetaillee", facture);
			request.setAttribute("num_facture", num_facture);
			request.setAttribute("coms", coms);
		} catch (DaoException e) {
			System.out.println("erreur " + e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/facture_detaillee.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
