package undoRedo;

import data.Graph;
import undoRedo.snap.SnapVertex;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;

/**
 * Classe AlgoEdit, enregistre l'application d'un {@link algorithm.IAlgorithm} sur un {@link data.Graph}.
*/
 public class AlgoEdit extends AbstractUndoableEdit {

    private ArrayList<SnapVertex> verticesBefore; //propriétés de l'ensemble des Vertices du Graph avant application de l'algorithme
    private ArrayList<SnapVertex> verticesAfter;//propriétés de l'ensemble des Vertices du Graph après application de l'algorithme

    private Graph graph;//Graph sur lequel a été appliqué l'algorithme

    /**
     * Constructeur de la classe AlgoEdit
     * @param model Graph sur lequel a été appliqué l'algorithme
     * @param before propriétés des Vertices avant l'algorithme
     * @param after propriétés des Vertices après l'algorithme
     */
    public AlgoEdit(Graph model, ArrayList<SnapVertex> before, ArrayList<SnapVertex> after) {
        graph=model;
        verticesBefore =before;
        verticesAfter=after;
    }

    /**
     * Action correspondante à l'annulation de cet AlgoEdit. Retour à l'état avant application de l'algorithme
     * @throws CannotUndoException renvoyée lorsque l'undo est impossible
     */
    public void undo() throws CannotUndoException {

        for(SnapVertex s : verticesBefore) {
            graph.getVertexes().get(s.getIndex()).setColor(s.getColor());
            graph.getVertexes().get(s.getIndex()).setLabel(s.getLabel());
            graph.getVertexes().get(s.getIndex()).setSize(s.getSize());
            graph.getVertexes().get(s.getIndex()).setShape(s.getShape());
            graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
            graph.getVertexes().get(s.getIndex()).setValue(s.getValue());

        }
        graph.setChanged();


    }

    /**
     * Action correspondante au rétablissement de cet AlgoEdit précédemment annulé. Retour à l'état du Graph après application de l'algorithme
     * @throws CannotUndoException renvoyée lorsque le redo est impossible
     */
    public void redo() throws CannotUndoException {

            for(SnapVertex s : verticesAfter) {
                graph.getVertexes().get(s.getIndex()).setColor(s.getColor());
                graph.getVertexes().get(s.getIndex()).setLabel(s.getLabel());
                graph.getVertexes().get(s.getIndex()).setSize(s.getSize());
                graph.getVertexes().get(s.getIndex()).setShape(s.getShape());
                graph.getVertexes().get(s.getIndex()).setPosition(s.getPosition());
                graph.getVertexes().get(s.getIndex()).setValue(s.getValue());

            }

            graph.setChanged();

    }

    /**
     * Indique que l'AlgoEdit est annulable
     * @return true
     */
    public boolean canUndo() { return true; }
    /**
     * Indique que l'on peut rétablir cet AlgoEdit
     * @return true
     */
    public boolean canRedo() { return true; }

}
