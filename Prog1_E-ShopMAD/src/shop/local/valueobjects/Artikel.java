package shop.local.valueobjects;

import shop.local.valueobjects.Artikel;

/**
 * Klasse f�r Artikel */

public class Artikel implements Comparable<Artikel> {

	// Attribute zur Beschreibung eines Artikels:
	 String bezeichnung;
	 int artikelnummer;
	 boolean verfuegbar; 
	 int bestand;	
	 double artikelPreis;
	 double gruppenpreis;

	 
	
	public Artikel(String bezeichnung, int bestand, double artikelPreis) {
		this.bezeichnung = bezeichnung; 
		this.bestand = bestand;
		this.artikelPreis = artikelPreis;
		setArtikelpreisBerechnen();
	}

	// wird benötigt um die Artikel aus der Liste auszugeben
	public String toString() {
		String verfuegbarkeit =  "Noch " + bestand + " Stueck auf Lager";
		return (" Artikelnummer: " + artikelnummer + " \n Artikel: " + bezeichnung + " \n Artikelpreis: " + artikelPreis + " Euro "+ " \n Bestand: " + verfuegbarkeit +"\n"); 
	}
		
	public int getNummer() {
		return artikelnummer;
	}
	
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
	public void setArtikelpreisBerechnen() {
		gruppenpreis = artikelPreis * bestand;
	}

	public double getArtikelpreisBerechnen() {
		return gruppenpreis;
	}
	
	public double getPreis() {
		return artikelPreis;
	}

	
	public int compareTo(Artikel o) {
		// zu String und vergelciht mit Artikel "o"
		return toString().compareTo(o.toString());		
	}
}