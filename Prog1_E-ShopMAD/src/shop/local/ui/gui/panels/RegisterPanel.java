package shop.local.ui.gui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shop.local.domain.Eshop;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.ui.gui.panels.RegisterPanel.AbschickenListener;
import shop.local.ui.gui.panels.RegisterPanel.RegisterListener;
import shop.local.valueobjects.Kunde;

public class RegisterPanel extends JPanel {

	 
		private static final long serialVersionUID = 1L;


		public interface RegisterListener {
			public void regToTable();
			
		}
		
		private RegisterListener registerListener = null;
		private Eshop shop = null;
		
		private JLabel nameLabel;
		private JTextField nachnameTextField;
		
		private JLabel strasseLabel;
		private JTextField strasseTextField;
		
		private JLabel hausnummerLabel;
		private JTextField hausnummerTextField;
		
		private JLabel postleitzahlLabel;
		private JTextField postleitzahlTextField;
		
		private JLabel ortLabel;
		private JTextField ortTextField;
		
		private JLabel usernameLabel;
		private JTextField usernameTextField;
		
		private JLabel passwordLabel;
		private JTextField passwordField;
		
		private JLabel platzhalter;
		
		private JButton registrierenButton;
		
		public RegisterPanel(Eshop shop, RegisterListener registerListener) {
			this.shop = shop;
			this.registerListener = registerListener;
			
			setupUi();
			setupEvents();
		}
		
		private void setupUi(){
			int anzahlZeilen = 16;
				this.setLayout(new GridLayout(anzahlZeilen, 1));
				
				nameLabel = new JLabel("Vollständiger Name:");
				this.add(nameLabel);
				nachnameTextField = new JTextField();
				this.add(nachnameTextField);
				
				strasseLabel = new JLabel("Strasse:");
				this.add(strasseLabel);
				strasseTextField = new JTextField();
				this.add(strasseTextField);
				
				hausnummerLabel = new JLabel("Hausnummer:");
				this.add(hausnummerLabel);
				hausnummerTextField = new JTextField();
				this.add(hausnummerTextField);
				
				postleitzahlLabel = new JLabel("Postleitzahl:");
				this.add(postleitzahlLabel);
				postleitzahlTextField = new JTextField();
				this.add(postleitzahlTextField);
				
				ortLabel = new JLabel("Ort:");
				this.add(ortLabel);
				ortTextField = new JTextField();
				this.add(ortTextField);
				
				usernameLabel = new JLabel("Username:");
				this.add(usernameLabel);
				usernameTextField = new JTextField();
				this.add(usernameTextField);
				
				passwordLabel = new JLabel("Passwort:");
				this.add(passwordLabel);
				passwordField = new JTextField();
				this.add(passwordField);
				
				platzhalter = new JLabel();
				this.add(platzhalter);
				
				registrierenButton = new JButton("Registrieren");
				this.add(registrierenButton);
		}
		
		private void setupEvents() {
			registrierenButton.addActionListener(new AbschickenListener());
		}
		
		//Kunde wird registriert und gespeichert
		class AbschickenListener implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(registrierenButton)) {
				String name = nachnameTextField.getText();
				String strasse = strasseTextField.getText();
				String hausNr = hausnummerTextField.getText();
				String plz = postleitzahlTextField.getText();
				String ort = ortTextField.getText();
				String username = usernameTextField.getText();
				String passwort = passwordField.getText();
				Kunde einKunde = new Kunde(name, strasse, hausNr, plz, ort, username, passwort );	
				try {
					shop.kundenRegistrieren(einKunde);
					try {
						shop.speicherKunden();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					registerListener.regToTable();
				} catch ( KundeExistiertBereitsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
}