package fr.guronzan.mediatheque;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

public class CreateUser {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CreateUser.class);
	private static final UserDao USER_DAO = MediathequeApplicationContext
			.getBean(UserDao.class);

	private JFrame frame;
	private JTextField nameField;
	private JTextField forNameField;
	private JTextField nickField;
	private JPasswordField passwordField;
	private JButton btnCreer;
	private JButton btnQuitter;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final CreateUser window = new CreateUser();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					LOGGER.error(
							"Erreur sur la fenêtre de création des utilisateurs.",
							e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame
				.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 118, 261, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		this.frame.getContentPane().setLayout(gridBagLayout);

		final JLabel lblNom = new JLabel("Nom");
		final GridBagConstraints gbcLblNom = new GridBagConstraints();
		gbcLblNom.anchor = GridBagConstraints.EAST;
		gbcLblNom.insets = new Insets(0, 0, 5, 5);
		gbcLblNom.gridx = 0;
		gbcLblNom.gridy = 1;
		this.frame.getContentPane().add(lblNom, gbcLblNom);

		this.nameField = new JTextField();
		this.nameField.setText("...");
		final GridBagConstraints gbcNameField = new GridBagConstraints();
		gbcNameField.fill = GridBagConstraints.HORIZONTAL;
		gbcNameField.insets = new Insets(0, 0, 5, 0);
		gbcNameField.gridx = 1;
		gbcNameField.gridy = 1;
		this.frame.getContentPane().add(this.nameField, gbcNameField);
		this.nameField.setColumns(10);

		final JLabel lblPrnom = new JLabel("Pr\u00E9nom");
		final GridBagConstraints gbcLblPrnom = new GridBagConstraints();
		gbcLblPrnom.anchor = GridBagConstraints.EAST;
		gbcLblPrnom.insets = new Insets(0, 0, 5, 5);
		gbcLblPrnom.gridx = 0;
		gbcLblPrnom.gridy = 2;
		this.frame.getContentPane().add(lblPrnom, gbcLblPrnom);

		this.forNameField = new JTextField();
		this.forNameField.setText("...");
		final GridBagConstraints gbcForNameField = new GridBagConstraints();
		gbcForNameField.insets = new Insets(0, 0, 5, 0);
		gbcForNameField.fill = GridBagConstraints.HORIZONTAL;
		gbcForNameField.gridx = 1;
		gbcForNameField.gridy = 2;
		this.frame.getContentPane().add(this.forNameField, gbcForNameField);
		this.forNameField.setColumns(10);

		final JLabel lblPseudo = new JLabel("Pseudo");
		final GridBagConstraints gbcLblPseudo = new GridBagConstraints();
		gbcLblPseudo.anchor = GridBagConstraints.EAST;
		gbcLblPseudo.insets = new Insets(0, 0, 5, 5);
		gbcLblPseudo.gridx = 0;
		gbcLblPseudo.gridy = 3;
		this.frame.getContentPane().add(lblPseudo, gbcLblPseudo);

		this.nickField = new JTextField();
		this.nickField.setText("...");
		final GridBagConstraints gbcNickField = new GridBagConstraints();
		gbcNickField.insets = new Insets(0, 0, 5, 0);
		gbcNickField.fill = GridBagConstraints.HORIZONTAL;
		gbcNickField.gridx = 1;
		gbcNickField.gridy = 3;
		this.frame.getContentPane().add(this.nickField, gbcNickField);
		this.nickField.setColumns(10);

		final JLabel lblMotDePasse = new JLabel("Mot de passe");
		final GridBagConstraints gbcLblMotDePasse = new GridBagConstraints();
		gbcLblMotDePasse.anchor = GridBagConstraints.EAST;
		gbcLblMotDePasse.insets = new Insets(0, 0, 5, 5);
		gbcLblMotDePasse.gridx = 0;
		gbcLblMotDePasse.gridy = 4;
		this.frame.getContentPane().add(lblMotDePasse, gbcLblMotDePasse);

		this.passwordField = new JPasswordField();
		final GridBagConstraints gbcPasswordField = new GridBagConstraints();
		gbcPasswordField.insets = new Insets(0, 0, 5, 0);
		gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbcPasswordField.gridx = 1;
		gbcPasswordField.gridy = 4;
		this.frame.getContentPane().add(this.passwordField, gbcPasswordField);

		this.btnCreer = new JButton("Cr\u00E9er");
		final GridBagConstraints gbcBtnCreer = new GridBagConstraints();
		gbcBtnCreer.insets = new Insets(0, 0, 0, 5);
		gbcBtnCreer.gridx = 0;
		gbcBtnCreer.gridy = 7;
		this.btnCreer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createUser();
			}
		});
		this.frame.getContentPane().add(this.btnCreer, gbcBtnCreer);

		this.btnQuitter = new JButton("Quitter");
		final GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.gridx = 1;
		gbcBtnQuitter.gridy = 7;
		this.btnQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		this.frame.getContentPane().add(this.btnQuitter, gbcBtnQuitter);
	}

	private void createUser() {
		final boolean userExists = USER_DAO.containsUser(this.nickField
				.getText());
		if (userExists) {
			JOptionPane.showMessageDialog(null,
					"Pseudo déjà existant, veuillez en choisir un autre.",
					"Erreur création compte", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.user = new User(this.nameField.getText(),
				this.forNameField.getText(), this.nickField.getText(),
				String.valueOf(this.passwordField.getPassword()), new Date());
		USER_DAO.createOrUpdate(this.user);
	}
}
