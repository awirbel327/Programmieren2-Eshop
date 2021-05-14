package shop.local.persistence;

import java.io.IOException;

import shop.local.valueobjects.Artikel;

public interface PersistenceManager {

	
public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public Artikel ladeArtikel() throws IOException;
	
	public boolean close();
}