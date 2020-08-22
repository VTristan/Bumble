package fr.bastion.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		this.setSize(600, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout());

		// BUTTONS
		okButton = new JButton("CREER NOUVELLE INSTANCE");
		cancelButton = new JButton("ARRETER LE PROGRAMME");

		// Instances List
		JPanel pInstance = new JPanel(new GridLayout(10,1));
		pInstance.setBorder(BorderFactory.createTitledBorder("Liste des instances en cours d'execution :"));
		pInstance.setPreferredSize(new Dimension(550, 300));

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
		okButton.addActionListener((e)-> new NewInstance(pInstance));

		// LISTENER CANCEL
		cancelButton.addActionListener((e) -> System.exit(0));

		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    { //INNER CLASS
		        Monitoring.this.setExtendedState(JFrame.ICONIFIED);
		    }
		});
		
		this.setVisible(true);
	}

}