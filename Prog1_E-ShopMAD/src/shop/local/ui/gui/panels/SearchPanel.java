package shop.local.ui.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shop.local.domain.Eshop;

import shop.local.ui.gui.panels.SearchPanel.SearchResultListener;

import shop.local.valueobjects.Artikel;

public class SearchPanel extends JPanel {
	
	private Eshop shop = null;
	private SearchResultListener searchListener = null;

	private JTextField searchTextField;
	private JButton searchButton = null;
	
	private static final long serialVersionUID = 1L;

	public interface SearchResultListener {
		public void onSearchResult(Vector<Artikel> artikelListe);
	}
	
	
	public SearchPanel(Eshop shop, SearchResultListener listener) {
		this.shop = shop;
		searchListener = listener;
		
		setupUI();
		
		setupEvents();
	}
	
	private void setupUI() {
		GridBagLayout gridBagLayout = new GridBagLayout(); 
		this.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;	// Zeile 0

		JLabel searchLabel = new JLabel("Suchbegriff:");
		c.gridx = 0;	// Spalte 0
		c.weightx = 0.2;	// 20% der gesamten Breite
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(searchLabel, c);
		this.add(searchLabel);
		
		searchTextField = new JTextField();
		searchTextField.setToolTipText("Suchbegriff eingeben.");
		c.gridx = 1;	// Spalte 1
		c.weightx = 0.6;	// 60% der gesamten Breite
		gridBagLayout.setConstraints(searchTextField, c);
		this.add(searchTextField);
		
		searchButton = new JButton("Such!");
		c.gridx = 2;	// Spalte 2
		c.weightx = 0.2;	// 20% der gesamten Breite
		gridBagLayout.setConstraints(searchButton, c);
		this.add(searchButton);
		
		// Rahmen definieren
		setBorder(BorderFactory.createTitledBorder("Hier können sie nach Artikeln suchen!"));
	}
	
	private void setupEvents() {
		searchButton.addActionListener(new SuchListener());
	}
	
	class SuchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			Vector<Artikel> suchErgebnis;
			if (ae.getSource().equals(searchButton)) {
				String bezeichnung = searchTextField.getText();
				if (bezeichnung.isEmpty()) {
					suchErgebnis = shop.artikelListeGui();
				} else {
//					suchErgebnis = shop.sucheNachBezeichnung(bezeichnung);

				}
//				searchListener.onSearchResult(suchErgebnis);	
						
				}

				
				
			}
		}
	
}
