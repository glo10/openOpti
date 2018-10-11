package fr.cnam.openopti.servlets;

import java.io.IOException;

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
import fr.cnam.openopti.mesException.DaoException;

/**
 * Servlet implementation class AjouterOAM
 */
public class GestionOAM extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OamDao mutDao;

	// private OamDao<Secu> secuDao;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionOAM() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		mutDao = DaoFactory.getInstance().getOamDao();
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher req = null;
		String mode = request.getParameter("mode");
		String type = "";
		request.setAttribute("mode", mode);
		Oam oam = null;
		System.out.println(mode);
		switch (mode) {
		case "ajout":
			if (null != request.getParameter("status")) {
				
				if (type == "mutuelle") {
					oam = new Mutuelle();
				} else {
					oam = new Secu();
				}
				oam.setNom_t(request.getParameter("nom"));
				oam.setAdresse_t(request.getParameter("adresse"));
				oam.setCp_t(request.getParameter("cp"));
				oam.setEmail_t(request.getParameter("email"));
				oam.setTel_t(request.getParameter("telephone"));
				oam.setFax_t(request.getParameter("fax"));
				oam.setVille_t(request.getParameter("ville"));
				try {
					if (type == "mutuelle") {
						mutDao.ajouter(oam, "mutuelle");
					} else {
						mutDao.ajouter(oam, "secu");
					}
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				req = request.getRequestDispatcher("Accueil");
			} else {
				req = request.getRequestDispatcher("/WEB-INF/gestion_oam.jsp");
			}
			break;
		case "modif":
			type = request.getParameter("type");
			if (null != request.getParameter("status")) {
				if (type=="secu") {
					oam = new Secu();
				}else {
					oam = new Mutuelle();
				}
				oam.setId_t(Integer.parseInt(request.getParameter("id")));
				oam.setNom_t(request.getParameter("nom"));
				oam.setAdresse_t(request.getParameter("adresse"));
				oam.setCp_t(request.getParameter("cp"));
				oam.setEmail_t(request.getParameter("email"));
				oam.setTel_t(request.getParameter("telephone"));
				oam.setFax_t(request.getParameter("fax"));
				oam.setVille_t(request.getParameter("ville"));
				System.out.println("type : "+ type);
				mutDao.modifier(oam, type);
				req = request.getRequestDispatcher("Liste_OAM");
			}else {
				oam =mutDao.getById(Integer.parseInt(request.getParameter("qui")), request.getParameter("oam"));
				request.setAttribute("mode", "modif");
				request.setAttribute("type", type);
				request.setAttribute("_oam", oam);
				req = request.getRequestDispatcher("/WEB-INF/gestion_oam.jsp");
				
			}
			

			break;
		case "del":
			type=request.getParameter("oam");
			Oam temp = new Oam();
			temp.setId_t(Integer.parseInt( request.getParameter("qui")));
			try {
				mutDao.supprimer(temp, type);
			} catch (DaoException e) {
				e.printStackTrace();
			}
			req = request.getRequestDispatcher("Liste_OAM");
			break;
		default:
			req = request.getRequestDispatcher("Liste_OAM");
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
