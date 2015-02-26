package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexis Dufrenne
 *         Classe MenuActionListener, écouteur des actions effectuées sur les boutons du Menu de l'application
 */
public class MenuActionListener implements ActionListener {
    private JMenuItem menuItem;
    private Controller controller;

    /**
     * Constructeur de la classe MenuActionListener
     *
     * @param menuItem   le JMenuItem qu'il écoute
     * @param controller le Controller
     */
    public MenuActionListener(JMenuItem menuItem, Controller controller) {
        this.menuItem = menuItem;
        this.controller = controller;
    }

    /**
     * Override de la fonction actionPerformed retournant le texte du bouton sur lequel on clique
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        this.controller.notifyMenuItemActivated(menuItem.getText());
    }
}
