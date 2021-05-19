package shop.local.valueobjects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import shop.local.valueobjects.Artikel;

/**
 * Klasse zur ReprÃ¤sentation einzelner BÃ¼cher.
 * 
 * @author teschke
 */

public class Artikel implements Comparable<Artikel> {


	// Attribute zur Beschreibung eines Buchs:
	private String bezeichnung;
	private int artikelnummer;
	private boolean verfuegbar; 
	private int bestand;		//String damit Warnung weg geht ?
	


	public Artikel(String titel, int nr, boolean verfuegbar, int bestand) {
		artikelnummer = nr;
		this.bezeichnung = titel;
		this.verfuegbar = verfuegbar; //können wir das nicht beim erstellen direkt auf true setzen?
		this.bestand = bestand;
	}
	
	// wird benÃ¶tigt um die Artikel aus der Liste auszugeben
	public String toString() {
		String verfuegbarkeit = verfuegbar ? "Noch " + bestand + " Stück auf Lager" : "Ausverkauft";
		return (" Artikelnummer: " + artikelnummer + " \n Artikel: " + bezeichnung + " \n Bestand: " + verfuegbarkeit +"\n"); 
	}
	
	/**
	 * Methode um Artikel nach Nummer zu sortieren
	 * Nach Name muss ich nochmal gucken!!
	 * 
	 */
	/*public int compareTo (Artikel andererArtikel) {
		return this.getNummer() - andererArtikel.getNummer(); 
	}
	*/
	

	
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
	

	@Override	//https://stackoverflow.com/questions/18895915/how-to-sort-an-array-of-objects-in-java
	public int compareTo(Artikel o) {
		return toString().compareTo(o.toString());		// zu String und vergelciht mit Artikel "o"
	}

}