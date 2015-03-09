package view.frames;

import controller.listeners.ButtonActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aledufrenne on 09/03/15.
 */
public class ButtonFactory {

    public static JButton createStateButton(String fileName, String actionCommand, String helpMessage) {
        JButton button = new JButton();
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        button.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBounds(0, 0, 20, 20);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.setFocusable(false);
        button.setActionCommand(actionCommand);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setToolTipText(helpMessage);
        return button;
    }

    public static JButton createToolBarButton(String buttonName, String actionName, String helpMessage) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(actionName);
        button.setToolTipText(helpMessage);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setFocusable(false);
        return button;
    }

    public static JButton createToolBarButtonWithImage(String buttonName, String actionName, String fileName, String helpMessage) {
        Image   img    = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton button = new JButton();
        button.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBounds(0, 0, 20, 20);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setName(buttonName);
        button.setActionCommand(actionName);
        button.setSelected(false);
        button.setToolTipText(helpMessage);
        button.setFocusable(false);
        return button;
    }
}
