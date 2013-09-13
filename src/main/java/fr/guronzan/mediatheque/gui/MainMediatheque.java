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
import fr.guronzan.mediatheque.MediathequeApplicationContext;
import fr.guronzan.mediatheque.gui.createDialog.CreateDialog;
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

    private JFrame frmMediatheque;
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    private final JPanel musicPanel = new JPanel();
    private final JPanel moviesPanel = new JPanel();
    private final JList<String> movieList = new JList<>();
    private final JPanel mainPanel = new JPanel();
    private final JLabel lblTitle = new JLabel("Titre");
    private final JLabel lblPicture = new JLabel("");

    private JTextField titleField;
    private JCheckBox chckbxSupportDvd;
    private String currentUserNick;
    private JTextField directorField;
    private JTextField releaseDateField;
    final JComboBox<String> existingElementBox = new JComboBox<>();
    private final JButton btnAddExisting = new JButton(
            "Ajouter l'\u00E9l\u00E9ment s\u00E9lectionn\u00E9");

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
        this.frmMediatheque.setBounds(100, 100, 1112, 455);
        this.frmMediatheque.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frmMediatheque.getContentPane().add(this.tabbedPane,
                BorderLayout.NORTH);

        this.musicPanel.setName(DataType.MUSIC.getValue());
        this.tabbedPane.addTab("Musique", null, this.musicPanel, null);
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

        this.mainPanel.setLayout(null);

        final GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 1;
        gbcPanel.gridy = 0;
        this.moviesPanel.setName(DataType.MOVIE.getValue());
        this.moviesPanel.add(this.mainPanel, gbcPanel);
        this.lblTitle.setBounds(10, 36, 46, 14);

        this.mainPanel.add(this.lblTitle);

        this.titleField = new JTextField();
        this.titleField.setEditable(false);
        this.titleField.setBounds(101, 33, 229, 20);
        this.mainPanel.add(this.titleField);
        this.titleField.setColumns(10);

        this.chckbxSupportDvd = new JCheckBox("Support DVD");
        this.chckbxSupportDvd.setBounds(101, 167, 97, 23);
        this.chckbxSupportDvd.setEnabled(false);
        this.mainPanel.add(this.chckbxSupportDvd);

        final JLabel lblDirector = new JLabel("R\u00E9alisateur");
        lblDirector.setBounds(10, 72, 71, 14);
        this.mainPanel.add(lblDirector);

        this.directorField = new JTextField();
        this.directorField.setEditable(false);
        this.directorField.setBounds(101, 69, 229, 20);
        this.mainPanel.add(this.directorField);
        this.directorField.setColumns(10);

        this.lblPicture.setBounds(374, 36, 286, 299);
        this.mainPanel.add(this.lblPicture);

        final JLabel lblReleaseDate = new JLabel("Date de sortie");
        lblReleaseDate.setBounds(10, 131, 86, 14);
        this.mainPanel.add(lblReleaseDate);

        this.releaseDateField = new JTextField();
        this.releaseDateField.setEditable(false);
        this.releaseDateField.setBounds(101, 128, 184, 20);
        this.mainPanel.add(this.releaseDateField);
        this.releaseDateField.setColumns(10);

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
    }

    private void addExistingElement() {
        final String selectedElement = (String) this.existingElementBox
                .getSelectedItem();
        if (selectedElement == null) {
            // TODO add popup
            return;
        }
        final String name = this.tabbedPane.getSelectedComponent().getName();
        final DataType dataType = DataType.valueOf(name.toUpperCase());

        DB_ACCESS.updateUser(this.currentUserNick, selectedElement, dataType);
        populateMovieList();
        // populateCDList();
        // populateBookList();
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
    }

    private void reFillMovieList() {
        final Movie selectedMovie = this.movies.get(this.movieList
                .getSelectedValue());
        if (selectedMovie != null) {
            this.titleField.setText(selectedMovie.getTitle());
            this.chckbxSupportDvd.setSelected(selectedMovie.isOwnedDVD());
            this.directorField.setText(selectedMovie.getDirectorName());
            this.releaseDateField.setText(selectedMovie.getReleaseDate()
                    .toString());
            if (selectedMovie.getPicture() != null) {
                this.lblPicture.setIcon(new ImageIcon(selectedMovie
                        .getPicture()));
            } else {
                this.lblPicture.setIcon(null);
            }
        }
    }

    public void populateMovieList() {
        final User currentUser = DB_ACCESS
                .getUserFromNickName(this.currentUserNick);
        final List<Movie> movies = currentUser.getMovies();
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
        this.currentUserNick = nick;
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
