package fr.guronzan.mediatheque;

import javax.swing.JOptionPane;

public class MainWindow {
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		final MainMediatheque mainMediatheque = new MainMediatheque();
		final Login loginWindow = new Login(mainMediatheque.getFrame());
		loginWindow.getDialogLogin().setVisible(true);

		final boolean result = loginWindow.getResult();

		if (result) {
			mainMediatheque.getFrame().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"Compte inconnu ou mot de passe invalide.", "Erreur Login",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
