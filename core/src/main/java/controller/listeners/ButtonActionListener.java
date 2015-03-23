package controller.listeners;

import controller.ActionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link java.awt.event.ActionListener} des actions d'un bouton, surchargé.
 */
public class ButtonActionListener implements ActionListener {

    private AbstractButton button;
    private Point          position;
    private String         tabTitle;

    /**
     * Constructeur de base
     * @param button Le bouton qui sera écouté
     * @param position La position du bouton dans la fenêtre
     * @param tabTitle Le nom du {@link view.editor.Tab} dans lequel se situe le bouton
     */
    public ButtonActionListener(AbstractButton button, Point position, String tabTitle) {
        this.button   = button;
        this.position = position;
        this.tabTitle = tabTitle;
    }

    /**
     * Override de la méthode de récupération d'un événement. Appelle le controller pour qu'il traite l'opération.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ActionController.handleButton(this.button, this.position, this.tabTitle);
    }
}
