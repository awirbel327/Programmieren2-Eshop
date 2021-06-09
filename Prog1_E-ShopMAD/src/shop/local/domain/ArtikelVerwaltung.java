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
	
	private ArtikelVerwaltung meineArtikel;
//	private Warenkorb warenkorb;
	private UserVerwaltung meineNutzer;
	
	private Vector<Artikel> artikelBestand = new Vector<Artikel>();
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	private Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	
	public Vector <Artikel> getArtikelliste(){
		return artikelListeVector;
	}
	
	
	//Methode zum Sortieren von Artikel nach ArtikelNummer.
	public void artikelSortNummer(Vector <Artikel> artikel) {
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige gr��e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() {		
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird �berschrieben
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
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige gr��e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() { //
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird �berschrieben
				return artikel1.getTitel().compareTo(artikel2.getTitel());	// vergleichen Nachbarn anhand von Buchstaben
			}
		});
		System.out.println(Arrays.asList(vecZuArr));
	}		
	
	
	// Methode der unsere E-Shop Datei �bergeben wird um die Artikelliste in die Persistenz zu �berf�hren
	// So ist diese bei jedem �ffnen des E-Shops auf dem neusten Stand und wird �ber l�ngere Zeit gespeichert!
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
	
	
	// Methode um neue Artikel zur Artikelliste hinzuzuf�gen. Fehler wenn Artikel bereits in der Liste ist.
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelBestand.contains(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}
		einArtikel.setNummer(artikelListeVector.size() + 1);
		artikelBestand.add(einArtikel);
	}

	
	// Getter Methode f�r den Artikel Bestands Vector, gibt Liste aus
	public Vector<Artikel> getArtikelBestand() {
		return new Vector<Artikel>(artikelBestand);
	}	
	
	
	// Methode die einen Artikel anhand des Titels (BEZEICHNUNG???) sucht und eine Liste aller Artikel mit dieser Bezeichnung ausgibt
	// FRAGE:warum wird hier eine Liste ausgegeben? Haben die Artikel nicht sowieso unique Bezeichnungen? Wof�r wird diese Methode gebraucht?
	public List<Artikel> sucheArtikel(String titel) { //TITEL = BEZEICHNUNG?
		List<Artikel> suchErg = new Vector<Artikel>();

		// Artikelbestand durchlaufen und nach Bezeichnung des Artikels suchen
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' für späteren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics   ------>>> HIER NOCHMAL PR�FEN WENN WIR GENERCIS HATTEN
			// 			(-> Vergleiche mit Einsatz von Vector OHNE Generics) 
			Artikel a = iter.next();
				if (a.getTitel().equals(titel)) //TITEL = BEZEICHNUNG?
					suchErg.add(a);
				}
		return suchErg;
		}
	
	//Methode zum speichern der Artikelliste (z.B. bei hinzufuegen)
	public void speicherArtikel() throws IOException {
		pm.openForWriting("SHOP_B.txt"); // PersistenzManager für Schreibvorgang �ffnen
			for(Artikel artikel:artikelListeVector) {
				System.out.println(artikel.getTitel() + " wurde gespeichert");
				pm.speicherArtikelDaten(artikel);	
			}
				pm.close();
		}
	}

