package shop.local.ui.gui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shop.local.domain.exceptions.*;
import shop.local.ui.gui.panels.WarenkorbPanel.WarenkorbListener;
import shop.local.ui.gui.panels.WarenkorbPanel.addWarenkorbListener;
import shop.local.ui.gui.panels.WarenkorbPanel.bezahlenListener;
import shop.local.ui.gui.panels.WarenkorbPanel.showWarenkorbListener;
import shop.local.ui.gui.swing.models.ArtikelTableModel;
import shop.local.domain.Eshop;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.User;
import shop.local.domain.exceptions.LagerbestandsException;
import shop.local.domain.exceptions.PackungsgroesseException;


public class WarenkorbPanel  extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Eshop shop = null;
	private WarenkorbListener warenkorbListener = null;
	
	private JButton wkAnzeigenButton;
	private JButton artikelHinzufuegenButton;
	private JButton wkBearbeitenButton;
	private JButton wkLoeschenButton;
	private JButton wkBezahlenButton;
	
	private JTextField artikelNummerField, bestandsaenderungNummerField, bestandsaenderungField;
	private JTextField artikelAnzahlField;
	private JCheckBox senkenCheckBox;
	private User userEingeloggt;
	
	public interface WarenkorbListener {
		public void onWarenkorbHinzufuegen (Vector<Artikel> warenkorbliste);
		public void onSearchResult (Vector<Artikel> showWarenkorb);
		public void onWarenkorbAnzeigen ();
	}
	
	public WarenkorbPanel(Eshop shop, WarenkorbListener warenkorbListener) {
		this.shop = shop;
		this.warenkorbListener = warenkorbListener;
		JEditorPane editorPane = new JEditorPane();

		
		setupUI();
		
		setupEvents();
	}
	
	
	private void setupUI() {
		int anzahlZeilen = 12;	
		this.setLayout(new GridLayout(anzahlZeilen, 1));

		this.add(new JLabel());
		artikelNummerField = new JTextField();
		artikelNummerField.setToolTipText("Artikelnummer eingeben");
		this.add(artikelNummerField);
		artikelAnzahlField = new JTextField();
		artikelAnzahlField.setToolTipText("Artikelanzahl eingeben");
		this.add(artikelAnzahlField);
		artikelHinzufuegenButton = new JButton("Zum Warenkorb hinzu");
		this.add(artikelHinzufuegenButton);

		bestandsaenderungNummerField = new JTextField();
		bestandsaenderungNummerField.setToolTipText("Artikelnummer eingeben");
		this.add(bestandsaenderungNummerField);
		bestandsaenderungField = new JTextField();
		bestandsaenderungField.setToolTipText("Artikelanzahl eingeben");
		this.add(bestandsaenderungField);
		senkenCheckBox = new JCheckBox();
		String label = "Bestand senken?";
		senkenCheckBox.setText(label);
		this.add(senkenCheckBox);
		wkBearbeitenButton = new JButton("Warenkorb bearbeiten");
		this.add(wkBearbeitenButton);
		
		this.add(new JLabel());
		
		wkAnzeigenButton = new JButton("Warenkorb");
		this.add(wkAnzeigenButton);
		
		wkLoeschenButton = new JButton("Warenkorb leeren");
		this.add(wkLoeschenButton);

		
		wkBezahlenButton = new JButton("Bezahlen");
		this.add(wkBezahlenButton);
	}
	
	
	private void setupEvents() {
		artikelHinzufuegenButton.addActionListener(new addWarenkorbListener());
		
		//WK anzeigen
		wkAnzeigenButton.addActionListener(new showWarenkorbListener() {
			public void actionPerformed(ActionEvent e) {
				
			warenkorbListener.onWarenkorbAnzeigen();
			}
		});
	
		//Listener ganz unten
		wkBezahlenButton.addActionListener(new bezahlenListener() {});
		
		//Bestand senken oder erhoehen
		wkBearbeitenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (senkenCheckBox.isSelected()) {
					try {
						//HIER MUSS SENKE EINKAUF
						shop.erhoeheEinkauf( Integer.parseInt(bestandsaenderungNummerField.getText()), Integer.parseInt(bestandsaenderungField.getText()));
					}  catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Eingabe muss eine Nummer sein", "Fehler", JOptionPane.WARNING_MESSAGE);
					} catch (LagerbestandsException |PackungsgroesseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					try {
						shop.erhoeheEinkauf(Integer.parseInt(bestandsaenderungNummerField.getText()), Integer.parseInt(bestandsaenderungField.getText()));
					} catch (LagerbestandsException | PackungsgroesseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Eingabe muss eine Nummer sein", "Fehler", JOptionPane.WARNING_MESSAGE);
					} catch (NullPointerException e3) {
						JOptionPane.showMessageDialog(null, "Artikel befindet sich nicht in Ihrem Warenkorb", "Fehler", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		wkLoeschenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shop.leereWk();
				warenkorbListener.onWarenkorbAnzeigen();
			}
		});		
	}
	
	
class addWarenkorbListener implements ActionListener {
		//Artikel hinzufuegen
		public void actionPerformed (ActionEvent ae) {
			if (ae.getSource().equals(artikelHinzufuegenButton)) {
				String artNummerString = artikelNummerField.getText();
				int artNummer = 0;
				int artAnzahl  = 0;
				try {
				artNummer = Integer.parseInt(artNummerString);
				String artAnzahlString = artikelAnzahlField.getText();
				artAnzahl = Integer.parseInt(artAnzahlString);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Die Eingabe muss eine Zahl sein", "Fehler", JOptionPane.WARNING_MESSAGE);
				}
				
				try {
					shop.wkBefuellen( artNummer, artAnzahl);
				} catch (LagerbestandsException | PackungsgroesseException ex) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	class showWarenkorbListener implements ActionListener {
		
		public void actionPerformed (ActionEvent ae) {
			Vector<Artikel> warenkorbAnzeige;
			if (ae.getSource().equals(wkAnzeigenButton)) {
				warenkorbAnzeige = shop.warenkorbGUI();
				warenkorbListener.onSearchResult(warenkorbAnzeige);
			}
		}
	}
	
	class bezahlenListener implements ActionListener {

		@Override
		public void actionPerformed (ActionEvent ae) {
	        JFrame rechnungFrame = new JFrame("Rechnung");
			JLabel rechnungLabel = new JLabel();
	        rechnungFrame.setSize(640, 480);
	        rechnungFrame.add(rechnungLabel);
	        try {
				String gekaufteArtikel = shop.kaufeWarenkorb();
				System.out.println(gekaufteArtikel);
				rechnungLabel.setText("<html>" + gekaufteArtikel + "</html>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
			}
	        rechnungFrame.setVisible(true);
		}
	}
}