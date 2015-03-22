package controller;

import algorithm.CircularPositioning;
import algorithm.Property;
import algorithm.RandomPositioning;
import algorithm.VertexColoring;
import controller.state.CreationState;
import controller.state.State;
import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;
import threading.GenerationThread;
import view.UIElements.CustomUIManager;
import view.Window;
import view.editor.Tab;
import view.editor.elements.ElementView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * Classe Controller, contrôleur principal de l'application
 */
public class Controller {

    /* La Window du programme */
    private Window                  window;
    /* L'Etat du Controller */
    private State                   state;
    /* La liste des Graphs ouverts */
    private ArrayList<Graph>        graphs;
    /* Les GraphElement copiés pour être ajoutés à un Graph */
    private ArrayList<GraphElement> copiedElements;

    public Controller() {
        this.state          = new CreationState(this);
        this.graphs         = new ArrayList<Graph>();
        this.copiedElements = new ArrayList<GraphElement>();
    }

    /**
     * Configure la Window du controller (associe la vue au controleur)
     *
     * @param window la Window qui doit être associée
     */
    public void setWindow(Window window) {
        this.window = window;
        this.window.setState(this.state);
    }

    /**
     * Getter de la Window du programme
     * @return la Window
     */
    public Window getWindow() {
        return this.window;
    }

    /**
     * Ajoute un nouveau {@link data.Vertex} au {@link data.Graph} en question
     *
     * @param g        Le graphe auquel le vertex doit être ajouté
     * @param color    la couleur du Vertex à ajouter
     * @param position la position du Vertex à ajouter
     * @param size     la taille du Vertex à ajouter
     * @param shape    la forme du Vertex à ajouter
     */
    public void addVertex(Graph g, Color color, Point position, int size, Vertex.Shape shape) {
        if (this.window.getCurrentSheet().canAddVertex(position)) {
            ArrayList<GraphElement> tmp =  new ArrayList<>();
            tmp.add(g.createVertex(color, position, size, shape));
            window.getCurrentTab().getUndoRedo().registerAddEdit(tmp);
        }
    }

    /**
     * Méthode permettant d'ajouter une Edge au Graph courant
     *
     * @param src le Vertex de départ de l'Edge
     * @param dst le Vertex de destination de l'Edge
     */
    public void addEdge(Vertex src, Vertex dst) {
        boolean ok = true;
        ArrayList<Edge> src_edges = src.getEdges();
        for (Edge e : dst.getEdges()) { // on teste si un Edge entre ces deux Vertex existe déjà
            if (src_edges.contains(e)) {
                ok = false;
                break;
            }
        }
        if (ok) {
            ArrayList<GraphElement> tmp = new ArrayList<>();
            tmp.add(this.window.getCurrentTab().getGraph().createEdge(this.window.getCurrentSheet().getDefaultEdgesColor(), src, dst, this.window.getCurrentSheet().getDefaultEdgesThickness()));
            window.getCurrentTab().getUndoRedo().registerAddEdit(tmp);
        }
    }

    /**
     * Getter de l'état du Controller, de son mode : SELECTION, CREATION, ZOOM_IN, ZOOM_OUT, ...
     *
     * @return l'état du Controller
     */
    public State getState() {
        return this.state;
    }

    /**
     * Renvoie le {@link data.Graph} situé à l'indice numGraph
     *
     * @param numGraph Numéro du graphe
     * @return Le graphe situé à l'indice
     */
    public Graph getGraph(int numGraph) {
        return graphs.get(numGraph);
    }

    /**
     * Crée un nouveau {@link data.Graph}, l'ajoute à l'{@link java.util.ArrayList} de graphes et le renvoie
     *
     * @return Le graphe nouvellement crée
     */
    public Graph addNewGraph() {
        Graph graph = new Graph();
        graphs.add(graph);
        return graph;
    }

    public Graph addGraph(Graph g){
        graphs.add(g);
        return g;
    }

    /**
     * Méthode pour récupérer le zoom (scale) de l'onglet ouvert
     * @return la valeur du zoom
     */
    public double getCurrentTabScale() {
        return this.window.getCurrentSheet().getScale();
    }

    /**
     * Méthode appelée lorsqu'un ElementView est sélectionné
     * - s'il n'est pas sélectionné, il est ajouté à la liste des ElementView sélectionnés
     * - s'il est déjà sélectionné, il est retiré de la liste des ElementView sélectionnés
     *
     * @param selectedElement le ElementView sélectionné à ajouter
     */
    public void notifyHandleElementSelected(ElementView selectedElement) {
        if (this.window.getCurrentSheet().getSelectedElements().contains(selectedElement)) {
            this.window.getCurrentSheet().unselectElement(selectedElement);
        } else {
            this.window.getCurrentSheet().selectElement(selectedElement);
        }
    }

    /**
     * Méthode appelée lorsque l'on gère une sélection :
     * - si l'élément n'est pas dans la sélection, on vide les ElementView sélectionné et on met le nouveau ElementView
     * - sinon on ne fait rien
     *
     * @param selectedElement l'ElementView sélectionné
     */
    public void notifyHandleElement(ElementView selectedElement) {
        if (!this.window.getCurrentSheet().getSelectedElements().contains(selectedElement)) {
            this.window.getCurrentSheet().clearSelectedElements();
            this.window.getCurrentSheet().selectElement(selectedElement);
        }
    }

    /**
     * Méthode appelée pour vider la liste des IElementView sélectionnés
     */
    public void notifyClearSelection() {
        this.window.getCurrentSheet().clearSelectedElements();
    }

    /**
     * Méthode appelée pour demander de rafficher le Tab en cours
     */
    public void notifyRepaintTab() {
        this.window.getCurrentTab().repaint(); // mettre la sheet à repaint ?
    }

    /**
     * Méthode appelée lorsque l'on change d'onglet, donc de Tab
     */
    public void notifyTabChanged() {
        if (this.window.getTabCount() > 0) {
            this.window.getCurrentTab().getUndoRedo().refreshUndoRedo(); // on met les boutons d'undo & redo à jour
        } else {
            ActionController.refreshUndoRedo(false, false);
        }
    }

    /**
     * Méthode permettant de supprimer les GraphElement provenants des ElementView sélectionnés
     */
    public void deleteElements() {
        ArrayList<GraphElement> suppSelectedElements = new ArrayList<>();
        for (ElementView e : this.window.getCurrentSheet().getSelectedElements()) {

            this.getGraph(this.window.getCurrentTabIndex()).removeGraphElement(e.getGraphElement());
            suppSelectedElements.add(e.getGraphElement());
        }
        window.getCurrentTab().getUndoRedo().registerSuppEdit(suppSelectedElements);
        this.window.getCurrentSheet().clearSelectedElements();
    }

    /**
     * Méthode permettant de créer une copie des GraphElement provenants des ElementView sélectionnés
     */
    public void copyElements() {
        if (!this.window.getCurrentSheet().getSelectedElements().isEmpty()) {
            copiedElements.clear();
            // on récupère les GraphElement sélectionnés dans le Tab
            for (ElementView elementView : this.window.getCurrentSheet().getSelectedElements()) {
                copiedElements.add(elementView.getGraphElement());
            }
            // on crée une copie de ces GraphElements
            copiedElements = Graph.copyGraphElements(this.copiedElements);
        }
    }

    /**
     * Méthode permettant de "coller" les GraphElement copiés depuis la sélection
     *
     * @param position la position de la souris pour positionner les GraphElement copiés
     */
    public void pasteElements(Point position) {
        if (!this.copiedElements.isEmpty()) {
            ArrayList<GraphElement> newElements;
            if (position != null) {
                newElements = Graph.copyGraphElements(this.copiedElements, position);

                this.graphs.get(this.window.getCurrentTabIndex()).addGraphElements(newElements);
            } else {
                newElements = Graph.copyGraphElements(this.copiedElements, Graph.findBestCopyPoint(this.copiedElements));

                this.graphs.get(this.window.getCurrentTabIndex()).addGraphElements(newElements);
            }
            window.getCurrentTab().getUndoRedo().registerAddEdit(newElements);
        }
    }

    /**
     * Méthode appelée pour changer l'état du Controller
     *
     * @param command le nouvel état du Controller
     */
    public void changeState(String command) {
    	this.state = State.changeState(State.Mode.valueOf(command), this);
        this.window.setState(this.state);

        if (this.window.getTabCount() > 0)
            this.window.getCurrentSheet().clearSelectedElements();
    }

    /**
     * Méthode appellée lors d'un drag de l'utilisateur sur un onglet
     *
     * @param origin   Le point initial d'où a commencé le drag
     * @param position Le point actuel où en est le drag
     */
    public void notifyDragging(Point origin, Point position) {
        this.window.getCurrentSheet().launchSelectionZone(origin, position);
    }
    
    /**
     * Méthode appellée lors d'un drag de l'utilisateur sur un onglet avec la touche Ctrl enfoncée
     *
     * @param origin   Le point initial d'où a commencé le drag
     * @param position Le point actuel où en est le drag
     */
    public void notifyAddToDragging(Point origin, Point position) {
        this.window.getCurrentSheet().addToSelectionZone(origin, position);
    }

    /**
     * Méthode appellée au début d'un drag d'un vertex vers un autre, pour dessiner une "pseudo-edge" temporaire qui n'est qu'une
     * succession de points sans consistance.
     *
     * @param origin   Le point d'origine de l'Edge temporaire
     * @param position Le point de destination de l'Edge.
     */
    public void notifyDraggingEdge(Point origin, Point position) {
        this.window.getCurrentSheet().launchTemporarilyEdge(origin, position);
    }

    /**
     * Méthode utilisée pour envoyer une information signalant la fin du drag sur une {@link data.Edge} temporaire
     */
    public void notifyEndDraggingEdge() {
        this.window.getCurrentSheet().endTemporarilyEdge();
    }
    
    public void notifyMousePressedWithoutControlDown() {
    	this.window.getCurrentSheet().clearCurrentSelectedElements();
    }

    /**
     * Méthode notifiant la fin du dragging de l'utilisateur
     */
    public void notifyEndDragging() {
        this.window.getCurrentSheet().handleEndSelectionZone();
    }

    /**
     * Méthode permettant d'initialiser le déplacement des ElementView de la Sheet, suivant un certain vecteur de déplacement
     *
     * @param vector le vecteur selon lequel les ElementView doivent être déplacés
     */
    public void notifyInitMoveSelectedElements(Point vector) {
        this.window.getCurrentSheet().initMovingSelectedElements(vector);
    }

    /**
     * Méthode faisant appel au déplacement des ElementView de la Sheet, suivant un certain vecteur de déplacement
     *
     * @param vector le vecteur selon lequel les ElementView doivent être déplacés
     */
    public void notifyMoveSelectedElements(Point vector) {
        this.window.getCurrentSheet().moveSelectedElements(vector);
    }

    /**
     * Méthode faisant appel à la fin de déplacement des ElementView de la Sheet
     */
    public void notifyEndMoveSelectedElements() {
        this.window.getCurrentSheet().endMovingSelectedElements();
    }

    /**
     * Méthode permettant de fermer un onglet s'il y en a d'ouverts, soit la fenetre principal s'il n'y en a pas.
     *
     * @param tabIndex l'index du Tab à fermer
     */
    public void closeWithOptions(int tabIndex) {
        //Attention, la fermeture ne libère probablement pas toute la mémoire ...
        if (this.window.getTabCount() > 0) {
            if(this.window.getCurrentSheet().getFile() == null) {
                if (JOptionPane.showConfirmDialog(this.window, "Graphe non sauvegardé, souhaitez vous fermer ce graphe ?", "Fermer le graphe", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    this.graphs.remove(this.graphs.get(tabIndex));
                    this.window.getTabs().removeTabAt(tabIndex);
                    if (this.window.getTabCount() == 0) {
                        this.window.showStartPanel();
                    }
                }
            } else {
                this.graphs.remove(this.graphs.get(tabIndex));
                this.window.getTabs().removeTabAt(tabIndex);
                if (this.window.getTabCount() == 0) {
                    this.window.showStartPanel();
                }
            }
        } else {
            if (JOptionPane.showConfirmDialog(this.window, "Souhaitez vous vraiment quitter ?", "Fermer le programme", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                this.graphs.clear();
                this.window.getTabs().removeAll();
                this.window.dispose();
            }
        }
    }

    /**
     * Methode pour choisir un fichier dans une arborescence à partir du répertoire principal de l'utilisateur en cours. Elle
     * possède des filtres sur les types de fichiers.
     * @param extensions La liste des extensions devant apparaitre à la sélection
     * @param descriptions La liste des descriptions associées aux extensions
     * @return Le {@link java.io.File} sélectionné
     */
    private File chooseFile(String[] extensions, String descriptions[]) {

        JFileChooser dialogue = new JFileChooser(new File("~/"));

        if(this.window.getTabCount()>0) {
            dialogue.setSelectedFile(new File(this.graphs.get(this.window.getCurrentTabIndex()).getName()));
        }

        if(extensions.length == 1){
            dialogue.setFileFilter(new FileNameExtensionFilter(extensions[0], descriptions[0]));
        }else {
            for (int i = 0; i < extensions.length; i++) {
                if (i == 0) {
                    dialogue.setFileFilter(new FileNameExtensionFilter(extensions[i], descriptions[i]));
                } else {
                    dialogue.addChoosableFileFilter(new FileNameExtensionFilter(extensions[i], descriptions[i]));
                }
            }
        }

        File file = null;

        if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = dialogue.getSelectedFile();
            //Si le fichier rentré (entrée du nom à la main) ne dispose pas d'extension, alors on lui en rajoute une.
            if(!file.getName().contains(".")) {
                String newFile = file.getAbsolutePath() + "." + ((FileNameExtensionFilter)dialogue.getFileFilter()).getExtensions()[0];
                file = new File(newFile);
            }

            //Si le fichier ne possède pas la bonne extension
            if (!file.getName().endsWith(".graphml") && !file.getName().endsWith(".dot")) {
                JOptionPane.showMessageDialog(null, "Impossible d'utiliser ce format", "Erreur", JOptionPane.ERROR_MESSAGE);
                file = null;
            }
        }
        return file;
    }

    /**
     * Méthode permettant l'appel d'un {@link algorithm.IAlgorithm} à partir de son string le caractérisant
     * @param algorithmProperties Object[] contenant le nom de l'algorithme a appliquer, les couleurs min et max, la propriété sur laquelle l'appliquer.
     */
    public void applyAlgorithm(Object[] algorithmProperties, Point initialPosition, Dimension application){
        switch((String) algorithmProperties[0]) {
            case "Positionnement Aléatoire":
                new RandomPositioning(initialPosition, application).run(window.getCurrentTab().getGraph());
                //remplacer window.getCurrentSheetViewPort().getExtentSize()) par window.getCurrentSheetViewPort().getViewSize() pour l'application de l'algorithme
                break;
            case "Positionnement Circulaire":
                new CircularPositioning(initialPosition, application).run(window.getCurrentTab().getGraph());
                break;
            case "Coloration des Sommets":
                new VertexColoring().run(window.getCurrentTab().getGraph(), (Property)algorithmProperties[3], (Color)algorithmProperties[1], (Color)algorithmProperties[2]);
                break;
         }
    }

    /**
     * Méthode d'ouverture d'un fichier et de lecture de ce dernier. Elle utilise de {@link files.gml.GmlFileManager}
     * ou le {@link files.dot.DotFileManager}
     * @param extensions La liste des extensions devant apparaitre à la sélection
     * @param descriptions La liste des descriptions associées aux extensions
     */
    public void openFile(String [] extensions, String[] descriptions){
        try {
            File file = this.chooseFile(extensions, descriptions);

            if (file != null) {

                for (Tab t : this.window.getTabsArray()) {
                    if(Objects.equals((t.getSheet().getFile()), file.getAbsolutePath())){
                        throw new AlreadyBoundException();
                    }
                }

                if (file.getName().contains(".graphml")) {
                    this.window.openGML(file);
                } else if (file.getName().contains(".dot")) {
                    this.window.openDOT(file);
                }
                this.window.getCurrentSheet().setFile(file.getAbsolutePath());
            }
        } catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this.window, "Un problème est survenu lors de la lecture :\n" + iae.getLocalizedMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch(AlreadyBoundException abe){
            JOptionPane.showMessageDialog(this.window, "Le graphe est déjà ouvert !", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.window, "Une erreur inattendue s'est produite : " + e.getLocalizedMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode de sauvegarde d'un graphe avec écriture dans un fichier, utilisant le {@link files.gml.GmlFileManager} ou
     * le {@link files.dot.DotFileManager}
     * @param extensions La liste des extensions devant apparaitre à la sélection
     * @param descriptions La liste des descriptions associées aux extensions
     */
    public void saveFile(String[] extensions, String[] descriptions){
        try {
            File file = this.chooseFile(extensions, descriptions);

            if(file != null) {
                if (file.getName().contains(".graphml")) {
                    this.window.getCurrentSheet().saveToGML(file);
                } else if (file.getName().contains(".dot")) {
                    this.window.getCurrentSheet().saveToVIZ(file);
                }
                this.window.getCurrentSheet().setFile(file.getAbsolutePath());
            }
        }catch(ArrayIndexOutOfBoundsException aioobe){
            JOptionPane.showMessageDialog(null, "Rien à sauvegarder...", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode permettant de gérer la sauvegarde d'un graphe en cours. On lui envoie des extensions à filtrer
     * @param extensions Liste des extensions disponibles à la sauvegarde
     * @param descriptions Descriptions associées aux extensions
     */
    public void save(String[] extensions, String[] descriptions) {
        try{
            File file;
            if(this.window.getTabCount() > 0 && this.window.getCurrentSheet().getFile() != null ){
                file = new File(this.window.getCurrentSheet().getFile());
            }else{
                file = this.chooseFile(extensions, descriptions);
            }
            if(file != null) {
                if (file.getName().contains(".graphml")) {
                    this.window.getCurrentSheet().saveToGML(file);
                } else if (file.getName().contains(".dot")) {
                    this.window.getCurrentSheet().saveToVIZ(file);
                }
                this.window.getCurrentSheet().setFile(file.getAbsolutePath());
            }
        }catch(ArrayIndexOutOfBoundsException aioobe){
            JOptionPane.showMessageDialog(null, "Rien à sauvegarder...", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode permettant de charger un {@link javax.swing.JOptionPane} pour demander à l'utilisateur le thème
     * qu'il souhaite avoir sur sa fenêtre
     */
    public void changeLook() {
        String styles[] = {"Darcula", "Lightula"};
        String s = (String)JOptionPane.showInputDialog(this.window, "Choisir le theme", "Choisissez le theme principal :", JOptionPane.QUESTION_MESSAGE,null, styles, styles[0]);

        try {
            if (s != null) {
                if (s.equals("Darcula")) {
                    CustomUIManager.setDarkTheme();
                } else {
                    CustomUIManager.setLightTheme();
                }

                this.window.repaint();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode de génération d'éléments dans un graphe. On prend le graphe courant et on lui génère X vertices.
     */
    public void generateGraphElements() {

        if(this.window.getTabCount() < 1 ) {
            JOptionPane.showMessageDialog(null, "Vous devez d'abord ouvrir un graphe.", "Erreur", JOptionPane.ERROR_MESSAGE);

        }else{
            String result = (String) JOptionPane.showInputDialog(this.window, "Entrez le nombre de noeuds que vous souhaitez générer : ", "Génération d'éléments", JOptionPane.QUESTION_MESSAGE, null, null, "50");

            try {
                if (result != null) {
                    int value = Integer.parseInt(result);

                    //On commence la génération
                    GenerationThread generationThread = new GenerationThread(this, Integer.parseInt(result));

                    this.getGraph(this.window.getCurrentTabIndex()).addGraphElements(generationThread.getElements());
                    this.window.getCurrentTab().getUndoRedo().registerAddEdit(generationThread.getElements());
                }

                //Si l'utilisateur ne rentre pas un entier

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Merci de rentrer une valeur entiere.", "Erreur", JOptionPane.ERROR_MESSAGE);
                generateGraphElements();
            }
        }

    }

    /**
     * Main du logiciel de visualisation de graphes
     *
     * @param args arguments du main
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(5, 0, 0, 0));
            UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
            UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);
            CustomUIManager.setLightTheme();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        Controller controller = new Controller();
        ActionController.setController(controller);
        Window window = new Window((int)dimension.getWidth()-100, (int)dimension.getHeight()-100, controller);
        controller.setWindow(window);

    }
}

