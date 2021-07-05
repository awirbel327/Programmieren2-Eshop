package shop.local.valueobjects;

public class User {

	private String name;
	private String username;	
	private String passwort;
	
	public User(String name, String username, String passwort) {
		this.name = name;
		this.username = username;
		this.passwort = passwort;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
}
