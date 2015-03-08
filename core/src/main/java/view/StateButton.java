package view;

import controller.Controller;
import controller.listeners.ButtonActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aledufrenne on 08/03/2015.
 */
public class StateButton extends JButton {

    public StateButton(String fileName, String actionCommand, String helpMessage) {
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        setBounds(0, 0, 20, 20);
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
        setActionCommand(actionCommand);
        addActionListener(new ButtonActionListener(this, null));
        setToolTipText(helpMessage);
    }
}
