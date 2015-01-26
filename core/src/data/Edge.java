package data;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.awt.*;

/**
 * Created by cordavidenko on 26/01/15.
 */

public class Edge implements javax.swing.undo.UndoableEdit{

    public Edge(int thickness, Color color, String label, Vertex origin, Vertex destination) {
        this.thickness = thickness;
        this.color = color;
        this.label = label;
        this.origin = origin;
        this.destination = destination;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    private int thickness;
    private java.awt.Color color;
    private String label;
    private Vertex origin;
    private Vertex destination;

    @Override
    public void undo() throws CannotUndoException {

    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public void redo() throws CannotRedoException {

    }

    @Override
    public boolean canRedo() {
        return false;
    }

    @Override
    public void die() {

    }

    @Override
    public boolean addEdit(UndoableEdit undoableEdit) {
        return false;
    }

    @Override
    public boolean replaceEdit(UndoableEdit undoableEdit) {
        return false;
    }

    @Override
    public boolean isSignificant() {
        return false;
    }

    @Override
    public String getPresentationName() {
        return null;
    }

    @Override
    public String getUndoPresentationName() {
        return null;
    }

    @Override
    public String getRedoPresentationName() {
        return null;
    }

    //rajouter des statics pour les paramètres par défaut

}
