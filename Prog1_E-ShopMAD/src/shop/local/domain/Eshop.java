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
 * Bietet Methoden 
 * Zum Ausgeben aller Artikel (soriert), zur Suche nach Artikeln und zur suche nach Artikeln.
 * Zum einloggen, registrieren und speichern von Kunden sowie zum einloggen von Mitarbeitern.
 * ...WARENKORB METHODEN tbd.....
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
		meineNutzer = new UserVerwaltung(); // kann man löschen?
		meineNutzer.liesMitarbeiter("SHOP_Mitarbeiter.txt");
		
	}
	
	// Methodenaufrufe zum einloggen/registrieren und speichern aus der Userverwaltung
	public Kunde kundenlogIn(String username, String passwort) {
		return meineNutzer.kundenlogIn(username, passwort);
	}
	
	public Kunde kundenRegistrieren(Kunde einKunde) throws KundeExistiertBereitsException {
		return meineNutzer.registrieren(einKunde);
	}
	
	public Mitarbeiter mitarbeiterRegistrieren (Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException{
		return meineNutzer.mitRegistrierenMit(einMitarbeiter);
	}
	
	public Mitarbeiter mitarbeiterlogIn(String username, String passwort) {
		return meineNutzer.mitarbeiterlogIn(username, passwort);
	}
	public void speicherKunden() throws IOException {
		// TODO Auto-generated method stub
		meineNutzer.speicherKunden();
	}	
	
	public void speicherMitarbeiter() throws IOException {
		// TODO Auto-generated method stub
		meineNutzer.speicherMitarbeiter();
	}
	
	public void speicherArtikel() throws IOException {
		// TODO Auto-generated method stub
		meineArtikel.speicherArtikel();
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

	
	//Geh�rt die Methode nicht in die UserVerwaltung unter Mitarbeiter?
	//Methodenaufrufe Artikel Mitarbeiter
	public void mitArtikelHinzu(Artikel artikel) throws ArtikelExistiertBereitsException {
		meineArtikel.einfuegen(artikel);
	}
	
	
	
	/**
	 * Methoden zur Warenkorbverwaltung: Auslagern in passende Klasse, hier dann nur
	 * noch Aufrufen der Methoden
	 * 
	 */
	
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
//		 * bestand prüfen
//		 * if artikel verfügbar
//		 * WK befüllen else "Artikel nicht vorhanden oder falsche nummer"
//		 */
//		
//		return null;
//	}
	
	
	//WARENKORBVERWALTUNG ?????
	
	//Methode zum Pr�fen ob ein Artikel bereits im Warenkorb liegt (w�rde die Methode vielleicht anders nennen)
	public boolean wkBestandspruefung(Artikel artikel, Kunde kundEingeloggt) {
		for (int i = 0; kundEingeloggt.getWk().getListe().size() > i; i++) {
			if(kundEingeloggt.getWk().getListe().elementAt(i).getTitel().equals(artikel.getTitel())) {
				return true;
			}
		}
		return false;
	}
		
	
	//Methode zum hinzuf�gen eines Artikels (falls noch nicht im WK) oder Erh�hens seiner Anzahl
	public void hinzufuegenOderErhoehen(Kunde kundEingeloggt,Artikel gefundenArt, int anzahl) throws LagerbestandsException {
		if(wkBestandspruefung(gefundenArt, kundEingeloggt) == true) {
			//try {
			erhoeheEinkauf(kundEingeloggt,gefundenArt.getNummer(), anzahl);
			//} catch (LagerbestandsException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		} else { 
			//HIER IST NOCH EIN FEHLER "ANZAHL" WIRD DER BESTAND GENOMMEN STATT DIE ANZAHL DIE MAN HABEN MÖCHTE
			Artikel gesuchterArt = new Artikel (gefundenArt.getTitel(),gefundenArt.getNummer(),gefundenArt.isVerfuegbar(),gefundenArt.getBestand(),gefundenArt.getPreis());
			gesuchterArt.setNummer(gefundenArt.getNummer());
			kundEingeloggt.getWk().artikelwkHinzufuegen(gesuchterArt, anzahl);
		}
	}
	
	
	//Methode zum Erh�hen der Anzahl des Artikels im WK 
	// TODO: Das allermeiste in Artikelverwaltung erledigen
	public void erhoeheEinkauf(Kunde kundEingeloggt,int artNummer, int plusBestand) throws LagerbestandsException {
//		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
		Vector <Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
		Artikel gesuchterArt = sucheArtikelinListe(warenkorbFuellung, artNummer);
		Artikel ausArtliste = sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);
			if((ausArtliste.getBestand() - gesuchterArt.getBestand())>= plusBestand) {
				gesuchterArt.setBestand(plusBestand + gesuchterArt.getBestand()); //Bestand aus Lager verringern?
			} else {
				//System.out.println("LagerbestandsException ...");
				throw new LagerbestandsException(ausArtliste);
			}
	}	
				
	
	//Methode um einen Artikel anhand seiner Nummer aus einem beliebigen Vector rauszusuchen	
	public Artikel sucheArtikelinListe(Vector<Artikel> artListe, int nummer) {
		Artikel gesuchterArt=null; // warum =null?
		for (int i = 0; artListe.size() > i; i++) {
			if(artListe.elementAt(i).getNummer() == nummer) {
				gesuchterArt = artListe.elementAt(i);
			}
		}
		return gesuchterArt;
	}
	
	
	//Methode um einen Artikel anhand seiner Nummer in beliebiger Anzahl dem pers�nlichen WK (des Kunden) hinzuzuf�gen(inkl. Best�tigung)	
	public String wkBefuellen(Kunde kundeEingeloggt,  int artNummer, int artAnzahl) throws LagerbestandsException {
		Vector <Artikel> artListe = meineArtikel.getArtikelBestand();
// 		clone() Methode ????
//		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
		String bestaetigung = "Dieser Artikel existiert nicht.";
// 		Die Artikelliste wird nach den gewuenschten Artikel durchsucht.
		for(int i = 0 ; artListe.size() > i ; i++) {
			if(artListe.elementAt(i).getNummer() == artNummer) {
				Artikel gefundenArt = artListe.elementAt(i);
//					Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
				
				
					// If Abfrage wird unn�tig, da die Exception schon in der Methode hinzuf�genOderErhoehen gepr�ft wird
					//	if((gefundenArt.getBestand()>= artAnzahl) == true) {
				
				
//						
						hinzufuegenOderErhoehen(kundeEingeloggt,gefundenArt, artAnzahl);
//						String "bestaetigung" wird �berschrieben
						bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getTitel() + " in der Stueckzahl " + artAnzahl + " befuellt.\n";

						//					} else {
//						throw new LagerbestandsException(gefundenArt);
					}
		}
//		}
		return bestaetigung;
	}	
	

		
	
}