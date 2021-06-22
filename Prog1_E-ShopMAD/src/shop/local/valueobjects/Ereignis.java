package shop.local.valueobjects;

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
		//this.datum = datum;
		this.anzahl = anzahl;
		this.aktion = aktion;
		
		//DATUM NOCH AUTOMATISCH ERSTELLEN 
	}
	
	//getterundsetter?
}
