package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cnam.openopti.beans.Mutuelle;
import fr.cnam.openopti.beans.Oam;
import fr.cnam.openopti.beans.Secu;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OamDao;

/**
 * Servlet implementation class Liste_OAM
 */
public class Liste_OAM extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OamDao oamDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Liste_OAM() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init() throws ServletException {
		super.init();
    	oamDao = DaoFactory.getInstance().getOamDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Secu> secus = null;
		List<Mutuelle> mutuelles = null;
		RequestDispatcher req = null;
		
		try {
			secus = oamDao.getAll("secu");
			mutuelles = oamDao.getAll("mutuelle");
			request.setAttribute("secus", secus);
			request.setAttribute("mutuelles", mutuelles);
			request.getRequestDispatcher("/WEB-INF/liste_oam.jsp").forward(request, response);			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
