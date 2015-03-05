package controller.state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.ContextMenuActionListener;
import controller.Controller;
import data.Graph;
import view.elements.ElementView;
import view.Tab;
import view.elements.VertexView;

public abstract class State {
	
	protected Controller controller;
	protected boolean    dragging;
	protected Point      sourceDrag;
	
	public enum Mode { SELECTION, CREATION, ZOOM };
	
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
	 * Méthode abstraite appelée lors d'un clic sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris clic
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public abstract void click(Tab tab, Graph graph, MouseEvent e);
	/**
	 * Méthode abstraite appelée lors d'un clic sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris clic
	 * @param e l'événement souris
	 */
	public abstract void click(ElementView element, MouseEvent e);
	/**
	 * Méthode abstraite appelée lors d'un drag sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public abstract void drag(Tab tab, Graph graph, MouseEvent e);
	/**
	 * Méthode abstraite appelée lors d'un drag sur un VertexView
	 * @param vertex le VertexView reçevant l'événement souris drag
	 * @param e l'événement souris
	 */
	public abstract void drag(VertexView vertex, MouseEvent e);
	/**
	 * Méthode abstraite appelée lorsque la souris est appuyée sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public abstract void pressed(Tab tab, Graph graph, MouseEvent e);
	/**
	 * Méthode abstraite appelée lorsque la souris est appuyée sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris pressed
	 * @param e l'événement souris
	 */
	public abstract void pressed(ElementView element, MouseEvent e);
	/**
	 * Méthode abstraite appelée lorsque la souris est relâchée sur la feuille de dessin d'un Tab
	 * @param tab le Tab qui a reçu l'événement souris drag
	 * @param graph le Graph correspondant au Tab
	 * @param e l'événement souris
	 */
	public abstract void released(Tab tab, Graph graph, MouseEvent e);
	/**
	 * Méthode abstraite appelée lorsque la souris est relâchée sur un ElementView
	 * @param element l'ElementView reçevant l'événement souris released
	 * @param e l'événement souris
	 */
	public abstract void released(ElementView element, MouseEvent e);
	
	/**
	 * Méthode permettant de retourner le Mode de la classe héritant de State
	 * @return le mode associé à la classe héritant de State
	 */
	public abstract String getMode();
	
	/**
	 * Méthode permettant de savoir si on est en train d'effectuer un événement souris drag
	 * @return le booléen contenant le résultat
	 */
	public boolean isDragging() {
		return this.dragging;
	}
	
	/**
	 * Setter du booléen permettant de savoir si on est en train d'effectuer un événement souris drag
	 * @param dragging le nouvel état du drag
	 */
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
	
	/**
	 * Méthode permettant de créer un PopupMenu
	 * @param menuItems les MenuItems présents dans le PopuMenu
	 * @param position la position du PopupMenu
	 * @return le PopupMenu créé
	 */
	protected JPopupMenu initNewPopupMenu(String [] menuItems, Point position){
		JPopupMenu jpm = new JPopupMenu();
		for(String s : menuItems){
			JMenuItem jmi = new JMenuItem(s);
			jmi.addActionListener(new ContextMenuActionListener(jmi, this.controller, null, position));
			jpm.add(jmi);
		}
		return jpm;
	}
}
