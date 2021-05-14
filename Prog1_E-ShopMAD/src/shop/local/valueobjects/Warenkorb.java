package shop.local.valueobjects;

import java.util.Vector;
import shop.local.valueobjects.Artikel;
import shop.local.domain.Eshop;

public class Warenkorb {
	private Vector <Artikel> warenkorbVector = new Vector<Artikel>();
	
	public String warenkorbAusgeben() {
		String inhalt=" Ihr Warenkorb ist leer";
//		
//		Warenkorb mit "inhalt" befüllen
//		if abfrage Vector.size > 0
//		inhalt = "in ihrem Warenkorb befindet sich folgendes"
//		 schleife Vector i++
//		 Artikel artikel = Warenkorb Vector.elementAt(i)
//		 inhalt = Artikelnummer: + artikel.getNummer().		
//		
//			}
//		}
		return inhalt;
	}
}