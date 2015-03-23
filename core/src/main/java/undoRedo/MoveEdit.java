package undoRedo;

import data.Graph;
import undoRedo.snap.SnapPosition;

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

    /**
     * Action correspondante à l'annulation de ce MoveEdit. Retour à la position avant déplacement
     * @throws CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {

        for(SnapPosition s : positionsBefore) {
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
        }

        graph.setChanged();


    }

    /**
     * Action correspondante au rétablissement de ce MoveEdit précédemment annulé. Retour à la position après déplacement
     * @throws CannotUndoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotUndoException {

        for(SnapPosition s : positionsAfter) {
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
        }

        graph.setChanged();

    }
    /**
     * Indique que le MoveEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }

    /**
     * Indique que l'on peut rétablir ce MoveEdit
     * @return true
     */
    public boolean canRedo() { return true; }

    }
