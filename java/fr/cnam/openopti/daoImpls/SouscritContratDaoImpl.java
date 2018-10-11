package fr.cnam.openopti.daoImpls;

import fr.cnam.openopti.beans.SouscritContrat;
import fr.cnam.openopti.dao.DaoFactory;
import fr.cnam.openopti.dao.SouscritContratDao;
import fr.cnam.openopti.mesException.DaoException;

public class SouscritContratDaoImpl implements SouscritContratDao {
	private DaoFactory daoFactory;

	public SouscritContratDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouterSouscrit(SouscritContrat souscrit) throws DaoException {
		// TODO Auto-generated method stub
		
	}

}
