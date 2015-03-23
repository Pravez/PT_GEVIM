package undoRedo;

import data.Graph;
import undoRedo.snap.SnapVertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 18/03/15.
 */
public class VertexEdit extends AbstractUndoableEdit {


    private ArrayList<SnapVertex> verticesBefore;
    private SnapVertex vertexAfter;

    private Graph graph;

    public VertexEdit(Graph model, ArrayList<SnapVertex> before,SnapVertex after) {
        graph=model;
        verticesBefore =before;
        vertexAfter=after;
    }

    public VertexEdit(Graph model, SnapVertex before,SnapVertex after) {
        graph=model;
        verticesBefore= new ArrayList<>();
        verticesBefore.add(before);
        vertexAfter=after;
    }

    /**
     * Action correspondante à l'annulation de ce VertexEdit.Les Vertices récupérent leurs propriétés avant modification
     * @throws CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {

        for(SnapVertex s : verticesBefore) {
            if (vertexAfter.getColor() != null)
                graph.getVertexes().get(s.getIndex()).setColor(s.getColor());

            if (vertexAfter.getLabel() != null)
                graph.getVertexes().get(s.getIndex()).setLabel(s.getLabel());

            if (vertexAfter.getSize() != -1)
                graph.getVertexes().get(s.getIndex()).setSize(s.getSize());

            if (vertexAfter.getShape() != null)
                graph.getVertexes().get(s.getIndex()).setShape(s.getShape());

            if (vertexAfter.getPosition() != null)
                graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());

            if(vertexAfter.getValue()!=-1)
                graph.getVertexes().get(s.getIndex()).setValue(s.getValue());
        }
        graph.setChanged();

    }

    /**
     * Action correspondante au rétablissement de ce VertexEdit. Les Vertices retrouvent leurs propriétés après modification
     * @throws CannotUndoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotUndoException {

        for(SnapVertex s : verticesBefore) {

            if (vertexAfter.getColor() != null)
                graph.getVertexes().get(s.getIndex()).setColor(vertexAfter.getColor());

            if (vertexAfter.getLabel() != null)
                graph.getVertexes().get(s.getIndex()).setLabel(vertexAfter.getLabel());

            if (vertexAfter.getSize() != -1)
                graph.getVertexes().get(s.getIndex()).setSize(vertexAfter.getSize());

            if (vertexAfter.getShape() != null)
                graph.getVertexes().get(s.getIndex()).setShape(vertexAfter.getShape());

            if (vertexAfter.getPosition() != null)
                graph.getVertexes().get(s.getIndex()).setPosition(vertexAfter.getPosition());
            if(vertexAfter.getValue()!=-1)
                graph.getVertexes().get(s.getIndex()).setValue(vertexAfter.getValue());

        }
        graph.setChanged();

    }
    /**
     * Indique que le VertexEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }

    /**
     * Indique que l'on peut rétablir ce VertexEdit
     * @return true
     */
    public boolean canRedo() { return true; }

}
