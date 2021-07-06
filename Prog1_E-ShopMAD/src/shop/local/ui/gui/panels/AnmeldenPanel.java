package shop.local.ui.gui.panels;

import javax.swing.JPanel;

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

import shop.local.domain.Eshop;
//import shop.local.ui.gui.awt.ShopClientGUI;
//import shop.local.valueobjects.Kunde;


public class AnmeldenPanel extends JPanel {
	
	public interface AnmeldenListener {
		public void angemeldeterUser(int a);
		public void angemeldeterMitarbeiter();
		public void regMenue();

	}
	private AnmeldenListener anmeldenListener = null; 
}
