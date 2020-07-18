package fr.bastion.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Monitoring extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton okButton, cancelButton;
	private JLabel warning;

	public Monitoring() {

		this.setTitle("Ecran de gestion");
		this.setSize(500, 350);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout());

		// BUTTONS
		okButton = new JButton("CREER NOUVELLE INSTANCE");
		cancelButton = new JButton("QUITTER");

		// Instances List
		JPanel pInstance = new JPanel();
		pInstance.setBorder(BorderFactory.createTitledBorder("Liste des instances en cours d'execution :"));
		pInstance.setPreferredSize(new Dimension(440, 200));
		pInstance.add(new JLabel("instance A + prénom + status"));
		pInstance.add(new JButton("STOP"));

		// LABEL WARNING
		warning = new JLabel();
		warning.setText("OK");
		warning.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
		warning.setBackground(Color.BLUE);
		warning.setVisible(false);

		// ADD CONTENTPANE
		this.getContentPane().add(pInstance);
		this.getContentPane().add(okButton);
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(warning);

		// LISTENER OK
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewInstance(pInstance);
			}
		});

		// CANCEL
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		this.setVisible(true);
	}

}