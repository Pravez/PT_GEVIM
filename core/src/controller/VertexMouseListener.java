package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		System.out.println("Mouse Clicked on Vertex");
		this.controller.notifyVertexSelected(this.vertex);
		switch(e.getButton()) {
	        case MouseEvent.BUTTON1: // Clic gauche
	            break;
	
	        case MouseEvent.BUTTON3: // clic droit
	            /*if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null) {
	                contextMenu.show(tabs.getSelectedComponent(), mouseEvent.getX(), mouseEvent.getY());
	            }*/
	            break;
            default:
            	break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered on vertex");
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
