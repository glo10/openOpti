package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cnam.openopti.beans.Opticien;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OpticienDao;

/**
 * Servlet implementation class GestionOpticien
 */
public class GestionOpticien extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5844041101333302912L;
	OpticienDao opticienDao = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionOpticien() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.opticienDao = daoFactory.getOpticienDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher req = null;
		String mode = request.getParameter("mode");
		request.setAttribute("mode", mode);

		switch (mode) {
		case "del":
			String qui = request.getParameter("qui");

			opticienDao.deletOpticien(qui);
			System.out.println(qui + " à été supprimé");
			req = request.getRequestDispatcher("/Opticiens");
			break;
		case "ajout":
			if (null != request.getParameter("status")) {// || request.getParameter("status").equals("ok")) {
				Opticien opt = new Opticien();
				opt.setFonction(request.getParameter("fonction"));
				opt.setLogin(request.getParameter("login"));
				opt.setMotDePasse(request.getParameter("mdp"));
				opt.setNom(request.getParameter("nom"));
				opt.setPrenom(request.getParameter("prenom"));
				opticienDao.insertOpticien(opt);
				req = request.getRequestDispatcher("/Accueil");
			} else {
				req = request.getRequestDispatcher("/WEB-INF/gestion_opticien.jsp");
			}
			break;
		case "modif":
			if (null != request.getParameter("status")) {
				Opticien optSess = (Opticien) request.getSession().getAttribute("opticien");
				Opticien optBdd = opticienDao.getOpticienByLogin(optSess.getLogin(), request.getParameter("a_mdp"));
				
				if (null != optBdd ) {
					optBdd.setMotDePasse(request.getParameter("mdp"));
					opticienDao.updateOpticien(optBdd);
					req = request.getRequestDispatcher("/Accueil");
				}else{
					System.out.println("Je passe ici aussi");
					request.setAttribute("error", "Ancien mot de passe incorrecte");
					request.setAttribute("mode", "modif");
					req = request.getRequestDispatcher("/WEB-INF/gestion_opticien.jsp");
				}	
			} else {
				request.setAttribute("mode", "modif");
				req = request.getRequestDispatcher("/WEB-INF/gestion_opticien.jsp");
			}
			break;

		default:
			break;
		}

		req.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
