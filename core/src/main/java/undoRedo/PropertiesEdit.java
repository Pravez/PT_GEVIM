package undoRedo;

import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
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
     * @throws javax.swing.undo.CannotUndoException
     */
    public void undo() throws CannotUndoException {

        for(int i=0; i<propertiesBefore.size(); i++)
        {
            graph.getGraphElements().get(propertiesBefore.get(i).getIndex()).setColor(propertiesBefore.get(i).getColor());
            graph.setChanged();

        }

    }

    /**
     * Action correspondante au rétablissement de ce PropertiesEdit précédemment annulé
     * @throws javax.swing.undo.CannotRedoException
     */
    public void redo() throws CannotRedoException {

        for(int i=0; i<propertiesBefore.size(); i++)
        {
            GraphElement tmp = graph.getGraphElements().get(propertiesBefore.get(i).getIndex());
            tmp.setColor(propertiesAfter.getColor());
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

    //pas encore utilisée, permettrait de générer un historique
    public String getPresentationName() { return "Properties edited"; }
    
    
}
