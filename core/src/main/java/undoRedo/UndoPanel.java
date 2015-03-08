package undoRedo;

import controller.ActionController;
import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;


/**
 * @author Benjamin Dos Santos
 * Classe UndoPanel, système de gestion de l'undoRedo, il inclut l'interface (boutons) ainsi que le Graph dont il est en charge
 */
public class UndoPanel extends JPanel {

    private Graph graph;

    UndoManager undoManager_;         // history list
    UndoableEditSupport undoSupport_; // event support

    /**
     * Constructeur par défaut de la classe UndoPanel
     */
    public UndoPanel( ) {
        //Initialisation du module UndoRedo
        undoManager_= new UndoManager();
        undoSupport_ = new UndoableEditSupport();
        undoSupport_.addUndoableEditListener(new UndoAdapter());
    }


    public UndoPanel(Graph graph ) {
        //Initialisation du module UndoRedo
        undoManager_= new UndoManager();
        undoSupport_ = new UndoableEditSupport();
        undoSupport_.addUndoableEditListener(new UndoAdapter());
        this.graph=graph;
    }

    /**
     * Setter de graph
     * @param graph le nouveau graph associé
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }


    /**
     * Action appelée après chaque action d'undo/redo. Elle met notamment à jour la disponibilité des boutons
     */
    public void refreshUndoRedo() {
        ActionController.refreshUndoRedo(undoManager_.canUndo(), undoManager_.canRedo());
    }


    /**
     * Enregistre au sein du module UndoRedo la création d'un ou plusieurs GraphElements au sein de graph
     * @param newElements les nouveaux objets
     */
    public void registerAddEdit(ArrayList<GraphElement> newElements) {

        UndoableEdit edit = new AddEdit(graph, newElements);

        undoSupport_.postEdit(edit);
    }



    /**
     * Enregistre au sein du module UndoRedo la suppression d'un ou pluqieurs GraphElements au sein de graph
     * @param suppElements GraphElements venant d'être supprimés
     */
    public void registerSuppEdit(ArrayList<GraphElement> suppElements) {


        ArrayList <GraphElement> vertexes= new ArrayList<>();
        ArrayList <GraphElement> collateralEdges = new ArrayList<>();
        for(GraphElement g : suppElements) {

            if(g.isVertex())
            {
                vertexes.add(g);
                Vertex v = (Vertex) g;
                for(Edge e : v.getEdges())
                {
                    if(!collateralEdges.contains(e))
                    collateralEdges.add(e);
                }
            } 
            else {
                if(!collateralEdges.contains(g))
                collateralEdges.add(g);
            }

        }
        vertexes.addAll(collateralEdges);
        UndoableEdit edit = new SuppEdit(graph, vertexes);

        undoSupport_.postEdit(edit);
    }

    public void undo() {
        if (undoManager_.canUndo()) {
            undoManager_.undo();
            refreshUndoRedo();
        }
    }

    public void redo() {
        if (undoManager_.canRedo()) {
            undoManager_.redo();
            refreshUndoRedo();
        }
    }

    /**
     * Listener mettant à jour l'undoManager après chaque undo ou redo
     */
    private class UndoAdapter implements UndoableEditListener {
        public void undoableEditHappened (UndoableEditEvent evt) {
            UndoableEdit edit = evt.getEdit();
            undoManager_.addEdit( edit );
            refreshUndoRedo();
        }
    }
}









