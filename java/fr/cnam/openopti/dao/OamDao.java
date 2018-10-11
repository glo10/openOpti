package fr.cnam.openopti.dao;

import java.util.List;
import java.util.Map;

import fr.cnam.openopti.beans.Oam;
import fr.cnam.openopti.mesException.DaoException;

public interface OamDao<T> {
	Map<Integer,T> getList(List<String> attrs,String table) throws DaoException;
	void ajouter(T t,String table) throws DaoException;
	void supprimer(T t,String table) throws DaoException;
	public List<Oam> getAll(String table) throws DaoException;
	public boolean modifier(Oam oam, String table);
	public Oam getById(int id, String table);
	String getOamName(int id_oam,String table) throws DaoException;

}
