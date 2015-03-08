package controller.listeners;

import controller.ActionController;
import controller.Controller;

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

    public ButtonActionListener(AbstractButton button, Point position) {
        this.button     = button;
        this.position   = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionController.handleButton(this.button, this.position);
    }
}
