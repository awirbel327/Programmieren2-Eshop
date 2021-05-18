package shop.local.domain;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Kunde;

public class UserVerwaltung {
	
	
	/********Methoden für KUNDEN und MITARBEITER********/

	
	public void logIn (String Benutzererkennung, String Passwort) {
	}
	
	public void register (String name, double number) {
	}
	
	/******** Methoden für MITARBEITER ********/
	
	public void newArticle (String articleName, int stock) {
	}
	
	public void increaseStock (Artikel artikel, int increasedStock) {
		
	}
	
	/********Methoden für KUNDEN********/
	
	
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
	
	
	public void reduceStock () {
	}
}
