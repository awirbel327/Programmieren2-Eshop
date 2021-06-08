package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Mitarbeiter;



/*
 * Klasse zur Verwaltung unserer Artikeldaten
 */


public class ArtikelVerwaltung {
	private Vector<Artikel> artikelBestand = new Vector<Artikel>();
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	
	
	//Methode zum Sortieren von Artikel nach ArtikelNummer.
	public void artikelSortNummer(Vector <Artikel> artikel) {
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige grï¿½ï¿½e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() {		
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird überschrieben
				String nr1 = Integer.toString(artikel1.getNummer());	// Int zu String
				String nr2 = Integer.toString(artikel2.getNummer());	// Int zu String
				return nr1.compareTo(nr2);	// vergleich Nachbarn (nur Strings vergelichbar)
			}
		});
		System.out.println(Arrays.asList(vecZuArr));
	}
	
	
	//Methode zum Sortieren von Artikel nach ArtikelBezeichnung.
	//https://stackoverflow.com/questions/18895915/how-to-sort-an-array-of-objects-in-java
	public void artikelSortBezeichnung(Vector <Artikel> artikel) {
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige grï¿½ï¿½e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() { //
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird überschrieben
				return artikel1.getTitel().compareTo(artikel2.getTitel());	// vergleichen Nachbarn anhand von Buchstaben
			}
		});
		System.out.println(Arrays.asList(vecZuArr));
	}		
	
	
	// Methode der unsere E-Shop Datei übergeben wird um die Artikelliste in die Persistenz zu überführen
	// So ist diese bei jedem öffnen des E-Shops auf dem neusten Stand und wird über längere Zeit gespeichert!
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager fÃ¼r LesevorgÃ¤nge Ã¶ffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
	
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Buch in Liste einfÃ¼gen
				try {
					einfuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schlieÃŸen
		pm.close();
	}
	
	
	// Methode um neue Artikel zur Artikelliste hinzuzufügen. Fehler wenn Artikel bereits in der Liste ist.
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelBestand.contains(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}
		artikelBestand.add(einArtikel);
	}
	
	//Mitarbeiter fügt Artikel hinzu
	public void mitArtikelhinzufuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		for(Artikel artikel:artikelListeVector) {
			if(einArtikel.getTitel().equals(artikel.getTitel())) {
				throw new ArtikelExistiertBereitsException(einArtikel, "Artikel existiert bereits");
			}
		}
		einArtikel.setNummer(artikelListeVector.size() + 1);
		artikelListeVector.add(einArtikel);
		return;
	}

	
	// Getter Methode für den Artikel Bestands Vector, gibt Liste aus
	public Vector<Artikel> getArtikelBestand() {
		return new Vector<Artikel>(artikelBestand);
	}	
	
	public Vector <Artikel> getArtikelliste(){
		return artikelListeVector;
	}
	
	
	// Methode die einen Artikel anhand des Titels (BEZEICHNUNG???) sucht und eine Liste aller Artikel mit dieser Bezeichnung ausgibt
	// FRAGE:warum wird hier eine Liste ausgegeben? Haben die Artikel nicht sowieso unique Bezeichnungen? Wofür wird diese Methode gebraucht?
	public List<Artikel> sucheArtikel(String titel) { //TITEL = BEZEICHNUNG?
		List<Artikel> suchErg = new Vector<Artikel>();

		// Artikelbestand durchlaufen und nach Bezeichnung des Artikels suchen
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' fÃ¼r spÃ¤teren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics   ------>>> HIER NOCHMAL PRÜFEN WENN WIR GENERCIS HATTEN
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics) 
			Artikel a = iter.next();
				if (a.getTitel().equals(titel)) //TITEL = BEZEICHNUNG?
					suchErg.add(a);
				}
		return suchErg;
		}
	
	//Methode zum speichern der Artikelliste (z.B. bei hinzufuegen)
	public void speicherArtikel() throws IOException {
		pm.openForWriting("SHOP_B.txt"); // PersistenzManager fÃ¼r Schreibvorgang ï¿½ffnen
			for(Artikel artikel:artikelListeVector) {
				System.out.println(artikel.getTitel() + " wurde gespeichert");
				pm.speicherArtikelDaten(artikel);	
			}
				pm.close();
		}
	}

