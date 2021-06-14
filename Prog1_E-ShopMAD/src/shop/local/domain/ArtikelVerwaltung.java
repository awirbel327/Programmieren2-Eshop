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
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Mitarbeiter;



/*
 * Klasse zur Verwaltung unserer Artikeldaten
 */


public class ArtikelVerwaltung {
	
	private ArtikelVerwaltung meineArtikel;
//	private Warenkorb warenkorb;
	private UserVerwaltung meineNutzer;
	
//	private Vector<Artikel> artikelBestand = new Vector<Artikel>();
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	private Massengutartikel mArtikel;
	
	
	//Methode zum Sortieren von Artikel nach ArtikelNummer.
	public void artikelSortNummer(Vector <Artikel> artikel) {
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige grï¿½ï¿½e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() {		
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird ï¿½berschrieben
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
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird ï¿½berschrieben
				return artikel1.getTitel().compareTo(artikel2.getTitel());	// vergleichen Nachbarn anhand von Buchstaben
			}
		});
		System.out.println(Arrays.asList(vecZuArr));
	}		
	
	
	// Methode der unsere E-Shop Datei ï¿½bergeben wird um die Artikelliste in die Persistenz zu ï¿½berfï¿½hren
	// So ist diese bei jedem ï¿½ffnen des E-Shops auf dem neusten Stand und wird ï¿½ber lï¿½ngere Zeit gespeichert!
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager fÃ¼r LesevorgÃ¤nge Ã¶ffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
	
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Buch in Liste einfÃ¼gen
				try {
					mitArtikelhinzufuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schlieÃŸen
		pm.close();
	}
	
	/*
	// Methode um neue Artikel zur Artikelliste hinzuzufï¿½gen. Fehler wenn Artikel bereits in der Liste ist.
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelListeVector.contains(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}
		artikelListeVector.add(einArtikel);
	}
	*/
	
	//Mitarbeiter fügt Artikel hinzu
	public void mitArtikelhinzufuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		for(Artikel artikel:artikelListeVector) {
			if(einArtikel.getTitel().equals(artikel.getTitel())) {
				throw new ArtikelExistiertBereitsException(einArtikel, "Artikel existiert bereits");
			}
		}
		
		einArtikel.setNummer(artikelListeVector.size() + 1);
		artikelListeVector.add(einArtikel);
		//Falls es sich um einen neuen Massenartikel handelt, wird der Bestand direkt auf die vorgegebene Packungsgröße gesetzt
		if (einArtikel instanceof Massengutartikel) {
			einArtikel.setBestand(((Massengutartikel) einArtikel).getPackungsgroesse()); //Downcasting
		}
		return;
	}

	
	// Getter Methode fï¿½r den Artikel Bestands Vector, gibt Liste aus
	public Vector<Artikel> getArtikelBestand() {
		return new Vector<Artikel>(artikelListeVector);
	}	
	
	public Vector <Artikel> getArtikelliste(){
		return artikelListeVector;
	}
	
	
	// Methode die einen Artikel anhand des Titels (BEZEICHNUNG???) sucht und eine Liste aller Artikel mit dieser Bezeichnung ausgibt
	// FRAGE:warum wird hier eine Liste ausgegeben? Haben die Artikel nicht sowieso unique Bezeichnungen? Wofï¿½r wird diese Methode gebraucht?
	public List<Artikel> sucheArtikel(String titel) { //TITEL = BEZEICHNUNG?
		List<Artikel> suchErg = new Vector<Artikel>();

		// Artikelbestand durchlaufen und nach Bezeichnung des Artikels suchen
		Iterator<Artikel> iter = artikelListeVector.iterator();
		while (iter.hasNext()) {
			// WICHTIG: Type Cast auf 'Buch' fÃ¼r spÃ¤teren Zugriff auf Titel
			// 		    hier nicht erforderlich wegen Verwendung von Generics   ------>>> HIER NOCHMAL PRï¿½FEN WENN WIR GENERCIS HATTEN
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

	/* TEST bitte noch drin lassen, falls man das noch braucht :)
	 * 
	public void MassengutArtikelEinfügen (Artikel artikel, int erhohung) {
		mArtikel = null;
		artikel = (Massengutartikel) mArtikel;
		if (mArtikel.getBestand() + erhohung % mArtikel.getPackungsgroesse() != 0) {
			System.out.println("Massenartikel muss im Pack gekauft werden"); //Exception einfügen
		}
	}
	*/
	
	//Methode um Artikelbestand zu erhöhen (Mitarbeiter), prüft jetzt auch ob es sich um Massengutartikel handelt!
	public void mitErhoehtArtikel(String artikelname, int erhohung) {
		for (Artikel artikel:artikelListeVector) {
			if(artikelname.equals(artikel.getTitel())) {
				if (artikel instanceof Massengutartikel && artikel.getBestand() + erhohung % ((Massengutartikel) artikel).getPackungsgroesse() != 0) { //Downcasting
					System.out.println("Massenartikel muss im Pack gekauft werden"); //Exception kommt noch
					//MassengutArtikelEinfügen(artikel, erhohung);
				}
				artikel.setBestand(artikel.getBestand() + erhohung);		
			}
				
		}
		try {
			speicherArtikel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

