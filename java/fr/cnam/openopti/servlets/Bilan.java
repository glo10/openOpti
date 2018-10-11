package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.cnam.openopti.dao.BilanDao;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.fonctions.FormPattern;
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class Bilan
 */
public class Bilan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BilanDao bilanDao;
    private LinkedHashMap<String,String[]> reporting;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bilan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.bilanDao = daoFactory.getBilanDao();
		this.reporting = new LinkedHashMap<String,String[]>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patternDate = FormPattern.patternDate;
		request.setAttribute("patternDate",patternDate);
		try {
			reporting = bilanDao.getBilan("nom_mutuelle ASC");
			request.setAttribute("reporting", reporting);
			
		} catch (DaoException e) {
			System.out.println(e);
		}
		
		request.getRequestDispatcher("/WEB-INF/bilan_financier.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String filtre = request.getParameter("filtre");
		
		try {
			reporting = bilanDao.getBilan(filtre);
			request.setAttribute("reporting", reporting);
			session.setAttribute("filtre", filtre);
			
		} catch (DaoException e) {
			System.out.println(e);
		}
		
		request.getRequestDispatcher("/WEB-INF/bilan_financier.jsp").forward(request, response);
	}

}
