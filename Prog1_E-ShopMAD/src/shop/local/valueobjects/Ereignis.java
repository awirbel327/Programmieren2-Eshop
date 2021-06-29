package shop.local.valueobjects;

import java.io.FileWriter;
import java.util.Date;

public class Ereignis {

	private String kUserId;
	private int userId;
	private String artikelbezeichnung;
	private String datum;
	private int anzahl;
	private String aktion;
	
	
	public Ereignis(String kUserId, int userId,String artikelbezeichnung, int anzahl, String aktion ) {
		this.kUserId = kUserId;
		this.userId =userId;
		this.artikelbezeichnung = artikelbezeichnung;
		this.anzahl = anzahl;
		this.aktion = aktion;
		
		//DATUM NOCH AUTOMATISCH ERSTELLEN 
		/*FileWriter fw = new FileWriter("Rechnung.txt", true);
			Date df = new Date();
			fw.write("\nRechnung:\n" + "Datum: " + df.toString() + "\n" + rechnungsNachricht);
			fw.close();*/
	}
	
	//getterundsetter
	public String getKUserId() {
		return kUserId;
	}

	
	public int getUserId() {
		return userId;
	}
	
	public String getArtikelbezeichnung() {
		return artikelbezeichnung;
	}
	
	public String getAktion() {
		return aktion;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
}
