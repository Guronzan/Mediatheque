package fr.guronzan.mediatheque;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lombok.extern.slf4j.Slf4j;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.webservice.DBAccess;

@Slf4j
public class MainMediatheque {
	private static final DBAccess DB_ACCESS = MediathequeApplicationContext
			.getBean(DBAccess.class);
	private final Map<String, Movie> movies = new HashMap<>();

	private JFrame frmMediatheque;
	private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
	private final JPanel musicPanel = new JPanel();
	private final JPanel moviesPanel = new JPanel();
	private final JList<String> movieList = new JList<>();
	private final JPanel mainPanel = new JPanel();
	private final JLabel lblTitle = new JLabel("Titre");
	private JTextField titleField;
	private JCheckBox chckbxSupportDvd;
	private User currentUser;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final MainMediatheque window = new MainMediatheque();
					window.setUserNick("nick");
					window.fillData();
					window.frmMediatheque.setVisible(true);
				} catch (final Exception e) {
					log.error(
							"Erreur durant l'exécution de la fenêtre principale de l'application.",
							e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMediatheque() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frmMediatheque = new JFrame();
		this.frmMediatheque.setTitle("Mediatheque");
		this.frmMediatheque.setBounds(100, 100, 582, 448);
		this.frmMediatheque.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frmMediatheque.getContentPane().add(this.tabbedPane,
				BorderLayout.NORTH);

		this.musicPanel.setName(DataType.MUSIC.getValue());
		this.tabbedPane.addTab("Musique", null, this.musicPanel, null);
		this.tabbedPane.setEnabledAt(0, true);

		this.tabbedPane.addTab("Vid\u00E9os", null, this.moviesPanel, null);
		final GridBagLayout gblMoviesPanel = new GridBagLayout();
		gblMoviesPanel.columnWidths = new int[] { 230, 0, 0 };
		gblMoviesPanel.rowHeights = new int[] { 346, 0 };
		gblMoviesPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gblMoviesPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		this.moviesPanel.setLayout(gblMoviesPanel);

		this.mainPanel.setLayout(null);

		final GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 0;
		this.moviesPanel.setName(DataType.MOVIE.getValue());
		this.moviesPanel.add(this.mainPanel, gbcPanel);
		this.lblTitle.setBounds(44, 67, 46, 14);

		this.mainPanel.add(this.lblTitle);

		this.titleField = new JTextField();
		this.titleField.setBounds(129, 64, 161, 20);
		this.mainPanel.add(this.titleField);
		this.titleField.setColumns(10);

		this.chckbxSupportDvd = new JCheckBox("Support DVD");
		this.chckbxSupportDvd.setBounds(129, 114, 97, 23);
		this.mainPanel.add(this.chckbxSupportDvd);

		final JPanel addPanel = new JPanel();
		this.frmMediatheque.getContentPane().add(addPanel, BorderLayout.SOUTH);
		final GridBagLayout gblAddPanel = new GridBagLayout();
		gblAddPanel.columnWidths = new int[] { 118, 0, 89, 105, 132, 0 };
		gblAddPanel.rowHeights = new int[] { 23, 0 };
		gblAddPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gblAddPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		addPanel.setLayout(gblAddPanel);

		final JButton btnAdd = new JButton("Ajouter Nouvel \u00E9lement");
		final GridBagConstraints gbcBtnAdd = new GridBagConstraints();
		gbcBtnAdd.anchor = GridBagConstraints.NORTH;
		gbcBtnAdd.insets = new Insets(0, 0, 0, 5);
		gbcBtnAdd.gridx = 1;
		gbcBtnAdd.gridy = 0;
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				addNewElement();
			}
		});
		addPanel.add(btnAdd, gbcBtnAdd);

		final JButton btnQuit = new JButton("Quitter");
		final GridBagConstraints gbcBtnQuit = new GridBagConstraints();
		gbcBtnQuit.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnQuit.insets = new Insets(0, 0, 0, 5);
		gbcBtnQuit.anchor = GridBagConstraints.NORTH;
		gbcBtnQuit.gridx = 3;
		gbcBtnQuit.gridy = 0;
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		addPanel.add(btnQuit, gbcBtnQuit);

		final GridBagConstraints gbcMovieList = new GridBagConstraints();
		gbcMovieList.insets = new Insets(0, 0, 0, 5);
		gbcMovieList.fill = GridBagConstraints.BOTH;
		gbcMovieList.gridx = 0;
		gbcMovieList.gridy = 0;
		this.moviesPanel.add(this.movieList, gbcMovieList);
	}

	private void addNewElement() {
		final String name = this.tabbedPane.getSelectedComponent().getName();
		final DataType dataType = DataType.valueOf(name.toUpperCase());
		switch (dataType) {
		case BOOK:
			new CreateBook(this, this.currentUser).setVisible();
			break;
		case MUSIC:
			new CreateCD(this, this.currentUser).setVisible();
			break;
		case MOVIE:
			new CreateMovie(this, this.currentUser).setVisible();
			break;
		default:
			log.error("Trying to create an unknown element type : {}", name);
			throw new IllegalArgumentException("Invalid tab Name : " + name);
		}
	}

	private void reFillMovieList() {
		final Movie selectedMovie = this.movies.get(this.movieList
				.getSelectedValue());
		this.titleField.setText(selectedMovie.getTitle());
		this.chckbxSupportDvd.setSelected(selectedMovie.isOwnedDVD());
	}

	public void populateMovieList() {
		this.currentUser = DB_ACCESS.getUserFromNickName(this.currentUser
				.getNickName());
		final List<Movie> movies = this.currentUser.getMovies();
		final DefaultListModel<String> listModel = new DefaultListModel<>();
		this.movies.clear();
		for (final Movie movie : movies) {
			listModel.addElement(movie.getTitle());
			this.movies.put(movie.getTitle(), movie);
		}
		this.movieList.setModel(listModel);
	}

	public JFrame getFrame() {
		return this.frmMediatheque;
	}

	public void setUserNick(final String nick) {
		this.currentUser = DB_ACCESS.getUserFromNickName(nick);
	}

	public void fillData() {
		populateMovieList();
		this.movieList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.movieList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				reFillMovieList();
			}
		});
	}
}
