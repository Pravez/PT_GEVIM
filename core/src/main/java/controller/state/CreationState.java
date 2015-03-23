package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class CreationState extends State {

    /* Le VertexView au-dessus duquel on effectue un drag */
    private VertexView underMouseVertex = null;

    /**
     * Le constructeur de la classe CreationState
     * @param controller le Controller de l'application
     */
	public CreationState(Controller controller) {
		super(controller);
	}

    /**
     * Override de la méthode permettant de savoir que le curseur est au-dessus d'un VertexView :
     * - on stocke dans underMouseVertex ce VertexView
     * @param element le VertexView reçevant l'événement souris mouseEntered
     * @param e l'événement souris
     */
    @Override
    public void mouseEntered(VertexView element, MouseEvent e) {
        this.underMouseVertex = element;
    }

    /**
     * Override de la méthode permettant de savoir que le curseur n'est plus au-dessus du VertexView :
     * - on met underMouseVertex à null
     * @param element le VertexView reçevant l'événement souris mouseExited
     * @param e l'événement souris
     */
    @Override
    public void mouseExited(VertexView element, MouseEvent e) {
        this.underMouseVertex = null;
    }

    /**
     * Override de la méthode quand on clique sur la feuille de dessin :
     * - si c'est un clic gauche, on ajoute un nouveau VertexView
     * - si c'est un clic droit, on ouvre le PopUpMenu concernant le Tab
     * @param tab le Tab qui a reçu l'événement souris clic
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
            Point position = new Point((int)(1.0*e.getX()/this.controller.getCurrentTabScale()), (int)(1.0*e.getY()/this.controller.getCurrentTabScale()));
            this.controller.addVertex(graph, tab.getSheet().getDefaultVerticesColor(), position, tab.getSheet().getDefaultVerticesSize(), tab.getSheet().getDefaultVerticesShape());
            tab.repaint();
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			openTabPopUpMenu(tab, e.getPoint());
		}
	}

    /**
     * Override de la méthode quand on clique sur un ElementView :
     * - si c'est un clic droit on ouvre le PopUpMenu concernant l'ElementView
     * @param element l'ElementView reçevant l'événement souris clic
     * @param e l'événement souris
     */
	@Override
	public void click(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit sur un ElementView
			this.controller.notifyHandleElement(element);
            HashMap<String, String> menus = new HashMap<String, String>(){{put("Edit", "Editer");}{put("Delete", "Supprimer");}{put("Copy", "Copier");}{put("Paste", "Coller");}};
			initNewPopupMenu(menus, e.getPoint()).show(element, e.getX(), e.getY());
		}
	}

    /**
     * Override de la méthode quand on effectue un drag avec la souris sur un VertexView :
     * - on déplace le VertexView selon la position du curseur
     * @param vertex le VertexView reçevant l'événement souris drag
     * @param e l'événement souris
     */
	@Override
	public void drag(VertexView vertex, MouseEvent e) {
		this.dragging = true;
		this.controller.notifyDraggingEdge(vertex.getScaledPosition(), e.getPoint());
	}

    /**
     * Override de la méthode quand on relâche le bouton de la souris sur un ElementView :
     * - on clôt le dessin de l'EdgeView temporaire
     * - si on peut créer une arête, on la créée en mettant en source, le VertexView le plus à gauche
     * @param element l'ElementView reçevant l'événement souris released
     * @param e l'événement souris
     */
	@Override
	public void released(ElementView element, MouseEvent e) {
        VertexView vertex = (VertexView) element;
		this.controller.notifyEndDraggingEdge();
        if (underMouseVertex != null && underMouseVertex != vertex) {
            if (vertex.getPosition().x < underMouseVertex.getPosition().x) {
                this.controller.addEdge(vertex.getVertex(), underMouseVertex.getVertex());
            } else {
                this.controller.addEdge(underMouseVertex.getVertex(), vertex.getVertex());
            }
        }
		this.dragging = false;
	}

    /**
     * Override de la méthode permettant de connaitre le Mode en cours
     * @return CREATION, on est en mode CREATION
     */
	@Override
	public String getMode() {
		return "CREATION";
	}
}
