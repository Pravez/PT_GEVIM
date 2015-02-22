package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * @author Benjamin Dos Santos
 * Classe AddEdit, à chaque création d'objets au sein d'un Graph, une instance de AddEdit est créée et associée à l'undoManager.
 */
public class AddEdit extends AbstractUndoableEdit{


    private GraphElement element;

    private Graph graph;

    public AddEdit(Graph model, GraphElement element) {
        graph=model;

        this.element = element;

    }

    /**
     * Action correspondante à l'annulation de cet AddEdit
     * @throws CannotUndoException
     */
    public void undo() throws CannotUndoException {
        graph.removeGraphElement(element);

    }

    /**
     * Action correspondante au rétablissement de cet AddEdit précédemment annulé
     * @throws CannotRedoException
     */
    public void redo() throws CannotRedoException {
        graph.createGraphElement(element);

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