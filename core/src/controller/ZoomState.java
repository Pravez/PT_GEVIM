package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;

import view.ElementView;
import view.Tab;
import view.VertexView;
import data.Graph;

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
	public String getMode() {
		return "ZOOM";
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
}
