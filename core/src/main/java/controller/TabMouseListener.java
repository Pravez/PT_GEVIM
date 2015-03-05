package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.Tab;
import data.Graph;

/**
 * @author Alexis Dufrenne
 * Classe TabMouseListener, écouteur des événements souris survenant au niveau des Tab
 */
public class TabMouseListener implements MouseListener, MouseMotionListener {
	
	private Controller controller;
	private Tab        tab;
	private Graph      graph;
	private Point      sourceDrag;
	
	/**
	 * Constructeur de la classe TabMouseListener
	 * @param controller le Controller
	 * @param tab le Tab (onglet) qu'il écoute
	 * @param graph le Graph (model) correspondant au tab
	 */
	public TabMouseListener(Controller controller, Tab tab, Graph graph) {
		this.controller = controller;
		this.tab        = tab;
		this.graph      = graph;
	}
	
	/**
	 * Méthode appelée lors d'un clic sur le Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		this.controller.getState().click(this.tab, this.graph, mouseEvent);
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris entre dans la zone du Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent mouseEvent) { }

	/**
	 * Méthode appelée lorsque le curseur de la souris quitte la zone du Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent mouseEvent) { }

	/**
	 * Méthode appelée lorsque l'on presse un bouton de la souris sur le Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		this.sourceDrag = new Point(mouseEvent.getX(), mouseEvent.getY());
		if (!mouseEvent.isControlDown()) {
			this.controller.notifyMousePressedWithoutControlDown();
		}
	}

	/**
	 * Méthode appelée lorsque l'on relâche le bouton pressé de la souris sur le Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if (this.controller.getState().isDragging()) {
			this.controller.notifyEndDragging();
			this.controller.getState().setDragging(false);
		}
		//if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null){ }
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		this.controller.getState().drag(this.tab, this.graph, this.sourceDrag, mouseEvent);
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// appelée à chaque mouvement de la souris
	}
}