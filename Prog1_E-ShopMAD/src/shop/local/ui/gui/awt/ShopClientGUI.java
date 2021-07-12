package shop.local.ui.gui.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;


import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import shop.local.domain.Eshop;
import shop.local.domain.UserVerwaltung;
import shop.local.ui.cui.ShopClientCUI;
import shop.local.ui.gui.awt.ShopClientGUI.FileMenu;
import shop.local.ui.gui.panels.AnmeldenPanel;
import shop.local.ui.gui.panels.ArtikelTablePanel;

import shop.local.ui.gui.panels.MitarbeiterPanel;
import shop.local.ui.gui.panels.RegisterPanel;
import shop.local.ui.gui.panels.SearchPanel;
import shop.local.ui.gui.panels.WarenkorbPanel;
import shop.local.ui.gui.panels.AnmeldenPanel.AnmeldenListener;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.domain.*;
import shop.local.domain.Eshop;

public class ShopClientGUI extends JFrame implements AnmeldenListener{ 
	
	private static final long serialVersionUID = 1L;
	private static Eshop shop;
	private static UserVerwaltung userverwaltung;
	
	private ArtikelTablePanel artikelPanel;
	private SearchPanel suchenPanel;
	private AnmeldenPanel anmeldenPanel;
	private WarenkorbPanel warenkorbPanel;
	private MitarbeiterPanel mitarbeiterPanel;
	private RegisterPanel registerPanel;
	private JScrollPane scrollPane;
//	private HistoriePanel historiePanel;
	private JScrollPane historieScrollPane;
	
	public ShopClientGUI(String titel) {
		super(titel);

		initialize();
		
	}
	
	public void initialize() {
		
		
		setupMenu();
		
		// Fensterlayout


				setLayout(new BorderLayout());
				
				setTitle("Eshop");
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				addWindowListener(new WindowCloser());
				
//				
//				java.util.List<Artikel> artikel = shop.gibAlleArtikel();
//				artikelPanel = new ArtikelTablePanel((Vector<Artikel>) artikel);
//				
				System.out.print(shop);
				anmeldenPanel = new AnmeldenPanel(shop, (AnmeldenListener) this);
				this.add(anmeldenPanel, BorderLayout.SOUTH);
				
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
			super("SHOP");
			
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
	
	public static void main(String[] args) {
		ShopClientGUI gui;
		gui = new ShopClientGUI("SHOP");
		gui.run();
	}
	public void run() {
		String input = ""; 
		ShopClientGUI shopClientGui = new ShopClientGUI("Eshop");
		
	}

	@Override
	public void angemeldeterUser(int a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void angemeldeterMitarbeiter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void regMenue() {
		// TODO Auto-generated method stub
		
	}
	
	
}
