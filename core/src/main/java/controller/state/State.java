package controller.state;

import controller.Controller;
import controller.listeners.ButtonActionListener;
import data.Graph;
import view.editor.Tab;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;

/**
 * Classe abstraite State recueillant les différents événements souris pour les utiliser dans les différents modes de l'application qui vont
 * hériter de cette classe
 */
public abstract class State {

    /* Le Controller de l'application */
	protected Controller controller;
    /* Booléen permettant de savoir si on est en train d'effectuer un drag */
	protected boolean    dragging;
    /* Point permettant de stocker la position de départ du drag */
	protected Point      sourceDrag;

    /* Enumération listant les différents modes de l'application */
    public enum Mode { SELECTION, CREATION, ZOOM }

	/**
	 * Constructeur de la classe State
	 * @param controller le controller principal de l'application
	 */
	public State(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Méthode statique permettant de changer d'Etat en fonction du mode demandé
	 * @param mode le mode demandé, c'est l'énumération publique de la classe State, regroupant l'ensemble des Etats du Controller
	 * @param controller le Controller de l'application
	 * @return le nouveau State en fonction du mode demandé
	 */
	public static State changeState(Mode mode, Controller controller) {
		switch (mode) {
		case CREATION:
			return new CreationState(controller);
		case SELECTION:
			return new SelectionState(controller);
		case ZOOM:
			return new ZoomState(controller);
		default:
			return new SelectionState(controller);
		}
	}

    /**
     * Méthode appelée lorsque la souris entre sur un ElementView
     * @param element le VertexView reçevant l'événement souris mouseEntered
     * @param e l'événement souris
     */
    public void mouseEntered(VertexView element, MouseEvent e) {

    }
    /**
     * Méthode appelée lorsque la souris sort d'un ElementView
     * @param element le VertexView reçevant l'événement souris mouseExited
     * @param e l'événement souris
     */
    public void mouseExited(VertexView element, MouseEvent e) {

    }
	/**
	 * Méthode appelée lors d'un clic sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris clic
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public void click(Tab tab, Graph graph, MouseEvent e) { }
	/**
	 * Méthode appelée lors d'un clic sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris clic
	 * @param e l'événement souris
	 */
	public void click(ElementView element, MouseEvent e) { }
	/**
	 * Méthode appelée lors d'un drag sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public void drag(Tab tab, Graph graph, MouseEvent e) { }
	/**
	 * Méthode appelée lors d'un drag sur un VertexView
	 * @param vertex le VertexView reçevant l'événement souris drag
	 * @param e l'événement souris
	 */
	public void drag(VertexView vertex, MouseEvent e) { }
	/**
	 * Méthode appelée lorsque la souris est appuyée sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public void pressed(Tab tab, Graph graph, MouseEvent e) { }
	/**
	 * Méthode appelée lorsque la souris est appuyée sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris pressed
	 * @param e l'événement souris
	 */
	public void pressed(ElementView element, MouseEvent e) { }
	/**
	 * Méthode appelée lorsque la souris est relâchée sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public void released(Tab tab, Graph graph, MouseEvent e) { }
	/**
	 * Méthode appelée lorsque la souris est relâchée sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris released
	 * @param e l'événement souris
	 */
	public void released(ElementView element, MouseEvent e) { }

    /**
     * Méthode appelée lorsque la molette de la souris est actionnée
     * @param tab le Tab qui a reçu l'événement souris wheel
     * @param e l'événement souris
     */
    public void wheel(Tab tab, MouseWheelEvent e) { }
	
	/**
	 * Méthode permettant de retourner le Mode de la classe héritant de State
	 * @return le mode associé à la classe héritant de State
	 */
	public abstract String getMode();
	
	/**
	 * Méthode permettant de créer un PopupMenu
	 * @param menuItems les MenuItems présents dans le PopuMenu (Map contenant d'un coté le string de la commande, de l'autre
     *                  le string qui sera affiché).
	 * @param position la position du PopupMenu
	 * @return le PopupMenu créé
	 */
	protected JPopupMenu initNewPopupMenu(HashMap<String, String> menuItems,  Point position){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems.keySet()){
			JMenuItem jmi = new JMenuItem(menuItems.get(s));
            jmi.setActionCommand(s);
			jmi.addActionListener(new ButtonActionListener(jmi, position, ""));
			jpm.add(jmi);
		}
		return jpm;
	}

	/**
	 * Méthode permettant d'oubrir un PopUp Menu pour le Tab
	 * @param tab le Tab dans lequel il faut créer le PopUp Menu
	 * @param mouse_pos position de la souris
	 */
	protected void openTabPopUpMenu(Tab tab, Point mouse_pos) {
		Point show_pos   = new Point(mouse_pos.x - tab.getScrollPane().getViewport().getViewPosition().x, mouse_pos.y - tab.getScrollPane().getViewport().getViewPosition().y);
		Point effect_pos = new Point((int)(mouse_pos.x / tab.getSheet().getScale()), (int)(mouse_pos.y / tab.getSheet().getScale()));
		initNewPopupMenu(new HashMap<String, String>(){{put("Paste", "Coller");}{put("Properties", "Proprietes");}}, effect_pos).show(tab, show_pos.x, show_pos.y);
	}
}
