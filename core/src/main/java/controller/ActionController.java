package controller;

import data.Graph;
import view.theme.StateButton;

import javax.swing.*;
import java.awt.*;

/**
 * Classe ActionController, permettant de gérer tous les appels à des ActionCommand dans le programme ({@link javax.swing.JMenuItem}
 * par exemple).
 */
public class ActionController {

    private static Controller controller;

    /**
     * Setter du controller
     * @param controller
     */
    public static void setController(Controller controller) {
        ActionController.controller = controller;
    }

    /**
     * Méthode différenciant le bouton appellant l'actionController. Si c'est un bouton de changement
     * d'état, on change l'état, sinon on fait un traitement simple.
     * @param button Le button en provenance de l'action
     * @param position La position de la souris à l'instant T (si c'est un menu contextuel par exemple)
     * @param tabTitle Le nom du {@link view.editor.Tab} dans lequel est appelé l'action
     */
    public static void handleButton(AbstractButton button, Point position, String tabTitle) {
        if (button.getClass() == StateButton.class) { // bouton de contrôle de l'Etat du Controller
            ActionController.controller.changeState(button.getActionCommand());
        } else { // tous les autres boutons du programme
            handleMenuButton(button.getActionCommand(), position, ActionController.controller.getWindow().getTabIndexOf(tabTitle));
        }
    }

    /**
     * Méthode de traitement de la demande d'une action.
     * @param type Le nom de l'action à traiter
     * @param position La position de la souris à l'instant T de l'appel de la fonction
     * @param tabIndex Le nom du {@link view.editor.Tab} courant
     */
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

            case "Algorithms":
                ActionController.callAlgorithmsToolBox();
                break;

            case "changeStyle":
                ActionController.changeStyle();
                break;

            case "generate":
                ActionController.generateGraphElements();
                break;

            default:
                break;
        }
    }

    /**
     * Generation d'éléments
     */
    private static void generateGraphElements() {
        ActionController.controller.generateGraphElements();
    }

    /**
     * Changement du style de la fenêtre
     */
    private static void changeStyle() {
        ActionController.controller.changeLook();
    }

    /**
     * Appel de la boite de sélection d'algorithmes
     */
    private static void callAlgorithmsToolBox() {
        ActionController.controller.getWindow().callAlgoToolBox();
    }

    /**
     * Sélection de l'ensemble des éléments d'un Graphe
     */
    public static void selectAll() {
        ActionController.controller.getWindow().getCurrentSheet().selectAll();
    }

    /**
     * Ajout d'un nouveau graphe
     */
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

    /**
     * Fermeture d'un {@link view.editor.Tab}
     */
    public static void closeTab() {
        ActionController.controller.closeWithOptions(ActionController.controller.getWindow().getCurrentTabIndex());
    }

    /**
     * Fermeture d'un {@link view.editor.Tab} ayant un certain index
     * @param tabIndex
     */
    public static void closeTab(int tabIndex) {
        ActionController.controller.closeWithOptions(tabIndex);
    }

    /**
     * Edition des propriétés des éléments sélectionés
     */
    private static void edit() {
        ActionController.controller.getWindow().getCurrentSheet().modifySelectedElements();
    }

    /**
     * Edition des propriétés de la {@link view.editor.Sheet}
     */
    private static void properties() {
        ActionController.controller.getWindow().getCurrentSheet().modifyProperties();
    }

    /**
     * Supression d'éléments
     */
    public static void delete() {
        ActionController.controller.deleteElements();
    }

    /**
     * Copie d'éléments
     */
    public static void copy() {
        ActionController.controller.copyElements();
    }

    public static void cut() {
        // To-Do
    }

    /**
     * Recopie d'éléments
     * @param position Position où les recopier
     */
    public static void paste(Point position) {
        ActionController.controller.pasteElements(position);
    }

    /**
     * Annuler une action
     */
    public static void undo() {
        if (ActionController.controller.getWindow().getTabCount() != 0) {
            ActionController.controller.getWindow().getCurrentTab().getUndoRedo().undo();
        }
    }

    /**
     * Refaire une action
     */
    public static void redo() {
        if (ActionController.controller.getWindow().getTabCount() != 0) {
            ActionController.controller.getWindow().getCurrentTab().getUndoRedo().redo();
        }
    }

    /**
     * Sauvegarder au format .graphml
     */
    public static void saveToGraphml() {
        ActionController.controller.saveFile(new String[]{"GraphML files (*.graphml)"}, new String[]{"graphml"});
    }

    /**
     * Sauvegarder au format .dot
     */
    public static void saveToGraphviz() {
        ActionController.controller.saveFile(new String[]{"DOT files (*.dot)"}, new String[]{"dot"});
    }

    /**
     * Ouvrir un fichier .graphml
     */
    public static void openFromGraphml() {
        ActionController.controller.openFile(new String[]{"GraphML files (*.graphml)"}, new String[]{"graphml"});
    }

    /**
     * Ouvrir un fichier .dot
     */
    public static void openFromGraphviz() {
        ActionController.controller.openFile(new String[]{"DOT files (*.dot)"}, new String[]{"dot"});
    }

    /**
     * Ouvrir un graphe plus généralement
     */
    public static void openGraph() {
        ActionController.controller.openFile(new String[]{"GraphML files (*.graphml)", "DOT files (*.dot)"}, new String[]{"graphml", "dot"});
    }

    /**
     * Sauvegarder un graphe plus généralement
     */
    public static void saveGraph() {
        ActionController.controller.save(new String[]{"GraphML files (*.graphml)", "DOT files (*.dot)"}, new String[]{"graphml", "dot"});
    }

    /**
     * Application d'un algorithme
     * @param algorithmProperties Object[]
     */
    public static void applyAlgorithm(Object[] algorithmProperties) {
        ActionController.controller.applyAlgorithm(algorithmProperties, new Point(0,0), ActionController.controller.getWindow().getCurrentSheetViewPort().getExtentSize());
    }

    /**
     * Rafraichissement de la gestion de l'undo/redo
     * @param undoEnable L'état de l'undo
     * @param redoEnable L'état du redo
     */
    public static void refreshUndoRedo(boolean undoEnable, boolean redoEnable) {
        ActionController.controller.getWindow().setUndoEnable(undoEnable);
        ActionController.controller.getWindow().setRedoEnable(redoEnable);
    }
}
