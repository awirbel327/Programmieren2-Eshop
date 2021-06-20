package shop.local.domain;

import java.util.Vector;

import shop.local.domain.exceptions.LagerbestandsException;
import shop.local.domain.exceptions.PackungsgroesseException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.domain.Eshop;



/**
 * Klasse zur Verwaltung vom Warenkorb
 * TO DO
 * +Artikelbestand �ndern (Zugriff auf Artikel)
 * +Rechnung erstellen (Rechnungsobjekt) Ausgabe �ber CUI
 * +Artikel hinzuf�gen (Artikelobjekt erstellen)
 * +Menge �ndern
 * @author maust
 * @version 1
 * Verwaltung des Warenkorbs
 */
public class WarenkorbVerwaltung {
	
	//private List<Artikel> warenkorb = new Vector<Artikel>();
//	private ArtikelVerwaltung meineArtikel;
	private Eshop shop;
	
	
	private Vector <Artikel> warenkorbVector = new Vector<Artikel>();
	private double wkGesamtpreis;
	public Vector <Artikel> getListe() {
		return warenkorbVector;
	}
	public WarenkorbVerwaltung(){
	
	}

	//Methode um einen Artikel anhand seiner Nummer in beliebiger Anzahl dem persönlichen WK (des Kunden) hinzuzufügen(inkl. Bestätigung)	
	public String wkBefuellen(Kunde userEingeloggt,  int artNummer, int artAnzahl, ArtikelVerwaltung meineArtikel) throws LagerbestandsException, PackungsgroesseException {
		Vector <Artikel> artListe = meineArtikel.getArtikelBestand();
// 		clone() Methode ????
//		Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
		String bestaetigung = "Es ist ein Fehler aufgetreten, versuchen Sie es noch mal.";
// 		Die Artikelliste wird nach den gewuenschten Artikel durchsucht.
		for(int i = 0 ; artListe.size() > i ; i++) {
			if(artListe.elementAt(i).getNummer() == artNummer) {
				Artikel gefundenArt = artListe.elementAt(i);
//					Hat man den Artikel gefunden, wird geschaut ob man genug auf Lager hat.
				
				
					// If Abfrage wird unnötig, da die Exception schon in der Methode hinzufügenOderErhoehen geprüft wird
					//	if((gefundenArt.getBestand()>= artAnzahl) == true) {
										
						hinzufuegenOderErhoehen(userEingeloggt,gefundenArt, artAnzahl, meineArtikel);
//						String "bestaetigung" wird überschrieben
						bestaetigung = "Sie haben Ihren Warenkorb erfolgreich mit dem Artikel " + gefundenArt.getTitel() + " in der Stueckzahl " + artAnzahl + " befuellt.\n";

						//					} else {
//						throw new LagerbestandsException(gefundenArt);
					}
		}
//		}
		return bestaetigung;
	}	
	
	//Methode zum Erhöhen der Anzahl des Artikels im WK 
		// TODO: Das allermeiste in Artikelverwaltung erledigen
		public void erhoeheEinkauf(Kunde kundEingeloggt,int artNummer, int plusBestand, ArtikelVerwaltung meineArtikel) throws LagerbestandsException, PackungsgroesseException {
//			Kunde unserKunde = (Kunde) meineNutzer.getAngemeldeterUser();
			Vector <Artikel> warenkorbFuellung = kundEingeloggt.getWk().getListe();
			Artikel gesuchterArt = shop.sucheArtikelinListe(warenkorbFuellung, artNummer);
			Artikel ausArtliste = shop.sucheArtikelinListe(meineArtikel.getArtikelliste(), artNummer);
				if((ausArtliste.getBestand() - gesuchterArt.getBestand())>= plusBestand) {
					if (ausArtliste instanceof Massengutartikel && plusBestand % ((Massengutartikel) gesuchterArt).getPackungsgroesse() != 0) {
						throw new PackungsgroesseException((Massengutartikel) gesuchterArt);
					}
						
					gesuchterArt.setBestand(plusBestand + gesuchterArt.getBestand()); //Bestand aus Lager verringern?
				} else {
					throw new LagerbestandsException(ausArtliste);
				}
		}	
		//Methode zum hinzufügen eines Artikels (falls noch nicht im WK) oder Erhöhens seiner Anzahl
		public void hinzufuegenOderErhoehen(Kunde kundEingeloggt,Artikel gefundenArt, int anzahl, ArtikelVerwaltung meineArtikel) throws LagerbestandsException, PackungsgroesseException {
			if(wkBestandspruefung(gefundenArt, kundEingeloggt) == true) {
				//try {
				erhoeheEinkauf(kundEingeloggt,gefundenArt.getNummer(), anzahl, meineArtikel); 
				//} catch (LagerbestandsException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			} else { 
				//HIER IST NOCH EIN FEHLER "ANZAHL" WIRD DER BESTAND GENOMMEN STATT DIE ANZAHL DIE MAN HABEN MÃ–CHTE
				Artikel gesuchterArt = new Artikel (gefundenArt.getTitel(),gefundenArt.getNummer(),gefundenArt.isVerfuegbar(),gefundenArt.getBestand(),gefundenArt.getPreis());
				gesuchterArt.setNummer(gefundenArt.getNummer());
				kundEingeloggt.getWk().artikelwkHinzufuegen(gesuchterArt, anzahl);
			}
		}
		
		//Methode zum Prüfen ob ein Artikel bereits im Warenkorb liegt (würde die Methode vielleicht anders nennen)
		public boolean wkBestandspruefung(Artikel artikel, Kunde kundEingeloggt) {
			for (int i = 0; kundEingeloggt.getWk().getListe().size() > i; i++) {
				if(kundEingeloggt.getWk().getListe().elementAt(i).getTitel().equals(artikel.getTitel())) {
					return true;
				}
			}
			return false;
		}
		public String wkAusgeben(Kunde userEingeloggt) {
			//warenkorb des EINGELOGTEN KUNDEN ausgeben
//	Warenkorb wk = new Warenkorb();
//	 return wk.warenkorbAusgeben();
//	Kunde kundeEingeloggt = (Kunde) UserVerwaltung.getAngemeldeterUser();
	//warenkorb des EINGELOGTEN KUNDEN ??? ausgeben
	return userEingeloggt.getWk().warenkorbAusgeben();
		}
}
