package shop.local.persistence;

import shop.local.persistence.PersistenceManager;

import java.util.*;
import java.io.BufferedReader;
import shop.local.valueobjects.Artikel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FilePersistenceManager  implements PersistenceManager  {
	private BufferedReader reader = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		
	}
	
	public boolean close() {
		return true;
	}

	
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
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new Artikel(titel, nummer, verfuegbar, bestand);
		}
	
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}
	}