package shop.local.valueobjects;


/**
 * Abgeleitet von der Artikelklasse
 * 
 * @author Mareike
 *
 */
public class Massengutartikel extends Artikel {

	
	
	private int packungsgroesse;
	
	
	public Massengutartikel(String bezeichnung, int nr, int bestand, double artikelPreis, int packungsgroesse) {
		super(bezeichnung, nr, bestand, artikelPreis);
		this.packungsgroesse = packungsgroesse;
	}

//	public Massengutartikel (String bezeichnung,int nr, int bestand, double artikelPreis, int packungsgroesse) {
//		super(bezeichnung, nr, bestand, artikelPreis);
//		this.packungsgroesse = packungsgroesse;
//	}
	
	
	public int getPackungsgroesse() {
		return packungsgroesse;
	}
	
	
	@Override	//https://stackoverflow.com/questions/18895915/how-to-sort-an-array-of-objects-in-java
	public int compareTo(Artikel o) {
		return toString().compareTo(o.toString());		// zu String und vergelciht mit Artikel "o"
	}
}
