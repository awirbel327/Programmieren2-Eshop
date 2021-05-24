package shop.local.domain;

import java.io.IOException;
import java.util.Vector;
import shop.local.domain.exceptions.*;
import shop.local.persistence.*;
import shop.local.valueobjects.*;

public class UserVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Kunde> kundenListe = new Vector<Kunde>();	
	public static Vector <Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
	
	//public User angemeldeterUser;
	
	/********KUNDEN und MITARBEITER********/
	/*public User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	public void setAngemeldeterUser() {
		angemeldeterUser = user;
	}*/

	
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
	
	public void kundeEinfuegen(Kunde einKunde) throws KundeExistiertBereitsException {
		if (kundenListe.contains(einKunde)) {
			throw new KundeExistiertBereitsException(einKunde, " - in 'einfuegen()'");
		}

		kundenListe.add(einKunde);
	}
	
	//Kern vom Kunde login 
	public Kunde kundenlogIn (String username, String passwort) {
		for (Kunde kunde:kundenListe) {	
			if(username.equals(kunde.getUsername())) {
				if(passwort.equals(kunde.getPasswort())) {
					return kunde;
				}
			}
		}
		return null;
	}
	
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
	
	public void speicherKunden() {	//Methode zum speichern der Kundenliste (z.B. bei Registrierung)
		
	}
	
	/*
	public void liesMitarbeiter(String datei) throws IOException {
		pm.openForReading(datei); // PersistenzManager für Lesevorgänge öffnen

		Mitarbeiter einMitarbeiter;
		do {
			einMitarbeiter = pm.ladeMitarbeiter();
			if (einMitarbeiter != null) {
				try {
					mitarbeiterEinfuegen(einMitarbeiter);
				} catch (KundeExistiertBereitsException e1) {
				}
			}
		} while (einMitarbeiter != null);
		pm.close();
	}
	
	public void mitarbeiterEinfuegen(Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException {
		if (kundenListe.contains(einMitarbeiter)) {
			throw new MitarbeiterExistiertBereitsException(einMitarbeiter, " - in 'einfuegen()'");
		}

		kundenListe.add(einMitarbeiter);
	}
	
	public Mitarbeiter mitarbeiterlogIn (String username, String passwort) {
		for (Mitarbeiter mitarbeiter:mitarbeiterListe) {	
			if(username.equals(mitarbeiter.getUsername())) {
				if(passwort.equals(mitarbeiter.getPasswort())) {
					return mitarbeiter;
					
				}
			}
		}
		return null;
	}*/
		
	
	/******** Methoden f�r MITARBEITER ********/
	
	public void newArticle (String articleName, int stock) {
	}
	
	public void increaseStock (Artikel artikel, int increasedStock) {
		
	}
	
	/********Methoden f�r KUNDEN********/
	
	
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
}
