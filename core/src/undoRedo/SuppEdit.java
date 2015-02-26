package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * @author Benjamin Dos Santos
 * Classe SuppEdit, à chaque suppression d'objets au sein d'un Graph, une instance de SuppEdit est créée et associée à l'undoManager.
 */
public class SuppEdit extends AbstractUndoableEdit {

    private GraphElement element;

    private Graph graph;

    public SuppEdit(Graph model, GraphElement element) {
        graph=model;
        this.element = element;
    }

    /**
     * Action correspondante à l'annulation de cet SuppEdit
     * @throws CannotUndoException
     */
    public void undo() throws CannotUndoException {
        graph.createGraphElement(element);

    }

    /**
     * Action correspondante au rétablissement de cet SuppEdit précédemment annulé
     * @throws CannotRedoException
     */
    public void redo() throws CannotRedoException {
        graph.removeGraphElement(element);


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

    //pas encore utilisée, permettrait de générer un historique
    public String getPresentationName() { return "Add"; }

}
