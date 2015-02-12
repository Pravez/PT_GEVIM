package controller;

import View.Window;
import data.Graph;

import javax.swing.*;
import java.util.ArrayList;


// à voir pour créer des onglets : http://openclassrooms.com/courses/apprenez-a-programmer-en-java/conteneurs-sliders-et-barres-de-progression


/**
 * Created by quelemonnier on 26/01/15.
 */
public class Controller {
	
	private Window window;
	private ArrayList<Graph> graphs = new ArrayList<Graph>();
	
	public void setWindow(Window window) {
		this.window = window;
	}

	public void addVertex(Graph g, int x, int y){
		g.createVertex(x,y);
	}

	public Graph getCurrentGraph(){
		return null;
	}

	public Graph getGraph(int numGraph){
		return graphs.get(numGraph);
	}

	public void addNewGraph(){

	}

    public static void main(String[] args){
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }
    	Controller controller = new Controller();
		controller.graphs.add(new Graph());
		Window window = new Window(400, 500, controller);
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
}
