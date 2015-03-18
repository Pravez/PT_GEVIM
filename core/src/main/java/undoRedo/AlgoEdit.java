package undoRedo;

import data.Edge;
import data.Graph;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class AlgoEdit extends AbstractUndoableEdit {

    private EdgeEdit edgeEdits;
    private VertexEdit vertexEdits;

    private Graph graph;
    public AlgoEdit(Graph model, EdgeEdit edges,VertexEdit vertices) {
        graph=model;
        edgeEdits=edges;
        vertexEdits=vertices;
    }


    public void undo() throws CannotUndoException {
        vertexEdits.undo();
        edgeEdits.undo();

    }

    public void redo() throws CannotUndoException {

        vertexEdits.redo();
        edgeEdits.redo();

    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Algo"; }



}
