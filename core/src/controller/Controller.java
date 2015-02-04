package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import View.Window;

// à voir pour créer des onglets : http://openclassrooms.com/courses/apprenez-a-programmer-en-java/conteneurs-sliders-et-barres-de-progression


/**
 * Created by quelemonnier on 26/01/15.
 */
public class Controller {
	
	private Window window;
	
	public void setWindow(Window window) {
		this.window = window;
	}
	
    public static void main(String[] args){
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		UIManager.put("TabbedPane.contentAreaColor ",ColorUIResource.RED);
    		UIManager.put("TabbedPane.selected",ColorUIResource.BLUE);
    		UIManager.put("TabbedPane.background",ColorUIResource.YELLOW);
    		UIManager.put("TabbedPane.shadow",ColorUIResource.GREEN);
		} catch (Exception e) { }
    	Controller controller = new Controller();
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
