package shop.local.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import shop.local.valueobjects.*;

public interface PersistenceManager {

	
	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public Artikel ladeArtikel() throws IOException;
	
	public void bestandKauf(String name, int bestandEins, int bestandZwei, String username) throws IOException;
	
	public void artikellisteAbspeichernPm(Vector<Artikel> artikelListeVector) throws FileNotFoundException, IOException;
	
	public boolean close();
	
	public Kunde ladeKunde() throws IOException;

	public boolean speicherKundeDaten(Kunde kunde) throws IOException;

	public boolean speicherMitarbeiterDaten(Mitarbeiter mitarbeiter) throws IOException;
	
	public Mitarbeiter ladeMitarbeiter() throws IOException;

	public boolean speicherArtikelDaten(Artikel artikel) throws IOException;
	
	
}