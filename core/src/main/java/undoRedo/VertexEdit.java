package undoRedo;

import data.Graph;

import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class VertexEdit {


    private SnapVertex vertexBefore;
    private SnapVertex vertexAfter;

    private Graph graph;

    public VertexEdit(Graph model, SnapVertex before,SnapVertex after) {
        graph=model;
        vertexBefore=after;
        vertexAfter=before;
    }

    public void undo() throws CannotUndoException {

        graph.getVertexes().get(vertexBefore.getIndex()).setColor(vertexBefore.getColor());
        graph.getVertexes().get(vertexBefore.getIndex()).setLabel(vertexBefore.getLabel());
        graph.getVertexes().get(vertexBefore.getIndex()).setSize(vertexBefore.getSize());
        graph.getVertexes().get(vertexBefore.getIndex()).setShape(vertexBefore.getShape());


    }

    public void redo() throws CannotUndoException {

        graph.getVertexes().get(vertexAfter.getIndex()).setColor(vertexAfter.getColor());
        graph.getVertexes().get(vertexAfter.getIndex()).setLabel(vertexAfter.getLabel());
        graph.getVertexes().get(vertexAfter.getIndex()).setSize(vertexAfter.getSize());
        graph.getVertexes().get(vertexAfter.getIndex()).setShape(vertexAfter.getShape());


    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
