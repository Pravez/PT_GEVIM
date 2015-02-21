package undoRedo;

import data.Graph;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;


/**
 * @author Benjamin Dos Santos
 * Classe UndoPanel, système de gestion de l'undoRedo, il inclut l'interface (boutons) ainsi que le Graph dont il est en charge
 */
public class UndoPanel extends JPanel {


    private Graph graph;
    JButton undo;
    JButton redo;

    UndoManager undoManager_;         // history list
    UndoableEditSupport undoSupport_; // event support

    /**
     * Getter du bouton undo
     * @return le bouton undo
     */
    public JButton getUndo() {
        return undo;
    }

    /**
     * Getter du bouton redo
     * @return le bouton redo
     */
    public JButton getRedo() {
        return redo;
    }

    /**
     * Constructeur par défaut de la classe UndoPanel
     */
    public UndoPanel( ) {


        //Création des actions associées aux boutons
        Action undoAction = new UndoAction();
        Action redoAction = new RedoAction();

        //Création des boutons et associations avec les actions correspondantes
        undo = new JButton("Undo");
        undo.addActionListener(undoAction);
        redo = new JButton("Redo");
        redo.addActionListener(redoAction);

        //Initialisation du module UndoRedo
        undoManager_= new UndoManager();
        undoSupport_ = new UndoableEditSupport();
        undoSupport_.addUndoableEditListener(new UndoAdapter());
        refreshUndoRedo();
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

        // refresh undo
        undo.setEnabled(undoManager_.canUndo());

        // refresh redo
        redo.setEnabled( undoManager_.canRedo() );
    }


    /**
     * Enregistre au sein du module UndoRedo la création d'un nouvel objet o au sein de graph
     * @param o le nouvel objet
     */
    public void register(Object o) {

        Object element = o;
        UndoableEdit edit = new AddEdit(graph,
                element);
        undoSupport_.postEdit(edit);
    }


    /**
     * Classe action associée au bouton undo
     */
    private class UndoAction extends AbstractAction {

        public void actionPerformed( ActionEvent evt ) {
            undoManager_.undo();
            refreshUndoRedo();
        }
    }


    /**
     * Class action associée au bouton redo
     */
    private class RedoAction extends AbstractAction {

        public void actionPerformed(ActionEvent evt ) {
            undoManager_.redo();
            refreshUndoRedo();
        }
    }


    /**
     * Listener mettant à jour l'undoManager après chaque undo ou redo
     */
    private class UndoAdapter implements UndoableEditListener {
        public void undoableEditHappened (UndoableEditEvent evt) {
            UndoableEdit edit = evt.getEdit();
            undoManager_.addEdit( edit );
            refreshUndoRedo();
        }
    }


}









