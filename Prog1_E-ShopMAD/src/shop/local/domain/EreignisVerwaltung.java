package shop.local.domain;

import shop.local.persistence.*;

import java.io.IOException;
import java.util.Vector;

import shop.local.domain.exceptions.*;
import shop.local.valueobjects.*;

public class EreignisVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Ereignis> ereignisListeVector = new Vector<Ereignis>();
	
	public void addEreignis(Ereignis ereignis) {
		ereignisListeVector.add(ereignis);	
	}
	
	//Ereignis Daten lesen
	public void liesDaten(String datei) throws IOException {
		pm.openForReading(datei);

		Ereignis einEreignis;
		do {
			einEreignis = pm.ladeEreignisse();
			if (einEreignis != null) {
				addEreignis(einEreignis);
			}
		} while (einEreignis != null);
		pm.close();
	}
	
	//ereignis Daten speichern
	public void speicherEreignis() throws IOException {
		pm.openForWriting("SHOP_Ereignisse.txt"); // PersistenzManager für Schreibvorgang �ffnen
			for(Ereignis ereignis:ereignisListeVector) {
				System.out.println("Ein Ereignis wurde gespeichert!");
				pm.speicherEreignis(ereignis);	
			}
				pm.close();
		}
}
