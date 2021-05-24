package shop.local.domain.exceptions;

import shop.local.valueobjects.*;

public class KundeExistiertBereitsException extends Exception {

	private Kunde kunde;
	
	public KundeExistiertBereitsException(Kunde kunde, String zusatzMsg) {
		super("Kunde existiert bereits");
		this.kunde = kunde;
	}

	public Kunde getKunde() {
		return kunde;
	}
}