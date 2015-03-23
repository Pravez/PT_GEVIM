package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ZoomState extends State {

    /**
     * Constructeur de la classe ZoomState
     * @param controller le Controller de l'application
     */
	public ZoomState(Controller controller) {
		super(controller);
	}

    /**
     * Override de la méthode quand on effectue un drag avec la souris sur le Tab :
     * - on met le curseur de déplacement
     * - on déplace la feuille de dessin
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
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

    /**
     * Ovveride de la méthode quand on clique sur la feuille de dessin :
     * - on zoome si c'est un clic gauche
     * - on dézoome si c'est un clic droit
     * @param tab le Tab qui a reçu l'événement souris clic
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
    @Override
    public void click(Tab tab, Graph graph, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
            tab.getScrollPane().zoomIn(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
            tab.getScrollPane().zoomOut(e.getX(), e.getY());
        }
    }

    /**
     * Override de la méthode quand on appuie avec la souris sur la feuille de dessin :
     * - on récupère la position de la souris à l'écran
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void pressed(Tab tab, Graph graph, MouseEvent e) {
        this.sourceDrag = e.getLocationOnScreen(); // on récupère la position à laquelle on presse la souris
	}

    /**
     * Override de la méthode quand on appuie avec la souris sur la feuille de dessin :
     * - on récupère la position de la souris à l'écran
     * @param element l'ElementView reçevant l'événement souris pressed
     * @param e l'événement souris
     */
	@Override
	public void pressed(ElementView element, MouseEvent e) {
        this.sourceDrag = e.getLocationOnScreen(); // on récupère la position à laquelle on presse la souris
    }

    /**
     * Override de la méthode quand on relâche le bouton de la souris sur la feuille de dessin :
     * - on remet le curseur par défaut
     * - on termine le drag
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) {
        tab.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dragging = false;
	}

    /**
     * Override de la méthode quand on relâche le bouton de la souris sur la feuille de dessin :
     * - on remet le curseur par défaut
     * - on termine le drag
     * @param element l'ElementView reçevant l'événement souris released
     * @param e l'événement souris
     */
	@Override
	public void released(ElementView element, MouseEvent e) {
        this.controller.getWindow().getCurrentTab().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dragging = false;
	}

    /**
     * Override de la méthode lorsque l'on déplace la molette de la souris :
     * - on zoome si la molette est déplacée vers le haut
     * - on dézoome si la molette est déplacée vers le bas
     * @param tab le Tab qui a reçu l'événement souris wheel
     * @param e l'événement souris
     */
    @Override
    public void wheel(Tab tab, MouseWheelEvent e) {
        int direction = e.getWheelRotation();
        if (direction > 0) { // on bouge la molette vers le bas : on dézoome
            tab.getScrollPane().zoomOut(e.getX(), e.getY());
        } else { // on bouge la molette vers le haut : on zoome
            tab.getScrollPane().zoomIn(e.getX(), e.getY());
        }
    }

    /**
     * Override de la méthode permettant de connaitre le Mode en cours
     * @return ZOOM, on est en mode ZOOM
     */
	@Override
	public String getMode() {
		return "ZOOM";
	}
}
