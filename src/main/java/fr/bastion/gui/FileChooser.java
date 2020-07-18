package fr.bastion.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooser {

	
	public String FilePath() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choisissez un rapport � comparer: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setCurrentDirectory(new File("."));

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// if (jfc.getSelectedFile().isDirectory()) {
			System.out.println("You selected the directory: " + jfc.getSelectedFile());
			// }
			return jfc.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
}
