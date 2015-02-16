package controller;

import data.Graph;
import data.Vertex;
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

	/**
	 * Configure la Window du controller (associe la vue au controleur)
	 * @param window la Window qui doit être associée
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/**
	 * Ajoute un nouveau {@link data.Vertex} au {@link data.Graph} en question
	 * @param g Le graphe auquel le vertex doit être ajouté
	 * @param value la valeur du Vertex à ajouter
     * @param color la couleur du Vertex à ajouter
     * @param position la position du Vertex à ajouter
     * @param size la taille du Vertex à ajouter
     * @param shape la forme du Vertex à ajouter
	 */
	public void addVertex(Graph g, int value, Color color, Point position, int size, Vertex.Shape shape){
		if (this.window.getCurrentTab().canAddVertex(position)) {
			g.createVertex(value, color, position, size, shape);
		}
	}

	public void addEdge (Vertex src, Vertex dst){
		this.window.getCurrentTab().getGraph().createEdge(0,
															this.window.getCurrentTab().getDefaultColor(),
															src, dst,
															this.window.getCurrentTab().getDefaultSize());
	}

	public void removeVertex(Graph g, Object o){
		g.getVertexes().remove(o);
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
			//if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment lancer une nouvelle partie ?", "Nouvelle Partie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION)
			Graph graph = addNewGraph();
			this.window.addNewTab(graph);
			break;
			
		case "Close":
			break;
			
		default:
			break;
		}
	}

	/**
	 * Méthode appelée lorsqu'un VertexView a été sélectionné :
	 *   - vide la liste des VertexView sélectionnés
	 *   - ajoute le VertexView à la liste des VertexView sélectionnés
	 * @param selectedVertext le VertexView sélectionné
	 */
	public void notifyVertexSelected(VertexView selectedVertext) {
		this.window.getCurrentTab().clearSelectedItem();
		this.window.getCurrentTab().selectVertex(selectedVertext);
	}
	
	/**
	 * Méthode appelée lorsqu'un VerteView sélectionné doit être ajouté à la liste des VertexView sélectionnés
	 * @param selectedVertex le VertexView sélectionné à ajouter
	 */
	public void notifyVertexAddToSelection(VertexView selectedVertex) {
		this.window.getCurrentTab().selectVertex(selectedVertex);
	}

	/**
	 * Méthode appellée lorsqu'un item d'un menu contextuel a été sollicité. Elle associé à son nom une action.
	 * @param text le nom de l'item source
	 */
	public void notifyContextMenuItemActivated(String text) {
		switch(text){
			case "Edit":
					this.window.getCurrentTab().modifySelectedVertex();
				break;

			case "Delete":
					for(VertexView v : this.window.getCurrentTab().getSelectedVertexes()){
						this.getGraph(this.window.getCurrentTabIndex()).getVertexes().remove(v.getVertex());
						this.window.getCurrentTab().getVertexes().remove(v);
					}
				break;

			default:
				break;
		}
	}
}