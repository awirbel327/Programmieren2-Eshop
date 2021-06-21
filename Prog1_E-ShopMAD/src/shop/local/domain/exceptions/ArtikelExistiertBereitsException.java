package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {

	private Artikel artikel;
	
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Buch mit Titel " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer() 
				+ " existiert bereits" + zusatzMsg);
		this.artikel = artikel;
	}

	public Artikel getArtikel() {
		return artikel;
	}
}