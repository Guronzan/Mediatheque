package fr.guronzan.mediatheque.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lombok.extern.slf4j.Slf4j;

import com.toedter.calendar.JCalendar;

import fr.guronzan.mediatheque.MediathequeApplicationContext;
import fr.guronzan.mediatheque.gui.createDialog.CreateDialog;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.CD;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.mappingclasses.domain.types.DataType;
import fr.guronzan.mediatheque.webservice.DBAccess;

@Slf4j
public class MainMediatheque {
	private static final DBAccess DB_ACCESS = MediathequeApplicationContext
			.getBean(DBAccess.class);
	private final Map<String, Movie> movies = new HashMap<>();
	private final Map<String, Book> books = new HashMap<>();
	private final Map<String, CD> cds = new HashMap<>();

	private JFrame frmMediatheque;
	private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
	private final JPanel musicPanel = new JPanel();
	private final JPanel moviesPanel = new JPanel();
	private final JPanel mainMoviePanel = new JPanel();
	private final JPanel mainBookPanel = new JPanel();
	private final JPanel mainCDPanel = new JPanel();
	private final JLabel movieLblTitle = new JLabel("Titre");
	private final JLabel movieLblPicture = new JLabel("");

	private JTextField movieTitleField;
	private JCheckBox movieChckbxSupportDvd;
	private String currentUserNick;
	private JTextField movieDirectorField;
	private final JComboBox<String> existingElementBox = new JComboBox<>();
	private final JButton btnAddExisting = new JButton(
			"Ajouter l'\u00E9l\u00E9ment s\u00E9lectionn\u00E9");
	private final JPanel bookPanel = new JPanel();
	private final JList<String> movieList = new JList<>();
	private final JList<String> bookList = new JList<>();
	private final JList<String> cdList = new JList<>();
	private final JLabel cdLblTitreAlbum = new JLabel("Titre album");
	private final JTextField cdTitleField = new JTextField();
	private final JLabel cdLblAuthor = new JLabel("Auteur");
	private final JTextField cdAuthorField = new JTextField();
	private final JLabel cdLblReleaseDate = new JLabel("Date de sortie");
	private final JLabel cdLblPicture = new JLabel("");
	private final JLabel cdLblKind = new JLabel("Genre");
	private final JTextField cdKindField = new JTextField();
	private final JLabel bookLblTitle = new JLabel("Titre");
	private final JTextField bookTitleField = new JTextField();
	private final JLabel bookLblAuthor = new JLabel("Auteur");
	private final JTextField bookAuthorField = new JTextField();
	private final JLabel lblDateDePublication = new JLabel(
			"Date de publication");
	private final JCalendar movieReleaseDateField = new JCalendar();
	private final JCalendar cdReleaseDateField = new JCalendar();
	private final JCalendar bookReleaseDateField = new JCalendar();
	private final JLabel bookLblPicture = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final MainMediatheque window = new MainMediatheque();
					window.currentUserNick = "nick";
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
		this.musicPanel.setName(DataType.MUSIC.getValue());
		this.bookPanel.setName(DataType.BOOK.getValue());
		this.moviesPanel.setName(DataType.MOVIE.getValue());

		this.bookReleaseDateField.setBounds(190, 165, 182, 20);
		this.bookAuthorField.setBounds(190, 109, 182, 20);
		this.bookAuthorField.setColumns(10);
		this.bookTitleField.setBounds(190, 66, 182, 20);
		this.bookTitleField.setColumns(10);
		this.cdReleaseDateField.setBounds(133, 159, 178, 20);
		this.cdAuthorField.setBounds(133, 104, 178, 20);
		this.cdAuthorField.setColumns(10);
		this.cdTitleField.setBounds(133, 79, 178, 20);
		this.cdTitleField.setColumns(10);
		this.frmMediatheque = new JFrame();
		this.frmMediatheque.setTitle("Mediatheque");
		this.frmMediatheque.setBounds(100, 100, 1112, 455);
		this.frmMediatheque.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmMediatheque.setLocationRelativeTo(null);

		this.frmMediatheque.getContentPane().add(this.tabbedPane,
				BorderLayout.NORTH);

		this.tabbedPane.addTab("Musique", null, this.musicPanel, null);
		final GridBagLayout gblMusicPanel = new GridBagLayout();
		gblMusicPanel.columnWidths = new int[] { 284, 0, 0 };
		gblMusicPanel.rowHeights = new int[] { 0, 0 };
		gblMusicPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gblMusicPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		this.musicPanel.setLayout(gblMusicPanel);

		final GridBagConstraints gbcCdList = new GridBagConstraints();
		gbcCdList.insets = new Insets(0, 0, 0, 5);
		gbcCdList.fill = GridBagConstraints.BOTH;
		gbcCdList.gridx = 0;
		gbcCdList.gridy = 0;
		this.cdList
		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.musicPanel.add(this.cdList, gbcCdList);
		this.mainCDPanel.setLayout(null);

		final GridBagConstraints gbcMainCDPanel = new GridBagConstraints();
		gbcMainCDPanel.fill = GridBagConstraints.BOTH;
		gbcMainCDPanel.gridx = 1;
		gbcMainCDPanel.gridy = 0;
		this.musicPanel.add(this.mainCDPanel, gbcMainCDPanel);
		this.cdLblPicture.setBounds(339, 32, 289, 275);
		this.mainCDPanel.add(this.cdLblPicture);
		this.cdLblTitreAlbum.setHorizontalAlignment(SwingConstants.RIGHT);
		this.cdLblTitreAlbum.setBounds(21, 82, 102, 14);
		this.mainCDPanel.add(this.cdLblTitreAlbum);
		this.mainCDPanel.add(this.cdTitleField);
		this.cdLblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
		this.cdLblAuthor.setBounds(31, 107, 92, 14);
		this.mainCDPanel.add(this.cdLblAuthor);
		this.mainCDPanel.add(this.cdAuthorField);
		this.cdLblReleaseDate.setHorizontalAlignment(SwingConstants.RIGHT);
		this.cdLblReleaseDate.setBounds(30, 162, 93, 14);
		this.mainCDPanel.add(this.cdLblReleaseDate);
		this.cdReleaseDateField.setEnabled(false);
		this.mainCDPanel.add(this.cdReleaseDateField);
		this.cdLblKind.setHorizontalAlignment(SwingConstants.RIGHT);
		this.cdLblKind.setBounds(37, 199, 86, 14);

		this.mainCDPanel.add(this.cdLblKind);

		this.cdKindField.setBounds(133, 196, 86, 20);
		this.mainCDPanel.add(this.cdKindField);
		this.cdKindField.setColumns(10);
		this.tabbedPane.setEnabledAt(0, true);
		this.tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				changeTabAction();
			}
		});

		this.tabbedPane.addTab("Vid\u00E9os", null, this.moviesPanel, null);
		this.tabbedPane.setEnabledAt(1, true);
		final GridBagLayout gblMoviesPanel = new GridBagLayout();
		gblMoviesPanel.columnWidths = new int[] { 230, 0, 0 };
		gblMoviesPanel.rowHeights = new int[] { 346, 0 };
		gblMoviesPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gblMoviesPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		this.moviesPanel.setLayout(gblMoviesPanel);

		this.mainMoviePanel.setLayout(null);

		final GridBagConstraints gbcMainMoviePanel = new GridBagConstraints();
		gbcMainMoviePanel.fill = GridBagConstraints.BOTH;
		gbcMainMoviePanel.gridx = 1;
		gbcMainMoviePanel.gridy = 0;
		this.moviesPanel.add(this.mainMoviePanel, gbcMainMoviePanel);
		this.movieLblTitle.setBounds(10, 36, 46, 14);

		this.mainMoviePanel.add(this.movieLblTitle);

		this.movieTitleField = new JTextField();
		this.movieTitleField.setEditable(false);
		this.movieTitleField.setBounds(101, 33, 229, 20);
		this.mainMoviePanel.add(this.movieTitleField);
		this.movieTitleField.setColumns(10);

		this.movieChckbxSupportDvd = new JCheckBox("Support DVD");
		this.movieChckbxSupportDvd.setBounds(101, 167, 97, 23);
		this.movieChckbxSupportDvd.setEnabled(false);
		this.mainMoviePanel.add(this.movieChckbxSupportDvd);

		final JLabel movieLblDirector = new JLabel("R\u00E9alisateur");
		movieLblDirector.setBounds(10, 72, 71, 14);
		this.mainMoviePanel.add(movieLblDirector);

		this.movieDirectorField = new JTextField();
		this.movieDirectorField.setEditable(false);
		this.movieDirectorField.setBounds(101, 69, 229, 20);
		this.mainMoviePanel.add(this.movieDirectorField);
		this.movieDirectorField.setColumns(10);

		this.movieLblPicture.setBounds(374, 36, 286, 299);
		this.mainMoviePanel.add(this.movieLblPicture);

		final JLabel movieLblReleaseDate = new JLabel("Date de sortie");
		movieLblReleaseDate.setBounds(10, 131, 86, 14);
		this.mainMoviePanel.add(movieLblReleaseDate);

		this.movieReleaseDateField.setEnabled(false);
		this.movieReleaseDateField.setBounds(101, 128, 184, 20);
		this.mainMoviePanel.add(this.movieReleaseDateField);

		final JPanel addPanel = new JPanel();
		this.frmMediatheque.getContentPane().add(addPanel, BorderLayout.SOUTH);
		final GridBagLayout gblAddPanel = new GridBagLayout();
		gblAddPanel.columnWidths = new int[] { 118, 0, 262, 105, 132, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gblAddPanel.rowHeights = new int[] { 23, 0 };
		gblAddPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gblAddPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		addPanel.setLayout(gblAddPanel);

		final JLabel lblAddExisting = new JLabel("Ajouter Element existant");
		final GridBagConstraints gbcLblAddExisting = new GridBagConstraints();
		gbcLblAddExisting.anchor = GridBagConstraints.WEST;
		gbcLblAddExisting.insets = new Insets(0, 0, 0, 5);
		gbcLblAddExisting.gridx = 0;
		gbcLblAddExisting.gridy = 0;
		addPanel.add(lblAddExisting, gbcLblAddExisting);

		final GridBagConstraints gbcExistingElementBox = new GridBagConstraints();
		gbcExistingElementBox.fill = GridBagConstraints.HORIZONTAL;
		gbcExistingElementBox.insets = new Insets(0, 0, 0, 5);
		gbcExistingElementBox.gridx = 2;
		gbcExistingElementBox.gridy = 0;
		addPanel.add(this.existingElementBox, gbcExistingElementBox);

		final JButton btnQuit = new JButton("Quitter");
		final GridBagConstraints gbcBtnQuit = new GridBagConstraints();
		gbcBtnQuit.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnQuit.anchor = GridBagConstraints.NORTH;
		gbcBtnQuit.gridx = 15;
		gbcBtnQuit.gridy = 0;
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});

		final JButton btnAdd = new JButton("Ajouter Nouvel \u00E9lement");
		final GridBagConstraints gbcBtnAdd = new GridBagConstraints();
		gbcBtnAdd.anchor = GridBagConstraints.NORTH;
		gbcBtnAdd.insets = new Insets(0, 0, 0, 5);
		gbcBtnAdd.gridx = 10;
		gbcBtnAdd.gridy = 0;
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					addNewElement();
				} catch (NoSuchMethodException | SecurityException
						| InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e1) {
					log.error("Can't create new instance for CreateDialog", e1);
					JOptionPane
					.showMessageDialog(null,
							"Erreur durant l'ouverture de la fenêtre de création de l'objet. "
									+ e1.getMessage(),
									"Erreur création objet.",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		this.btnAddExisting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				addExistingElement();
			}
		});
		final GridBagConstraints gbcBtnAddExisting = new GridBagConstraints();
		gbcBtnAddExisting.insets = new Insets(0, 0, 0, 5);
		gbcBtnAddExisting.gridx = 4;
		gbcBtnAddExisting.gridy = 0;
		addPanel.add(this.btnAddExisting, gbcBtnAddExisting);
		addPanel.add(btnAdd, gbcBtnAdd);
		addPanel.add(btnQuit, gbcBtnQuit);

		final GridBagConstraints gbcMovieList = new GridBagConstraints();
		gbcMovieList.insets = new Insets(0, 0, 0, 5);
		gbcMovieList.fill = GridBagConstraints.BOTH;
		gbcMovieList.gridx = 0;
		gbcMovieList.gridy = 0;
		this.moviesPanel.add(this.movieList, gbcMovieList);
		this.tabbedPane.addTab("Livres", null, this.bookPanel, null);
		this.tabbedPane.setEnabledAt(2, true);
		final GridBagLayout gblBookPanel = new GridBagLayout();
		gblBookPanel.columnWidths = new int[] { 213, 0, 0 };
		gblBookPanel.rowHeights = new int[] { 0, 0 };
		gblBookPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gblBookPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		this.bookPanel.setLayout(gblBookPanel);

		final GridBagConstraints gbcBookList = new GridBagConstraints();
		gbcBookList.insets = new Insets(0, 0, 0, 5);
		gbcBookList.fill = GridBagConstraints.BOTH;
		gbcBookList.gridx = 0;
		gbcBookList.gridy = 0;
		this.bookList
		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.bookPanel.add(this.bookList, gbcBookList);
		this.mainBookPanel.setLayout(null);
		final GridBagConstraints gbcMainBookPanel = new GridBagConstraints();
		gbcMainBookPanel.fill = GridBagConstraints.BOTH;
		gbcMainBookPanel.gridx = 1;
		gbcMainBookPanel.gridy = 0;
		this.bookPanel.add(this.mainBookPanel, gbcMainBookPanel);
		this.bookLblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		this.bookLblTitle.setBounds(38, 66, 142, 14);

		this.mainBookPanel.add(this.bookLblTitle);

		this.mainBookPanel.add(this.bookTitleField);
		this.bookLblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
		this.bookLblAuthor.setBounds(38, 109, 142, 14);

		this.mainBookPanel.add(this.bookLblAuthor);

		this.mainBookPanel.add(this.bookAuthorField);
		this.lblDateDePublication.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblDateDePublication.setBounds(43, 168, 137, 14);

		this.mainBookPanel.add(this.lblDateDePublication);
		this.bookReleaseDateField.setEnabled(false);
		this.mainBookPanel.add(this.bookReleaseDateField);
		this.bookLblPicture.setBounds(442, 38, 319, 269);

		this.mainBookPanel.add(this.bookLblPicture);

		// this.frmMediatheque.setIconImage(GUIUtils.getIcon("mediatheque.jpg"));
	}

	private void addExistingElement() {
		final String selectedElement = (String) this.existingElementBox
				.getSelectedItem();
		if (selectedElement == null) {
			JOptionPane.showMessageDialog(this.frmMediatheque,
					"Sélectionner un élément existant et non vide.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		final String name = this.tabbedPane.getSelectedComponent().getName();
		final DataType dataType = DataType.valueOf(name.toUpperCase());

		DB_ACCESS.updateUser(this.currentUserNick, selectedElement, dataType);
		populateMovieList();
		populateCDList();
		populateBookList();
	}

	private void changeTabAction() {
		// Refill the comboxbox of existing items
		final String name = this.tabbedPane.getSelectedComponent().getName();
		final DataType dataType = DataType.valueOf(name.toUpperCase());
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		final List<? extends DomainObject> allNotOwned = DB_ACCESS
				.getAllNotOwned(dataType, this.currentUserNick);
		Collections.sort(allNotOwned, new DomainObjectComparator());
		for (final DomainObject data : allNotOwned) {
			model.addElement(data.getLblExpression());
		}
		this.existingElementBox.setModel(model);
	}

	private void addNewElement() throws NoSuchMethodException,
	SecurityException, InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException {
		final String name = this.tabbedPane.getSelectedComponent().getName();
		final DataType dataType = DataType.valueOf(name.toUpperCase());

		final Constructor<? extends CreateDialog> ctor = dataType.getClazz()
				.getConstructor(this.getClass(), String.class);
		final CreateDialog createDialog = ctor.newInstance(this,
				this.currentUserNick);
		createDialog.create();
		final Method method = MainMediatheque.class.getDeclaredMethod(dataType
				.getRefillMethodName());
		method.invoke(this);
	}

	private void reFillMovieList() {
		final Movie selectedMovie = this.movies.get(this.movieList
				.getSelectedValue());
		if (selectedMovie != null) {
			this.movieTitleField.setText(selectedMovie.getTitle());
			this.movieChckbxSupportDvd.setSelected(selectedMovie.isOwnedDVD());
			this.movieDirectorField.setText(selectedMovie.getDirectorName());
			this.movieReleaseDateField.setDate(selectedMovie.getReleaseDate());
			if (selectedMovie.getPicture() != null) {
				this.movieLblPicture.setIcon(new ImageIcon(selectedMovie
						.getPicture()));
			} else {
				this.movieLblPicture.setIcon(null);
			}
		}
	}

	private void reFillCDList() {
		final CD selectedCD = this.cds.get(this.cdList.getSelectedValue());
		if (selectedCD != null) {
			this.cdTitleField.setText(selectedCD.getTitle());
			this.cdAuthorField.setText(selectedCD.getAuthorName());
			this.cdKindField.setText(selectedCD.getKind().getValue());
			this.cdReleaseDateField.setDate(selectedCD.getReleaseDate());
			if (selectedCD.getPicture() != null) {
				this.cdLblPicture
				.setIcon(new ImageIcon(selectedCD.getPicture()));
			} else {
				this.cdLblPicture.setIcon(null);
			}
		}
	}

	private void reFillBookList() {
		final Book selectedBook = this.books.get(this.bookList
				.getSelectedValue());
		if (selectedBook != null) {
			this.bookTitleField.setText(selectedBook.getTitle());
			this.bookAuthorField.setText(selectedBook.getAuthorName());
			this.bookReleaseDateField.setDate(selectedBook.getReleaseDate());
			if (selectedBook.getPicture() != null) {
				this.bookLblPicture.setIcon(new ImageIcon(selectedBook
						.getPicture()));
			} else {
				this.bookLblPicture.setIcon(null);
			}
		}
	}

	public void populateMovieList() {
		final User currentUser = DB_ACCESS
				.getUserFromNickName(this.currentUserNick);
		final DefaultListModel<String> listModel = new DefaultListModel<>();
		this.movies.clear();
		for (final Movie movie : currentUser.getMovies()) {
			listModel.addElement(movie.getTitle());
			this.movies.put(movie.getTitle(), movie);
		}
		this.movieList.setModel(listModel);
	}

	public void populateCDList() {
		final User currentUser = DB_ACCESS
				.getUserFromNickName(this.currentUserNick);
		final DefaultListModel<String> listModel = new DefaultListModel<>();
		this.cds.clear();
		for (final CD cd : currentUser.getCds()) {
			listModel.addElement(cd.getTitle());
			this.cds.put(cd.getTitle(), cd);
		}
		this.cdList.setModel(listModel);
	}

	public void populateBookList() {
		final User currentUser = DB_ACCESS
				.getUserFromNickName(this.currentUserNick);
		final DefaultListModel<String> listModel = new DefaultListModel<>();
		this.books.clear();
		for (final Book book : currentUser.getBooks()) {
			listModel.addElement(book.getTitle());
			this.books.put(book.getTitle(), book);
		}
		this.bookList.setModel(listModel);
	}

	public JFrame getFrame() {
		return this.frmMediatheque;
	}

	public void setUserNick(final String nick) {
		this.currentUserNick = nick;
	}

	public void fillData() {
		populateMovieList();
		populateCDList();
		populateBookList();
		this.movieList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				reFillMovieList();
			}
		});
		this.cdList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				reFillCDList();
			}
		});
		this.bookList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				reFillBookList();
			}
		});
	}
}
