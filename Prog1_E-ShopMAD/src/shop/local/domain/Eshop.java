package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import shop.local.domain.exceptions.*;
import shop.local.domain.*;
import shop.local.valueobjects.*;

//E-Shop ist eig nur eine Schnittstelle zwischen der Ansicht und Logik!!!!!

/**
 * Klasse zur Verwaltung unseres E-Shops.
 * Bietet Methoden zum Zurückgeben aller Bücher im Bestand,
 * zur Suche nach Artiekln, zum Einfügen neuer Bücher
 * und zum Speichern des Bestands.
 */

public class Eshop {
	
	private String datei = "";
	
	private ArtikelVerwaltung meineArtikel;
//	private Warenkorb warenkorb;
	private UserVerwaltung meineNutzer;
	
	
	public Eshop(String datei) throws IOException {
		this.datei = datei;
		
		// Artikelbestand aus Datei einlesen
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(datei + "_B.txt");
		
		meineNutzer = new UserVerwaltung();
		meineNutzer.liesKunden("SHOP_Kunde.txt");
		
		meineNutzer = new UserVerwaltung();
		//meineNutzer.liesMitarbeiter("SHOP_Mitarbeiter.txt");
		
	}
	
	
	public Kunde kundenlogIn(String username, String passwort) {
		return meineNutzer.kundenlogIn(username, passwort);
	}
	
	public Kunde kundenRegistrieren(Kunde einKunde) throws KundeExistiertBereitsException {
		return meineNutzer.registrieren(einKunde);
	}
	
	/*public Mitarbeiter mitarbeiterlogIn(String username, String passwort) {
		return meineNutzer.mitarbeiterlogIn(username, passwort);
	}*/
	

	public void artikelsortiertAusgebenBezeichnung() {
		meineArtikel.artikelSortBezeichnung(meineArtikel.getArtikelBestand());
	}
	
	public void artikelsortiertAusgebenNummer() {
		meineArtikel.artikelSortNummer(meineArtikel.getArtikelBestand());
	}

	public String wkAusgeben() {
				//warenkorb des EINGELOGTEN KUNDEN ausgeben
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
	
	public List<Artikel> gibAlleArtikel() {// einfach delegieren an ArtikelVerwaltung meineArtikel
		return meineArtikel.getArtikelBestand();
	}
	
	public List<Artikel> sucheNachTitel(String titel) {// einfach delegieren an ArtieklVerwaltung meineArtikel
		return meineArtikel.sucheArtikel(titel); 
	}
	//WARENKORBVERWALTUNG ?????
	//Methode um Artikel zum Warenkorb hinzuzufügen
		public String artikelZumWarenkorb(int artNummer, int artAnzahl) {
			Vector <Artikel> artListe = meineArtikel.getArtikelBestand();
//			Kunde dieserKunde = (Kunde) BenutzerVerwaltung.getAngemeldeterBenutzer();
			String bestaetigung = "Es ist ein Fehler aufgetreten, versuchen Sie es noch mal.";
			// Die Artikelliste wird nacch den gewuenschten Artikel durchsucht.
			for(int i = 0 ; artListe.size() > i ; i++) {
				if(artListe.elementAt(i).getNummer() == artNummer) {
					Artikel gefundenArt = artListe.elementAt(i);
					//Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
					
					if((gefundenArt.getBestand()>= artAnzahl) == true) {
//						erhoehenOderhinzufuegen(gefundenArt, dieserKunde, artAnzahl);
						bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getTitel() + " in der Stueckzahl " + artAnzahl + " befuellt.\n";
					} else {
						bestaetigung = "Leider haben wir nicht genuegend Artikel auf Lager, der Bestand des Artikels "+ gefundenArt.getTitel() + " betraegt: " + gefundenArt.getBestand() + ". Bitte wiederholen Sie die Eingabe.";
					}
				}
			}
			return bestaetigung;
		}

		//Schnittstelle
		public void speicherKunden() throws IOException {
			// TODO Auto-generated method stub
			meineNutzer.speicherKunden();
		}	
	
}