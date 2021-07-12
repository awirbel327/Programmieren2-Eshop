package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import shop.local.domain.exceptions.*;
import java.util.Collections;
import shop.local.domain.Eshop;
import shop.local.ui.cui.ShopClientCUI;
import shop.local.valueobjects.*;

public class ShopClientCUI {
	
	
	private Eshop shop;
	private BufferedReader in;
	private User userEingeloggt;
	private Artikel einArtikel;
	
	
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
		System.out.print("		   \n  Artikel ausgeben:  'a'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'a1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'a2'");
		System.out.print("         \n  Artikel suchen  'b'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	//Methode zur Ausgabe des Mitarbeiter Menues
	private void gibMitarbeiterMenueAus() {
		System.out.print("Befehle: \n  Mitarbeiter Registrieren:  'z'");
		System.out.println("	   \n  einen neuen Artikel hinzufuegen: 'w'");
		System.out.println("	   \n  Bestand erhoehen: 'l'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	//Methode zur Ausgabe des eingeloggten Kunden Menues
	private void eingeloggterUserMenue() {
		System.out.print("Befehle: \n  Artikel ausgeben:  'a'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'a1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'a2'");
		System.out.print("         \n  Artikel suchen  'b'");
		System.out.print("         \n  Artikel zum WK hinzufuegen: 'c'");
		System.out.print("         \n  Warenkorb anzeigen:  'd'");
		System.out.print("         \n  Warenkorb bearbeiten:  'e'");
		System.out.print("         \n  Warenkorb Leeren:  'g'");
		System.out.print("         \n  Warenkorb kaufen:  'f'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	// Kernmethode der CUI die je nach Eingabe die noetigen Untereingaben einliest und die passenden Methoden aus der Domain aufruft
	private void verarbeiteEingabe(String line) throws IOException {
		String auswahl;
		String bezeichnung;
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
			System.out.print("Als Kunde(k) oder als Mitarbeiter(m) anmelden :   > ");
			auswahl = liesEingabe();
			if(auswahl.equals("k")) {
				kundenlogin();
			}
			else if(auswahl.equals("m")) {
				mitarbeiterlogin();
			} else {
				System.out.println("Bitte w�hlen Sie (k) oder (m) aus.");
			}
			break;
		// Regstrieren (Kunden)
		case "1":	
			System.out.print("Vollstaendiger Name :   > ");
			name = liesEingabe();
			System.out.print("Str.:   > ");
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
			//neuen Kunden erschaffen
			Kunde einKunde = new Kunde(name, strasse, hausNr, plz, ort, username, passwort );	
			try {
				shop.kundenRegistrieren(einKunde);	
				userEingeloggt = einKunde ;
				System.out.println("Sie haben sich erfolgreich Registriert!");
				shop.speicherKunden();
				shop.speicherEreignis();
			} catch (KundeExistiertBereitsException e) {
				System.out.println(e.getMessage());
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
			bezeichnung = liesEingabe();
			liste = shop.sucheNachBezeichnung(bezeichnung);
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
			break;
			
		// Warenkorb bearbeiten
		case "e":
			System.out.println("Artikelbestand im Warenkorb erhoehen (5) oder senken(6)? > ");
			int abfrageWK = Integer.parseInt(br.readLine());
			System.out.println("Geben Sie die Artikelnummer ein: \n");
			System.out.println("> ");
			int wkNummer = Integer.parseInt(br.readLine());
			int wkStueck = 0;
			if(abfrageWK == 5) {
				System.out.println("Um wie viel soll erhoeht werden?: \n");
				System.out.println("> ");
				wkStueck = Integer.parseInt(br.readLine());
			}
			else if(abfrageWK == 6 ) {
				System.out.println("Um wie viel soll gesenkt werden?: \n");
				System.out.println("> ");
				wkStueck = Integer.parseInt(br.readLine());
				wkStueck = -wkStueck;
			} 
			else {
				System.out.println("bitte waehlen Sie (5) oder (6) aus.");
			}
			try {
				shop.erhoeheEinkauf((Kunde)userEingeloggt,wkNummer,wkStueck);
				System.out.println("Die Artikelanzahl wurde erfolgreich angepasst.");
			} catch (PackungsgroesseException e) {
				System.out.println(e.getMessage());
			} catch (LagerbestandsException e) {
				System.out.println(e.getMessage());
			}
			
			gibMenueAus();
			break;
			
			//Warenkorb kaufen
			case "f":
			System.out.println(shop.kaufeWarenkorb((Kunde)userEingeloggt));
			break;
			case "g":
				shop.leereWk((Kunde)userEingeloggt);
				break;
		
		//Mitarbeiter registrieren
		case "z":
			System.out.print("Vollstaendiger Name :   > ");
			name = liesEingabe();
			System.out.print("Username :   > ");
			username = liesEingabe();
			System.out.print("passwort :   > ");
			passwort = liesEingabe();
			Mitarbeiter einMitarbeiter = new Mitarbeiter(name,username, passwort );	//neuen Mitarbeiter erschaffen
			try {
				shop.mitarbeiterRegistrieren(einMitarbeiter);
				System.out.println(name + " wurde erfolgreich registriert!");
				shop.speicherMitarbeiter();
			} catch (MitarbeiterExistiertBereitsException e) {
				System.out.println(e.getMessage());
			}
			break;
			
			//mitarbeiter artikel hinzufügen
		case "w":
			System.out.print("Artikelbezeichnung :   >");
			String bezeichnung1 = liesEingabe();
			System.out.print("Massengutartikel? j/n :   >");
			String artikelArt = liesEingabe();
			System.out.print("Artikelbestand :   >");
			String bestandString = liesEingabe();
			int bestand = Integer.parseInt(bestandString);
			System.out.print("Artikelpreis :   >");
			String artikelPreisString = liesEingabe();
			double artikelPreis = Double.parseDouble(artikelPreisString);
			
			if (artikelArt.equals("j")) {
				System.out.print("Packungsgroesse :   >");
				String packungsgroesseString = liesEingabe();
				int packungsgroesse = Integer.parseInt(packungsgroesseString);
				einArtikel = new Massengutartikel(bezeichnung1, bestand, artikelPreis, packungsgroesse);
			} 
			else if (artikelArt.equals("n")) {
				einArtikel = new Artikel(bezeichnung1, bestand, artikelPreis);
			}
			else {
				System.out.println("Bitte waehlen sie j/n.");
			}
			
			try {
				shop.mitArtikelHinzu(einArtikel);
				System.out.println("Sie haben " + einArtikel  + " erfolgreich zum Lager hinzugefuegt!");
				shop.speicherArtikel();
				shop.speicherEreignis();
			} catch (ArtikelExistiertBereitsException e1) {
				System.out.println(e1.getMessage());
			}
			catch (PackungsgroesseException e2) {
				System.out.println(e2.getMessage());
			}
			break;
			
			//Mitarbeiter erhoeht bestand
		case "l":
			System.out.print("Artikelbezeichnung :   >");
			String artikelname = liesEingabe();
			//Wieso Liste?
			List<Artikel> gefundeneArtikel = shop.sucheNachBezeichnung(artikelname);
			if(gefundeneArtikel.size() > 1 ) {
				System.out.println("Fehler: mehr als ein Artikel mit dieser Bezeichnung in Liste!");
				return;
			} else {
				System.out.print("Bestand erhoehen um :   >");
				String erhoehung = liesEingabe();
				int erhohung = Integer.parseInt(erhoehung);
				try {
				shop.mitErhoehtArtikel(artikelname, erhohung);
				shop.speicherArtikel();
				shop.speicherEreignis();
				} catch (PackungsgroesseException e){
					System.out.println(e.getMessage());
				}
			}
			break;
		default:
			System.out.println("Der Vorgang wurde abgebrochen. Bitte waehlen Sie eine der vorgegebenen Optionen aus.\n");
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
			System.out.println(e.getMessage());
		}
		System.out.print("Passwort eingeben :   > ");
		try {
			passwort = liesEingabe();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			Kunde kunde = shop.kundenlogIn(username, passwort);
			System.out.println("Hallo "+ kunde.getName()+ "!! Schoen, dass du da bist!");
			userEingeloggt = kunde;
			shop.userEingeloggt(kunde);
		} catch (PasswortOderUsernameFalschException e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}
		System.out.print("Passwort eingeben :   > ");
		try {
			passwort = liesEingabe();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			Mitarbeiter mitarbeiter = shop.mitarbeiterlogIn(username, passwort);
			System.out.println("erfolgreich eingeloggt als "+ mitarbeiter.getName()+ "!!");
			userEingeloggt = mitarbeiter;
			shop.userEingeloggt(mitarbeiter);	
		} catch (PasswortOderUsernameFalschException e) {
			System.out.println(e.getMessage());	
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
		System.out.print("> ");
		int artNummer=0;
		int artAnzahl=0;
		try {
		//setzt artNummer welches vorher 0 ist auf die Nummer die eingegeben wurde
		artNummer = Integer.parseInt(br.readLine());
		System.out.println("Geben Sie die Stueckzahl an: \n");
		System.out.print("> ");
		//setzt artAnzahl welches vorher 0 ist auf die Nummer die eingegeben wurde
		artAnzahl = Integer.parseInt(br.readLine());
		} catch (NumberFormatException exception) {
			System.out.println("Geben Sie eine Zahl ein!!!");
			System.out.print("> ");
			menueWk(br);
		}
		try {
			System.out.println(shop.wkBefuellen((Kunde)userEingeloggt,artNummer, artAnzahl));
		} catch (LagerbestandsException e1) {
			System.out.println(e1.getMessage());
		} catch (PackungsgroesseException e2) {
			System.out.println(e2.getMessage());
		}
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
				System.out.println(e.getMessage());
				gibMenueAus();
			}//Überprüft ob usereingeloggt Objekt aus Klasse Kunde ist
			} else if(userEingeloggt instanceof Kunde) {
				eingeloggterUserMenue();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					eingeloggterUserMenue();
				}
			} else if (userEingeloggt instanceof Mitarbeiter) {
				gibMitarbeiterMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabe(input);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					gibMitarbeiterMenueAus();
				}
			}
			
		} while (!input.equals("q"));
		
	}
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI("SHOP");
			cui.run();
		} catch (IOException e) {	
			System.out.println(e.getMessage());
		}
	}
}