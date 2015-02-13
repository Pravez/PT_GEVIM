package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

import view.VertexView;

public class VertexMouseListener implements MouseListener {
	
	private Controller controller;
	private VertexView vertex;

	public VertexMouseListener(Controller controller, VertexView vertex) {
		this.controller = controller;
		this.vertex     = vertex;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getButton()) {
	        case MouseEvent.BUTTON1: // Clic gauche
	        	if (e.isControlDown()) {
	        		this.controller.notifyVertexAddToSelection(this.vertex);
	        	} else {
	        		this.controller.notifyVertexSelected(this.vertex);
	        	}
	            break;
	
	        case MouseEvent.BUTTON3: // Clic droit
	        	if (e.isControlDown()) {
	        		this.controller.notifyVertexAddToSelection(this.vertex);
	        	} else {
	        		this.controller.notifyVertexSelected(this.vertex);
	        	}
	            JPopupMenu contextMenu = new JPopupMenu();
	            contextMenu.add("Edit");
	            contextMenu.add("Delete");
	            contextMenu.show(this.vertex, e.getX(), e.getY());
	            break;
            default:
            	break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
