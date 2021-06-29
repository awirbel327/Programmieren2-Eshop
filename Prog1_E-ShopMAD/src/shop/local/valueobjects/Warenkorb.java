package shop.local.valueobjects;

import java.util.Vector;
import shop.local.domain.*;
public class Warenkorb {
	
	private Vector <Artikel> warenkorbVector;
	private WarenkorbVerwaltung wkverwaltung;
	
	public Warenkorb () {
		 this.warenkorbVector = new Vector<Artikel>();
	}
	
	public Vector <Artikel> getListe() {
		return warenkorbVector;
	}
	


//	public String wkAusgeben(Kunde userEingeloggt) {
//		// warenkorb des EINGELOGTEN KUNDEN ausgeben
//		//	Warenkorb wk = new Warenkorb();
//		//	 return wk.warenkorbAusgeben();
//		//	Kunde kundeEingeloggt = (Kunde) UserVerwaltung.getAngemeldeterUser();
//		// warenkorb des EINGELOGTEN KUNDEN ??? ausgeben
//		return userEingeloggt.getWk().warenkorbAusgeben();
//	}

	
	public void artikelwkHinzufuegen(Artikel artikel, int anzahl) {
		if (anzahl > 0) {
			warenkorbVector.add(artikel);
//			berechneWkGesamt();
		}
	}
	public double getWkGesamtpreis() {
		return 22.0;

	}
	public void warenkorbLeeren() {
		warenkorbVector.clear();
//		wkGesamtpreis = 0;
	}
}

	
//	public String warenkorbAusgeben() {
//		String inhalt = "Ihr Warenkorb ist leer";
////			berechneGesamtsumme();
//		if (warenkorbVector.size() > 0) {
//			inhalt = "In Ihrem Warenkorb befinden sich folgende Artikel: \n";
//			for (int i = 0; warenkorbVector.size() > i; i++) {
//				Artikel artikel = warenkorbVector.elementAt(i);
//				inhalt += "Artikelnummer: " + artikel.getNummer() + "\nArtikelbezeichnung: " + artikel.getBezeichnung()
//						+ "\nPreis pro Stueck: " + artikel.getPreis() + "\nAnzahl: " + artikel.getBestand()
//						+ "\nArtikel Preis gesamt: " + "\n\n" + "Gesamtpreis: " + " Euro\n\n";
//			}
//		}
//		return inhalt;
//	}



