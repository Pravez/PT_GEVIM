package controller.state;

import controller.Controller;
import controller.listeners.ButtonActionListener;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectionState extends State {

	public SelectionState(Controller controller) {
		super(controller);
	}
	
	/**
	 * Initialize a Popup menu with MenuItems
	 * @param menuItems the MenuItems to be present in the popUp menu
	 * @param position la position du PopuMenu
	 * @return The popup menu created
	 */
	private JPopupMenu initElementPopupMenu(String [] menuItems, Point position){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ButtonActionListener(jmi, position, 0));
			jpm.add(jmi);
		}
		return jpm;
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
	}

	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			Point show_pos   = new Point(e.getX() - tab.getScrollPane().getViewport().getViewPosition().x, e.getY() - tab.getScrollPane().getViewport().getViewPosition().y);
			Point effect_pos = new Point((int)(e.getX() / tab.getSheet().getScale()), (int)(e.getY() / tab.getSheet().getScale()));
			initNewPopupMenu(new String[]{"Paste", "Properties"}, effect_pos).show(tab, show_pos.x, show_pos.y);
		}
		this.controller.notifyClearSelection();
	}
	
	@Override
	public void click(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			this.controller.notifyHandleElement(element);
			//Création du menu contextuel avec Edit et Delete comme options.
			JPopupMenu contextMenu = initElementPopupMenu(new String[]{"Edit", "Delete", "Copy", "Paste"}, e.getPoint());
			contextMenu.show(element, e.getX(), e.getY());
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
	public void pressed(Tab tab, Graph grap, MouseEvent e) {
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
	public void released(Tab tab, Graph grap, MouseEvent e) {
		if (this.dragging) {
			this.controller.notifyEndDragging();
		}
		this.dragging = false;
		//if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null){ }
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
