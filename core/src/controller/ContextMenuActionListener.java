package controller;

import javax.swing.*;

import view.ElementView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by paubreton on 14/02/15.
 * Classe écoutant les JMenuItem d'un JPopupMenu et associant des actions à ces derniers
 */
public class ContextMenuActionListener implements ActionListener {

    private JMenuItem menuItem;
    private Controller controller;
    private ElementView source;

    /**
     * Constructeur de la classe ContextMenuActionListener
     */
    public ContextMenuActionListener (JMenuItem menuItem, Controller controller, ElementView source) {
        this.menuItem   = menuItem;
        this.controller = controller;
        this.source = source;
    }

    /**
     * Override de la fonction actionPerformed retournant l'action du bouton sur lequel on clique
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        this.controller.notifyContextMenuItemActivated(menuItem.getActionCommand(), this.source);    }
}
