package main.java.controller.state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import main.java.controller.Controller;
import main.java.view.elements.ElementView;
import main.java.view.Tab;
import main.java.view.elements.VertexView;
import main.java.data.Graph;

public class ZoomState extends State {

	public ZoomState(Controller controller) {
		super(controller);
	}

	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			initNewPopupMenu(new String[]{"Paste", "Properties"}, e.getPoint()).show(tab, e.getX(), e.getY());
		}
	}
	
	@Override
	public void drag(Tab tab, Graph graph, Point sourceDrag, MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void click(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drag(VertexView vertex, Point sourceDrag, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressed(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void released(ElementView element, Point sourceDrag, MouseEvent e) {
		this.dragging = false;	
	}
	
	@Override
	public String getMode() {
		return "ZOOM";
	}
}
