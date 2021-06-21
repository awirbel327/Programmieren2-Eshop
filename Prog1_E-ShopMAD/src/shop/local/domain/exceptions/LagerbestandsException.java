package shop.local.domain.exceptions;

import shop.local.valueobjects.*;


public class LagerbestandsException extends Exception {
	
	private Artikel artikel;
	
	public LagerbestandsException (Artikel artikel) {
		super("Leider sind nicht gen�gend Artikel vorr�tig. Sie k�nen h�hstens" + artikel.getBestand() + artikel.getBezeichnung() 
		+ "in ihren Warenkorb legen.");
		this.artikel = artikel;
	}
	
	/*
	public Artikel getArtikel() {
		return artikel;
	}
	*/
}
