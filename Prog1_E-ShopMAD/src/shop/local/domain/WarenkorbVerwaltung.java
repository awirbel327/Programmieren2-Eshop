package shop.local.domain;
/**
 * Klasse zur Verwaltung vom Warenkorb
 * TO DO
 * +Artikelbestand �ndern (Zugriff auf Artikel)
 * +Rechnung erstellen (Rechnungsobjekt) Ausgabe �ber CUI
 * +Artikel hinzuf�gen (Artikelobjekt erstellen)
 * +Menge �ndern
 * @author maust
 * @version 1
 * Verwaltung des Warenkorbs
 */
public class WarenkorbVerwaltung {
	
	//private List<Artikel> warenkorb = new Vector<Artikel>();
	
	
	
	public void emptyCart () {
		
	}
	
	public void buyCart() {
		emptyCart();
		//createBill();
		reduceStock();
	}
	
	public void addItemtoCart(int Anzahl, String Artikel) {
		
		
	}
	
	public void changeAmound () {
	}
	
	//public Bill createBill() {}
	
	public void reduceStock () {
		
	}
	
}
