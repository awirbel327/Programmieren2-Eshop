package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Massengutartikel;

public class PackungsgroesseException extends Exception {
	
	private Massengutartikel massengutartikel;
	
	public PackungsgroesseException (Massengutartikel massengutartikel, String zusatzMsg) {
		super("Dieser Artikel kann nur in Packungseinheiten der Gr��e " + massengutartikel.getPackungsgroesse() + " eingef�gt werden. ");
		this.massengutartikel = massengutartikel;
	}
	
	/*
	public Massengutartikel getArtikel() {
		return massengutartikel;
	}
	*/
	
}
