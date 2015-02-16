package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ToolBarContextActionListener implements ActionListener {
	
	private Controller controller;
	private JButton    button;

	public ToolBarContextActionListener(Controller controller, JButton button) {
		this.controller = controller;
		this.button     = button; 
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.controller.notifyToolBarContextActivated(this.button.getActionCommand());
	}
}
