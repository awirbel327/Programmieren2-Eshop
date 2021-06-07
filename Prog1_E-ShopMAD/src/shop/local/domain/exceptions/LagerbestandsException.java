package shop.local.domain.exceptions;

import shop.local.valueobjects.*;


public class LagerbestandsException extends Exception {
	
	private Artikel artikel;
	
	public LagerbestandsException (Artikel artikel) {
		super("Leider sind nicht genügend Artikel vorrätig. Sie könen höhstens" + artikel.getBestand() + artikel.getTitel() 
		+ "in ihren Warenkorb legen.");
		this.artikel = artikel;
	}
	
	public Artikel getArtikel() {
		return artikel;
	}

}
