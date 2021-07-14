package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import shop.local.persistence.*;
import shop.local.domain.exceptions.*;

import shop.local.valueobjects.*;

/**
 * Klasse zur Verwaltung unseres E-Shops.
 * Bietet Methoden 
 * Zum Ausgeben aller Artikel (soriert), zur Suche nach Artikeln und zur suche nach Artikeln.
 * Zum einloggen, registrieren und speichern von Kunden sowie zum einloggen von Mitarbeitern.
 * Warenkorb methoden
 * E-Shop ist eig nur eine Schnittstelle zwischen der Ansicht und Logik!!!!!
 */

public class Eshop {
	
	private String datei;
	
	private ArtikelVerwaltung meineArtikel;
	private WarenkorbVerwaltung meinWarenkorb;
	private UserVerwaltung meineNutzer;
	private EreignisVerwaltung meineEreignisse;
	private User userEingeloggt;
	
	
	public Eshop(String datei) throws IOException {
		this.datei = datei;
		
		// Artikelbestand aus Datei einlesen
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(datei + "_B.txt");
		
		// Kundendaten aus Datei einlesen
		meineNutzer = new UserVerwaltung();
		meineNutzer.liesKunden("SHOP_Kunde.txt");
		
		// Mitarbeiterdaten aus Datei einlesen
		meineNutzer = new UserVerwaltung();
		meineNutzer.liesMitarbeiter("SHOP_Mitarbeiter.txt");
		
		meinWarenkorb = new WarenkorbVerwaltung();
		
		meineEreignisse = new EreignisVerwaltung();
		meineEreignisse.liesDaten("SHOP_Ereignisse");
		
	}
	
	public Eshop() {
		// TODO Auto-generated constructor stub
	}


	/* Warenkorb-Methoden*/
	public String wkBefuellen(Kunde userEingeloggt, int artNummer, int artAnzahl) throws LagerbestandsException, PackungsgroesseException {
		
		return meinWarenkorb.wkBefuellen(userEingeloggt, artNummer, artAnzahl, meineArtikel);
	}
	
	public String wkAusgeben(Kunde userEingeloggt) {
		return meinWarenkorb.warenkorbAusgeben(userEingeloggt);
	}
	
	public void erhoeheEinkauf(Kunde userEingeloggt,int wkNummer,int wkStueck) throws PackungsgroesseException, LagerbestandsException {
		meinWarenkorb.erhoeheEinkauf(userEingeloggt, wkNummer, wkStueck, meineArtikel);
	}
	
	public String kaufeWarenkorb(Kunde userEingeloggt) throws IOException {
		return meinWarenkorb.kaufeWarenkorb(userEingeloggt, meineArtikel, meineEreignisse);
	}
	public void leereWk(Kunde userEingeloggt) {
		meinWarenkorb.warenkorbLeeren();
		
	}
	public Vector<Artikel> warenkorbGUI(Kunde userEingeloggt) {
		
		return userEingeloggt.getWk().getWKGui();
	}
	/* User-Methoden*/
	
	// Methodenaufrufe zum einloggen/registrieren und speichern aus der Userverwaltung
//	public void userLogIn (String username, String passwort)throws PasswortOderUsernameFalschException{
//		this.userEingeloggt = meineNutzer.userLogin(username,passwort);
//	}
//	
	public User userLogIn(String username, String passwort) throws PasswortOderUsernameFalschException {
		this.userEingeloggt = meineNutzer.userLogin(username, passwort);
		return userEingeloggt;
	}
	
	
//	public Kunde kundenlogIn(String username, String passwort) throws PasswortOderUsernameFalschException {
//		return meineNutzer.kundenlogIn(username, passwort);
//	}
	
	public Kunde kundenRegistrieren(Kunde einKunde) throws KundeExistiertBereitsException {
		return meineNutzer.registrieren(einKunde);
		
	}
	
	public void speicherKunden() throws IOException {
		meineNutzer.speicherKunden();
	}	
	
	public Mitarbeiter mitarbeiterRegistrieren (Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException{
		return meineNutzer.mitRegistrierenMit(einMitarbeiter);
	}
	
//	public Mitarbeiter mitarbeiterlogIn(String username, String passwort) throws PasswortOderUsernameFalschException {
//		return meineNutzer.mitarbeiterlogIn(username, passwort);
//	}

	public void speicherMitarbeiter() throws IOException {
		meineNutzer.speicherMitarbeiter();
	}
	
	
	/* Artikel-Methoden*/
	
	//Methodenaufrufe Artikel Mitarbeiter
	public void mitArtikelHinzu(Artikel einArtikel) throws ArtikelExistiertBereitsException, PackungsgroesseException {
		meineArtikel.mitArtikelhinzufuegen(einArtikel);
		Ereignis ereignis = new Ereignis("Mitarbeiter", ((Mitarbeiter) userEingeloggt).getMitarbeiterNr(), einArtikel.getBezeichnung(), einArtikel.getBestand(), "ArtikelHinzugefuegt");
		meineEreignisse.addEreignis(ereignis);
	}
	
	
	public void speicherArtikel() throws IOException {
		meineArtikel.speicherArtikel();
	}
	
	// Methodenaufrufe zur (sortierten) Ausgabe und suche nach Artikeln aus der Artikelverwaltung 
	public void artikelsortiertAusgebenBezeichnung() {
		meineArtikel.artikelSortBezeichnung(meineArtikel.getArtikelBestand());
	}
	
	public void artikelsortiertAusgebenNummer() {
		meineArtikel.artikelSortNummer(meineArtikel.getArtikelBestand());
	}
	
	public List<Artikel> gibAlleArtikel() {
		return meineArtikel.getArtikelBestand();
	}
	
	public List<Artikel> sucheNachBezeichnung(String bezeichnung) {
		return meineArtikel.sucheArtikel(bezeichnung); 
	}

	public void mitErhoehtArtikel(String artikelname, int erhohung) throws PackungsgroesseException {
		meineArtikel.mitErhoehtArtikel(artikelname, erhohung);
		Ereignis ereignis = new Ereignis("Mitarbeiter", ((Mitarbeiter) userEingeloggt).getMitarbeiterNr(), artikelname,erhohung, "Artikelbestand Erhoeht");
		meineEreignisse.addEreignis(ereignis);
	}
	
	public void userEingeloggt(User userEingeloggt) {
		this.userEingeloggt = userEingeloggt;
	}
	
	public User getUserEingeloggt() {
		return this.userEingeloggt;
	}

	public void speicherEreignis() throws IOException {
		meineEreignisse.speicherEreignis();
	}
}