package fr.cnam.openopti.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cnam.openopti.beans.Opticien;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.OpticienDao;

/**
 * Servlet implementation class Liste_Opticien
 */
public class Liste_Opticien extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OpticienDao opticienDao = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Liste_Opticien() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.opticienDao = daoFactory.getOpticienDao();
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Opticien opt = (Opticien) request.getSession().getAttribute("opticien");
		System.out.println(opt.getFonction()+ " ici ");
		List<Opticien> opticiens = new ArrayList<Opticien>();
		if (opt.getFonction().equals("directeur")) {
			opticiens = opticienDao.getAll();
		} else if (opt.getFonction().equals("opticien")) {
			opticiens.add(opt);
		}

		request.setAttribute("opticiens", opticiens);
		request.getRequestDispatcher("/WEB-INF/liste_opticien.jsp").forward(request, response);
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
