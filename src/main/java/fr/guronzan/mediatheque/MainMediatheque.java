package fr.guronzan.mediatheque;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainMediatheque {

	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel musicPanel = new JPanel();
	private final JPanel moviesPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final MainMediatheque window = new MainMediatheque();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
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
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 1073, 580);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frame.getContentPane().add(this.tabbedPane, BorderLayout.NORTH);

		this.tabbedPane.addTab("Musique", null, this.musicPanel, null);
		this.tabbedPane.setEnabledAt(0, true);

		this.tabbedPane.addTab("Vid\u00E9os", null, this.moviesPanel, null);
	}

}
