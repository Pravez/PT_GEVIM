package controller;

import view.EdgeView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Alexis Dufrenne
 * Classe VertexMouseListener, écouteur des événements souris survenant au niveau des VertexView
 */
public class EdgeMouseListener implements MouseListener {

	private Controller controller;
	private EdgeView   edge;

	/**
	 * Constructeur de la classe VertexMouseListener
	 * @param controller le Controller
	 * @param edge le VertexView qu'il écoute
	 */
	public EdgeMouseListener(Controller controller, EdgeView edge) {
		this.controller = controller;
		this.edge       = edge;
	}

	/**
	 * Méthode récupérant le clic d'une souris
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		this.controller.getState().click(this.edge, e);
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris entre dans la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) { }

	/**
	 * Méthode appelée lorsque le curseur de la souris quitte la zone du VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) { }

	/**
	 * Méthode appelée lorsque l'on presse un bouton de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.controller.getState().pressed(this.edge, e);
	}

	/**
	 * Méthode appelée lorsque l'on relâche le bouton pressé de la souris sur le VertexView
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) { }
}
