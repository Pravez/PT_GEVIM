package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.Graph;
import view.ElementView;
import view.Tab;
import view.VertexView;

public abstract class State {
	
	protected Controller controller;
	protected boolean    dragging;
	
	public State(Controller controller) {
		this.controller = controller;
	}
	
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			initNewPopupMenu(new String[]{"Paste", "Properties"}, e.getPoint()).show(tab, e.getX(), e.getY());
		}
	}
	public abstract void click(ElementView element, MouseEvent e);
	public abstract void drag(Tab tab, Graph graph, Point sourceDrag, MouseEvent e);
	public abstract void drag(VertexView vertex, Point sourceDrag, MouseEvent e);
	public abstract void pressed(ElementView element, MouseEvent e);
	public abstract void released(ElementView element, Point sourceDrag, MouseEvent e);
	
	public abstract String getMode();
	
	public boolean isDragging() {
		return this.dragging;
	}
	
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
	
	/**
	 * Méthode permettant de créer un PopupMenu
	 * @param menuItems les MenuItems présents dans le PopuMenu
	 * @param position la position du PopupMenu
	 * @return le PopupMenu créé
	 */
	private JPopupMenu initNewPopupMenu(String [] menuItems, Point position){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ContextMenuActionListener(jmi, this.controller, null, position));
			jpm.add(jmi);
		}
		return jpm;
	}
}
