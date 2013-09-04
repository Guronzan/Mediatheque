package fr.guronzan.mediatheque;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSettings {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserSettings.class);
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final UserSettings window = new UserSettings();
                    window.frame.setVisible(true);
                } catch (final Exception e) {
                    LOGGER.error(
                            "Erreur durant l'utilisation de la frame UserSettings",
                            e);
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public UserSettings() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
