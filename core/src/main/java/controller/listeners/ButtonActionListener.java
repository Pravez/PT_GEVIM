package controller.listeners;

import controller.ActionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link java.awt.event.ActionListener} des actions d'un bouton, surchargé
 */
public class ButtonActionListener implements ActionListener {

    private AbstractButton button;
    private Point          position;
    private String         tabTitle;

    public ButtonActionListener(AbstractButton button, Point position, String tabTitle) {
        this.button   = button;
        this.position = position;
        this.tabTitle = tabTitle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionController.handleButton(this.button, this.position, this.tabTitle);
    }
}
