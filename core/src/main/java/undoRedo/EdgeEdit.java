package undoRedo;

import data.Graph;

import javax.swing.undo.CannotUndoException;

/**
 * Created by bendossantos on 18/03/15.
 */
public class EdgeEdit {


    private SnapEdge edgeBefore;
    private SnapEdge edgeAfter;

    private Graph graph;

    public EdgeEdit(Graph model, SnapEdge before,SnapEdge after) {
        graph=model;
        edgeAfter=after;
        edgeBefore=before;
    }


    public void undo() throws CannotUndoException {

        graph.getEdges().get(edgeBefore.getIndex()).setColor(edgeBefore.getColor());
        graph.getEdges().get(edgeBefore.getIndex()).setLabel(edgeBefore.getLabel());
        graph.getEdges().get(edgeBefore.getIndex()).setThickness(edgeBefore.getSize());
        graph.getEdges().get(edgeBefore.getIndex()).setDestination(graph.getVertexes().get(edgeBefore.getIndexDestination()));
        graph.getEdges().get(edgeBefore.getIndex()).setOrigin(graph.getVertexes().get(edgeBefore.getIndexSource()));



    }

    public void redo() throws CannotUndoException {

        if(edgeAfter.getColor()!=null)
            graph.getVertexes().get(edgeBefore.getIndex()).setColor(edgeAfter.getColor());

        if(edgeAfter.getLabel()!=null)
            graph.getVertexes().get(edgeBefore.getIndex()).setLabel(edgeAfter.getLabel());

        if(edgeAfter.getSize()!=-1)
            graph.getVertexes().get(edgeBefore.getIndex()).setSize(edgeAfter.getSize());

        if(edgeAfter.getIndexDestination()!=-1)
            graph.getEdges().get(edgeBefore.getIndex()).setDestination(graph.getVertexes().get(edgeAfter.getIndexDestination()));

        if(edgeAfter.getIndexSource()!=-1)
            graph.getEdges().get(edgeBefore.getIndex()).setOrigin(graph.getVertexes().get(edgeAfter.getIndexSource()));

    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertex edited"; }

}
