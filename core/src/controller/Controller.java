package controller;

import data.Graph;
import data.GraphElement;
import data.Vertex;
import view.EdgeView;
import view.IElementView;
import view.VertexView;
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
	
	public Controller() {
		this.state  = State.SELECTION;
		this.graphs = new ArrayList<Graph>();
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

	public void addEdge (Vertex src, Vertex dst){
		this.window.getCurrentTab().getGraph().createEdge(this.window.getCurrentTab().getDefaultColor(),src, dst,this.window.getCurrentTab().getDefaultThickness());
	}

	public void removeVertex(Graph g, Object o){
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
			if (JOptionPane.showConfirmDialog(this.window,"Souhaitez vous vraiment quitter ? (Vous devriez peut-être sauvegarder ...?)", "Fermer le programme", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
				this.graphs.clear();
				this.window.getTabs().removeAll();
				this.window.dispose();
			}
			break;

		case "Save":
			this.window.getCurrentTab().saveToGraphml("test.gml");
			
		default:
			break;
		}
	}

	/**
	 * Méthode appelée lorsqu'un IElementView a été sélectionné :
	 *   - vide la liste des objets sélectionnés
	 *   - ajoute le IElementView à la liste des IElementView sélectionnés
	 * @param selectedElement le IElementView sélectionné
	 */
	public void notifyElementSelected(IElementView selectedElement) {
		this.window.getCurrentTab().clearSelectedElements();
		this.window.getCurrentTab().selectElement(selectedElement);
	}
	
	/**
	 * Méthode appelée lorsqu'un IElementView sélectionné doit être ajouté à la liste des VertexView sélectionnés
	 * @param selectedElement le IElementView sélectionné à ajouter
	 */
	public void notifyElementAddToSelection(IElementView selectedElement) {
		this.window.getCurrentTab().selectElement(selectedElement);
	}
	
	/**
	 * Méthode appelée pour retirer un IElementView de la liste des IElementView sélectionnés
	 * @param element le IElementView sélectionné à retirer de la liste
	 */
	public void notifyElementRemoveFromSelection(IElementView element) {
		this.window.getCurrentTab().selectElement(element);
	}
	
	/**
	 * Méthode appelée pour vider la liste des IElementView sélectionnés
	 */
	public void notifyElementClearSelection() {
		this.window.getCurrentTab().clearSelectedElements();
	}

	/**
	 * Méthode appellée lorsqu'un item d'un menu contextuel a été sollicité. Elle associé à son nom une action.
	 * @param text le nom de l'item source
	 * @param source
	 */
	public void notifyContextMenuItemActivated(String text, Object source) {

		switch(text){
			case "Edit":
				this.window.getCurrentTab().modifySelectedElement();

				break;

			case "Delete":
				if(source.getClass() == VertexView.class) {
					for (IElementView element : this.window.getCurrentTab().getSelectedElements()) {
						this.getGraph(this.window.getCurrentTabIndex()).removeVertex(((VertexView) element).getVertex());
					}
				} else if(source.getClass() == EdgeView.class) {
					for(IElementView element : this.window.getCurrentTab().getSelectedElements()){
						this.getGraph(this.window.getCurrentTabIndex()).removeEdge(((EdgeView) element).getEdge());
					}
				}
				this.getGraph(this.window.getCurrentTabIndex()).setChanged();
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
		// TODO Auto-generated method stub
	}

	/**
	 * Méthode appelée lorsqu'un bouton de contexte du JToolBar de la Window est sélectionné
	 * @param button le bouton correspondant
	 */
	public void notifyToolBarContextActivated(JButton button) {
		this.state = State.valueOf(button.getActionCommand());
		this.window.setState(this.state);		
	}
}