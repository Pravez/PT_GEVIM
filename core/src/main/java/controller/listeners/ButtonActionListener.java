package controller.listeners;

import controller.ActionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aledufrenne on 08/03/2015.
 */
public class ButtonActionListener implements ActionListener {

    private AbstractButton button;
    private Point          position;
    private int            tabIndex;

    public ButtonActionListener(AbstractButton button, Point position, int tabIndex) {
        this.button   = button;
        this.position = position;
        this.tabIndex = tabIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionController.handleButton(this.button, this.position, this.tabIndex);
    }
}
