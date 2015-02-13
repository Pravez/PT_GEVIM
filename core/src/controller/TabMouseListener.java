package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.Tab;
import data.Graph;

public class TabMouseListener implements MouseListener {
	
	private Controller controller;
	private Tab        tab;
	private Graph      graph;
	
	public TabMouseListener(Controller controller, Tab tab, Graph graph) {
		this.controller = controller;
		this.tab        = tab;
		this.graph      = graph;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		switch(mouseEvent.getButton()) {
        case MouseEvent.BUTTON1: // Clic gauche
            this.controller.addVertex(this.graph, mouseEvent.getPoint());
            this.tab.repaint();
            break;

        case MouseEvent.BUTTON3: // clic droit
            break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		 /*System.out.println("pressed");
        Vertex vertexTmp = ((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent);
        if(vertexTmp != null){
            System.out.println("onVezretex");
        }*/
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		//if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null){ }
	}
}