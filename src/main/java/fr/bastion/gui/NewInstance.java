package fr.bastion.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	private JRadioButton tinder, bumble, happn, once, fruits;
	private JTextField name, cookie, delay, period, longitude, latitude, rayon, distance;
	ButtonGroup bg;
	private List<JRadioButton> list;

	public NewInstance(JPanel pInstance) {

		this.setTitle("Création d'une nouvelle instance");
		this.setSize(500, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));

		//LIST
		list = new ArrayList<JRadioButton>(5);
		
		//BUTTONS
		okButton = new JButton("CREER");
		cancelButton = new JButton("ANNULER");
		
		//SITES LIST
		JPanel pSite = new JPanel();
		pSite.setBorder(BorderFactory.createTitledBorder("Application :"));
		pSite.setPreferredSize(new Dimension(440, 60));
		tinder = new JRadioButton("Tinder");
		bumble = new JRadioButton("Bumble");
		bumble.setSelected(true);
		happn = new JRadioButton("Happn");
		once = new JRadioButton("Once");
		fruits = new JRadioButton("Fruits");
		bg = new ButtonGroup();
		bg.add(tinder);bg.add(bumble);bg.add(happn);bg.add(once);bg.add(fruits);
		pSite.add(tinder);pSite.add(bumble);pSite.add(happn);pSite.add(once);pSite.add(fruits);
		list.add(tinder);list.add(bumble);list.add(happn);list.add(once);list.add(fruits);
		
		//NAME + COOKIES
		JPanel pUser = new JPanel();
		pUser.setBorder(BorderFactory.createTitledBorder("Utilisateur :"));
		pUser.setPreferredSize(new Dimension(440, 100));
		JPanel line1 = new JPanel(); JPanel line2 = new JPanel();
		line1.add(new JLabel("Nom de l'instance:", SwingConstants.CENTER));
		name = new JTextField("1:ISTNC-"+LocalDateTime.now().toLocalDate(), 12);
		line1.add(name);
		line2.add(new JLabel("Cookies :",SwingConstants.CENTER));
		cookie = new JTextField("s2:427:gTxCGeazuXJH00bZVnGsW5pe7I7YtOf6U26sWc6M", 32);
		line2.add(cookie);
		pUser.add(line1);
		pUser.add(line2);
		
		//DELAY + PERIOD
		JPanel pTimer = new JPanel();
		pTimer.setBorder(BorderFactory.createTitledBorder("Périodicité :"));
		pTimer.setPreferredSize(new Dimension(440, 70));
		pTimer.setLayout(new GridLayout(2, 3));
		pTimer.add(new JLabel("Dans un délai de: ",SwingConstants.RIGHT));
		delay = new JTextField("1");
		pTimer.add(delay);
		pTimer.add(new JLabel(" minute(s)",SwingConstants.LEFT));
		pTimer.add(new JLabel("Par période de: ",SwingConstants.RIGHT));
		period = new JTextField("5");
		pTimer.add(period);
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
		
		// Add to ContentPane
		this.getContentPane().add(pSite);
		this.getContentPane().add(pUser);
		this.getContentPane().add(pTimer);
		this.getContentPane().add(pLocation);
		this.getContentPane().add(okButton);
		this.getContentPane().add(cancelButton);

		// Listener OK
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String app = null;
				for (JRadioButton jRadioButton : list) {
					app = (jRadioButton.isSelected()) ? jRadioButton.getText() : app;
				}
				JPanel jPanel = new JPanel(new GridLayout(2,1));
				jPanel.add(new JLabel(name.getText()+ " : ",SwingConstants.CENTER));
				JLabel label = new JLabel(app+" ("+latitude.getText()+","+longitude.getText()+") ["+rayon.getText()+","+distance.getText()+"] {"+delay.getText()+","+ period.getText()+"}  '"+cookie.getText()+"'",SwingConstants.CENTER);
				label.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
				//TODO: STOP BUTTON

				jPanel.add(label);
				pInstance.add(jPanel);
				pInstance.revalidate();
				pInstance.repaint();
				dispose();
			}});
		
		// CANCEL
		cancelButton.addActionListener((e) -> dispose());
		
		this.setVisible(true);
	}

}