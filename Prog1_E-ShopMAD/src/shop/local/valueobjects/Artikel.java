package shop.local.valueobjects;

import shop.local.valueobjects.Artikel;

/**
 * Klasse zur Repräsentation einzelner Bücher.
 * 
 * @author teschke
 */
public class Artikel {

	// Attribute zur Beschreibung eines Buchs:
	private String bezeichnung;
	private int artikelnummer;
	private boolean verfuegbar; 
	private int bestand;		//String damit Warnung weg geht ?
	


	public Artikel(String titel, int nr, boolean verfuegbar) {
		artikelnummer = nr;
		this.bezeichnung = titel;
		this.verfuegbar = verfuegbar;
		this.bestand = bestand;
	}
	
	// wird benötigt um die Artikel aus der Liste auszugeben
	public String toString() {
		String verfuegbarkeit = verfuegbar ? "Noch __ Stück auf Lager" : "Ausverkauft";
		return (" Artikelnummer: " + artikelnummer + " \n Artikel: " + bezeichnung + " \n Bestand: " + verfuegbarkeit +"\n");
	}

	
	public int getNummer() {
		return artikelnummer;
	}

	public String getTitel() {
		return bezeichnung;
	}
	
	public int getBestand() {
		return bestand;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}
}