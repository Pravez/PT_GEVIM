package undoRedo;

import data.Graph;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class EdgeEdit extends AbstractUndoableEdit {


    private ArrayList<SnapEdge> edgesBefore;
    private ArrayList<SnapEdge> edgesAfter;

    private Graph graph;

    public EdgeEdit(Graph model, ArrayList<SnapEdge> before, ArrayList<SnapEdge> after) {
        graph=model;
        edgesAfter=after;
        edgesBefore=before;
    }


    public void undo() throws CannotUndoException {
        
        for(SnapEdge s : edgesBefore) {
            graph.getEdges().get(s.getIndex()).setColor(s.getColor());
            graph.getEdges().get(s.getIndex()).setLabel(s.getLabel());
            graph.getEdges().get(s.getIndex()).setThickness(s.getSize());
            graph.getEdges().get(s.getIndex()).setDestination(graph.getVertexes().get(s.getIndexDestination()));
            graph.getEdges().get(s.getIndex()).setOrigin(graph.getVertexes().get(s.getIndexSource()));

        }

    }

    public void redo() throws CannotUndoException {
/*
        for(SnapEdge s : edgesBefore) {
            if (edgeAfter.getColor() != null)
                graph.getVertexes().get(edgeBefore.getIndex()).setColor(edgeAfter.getColor());

            if (edgeAfter.getLabel() != null)
                graph.getVertexes().get(edgeBefore.getIndex()).setLabel(edgeAfter.getLabel());

            if (edgeAfter.getSize() != -1)
                graph.getVertexes().get(edgeBefore.getIndex()).setSize(edgeAfter.getSize());

            if (edgeAfter.getIndexDestination() != -1)
                graph.getEdges().get(edgeBefore.getIndex()).setDestination(graph.getVertexes().get(edgeAfter.getIndexDestination()));

            if (edgeAfter.getIndexSource() != -1)
                graph.getEdges().get(edgeBefore.getIndex()).setOrigin(graph.getVertexes().get(edgeAfter.getIndexSource()));
        }*/
    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
