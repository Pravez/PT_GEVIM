package controller;

import view.elements.VertexView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Alexis Dufrenne
 * Classe VertexMouseListener, écouteur des événements souris survenant au niveau des VertexView
 */
public class VertexMouseListener implements MouseListener, MouseMotionListener {
	
	private Controller controller;
	private VertexView vertex;

	private static VertexView underMouseVertex = null;

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
	 * Méthode récupérant le clic d'une souris
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		this.controller.getState().click(this.vertex, e);
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris entre dans la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		underMouseVertex = vertex;
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris quitte la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		underMouseVertex = null;
	}

	/**
	 * Méthode appelée lorsque l'on presse un bouton de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.controller.getState().pressed(this.vertex, e);
	}

	/**
	 * Méthode appelée lorsque l'on relâche le bouton pressé de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		this.controller.getState().released(this.vertex, e);
		if (underMouseVertex != null && underMouseVertex != vertex){ // à mettre ailleurs ??? --> State ?
			if (this.vertex.getPosition().x < underMouseVertex.getPosition().x) {
				this.controller.addEdge(vertex.getVertex(), underMouseVertex.getVertex());
			} else {
				this.controller.addEdge(underMouseVertex.getVertex(), vertex.getVertex());
			}
		}
	}

	/**
	 * Méthode appelée lorsqu'on déplace la souris en ayant le bouton enfoncé
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		this.controller.getState().drag(this.vertex, e);
	}

	/**
	 * Méthode appelée au déplacement de la souris
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) { }
}
