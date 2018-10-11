package fr.cnam.openopti.fonctions;

public class FormPattern {
	public static final String patternDateI = "(0[1-9]|1[0-9]|2[0-9]|3[01])\\/(0[1-9]|1[012])\\/(19|20)[0-9]{2}";
	public static final String patternDate = "(19|20)[0-9]{2}[-](0[1-9]|1[012])[-](0[1-9]|1[0-9]|2[0-9]|3[01])";
	public static final String patternNumero ="[a-zA-Z0-9]+";
	public static final String patternIdentite = "[^$|%|<|>|?|\"|#|@]{2,80}";
	public static final String patternNomPrenom = "[^$|%|<|>|?|\"|#|@]{2,163}";
	public static final String patternAdresse ="[^$|%|<|>|?|\"|#|@]{2,80}";
	public static final String patternCpClient ="[1-9][0-9]{4}";
	public static final String patternPrix ="[0-9]+([.|,][0-9]{2})?";
	public static final String patternSecu ="[1|2]([0-9]){12}([0-9]{2})?" ;
	public static final String patternLot="[1-9]+[0-9]*";
	public static final String patternEmail="[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\\.[a-zA-Z]{2,}";
	public static final String patternTel = "[0][1-9][0-9]{8}";
	public static final String patternTitre = "[^$|%|<|>|?|\"|#|@]{2,80}";
	public static final String patternCom = "[^$|<|>|\"|#]{2,1500}";
	public static final String patternDateTime = "(0[1-9]|1[0-9]|2[0-9]|3[01])\\/(0[1-9]|1[012])\\/(19|20)[0-9]{2}[\\s][à][\\s]([\\d]{2}[:]){2}[0-9]{2}";
	
	public FormPattern() {
		super();
	}

}
