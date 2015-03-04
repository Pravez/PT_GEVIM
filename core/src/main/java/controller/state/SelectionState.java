package main.java.controller.state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.java.controller.ContextMenuActionListener;
import main.java.controller.Controller;
import main.java.view.elements.ElementView;
import main.java.view.Tab;
import main.java.view.elements.VertexView;
import main.java.data.Graph;

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
	private JPopupMenu initElementPopupMenu(String [] menuItems, Point position, ElementView element){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ContextMenuActionListener(jmi, this.controller, element, position));
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
			initNewPopupMenu(new String[]{"Paste", "Properties"}, e.getPoint()).show(tab, e.getX(), e.getY());
		}
		this.controller.notifyClearSelection();
	}
	
	@Override
	public void click(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			this.controller.notifyHandleElement(element);
			//Création du menu contextuel avec Edit et Delete comme options.
			JPopupMenu contextMenu = initElementPopupMenu(new String[]{"Edit", "Delete", "Copy", "Paste"}, e.getPoint(), element);
			contextMenu.show(element, e.getX(), e.getY());
		}
	}

	@Override
	public void drag(Tab tab, Graph graph, Point sourceDrag, MouseEvent e) {
		this.dragging = true;
		this.controller.notifyDragging(sourceDrag, e.getPoint());
	}

	@Override
	public void drag(VertexView vertex, Point sourceDrag, MouseEvent e) {
		this.dragging = true;
		this.controller.notifyMoveSelectedElements(new Point(e.getX() - sourceDrag.x, e.getY() - sourceDrag.y));
		this.controller.notifyRepaintTab();
	}
	
	@Override
	public void pressed(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			manageSelection(element, e);
		}
	}

	@Override
	public void released(ElementView element, Point sourceDrag, MouseEvent e) {
		this.controller.notifyMoveSelectedElements(new Point(e.getX() - sourceDrag.x, e.getY() - sourceDrag.y));
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
