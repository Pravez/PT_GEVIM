package undoRedo;

import controller.ActionController;
import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;
import undoRedo.snap.SnapEdge;
import undoRedo.snap.SnapPosition;
import undoRedo.snap.SnapProperties;
import undoRedo.snap.SnapVertex;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;


/**
 * Classe UndoPanel, système de gestion de l'undoRedo, il inclut les modules fournis par java ({@link javax.swing.undo.UndoManager} et {@link javax.swing.undo.UndoableEditSupport})
 * ainsi que le {@link data.Graph} dont il est en charge
 */
public class UndoPanel extends JPanel {

    private Graph graph; //Graph dont est en charge l'UndoPanel

    UndoManager undoManager;         // Historique de l'undoRedo
    UndoableEditSupport undoSupport; // support gérant automatiquement l'empilage et le dépilage de la pile de l'undoManager

    /**
     * Constructeur par défaut de la classe UndoPanel
     */
    public UndoPanel( ) {
        //Initialisation du module UndoRedo
        undoManager = new UndoManager();
        undoSupport = new UndoableEditSupport();
        undoSupport.addUndoableEditListener(new UndoAdapter());
    }

    /**
     * Constructeur de la classe UndoPanel
     * @param graph Graph dont sera en charge l'UndoPanel
     */
    public UndoPanel(Graph graph ) {
        //Initialisation du module UndoRedo
        undoManager = new UndoManager();
        undoSupport = new UndoableEditSupport();
        undoSupport.addUndoableEditListener(new UndoAdapter());
        this.graph=graph;
    }

    /**
     * Setter de graph
     * @param graph le nouveau graph associé
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }


    /**
     * Action appelée après chaque action d'undo/redo. Elle met notamment à jour la disponibilité des boutons
     */
    public void refreshUndoRedo() {
        ActionController.refreshUndoRedo(undoManager.canUndo(), undoManager.canRedo());
    }


    /**
     * Enregistre au sein du module UndoRedo la création d'un ou plusieurs {@link data.GraphElement} au sein du {@link data.Graph} associé
     * @param newElements les nouveaux objets créés
     */
    public void registerAddEdit(ArrayList<GraphElement> newElements) {

        UndoableEdit edit = new AddEdit(graph, newElements);
        undoSupport.postEdit(edit);
    }



    /**
     * Enregistre au sein du module UndoRedo la suppression d'un ou pluqieurs {@link data.GraphElement} au sein du {@link data.Graph} associé
     * @param suppElements GraphElements venant d'être supprimés
     */
    public void registerSuppEdit(ArrayList<GraphElement> suppElements) {

        ArrayList <GraphElement> vertexes= new ArrayList<>();
        ArrayList <GraphElement> collateralEdges = new ArrayList<>(); //ensemble des Edges supprimés suite à la suppression d'un des Vertices
        for(GraphElement g : suppElements) {

            if(g.isVertex())
            {
                vertexes.add(g);
                Vertex v = (Vertex) g;
                for(Edge e : v.getEdges())
                {
                    if(!collateralEdges.contains(e))
                    collateralEdges.add(e);
                }
            } 
            else {
                if(!collateralEdges.contains(g))
                collateralEdges.add(g);
            }

        }
        vertexes.addAll(collateralEdges);
        UndoableEdit edit = new SuppEdit(graph, vertexes);
        undoSupport.postEdit(edit);
    }

    /**
     * Enregistre au sein du module UndoRedo la modification des propriétés d'un ensemble de {@link data.Edge} et de {@link data.Vertex}
     * @param propertiesBefore ensemble des propriétés des GraphElements concernés avant modification
     * @param snapAfter ensemble des nouvelles propriétés
     */
    public void registerPropertiesEdit(ArrayList<SnapProperties> propertiesBefore, SnapProperties snapAfter)
    {
        UndoableEdit edit = new PropertiesEdit(graph, propertiesBefore,snapAfter);
        undoSupport.postEdit(edit);

    }

    /**
     * Enregistre au sein du module UndoRedo le déplacement d'un ensemble de {@link data.Vertex}
     * @param positionsBefore ensemble des positions avant déplacements
     * @param positionsAfter ensemble des positions après déplacements
     */
    public void registerMoveEdit(ArrayList<SnapPosition> positionsBefore, ArrayList<SnapPosition> positionsAfter)
    {
        UndoableEdit edit = new MoveEdit(graph, positionsBefore,positionsAfter);
        undoSupport.postEdit(edit);
    }

    /**
     * Enregistre au sein du module UndoRedo la modification des propriétés d'un ensemble composé uniquement de  {@link data.Vertex} ou de {@link data.Edge}
     * @param propertiesBefore ensemble des propriétés des GraphElements concernés avant modification
     * @param snapAfter  ensemble des nouvelles propriétés
     */
    public void registerSingleTypeEdit(ArrayList <SnapProperties> propertiesBefore, SnapProperties snapAfter)
    {
        UndoableEdit edit;
        if(propertiesBefore.get(0).isSnapVertex()){

            ArrayList<SnapVertex> toSnapVertex = new ArrayList<>();
            for(SnapProperties s : propertiesBefore)
            {
             toSnapVertex.add((SnapVertex)s);
            }
           edit = new VertexEdit(graph, toSnapVertex,(SnapVertex)snapAfter);
        }
        else
            edit = new EdgeEdit(graph, (SnapEdge)propertiesBefore.get(0),(SnapEdge)snapAfter);

        undoSupport.postEdit(edit);

    }

    /**
     * Enregistre au sein du module UndoRedo la modification des propriétés d'un seul {@link data.GraphElement}
     * @param propertiesBefore ensemble des propriétés du GraphElements concerné avant modification
     * @param snapAfter  ensemble des nouvelles propriétés
     */
    public void registerSingleTypeEdit(SnapProperties propertiesBefore, SnapProperties snapAfter)
    {
        UndoableEdit edit;
        if(propertiesBefore.isSnapVertex()){

              edit = new VertexEdit(graph, (SnapVertex)propertiesBefore,(SnapVertex)snapAfter);
        }
        else
            edit = new EdgeEdit(graph, (SnapEdge)propertiesBefore,(SnapEdge)snapAfter);

        undoSupport.postEdit(edit);

    }

    /**
     * Enregistre au sein du module UndoRedo l'application d'un {@link algorithm.IAlgorithm}
     * @param verticesBefore propriétés de l'ensemble des Vertices du Graph avant application de l'algorithme
     * @param verticesAfter propriétés de l'ensemble des Vertices du Graph après application de l'algorithme
     */
    public void registerAlgoEdit(ArrayList<SnapVertex> verticesBefore, ArrayList<SnapVertex> verticesAfter)
    {
        UndoableEdit edit = new AlgoEdit(graph, verticesBefore,verticesAfter);
        undoSupport.postEdit(edit);

    }

    /**
     * Méthode appelant la méthode undo de l'instance de {@link javax.swing.undo.AbstractUndoableEdit} du dessus de la pile du {@link javax.swing.undo.UndoManager}
     */
    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
            refreshUndoRedo();
        }
    }

    /**
     * Méthode appelant la méthode redo de l'instance de {@link javax.swing.undo.AbstractUndoableEdit} du dessus de la pile du {@link javax.swing.undo.UndoManager}
     */
    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
            refreshUndoRedo();
        }
    }

    /**
     * Listener mettant à jour l'undoManager après chaque undo ou redo
     */
    private class UndoAdapter implements UndoableEditListener {
        public void undoableEditHappened (UndoableEditEvent evt) {
            UndoableEdit edit = evt.getEdit();
            undoManager.addEdit(edit);
            refreshUndoRedo();
        }
    }
}









