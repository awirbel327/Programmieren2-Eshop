package shop.local.persistence;

import java.io.IOException;

import shop.local.valueobjects.*;

public interface PersistenceManager {

	
public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public Artikel ladeArtikel() throws IOException;
	
	
	
	public boolean close();
	
	public Kunde ladeKunde() throws IOException;

	public boolean speicherKundeDaten(Kunde kunde) throws IOException;

	public boolean speicherMitarbeiterDaten(Mitarbeiter mitarbeiter) throws IOException;
	
	public Mitarbeiter ladeMitarbeiter() throws IOException;

	public boolean speicherArtikelDaten(Artikel artikel) throws IOException;
	
	
}