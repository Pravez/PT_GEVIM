package controller;

import data.Graph;
import view.theme.StateButton;

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

    public static void handleButton(AbstractButton button, Point position, String tabTitle) {
        if (button.getClass() == StateButton.class) { // bouton de contrôle de l'Etat du Controller
            ActionController.controller.changeState(button.getActionCommand());
        } else { // tous les autres boutons du programme
            handleMenuButton(button.getActionCommand(), position, ActionController.controller.getWindow().getTabIndexOf(tabTitle));
        }
    }

    private static void handleMenuButton(String type, Point position, int tabIndex) {
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
                ActionController.closeTab(tabIndex);
                break;

            case "Open":
                openGraph();
                break;

            case "Save":
                saveGraph();
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

            case "GraphML...":
                ActionController.saveToGraphml();
                break;

            case "GraphViz...":
                ActionController.saveToGraphviz();
                break;

            case "from GraphML...":
                ActionController.openFromGraphml();
                break;

            case "from GraphViz...":
                ActionController.openFromGraphviz();
                break;

          /*  case "Random Positioning":
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
                break;*/
            case "Algorithms":
                ActionController.callAlgorithmsToolBox();
                break;

            case "changeStyle":
                ActionController.changeStyle();

            case "generate":
                ActionController.generateGraphElements();

            default:
                break;
        }
    }

    private static void generateGraphElements() {
        ActionController.controller.generateGraphElements();
    }

    private static void changeStyle() {
        ActionController.controller.changeLook();
    }

    private static void callAlgorithmsToolBox() {
        ActionController.controller.getWindow().callAlgoToolBox();
    }

    public static void selectAll() {
        ActionController.controller.getWindow().getCurrentSheet().selectAll();
    }

    public static void newTab() {
        String title = "Sheet" + ActionController.controller.getWindow().getTabIndex();
        title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);
        if (title != null && !title.equals("")) {
            if (ActionController.controller.getWindow().tabExists(title)) {
                //Invitation à nommer autrement le nouveau graphe (onglet) créé.
                JOptionPane.showMessageDialog(ActionController.controller.getWindow(), "Un Graph portant ce nom est déjà ouvert");
                newTab();
            } else {
                Graph graph = ActionController.controller.addNewGraph();
                graph.setName(title);
                ActionController.controller.getWindow().addNewTab(graph, title);
            }
        } else if (title != null && title.equals("")) {
            //Invitation à nommer le nouveau graphe (onglet) créé.
            JOptionPane.showMessageDialog(ActionController.controller.getWindow(), "Nom de graphe attendu.");
            newTab();
        }
    }

    public static void closeTab() {
        ActionController.controller.closeWithOptions(ActionController.controller.getWindow().getCurrentTabIndex());
    }

    public static void closeTab(int tabIndex) {
        ActionController.controller.closeWithOptions(tabIndex);
    }

    private static void edit() {
        ActionController.controller.getWindow().getCurrentSheet().modifySelectedElements();
    }

    private static void properties() {
        ActionController.controller.getWindow().getCurrentSheet().modifyProperties();
    }

    public static void delete() {
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
        ActionController.controller.saveFile(new String[]{"GraphML files (*.graphml)"}, new String[]{"graphml"});
    }

    public static void saveToGraphviz() {
        ActionController.controller.saveFile(new String[]{"DOT files (*.dot)"}, new String[]{"dot"});
    }

    public static void openFromGraphml() {
        ActionController.controller.openFile(new String[]{"GraphML files (*.graphml)"}, new String[]{"graphml"});
    }

    public static void openFromGraphviz() {
        ActionController.controller.openFile(new String[]{"DOT files (*.dot)"}, new String[]{"dot"});
    }

    public static void openGraph() {
        ActionController.controller.openFile(new String[]{"GraphML files (*.graphml)", "DOT files (*.dot)"}, new String[]{"graphml", "dot"});
    }

    public static void saveGraph() {
        ActionController.controller.save(new String[]{"GraphML files (*.graphml)", "DOT files (*.dot)"}, new String[]{"graphml", "dot"});
    }

    public static void applyAlgorithm(String name) {
        ActionController.controller.applyAlgorithm(name, new Point(0,0), ActionController.controller.getWindow().getCurrentSheetViewPort().getExtentSize());
    }

    public static void refreshUndoRedo(boolean undoEnable, boolean redoEnable) {
        ActionController.controller.getWindow().setUndoEnable(undoEnable);
        ActionController.controller.getWindow().setRedoEnable(redoEnable);
    }
}
