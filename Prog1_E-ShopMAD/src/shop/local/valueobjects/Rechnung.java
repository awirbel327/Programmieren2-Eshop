package shop.local.valueobjects;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

public class Rechnung {
	private Vector <Artikel> rechnungsVector;
	private static String rechnungsNachricht;
	private Kunde user;
	private String userDaten;
	
	
		//Konstruktor Rechnung
	public Rechnung(Vector<Artikel> wkVector, User user, double gesamtpreis) throws IOException{
		rechnungsVector = wkVector;
		this.user = (Kunde) user;
		Date df = new Date();
//		userDaten = "";
		rechnungsNachricht = "\n ZAHLUNGSINFORMATION \n \n Guten Tag " + user.getName() + ", \n \n Vielen Dank für die Bestellung vom: " + df.toString() + " in unserem MAD-SHOP! \n \n HIER IST IHRE RECHNUNG: \n \n " + sammelDaten();
		rechnungArchivieren();
	}
	
	//Methode um Daten zu sammeln und sie zurück zugeben
		public String sammelDaten() throws IOException {
			String gesammelteDaten = "";
			
			for (int i = 0; rechnungsVector.size() > i; i++) {
				gesammelteDaten += "Artikelname: " + rechnungsVector.elementAt(i).getBezeichnung() + "\n Artikelanzahl: " + rechnungsVector.elementAt(i).getBestand()+ "\n Preis pro Artikel: " + rechnungsVector.elementAt(i).getPreis() + " Euro" + "\n Artikel gesamtpreis: " + rechnungsVector.elementAt(i).getArtikelpreisBerechnen() + "\n\n";
				
			}
			return gesammelteDaten;
		}
		

		public void rechnungArchivieren() throws IOException {
			FileWriter fw = new FileWriter("Rechnung.txt", true);
			Date df = new Date();
			fw.write("\nRechnung:\n" + "Datum: " + df.toString() + "\n" + rechnungsNachricht);
			fw.close();
		}
	
	public String toString() {
		return rechnungsNachricht;
	}
	
}
