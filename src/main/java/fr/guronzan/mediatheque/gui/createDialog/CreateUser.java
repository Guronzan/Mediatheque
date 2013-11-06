package fr.guronzan.mediatheque.gui.createDialog;

import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import lombok.extern.slf4j.Slf4j;
import fr.guronzan.mediatheque.MediathequeApplicationContext;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.utils.DigestUtils;
import fr.guronzan.mediatheque.webservice.DBAccess;

@Slf4j
public class CreateUser implements CreateDialog {
    private static final DBAccess DB_ACCESS = MediathequeApplicationContext
            .getBean(DBAccess.class);

    private JDialog dialogCreateUser;
    private JTextField nameField;
    private JTextField forNameField;
    private JTextField nickField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final CreateUser window = new CreateUser(null);
                    window.create();
                } catch (final Exception e) {
                    log.error("Error while creating new user.", e);
                }
            }
        });
    }

    @Override
    public void create() {
        this.dialogCreateUser.setVisible(true);
    }

    /**
     * Create the application.
     * 
     * @param owner
     */
    public CreateUser(final Dialog owner) {
        initialize(owner);
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @param owner
     */
    private void initialize(final Dialog owner) {
        this.dialogCreateUser = new JDialog(owner);
        this.dialogCreateUser.setResizable(false);
        this.dialogCreateUser.setBounds(100, 100, 411, 226);
        this.dialogCreateUser.setType(Type.POPUP);
        this.dialogCreateUser
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.dialogCreateUser.setLocationRelativeTo(null);
        this.dialogCreateUser.setModalityType(ModalityType.APPLICATION_MODAL);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 118, 261, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, Double.MIN_VALUE };
        this.dialogCreateUser.getContentPane().setLayout(gridBagLayout);

        final JLabel lblNom = new JLabel("Nom");
        final GridBagConstraints gbcLblNom = new GridBagConstraints();
        gbcLblNom.anchor = GridBagConstraints.EAST;
        gbcLblNom.insets = new Insets(0, 0, 5, 5);
        gbcLblNom.gridx = 0;
        gbcLblNom.gridy = 1;
        this.dialogCreateUser.getContentPane().add(lblNom, gbcLblNom);

        this.nameField = new JTextField();
        this.nameField.setText("...");
        final GridBagConstraints gbcNameField = new GridBagConstraints();
        gbcNameField.fill = GridBagConstraints.HORIZONTAL;
        gbcNameField.insets = new Insets(0, 0, 5, 0);
        gbcNameField.gridx = 1;
        gbcNameField.gridy = 1;
        this.dialogCreateUser.getContentPane()
                .add(this.nameField, gbcNameField);
        this.nameField.setColumns(10);

        final JLabel lblPrnom = new JLabel("Pr\u00E9nom");
        final GridBagConstraints gbcLblPrnom = new GridBagConstraints();
        gbcLblPrnom.anchor = GridBagConstraints.EAST;
        gbcLblPrnom.insets = new Insets(0, 0, 5, 5);
        gbcLblPrnom.gridx = 0;
        gbcLblPrnom.gridy = 2;
        this.dialogCreateUser.getContentPane().add(lblPrnom, gbcLblPrnom);

        this.forNameField = new JTextField();
        this.forNameField.setText("...");
        final GridBagConstraints gbcForNameField = new GridBagConstraints();
        gbcForNameField.insets = new Insets(0, 0, 5, 0);
        gbcForNameField.fill = GridBagConstraints.HORIZONTAL;
        gbcForNameField.gridx = 1;
        gbcForNameField.gridy = 2;
        this.dialogCreateUser.getContentPane().add(this.forNameField,
                gbcForNameField);
        this.forNameField.setColumns(10);

        final JLabel lblPseudo = new JLabel("Pseudo");
        final GridBagConstraints gbcLblPseudo = new GridBagConstraints();
        gbcLblPseudo.anchor = GridBagConstraints.EAST;
        gbcLblPseudo.insets = new Insets(0, 0, 5, 5);
        gbcLblPseudo.gridx = 0;
        gbcLblPseudo.gridy = 3;
        this.dialogCreateUser.getContentPane().add(lblPseudo, gbcLblPseudo);

        this.nickField = new JTextField();
        this.nickField.setText("...");
        final GridBagConstraints gbcNickField = new GridBagConstraints();
        gbcNickField.insets = new Insets(0, 0, 5, 0);
        gbcNickField.fill = GridBagConstraints.HORIZONTAL;
        gbcNickField.gridx = 1;
        gbcNickField.gridy = 3;
        this.dialogCreateUser.getContentPane()
                .add(this.nickField, gbcNickField);
        this.nickField.setColumns(10);

        final JLabel lblMotDePasse = new JLabel("Mot de passe");
        final GridBagConstraints gbcLblMotDePasse = new GridBagConstraints();
        gbcLblMotDePasse.anchor = GridBagConstraints.EAST;
        gbcLblMotDePasse.insets = new Insets(0, 0, 5, 5);
        gbcLblMotDePasse.gridx = 0;
        gbcLblMotDePasse.gridy = 4;
        this.dialogCreateUser.getContentPane().add(lblMotDePasse,
                gbcLblMotDePasse);

        this.passwordField = new JPasswordField();
        final GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.insets = new Insets(0, 0, 5, 0);
        gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
        gbcPasswordField.gridx = 1;
        gbcPasswordField.gridy = 4;
        this.dialogCreateUser.getContentPane().add(this.passwordField,
                gbcPasswordField);

        final JButton btnCreer = new JButton("Cr\u00E9er");
        final GridBagConstraints gbcBtnCreer = new GridBagConstraints();
        gbcBtnCreer.insets = new Insets(0, 0, 0, 5);
        gbcBtnCreer.gridx = 0;
        gbcBtnCreer.gridy = 6;
        btnCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final boolean result = createUser();
                if (result) {
                    CreateUser.this.dialogCreateUser.dispose();
                }
            }
        });
        this.dialogCreateUser.getContentPane().add(btnCreer, gbcBtnCreer);

        final JButton btnQuitter = new JButton("Quitter");
        final GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.gridx = 1;
        gbcBtnQuitter.gridy = 6;
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CreateUser.this.dialogCreateUser.dispose();
            }
        });
        this.dialogCreateUser.getContentPane().add(btnQuitter, gbcBtnQuitter);
    }

    private boolean createUser() {
        final boolean userExists = DB_ACCESS.containsUser(this.nickField
                .getText());
        if (userExists) {
            JOptionPane.showMessageDialog(null,
                    "Pseudo déjà existant, veuillez en choisir un autre.",
                    "Erreur création compte", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final User user = new User(this.nameField.getText(),
                this.forNameField.getText(), this.nickField.getText(),
                DigestUtils.hashPassword(this.passwordField.getPassword()),
                new Date());
        DB_ACCESS.addUser(user);

        JOptionPane.showMessageDialog(null,
                "Création du compte " + user.getNickName()
                        + " réalisée avec succès.", "Création réussie",
                JOptionPane.INFORMATION_MESSAGE);
        log.info("Account {} created sucessfully at {}", user, new Date());
        return true;
    }
}
