package shop.local.ui.gui.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;


import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import shop.local.ui.cui.ShopClientCUI;
import shop.local.ui.gui.awt.ShopClientGUI.FileMenu;
import shop.local.ui.gui.panels.AnmeldenPanel;
import shop.local.ui.gui.panels.ArtikelTablePanel;

import shop.local.ui.gui.panels.MitarbeiterPanel;
import shop.local.ui.gui.panels.RegisterPanel;
import shop.local.ui.gui.panels.RegisterPanel.RegisterListener;
import shop.local.ui.gui.panels.SearchPanel;
import shop.local.ui.gui.panels.SearchPanel.SearchResultListener;
import shop.local.ui.gui.panels.WarenkorbPanel;
import shop.local.ui.gui.panels.WarenkorbPanel.WarenkorbListener;
import shop.local.ui.gui.panels.AnmeldenPanel.AnmeldenListener;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.User;
import shop.local.domain.*;
import shop.local.persistence.*;

public class ShopClientGUI extends JFrame implements AnmeldenListener, SearchResultListener, WarenkorbListener, RegisterListener{ 
	
	private static final long serialVersionUID = 1L;
	private static Eshop shop;
	private static UserVerwaltung userverwaltung;
	private static String datei;
	
	private ArtikelTablePanel artikelPanel;
	private SearchPanel suchenPanel;
	private AnmeldenPanel anmeldenPanel;
	private WarenkorbPanel warenkorbPanel;
	private MitarbeiterPanel mitarbeiterPanel;
	private RegisterPanel registerPanel;
	private JScrollPane scrollPane;
//	private HistoriePanel historiePanel;
	private JScrollPane historieScrollPane;
	
	public ShopClientGUI(String datei) throws IOException {
//		super(titel);
		shop = new Eshop(datei);
		initialize();
	}
	
	public void initialize() {
		List<Artikel>liste;
		
		setupMenu();
		
		// Fensterlayout


		
				setLayout(new BorderLayout());
				
				setTitle("Eshop");
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				addWindowListener(new WindowCloser());
				
				
				java.util.List<Artikel> artikel = shop.gibAlleArtikel(); // Irgendwie findet er die liste nicht. Oder er findet allgemein die Eshop klasse nicht (laut Khai)
				
				artikelPanel = new ArtikelTablePanel((Vector<Artikel>) artikel);
				scrollPane = new JScrollPane(artikelPanel);
				this.add(scrollPane, BorderLayout.CENTER);
				scrollPane.setVisible(true);
				

				
				System.out.print(shop);
				anmeldenPanel = new AnmeldenPanel(shop, this); // hier müsste eigentlich nur (shop,this); stehen
				this.add(anmeldenPanel, BorderLayout.SOUTH);
				
				suchenPanel = new SearchPanel(shop, this); // Hier muss statt null eigentlich this stehen
				this.add(suchenPanel, BorderLayout.NORTH);
				
				warenkorbPanel = new WarenkorbPanel(shop, this);
				this.add(warenkorbPanel, BorderLayout.EAST);
				warenkorbPanel.setVisible(true); // MÜSSTE EIGENTLICH ERST FALSE UND NACH DEM ANMELDEN AUF TRUE GESETZT WERDEN
				
				setVisible(true);
				setSize(640, 480);
	}
	
	
	private void setupMenu() {
		JMenuBar mBar = new JMenuBar();

		JMenu fileMenu = new FileMenu();
		mBar.add(fileMenu);
	
		this.setJMenuBar(mBar);
	}
	
	class FileMenu extends JMenu implements ActionListener {
		
		public FileMenu() {
			super("MAD-SHOP");
			
 		JMenuItem mi = new JMenuItem ("Abmelden");
			mi.addActionListener(this);
			this.add(mi);
			
	 	JMenuItem miExit = new JMenuItem ("Beenden");
			miExit.addActionListener(this);
			this.add(miExit);
		}
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			System.out.println(command);
			
			if (command.equals("Abmelden")) {
//				ShopClientGUI.this.setVisible(false);
//				warenkorbPanel.setVisible(false);
//				mitarbeiterPanel.setVisible(false);
				anmeldenPanel.setVisible(true);
			}
			
			if (command.contentEquals("Beenden")) {
				System.exit(0);
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		ShopClientGUI gui;
		try {
			gui = new ShopClientGUI("SHOP");
//			gui.run();
		} catch (IOException e) {	
			System.out.println(e.getMessage());
		}
	
//		ShopClientGUI gui;
//		try {
//			gui = new ShopClientGUI("SHOP");
//			
//		} catch (IOException e) {	
//			System.out.println(e.getMessage());
//		}
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ShopClientGUI shopClientGui = new ShopClientGUI(datei);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
	}
	
//	public void run() {
//		String input = ""; 
//		ShopClientGUI shopClientGui = new ShopClientGUI("Eshop");
//		
//	}

	@Override
	public void userEingeloggt(User a) {
		// TODO Auto-generated method stub
		if (a instanceof Kunde) {
			System.out.println(a);
		warenkorbPanel.setVisible(true);
	} else if (a instanceof Mitarbeiter){
		System.out.println(a);
		//mitarbeiterPanel.setVisible(true);
	}
		anmeldenPanel.setVisible(false);
	}

	@Override
	public void angemeldeterMitarbeiter() {
		// TODO Auto-generated method stub
	}


	@Override
	public void onSearchResult(Vector<Artikel> artikelListe) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onWarenkorbHinzufuegen(Vector<Artikel> warenkorbliste) {
		// TODO Auto-generated method stub
	}
	
	public void regMenue() {
		registerPanel = new RegisterPanel(shop, this);
		this.add(registerPanel, BorderLayout.CENTER);
		scrollPane.setVisible(false);
		registerPanel.setVisible(true);
		this.revalidate();
	}
	
	public void regToTable() {
		scrollPane = new JScrollPane(artikelPanel);
		this.add(scrollPane, BorderLayout.CENTER);
		registerPanel.setVisible(false);
		scrollPane.setVisible(true);
		this.revalidate();
	}
}
