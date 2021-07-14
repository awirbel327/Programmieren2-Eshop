package shop.local.ui.gui.panels;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shop.local.domain.*;
//import shop.local.ui.gui.awt.ShopClientGUI;
//import shop.local.valueobjects.Kunde;
//import shop.local.ui.gui.panels.AnmeldenPanel.AnmeldenListener;
//import shop.local.ui.gui.panels.AnmeldenPanel.addRegListener;
//import shop.local.ui.gui.panels.AnmeldenPanel.loginListener;
import shop.local.domain.exceptions.PasswortOderUsernameFalschException;
import shop.local.valueobjects.User;


public class AnmeldenPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	Eshop shop = null;
	private AnmeldenListener anmeldenListener = null; 
	private JTextField searchTextField; 
//	private JPasswordField searchTextField2;
	private JTextField searchTextField2;
	private JButton loginButton, regButton;
	private JLabel userLabel;
	private JLabel passwortLabel;

	
	public interface AnmeldenListener {
		public void userEingeloggt(User a);
		public void angemeldeterMitarbeiter();
		public void regMenue();
	}
	
	
	public AnmeldenPanel(Eshop shop, AnmeldenListener listener) {
		this.shop = shop;
		anmeldenListener = listener;
		setupUI();
		setupEvents();
	}
	
	
	private void setupUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		
		userLabel = new JLabel("Username: ");
		c.gridx = 0;
		c.weightx = 0.01;
		c.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(userLabel, c);
		this.add(userLabel);
		
		searchTextField = new JTextField();
		searchTextField.setToolTipText("Username eingeben");
		c.gridx = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(searchTextField, c);
		this.add(searchTextField);
		
		passwortLabel = new JLabel("Passwort: ");
		c.gridx = 2;
		c.weightx = 0.01;
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(passwortLabel, c);
		this.add(passwortLabel);
		
		searchTextField2 = new JTextField();
		searchTextField2.setToolTipText("Passwort eingeben");
		c.gridx = 3;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(searchTextField2, c);
		this.add(searchTextField2);
		
		loginButton = new JButton("Login");
		c.gridx = 4;
		c.weightx = 0.2;
		gridBagLayout.setConstraints(loginButton, c);
		this.add(loginButton);

		regButton = new JButton("Registrieren");
		c.gridx = 5;
		c.weightx = 0.2;
		gridBagLayout.setConstraints(regButton, c);
		this.add(regButton);

		setBorder(BorderFactory.createTitledBorder("Hier kÃ¶nnen Sie sich anmelden!"));
	}
	
	
	private void setupEvents() {
		loginButton.addActionListener(new loginListener());
		loginButton.addKeyListener(new loginListener());
//		searchTextField2.addKeyListener(new loginListener());
		regButton.addActionListener(new addRegListener());
	}
	
	
	class loginListener implements ActionListener, KeyListener{
		
		//Button 
		public void actionPerformed (ActionEvent ae) {
			if (ae.getSource().equals(loginButton)) {
				String username = searchTextField.getText();
				String passwort= searchTextField2.getText();
				try {
					shop.userLogIn(username, passwort);
					User a = shop.userLogIn(username, passwort);
						anmeldenListener.userEingeloggt(a);
					
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
				}
	        }
				}
				//System.out.println();
	
		
		

		//eig weg ?
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		// Wenn man enter drückt bei LoginButton
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode()==KeyEvent.VK_ENTER){		//quelle = Enter 
				String username = searchTextField.getText();
				String passwort= searchTextField2.getText();
				try {
					shop.userLogIn(username, passwort);
					User a = shop.userLogIn(username, passwort);
					if (a != null)  {
						anmeldenListener.userEingeloggt(a);
				} else {
					anmeldenListener.userEingeloggt(a);
				}
					
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
				}
	        }
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	public class addRegListener implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			if (ae.getSource().equals(regButton)) {
				anmeldenListener.regMenue();
			}
		}
	}
}
