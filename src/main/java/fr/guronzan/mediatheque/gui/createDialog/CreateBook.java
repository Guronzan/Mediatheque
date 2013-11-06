package fr.guronzan.mediatheque.gui.createDialog;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import lombok.extern.slf4j.Slf4j;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import fr.guronzan.mediatheque.MediathequeApplicationContext;
import fr.guronzan.mediatheque.gui.MainMediatheque;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.utils.ImageFileFilter;
import fr.guronzan.mediatheque.webservice.DBAccess;

@Slf4j
public class CreateBook implements CreateDialog {
    private static final String ERREUR_CREATION_LIVRE = "Erreur création livre";

    private static final DBAccess DB_ACCESS = MediathequeApplicationContext
            .getBean(DBAccess.class);

    private JDialog frame;
    private JTextField titleField;
    private JTextField authorField;
    private JMonthChooser publicationMonthField;
    private JYearChooser publicationYearField;
    private JTextField editorField;

    private JSpinner tomeSpinner;
    private File picture = null;

    private final String currentNick;

    private final MainMediatheque parent;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final CreateBook window = new CreateBook(null, "nick");
                    window.frame.setVisible(true);
                } catch (final Exception e) {
                    log.error("Error while creating new book.", e);
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
    public CreateBook(final MainMediatheque mainMediatheque,
            final String currentNick) {
        this.currentNick = currentNick;
        this.parent = mainMediatheque;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.frame = new JDialog();
        this.frame.setBounds(100, 100, 544, 395);
        this.frame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.setModalityType(ModalityType.APPLICATION_MODAL);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 118, 261, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        this.frame.getContentPane().setLayout(gridBagLayout);
        this.frame.setLocationRelativeTo(null);

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

        final JLabel lblAuthor = new JLabel("Auteur");
        final GridBagConstraints gbcLblAuthor = new GridBagConstraints();
        gbcLblAuthor.insets = new Insets(0, 0, 5, 5);
        gbcLblAuthor.gridx = 0;
        gbcLblAuthor.gridy = 2;
        this.frame.getContentPane().add(lblAuthor, gbcLblAuthor);

        this.authorField = new JTextField();
        this.authorField.setText("...");
        final GridBagConstraints gbcAuthorField = new GridBagConstraints();
        gbcAuthorField.insets = new Insets(0, 0, 5, 0);
        gbcAuthorField.fill = GridBagConstraints.HORIZONTAL;
        gbcAuthorField.gridx = 1;
        gbcAuthorField.gridy = 2;
        this.frame.getContentPane().add(this.authorField, gbcAuthorField);
        this.authorField.setColumns(10);

        final JLabel lblPublicationDate = new JLabel("Date de publication");
        final GridBagConstraints gbcLblPublicationDate = new GridBagConstraints();
        gbcLblPublicationDate.insets = new Insets(0, 0, 5, 5);
        gbcLblPublicationDate.gridx = 0;
        gbcLblPublicationDate.gridy = 3;
        this.frame.getContentPane().add(lblPublicationDate,
                gbcLblPublicationDate);

        final JPanel datePanel = new JPanel();
        final GridBagConstraints gbcDatePanel = new GridBagConstraints();
        gbcDatePanel.insets = new Insets(0, 0, 5, 0);
        gbcDatePanel.fill = GridBagConstraints.BOTH;
        gbcDatePanel.gridx = 1;
        gbcDatePanel.gridy = 3;
        this.frame.getContentPane().add(datePanel, gbcDatePanel);
        final GridBagLayout gblDatePanel = new GridBagLayout();
        gblDatePanel.columnWidths = new int[] { 82, 97, 0 };
        gblDatePanel.rowHeights = new int[] { 20, 0 };
        gblDatePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gblDatePanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        datePanel.setLayout(gblDatePanel);

        this.publicationMonthField = new JMonthChooser(true);
        final GridBagConstraints gbcPublicationMonthField = new GridBagConstraints();
        gbcPublicationMonthField.anchor = GridBagConstraints.NORTHWEST;
        gbcPublicationMonthField.gridx = 0;
        gbcPublicationMonthField.gridy = 0;
        datePanel.add(this.publicationMonthField, gbcPublicationMonthField);
        this.publicationYearField = new JYearChooser();
        final GridBagConstraints gbcPublicationYearField = new GridBagConstraints();
        gbcPublicationYearField.insets = new Insets(0, 0, 5, 0);
        gbcPublicationYearField.fill = GridBagConstraints.HORIZONTAL;
        gbcPublicationYearField.gridx = 1;
        gbcPublicationYearField.gridy = 0;
        datePanel.add(this.publicationYearField, gbcPublicationYearField);

        final JLabel lblEditor = new JLabel("Editeur");
        final GridBagConstraints gbcLblEditor = new GridBagConstraints();
        gbcLblEditor.insets = new Insets(0, 0, 5, 5);
        gbcLblEditor.gridx = 0;
        gbcLblEditor.gridy = 4;
        this.frame.getContentPane().add(lblEditor, gbcLblEditor);

        final JButton btnCreer = new JButton("Cr\u00E9er");
        final GridBagConstraints gbcBtnCreer = new GridBagConstraints();
        gbcBtnCreer.insets = new Insets(0, 0, 5, 5);
        gbcBtnCreer.gridx = 0;
        gbcBtnCreer.gridy = 7;
        btnCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (createBook()) {
                        if (CreateBook.this.parent != null) {
                            CreateBook.this.parent.populateBookList();
                        }
                        CreateBook.this.frame.dispose();
                    }
                } catch (final IOException e1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Erreur durant la création du livre : "
                                    + e1.getMessage(), ERREUR_CREATION_LIVRE,
                            JOptionPane.ERROR_MESSAGE);
                    CreateBook.log.error("Error while creating new book.", e1);
                }
            }
        });
        this.frame.getContentPane().add(btnCreer, gbcBtnCreer);

        this.editorField = new JTextField();
        this.editorField.setText("...");
        this.editorField.setColumns(10);
        final GridBagConstraints gbcEditorField = new GridBagConstraints();
        gbcEditorField.insets = new Insets(0, 0, 5, 0);
        gbcEditorField.fill = GridBagConstraints.HORIZONTAL;
        gbcEditorField.gridx = 1;
        gbcEditorField.gridy = 4;
        this.frame.getContentPane().add(this.editorField, gbcEditorField);

        final JLabel lblVolume = new JLabel("Volume");
        final GridBagConstraints gbcLblVolume = new GridBagConstraints();
        gbcLblVolume.insets = new Insets(0, 0, 5, 5);
        gbcLblVolume.gridx = 0;
        gbcLblVolume.gridy = 5;
        this.frame.getContentPane().add(lblVolume, gbcLblVolume);

        this.tomeSpinner = new JSpinner();
        this.tomeSpinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));
        final GridBagConstraints gbcTomeSpinner = new GridBagConstraints();
        gbcTomeSpinner.insets = new Insets(0, 0, 5, 0);
        gbcTomeSpinner.gridx = 1;
        gbcTomeSpinner.gridy = 5;
        this.frame.getContentPane().add(this.tomeSpinner, gbcTomeSpinner);

        final JLabel lblPicture = new JLabel("Image de couverture");
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
                        .showOpenDialog(CreateBook.this.frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    CreateBook.this.picture = fileChooser.getSelectedFile();
                }
            }
        });

        final JButton btnQuitter = new JButton("Quitter");
        final GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.insets = new Insets(0, 0, 5, 0);
        gbcBtnQuitter.gridx = 1;
        gbcBtnQuitter.gridy = 7;
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CreateBook.this.frame.dispose();
            }
        });
        this.frame.getContentPane().add(btnQuitter, gbcBtnQuitter);
    }

    private boolean createBook() throws IOException {
        final Integer tomeValue = (Integer) this.tomeSpinner.getValue();
        final boolean bookExists;
        if (tomeValue == 0) {
            bookExists = DB_ACCESS
                    .containsBook(this.titleField.getText(), null);
        } else {
            bookExists = DB_ACCESS.containsBook(this.titleField.getText(),
                    tomeValue);
        }

        if (bookExists) {
            JOptionPane
                    .showMessageDialog(
                            null,
                            "Titre et tome déjà existant, veuillez le choisir dans la liste",
                            ERREUR_CREATION_LIVRE, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final String title = this.titleField.getText();
        if (title.equals("...") || title.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Veuillez renseigner un Titre.", ERREUR_CREATION_LIVRE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final String authorName = this.authorField.getText();
        if (authorName.equals("...") || authorName.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Veuillez renseigner un auteur.", ERREUR_CREATION_LIVRE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final String editor = this.editorField.getText();
        if (editor.equals("...") || editor.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Veuillez renseigner un editeur.", ERREUR_CREATION_LIVRE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final Book book = new Book(title);
        book.setAuthorName(authorName);
        book.setEditor(editor);
        book.addPicture(this.picture);
        book.setTome(tomeValue);

        final Calendar publicationDateCalendar = Calendar.getInstance();
        publicationDateCalendar.set(Calendar.YEAR,
                this.publicationYearField.getYear());
        publicationDateCalendar.set(Calendar.MONTH,
                this.publicationMonthField.getMonth());
        publicationDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        final Date publicationDate = publicationDateCalendar.getTime();
        book.setReleaseDate(publicationDate);

        final User currentUser = DB_ACCESS
                .getUserFromNickName(this.currentNick);
        currentUser.addBook(book);

        // DB_ACCESS.addBook(book);
        DB_ACCESS.updateUser(currentUser);

        JOptionPane.showMessageDialog(null,
                "Création du livre " + book.getTitle()
                        + " réalisée avec succès.", "Création réussie",
                JOptionPane.INFORMATION_MESSAGE);
        log.info("Book {} created sucessfully at {}", book, new Date());
        return true;
    }

    @Override
    public void create() {
        this.frame.setVisible(true);
    }
}
