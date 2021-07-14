package shop.local.ui.gui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import shop.local.domain.*;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.PackungsgroesseException;
import shop.local.ui.gui.panels.MitarbeiterPanel.AtikelAendernListener;
import shop.local.ui.gui.panels.MitarbeiterPanel.MitarbeiterListener;
import shop.local.ui.gui.panels.MitarbeiterPanel.addArtikelErstellenListener;
import shop.local.ui.gui.panels.MitarbeiterPanel.checkBoXListener;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Ereignis;

public class MitarbeiterPanel extends JPanel {

	/**
	 * TEST
	 */
	private static final long serialVersionUID = 1L;

	public interface MitarbeiterListener {
		public void onSearchResult(Vector<Artikel> artikelListe);

		public void angemeldeterMitarbeiter();

		public void fromTableToHistory();

		public void searchHistorie(Vector<Ereignis> suchergebnis);
	}

	private Eshop shop = null;
	private MitarbeiterListener listener = null;

	private JTextField artikelNameField;
	private JTextField artikelPreisField;
	private JTextField artikelAnzahlField;
	private JCheckBox massengutCheckBox;
	private JTextField massengutPaketField;
	private JButton artikelErstellenButton;

	private JLabel platzhalter;

	private JTextField artikelAendernField;
	private JTextField artikelBestandField;
	private JButton artikelAendernButton;

	private JLabel platzhalter2;

	private JTextField historieArtikelNummerField;
	private JTextField historieTageField;

	private JButton showHistorieButton;
	private Artikel einArtikel;
	private ArtikelTablePanel artikelPanel;
	
	public MitarbeiterPanel(Eshop shop, MitarbeiterListener listener) {
		this.shop = shop;
		this.listener = listener;

		setupUI();

		setupEvents();

	}

	private void setupUI() {
		int anzahlZeilen = 14;
		this.setLayout(new GridLayout(anzahlZeilen, 1));

		artikelNameField = new JTextField();
		artikelNameField.setToolTipText("Artikelname");
		this.add(artikelNameField);

		artikelPreisField = new JTextField();
		this.add(artikelPreisField);
		artikelPreisField.setToolTipText("Artikelpreis");

		artikelAnzahlField = new JTextField();
		this.add(artikelAnzahlField);
		artikelAnzahlField.setToolTipText("Stueckzahl");

		massengutCheckBox = new JCheckBox("Massengut");
		this.add(massengutCheckBox);

		massengutPaketField = new JTextField();
		this.add(massengutPaketField);
		massengutPaketField.setToolTipText("Paketgroesse");
		massengutPaketField.setVisible(false);

		artikelErstellenButton = new JButton("Artikel erstellen");
		this.add(artikelErstellenButton);

		platzhalter = new JLabel();
		this.add(platzhalter);

		artikelAendernField = new JTextField();
		this.add(artikelAendernField);
		artikelAendernField.setToolTipText("Artikelnummer");

		artikelBestandField = new JTextField();
		this.add(artikelBestandField);
		artikelBestandField.setToolTipText("Stueckzahl");

		artikelAendernButton = new JButton("Bestand aendern");
		this.add(artikelAendernButton);

		platzhalter2 = new JLabel();
		this.add(platzhalter2);

		

	}

	private void setupEvents() {
		artikelErstellenButton.addActionListener(new addArtikelErstellenListener());
		artikelAendernButton.addActionListener(new AtikelAendernListener());
		massengutCheckBox.addActionListener(new checkBoXListener());
	}

	class addArtikelErstellenListener implements ActionListener {

		private Vector<Artikel> artikelListe;

		public void actionPerformed(ActionEvent ae) {

			Vector<Artikel> suchErgebnis;
//			Vector<Artikel> artikelListe;
			String artNameString = artikelNameField.getText();

			String artPreisStringToParse = artikelPreisField.getText();
			float artPreisString = Float.parseFloat(artPreisStringToParse);

			String artStueckzahlStringToParse = artikelAnzahlField.getText();
			int artStueckzahlString = Integer.parseInt(artStueckzahlStringToParse);

			einArtikel = new Artikel(artNameString, artStueckzahlString, artPreisString);
//			String paketGroesseToParse = massengutPaketField.getText();
//			int paketGroesseInt = Integer.parseInt(paketGroesseToParse);

			if (ae.getSource().equals(artikelErstellenButton) && !massengutCheckBox.isSelected()) {

				try {

					shop.mitArtikelHinzu(einArtikel);
					suchErgebnis = (Vector<Artikel>) shop.gibAlleArtikel();
					listener.onSearchResult(suchErgebnis);
					try {
						shop.speicherArtikel();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						shop.speicherEreignis();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					artikelPanel.updateArtikelList(artikelListe);
				} catch (ArtikelExistiertBereitsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
				} catch (PackungsgroesseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				String paketGroesseToParse = massengutPaketField.getText();
				int paketGroesseInt = Integer.parseInt(paketGroesseToParse);
//					shop.erstelleMassenartikel(artStueckzahlString, artNameString, artPreisString, paketGroesseInt);
//					suchErgebnis = shop.artikelListeGui();
//					listener.onSearchResult(suchErgebnis);
			}
		}
	}
	class AtikelAendernListener implements ActionListener {
		private Vector<Artikel> artikelListe;
		public void actionPerformed(ActionEvent ae) {
			Vector<Artikel> suchErgebnis;

			String artikelname = artikelAendernField.getText();

			String artBestandStringToParse = artikelBestandField.getText();
			int erhohung = Integer.parseInt(artBestandStringToParse);

			try {
				shop.mitErhoehtArtikel(artikelname, erhohung);
				try {
					shop.speicherArtikel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					shop.speicherEreignis();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (PackungsgroesseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			suchErgebnis = (Vector<Artikel>) shop.gibAlleArtikel();
			listener.onSearchResult(suchErgebnis);
		}
		
	}

	class checkBoXListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (massengutCheckBox.isSelected()) {
				AbstractButton abstractButton = (AbstractButton) ae.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				massengutPaketField.setVisible(true);

			} else if (!massengutCheckBox.isSelected()) {
				AbstractButton abstractButton = (AbstractButton) ae.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				massengutPaketField.setVisible(false);
			}
		}
	}

	
}
