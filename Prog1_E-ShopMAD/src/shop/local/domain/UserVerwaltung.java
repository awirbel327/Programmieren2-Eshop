package shop.local.domain;

import java.io.IOException;
import java.util.Vector;
import shop.local.domain.exceptions.*;
import shop.local.persistence.*;
import shop.local.valueobjects.*;



/*
 * Klasse zur Verwaltung unserer Userdaten
 * Beinhaltet Methoden f�r MITARBEITER und f�r KUNDEN
 * Beide sind Unterklassen dieser Klasse
 */
public class UserVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	public static User angemeldeterUser;
	public static Vector <Kunde> kundenListe = new Vector<Kunde>();	
	public static Vector <Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
	
	
	
	
	/********KUNDEN und MITARBEITER********/
	
	// Getter & Setter f�r angemeldete User
	public static User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	
	// FRAGE: M�sste dieser Methode nicht dann auch ein User Objekt �bergeben werden?
	public void setAngemeldeterUser(Kunde kunde) {
		angemeldeterUser = kunde;
	}
	
	
	/********Methoden f�r KUNDEN********/
	
	// Methode der unsere E-Shop Datei �bergeben wird um die Kundenliste in die Persistenz zu �berf�hren
	// So ist diese bei jedem �ffnen des E-Shops auf dem neusten Stand und wird �ber l�ngere Zeit gespeichert!
	public void liesKunden(String datei) throws IOException {
		pm.openForReading(datei); // PersistenzManager für Lesevorgänge öffnen
		Kunde einKunde;
		do {
			einKunde = pm.ladeKunde();
			if (einKunde != null) {
				try {
					kundeEinfuegen(einKunde);
				} catch (KundeExistiertBereitsException e1) {
				}
			}
		} while (einKunde != null);
		pm.close();
	}
	
	
	// Methode um einen Kunden zur Kundenliste hinzuzuf�gen. Fehler wenn Kunde bereits in der Liste ist.
	// F�r Registrieren
	public void kundeEinfuegen(Kunde einKunde) throws KundeExistiertBereitsException {
		if (kundenListe.contains(einKunde)) {
			throw new KundeExistiertBereitsException(einKunde, " - in 'kundeEinfuegen()'");
		}

		kundenListe.add(einKunde);
	}
	
	// Methode zum Abgleichen der eingegeben Kundendaten mit den Gespeicherten. Gibt bei Korrekter Eingabe Kunde wieder.
	//Kern vom Kunde login 
	public Kunde kundenlogIn (String username, String passwort) {
		for (Kunde kunde:kundenListe) {	
			if(username.equals(kunde.getUsername())) {
				if(passwort.equals(kunde.getPasswort())) {
					return kunde;
				}
			}
		}
		return null; //Warum wird hier am Ende nochmal null zur�ckgegeben?
	}
	
	
	// Methode zum Abgleichen des neuen Kundenobjekts mit den bestehenden Kundendaten. Gibt, wenn es keine Dopplung gibt, den 
	// neuen Kunden aus und f�gt ihn zur Liste der Kunden hinzu.
	//Kunde registrieren
	public Kunde registrieren(Kunde einKunde) throws KundeExistiertBereitsException {
	for(Kunde kunde:kundenListe) {
		if(einKunde.getUsername().equals(kunde.getUsername())) {	//Gucken ob Kunde mit Namen bereits existiert
			throw new KundeExistiertBereitsException(einKunde, "Kundenname existiert bereits");
		}
	}
	kundenListe.add(einKunde);
	return einKunde; 
	}
	
	
	
	
	
	//Methode zum speichern der Kundenliste (z.B. bei Registrierung) 
	public void speicherKunden() throws IOException {	
		pm.openForWriting("SHOP_Kunde.txt"); // PersistenzManager für Schreibvorgang �ffnen
		for(Kunde kunde:kundenListe) {
			System.out.println(kunde.getName() + " wurde gespeichert");
			pm.speicherKundeDaten(kunde);	
		}
		pm.close();
	}

	
	
	/****** Interaktionen mit Warenkorb *******/
	
	public void emptyCart () {
	}
	
	public void buyCart() {
		emptyCart();
		//createBill();
		reduceStock();
	}
	
	public void addItemtoCart(int Anzahl, String Artikel) {
	}
	
	public void changeAmound () {
	}
	
	
	public void reduceStock () {
	}
	
	
	
	
	/******** Methoden f�r MITARBEITER ********/
	
	// FRAGE: K�nnte man die beiden Lese-Methoden vielleicht zusammenlegen?
	// Methode der unsere E-Shop Datei �bergeben wird um die Mitarbeiterliste in die Persistenz zu �berf�hren
	// So ist diese bei jedem �ffnen des E-Shops auf dem neusten Stand und wird �ber l�ngere Zeit gespeichert!
	public void liesMitarbeiter(String datei) throws IOException {
		pm.openForReading(datei); // PersistenzManager für Lesevorgänge öffnen

		Mitarbeiter einMitarbeiter;
		do {
			einMitarbeiter = pm.ladeMitarbeiter();
			if (einMitarbeiter != null) {
				try {
					mitarbeiterEinfuegen(einMitarbeiter);
				} catch (MitarbeiterExistiertBereitsException e1) {
				}
			}
		} while (einMitarbeiter != null);
		pm.close();
	}
	
	
	// Methode um einen Mitarbeiter zur Mitarbeiterliste hinzuzuf�gen. Fehler wenn Mitarbeiter bereits in der Liste ist.
	public void mitarbeiterEinfuegen(Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException {
		if (mitarbeiterListe.contains(einMitarbeiter)) {
			throw new MitarbeiterExistiertBereitsException(einMitarbeiter, " - in 'mitarbeiterEinfuegen()'");
		}

		mitarbeiterListe.add(einMitarbeiter);
	}
	
	// Methode zum Abgleichen der eingegeben Mitarbeiterdaten mit den Gespeicherten. Gibt bei Korrekter Eingabe Mitarbeiter wieder.
	public Mitarbeiter mitarbeiterlogIn (String username, String passwort) {
		for (Mitarbeiter mitarbeiter:mitarbeiterListe) {	
			if(username.equals(mitarbeiter.getUsername())) {
				if(passwort.equals(mitarbeiter.getPasswort())) {
					return mitarbeiter;
					
				}
			}
		}
		return null;
	}
	
	
	//
	//Mitarbieter registrieren Mitarbeiter
	public Mitarbeiter mitRegistrierenMit(Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException {
		for(Mitarbeiter mitarbeiter:mitarbeiterListe) {
			if(einMitarbeiter.getUsername().equals(mitarbeiter.getUsername())) {	//Gucken ob Kunde mit Namen bereits existiert
				throw new MitarbeiterExistiertBereitsException(einMitarbeiter, "- in mitRegistrierenMit");
			}
		}
		einMitarbeiter.setMitarbeiterNr(mitarbeiterListe.size() + 1);		//setzen der Mitarbeiternummer
		mitarbeiterListe.add(einMitarbeiter);
		return einMitarbeiter; 
	}
			
	
	
	//Methode zum speichern der Mitarbeiterliste (z.B. bei Registrierung) 
	public void speicherMitarbeiter() throws IOException {	
		pm.openForWriting("SHOP_Mitarbeiter.txt"); // PersistenzManager für Schreibvorgang �ffnen
		
		for(Mitarbeiter mitarbeiter:mitarbeiterListe) {
			System.out.println(mitarbeiter.getName() + " wurde gespeichert");
			pm.speicherMitarbeiterDaten(mitarbeiter);	
		}
		pm.close();
	}
	
		
	/***** Interaktion mit Artikel(Bestand) *****/
	
	public void newArticle (String articleName, int stock) {
	}
	
	public void increaseStock (Artikel artikel, int increasedStock) {
		
	}
	
	
}
