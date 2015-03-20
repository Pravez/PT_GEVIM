package undoRedo;

import data.Graph;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 20/03/15.
 */
public class MoveEdit extends AbstractUndoableEdit {

    ArrayList<SnapPosition> positionsBefore;
    ArrayList<SnapPosition> positionsAfter;

    Graph graph;

    public MoveEdit(Graph model,ArrayList<SnapPosition> before, ArrayList<SnapPosition> after) {
        graph=model;
        positionsAfter=after;
        positionsBefore=before;
    }


    public void undo() throws CannotUndoException {

        for(SnapPosition s : positionsBefore) {
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
        }

        graph.setChanged();


    }


    public void redo() throws CannotUndoException {

        for(SnapPosition s : positionsAfter) {
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
        }

        graph.setChanged();

    }

    public boolean canUndo() { return true; }

    public boolean canRedo() { return true; }

    public String getPresentationName() { return "Vertices moved"; }

    }
