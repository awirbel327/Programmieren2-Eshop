package shop.local.valueobjects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import shop.local.valueobjects.Artikel;

/**
 * Klasse zur Repräsentation einzelner Bücher.
 * @author teschke */

public class Artikel implements Comparable<Artikel> {


	// Attribute zur Beschreibung eines Artikels:
	private String bezeichnung;
	private int artikelnummer;
	private boolean verfuegbar; 
	private int bestand;		//String damit Warnung weg geht ?
	double artikelPreis;
	double gruppenpreis;

	
	public Artikel(String titel, int nr, boolean verfuegbar, int bestand, double artikelPreis) {
		artikelnummer = nr;
		this.bezeichnung = titel;
		this.verfuegbar = verfuegbar; //können wir das nicht beim erstellen direkt auf true setzen?
		this.bestand = bestand;
		this.artikelPreis = artikelPreis;
	}
	
	// wird benötigt um die Artikel aus der Liste auszugeben
	public String toString() {
		String verfuegbarkeit = verfuegbar ? "Noch " + bestand + " Stueck auf Lager" : "Ausverkauft";
		return (" Artikelnummer: " + artikelnummer + " \n Artikel: " + bezeichnung + " \n Artikelpreis: " + artikelPreis + " \n Bestand: " + verfuegbarkeit +"\n"); 
	}
		
	public int getNummer() {
		return artikelnummer;
	}
	//Setter fuer Artikelnummer	
		public void setNummer(int artikelZaehler) {
			artikelnummer = artikelZaehler;

		}

	public String getTitel() {
		return bezeichnung;
	}
	
	public int getBestand() {
		return bestand;
	}
	public void setBestand(int plusBestand) {
		bestand = plusBestand;
//		setGruppenpreisBerechnen();
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

}