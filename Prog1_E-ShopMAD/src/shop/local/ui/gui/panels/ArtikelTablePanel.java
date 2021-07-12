package shop.local.ui.gui.panels;

import java.util.Collections;
import java.util.Vector;

import javax.swing.JTable;

import shop.local.ui.gui.swing.models.ArtikelTableModel;
import shop.local.valueobjects.Artikel;

public class ArtikelTablePanel extends JTable{
	
	private static final long serialVersionUID = 1L;

	public ArtikelTablePanel(Vector<Artikel> artikel) {
		super();
		ArtikelTableModel tableModel = new ArtikelTableModel(artikel);
		super.setAutoCreateRowSorter(true);
		setModel(tableModel);
//		updateArtikelList(artikel);	
	}
	
//	public void updateArtikelList(Vector<Artikel> artikel) {
//		Collections.sort(artikel, (b1, b2) -> b1.getNummer() - b2.getNummer());	
//		ArtikelTableModel tableModel = (ArtikelTableModel) getModel();
//		tableModel.setArtikel(artikel);
//	}
	
}
