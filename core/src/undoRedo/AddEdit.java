package undoRedo;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * Created by bendossantos on 12/02/15.
 */
public class AddEdit extends AbstractUndoableEdit{


    private Object element_;

                 private int index_;

                 private DefaultListModel model_;

                 public AddEdit(DefaultListModel model, Object element, int index) {
                  model_=model;

                  element_ = element;

                  index_=index;
             }

                 public void undo() throws CannotUndoException {

                 model_.removeElementAt(index_);

             }

                 public void redo() throws CannotRedoException {
                  model_.insertElementAt(element_,index_);
             }

                 public boolean canUndo() { return true; }

                 public boolean canRedo() { return true; }

             public String getPresentationName() { return "Add"; }

}
