package shop.local.domain;

import java.io.IOException;
import java.util.Vector;

import shop.local.domain.exceptions.*;
import shop.local.persistence.*;
import shop.local.valueobjects.*;


public class UserVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Kunde> kundenListe = new Vector<Kunde>();
	
	public static Vector <Kunde> mitarbeiterListe = new Vector<Kunde>();
	//public User angemeldeterUser;
	
	/********KUNDEN und MITARBEITER********/
	/*public User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	public void setAngemeldeterUser() {
		angemeldeterUser = user;
	}*/

	
	public void liesKunden(String datei) throws IOException {
		pm.openForReading(datei); // PersistenzManager fÃ¼r LesevorgÃ¤nge Ã¶ffnen

		Kunde einKunde;
		do {
			einKunde = pm.ladeKunde();
			if (einKunde != null) {
				try {
					kundeEinfuegen(einKunde);
				} catch (KundeExistiertBereitsException e1) {
				}
			}
		} while (einKunde != null);
		pm.close();
	}
	
	public void kundeEinfuegen(Kunde einKunde) throws KundeExistiertBereitsException {
		if (kundenListe.contains(einKunde)) {
			throw new KundeExistiertBereitsException(einKunde, " - in 'einfuegen()'");
		}

		kundenListe.add(einKunde);
	}
	
	public Kunde kundenlogIn (String username, String passwort) {
		for (Kunde kunde:kundenListe) {
			if(username == kunde.getUsername()) {
				if(passwort == kunde.getPasswort()) {
					return kunde;
				}
			}
		}
		return null;
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
