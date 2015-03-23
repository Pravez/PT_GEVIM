package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ZoomState extends State {

	public ZoomState(Controller controller) {
		super(controller);
	}

	@Override
	public void drag(Tab tab, Graph graph, MouseEvent e) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            tab.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
        tab.getScrollPane().setScrollPosition(this.sourceDrag, e.getLocationOnScreen());
		this.dragging = true;
        this.sourceDrag = e.getLocationOnScreen();
	}

    @Override
    public void click(Tab tab, Graph graph, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
            tab.getScrollPane().zoomIn(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
            tab.getScrollPane().zoomOut(e.getX(), e.getY());
        }
    }

	@Override
	public void pressed(Tab tab, Graph grap, MouseEvent e) {
        this.sourceDrag = e.getLocationOnScreen(); // on récupère la position à laquelle on presse la souris
	}

	@Override
	public void pressed(ElementView element, MouseEvent e) {
        this.sourceDrag = e.getPoint(); // on récupère la position à laquelle on presse la souris
    }
	
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) {
        tab.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dragging = false;
	}

	@Override
	public void released(ElementView element, MouseEvent e) {
        this.controller.getWindow().getCurrentTab().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dragging = false;
	}

    @Override
    public void wheel(Tab tab, MouseWheelEvent e) {
        int direction = e.getWheelRotation();
        if (direction > 0) { // on bouge la molette vers le bas : on dézoome
            tab.getScrollPane().zoomOut(e.getX(), e.getY());
        } else { // on bouge la molette vers le haut : on zoome
            tab.getScrollPane().zoomIn(e.getX(), e.getY());
        }
    }
	
	@Override
	public String getMode() {
		return "ZOOM";
	}
}
