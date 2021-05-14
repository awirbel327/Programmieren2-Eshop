package shop.local.domain;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.valueobjects.Artikel;


public class ArtikelVerwaltung {
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	private Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	
	
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
	
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Buch in Liste einfügen
				try {
					einfuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}
	
	
	
		
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelBestand.contains(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}

		artikelBestand.add(einArtikel);
	}

	
	public List<Artikel> getArtikelBestand() {
		return new Vector<Artikel>(artikelBestand);
	}	
		
	public List<Artikel> sucheArtikel(String titel) {
	List<Artikel> suchErg = new Vector<Artikel>();

	// Buchbestand durchlaufen und nach Titel suchen
	Iterator<Artikel> iter = artikelBestand.iterator();
	while (iter.hasNext()) {
		// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
		// 		    hier nicht erforderlich wegen Verwendung von Generics
		// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics)
		Artikel a = iter.next();
		if (a.getTitel().equals(titel))
			suchErg.add(a);
	}
	
return suchErg;
}
}
