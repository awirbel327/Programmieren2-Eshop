package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.*;
import shop.local.valueobjects.Warenkorb;
import shop.local.domain.exceptions.*;
import java.util.Collections;
import shop.local.domain.Eshop;
import shop.local.ui.cui.ShopClientCUI;
import shop.local.valueobjects.*;
import shop.local.domain.*;



/**
 * Klasse zur Interaktion mit dem User.
 * Führt Methoden der Domain-Klassen aus und liest Eingaben der User ein.
 * Main Methode wird in dieser Klasse ausgeführt, welche den E-Shop aufbaut. 
 * @author Mareike
 *
 */

public class ShopClientCUI {
	
	private Eshop shop;
	private BufferedReader in;
	//private Kunde kundeEingeloggt;
	//private Mitarbeiter mitarbeiterEingeloggt;
	private User userEingeloggt;
	
	
	// Konstruktor-Methode welche neben dem Shop ansich auch einen BufferedReader initalisiert.
	public ShopClientCUI(String datei) throws IOException {
		shop = new Eshop(datei);
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// Methode zur Ausgabe des Menüs. Gibt Liste aller Optionen an, die der User beim Start des E-Shops hat.
	private void gibMenueAus() {
		System.out.print("Befehle: \n  Einloggen:  '0'");
		System.out.print("	       \n  als Kunde Registrieren:  '1'");
		System.out.print("		   \n  Artikel ausgeben:  'A'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'A1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'A2'");
		System.out.print("         \n  Artikel suchen  'B'");
		System.out.print("         \n  Artikel zum WK hinzufÃ¼gen: 'C'");
		System.out.print("         \n  Warenkorb anzeigen:  'D'");
		System.out.print("         \n  Warenkorb bearbeiten:  'E'");
		System.out.print("         \n  Bezahlen:  'F'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	private void gibMitarbeiterMenueAus() {
		System.out.print("Befehle: \n  Mitarbeiter Registrieren:  'Z'");
		System.out.println("	    \n einen neuen Artikel hinzufuegen: 'W'");
	}
	
	// Methode um die User-Eingaben einzulesen
	private String liesEingabe() throws IOException{
		return in.readLine();
	}
	
	// Kernmethode der CUI die je nach Eingabe die nötigen Untereingaben einliest und die passenden Methoden aus der Domain aufruft
	private void verarbeiteEingabe(String line) throws IOException {
		String auswahl;
		String titel;
		String name;
		String strasse;
		int hausNr;
		int plz;
		String ort;
		String username;
		String passwort;
		List<Artikel>liste;
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);	
		
		switch(line) {
		
		// Einloggen
		case "0":
			System.out.print("Als Kunde anmelden(j/n) :   > ");
			auswahl = liesEingabe();
			if(auswahl.equals("j")) {
				kundenlogin();
			} else {
				mitarbeiterlogin();
			}
			break;
			
		// Regstrieren (Kunden)
		case "1":	
			System.out.print("Vollstï¿½ndiger Name :   > ");
			name = liesEingabe();
			System.out.print("Straï¿½enname :   > ");
			strasse = liesEingabe();
			System.out.print("Hausnummer :   > ");			
			String hausNrString = liesEingabe();
			hausNr = Integer.parseInt(hausNrString);
			System.out.print("Postleitzahl :   > ");			
			String plzString = liesEingabe();
			plz = Integer.parseInt(plzString);
			System.out.print("Ort :   > ");
			ort = liesEingabe();
			System.out.print("Username :   > ");
			username = liesEingabe();
			System.out.print("passwort :   > ");
			passwort = liesEingabe();
			Kunde einKunde = new Kunde(name, strasse, hausNr, plz, ort, username, passwort );	//neuen Kunden erschaffen
			try {
				shop.kundenRegistrieren(einKunde);	
				userEingeloggt = einKunde ;
				System.out.println("Sie haben sich erfolgreich Registriert!");
				shop.speicherKunden();
			} catch (KundeExistiertBereitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		// Alle Artikel als Liste ausgeben (unsortiert)
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
			
		// Artikel suchen nach Bezeichnung (ändern von Titel)
		case "b":
			System.out.print("Welchen Artikel suchen Sie? :   > ");
			titel = liesEingabe();
			liste = shop.sucheNachTitel(titel);
			gibArtikellisteAus(liste);
			break;
			
		// Artikel nach Bezeichnung sortieren (vielleicht als Untermenü von "Artikel ausgeben")
		case "a1" :
			shop.artikelsortiertAusgebenBezeichnung();
			break;
			
		// Artikel nach Nummer sortieren
		case "a2" :
			shop.artikelsortiertAusgebenNummer();
			break;
			
		// Artikel zum WK hinzufügen (Vielleicht als Untermenü von "einloggen" wenn sich ein Kunde einloggt)
		case "c":
			menueWk(br);
			break;
			
		// Warenkorb anzeigen (Vielleicht als Untermenü von "einloggen" wenn sich ein Kunde einloggt)
		case "d":
			System.out.println(""+shop.wkAusgeben((Kunde)userEingeloggt));
			//gibMenueAus();
			break;
			
		// Warenkorb bearbeiten (Vielleicht als Untermenü von "einloggen" wenn sich ein Kunde einloggt)
		case "e":
			System.out.println(""+shop.wkAusgeben((Kunde)userEingeloggt));
			gibMenueAus();
			break;
		
		//Mitarbeiter registrieren
		case "z":
			System.out.print("Vollstï¿½ndiger Name :   > ");
			name = liesEingabe();
			System.out.print("Username :   > ");
			username = liesEingabe();
			System.out.print("passwort :   > ");
			passwort = liesEingabe();
			Mitarbeiter einMitarbeiter = new Mitarbeiter(name,username, passwort );	//neuen Mitarbeiter erschaffen
			try {
				shop.mitarbeiterRegistrieren(einMitarbeiter);
				System.out.println("Sie haben einen weiteren Mitarbeiter erfolgreich Registriert!");
				shop.speicherMitarbeiter();
			} catch (MitarbeiterExistiertBereitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			//mitarbeiter artikel hinzufügen
		case "w":
			System.out.print("Artikelbezeichnung :   >");
			String bezeichnung = liesEingabe();
			System.out.print("Artikelbestand :   >");
			String bestandString = liesEingabe();
			int bestand = Integer.parseInt(bestandString);
			System.out.print("Artikelpreis :   >");
			String artikelPreisString = liesEingabe();
			double artikelPreis = Double.parseDouble(artikelPreisString);
			Artikel einArtikel = new Artikel(bezeichnung, bestand, artikelPreis);
			try {
				shop.mitArtikelHinzu(einArtikel);
				System.out.println("Sie haben einen Artikel erfolgreich neu hinzugefuegt!");
				shop.speicherArtikel();
			} catch (ArtikelExistiertBereitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Ungueltige Eingabe!\n");
			//gibMenueAus();
		}
	}
	
	// Methode zum einlesen der Anmeldedaten wenn sich ein Kunde einloggen will
	private void kundenlogin() {
		String username = "";
		String passwort ="";
		System.out.print("Username eingeben :   > ");
		try {
			username = liesEingabe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Passwort eingeben :   > ");
		try {
			passwort = liesEingabe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Kunde kunde =shop.kundenlogIn(username, passwort);
		System.out.println("erfolgreich eingeloggt als "+ kunde.getName()+ "!!");
		userEingeloggt = kunde;		
	}
		
	// Methode zum einlesen der Anmeldedaten wenn sich ein Mitarbeiter einloggen will
	private void mitarbeiterlogin() {
		String username = "";
		String passwort ="";
		System.out.print("Username eingeben :   > ");
		try {
			username = liesEingabe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Passwort eingeben :   > ");
		try {
			passwort = liesEingabe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mitarbeiter mitarbeiter =shop.mitarbeiterlogIn(username, passwort);
		System.out.println("erfolgreich eingeloggt als "+ mitarbeiter.getName()+ "!!");
		userEingeloggt = mitarbeiter;		
	}
	
	// Methode um alle Artikel aus der Artikelliste auf der Konsole auszugeben
	private void gibArtikellisteAus(List<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Collections.sort(liste);
			for (Artikel artikel : liste) {
				System.out.println(artikel);
			}
		}
	}
	
	// Methode um den Warenkorb zu befüllen. Kunde kann Artikelnummer und Anzahl eingeben.
	public void menueWk(BufferedReader br) throws IOException {
		System.out.println("Geben Sie die Artikelnummer ein: \n");
		int artNummer=0;
		int artAnzahl=0;
		try {
		//setzt artNummer welches vorher 0 ist auf die Nummer die eingegeben wurde
		artNummer = Integer.parseInt(br.readLine());
		System.out.println("Geben Sie die Stueckzahl an: \n");
		//setzt artAnzahl welches vorher 0 ist auf die Nummer die eingegeben wurde
		artAnzahl = Integer.parseInt(br.readLine());
		} catch (NumberFormatException exception) {
			System.out.println("Geben sie eine Zahl ein!!!");
			menueWk(br);
		}
		try {
			System.out.println(shop.wkBefuellen((Kunde)userEingeloggt,artNummer, artAnzahl));
		} catch (LagerbestandsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		gibMenueAus();
	}

	
	/**
	 * Methode zur AusfÃ¼hrung der Hauptschleife:
	 * - MenÃ¼ ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 */
	public void run() {
		// Variable fÃ¼r Eingaben von der Konsole
		String input = ""; 
	
		// Hauptschleife der Benutzungsschnittstelle / Überprüfung Ausgabe Menue
		do {
			if(userEingeloggt == null) {
			gibMenueAus();
			try {
				input = liesEingabe();
				verarbeiteEingabe(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else if(userEingeloggt instanceof Kunde) {		//Überprüft ob usereingeloggt Objekt aus Klasse Kunde ist 
				gibMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (userEingeloggt instanceof Mitarbeiter) {
				gibMitarbeiterMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} while (!input.equals("q"));
	}
	
	
	/**
	 * Die main-Methode...
	 */
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI("SHOP");
			cui.run();
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
}