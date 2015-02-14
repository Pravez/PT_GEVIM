package controller;

import data.Graph;

import javax.swing.*;

import view.VertexView;
import view.Window;

import java.awt.Point;
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
	 * @param x La position x sur le graphe du vertex
	 * @param y La position y sur le graphe du vertex
	 */
	public void addVertex(Graph g, Point position){
		if (this.window.getCurrentTab2().canAddVertex(position)) {
			g.createVertex(position);
		}
	}

	/**
	 * Méthode qui renvoie le {@link data.Graph} courant à partir de l'onglet courant du JTabbedPane de la window
	 * @return Le graphe courant
	 */
	public Graph getCurrentGraph(){
		if(graphs.size() > 0){
			return getGraph(this.window.getCurrentTab());
		}
		return null;
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
		//controller.graphs.add(new Graph());
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
		this.window.getCurrentTab2().clearSelectedItem();
		this.window.getCurrentTab2().selectVertex(selectedVertext);
	}
	
	/**
	 * Méthode appelée lorsqu'un VerteView sélectionné doit être ajouté à la liste des VertexView sélectionnés
	 * @param selectedVertex le VertexView sélectionné à ajouter
	 */
	public void notifyVertexAddToSelection(VertexView selectedVertex) {
		this.window.getCurrentTab2().selectVertex(selectedVertex);
	}
}