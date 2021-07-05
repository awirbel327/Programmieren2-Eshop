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
		
		Date df = new Date();
		this.datum = df.toString();
	}
	
	//Einlese-Konstruktor
	public Ereignis(String kUserId, int userId,String artikelbezeichnung, int anzahl, String aktion, String datum ) {
		this.kUserId = kUserId;
		this.userId =userId;
		this.artikelbezeichnung = artikelbezeichnung;
		this.anzahl = anzahl;
		this.aktion = aktion;
		this.datum = datum;
	}
	
	//getter
	
	public String getKUserId() {
		return kUserId;
	}
	
	public String getDatum() {
		return datum;
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
