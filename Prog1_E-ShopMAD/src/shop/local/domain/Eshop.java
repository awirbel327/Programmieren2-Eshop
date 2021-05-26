package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;


import shop.local.domain.exceptions.*;
import shop.local.domain.exceptions.*;
import shop.local.domain.*;
import shop.local.valueobjects.*;
import shop.local.valueobjects.Artikel;
import shop.local.domain.UserVerwaltung;


/**
 * Klasse zur Verwaltung unseres E-Shops.
 * Bietet Methoden zum Ausgeben aller Artikel (soriert),
 * zur Suche nach Artiekln, zum Einfügen neuer Artikel
 * und zum Speichern des Bestands.
 * E-Shop ist eig nur eine Schnittstelle zwischen der Ansicht und Logik!!!!!
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
		
		// Kundendaten aus Datei einlesen
		meineNutzer = new UserVerwaltung();
		meineNutzer.liesKunden("SHOP_Kunde.txt");
		
		// Mitarbeiterdaten aus Datei einlesen
		meineNutzer = new UserVerwaltung(); // kann man lÃ¶schen?
		meineNutzer.liesMitarbeiter("SHOP_Mitarbeiter.txt");
		
	}
	
	// Methodenaufrufe zum einloggen/registrieren und speichern aus der Userverwaltung
	public Kunde kundenlogIn(String username, String passwort) {
		return meineNutzer.kundenlogIn(username, passwort);
	}
	
	public Kunde kundenRegistrieren(Kunde einKunde) throws KundeExistiertBereitsException {
		return meineNutzer.registrieren(einKunde);
	}
	
	public Mitarbeiter mitarbeiterlogIn(String username, String passwort) {
		return meineNutzer.mitarbeiterlogIn(username, passwort);
	}
	public void speicherKunden() throws IOException {
		// TODO Auto-generated method stub
		meineNutzer.speicherKunden();
	}	
	
	
	
	// Methodenaufrufe zur (sortierten) Ausgabe und suche nach Artikeln aus der Artikelverwaltung 
	public void artikelsortiertAusgebenBezeichnung() {
		meineArtikel.artikelSortBezeichnung(meineArtikel.getArtikelBestand());
	}
	
	public void artikelsortiertAusgebenNummer() {
		meineArtikel.artikelSortNummer(meineArtikel.getArtikelBestand());
	}
	
	public List<Artikel> gibAlleArtikel() {// einfach delegieren an ArtikelVerwaltung meineArtikel
		return meineArtikel.getArtikelBestand();
	}
	
	public List<Artikel> sucheNachTitel(String titel) {// einfach delegieren an ArtieklVerwaltung meineArtikel
		return meineArtikel.sucheArtikel(titel); 
	}

	
	
	
	public String wkAusgeben(Kunde kundeEingeloggt) {
				//warenkorb des EINGELOGTEN KUNDEN ausgeben
//		Warenkorb wk = new Warenkorb();
//		 return wk.warenkorbAusgeben();
//		Kunde kundeEingeloggt = (Kunde) UserVerwaltung.getAngemeldeterUser();
		//warenkorb des EINGELOGTEN KUNDEN ??? ausgeben
		return kundeEingeloggt.getWk().warenkorbAusgeben();
	}
	
	
//	public String zumWKhinzufuegen() {
//		/*
//		 * Nummer die man eingegeben hat mit Artikelliste vergleichen
//		 * wenn eingegebene Nummer == Nummer in Artikelliste dann 
//		 * bestand prÃ¼fen
//		 * if artikel verfÃ¼gbar
//		 * WK befÃ¼llen else "Artikel nicht vorhanden oder falsche nummer"
//		 */
//		
//		return null;
//	}
	
	
	//WARENKORBVERWALTUNG ?????
	
	//Methode zum Prüfen ob ein Artikel bereits im Warenkorb liegt
		public boolean wkBestandspruefung(Artikel artikel, Kunde kundEingeloggt) {
			for (int i = 0; kundEingeloggt.getWk().getListe().size() > i; i++) {
				if(kundEingeloggt.getWk().getListe().elementAt(i).getTitel().equals(artikel.getTitel())) {
					return true;
				}
			}
			return false;
		}
		
	//Methode zum hinzufügen eines Artikels (falls noch nicht im WK) oder Erhöhens seiner Anzahl
	public void hinzufuegenOderErhoehen(Kunde kundEingeloggt,Artikel gefundenArt, int anzahl) {
		if(wkBestandspruefung(gefundenArt, kundEingeloggt) == true) {
			erhoeheEinkauf(kundEingeloggt,gefundenArt.getNummer(), anzahl);
		} else {
			//HIER IST NOCH EIN FEHLER "ANZAHL" WIRD DER BESTAND GENOMMEN STATT DIE ANZAHL DIE MAN HABEN MÃ–CHTE
			Artikel gesuchterArt = new Artikel (gefundenArt.getTitel(),gefundenArt.getNummer(),gefundenArt.isVerfuegbar(),gefundenArt.getBestand(),gefundenArt.getPreis());
			gesuchterArt.setNummer(gefundenArt.getNummer());
			kundEingeloggt.getWk().artikelwkHinzufuegen(gesuchterArt, anzahl);
		}
	}
	
	//Methode zum Erhöhen des Bestand
		// TODO: Das allermeiste in Artikelverwaltung erledigen
		public void erhoeheEinkauf(Kunde kundEingeloggt,int artNummer, int plusBestand) {
//			Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
			Vector <Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
			Artikel gesuchterArt = sucheArtikelinListe(warenkorbFuellung, artNummer);
			Artikel ausArtliste = sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);
					if((ausArtliste.getBestand() - gesuchterArt.getBestand())>= plusBestand) {
						gesuchterArt.setBestand(plusBestand + gesuchterArt.getBestand());
					} else {
						System.out.println("LagerbestandsException ...");
//						throw new LagerbestandsException(ausArtliste.getArtikelBestand()-gesuchterArt.getArtikelBestand());
					}
				}	
				
	
		
		public Artikel sucheArtikelinListe(Vector<Artikel> artListe, int nummer) {
			Artikel gesuchterArt=null; // warum =null?
			for (int i = 0; artListe.size() > i; i++) {
				if(artListe.elementAt(i).getNummer() == nummer) {
					gesuchterArt = artListe.elementAt(i);
				}
			}
			return gesuchterArt;
		}
		
	public String wkBefuellen(Kunde kundeEingeloggt,  int artNummer, int artAnzahl) {
		Vector <Artikel> artListe = meineArtikel.getArtikelBestand();
// 		clone() Methode ????
//		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
		String bestaetigung = "Es ist ein Fehler aufgetreten, versuchen Sie es noch mal.";
// 		Die Artikelliste wird nach den gewuenschten Artikel durchsucht.
		for(int i = 0 ; artListe.size() > i ; i++) {
			if(artListe.elementAt(i).getNummer() == artNummer) {
				Artikel gefundenArt = artListe.elementAt(i);
//					Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
					if((gefundenArt.getBestand()>= artAnzahl) == true) {
//						Hier muss eine Methode hinzufÃ¼gen stehhen
						hinzufuegenOderErhoehen(kundeEingeloggt,gefundenArt, artAnzahl);
//						String "bestaetigung" neu Ã¼berschrieben
						bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getTitel() + " in der Stueckzahl " + artAnzahl + " befuellt.\n";
					} else {
//						throw new LagerbestandsException(gefundenArt.getBestand());
					}
//			}
		}
		}
		return bestaetigung;
	}	
	

		
	
}