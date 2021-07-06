package shop.local.valueobjects;


/**
 * Abgeleitet von der Artikelklasse 
 * @author Mareike
 *
 */
public class Massengutartikel extends Artikel {

	private int packungsgroesse;
	
	public Massengutartikel(String bezeichnung, int bestand, double artikelPreis, int packungsgroesse) {
		super(bezeichnung, bestand, artikelPreis);
		this.packungsgroesse = packungsgroesse;
	}	
	
	public int getPackungsgroesse() {
		return packungsgroesse;
	}
}
