package fr.guronzan.mediatheque;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import lombok.extern.slf4j.Slf4j;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.webservice.DBAccess;

@Slf4j
public class CreateMovie {
	private static final DBAccess DB_ACCESS = MediathequeApplicationContext
			.getBean(DBAccess.class);

	private JFrame frame;
	private JTextField titleField;
	private JTextField directorField;
	private JTextField releaseDateField;

	private JSpinner tomeSpinner;
	private File picture = null;

	private final JCheckBox ownedDvDCheckbox = new JCheckBox("");

	private final User currentUser;

	private final MainMediatheque parent;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final CreateMovie window = new CreateMovie(null, DB_ACCESS
							.getUserFromNickName("nick"));
					window.frame.setVisible(true);
				} catch (final Exception e) {
					log.error("Error while creating new movie.", e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param mainMediatheque
	 * 
	 * @param currentUser
	 */
	public CreateMovie(final MainMediatheque mainMediatheque,
			final User currentUser) {
		this.currentUser = currentUser;
		this.parent = mainMediatheque;
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
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		this.frame.getContentPane().setLayout(gridBagLayout);

		final JLabel lblTitle = new JLabel("Titre");
		final GridBagConstraints gbcLblTitle = new GridBagConstraints();
		gbcLblTitle.insets = new Insets(0, 0, 5, 5);
		gbcLblTitle.gridx = 0;
		gbcLblTitle.gridy = 1;
		this.frame.getContentPane().add(lblTitle, gbcLblTitle);

		this.titleField = new JTextField();
		this.titleField.setText("...");
		final GridBagConstraints gbcTitleField = new GridBagConstraints();
		gbcTitleField.fill = GridBagConstraints.HORIZONTAL;
		gbcTitleField.insets = new Insets(0, 0, 5, 0);
		gbcTitleField.gridx = 1;
		gbcTitleField.gridy = 1;
		this.frame.getContentPane().add(this.titleField, gbcTitleField);
		this.titleField.setColumns(10);

		final JLabel lblRalisateur = new JLabel("R\u00E9alisateur");
		final GridBagConstraints gbcLblRalisateur = new GridBagConstraints();
		gbcLblRalisateur.insets = new Insets(0, 0, 5, 5);
		gbcLblRalisateur.gridx = 0;
		gbcLblRalisateur.gridy = 2;
		this.frame.getContentPane().add(lblRalisateur, gbcLblRalisateur);

		this.directorField = new JTextField();
		this.directorField.setText("...");
		final GridBagConstraints gbcDirectorField = new GridBagConstraints();
		gbcDirectorField.insets = new Insets(0, 0, 5, 0);
		gbcDirectorField.fill = GridBagConstraints.HORIZONTAL;
		gbcDirectorField.gridx = 1;
		gbcDirectorField.gridy = 2;
		this.frame.getContentPane().add(this.directorField, gbcDirectorField);
		this.directorField.setColumns(10);

		final JLabel lblReleaseDate = new JLabel("Date de sortie");
		final GridBagConstraints gbcLblReleaseDate = new GridBagConstraints();
		gbcLblReleaseDate.insets = new Insets(0, 0, 5, 5);
		gbcLblReleaseDate.gridx = 0;
		gbcLblReleaseDate.gridy = 3;
		this.frame.getContentPane().add(lblReleaseDate, gbcLblReleaseDate);

		this.releaseDateField = new JTextField();
		this.releaseDateField.setText("...");
		final GridBagConstraints gbc_releaseDateField = new GridBagConstraints();
		gbc_releaseDateField.insets = new Insets(0, 0, 5, 0);
		gbc_releaseDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_releaseDateField.gridx = 1;
		gbc_releaseDateField.gridy = 3;
		this.frame.getContentPane().add(this.releaseDateField,
				gbc_releaseDateField);
		this.releaseDateField.setColumns(10);

		final JButton btnCreer = new JButton("Cr\u00E9er");
		final GridBagConstraints gbcBtnCreer = new GridBagConstraints();
		gbcBtnCreer.insets = new Insets(0, 0, 0, 5);
		gbcBtnCreer.gridx = 0;
		gbcBtnCreer.gridy = 7;
		btnCreer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					if (createMovie()) {
						if (CreateMovie.this.parent != null) {
							CreateMovie.this.parent.populateMovieList();
						}
						CreateMovie.this.frame.dispose();
					}
				} catch (final IOException e1) {
					JOptionPane.showMessageDialog(
							null,
							"Erreur durant la création du film : "
									+ e1.getMessage(), "Erreur création film",
							JOptionPane.ERROR_MESSAGE);
					CreateMovie.log
							.error("Error while creating new movie.", e1);
				}
			}
		});

		final JLabel lblOwnedDvD = new JLabel("Support DVD");
		final GridBagConstraints gbcLblOwnedDvD = new GridBagConstraints();
		gbcLblOwnedDvD.insets = new Insets(0, 0, 5, 5);
		gbcLblOwnedDvD.gridx = 0;
		gbcLblOwnedDvD.gridy = 4;
		this.frame.getContentPane().add(lblOwnedDvD, gbcLblOwnedDvD);

		final GridBagConstraints gbcOwnedDvDCheckbox = new GridBagConstraints();
		gbcOwnedDvDCheckbox.insets = new Insets(0, 0, 5, 0);
		gbcOwnedDvDCheckbox.gridx = 1;
		gbcOwnedDvDCheckbox.gridy = 4;
		this.frame.getContentPane().add(this.ownedDvDCheckbox,
				gbcOwnedDvDCheckbox);
		this.frame.getContentPane().add(btnCreer, gbcBtnCreer);

		final JLabel lblNumber = new JLabel("Saison / Num\u00E9ro");
		final GridBagConstraints gbcLblNumber = new GridBagConstraints();
		gbcLblNumber.insets = new Insets(0, 0, 5, 5);
		gbcLblNumber.gridx = 0;
		gbcLblNumber.gridy = 5;
		this.frame.getContentPane().add(lblNumber, gbcLblNumber);

		this.tomeSpinner = new JSpinner();
		this.tomeSpinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		final GridBagConstraints gbcTomeSpinner = new GridBagConstraints();
		gbcTomeSpinner.insets = new Insets(0, 0, 5, 0);
		gbcTomeSpinner.gridx = 1;
		gbcTomeSpinner.gridy = 5;
		this.frame.getContentPane().add(this.tomeSpinner, gbcTomeSpinner);

		final JLabel lblPicture = new JLabel("Affiche");
		final GridBagConstraints gbcLblPicture = new GridBagConstraints();
		gbcLblPicture.insets = new Insets(0, 0, 5, 5);
		gbcLblPicture.gridx = 0;
		gbcLblPicture.gridy = 6;
		this.frame.getContentPane().add(lblPicture, gbcLblPicture);

		final JButton btnSelectionnerImage = new JButton("Selectionner image");
		final GridBagConstraints gbcBtnSelectionnerImage = new GridBagConstraints();
		gbcBtnSelectionnerImage.insets = new Insets(0, 0, 5, 0);
		gbcBtnSelectionnerImage.gridx = 1;
		gbcBtnSelectionnerImage.gridy = 6;
		this.frame.getContentPane().add(btnSelectionnerImage,
				gbcBtnSelectionnerImage);

		btnSelectionnerImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new ImageFileFilter());
				final int result = fileChooser
						.showOpenDialog(CreateMovie.this.frame);

				if (result == JFileChooser.APPROVE_OPTION) {
					CreateMovie.this.picture = fileChooser.getSelectedFile();
				}
			}
		});

		final JButton btnQuitter = new JButton("Quitter");
		final GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.gridx = 1;
		gbcBtnQuitter.gridy = 7;
		btnQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		this.frame.getContentPane().add(btnQuitter, gbcBtnQuitter);
	}

	private boolean createMovie() throws IOException {
		final Integer tomeValue = (Integer) this.tomeSpinner.getValue();
		final boolean movieExists;
		if (tomeValue == 0) {
			movieExists = DB_ACCESS.containsMovie(this.titleField.getText(),
					null);
		} else {
			movieExists = DB_ACCESS.containsMovie(this.titleField.getText(),
					tomeValue);
		}

		if (movieExists) {
			JOptionPane
					.showMessageDialog(
							null,
							"Titre et tome déjà existant, veuillez le choisir dans la liste",
							"Erreur création Livre", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		final String title = this.titleField.getText();
		if (title.equals("...") || title.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Veuillez renseigner un Titre.", "Erreur création film",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		final String directorName = this.directorField.getText();
		if (directorName.equals("...") || directorName.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Veuillez renseigner un auteur.", "Erreur création film",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		final Movie movie = new Movie(title);
		movie.setDirectorName(directorName);
		movie.addPicture(this.picture);
		movie.setOwnedDVD(this.ownedDvDCheckbox.isSelected());
		this.currentUser.addMovie(movie);
		// TODO : mettre un vrai calendrier
		// book.setReleaseDate(this.dateField.getValue());

		DB_ACCESS.addMovie(movie);
		DB_ACCESS.updateUser(this.currentUser);

		JOptionPane.showMessageDialog(null,
				"Création du film " + movie.getTitle()
						+ " réalisée avec succès.", "Création réussie",
				JOptionPane.INFORMATION_MESSAGE);
		log.info("Movie {} created sucessfully at {}", movie, new Date());
		return true;
	}

	public void setVisible() {
		this.frame.setVisible(true);
	}
}
