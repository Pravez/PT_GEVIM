package controller;

import View.Window;
import data.Graph;
import data.Vertex;

import javax.swing.*;

import java.util.ArrayList;


// à voir pour créer des onglets : http://openclassrooms.com/courses/apprenez-a-programmer-en-java/conteneurs-sliders-et-barres-de-progression


/**
 * Created by quelemonnier on 26/01/15.
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
	public void addVertex(Graph g, int x, int y){
		g.createVertex(x,y);
	}

	/**
	 * Méthode qui renvoie le {@link data.Graph} courant à partir de l'onglet courant du JTabbedPane de la window
	 * @return Le graphe courant
	 */
	public Graph getCurrentGraph(){
		if(graphs.size() > 0){
			return getGraph(window.getCurrentTab());
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
		graphs.add(new Graph(this));
		return graphs.get(graphs.size()-1);
	}

    public static void main(String[] args){
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }
    	Controller controller = new Controller();
		//controller.graphs.add(new Graph());
		Window window = new Window(800, 640, controller);
        controller.setWindow(window);
    }

	public void notifyMenuItemActivated(String type) {
		switch (type) {
		case "New":
			//if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment lancer une nouvelle partie ?", "Nouvelle Partie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION)
			this.window.addNewTab();
			break;
			
		case "Close":
			break;
			
		default:
			break;
		}
	}

	public void notifyVertexSelected(Vertex selectedVertext) {
		getCurrentGraph().selectVertex(selectedVertext);
		System.out.println("Selected");
	}
}
