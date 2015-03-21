package undoRedo;

import data.Graph;
import undoRedo.snap.SnapEdge;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

/**
 * Created by bendossantos on 18/03/15.
 */
public class EdgeEdit extends AbstractUndoableEdit {


    private SnapEdge edgeBefore;
    private SnapEdge edgeAfter;

    private Graph graph;

    public EdgeEdit(Graph model,SnapEdge before, SnapEdge after) {
        graph=model;
        edgeAfter=after;
        edgeBefore=before;
    }


    public void undo() throws CannotUndoException {


        if (edgeAfter.getColor() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setColor(edgeBefore.getColor());

        if (edgeAfter.getLabel() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setLabel(edgeBefore.getLabel());

        if (edgeAfter.getSize() != -1)
            graph.getEdges().get(edgeBefore.getIndex()).setThickness(edgeBefore.getSize());

        if (edgeAfter.getDestination() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setDestination(edgeBefore.getDestination());

        if (edgeAfter.getSource() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setOrigin(edgeBefore.getSource());

        graph.setChanged();



    }

    public void redo() throws CannotUndoException {


        if (edgeAfter.getColor() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setColor(edgeAfter.getColor());

        if (edgeAfter.getLabel() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setLabel(edgeAfter.getLabel());

        if (edgeAfter.getSize() != -1)
            graph.getEdges().get(edgeBefore.getIndex()).setThickness(edgeAfter.getSize());

        if (edgeAfter.getDestination() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setDestination(edgeAfter.getDestination());

        if (edgeAfter.getSource() != null)
            graph.getEdges().get(edgeBefore.getIndex()).setOrigin(edgeAfter.getSource());

        graph.setChanged();


    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
