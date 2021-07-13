package shop.local.valueobjects;

import java.util.Vector;

public class Kunde extends User {

	private int kundenNr;
	private String strasse = " ";
	private String hausNr;
	private String plz;
	private String ort = " ";
	private Warenkorb warenkorb;

	
	//konstruktor f�r anmeldung usw.
	public Kunde(String name, String strasse, String hausNr, String plz, String ort, String username, String passwort) {
		super(name, username, passwort);
		this.strasse = strasse;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		warenkorb = new Warenkorb();
	}
	
	//Kostruktor f�r Laden aus der Persistenz
	public Kunde(String name, String strasse, String hausNr, String plz, String ort, String username, String passwort, int kundenNr) {
		super(name, username, passwort);
		this.strasse = strasse;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		this.kundenNr = kundenNr;
		warenkorb = new Warenkorb();
	}

	// getter und setter usw Kunden-Eigenschaften
	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausNr() {
		return hausNr;
	}

	public void setHausNr(String hausNr) {
		this.hausNr = hausNr;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public Warenkorb getWk() {
		return warenkorb;
	}
	
	

}
