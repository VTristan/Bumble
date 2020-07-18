package fr.bastion.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NewInstance extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton okButton, cancelButton;
	private JRadioButton choice1, choice2, choice3, choice4, choice5;
	private JTextField tfName, tfCookie, tfDelay, tfPeriod, longitude, latitude, rayon, distance;
	private JLabel warning;
	Map<String, String>listPath;

	public NewInstance(JPanel pInstance) {

		this.setTitle("Création d'une nouvelle instance");
		this.setSize(500, 400);
//		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));

		//BUTTONS
		okButton = new JButton("CREER");
		cancelButton = new JButton("ANNULER");
		
		//SITES LIST
		JPanel pSite = new JPanel();
		pSite.setBorder(BorderFactory.createTitledBorder("Application :"));
		pSite.setPreferredSize(new Dimension(440, 60));
		choice1 = new JRadioButton("TINDER");
		choice2 = new JRadioButton("BUMBLE");
		choice2.setSelected(true);
		choice3 = new JRadioButton("HAPPN");
		choice4 = new JRadioButton("ONCE");
		choice5 = new JRadioButton("FRUITS");
		ButtonGroup bg = new ButtonGroup();
		bg.add(choice1); pSite.add(choice1);
		bg.add(choice2); pSite.add(choice2);
		bg.add(choice3); pSite.add(choice3);
		bg.add(choice4); pSite.add(choice4);
		bg.add(choice5); pSite.add(choice5);
		
		//NAME + COOKIES
		JPanel pUser = new JPanel();
		pUser.setBorder(BorderFactory.createTitledBorder("Utilisateur :"));
		pUser.setPreferredSize(new Dimension(440, 100));
		JPanel line1 = new JPanel(); JPanel line2 = new JPanel();
		line1.add(new JLabel("Nom de l'instance:", SwingConstants.CENTER));
		tfName = new JTextField("INSTANCE-1", 8);
		line1.add(tfName);
		line2.add(new JLabel("Cookies :",SwingConstants.CENTER));
		tfCookie = new JTextField("s2:427:gTxCGeazuXJH00bZVnGsW5pe7I7YtOf6U26sWc6M", 32);
		line2.add(tfCookie);
		pUser.add(line1);
		pUser.add(line2);
		
		//DELAY + PERIOD
		JPanel pTimer = new JPanel();
		pTimer.setBorder(BorderFactory.createTitledBorder("Périodicité :"));
		pTimer.setPreferredSize(new Dimension(440, 70));
		pTimer.setLayout(new GridLayout(2, 3));
		pTimer.add(new JLabel("Dans un délai de: ",SwingConstants.RIGHT));
		tfDelay = new JTextField("1");
		pTimer.add(tfDelay);
		pTimer.add(new JLabel(" minute(s)",SwingConstants.LEFT));
		pTimer.add(new JLabel("Par période de: ",SwingConstants.RIGHT));
		tfPeriod = new JTextField("5");
		pTimer.add(tfPeriod);
		pTimer.add(new JLabel(" minute(s)",SwingConstants.LEFT));
		
		//Location
		JPanel pLocation = new JPanel();
		pLocation.setBorder(BorderFactory.createTitledBorder("Géolocalisation :"));
		pLocation.setPreferredSize(new Dimension(440, 70));
		pLocation.setLayout(new GridLayout(2, 4));

		pLocation.add(new JLabel("latitude: ",SwingConstants.CENTER));
		latitude = new JTextField("37,774929");
		pLocation.add(latitude);
		pLocation.add(new JLabel("longitude: ",SwingConstants.CENTER));
		longitude = new JTextField("-122,419416");
		pLocation.add(longitude);
		pLocation.add(new JLabel("rayon: ",SwingConstants.CENTER));
		rayon = new JTextField("5");
		pLocation.add(rayon);
		pLocation.add(new JLabel("distance: ",SwingConstants.CENTER));
		distance = new JTextField("1");
		pLocation.add(distance);
		
		// Label Warning
		warning = new JLabel("WARNING",SwingConstants.CENTER);
		warning.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
		warning.setVisible(false);
		
		// Add to ContentPane
		this.getContentPane().add(pSite);
		this.getContentPane().add(pUser);
		this.getContentPane().add(pTimer);
		this.getContentPane().add(pLocation);
		this.getContentPane().add(okButton);
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(warning);

		// Listener OK
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				warning.setVisible(true);

				System.out.println(tfName.getText());
				System.out.println(tfCookie.getText());
				System.out.println(tfDelay.getText());
				System.out.println(tfPeriod.getText());
				System.out.println(longitude.getText());
				System.out.println(latitude.getText());
				System.out.println(rayon.getText());
				System.out.println(distance.getText());
				//pInstance.getParent().add(pInstance.add(new JLabel("SITE " + tfName.getText()+ " ACTIF ")));
				//repaint();
			}});
		
		// CANCEL
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}});
		
		this.setVisible(true);
	}

}