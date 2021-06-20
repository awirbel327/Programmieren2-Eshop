package shop.local.valueobjects;


/**
 * Abgeleitet von der Artikelklasse
 * 
 * @author Mareike
 *
 */
public class Massengutartikel extends Artikel {

	
	
	private int packungsgroesse;
	
	
	public Massengutartikel(String titel, int nr, boolean verfuegbar, int bestand, double artikelPreis, int packungsgroesse) {
		super(titel, nr, verfuegbar, bestand, artikelPreis);
		this.packungsgroesse = packungsgroesse;
	}

	public Massengutartikel (String titel, int bestand2, double artikelPreis2, int packungsgroesse) {
		super(titel, bestand2, artikelPreis2);
		this.packungsgroesse = packungsgroesse;
	}
	
	
	public int getPackungsgroesse() {
		return packungsgroesse;
	}
	
	
	@Override	//https://stackoverflow.com/questions/18895915/how-to-sort-an-array-of-objects-in-java
	public int compareTo(Artikel o) {
		return toString().compareTo(o.toString());		// zu String und vergelciht mit Artikel "o"
	}
}
