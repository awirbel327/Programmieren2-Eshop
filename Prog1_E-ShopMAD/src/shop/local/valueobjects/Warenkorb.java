package shop.local.valueobjects;

import java.util.Vector;
import shop.local.valueobjects.Artikel;
import shop.local.domain.Eshop;

public class Warenkorb {
	private Vector <Artikel> warenkorbVector = new Vector<Artikel>();
	

	
	public Vector <Artikel> getListe() {
		return warenkorbVector;
	}
	
	public void artikelwkHinzufuegen(Artikel artikel, int anzahl) {
		if (anzahl > 0) {
		warenkorbVector.add(artikel);
//		berechneWkGesamt();
		}
	}
	
	public String warenkorbAusgeben() {
		String inhalt="Ihr Warenkorb ist leer";
//		berechneGesamtsumme();
		if (warenkorbVector.size() > 0) {
			inhalt = "In Ihrem Warenkorb befinden sich folgende Artikel: \n";
			for(int i=0;warenkorbVector.size() > i; i++) {
				Artikel artikel = warenkorbVector.elementAt(i);
				inhalt += "Artikelnummer: " + artikel.getNummer() + "\nArtikelbezeichnung: " + artikel.getTitel() +"\nPreis pro Stueck: " + artikel.getPreis() + "\nAnzahl: " + artikel.getBestand() + "\nArtikel Preis gesamt: "  + "\n\n" + "Gesamtpreis: " +  " Euro\n\n" ;
			}
		}
		return inhalt;
	}
}