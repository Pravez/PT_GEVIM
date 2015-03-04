package main.java.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarButtonActionListener implements ActionListener {
	
	private Controller controller;
	private JButton    button;

	public ToolBarButtonActionListener(Controller controller, JButton button) {
		this.controller = controller;
		this.button     = button; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.notifyToolBarItemActivated(this.button.getName());
	}
}
