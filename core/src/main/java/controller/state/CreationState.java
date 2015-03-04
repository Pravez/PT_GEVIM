package main.java.controller.state;

import main.java.controller.Controller;
import main.java.data.Graph;
import main.java.view.elements.ElementView;
import main.java.view.Tab;
import main.java.view.elements.VertexView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CreationState extends State {

	public CreationState(Controller controller) {
		super(controller);
	}
	
	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			initNewPopupMenu(new String[]{"Paste", "Properties"}, e.getPoint()).show(tab, e.getX(), e.getY());
		}
		if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
			this.controller.addVertex(graph, tab.getDefaultColor(), e.getPoint(), tab.getDefaultSize(), tab.getDefaultShape());
            tab.repaint();
		}
	}
	
	@Override
	public void click(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void drag(Tab tab, Graph graph, Point sourceDrag, MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void drag(VertexView vertex, Point sourceDrag, MouseEvent e) {
		this.dragging = true;
		this.controller.notifyDraggingEdge(vertex.getPosition(), e.getPoint());
	}
	
	@Override
	public void pressed(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void released(ElementView element, Point sourceDrag, MouseEvent e) {
		this.controller.notifyEndDraggingEdge();
		this.dragging = false;
	}

	@Override
	public String getMode() {
		return "CREATION";
	}
}
