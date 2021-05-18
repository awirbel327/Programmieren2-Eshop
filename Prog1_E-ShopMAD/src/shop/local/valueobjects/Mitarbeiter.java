package shop.local.valueobjects;

public class Mitarbeiter {

	private int mitarbeiterNr;
	private String name;
	
	
	public Mitarbeiter(int nr, String name) {
		mitarbeiterNr = nr;
		this.name = name;
	}
	
	//Getter/ Setter/ usw Mitarbeiter-Eigenschaften
	public int getMitarbeiterNr() {
		return mitarbeiterNr;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
