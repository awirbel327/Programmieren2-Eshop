package shop.local.valueobjects;

import java.util.Vector;
public class Warenkorb {
	
	private Vector <Artikel> warenkorbVector;
	
	public Warenkorb () {
		 this.warenkorbVector = new Vector<Artikel>();
	}
	
	public Vector <Artikel> getListe() {
		return warenkorbVector;
	}
	
	public void artikelwkHinzufuegen(Artikel artikel, int anzahl) {
		if (anzahl > 0) {
			warenkorbVector.add(artikel);
		}
	}

	public void warenkorbLeeren() {
		warenkorbVector.clear();
//		wkGesamtpreis = 0;
	}
}
