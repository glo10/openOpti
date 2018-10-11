package fr.cnam.openopti.dao;

import java.util.List;

import fr.cnam.openopti.beans.Opticien;

public interface OpticienDao {
	
	public Opticien getOpticienByLogin(String login, String motDePasse);
	
	public Opticien getOpticienById(String login);
	
	public Boolean insertOpticien(Opticien opticien);
	
	public Boolean deletOpticien(String login);
	
	public Boolean updateOpticien(Opticien opticien);
	
	public List<Opticien> getAll();
	
}
