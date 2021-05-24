package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;
import shop.local.valueobjects.Warenkorb;
import shop.local.domain.exceptions.*;
import java.util.Collections;
import shop.local.domain.Eshop;
import shop.local.ui.cui.ShopClientCUI;
import shop.local.valueobjects.*;
import shop.local.domain.*;

public class ShopClientCUI {
	
	private Eshop shop;
	private BufferedReader in;
	private Kunde kundeEingeloggt;
	private Mitarbeiter mitarbeiterEingeloggt;
	
	
	public ShopClientCUI(String datei) throws IOException {
		
		shop = new Eshop(datei);
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	private void gibMenueAus() {
		System.out.print("Befehle: \n  Einloggen:  '0'");
		System.out.print("	       \n  Registrieren:  '1'");
		System.out.print("		   \n  Artikel ausgeben:  'A'");
		System.out.print("         \n  Artikel nach Bezeichnung ausgeben 'A1'");
		System.out.print("         \n  Artikel nach Nummer ausgeben 'A2'");
		System.out.print("         \n  Artikel suchen  'B'");
		System.out.print("         \n  Artikel zum WK hinzufügen: 'C'");
		System.out.print("         \n  Warenkorb anzeigen:  'D'");
		System.out.print("         \n  Warenkorb bearbeiten:  'E'");
		System.out.print("         \n  Bezahlen:  'F'");
		System.out.print("         \n  ---------------------");
		System.out.println("       \n  Beenden:        'Q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	private String liesEingabe() throws IOException{
		return in.readLine();
	}
	
	private void verarbeiteEingabe(String line) throws IOException {
//		String nummer;
//		int nr;
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
		case "0":
			System.out.print("Als Kunde anmelden(j/n) :   > ");
			auswahl = liesEingabe();
			if(auswahl.equals("j")) {
				kundenlogin();
			} /*else {
				mitarbeiterlogin();
			}*/
			break;
		case "1":
			System.out.println("Als Kunde registrieren :   >");
			System.out.print("Vollst�ndiger Name :   > ");
			name = liesEingabe();
			System.out.print("Stra�enname :   > ");
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
			//shop.kundenRegistrieren(einKunde);
			break;
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "b":
			System.out.print("Welchen Artikel suchen Sie? :   > ");
			titel = liesEingabe();
			liste = shop.sucheNachTitel(titel);
			gibArtikellisteAus(liste);
			break;
		case "a1" :
			shop.artikelsortiertAusgebenBezeichnung();
			break;
		case "a2" :
			shop.artikelsortiertAusgebenNummer();
			break;
		case "c":
			menueWk(br);
			break;
		case "d":
			System.out.println(""+shop.wkAusgeben());
//			gibMenueAus();
			break;

		default:
			System.out.println("Ungueltige Eingabe!\n");
//			gibMenueAus();
		}
	}
	
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
		kundeEingeloggt = kunde;		
	}
		
	
	/*private void mitarbeiterlogin() {
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
		mitarbeiterEingeloggt = mitarbeiter;		
	}*/
	
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
	
	public void menueWk(BufferedReader br) throws NumberFormatException, IOException {
		System.out.println("Geben Sie die Artikelnummer ein: \n");
		System.out.print("> ");
		int artNummer = Integer.parseInt(br.readLine());
		System.out.println("Geben Sie die Stückzahl an: \n");
		System.out.print("> ");
		int artAnzahl = Integer.parseInt(br.readLine());
		System.out.println(shop.artikelZumWarenkorb(artNummer, artAnzahl));
//		hauptmenueKunde();
	}
	
	/**
	 * Methode zur Ausführung der Hauptschleife:
	 * - Menü ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 */
	public void run() {
		// Variable für Eingaben von der Konsole
		String input = ""; 
	
		// Hauptschleife der Benutzungsschnittstelle
		do {
			gibMenueAus();
			try {
				input = liesEingabe();
				verarbeiteEingabe(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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