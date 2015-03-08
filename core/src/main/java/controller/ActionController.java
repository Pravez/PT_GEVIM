package controller;

import data.Graph;
import view.StateButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aledufrenne on 08/03/2015.
 */
public class ActionController {

    private static Controller controller;

    public static void setController(Controller controller) {
        ActionController.controller = controller;
    }

    public static void handleButton(AbstractButton button, Point position) {
        if (button.getClass() == StateButton.class) { // bouton de contrôle de l'Etat du Controller
            ActionController.controller.changeState(button.getActionCommand());
        } else { // tous les autres boutons du programme
            handleMenuButton(button.getActionCommand(), position);
        }
    }

    private static void handleMenuButton(String type, Point position) {
        switch (type) {
            case "New":
                ActionController.newTab();
                break;

            case "Edit":
                ActionController.edit();
                break;

            case "Delete":
                ActionController.delete();
                break;

            case "Properties":
                ActionController.properties();
                break;

            case "Close":
                ActionController.closeTab();
                break;

            case "Open":
                // TO-DO statement
                break;

            case "Copy":
                ActionController.copy();
                break;

            case "Paste":
                ActionController.paste(position);
                break;

            case "Undo":
                ActionController.undo();
                break;

            case "Redo":
                ActionController.redo();
                break;

            case "to GraphML...":
                ActionController.saveToGraphml();
                break;

            case "to GraphViz...":
                ActionController.saveToGraphviz();
                break;

            case "from GraphML...":
                ActionController.openFromGraphml();
                break;

            case "from GraphViz...":
                ActionController.openFromGraphviz();
                break;

            case "Random Positioning":
                ActionController.applyAlgorithm("random");
                break;

            case "Circular Positioning":
                ActionController.applyAlgorithm("circular");
                break;

            case "Vertex Size Coloring":
                ActionController.applyAlgorithm("color");
                break;

            case "Vertex Number of Edges Coloring ":
                ActionController.applyAlgorithm("number");
                break;

            default:
                break;
        }
    }

    public static void newTab() {
        String title = "Tab " + ActionController.controller.getWindow().getTabCount();
        title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);
        if ((title != null) && (!title.equals(""))) {
            Graph graph = ActionController.controller.addNewGraph();
            ActionController.controller.getWindow().addNewTab(graph, title);
        } else if ((title != null) && (title.equals(""))) {
            //Invitation à nommer le nouveau graphe (onglet) créé.
            JOptionPane.showMessageDialog(ActionController.controller.getWindow(), "Nom de graphe attendu.");
            newTab();
        }
    }

    public static void closeTab() {
        ActionController.controller.closeWithOptions();
    }

    private static void edit() {
        ActionController.controller.getWindow().getCurrentSheet().modifySelectedElement();
    }

    private static void properties() {
        ActionController.controller.getWindow().getCurrentSheet().modifyProperties();
    }

    private static void delete() {
        ActionController.controller.deleteElements();
    }

    public static void copy() {
        ActionController.controller.copyElements();
    }

    public static void cut() {
        // To-Do
    }

    public static void paste(Point position) {
        ActionController.controller.pasteElements(position);
    }

    public static void undo() {
        if (ActionController.controller.getWindow().getTabCount() != 0) {
            ActionController.controller.getWindow().getCurrentTab().getUndoRedo().undo();
        }
    }

    public static void redo() {
        if (ActionController.controller.getWindow().getTabCount() != 0) {
            ActionController.controller.getWindow().getCurrentTab().getUndoRedo().redo();
        }
    }

    public static void saveToGraphml() {
        ActionController.controller.saveFile();
    }

    public static void saveToGraphviz() {
        //ActionController.controller.saveFile();
    }

    public static void openFromGraphml() {
        ActionController.controller.openFile();
    }

    public static void openFromGraphviz() {
        //ActionController.controller.openFile();
    }

    public static void applyAlgorithm(String name) {
        ActionController.controller.applyAlgorithm(name);
    }
}
