package fr.guronzan.mediatheque;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lombok.extern.slf4j.Slf4j;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Slf4j
public class MainMediatheque {
    private static final MovieDao MOVIE_DAO = MediathequeApplicationContext
            .getBean(MovieDao.class);
    private static final UserDao USER_DAO = MediathequeApplicationContext
            .getBean(UserDao.class);
    private final Map<String, Movie> movies = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();

    private JFrame frmMediatheque;
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    private final JPanel musicPanel = new JPanel();
    private final JPanel moviesPanel = new JPanel();
    private JList<String> movieList;
    private final JPanel mainPanel = new JPanel();
    private final JLabel lblTitle = new JLabel("Title");
    private JTextField titleField;
    private JCheckBox chckbxSupportDvd;
    private final JLabel lblOwners = new JLabel("Owners:");
    private final JList<String> ownersList = new JList<>();

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final MainMediatheque window = new MainMediatheque();
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
        this.frmMediatheque.setBounds(100, 100, 1073, 580);
        this.frmMediatheque.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frmMediatheque.getContentPane().add(this.tabbedPane,
                BorderLayout.NORTH);

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

        populateMovieList();
        this.movieList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        final GridBagConstraints gbcMovieList = new GridBagConstraints();
        gbcMovieList.insets = new Insets(0, 0, 0, 5);
        gbcMovieList.fill = GridBagConstraints.BOTH;
        gbcMovieList.gridx = 0;
        gbcMovieList.gridy = 0;
        this.moviesPanel.add(this.movieList, gbcMovieList);
        this.mainPanel.setLayout(null);

        final GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 1;
        gbcPanel.gridy = 0;
        this.moviesPanel.add(this.mainPanel, gbcPanel);
        this.lblTitle.setBounds(44, 67, 46, 14);

        this.mainPanel.add(this.lblTitle);

        this.titleField = new JTextField();
        this.titleField.setBounds(129, 64, 86, 20);
        this.mainPanel.add(this.titleField);
        this.titleField.setColumns(10);

        this.chckbxSupportDvd = new JCheckBox("Support DVD");
        this.chckbxSupportDvd.setBounds(129, 114, 97, 23);
        this.mainPanel.add(this.chckbxSupportDvd);

        final JScrollPane ownersScrollPane = new JScrollPane();
        ownersScrollPane.setBounds(403, 70, 202, 205);
        this.mainPanel.add(ownersScrollPane);

        ownersScrollPane.setRowHeaderView(this.ownersList);
        this.lblOwners.setBounds(347, 67, 46, 14);

        this.mainPanel.add(this.lblOwners);

        this.movieList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                reFillMovieList();
            }
        });
        for (final User user : USER_DAO.getAll()) {
            this.users.put(user.getNickName(), user);
        }
    }

    private void reFillMovieList() {
        final Movie selectedMovie = this.movies.get(this.movieList
                .getSelectedValue());
        this.titleField.setText(selectedMovie.getTitle());
        this.chckbxSupportDvd.setSelected(selectedMovie.isOwnedDVD());

        final DefaultListModel<String> listModel = new DefaultListModel<>();
        for (final User owner : selectedMovie.getOwners()) {
            listModel.addElement(owner.getNickName());
        }

        this.ownersList.setModel(listModel);
    }

    private void populateMovieList() {

        final List<Movie> movies = MainMediatheque.MOVIE_DAO.getAll();
        final DefaultListModel<String> listModel = new DefaultListModel<>();

        for (final Movie movie : movies) {
            listModel.addElement(movie.getTitle());
            this.movies.put(movie.getTitle(), movie);
        }

        this.movieList = new JList<>(listModel);
    }

    public JFrame getFrame() {
        return this.frmMediatheque;
    }
}
