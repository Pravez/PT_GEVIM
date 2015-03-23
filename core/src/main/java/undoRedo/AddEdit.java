package undoRedo;

import data.Graph;
import data.GraphElement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Classe AddEdit, enregistre la création de {@link data.GraphElement} au sein d'un {@link data.Graph}.
 * A chaque création d'objets au sein d'un {@link data.Graph}, une instance de AddEdit est créée.
 */
public class AddEdit extends AbstractUndoableEdit{


    private ArrayList <GraphElement> elements; //ensemble des GraphElements venant d'être créés

    private Graph graph; // le Graph dans lequel les nouveaux GraphElements ont été créés

    /**
     * Constructeur de la class AddEdit
     * @param model le Graph d'où proviennent les GraphElements
     * @param elements l'ensemble des GraphElements venant d'être créés
     */
    public AddEdit(Graph model, ArrayList <GraphElement> elements) {
        graph=model;
        this.elements = elements;
    }

    /**
     * Action correspondante à l'annulation de cet AddEdit
     * @throws CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {
        for(GraphElement e : elements) {
            graph.removeGraphElement(e);
        }
        graph.setChanged();
    }

    /**
     * Action correspondante au rétablissement de cet AddEdit précédemment annulé
     * @throws CannotRedoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotRedoException {
        for(GraphElement e : elements) {
            graph.createGraphElement(e);
        }
        graph.setChanged();

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

}