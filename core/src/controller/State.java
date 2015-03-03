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
	
	public enum Mode { SELECTION, CREATION, ZOOM };
	
	public State(Controller controller) {
		this.controller = controller;
	}
	
	public static State changeState(Mode mode, Controller controller) {
		switch (mode) {
		case CREATION:
			return new CreationState(controller);
		case SELECTION:
			return new SelectionState(controller);
		case ZOOM:
			return new ZoomState(controller);
		default:
			return new SelectionState(controller);
		}
	}
	
	public abstract void click(Tab tab, Graph graph, MouseEvent e);
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
	protected JPopupMenu initNewPopupMenu(String [] menuItems, Point position){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ContextMenuActionListener(jmi, this.controller, null, position));
			jpm.add(jmi);
		}
		return jpm;
	}
}
