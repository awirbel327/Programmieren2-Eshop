package shop.local.ui.gui.swing.models;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.Artikel;

public class ArtikelTableModel extends AbstractTableModel  {
	
	private static final long serialVersionUID = 1L;
	private Vector<Artikel> artikel;
    private String[] spaltenNamen = { "Artikelnummer","Artikelbezeichnung", "Preis", "Bestand" };

    public ArtikelTableModel(Vector<Artikel> aktuelleArtikel) {
    	super(); 

    	
    	artikel = new Vector<Artikel>();
    	artikel.addAll(aktuelleArtikel);
    }
    
    public void setArtikel(Vector<Artikel> aktuelleArtikel){
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return artikel.size();
    }


    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }
    
    
    @Override
	public Class<?> getColumnClass(int col) {
		if (artikel.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, col).getClass();
	}
    
    
 @Override
    public Object getValueAt(int row, int col) {
        Artikel gewaehlteArtikel = artikel.get(row);
        switch (col) {
            case 0:
                return gewaehlteArtikel.getNummer();
            case 1:
            	return gewaehlteArtikel.getBezeichnung();
            case 2:
            	return gewaehlteArtikel.getPreis()+"€";
            case 3:
                return gewaehlteArtikel.getBestand(); 
            default:
                return null;
        }
    }

}
