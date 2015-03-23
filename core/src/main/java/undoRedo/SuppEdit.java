package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * @author Benjamin Dos Santos
 * Classe SuppEdit, à chaque suppression d'objets au sein d'un Graph, une instance de SuppEdit est créée et associée à l'undoManager.
 */
public class SuppEdit extends AbstractUndoableEdit {

    private ArrayList<GraphElement> elements;

    private Graph graph;

    public SuppEdit(Graph model, ArrayList <GraphElement> elements) {
        graph=model;
        this.elements = elements;
    }

    /**
     * Action correspondante à l'annulation de cet SuppEdit. On recrée les GraphElements supprimés
     * @throws CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {
        for(GraphElement e : elements) {
            graph.createGraphElement(e);
        }
        graph.setChanged();

    }

    /**
     * Action correspondante au rétablissement de cet SuppEdit précédemment annulé. On supprime de nouveaux les GraphElements
     * @throws CannotRedoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotRedoException {
        for(GraphElement e : elements) {
            graph.removeGraphElement(e);
        }
    graph.setChanged();
    }

    /**
     * Indique que le SuppEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }

    /**
     * Indique que l'on peut rétablir cet SuppEdit
     * @return true
     */
    public boolean canRedo() { return true; }

}
