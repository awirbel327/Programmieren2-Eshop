package shop.local.valueobjects;

public class Kunde extends User {

	private int kundenNr;
	private String strasse = " ";
	private int hausNr;
	private int plz;
	private String ort = " ";
	private Warenkorb warenkorb;

	//konstruktor f�r anmeldung usw.
	public Kunde(String name, String strasse, int hausNr, int plz, String ort, String username, String passwort) {
		super(name, username, passwort);
		this.strasse = strasse;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		warenkorb = new Warenkorb();
	}

	// getter und setter usw Kunden-Eigenschaften
	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getHausNr() {
		return hausNr;
	}

	public void setHausNr(int hausNr) {
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
