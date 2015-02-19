package controller;

import data.Graph;
import data.GraphElement;
import data.Vertex;
import view.ElementView;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


// à voir pour créer des onglets : http://openclassrooms.com/courses/apprenez-a-programmer-en-java/conteneurs-sliders-et-barres-de-progression

/**
 * Created by quelemonnier on 26/01/15.
 * Classe Controller, contrôleur principal de l'application
 */
public class Controller {
	
	private Window window;
	private ArrayList<Graph> graphs = new ArrayList<Graph>();
	private State            state;

	private ArrayList<GraphElement> copiedElements;
	
	public Controller() {
		this.state  = State.SELECTION;
		this.graphs = new ArrayList<Graph>();
		copiedElements = new ArrayList<GraphElement>();
	}

	public static enum State { SELECTION, ZOOM_IN, ZOOM_OUT, CREATE };

	/**
	 * Configure la Window du controller (associe la vue au controleur)
	 * @param window la Window qui doit être associée
	 */
	public void setWindow(Window window) {
		this.window = window;
		this.window.setState(this.state);	
	}

	/**
	 * Ajoute un nouveau {@link data.Vertex} au {@link data.Graph} en question
	 * @param g Le graphe auquel le vertex doit être ajouté
     * @param color la couleur du Vertex à ajouter
     * @param position la position du Vertex à ajouter
     * @param size la taille du Vertex à ajouter
     * @param shape la forme du Vertex à ajouter
	 */
	public void addVertex(Graph g, Color color, Point position, int size, Vertex.Shape shape){
		if (this.window.getCurrentTab().canAddVertex(position)) {
			g.createVertex(color, position, size, shape);
		}
	}

	/**
	 * Méthode permettant d'ajouter une Edge au Graph courant
	 * @param src le Vertex de départ de l'Edge
	 * @param dst le Vertex de destination de l'Edge
	 */
	public void addEdge (Vertex src, Vertex dst){
		this.window.getCurrentTab().getGraph().createEdge(this.window.getCurrentTab().getDefaultColor(), src, dst, this.window.getCurrentTab().getDefaultThickness());
	}

	/**
	 * Méthode permettant de retirer un Vertex d'un Graph
	 * @param g le Graph
	 * @param o le Vertex en Object
	 */
	public void removeVertex(Graph g, Object o){
		// Non utilisée pour l'instant
		g.getVertexes().remove(o);
	}
	
	/**
	 * Getter de l'état du Controller, de son mode : SELECTION, CREATION, ZOOM_IN, ZOOM_OUT, ...
	 * @return l'état du Controller
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Renvoie le {@link data.Graph} situé à l'indice numGraph
	 * @param numGraph Numéro du graphe
	 * @return Le graphe situé à l'indice
	 */
	public Graph getGraph(int numGraph){
		return graphs.get(numGraph);
	}

	/**
	 * Crée un nouveau {@link data.Graph}, l'ajoute à l'{@link java.util.ArrayList} de graphes et le renvoie
	 * @return Le graphe nouvellement crée
	 */
	public Graph addNewGraph(){
		Graph graph = new Graph();
		graphs.add(graph);
		return graph;
	}

	/**
	 * Main du logiciel de visualisation de graphes
	 * @param args
	 */
    public static void main(String[] args){

    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }

    	Controller controller = new Controller();
		Window window = new Window(800, 640, controller);
        controller.setWindow(window);
    }

    /**
     * Méthode appelée lorsqu'un bouton du menu a été activé par l'utilisateur, agit en fonction du bouton
     * @param type le nom du bouton activé (sa fonction)
     */
	public void notifyMenuItemActivated(String type) {
		switch (type) {
		case "New":
			String title = "Tab " + this.window.getTabCount();
			title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);
			if (title != null) {
				Graph graph = addNewGraph();
				this.window.addNewTab(graph, title);
			}
			break;
			
		case "Close":
			//Attention, la fermeture ne libère probablement pas toute la mémoire ...
			if(this.window.getTabCount() > 0) {
				if (JOptionPane.showConfirmDialog(this.window, "Souhaitez vous fermer ce graphe ? (Vous devriez peut-être sauvegarder ...?)", "Fermer le graphe", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					this.graphs.remove(this.graphs.get(this.window.getCurrentTabIndex()));
					this.window.getTabs().remove(this.window.getCurrentTab());
				}
			}else {
				if (JOptionPane.showConfirmDialog(this.window, "Souhaitez vous vraiment quitter ?", "Fermer le programme", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					this.graphs.clear();
					this.window.getTabs().removeAll();
					this.window.dispose();
				}
			}

			break;

		case "Save":
			this.window.getCurrentTab().saveToGraphml("test.gml");
			
		default:
			break;
		}
	}

	/**
	 * Méthode appelée lorsqu'un ElementView a été sélectionné :
	 *   - vide la liste des objets sélectionnés
	 *   - ajoute le ElementView à la liste des ElementView sélectionnés
	 * @param selectedElement le ElementView sélectionné
	 */
	public void notifyElementSelected(ElementView selectedElement) {
		this.window.getCurrentTab().clearSelectedElements();
		this.window.getCurrentTab().selectElement(selectedElement);
	}
	
	/**
	 * Méthode appelée lorsqu'un ElementView est sélectionné
	 * - s'il n'est pas sélectionné, il est ajouté à la liste des ElementView sélectionnés
	 * - s'il est déjà sélectionné, il est retiré de la liste des ElementView sélectionnés
	 * @param selectedElement le ElementView sélectionné à ajouter
	 */
	public void notifyHandleElementSelected(ElementView selectedElement) {
		if (this.window.getCurrentTab().getSelectedElements().contains(selectedElement)) {
			this.window.getCurrentTab().unselectElement(selectedElement);
		} else {
			this.window.getCurrentTab().selectElement(selectedElement);
		}
	}
	
	/**
	 * Méthode appelée lorsque l'on gère une sélection :
	 * - si l'élément n'est pas dans la sélection, on vide les ElementView sélectionné et on met le nouveau ElementView
	 * - sinon on ne fait rien
	 * @param selectedElement
	 */
	public void notifyHandleElement(ElementView selectedElement) {
		if (!this.window.getCurrentTab().getSelectedElements().contains(selectedElement)) {
			this.window.getCurrentTab().clearSelectedElements();
			this.window.getCurrentTab().selectElement(selectedElement);
		}
	}
	
	/**
	 * Méthode appelée pour vider la liste des IElementView sélectionnés
	 */
	public void notifyClearSelection() {
		this.window.getCurrentTab().clearSelectedElements();
	}
	
	/**
	 * Méthode appelée pour demander de rafficher le Tab en cours
	 */
	public void notifyRepaintTab() {
		this.window.getCurrentTab().repaint();
	}

	/**
	 * Méthode appellée lorsqu'un item d'un menu contextuel a été sollicité. Elle associé à son nom une action.
	 * @param text le nom de l'item source
	 * @param source l'ElementView ayant activé le menu contextuel
	 */
	public void notifyContextMenuItemActivated(String text, ElementView source) {
		switch (text) {
			case "Edit":
				this.window.getCurrentTab().modifySelectedElement();
				break;
			case "Delete":
				for (ElementView e : this.window.getCurrentTab().getSelectedElements()) {
					this.getGraph(this.window.getCurrentTabIndex()).removeGraphElement(e.getGraphElement());
				}
				this.window.getCurrentTab().clearSelectedElements();
				break;
			case "Copy":
				copyElements();
				break;
			case "Paste":
				pasteElements();
				break;
			default:
				break;
		}
		this.window.getCurrentTab().repaint();
	}

	/**
	 * Méthode appelée lors d'un clic sur un bouton du JToolBar de la Window
	 * @param text le nom du bouton
	 */
	public void notifyToolBarItemActivated(String text) {
		switch(text){
			case "New":
				String title = "Tab " + this.window.getTabCount();
				title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);
				if (title != null) {
					Graph graph = addNewGraph();
					this.window.addNewTab(graph, title);
				}
				break;
			case "Copy":
				copyElements();
				break;
			case "Paste":
				pasteElements();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Méthode permettant de créer une copie des GraphElement provenants des ElementView sélectionnés
	 */
	public void copyElements() {
		copiedElements.clear();
		// on récupère les GraphElement sélectionnés dans le Tab
		for(ElementView elementView : this.window.getCurrentTab().getSelectedElements()){
			copiedElements.add(elementView.getGraphElement());
		}
		// on crée une copie de ces GraphElements
		copiedElements = Graph.copyGraphElements(this.copiedElements);
	}
	
	/**
	 * Méthode permettant de "coller" les GraphElement copiés depuis la sélection
	 */
	public void pasteElements() {
		this.graphs.get(this.window.getCurrentTabIndex()).addGraphElements(Graph.copyGraphElements(this.copiedElements));
	}

	/**
	 * Méthode appelée lorsqu'un bouton de contexte du JToolBar de la Window est sélectionné
	 * @param button le bouton correspondant
	 */
	public void notifyToolBarContextActivated(JButton button) {
		this.state = State.valueOf(button.getActionCommand());
		this.window.setState(this.state);

		if(this.window.getTabCount() > 0)
			this.window.getCurrentTab().clearSelectedElements();
	}

	/**
	 * Méthode appellée lors d'un drag de l'utilisateur sur un onglet
	 * @param origin Le point initial d'où a commencé le drag
	 * @param position Le point actuel où en est le drag
	 */
	public void notifyDragging(Point origin, Point position) {
		this.window.getCurrentTab().launchSelectionZone(origin, position);
	}

	/**
	 * Méthode appellée au début d'un drag d'un vertex vers un autre, pour dessiner une "pseudo-edge" temporaire qui n'est qu'une
	 * succession de points sans consistance.
	 * @param origin Le point d'origine de l'Edge temporaire
	 * @param position Le point de destination de l'Edge.
	 */
	public void notifyDraggingEdge(Point origin, Point position) {
		this.window.getCurrentTab().launchTemporarilyEdge(origin, position);
	}

	public void notifyEndDraggingEdge(){
		this.window.getCurrentTab().endTemporarilyEdge();
	}

	/**
	 * Méthode notifiant la fin du dragging de l'utilisateur
	 */
	public void notifyEndDragging() {
		this.window.getCurrentTab().handleSelectionZone();
	}

	/**
	 * Méthode faisant appel à déplacer des éléments sur un Onglet, suivant un certain point.
	 * @param vector Le point vers lequel doivent se déplacer les éléments
	 */
	public void notifyMoveSelectedElements(Point vector) {
		this.window.getCurrentTab().moveSelectedElements(vector);
	}
}
