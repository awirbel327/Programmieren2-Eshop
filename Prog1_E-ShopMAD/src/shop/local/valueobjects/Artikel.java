package shop.local.valueobjects;

import shop.local.valueobjects.Artikel;

/**
 * Klasse f�r Artikel */

public class Artikel implements Comparable<Artikel> {


	// Attribute zur Beschreibung eines Artikels:
	 String bezeichnung;
	 int artikelnummer;
	 boolean verfuegbar; 
	 int bestand;		//String damit Warnung weg geht ?
	 double artikelPreis;
	 double gruppenpreis;

	
	public Artikel(String bezeichnung, int artikelnummer, int bestand, double artikelPreis) { //int verfügbar
//		artikelnummer = nr;
		this.bezeichnung = bezeichnung;
		this.artikelnummer = artikelnummer;
//		this.verfuegbar = verfuegbar; 
		this.bestand = bestand;
		this.artikelPreis = artikelPreis;
		setArtikelpreisBerechnen();
	}
	
//	int artikelBestand, String artikelName, double artikelPreis, int massengutGroesse
	//Wo wird dieser Konstruktor verwendet? Wenn der Artikel in den Warenkorb gelegt wird?
//	public Artikel(String bezeichnung, int bestand2, double artikelPreis2) {
//		this.bezeichnung = bezeichnung;
//		this.verfuegbar = true; 
//		this.bestand = bestand2;
//		this.artikelPreis = artikelPreis2;
//	}

	// wird benötigt um die Artikel aus der Liste auszugeben
	public String toString() {
		String verfuegbarkeit =  "Noch " + bestand + " Stueck auf Lager";
		return (" Artikelnummer: " + artikelnummer + " \n Artikel: " + bezeichnung + " \n Artikelpreis: " + artikelPreis + " Euro "+ " \n Bestand: " + verfuegbarkeit +"\n"); 
	}
		
	public int getNummer() {
		return artikelnummer;
	}
	
	//Setter fuer Artikelnummer	
	public void setNummer(int artikelZaehler) {
		artikelnummer = artikelZaehler;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public int getBestand() {
		return bestand;
	}
	public void setBestand(int plusBestand) {
		bestand = plusBestand;
		setArtikelpreisBerechnen();
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}
	public double getPreis() {
		return artikelPreis;
	}

	@Override	//https://stackoverflow.com/questions/18895915/how-to-sort-an-array-of-objects-in-java
	public int compareTo(Artikel o) {
		return toString().compareTo(o.toString());		// zu String und vergelciht mit Artikel "o"
	}
	public void setArtikelpreisBerechnen() {
		gruppenpreis = artikelPreis * bestand;
	}

	public double getArtikelpreisBerechnen() {
		return gruppenpreis;
	}

}