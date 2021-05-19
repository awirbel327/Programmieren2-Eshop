package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.SortierException;
import shop.local.domain.ArtikelVerwaltung;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;

import shop.local.valueobjects.Warenkorb;


/**
 * Klasse zur Verwaltung unseres E-Shops.
 * Bietet Methoden zum Zurückgeben aller Bücher im Bestand,
 * zur Suche nach Büchern, zum Einfügen neuer Bücher
 * und zum Speichern des Bestands.
 */

public class Eshop {
	private String datei = "";
	
	private ArtikelVerwaltung meineArtikel;
//	private Warenkorb warenkorb;
// 	private UserVerwaltung meineNutzer;
	
	
	public Eshop(String datei) throws IOException {
		this.datei = datei;
		
		// Artikelbestand aus Datei einlesen
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(datei + "_B.txt");
		
	}
	

	public void artikelsortiertAusgeben() {
		meineArtikel.artikelSortBezeichnung(meineArtikel.getArtikelBestand());
	}

	public String wkAusgeben() {
				//warenkorb des EINGELOGTEN KUNDEN ??? ausgeben
		Warenkorb wk = new Warenkorb();
		 return wk.warenkorbAusgeben();
	}
	
	
	public String zumWKhinzufuegen() {
		/*
		 * Nummer die man eingegeben hat mit Artikelliste vergleichen
		 * wenn eingegebene Nummer == Nummer in Artikelliste dann 
		 * bestand prüfen
		 * if artikel verfügbar
		 * WK befüllen else "Artikel nicht vorhanden oder falsche nummer"
		 */
		
		return null;
	}
	
	public List<Artikel> gibAlleArtikel() {
		// einfach delegieren an BuecherVerwaltung meineBuecher
		return meineArtikel.getArtikelBestand();
	}
	
	public List<Artikel> sucheNachTitel(String titel) {
		// einfach delegieren an BuecherVerwaltung meineBuecher
		return meineArtikel.sucheArtikel(titel); 
	}
}