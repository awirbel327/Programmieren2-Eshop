package shop.local.domain.exceptions;

import shop.local.valueobjects.*;


public class LagerbestandsException extends Exception {
	

	public LagerbestandsException (Artikel artikel) {
		super("Leider sind nicht genuegend Artikel vorraetig. Sie koenen hoestens " + artikel.getBestand() + " " + artikel.getBezeichnung() 
		+ " in ihren Warenkorb legen.");
	}
	
}
