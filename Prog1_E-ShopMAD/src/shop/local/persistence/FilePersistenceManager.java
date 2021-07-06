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

//Fuer Dateien einlesen usw.

public class FilePersistenceManager  implements PersistenceManager  {
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void openForWriting(String datei) throws IOException {	
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}
	
	
	//Close() -->  zum schlie�en der leser und schreiber
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
	
	
	//Kunden laden
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
	
	
	//Ereignisse wird �bergeben und gespeichert 
	public boolean speicherEreignis(Ereignis ereignis) throws IOException{
		schreibeZeile(ereignis.getKUserId());
		schreibeZeile(Integer.toString(ereignis.getUserId()));
		schreibeZeile(ereignis.getArtikelbezeichnung());
		schreibeZeile(Integer.toString(ereignis.getAnzahl()));
		schreibeZeile(ereignis.getAktion());
		schreibeZeile(ereignis.getDatum());
		return true;
	}
	
	//Kunden laden
		public Ereignis ladeEreignisse() throws IOException {
			String kUserId = liesZeile();
			if (kUserId == null) {
				return null;
			}
			String userIdString = liesZeile();
			int userId = Integer.parseInt(userIdString);
			String artikelbezeichnung = liesZeile();
			String anzahlString = liesZeile();
			int anzahl = Integer.parseInt(anzahlString);
			String aktion = liesZeile();
			String datum = liesZeile();
			
			return new Ereignis(kUserId, userId, artikelbezeichnung, anzahl, aktion, datum);
		}
	
	
	// Kunde wird uebergeben und gespeichert
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
	
	
	public Mitarbeiter ladeMitarbeiter() throws IOException {
		String name = liesZeile();
		if (name == null) {
			return null;
		}
		String mitarbeiterNrString = liesZeile();
		int mitarbeiterNr = Integer.parseInt(mitarbeiterNrString);
		String username = liesZeile();
		String passwort = liesZeile();
		
		return new Mitarbeiter(name, mitarbeiterNr, username, passwort);	//statt mitarbeiternR vllt nr ?
	}
	
	// Mitarbeiter wird uebergeben und gespeichert
	public boolean speicherMitarbeiterDaten(Mitarbeiter mitarbeiter) throws IOException {
		schreibeZeile(mitarbeiter.getName());
		schreibeZeile(Integer.toString(mitarbeiter.getMitarbeiterNr()));
		schreibeZeile(mitarbeiter.getUsername());
		schreibeZeile(mitarbeiter.getPasswort());
		return true;
	}
	
	
	//Methode zum laden von Artikeln
	public Artikel ladeArtikel() throws IOException {
		String bezeichnung = liesZeile();						//liest Bezeichnung
		if (bezeichnung == null) {
			return null;										// keine Daten mehr vorhanden
		}
		String nummerString = liesZeile();						//Nummer einlesen
		int nummer = Integer.parseInt(nummerString);			//String in int konvertieren
		String bestandString = liesZeile();						//Bestand einlesen
		int bestand = Integer.parseInt(bestandString);			//String in int konvertieren	
		String preisString = liesZeile();						//Preis einlesen
		double preis = Double.parseDouble(preisString); 		//String in int konvertieren
		String typ = liesZeile();
		if (typ.equals("Massengut")) {
			String packungsgroesseString = liesZeile();
			int packungsgroesse = Integer.parseInt(packungsgroesseString);			//String in int konvertieren
			return new Massengutartikel(bezeichnung, bestand, preis, packungsgroesse);	//neues Massengut-Objekt anlegen und zurueckgeben
		} else {
			return new Artikel(bezeichnung, bestand, preis);	//neues Artikel-Objekt anlegen und zurueckgeben
		}
					
	}
	
	// Artikel wird uebergeben und gespeichert
		public boolean speicherArtikelDaten(Artikel artikel) throws IOException {
			schreibeZeile(artikel.getBezeichnung());
			schreibeZeile(Integer.toString(artikel.getNummer()));
			schreibeZeile(Integer.toString(artikel.getBestand()));
			schreibeZeile(Double.toString(artikel.getPreis()));
			if (artikel instanceof Massengutartikel) {
				schreibeZeile("Massengut");
				schreibeZeile(Integer.toString(((Massengutartikel) artikel).getPackungsgroesse()));
			} else {
			schreibeZeile("Einzelgut");
			}
			return true;	
		}
	
		
		
	// Methode zum Lesen von Dateien
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
	
	
	@Override
	public void bestandKauf(String name, int bestandEins, int bestandZwei, String username) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void artikellisteAbspeichernPm(Vector<Artikel> artikelListeVector)
			throws FileNotFoundException, IOException {
		
	}
}