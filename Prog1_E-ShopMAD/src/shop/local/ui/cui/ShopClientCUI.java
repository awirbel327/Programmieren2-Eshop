package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.*;
import shop.local.domain.exceptions.*;
import java.util.Collections;
import shop.local.domain.Eshop;
import shop.local.ui.cui.ShopClientCUI;
import shop.local.valueobjects.*;
import shop.local.domain.*;
import shop.local.domain.WarenkorbVerwaltung;

/**
 * Klasse zur Interaktion mit dem User.
 * Führt Methoden der Domain-Klassen aus und liest Eingaben der User ein.
 * Main Methode wird in dieser Klasse ausgeführt, welche den E-Shop aufbaut. 
 */

public class ShopClientCUI {
	
	private Eshop shop;
//	private WarenkorbVerwaltung warenkorb;
	private BufferedReader in;
	//private Kunde kundeEingeloggt;
	//private Mitarbeiter mitarbeiterEingeloggt;
	private User userEingeloggt;
	
	
	// Konstruktor-Methode welche neben dem Shop ansich auch einen BufferedReader initalisiert.
	public ShopClientCUI(String datei) throws IOException {
		shop = new Eshop(datei);
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// Methode um die User-Eingaben einzulesen
	private String liesEingabe() throws IOException{
		return in.readLine();
	}
	
	
	
	// Methode zur Ausgabe des Menüs. Gibt Liste aller Optionen an, die der User beim Start des E-Shops hat.
	private void gibMenueAus() {
		System.out.print("Befehle: \n  Einloggen:  '0'");
		System.out.print("	       \n  als Kunde Registrieren:  '1'");
		System.out.print("		   \n  Artikel ausgeben:  'A'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'A1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'A2'");
		System.out.print("         \n  Artikel suchen  'B'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	
	//Methode zur Ausgabe des Mitarbeiter Menues
	private void gibMitarbeiterMenueAus() {
		System.out.print("Befehle: \n  Mitarbeiter Registrieren:  'Z'");
		System.out.println("	   \n  einen neuen Artikel hinzufuegen: 'W'");
		System.out.println("	   \n  Bestand erh�hen: 'L'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	//Methode zur Ausgabe des eingeloggten Kunden Menues
	private void eingeloggterUserMenue() {
		System.out.print("Befehle: \n  Artikel ausgeben:  'A'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'A1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'A2'");
		System.out.print("         \n  Artikel suchen  'B'");
		System.out.print("         \n  Artikel zum WK hinzufuegen: 'C'");
		System.out.print("         \n  Warenkorb anzeigen:  'D'");
		System.out.print("         \n  Warenkorb bearbeiten:  'E'");
		System.out.print("         \n  Bezahlen:  'F'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	// Kernmethode der CUI die je nach Eingabe die noetigen Untereingaben einliest und die passenden Methoden aus der Domain aufruft
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
		Artikel einArtikel = null;
		
		switch(line) {
		
		// Einloggen
		case "0":
			System.out.print("Als Kunde(k) oder als Mitarbeiter(m) anmelden :   > ");
			auswahl = liesEingabe();
			if(auswahl.equals("k")) {
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
			
		// Artikel zum WK hinzufügen 
		case "c":
			menueWk(br);
			break;
			
		// Warenkorb anzeigen 
		case "d":
			System.out.println(""+shop.wkAusgeben((Kunde)userEingeloggt));
			//gibMenueAus();
			break;
			
		// Warenkorb bearbeiten
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
			System.out.print("Massengutartikel? j/n :   >");
			String artikelArt = liesEingabe();
			System.out.print("Artikelbestand :   >");
			String bestandString = liesEingabe();
			int bestand = Integer.parseInt(bestandString);
			System.out.print("Artikelpreis :   >");
			String artikelPreisString = liesEingabe();
			double artikelPreis = Double.parseDouble(artikelPreisString);
			
			if (artikelArt.equals("j")) {
				System.out.print("Packungsgr��e :   >");
				String packungsgroesseString = liesEingabe();
				int packungsgroesse = Integer.parseInt(packungsgroesseString);
				einArtikel = new Massengutartikel(bezeichnung, bestand, artikelPreis, packungsgroesse);
				
			} 
			else if (artikelArt.equals("n")) {
				einArtikel = new Artikel(bezeichnung, bestand, artikelPreis);
			}
			else {
				System.out.println("Bitte w�hlen sie j/n.");
			}
			
			try {
				shop.mitArtikelHinzu(einArtikel);
				System.out.println("Sie haben einen Artikel erfolgreich neu hinzugefuegt!");
				shop.speicherArtikel();
			} catch (ArtikelExistiertBereitsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (PackungsgroesseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
			
			//Mitarbeiter erhoeht bestand
		case "l":
			System.out.print("Artikelbezeichnung :   >");
			String artikelname = liesEingabe();
			List<Artikel> gefundeneArtikel = shop.sucheNachTitel(artikelname);
			if(gefundeneArtikel.size() > 1 ) {
				System.out.println("fehler mehr als 1 artikel in liste!");
				return;
			} else {
				System.out.print("Bestand erhoehen um :   >");
				String erhoehung = liesEingabe();
				int erhohung = Integer.parseInt(erhoehung);
				try {
				shop.mitErhoehtArtikel(artikelname, erhohung);
				} catch (PackungsgroesseException e){
					e.printStackTrace();
				}
			}
			break;
		default:
			System.out.println("Der Vorgang wurde abgebrochen\n");
			gibMenueAus();
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
		try {
			Kunde kunde =shop.kundenlogIn(username, passwort);
			System.out.println("Hallo "+ kunde.getName()+ "!! Schön, dass du da bist!");
			userEingeloggt = kunde;
		} catch (PasswortOderUsernameFalschException e) {
			e.printStackTrace();
		}
		
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
		try {
			Mitarbeiter mitarbeiter = shop.mitarbeiterlogIn(username, passwort);
			System.out.println("erfolgreich eingeloggt als "+ mitarbeiter.getName()+ "!!");
			userEingeloggt = mitarbeiter;	
		} catch (PasswortOderUsernameFalschException e) {
			e.printStackTrace();	
		}
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
		} catch (PackungsgroesseException e2) {
			System.out.println(e2.getMessage());
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
		String input = ""; // Variable fÃ¼r Eingaben von der Konsole
	
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
				eingeloggterUserMenue();
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