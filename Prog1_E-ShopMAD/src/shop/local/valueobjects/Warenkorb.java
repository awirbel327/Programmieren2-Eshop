package shop.local.valueobjects;

import java.util.Vector;
import shop.local.domain.*;
public class Warenkorb {
	
	private Vector <Artikel> warenkorbVector;
	private WarenkorbVerwaltung wkverwaltung;
	private double wkGesamtpreis;
	
	
	public Warenkorb () {
		 this.warenkorbVector = new Vector<Artikel>();
	}
	
	public Vector <Artikel> getListe() {
		return warenkorbVector;
	}
	
	public void artikelwkHinzufuegen(Artikel artikel, int anzahl) {
		if (anzahl > 0) {
			warenkorbVector.add(artikel);
//			berechneWkGesamt();
		}
	}
	public double getWkGesamtpreis() {
	return 22;

	}
	
	
	public void warenkorbLeeren() {
		warenkorbVector.clear();
//		wkGesamtpreis = 0;
	}
}
