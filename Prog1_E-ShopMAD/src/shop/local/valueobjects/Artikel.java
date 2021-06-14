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
	protected String bezeichnung;
	protected int artikelnummer;
	protected boolean verfuegbar; 
	protected int bestand;		//String damit Warnung weg geht ?
	protected double artikelPreis;
	protected double gruppenpreis;

	
	public Artikel(String titel, int nr, boolean verfuegbar, int bestand, double artikelPreis) {
		artikelnummer = nr;
		this.bezeichnung = titel;
		this.verfuegbar = verfuegbar; 
		this.bestand = bestand;
		this.artikelPreis = artikelPreis;
	}
	
	
	//Wo wird dieser Konstruktor verwendet? Wenn der Artikel in den Warenkorb gelegt wird?
	public Artikel(String titel, int bestand2, double artikelPreis2) {
		this.bezeichnung = titel;
		this.verfuegbar = true; 
		this.bestand = bestand2;
		this.artikelPreis = artikelPreis2;
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