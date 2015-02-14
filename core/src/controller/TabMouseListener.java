package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.Tab;
import data.Graph;

/**
 * @author Alexis Dufrenne
 * Classe TabMouseListener, écouteur des événements souris survenant au niveau des Tab
 */
public class TabMouseListener implements MouseListener {
	
	private Controller controller;
	private Tab        tab;
	private Graph      graph;
	
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
		switch(mouseEvent.getButton()) {
        case MouseEvent.BUTTON1: // Clic gauche
            this.controller.addVertex(this.graph, mouseEvent.getPoint());
            this.tab.repaint();
            break;

        case MouseEvent.BUTTON3: // clic droit
            break;
		}
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris entre dans la zone du Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	}

	/**
	 * Méthode appelée lorsque le curseur de la souris quitte la zone du Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	}

	/**
	 * Méthode appelée lorsque l'on presse un bouton de la souris sur le Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		 /*System.out.println("pressed");
        Vertex vertexTmp = ((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent);
        if(vertexTmp != null){
            System.out.println("onVezretex");
        }*/
	}

	/**
	 * Méthode appelée lorsque l'on relâche le bouton pressé de la souris sur le Tab
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		//if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null){ }
	}
}