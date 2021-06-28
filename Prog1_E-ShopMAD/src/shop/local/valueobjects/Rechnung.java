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
		userDaten = "Name blabla " +"<br>"+"<br>"+"<br>"+"<br>";
		rechnungsNachricht = userDaten +   "Sehr geehrte/r " + user.getName() + ", <br> \nHier ist Ihre Rechnung: \n<br><br>" + sammelDaten();
		rechnungArchivieren();
	}
	
	//Methode um Daten zu sammeln und sie zurück zugeben
		public String sammelDaten() throws IOException {
			String gesammelteDaten = "";
			
			for (int i = 0; rechnungsVector.size() > i; i++) {
				gesammelteDaten += "Artikelname: " + rechnungsVector.elementAt(i).getBezeichnung() + ",<br> Artikelnummer: " + rechnungsVector.elementAt(i).getNummer()+ ",<br> Artikelanzahl: " + rechnungsVector.elementAt(i).getBestand()+ ",<br> Preis pro Artikel: " + rechnungsVector.elementAt(i).getPreis() + " Euro" + ",<br> Artikel gesamt: " + rechnungsVector.elementAt(i).getArtikelpreisBerechnen() + "\n\n<br><br>";
				
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
