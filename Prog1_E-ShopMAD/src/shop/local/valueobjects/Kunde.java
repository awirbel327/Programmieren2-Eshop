package shop.local.valueobjects;

public class Kunde {

private int kundenNr;

private String name;
private String strasse = " ";
private String hausNr = "";
private String plz = " ";
private String ort = " ";
private String kUsername;	//getter und setter ? getter einfach gemacht
private String kPasswort;	//getter und setter ? getter einfach gemacht
private Warenkorb warenkorb;

public Kunde(int nr, String name) {
	kundenNr = nr;
	this.name = name;
	warenkorb = new Warenkorb();
	//mitarbeiter = false;
	}

// getter und setter usw Kunden-Eigenschaften
public int getKundenNr() {
	return kundenNr;
}

public String getName() {
	return name;
	}

public void setName(String name) {
	this.name = name;
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

public String getKUsername() {
	return kUsername;
	}

public String getKPasswort() {
	return kPasswort;
	}


public Warenkorb getWk() {
	return warenkorb;
	}

}
