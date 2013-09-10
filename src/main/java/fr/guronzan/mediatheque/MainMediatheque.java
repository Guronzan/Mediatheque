package fr.guronzan.mediatheque;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainMediatheque {
    private JFrame frmMediatheque;
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    private final JPanel musicPanel = new JPanel();
    private final JPanel moviesPanel = new JPanel();
    private final JList movieList = new JList();

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
        gblMoviesPanel.columnWidths = new int[] { 526, 1, 0 };
        gblMoviesPanel.rowHeights = new int[] { 346, 0 };
        gblMoviesPanel.columnWeights = new double[] { 1.0, 0.0,
                Double.MIN_VALUE };
        gblMoviesPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        this.moviesPanel.setLayout(gblMoviesPanel);

        final GridBagConstraints gbcMovieList = new GridBagConstraints();
        gbcMovieList.fill = GridBagConstraints.VERTICAL;
        gbcMovieList.anchor = GridBagConstraints.WEST;
        gbcMovieList.gridx = 1;
        gbcMovieList.gridy = 0;
        this.moviesPanel.add(this.movieList, gbcMovieList);
    }

    public JFrame getFrame() {
        return this.frmMediatheque;
    }
}
