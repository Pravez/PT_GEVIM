package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by paubreton on 15/02/15.
 * Classe d'édition de couleurs. Elle reçoit une couleur de base et à l'aide du {@link javax.swing.JColorChooser} aide l'utilisateur
 * à choisir une nouvelle couleur parmi un panel relativement étendu. Utilisée par les classes {@link view.VertexViewEditor} et {@link view.EdgeViewEditor}.
 */
public class ColorChooser extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel        contentPane;
    private JPanel        colorPane;
    private JPanel        buttonsPane;
    private JColorChooser colorChooser;
    private JButton       buttonOK;
    private JButton       buttonCancel;
    private Color         currentColor;

    /**
     * Constructeur mettant en place l'état initial de la fenêtre
     * @param bg La couleur première qu'aura le sélecteur de couleurs (id est : la couleur de l'élément que l'on veut modifier).
     */
    public ColorChooser(Color bg) {
        initComponents(bg);

        getRootPane().setDefaultButton(this.buttonOK);
        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initialisation des différents composants de swing associés à la fenêtre.
     * @param bg La couleur de base
     */
    public void initComponents(Color bg) {
        this.setTitle("Color chooser");

        this.contentPane = new JPanel();
        this.buttonsPane = new JPanel(new GridLayout(1,2));
        this.colorPane   = new JPanel();

        this.setContentPane(contentPane);


        this.colorChooser = new JColorChooser(bg);
        this.currentColor = bg;


        this.buttonOK     = new JButton("Ok");
        this.buttonCancel = new JButton("Cancel");

        this.contentPane.add(this.colorPane);
        this.contentPane.add(this.buttonsPane);
        this.colorPane.add(this.colorChooser);
        this.buttonsPane.add(this.buttonOK);
        this.buttonsPane.add(this.buttonCancel);

        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Métode appellée à la validation des données (appui sur le bouton OK). Elle enregistre la couleur choisie par l'utilisateur
     * et ferme la fenêtre.
     */
    private void onOK(){
        this.currentColor = colorChooser.getColor();
        dispose();
    }

    /**
     * Méthode fermant la fenêtre sans rien enregistrer
     */
    private void onCancel(){
        dispose();
    }

    /**
     * Méthode permettant de récupérer la couleur actuelle de l'instance du {@link view.ColorChooser}
     * @return La dernière couleur enregistrée par l'instance
     */
    public Color getColor(){
        return this.currentColor;
    }
}