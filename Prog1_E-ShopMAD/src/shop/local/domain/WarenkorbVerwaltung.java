package shop.local.domain;

import java.util.Vector;
import java.io.IOException;
import java.time.LocalDate;
import shop.local.domain.exceptions.LagerbestandsException;
import shop.local.domain.exceptions.PackungsgroesseException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.domain.Eshop;
import shop.local.valueobjects.Ereignis;
import shop.local.valueobjects.Rechnung;

/**
 * Klasse zur Verwaltung vom Warenkorb TO DO +Artikelbestand �ndern (Zugriff
 * auf Artikel) +Rechnung erstellen (Rechnungsobjekt) Ausgabe �ber CUI
 * +Artikel hinzuf�gen (Artikelobjekt erstellen) +Menge �ndern
 * 
 * @author maust
 * @version 1 Verwaltung des Warenkorbs
 */
public class WarenkorbVerwaltung {

	// private List<Artikel> warenkorb = new Vector<Artikel>();
//	private ArtikelVerwaltung meineArtikel;
	private Eshop shop;

	private Vector<Artikel> warenkorbVector = new Vector<Artikel>();
	private double wkGesamtpreis;

	public Vector<Artikel> getListe() {
		return warenkorbVector;
	}

	// ??
	public WarenkorbVerwaltung() {

	}

	// Methode um einen Artikel anhand seiner Nummer in beliebiger Anzahl dem persönlichen WK (des Kunden) hinzuzufügen(inkl. Bestätigung)
	public String wkBefuellen(Kunde userEingeloggt, int artNummer, int artAnzahl, ArtikelVerwaltung meineArtikel) {
//		throws LagerbestandsException, PackungsgroesseException 
		Vector<Artikel> artListe = meineArtikel.getArtikelliste();
// 		clone() Methode ????
//		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
		String bestaetigung = "Es ist ein Fehler aufgetreten, versuchen Sie es noch mal.";
// 		Die Artikelliste wird nach den gewuenschten Artikel durchsucht.
		for (int i = 0; artListe.size() > i; i++) {
			if (artListe.elementAt(i).getNummer() == artNummer) {
				Artikel gefundenArt = artListe.elementAt(i);
//					Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
				if ((gefundenArt.getBestand() >= artAnzahl) == true) {
				hinzufuegenOderErhoehen(userEingeloggt, gefundenArt, artAnzahl, meineArtikel);
				bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getBezeichnung()
						+ " in der Stueckzahl " + artAnzahl + " befuellt.\n";

				// } else {
//						throw new LagerbestandsException(gefundenArt);
				}else {
				}
			}
			
		}
//		}
		return bestaetigung;
//		System.out.println(bestaetigung);
	}

	// Methode zum Erhöhen der Anzahl des Artikels im WK
		public void erhoeheEinkauf(Kunde kundEingeloggt, int artNummer, int plusBestand, ArtikelVerwaltung meineArtikel) {// throws
																															// LagerbestandsException,
																															// PackungsgroesseException
//			Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
			Vector<Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
			//suchen im Warenkorb des Kunden
			Artikel ausWkListe = shop.sucheArtikelinListe(warenkorbFuellung, artNummer);
			//suche in der allgemeinen Artikelliste
			Artikel ausArtliste = shop.sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);

			if ((ausArtliste.getBestand() - ausWkListe.getBestand()) >= plusBestand) {
//				if (ausArtliste instanceof Massengutartikel
//						&& plusBestand % ((Massengutartikel) ausWkListe).getPackungsgroesse() != 0) {
//					// throw new PackungsgroesseException((Massengutartikel) gesuchterArt, "-in
//			
				// erhoeheEinkauf");
				ausWkListe.setBestand(plusBestand + ausWkListe.getBestand()); // Bestand aus Lager verringern?
				}

			
			 else {
				System.out.println("BLABLABLA HIHIHI LAGERBESTANDEXCEPTION");
				// throw new LagerbestandsException(ausArtliste);
			}

	}
	
//	// Methode zum Erhöhen der Anzahl des Artikels im WK
//	public void erhoeheEinkauf(Kunde kundEingeloggt, int artNummer, int plusBestand, ArtikelVerwaltung meineArtikel) {// throws
//																														// LagerbestandsException,
//																														// PackungsgroesseException
////		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
//		Vector<Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
//		//suchen im Warenkorb des Kunden
//		Artikel ausWkListe = shop.sucheArtikelinListe(warenkorbFuellung, artNummer);
//		//suche in der allgemeinen Artikelliste
//		Artikel ausArtliste = shop.sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);
//
//		if ((ausArtliste.getBestand() - ausWkListe.getBestand()) >= plusBestand) {
//			if (ausArtliste instanceof Massengutartikel
//					&& plusBestand % ((Massengutartikel) ausWkListe).getPackungsgroesse() != 0) {
//				// throw new PackungsgroesseException((Massengutartikel) gesuchterArt, "-in
//				// erhoeheEinkauf");
//			}
//
//			ausWkListe.setBestand(plusBestand + ausWkListe.getBestand()); // Bestand aus Lager verringern?
//		} else {
//			// throw new LagerbestandsException(ausArtliste);
//		}
//
//		String bestaetigung = "Der Artikel befindet sich nicht im Warenkorb";
//		if (ausArtliste.getBestand() >= plusBestand) {
//			ausWkListe.setBestand(plusBestand + ausWkListe.getBestand());
//			bestaetigung = "Sie haben den Bestand erhöht";
//		} else {
//			bestaetigung = "hat nicht geklappt";
//		}
//
//	}
/*
		if((ausArtliste.getBestand() - gesuchterArt.getBestand())>= plusBestand) {
			if (ausArtliste instanceof Massengutartikel && plusBestand % ((Massengutartikel) gesuchterArt).getPackungsgroesse() != 0) {
			throw new PackungsgroesseException((Massengutartikel) gesuchterArt);
			}
			gesuchterArt.setBestand(plusBestand + gesuchterArt.getBestand()); //Bestand aus Lager verringern?
			} else {
				throw new LagerbestandsException(ausArtliste);
			}
*/
	
	// Methode zum hinzufügen eines Artikels (falls noch nicht im WK) oder
	// Erhöhens seiner Anzahl
		public void hinzufuegenOderErhoehen(Kunde userEingeloggt, Artikel gefundenArt, int anzahl, ArtikelVerwaltung meineArtikel) {
			if (wkBestandspruefung(gefundenArt, userEingeloggt) == true) {
//					try {
				erhoeheEinkauf(userEingeloggt, gefundenArt.getNummer(), anzahl, meineArtikel);

			} else {
				// HIER IST NOCH EIN FEHLER "ANZAHL" WIRD DER BESTAND GENOMMEN STATT DIE ANZAHL
				// DIE MAN HABEN MÃ–CHTE
				Artikel gesuchterArt = new Artikel(gefundenArt.getBezeichnung(), gefundenArt.getNummer(), anzahl, gefundenArt.getPreis());

				gesuchterArt.setNummer(gefundenArt.getNummer());
				userEingeloggt.getWk().artikelwkHinzufuegen(gesuchterArt, anzahl);
			}
		}

	// Methode zum Prüfen ob ein Artikel bereits im Warenkorb liegt (würde die
	// Methode vielleicht anders nennen)
	public boolean wkBestandspruefung(Artikel artikel, Kunde kundEingeloggt) {
		for (int i = 0; kundEingeloggt.getWk().getListe().size() > i; i++) {
			if (kundEingeloggt.getWk().getListe().elementAt(i).getBezeichnung().equals(artikel.getBezeichnung())) {
				return true;
			}
		}
		return false;
	}

//	public String wkAusgeben(Kunde userEingeloggt) {
//		// warenkorb des EINGELOGTEN KUNDEN ausgeben
//		//	Warenkorb wk = new Warenkorb();
//		//	 return wk.warenkorbAusgeben();
//		//	Kunde kundeEingeloggt = (Kunde) UserVerwaltung.getAngemeldeterUser();
//		// warenkorb des EINGELOGTEN KUNDEN ??? ausgeben
//		return userEingeloggt.getWk().warenkorbAusgeben();
//	}
	
//	public void artikelwkHinzufuegen(Artikel artikel, int anzahl) {
//		if (anzahl > 0) {
//			warenkorbVector.add(artikel);
////			berechneWkGesamt();
//		}
//	}

	public String warenkorbAusgeben(Kunde userEingeloggt) {
		String inhalt = "Ihr Warenkorb ist leer";
		berechneWkGesamt();
		warenkorbVector = userEingeloggt.getWk().getListe();
		if (userEingeloggt.getWk().getListe().size() > 0) {
			inhalt = "In Ihrem Warenkorb befinden sich folgende Artikel: \n";
			for (int i = 0; warenkorbVector.size() > i; i++) {
				Artikel artikel = warenkorbVector.elementAt(i);			
				inhalt += "Artikelnummer: " + artikel.getNummer() + "\nArtikelbezeichnung: " + artikel.getBezeichnung() 
				+ "\nPreis pro Stueck: " + artikel.getPreis() + " € "+"\nAnzahl: " + artikel.getBestand() 
				+ "\nArtikel Preis gesamt:  " + artikel.getArtikelpreisBerechnen()+ " Euro "+ "\n\n" ;
			}
		}
		return inhalt + "\n\n"+ "Gesamtpreis: "+ wkGesamtpreis + " Euro\n\n";
		
	}
	public void berechneWkGesamt() {
		double preis = 0;
		for(int i = 0; warenkorbVector.size() > i; i++) {
			preis += warenkorbVector.elementAt(i).getArtikelpreisBerechnen();
		}
		preis = wkGesamtpreis;
	
	}
	public String kaufeWarenkorb(Kunde userEingeloggt, ArtikelVerwaltung meineArtikel) throws IOException{
		String bestaetigung = "";
//		Kunde unserKunde = (Kunde) userverwaltung.getAngemeldeterUser();
		Vector <Artikel> warenkorbInhalt = userEingeloggt.getWk().getListe();
		for(Artikel a: warenkorbInhalt) {
			int nummer = a.getNummer();
			int bestand = a.getBestand();
			Vector <Artikel> artikelliste = meineArtikel.getArtikelliste();
				for(Artikel l: artikelliste) {
					if(l.getNummer() == nummer) {
						int aktuellerBestand = l.getBestand();
						l.setBestand(aktuellerBestand - bestand);
						meineArtikel.aendereBestandDurchKauf(l.getBezeichnung(), aktuellerBestand, l.getBestand(),userEingeloggt.getUsername()); 
						LocalDate currentDate = LocalDate.now();
//						Ereignis kaufEreignis = new Ereignis(currentDate, l.getBezeichnung(), l.getBestand(), userEingeloggt.getUsername());
//						vecEreignisse.add(kaufEreignis);
//						artikelverwaltung.artVEreignis(vecEreignisse);
						Rechnung rechnung = new Rechnung(warenkorbInhalt, userEingeloggt, userEingeloggt.getWk().getWkGesamtpreis());
						bestaetigung = rechnung.toString() + "Gesamtpreis: "+ userEingeloggt.getWk().getWkGesamtpreis() + "Euro \n\n";
					}
				}
		}
		
		userEingeloggt.getWk().warenkorbLeeren();
		return bestaetigung;
		
	}
}
