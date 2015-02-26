package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarContextActionListener implements ActionListener {

    private Controller controller;
    private JButton button;

    public ToolBarContextActionListener(Controller controller, JButton button) {
        this.controller = controller;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.controller.notifyToolBarContextActivated(this.button);
    }
}
