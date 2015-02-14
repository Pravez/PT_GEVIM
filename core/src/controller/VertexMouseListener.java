package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

import view.VertexView;

/**
 * @author Alexis Dufrenne
 * Classe VertexMouseListener, écouteur des événements souris survenant au niveau des VertexView
 */
public class VertexMouseListener implements MouseListener {
	
	private Controller controller;
	private VertexView vertex;

	/**
	 * Constructeur de la classe VertexMouseListener
	 * @param controller le Controller
	 * @param vertex le VertexView qu'il écoute
	 */
	public VertexMouseListener(Controller controller, VertexView vertex) {
		this.controller = controller;
		this.vertex     = vertex;
	}

	/**
	 * Méthode appelée lorsque l'on clique sur un VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
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

	/**
	 * Méthode appelée lorsque le curseur de la souris entre dans la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris quitte la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Méthode appelée lorsque l'on presse un bouton de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Méthode appelée lorsque l'on relâche le bouton pressé de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
