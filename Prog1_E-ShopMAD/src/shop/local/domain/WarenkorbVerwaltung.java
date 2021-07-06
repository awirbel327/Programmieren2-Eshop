package shop.local.domain;

import java.util.Vector;
import java.io.IOException;
import java.time.LocalDate;
import shop.local.domain.exceptions.LagerbestandsException;
import shop.local.domain.exceptions.PackungsgroesseException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;

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


	public static Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	private Vector<Artikel> warenkorbVector = new Vector<Artikel>();
	private double wkGesamtpreis;
	private Artikel gesuchterArt;
	public Vector<Artikel> getListe() {
		return warenkorbVector;
	}

	// Methode die pr�ft ob noch genug Artikel auf Lager sind
	public String wkBefuellen(Kunde userEingeloggt, int artNummer, int artAnzahl, ArtikelVerwaltung meineArtikel) throws PackungsgroesseException, LagerbestandsException{
		Artikel gefundenArt;
		Vector<Artikel> artListe = meineArtikel.getArtikelliste();
		String bestaetigung = "Es ist ein Fehler aufgetreten, versuchen Sie es noch mal.";
// 		Die Artikelliste wird nach den gewuenschten Artikel durchsucht.
		for (int i = 0; artListe.size() > i; i++) {
			if (artListe.elementAt(i).getNummer() == artNummer) {
				gefundenArt = artListe.elementAt(i);
//					Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
				if ((gefundenArt.getBestand() >= artAnzahl) == true) {
					if (gefundenArt instanceof Massengutartikel	&& artAnzahl % ((Massengutartikel) gefundenArt).getPackungsgroesse() != 0) {
						throw new PackungsgroesseException((Massengutartikel) gefundenArt, "-inerhoeheEinkauf");
					} else {
					hinzufuegenOderErhoehen(userEingeloggt, gefundenArt, artAnzahl, meineArtikel);
					bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getBezeichnung()
						+ " in der Stueckzahl " + artAnzahl + " befuellt.\n";
					}
				} else {
					throw new LagerbestandsException(gefundenArt);
				}
			}
		}
		return bestaetigung;
	}
	
	// Methode zum Erhöhen der Anzahl des Artikels im WK
		public void erhoeheEinkauf(Kunde kundEingeloggt, int artNummer, int plusBestand, ArtikelVerwaltung meineArtikel) throws LagerbestandsException { 
			Vector<Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
			//suchen im Warenkorb des Kunden
			Artikel ausWkListe = meineArtikel.sucheArtikelinListe(warenkorbFuellung, artNummer);
			//suche in der allgemeinen Artikelliste
			Artikel ausArtliste = meineArtikel.sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);
			if ((ausArtliste.getBestand() - ausWkListe.getBestand()) >= plusBestand) {
				ausWkListe.setBestand(plusBestand + ausWkListe.getBestand()); 
			}
			 else {
				throw new LagerbestandsException(ausArtliste);
			}
	}
	

	// Methode die pr�ft ob der Artikel schon WK liegt & Falls nicht einen neuen Artikel erstellt.
		public void hinzufuegenOderErhoehen(Kunde userEingeloggt, Artikel gefundenArt, int anzahl, ArtikelVerwaltung meineArtikel) throws LagerbestandsException {

			if (wkBestandspruefung(gefundenArt, userEingeloggt) == true) {
				erhoeheEinkauf(userEingeloggt, gefundenArt.getNummer(), anzahl, meineArtikel);

			} else {
				//Wahrscheinlich Unn�tig
				if (gefundenArt instanceof Massengutartikel) {
					gesuchterArt = new Massengutartikel(gefundenArt.getBezeichnung(), anzahl, gefundenArt.getPreis(),((Massengutartikel)gefundenArt).getPackungsgroesse());	
				} else {
					gesuchterArt = new Artikel(gefundenArt.getBezeichnung(), anzahl, gefundenArt.getPreis());
				}
				gesuchterArt.setNummer(gefundenArt.getNummer());
				userEingeloggt.getWk().artikelwkHinzufuegen(gesuchterArt, anzahl);
			}
		}

	// Methode zum Prüfen ob ein Artikel bereits im Warenkorb liegt 
	public boolean wkBestandspruefung(Artikel artikel, Kunde kundEingeloggt) {
		for (int i = 0; kundEingeloggt.getWk().getListe().size() > i; i++) {
			if (kundEingeloggt.getWk().getListe().elementAt(i).getBezeichnung().equals(artikel.getBezeichnung())) {
				return true;
			}
		}
		return false;
	}

	public String warenkorbAusgeben(Kunde userEingeloggt) {
		String inhalt = "Ihr Warenkorb ist leer";
		warenkorbVector = userEingeloggt.getWk().getListe();
		berechneWkGesamt();
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
		wkGesamtpreis = preis;
	}
	
	public String kaufeWarenkorb(Kunde userEingeloggt, ArtikelVerwaltung meineArtikel) throws IOException{
		String bestaetigung = "";
		berechneWkGesamt();
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
						Rechnung rechnung = new Rechnung(warenkorbInhalt,userEingeloggt, wkGesamtpreis ); //userEingeloggt, userEingeloggt.getWk().getWkGesamtpreis()
						bestaetigung = rechnung.toString() + "Gesamtpreis: "+ wkGesamtpreis + "Euro \n\n"; // userEingeloggt.getWk().getWkGesamtpreis() + "Euro \n\n";
					}
				}
		}
		userEingeloggt.getWk().warenkorbLeeren();
		return bestaetigung;	
	}		
}
