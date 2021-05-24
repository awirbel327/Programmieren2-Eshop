package shop.local.valueobjects;

public class Mitarbeiter {

	private int mitarbeiterNr;
	private String name;
	private String username;
	private String passwort;
	
	//Konstruktor Mitarbeiter anmeldung usw....
	public Mitarbeiter(int nr, String name, String username, String passwort) {
		//mitarbeiterNr = nr;
		this.mitarbeiterNr = mitarbeiterNr;
		this.name = name;
		this.username = username;
		this.passwort = passwort;
		
	}
	
	//Getter/ Setter/ usw Mitarbeiter-Eigenschaften
	public int getMitarbeiterNr() {
		return mitarbeiterNr;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
