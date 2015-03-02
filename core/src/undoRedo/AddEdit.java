package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * @author Benjamin Dos Santos
 * Classe AddEdit, à chaque création d'objets au sein d'un Graph, une instance de AddEdit est créée et associée à l'undoManager.
 */
public class AddEdit extends AbstractUndoableEdit{


    private ArrayList <GraphElement> elements;

    private Graph graph;

    public AddEdit(Graph model, ArrayList <GraphElement> elements) {
        graph=model;

        this.elements = elements;

    }

    /**
     * Action correspondante à l'annulation de cet AddEdit
     * @throws CannotUndoException
     */
    public void undo() throws CannotUndoException {
        for(GraphElement e : elements) {
            graph.removeGraphElement(e);
        }
    }

    /**
     * Action correspondante au rétablissement de cet AddEdit précédemment annulé
     * @throws CannotRedoException
     */
    public void redo() throws CannotRedoException {
        for(GraphElement e : elements) {
            graph.createGraphElement(e);
        }

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