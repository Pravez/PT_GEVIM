package undoRedo;

import data.Graph;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class VertexEdit extends AbstractUndoableEdit {


    private SnapVertex vertexBefore;
    private SnapVertex vertexAfter;

    private Graph graph;

    public VertexEdit(Graph model, SnapVertex before,SnapVertex after) {
        graph=model;
        vertexBefore=before;
        vertexAfter=after;
    }

    public void undo() throws CannotUndoException {

        graph.getVertexes().get(vertexBefore.getIndex()).setColor(vertexBefore.getColor());
        graph.getVertexes().get(vertexBefore.getIndex()).setLabel(vertexBefore.getLabel());
        graph.getVertexes().get(vertexBefore.getIndex()).setSize(vertexBefore.getSize());
        graph.getVertexes().get(vertexBefore.getIndex()).setShape(vertexBefore.getShape());
        graph.getVertexes().get(vertexBefore.getIndex()).setPosition(vertexBefore.getPosition());
        graph.setChanged();


    }

    public void redo() throws CannotUndoException {

        if(vertexAfter.getColor()!=null)
            graph.getVertexes().get(vertexBefore.getIndex()).setColor(vertexAfter.getColor());

        if(vertexAfter.getLabel()!=null)
            graph.getVertexes().get(vertexBefore.getIndex()).setLabel(vertexAfter.getLabel());

        if(vertexAfter.getSize()!=-1)
            graph.getVertexes().get(vertexBefore.getIndex()).setSize(vertexAfter.getSize());

        if(vertexAfter.getShape()!=null)
            graph.getVertexes().get(vertexBefore.getIndex()).setShape(vertexAfter.getShape());

        if(vertexAfter.getPosition()!=null)
        graph.getVertexes().get(vertexBefore.getIndex()).setPosition(vertexAfter.getPosition());

        graph.setChanged();


    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
