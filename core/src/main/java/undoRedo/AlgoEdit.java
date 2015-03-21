package undoRedo;

import data.Graph;
import undoRedo.snap.SnapVertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class AlgoEdit extends AbstractUndoableEdit {

    private ArrayList<SnapVertex> verticesBefore;
    private ArrayList<SnapVertex> verticesAfter;

    private Graph graph;

    public AlgoEdit(Graph model, ArrayList<SnapVertex> before, ArrayList<SnapVertex> after) {
        graph=model;
        verticesBefore =before;
        verticesAfter=after;
    }

    public void undo() throws CannotUndoException {

        for(SnapVertex s : verticesBefore) {
            graph.getVertexes().get(s.getIndex()).setColor(s.getColor());
            graph.getVertexes().get(s.getIndex()).setLabel(s.getLabel());
            graph.getVertexes().get(s.getIndex()).setSize(s.getSize());
            graph.getVertexes().get(s.getIndex()).setShape(s.getShape());
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
            graph.setChanged();
        }

    }

    public void redo() throws CannotUndoException {




            for(SnapVertex s : verticesAfter) {
                graph.getVertexes().get(s.getIndex()).setColor(s.getColor());
                graph.getVertexes().get(s.getIndex()).setLabel(s.getLabel());
                graph.getVertexes().get(s.getIndex()).setSize(s.getSize());
                graph.getVertexes().get(s.getIndex()).setShape(s.getShape());
                graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
                graph.setChanged();
            }

            graph.setChanged();


    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Algo"; }



}
