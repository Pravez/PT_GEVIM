package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.Sheet;
import view.editor.elements.VertexView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CreationState extends State {

    private VertexView underMouseVertex = null;

	public CreationState(Controller controller) {
		super(controller);
	}

    @Override
    public void mouseEntered(VertexView element, MouseEvent e) {
        this.underMouseVertex = element;
    }

    @Override
    public void mouseExited(VertexView element, MouseEvent e) {
        this.underMouseVertex = null;
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
            this.controller.addVertex(graph, tab.getSheet().getDefaultVertexesColor(), position, tab.getSheet().getDefaultVertexesSize(), tab.getSheet().getDefaultVertexesShape());
            tab.repaint();
        }
	}
	
	@Override
	public void pressed(ElementView element, MouseEvent e) { }
	
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) { }

	@Override
	public void released(ElementView element, MouseEvent e) {
        VertexView vertex = (VertexView) element;
		this.controller.notifyEndDraggingEdge();
        if (underMouseVertex != null && underMouseVertex != vertex){ // à mettre ailleurs ??? --> State ?
            if (vertex.getPosition().x < underMouseVertex.getPosition().x) {
                this.controller.addEdge(vertex.getVertex(), underMouseVertex.getVertex());
            } else {
                this.controller.addEdge(underMouseVertex.getVertex(), vertex.getVertex());
            }
        }
		this.dragging = false;
	}

	@Override
	public String getMode() {
		return "CREATION";
	}
}
