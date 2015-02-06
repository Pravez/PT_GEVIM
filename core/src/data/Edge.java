package data;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import java.awt.*;

/**
 * Created by cordavidenko on 26/01/15.
 */

public class Edge implements javax.swing.undo.UndoableEdit {

    private String         label;
    private Vertex         origin;
    private Vertex         destination;
    private int            thickness;
    private java.awt.Color color;
	
    public Edge(int thickness, Color color, String label, Vertex origin, Vertex destination) {
        this.thickness   = thickness;
        this.color       = color;
        this.label       = label;
        this.origin      = origin;
        this.destination = destination;
    }
    
    public Edge(int thickness, Color color, Vertex origin, Vertex destination) {
        this.thickness   = thickness;
        this.color       = color;
        this.origin      = origin;
        this.destination = destination;
    }
    
    public int getThickness() {
        return this.thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Vertex getOrigin() {
        return this.origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getDestination() {
        return this.destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public void undo() throws CannotUndoException {

    }

    public boolean canUndo() {
        return false;
    }

    public void redo() throws CannotRedoException {

    }

    public boolean canRedo() {
        return false;
    }

    public void die() {

    }

    public boolean addEdit(UndoableEdit undoableEdit) {
        return false;
    }

    public boolean replaceEdit(UndoableEdit undoableEdit) {
        return false;
    }

    public boolean isSignificant() {
        return false;
    }

    public String getPresentationName() {
        return null;
    }

    public String getUndoPresentationName() {
        return null;
    }

    public String getRedoPresentationName() {
        return null;
    }

    //rajouter des statics pour les paramètres par défaut

}
