package shop.local.domain;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Kunde;

public class UserVerwaltung {
	//public static Vector <User> userListe = new Vector<User>();
	//public User angemeldeterUser;
	
	/********KUNDEN und MITARBEITER********/
	/*public User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	public void setAngemeldeterUser() {
		angemeldeterUser = user;
	}*/
	
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
