package fr.cnam.openopti.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.cnam.openopti.mesException.DaoException;

public interface BilanDao {
	LinkedHashMap<String,String[]> getBilan(String filtre) throws DaoException;
	void exportData(String dateDeb, String dateFin) throws DaoException;
}
