package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Massengutartikel;

public class PackungsgroesseException extends Exception {

	
	public PackungsgroesseException (Massengutartikel massengutartikel, String zusatzMsg) {
		super("Dieser Artikel kann nur in Packungseinheiten der Groesse " + massengutartikel.getPackungsgroesse() + " eingefuegt werden. ");
	}
	
}
