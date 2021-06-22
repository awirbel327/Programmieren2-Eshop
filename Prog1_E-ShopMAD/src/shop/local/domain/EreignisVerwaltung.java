package shop.local.domain;

import shop.local.persistence.*;

import java.util.Vector;

import shop.local.domain.exceptions.*;
import shop.local.valueobjects.*;

public class EreignisVerwaltung {
	
	private PersistenceManager pm = new FilePersistenceManager();
	
	public static Vector <Ereignis> ereignisListeVector = new Vector<Ereignis>();
	
	public void addEreignis(Ereignis ereignis) {
		ereignisListeVector.add(ereignis);			
	}
}
