package controller.state;

import controller.Controller;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class SelectionState extends State {

    /**
     * Constructeur de la classe SelectionState
     * @param controller le Controller de l'application
     */
	public SelectionState(Controller controller) {
		super(controller);
	}

	/**
	 * Méthode pour gérer la sélection d'un Vertex : 
	 * - soit on l'ajoute à la sélection si la touche Ctrl est enfoncée
	 * - soit on désire le sélectionner
	 * @param e MouseEvent pour récupérer l'événement isControlDown
	 */
	private void manageSelection(ElementView element, MouseEvent e) {
		if (e.isControlDown()) {
			this.controller.notifyHandleElementSelected(element);
		} else {
			this.controller.notifyHandleElement(element);
		}
        this.controller.notifyRepaintTab();
	}

    /**
     * Override de la méthode quand on clique sur la feuille de dessin :
     * - si c'est un clic droit, on ouvre le PopUpMenu concernant le Tab
     * - on clôt l'affichage de la zone de sélection
     * @param tab le Tab qui a reçu l'événement souris clic
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void click(Tab tab, Graph graph, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			openTabPopUpMenu(tab, e.getPoint());
		}
		this.controller.notifyClearSelection();
	}

    /**
     * Override de la méthode quand on clique sur un ElementView :
     * - si c'est un clic droit, on demande au Controller de le sélectionner et on ouvre le PopuMenu concernant l'ElementView
     * @param element l'ElementView reçevant l'événement souris clic
     * @param e l'événement souris
     */
	@Override
	public void click(ElementView element, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
			this.controller.notifyHandleElement(element);
			//Création du menu contextuel avec Edit et Delete comme options.
            HashMap<String, String> menus = new HashMap<String, String>(){{put("Edit", "Editer");}{put("Delete", "Supprimer");}{put("Copy", "Copier");}{put("Paste", "Coller");}};
            initNewPopupMenu(menus, e.getPoint()).show(element, e.getX(), e.getY());
		}
	}

    /**
     * Override la méthode quand on effectue un drag avec la souris sur le Tab :
     * - on créé une zone de sélection en sélectionnant les éléments selon si la touche Ctrl est enfoncée ou non
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void drag(Tab tab, Graph graph, MouseEvent e) {
        this.dragging = true;
		if (e.isControlDown()) {
			this.controller.notifyAddToDragging(this.sourceDrag, e.getPoint());
		} else {
			this.controller.notifyDragging(this.sourceDrag, e.getPoint());
		}
	}

	/**
	 * Override de la méthode quand on effectue un drag avec la souris sur un VertexView :
     * - si on commence à faire le drag, on initialise le déplacement des ElementView sélectionnés
     * - sinon on déplace les ElementView sélectionnés
     * - on repeint le Tab et on sauvegarde la position actuelle du curseur
	 * (non-Javadoc)
	 * @see controller.state.State#drag(view.editor.elements.VertexView, java.awt.event.MouseEvent)
	 */
	@Override
	public void drag(VertexView vertex, MouseEvent e) {
        if (!this.dragging) {
            this.dragging = true;
            this.controller.notifyInitMoveSelectedElements(new Point(e.getX() - this.sourceDrag.x, e.getY() - this.sourceDrag.y));
        } else {
            this.controller.notifyMoveSelectedElements(new Point(e.getX() - this.sourceDrag.x, e.getY() - this.sourceDrag.y));
        }
		this.controller.notifyRepaintTab();
        this.sourceDrag = e.getPoint();
	}

    /**
     * Override de la méthode quand on presse le bouton de la souris sur la feuille de dessin :
     * - si on n'a pas la touche Ctrl enfoncée, on déselectionne les ElementView sélectionnés
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void pressed(Tab tab, Graph graph, MouseEvent e) {
		this.sourceDrag = new Point(e.getX(), e.getY()); // à mettre plus tard dans la méthode pressed de la classe mère ?
		if (!e.isControlDown()) {
			this.controller.notifyMousePressedWithoutControlDown();
		}
	}

    /**
     * Override de la méthode quand on presse le bouton de la souris sur un ElementView :
     * - si c'est avec le clic gauche, on s'occupe de gérer la sélection de l'ElementView en fonction de l'état de la touche Ctrl
     * @param element l'ElementView reçevant l'événement souris pressed
     * @param e l'événement souris
     */
	@Override
	public void pressed(ElementView element, MouseEvent e) {
		this.sourceDrag = new Point(e.getX(), e.getY());
		if (e.getButton() == MouseEvent.BUTTON1) {
			manageSelection(element, e);
		}
	}

    /**
     * Override de la méthode quand on relâche le bouton de la souris sur la feuille de dessin :
     * - si on était en train d'effectuer un drag, on sélectionne les ElementView à l'intérieur de la zone de sélection
     * @param tab le Tab qui a reçu l'événement souris drag
     * @param graph le Graph correspondant au Tab
     * @param e l'événement souris
     */
	@Override
	public void released(Tab tab, Graph graph, MouseEvent e) {
		if (this.dragging) {
			this.controller.notifyEndDragging();
		}
		this.dragging = false;
	}

    /**
     * Override de la méthode quand on relâche le bouton de la souris sur un ElementView :
     * - si la touche Ctrl n'est pas enfoncée, on sélectionne l'ElementView
     * - si on était en train de faire un drag, on clôt le déplacement des ElementView sélectionnés
     * @param element l'ElementView reçevant l'événement souris released
     * @param e l'événement souris
     */
	@Override
	public void released(ElementView element, MouseEvent e) {
		if (!e.isControlDown()) {
			this.controller.notifyHandleElement(element);
		}
        if (this.dragging) {
            this.controller.notifyEndMoveSelectedElements();
        }
		this.dragging = false;
	}

    /**
     * Override de la méthode permettant de connaitre le Mode en cours
     * @return SELECTION, on est en mode SELECTION
     */
	@Override
	public String getMode() {
		return "SELECTION";
	}
}
