package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Arrays;
import java.util.Comparator;

import shop.local.persistence.*;
import shop.local.domain.exceptions.*;
import shop.local.valueobjects.*;




/*
 * Klasse zur Verwaltung unserer Artikeldaten
 */


public class ArtikelVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Artikel> artikelListeVector = new Vector<Artikel>();
	
	// Getter Methode f�r den Artikel Bestands Vector, gibt Liste aus
		public Vector<Artikel> getArtikelBestand() {
			return new Vector<Artikel>(artikelListeVector);
		}	
		
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
		for(int i = 0; vecZuArr.length > i ;i++) {
			Artikel art = vecZuArr[i];
			System.out.println(art.toString());
		}
	}
	
	
	//Methode zum Sortieren von Artikel nach ArtikelBezeichnung.
	public void artikelSortBezeichnung(Vector <Artikel> artikel) {
		Artikel[] vecZuArr = new Artikel[artikel.size()];	//damit Array richtige gr��e
		artikel.toArray(vecZuArr); //Vector Array
		Arrays.sort((Artikel[])vecZuArr, new Comparator<Artikel>() { //
			@Override 
			public int compare(Artikel artikel1, Artikel artikel2) { //Methode compare der Java-Klasse Comparator wird �berschrieben
				return artikel1.getBezeichnung().compareTo(artikel2.getBezeichnung());	// vergleichen Nachbarn anhand von Buchstaben
			}
		});
		//System.out.println(Arrays.asList(vecZuArr));
		for(int i = 0; vecZuArr.length > i ;i++) {
			Artikel art = vecZuArr[i];
			System.out.println(art.toString());
			
		}
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
				
				try {
					mitArtikelhinzufuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				} catch (PackungsgroesseException e2) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);
		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}
	
	//Mitarbeiter f�gt Artikel hinzu
	public void mitArtikelhinzufuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException, PackungsgroesseException {
		for(Artikel artikel:artikelListeVector) {
			if(einArtikel.getBezeichnung().equals(artikel.getBezeichnung())) {
				throw new ArtikelExistiertBereitsException(einArtikel, "Artikel existiert bereits");
			}
		}
		einArtikel.setNummer(artikelListeVector.size() + 1);
		artikelListeVector.add(einArtikel);
		if (einArtikel instanceof Massengutartikel) {
			if (einArtikel.getBestand() % ((Massengutartikel) einArtikel).getPackungsgroesse() !=0) {
				throw new PackungsgroesseException((Massengutartikel) einArtikel, "-in mitArtikelhinzufuegen");
			}
		}
		return;
	}

	//Methode um einen Artikel anhand seiner Nummer aus einem beliebigen Vector rauszusuchen	
	public Artikel sucheArtikelinListe(Vector<Artikel> artListe, int nummer) {
		Artikel gesuchterArt = null; 
		for (int i = 0; artListe.size() > i; i++) {
			if(artListe.elementAt(i).getNummer() == nummer) {
				 gesuchterArt = artListe.elementAt(i);
			} 
		}
		return gesuchterArt;
	}
	
	// Methode die einen Artikel anhand der Bezeichnung sucht und eine Liste aller Artikel mit dieser Bezeichnung ausgibt
	public List<Artikel> sucheArtikel(String bezeichnung) { 
		List<Artikel> suchErg = new Vector<Artikel>();

		// Artikelbestand durchlaufen und nach Bezeichnung des Artikels suchen
		Iterator<Artikel> iter = artikelListeVector.iterator();
		while (iter.hasNext()) { 
			Artikel a = iter.next();
				if (a.getBezeichnung().equals(bezeichnung)) 
					suchErg.add(a);
				}
		return suchErg;
		}
	
	//Methode zum speichern der Artikelliste (z.B. bei hinzufuegen)
	public void speicherArtikel() throws IOException {
		pm.openForWriting("SHOP_B.txt"); // PersistenzManager für Schreibvorgang �ffnen
			for(Artikel artikel:artikelListeVector) {
				pm.speicherArtikelDaten(artikel);	
			}
				pm.close();
		}
	
	//String kUserId, int userId,String artikelbezeichnung, int anzahl, String aktion
	public Vector<Ereignis> kaufen(Vector <Artikel> warenkorb, Kunde kunde) {
		Vector<Ereignis> er = new Vector<Ereignis>();
		for(Artikel artikel:warenkorb) {
			for(Artikel arti:artikelListeVector) {
				if(artikel.getNummer() == arti.getNummer()) {
					arti.setBestand(arti.getBestand() - artikel.getBestand());
					Ereignis erei = new Ereignis("Kunde", kunde.getKundenNr(), artikel.getBezeichnung(), artikel.getBestand(), "wurde gekauft"  );
					er.add(erei);
				}
			}
		}
		try {
			speicherArtikel();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return er;
	}
	
	//Methode um Artikelbestand zu erh�hen (Mitarbeiter)
	public void mitErhoehtArtikel(String artikelname, int erhohung) throws PackungsgroesseException {
		for (Artikel artikel:artikelListeVector) {
			if(artikelname.equals(artikel.getBezeichnung())) {
				if (artikel instanceof Massengutartikel && erhohung % ((Massengutartikel) artikel).getPackungsgroesse() != 0) { //Downcasting
					throw new PackungsgroesseException((Massengutartikel)artikel, "-in mitErhoehtArtikel");
				} 
				artikel.setBestand(artikel.getBestand() + erhohung);
				try {
					speicherArtikel();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}	
	}
}