package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class SelectionState extends State {

	public SelectionState(Controller controller) {
		super(controller);
	}

	/**
	 * Méthode pour gérer la sélection d'un Vertex : 
	 * - soit on l'ajoute à la sélection si la touche Ctrl est enfoncée
	 * - soit on désire le sélectionner
	 * @param e MouseEvent pour récupérer l'événement isControlDown
	 */
	private void manageSelection(ElementView element, MouseEvent e) {
		if (e.isControlDown()) {
			this.controller.notifyHandleElementSelected(element);
		} else {
			this.controller.notifyHandleElement(element);
		}
        this.controller.notifyRepaintTab();
	}

	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			openTabPopUpMenu(tab, e.getPoint());
		}
		this.controller.notifyClearSelection();
	}
	
	@Override
	public void click(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			this.controller.notifyHandleElement(element);
			//Création du menu contextuel avec Edit et Delete comme options.
            HashMap<String, String> menus = new HashMap<String, String>(){{put("Edit", "Editer");}{put("Delete", "Supprimer");}{put("Copy", "Copier");}{put("Paste", "Coller");}};
            initNewPopupMenu(menus, e.getPoint()).show(element, e.getX(), e.getY());
		}
	}

	@Override
	public void drag(Tab tab, Graph graph, MouseEvent e) {
        this.dragging = true;
		if (e.isControlDown()) {
			this.controller.notifyAddToDragging(this.sourceDrag, e.getPoint());
		} else {
			this.controller.notifyDragging(this.sourceDrag, e.getPoint());
		}
	}

	/**
	 * Méthode pour déplacer les VertexView sélectionnés
	 * (non-Javadoc)
	 * @see controller.state.State#drag(view.editor.elements.VertexView, java.awt.event.MouseEvent)
	 */
	@Override
	public void drag(VertexView vertex, MouseEvent e) {
        this.dragging = true;
        this.controller.notifyMoveSelectedElements(new Point(e.getX() - this.sourceDrag.x, e.getY() - this.sourceDrag.y));
		this.controller.notifyRepaintTab();
        this.sourceDrag = e.getPoint();
	}
	
	@Override
	public void pressed(Tab tab, Graph graph, MouseEvent e) {
		this.sourceDrag = new Point(e.getX(), e.getY()); // à mettre plus tard dans la méthode pressed de la classe mère ?
		if (!e.isControlDown()) {
			this.controller.notifyMousePressedWithoutControlDown();
		}
	}
	
	@Override
	public void pressed(ElementView element, MouseEvent e) {
		this.sourceDrag = new Point(e.getX(), e.getY());
		if (e.getButton() == MouseEvent.BUTTON1) {
			manageSelection(element, e);
		}
	}
	
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) {
		if (this.dragging) {
			this.controller.notifyEndDragging();
		}
		this.dragging = false;
	}

	@Override
	public void released(ElementView element, MouseEvent e) {
		this.controller.notifyMoveSelectedElements(new Point(e.getX() - this.sourceDrag.x, e.getY() - this.sourceDrag.y));
		if (!e.isControlDown()) {
			this.controller.notifyHandleElement(element);
		}
		this.dragging = false;
	}
	
	@Override
	public String getMode() {
		return "SELECTION";
	}
}
