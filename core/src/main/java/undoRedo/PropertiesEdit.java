package undoRedo;

import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;
import undoRedo.snap.SnapProperties;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Created by bendossantos on 16/03/15.
 */
public class PropertiesEdit extends AbstractUndoableEdit {


    private ArrayList<SnapProperties> propertiesBefore;
    private SnapProperties propertiesAfter;

    private Graph graph;

    public PropertiesEdit(Graph model, ArrayList <SnapProperties> before, SnapProperties after) {
        graph=model;
        propertiesAfter=after;
        propertiesBefore=before;
    }

    /**
     * Action correspondante à l'annulation de ce PropertiesEdit
     * @throws javax.swing.undo.CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {

        for(int i=0; i<propertiesBefore.size(); i++)
        {
            GraphElement tmp = graph.getGraphElements().get(propertiesBefore.get(i).getIndex());

            if(propertiesAfter.getColor()!=null)
                tmp.setColor(propertiesBefore.get(i).getColor());
            if(propertiesAfter.getLabel()!=null)
                tmp.setLabel(propertiesBefore.get(i).getLabel());
            if(propertiesAfter.getSize()!=-1) {
                if (tmp.isVertex())
                    ((Vertex) tmp).setSize(propertiesBefore.get(i).getSize());
                else
                    ((Edge) tmp).setThickness(propertiesBefore.get(i).getSize());
            }
            graph.setChanged();

        }

    }

    /**
     * Action correspondante au rétablissement de ce PropertiesEdit précédemment annulé
     * @throws javax.swing.undo.CannotRedoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotRedoException {

        for(int i=0; i<propertiesBefore.size(); i++)
        {
            GraphElement tmp = graph.getGraphElements().get(propertiesBefore.get(i).getIndex());

            if(propertiesAfter.getColor()!=null)
            tmp.setColor(propertiesAfter.getColor());
            if(propertiesAfter.getLabel()!=null)
            tmp.setLabel(propertiesAfter.getLabel());

            if(propertiesAfter.getSize()!=-1) {
                if (tmp.isVertex())
                    ((Vertex) tmp).setSize(propertiesAfter.getSize());
                else
                    ((Edge) tmp).setThickness(propertiesAfter.getSize());
            }
            graph.setChanged();

        }

    }

    /**
     * Indique que le PropertiesEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }

    /**
     * Indique que l'on peut rétablir ce PropertiesEdit
     * @return true
     */
    public boolean canRedo() { return true; }

}
