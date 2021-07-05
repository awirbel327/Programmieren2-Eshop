package shop.local.valueobjects;

public class Mitarbeiter extends User {

	private String name;
	private int mitarbeiterNr;
	private String username;
	private String passwort;
	
	//Konstruktor Mitarbeiter anmeldung usw....
	public Mitarbeiter(String name, int mitarbeiterNr, String username, String passwort) {
		super(name, username, passwort);
		this.name = name;
		this.mitarbeiterNr = mitarbeiterNr;
		this.username = username;
		this.passwort = passwort;
	}
	
	//Konstruktor 2 Registrieren Mitarbeiter
	public Mitarbeiter(String name, String username, String passwort) {
		super(name, username, passwort);
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
	
	public void setMitarbeiterNr(int mitarbeiterNr) {
		this.mitarbeiterNr = mitarbeiterNr;
	}
}
