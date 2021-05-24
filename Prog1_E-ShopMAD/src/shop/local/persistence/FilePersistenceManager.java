package shop.local.persistence;

import shop.local.persistence.PersistenceManager;

import java.util.*;
import java.io.BufferedReader;
import shop.local.valueobjects.*;	// * = importiert alles aus valueobjects
import shop.local.domain.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

//Für Dateien einlesen usw.

public class FilePersistenceManager  implements PersistenceManager  {
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void openForWriting(String datei) throws IOException {	
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}
	
	public boolean close() {
		if(writer != null) {
			writer.close();
		}
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public Kunde ladeKunde() throws IOException {
		String name = liesZeile();
		if (name == null) {
			return null;
		}
		String strasse = liesZeile();
		String hausNrString = liesZeile();
		int hausNr = Integer.parseInt(hausNrString);
		String plzString = liesZeile();
		int plz = Integer.parseInt(plzString);
		String ort = liesZeile();
		String kUsername = liesZeile();
		String kPasswort = liesZeile();
		
		return new Kunde (name, strasse, hausNr, plz, ort, kUsername, kPasswort);
	}
	
	// Kunde wird übergeben und gespeichert
	public boolean speicherKundeDaten(Kunde kunde) throws IOException {
		schreibeZeile(kunde.getName());
		schreibeZeile(kunde.getStrasse());
		schreibeZeile(Integer.toString(kunde.getHausNr()));
		schreibeZeile(Integer.toString(kunde.getPlz()));
		schreibeZeile(kunde.getOrt());
		schreibeZeile(kunde.getUsername());
		schreibeZeile(kunde.getPasswort());
		return true;
		}
	/*
	public Mitarbeiter ladeMitarbeiter() throws IOException {
		String name = liesZeile();
		if (name == null) {
			return null;
		}
		String mitarbeiterNrString = liesZeile();
		int mitarbeiterNr = Integer.parseInt(mitarbeiterNrString);
		String username = liesZeile();
		String passwort = liesZeile();
		
		return new Mitarbeiter(name,nr, username, passwort);
	}*/
	
	public Artikel ladeArtikel() throws IOException {
		String titel = liesZeile();
		if (titel == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// Nummer einlesen ...
		String nummerString = liesZeile();
		// ... und von String in int konvertieren
		int nummer = Integer.parseInt(nummerString);
		
		// Buch ausgeliehen?
		String verfuegbarCode = liesZeile();
		// Codierung des Ausleihstatus in boolean umwandeln
		boolean verfuegbar = verfuegbarCode.equals("t") ? true : false;
		
		// Bestand einlesen ...
				String bestandString = liesZeile();
				// ... und von String in int konvertieren
				int bestand = Integer.parseInt(bestandString);
		
		// neues Buch-Objekt anlegen und zurÃ¼ckgeben
		return new Artikel(titel, nummer, verfuegbar, bestand);
		}
	
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}
	
	// Methode zum Schreiben in Dateien
	private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
        else 
        	System.out.println("Fehler?");
    }
}