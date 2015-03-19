package undoRedo;

import data.Graph;

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



        graph.getEdges().get(edgeBefore.getIndex()).setColor(edgeBefore.getColor());
        graph.getEdges().get(edgeBefore.getIndex()).setLabel(edgeBefore.getLabel());
        graph.getEdges().get(edgeBefore.getIndex()).setThickness(edgeBefore.getSize());
        graph.getEdges().get(edgeBefore.getIndex()).setDestination(edgeBefore.getDestination());
        graph.getEdges().get(edgeBefore.getIndex()).setOrigin(edgeBefore.getSource());
        graph.setChanged();

        /*
        for(SnapEdge s : edgesBefore) {
            graph.getEdges().get(s.getIndex()).setColor(s.getColor());
            graph.getEdges().get(s.getIndex()).setLabel(s.getLabel());
            graph.getEdges().get(s.getIndex()).setThickness(s.getSize());
            graph.getEdges().get(s.getIndex()).setDestination(graph.getVertexes().get(s.getDestination()));
            graph.getEdges().get(s.getIndex()).setOrigin(graph.getVertexes().get(s.getSource()));

        }*/

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


/*
        for(SnapEdge s : edgesBefore) {
            if (edgeAfter.getColor() != null)
                graph.getVertexes().get(edgeBefore.getIndex()).setColor(edgeAfter.getColor());

            if (edgeAfter.getLabel() != null)
                graph.getVertexes().get(edgeBefore.getIndex()).setLabel(edgeAfter.getLabel());

            if (edgeAfter.getSize() != -1)
                graph.getVertexes().get(edgeBefore.getIndex()).setSize(edgeAfter.getSize());

            if (edgeAfter.getDestination() != -1)
                graph.getEdges().get(edgeBefore.getIndex()).setDestination(graph.getVertexes().get(edgeAfter.getDestination()));

            if (edgeAfter.getSource() != -1)
                graph.getEdges().get(edgeBefore.getIndex()).setOrigin(graph.getVertexes().get(edgeAfter.getSource()));
        }*/
    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
