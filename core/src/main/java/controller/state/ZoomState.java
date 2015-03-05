package controller.state;

import java.awt.event.MouseEvent;

import controller.Controller;
import view.elements.ElementView;
import view.Tab;
import view.elements.VertexView;
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
	public void click(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void drag(Tab tab, Graph graph, MouseEvent e) {
		this.dragging = true;
	}

	@Override
	public void drag(VertexView vertex, MouseEvent e) {
		this.dragging = true;
	}
	
	@Override
	public void pressed(Tab tab, Graph grap, MouseEvent e) {
		
	}

	@Override
	public void pressed(ElementView element, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void released(Tab tab, Graph grap, MouseEvent e) {
		this.dragging = false;	
	}

	@Override
	public void released(ElementView element, MouseEvent e) {
		this.dragging = false;	
	}
	
	@Override
	public String getMode() {
		return "ZOOM";
	}
}
