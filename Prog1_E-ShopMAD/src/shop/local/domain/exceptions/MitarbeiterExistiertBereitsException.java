package shop.local.domain.exceptions;

import shop.local.valueobjects.*;

public class MitarbeiterExistiertBereitsException extends Exception {

	private Mitarbeiter mitarbeiter;
	
	public MitarbeiterExistiertBereitsException(Mitarbeiter mitarbeiter, String zusatzMsg) {
		super("Mitarbeiter mit Nummer: " + mitarbeiter.getMitarbeiterNr() + " existiert bereits" + zusatzMsg);
		this.mitarbeiter = mitarbeiter;
	}

	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter;
	}
}