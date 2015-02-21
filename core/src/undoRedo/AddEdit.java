package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * @author Benjamin Dos Santos
 * Classe AddEdit, à chaque création d'objets au sein d'un Graph, une instance de AddEdit est créé et associée à l'undoManager.
 */
public class AddEdit extends AbstractUndoableEdit{


    private Object element;

    private Graph graph;

    public AddEdit(Graph model, Object element) {
        graph=model;

        this.element = element;

    }

    /**
     * Action correspondante à l'annulation de cet AddEdit
     * @throws CannotUndoException
     */
    public void undo() throws CannotUndoException {
        graph.removeGraphElement((GraphElement) element);

    }

    /**
     * Action correspondante au rétablissement de cet AddEdit précédemment annulé
     * @throws CannotRedoException
     */
    public void redo() throws CannotRedoException {
        graph.createGraphElement((GraphElement) element);

    }

    /**
     * Indique que l'AddEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }

    /**
     * Indique que l'on peut rétablir cet AddEdit
     * @return true
     */
    public boolean canRedo() { return true; }

    //pas encore utilisée, permettrait de générer un historique
    public String getPresentationName() { return "Add"; }

}