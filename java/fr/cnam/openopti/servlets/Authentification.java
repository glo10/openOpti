package fr.cnam.openopti.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cnam.openopti.beans.Opticien;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OpticienDao;

/**
 * Servlet implementation class Authentification
 */
public class Authentification extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -70291605123062620L;
	OpticienDao opticienDao = null;
	
	

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentification() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.opticienDao = daoFactory.getOpticienDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("login") != null) {
			Opticien opticien = opticienDao.getOpticienByLogin(request.getParameter("login"), request.getParameter("mdp"));
			if(opticien == null) {
				request.getRequestDispatcher("/WEB-INF/authenfication.jsp").forward(request, response);
			}else {
				System.out.println("Avant session :"+opticien.toString());
				request.getSession().setAttribute("opticien", opticien);
				
				//request.getRequestDispatcher("Accueil").forward(request, response);
				response.sendRedirect("Accueil");
			}
		}else {
		request.getRequestDispatcher("/WEB-INF/authenfication.jsp").forward(request, response);
		}
	}

}
