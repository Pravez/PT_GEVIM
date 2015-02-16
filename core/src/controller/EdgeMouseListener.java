package controller;

import view.EdgeView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Alexis Dufrenne
 * Classe VertexMouseListener, écouteur des événements souris survenant au niveau des VertexView
 */
public class EdgeMouseListener implements MouseListener {

	private Controller controller;
	private EdgeView edge;


	/**
	 * Constructeur de la classe VertexMouseListener
	 * @param controller le Controller
	 * @param edge le VertexView qu'il écoute
	 */
	public EdgeMouseListener(Controller controller, EdgeView edge) {
		this.controller = controller;
		this.edge     = edge;
	}

	/**
	 * Initialize a Popup menu with MenuItems
	 * @param menuItems the MenuItems to be present in the popUp menu
	 * @return The popup menu created
	 */
	public JPopupMenu initNewPopupMenu(String [] menuItems){
		JPopupMenu jpm = new JPopupMenu();

		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ContextMenuActionListener(jmi, controller));
			jpm.add(jmi);
		}

		return jpm;
	}

	/**
	 * Méthode récupérant le clic d'une souris
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getButton()) {
	        case MouseEvent.BUTTON1: // Clic gauche
	        	if (e.isControlDown()) {
	        		this.controller.notifyEdgeAddToSelection(this.edge);
	        	} else {
	        		this.controller.notifyEdgeSelected(this.edge);
	        	}
	            break;

	        case MouseEvent.BUTTON3: // Clic droit
	        	/*if (e.isControlDown()) {
	        		this.controller.notifyVertexAddToSelection(this.vertex);
	        	} else {
	        		this.controller.notifyVertexSelected(this.vertex);
	        	}

				//Création du menu contextuel avec Edit et Delete comme options.
				JPopupMenu contextMenu = initNewPopupMenu(new String[]{"Edit", "Delete"});
	            contextMenu.show(this.vertex, e.getX(), e.getY());*/
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
	public void mousePressed(MouseEvent e)  {
		System.out.println("Mouse pressed on EdgeView");
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
