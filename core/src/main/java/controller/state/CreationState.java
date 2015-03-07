package controller.state;

import controller.Controller;
import data.Graph;
import view.elements.ElementView;
import view.Tab;
import view.elements.VertexView;

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
	}
	
	@Override
	public void drag(VertexView vertex, MouseEvent e) {
		this.dragging = true;
		this.controller.notifyDraggingEdge(vertex.getPosition(), e.getPoint());
	}
	
	@Override
	public void pressed(Tab tab, Graph graph, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
            Point position = new Point((int)(1.0*e.getX()/this.controller.getCurrentTabScale()), (int)(1.0*e.getY()/this.controller.getCurrentTabScale()));
            this.controller.addVertex(graph, tab.getDefaultVertexesColor(), position, tab.getDefaultVertexesSize(), tab.getDefaultVertexesShape());
            tab.repaint();
        }
	}
	
	@Override
	public void pressed(ElementView element, MouseEvent e) { }
	
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) { }

	@Override
	public void released(ElementView element, MouseEvent e) {
		this.controller.notifyEndDraggingEdge();
		this.dragging = false;
	}

	@Override
	public String getMode() {
		return "CREATION";
	}
}
