package data;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vain on 26/01/15.
 * Modified by cordavidenko on 26/01/15
 */
public class Vertex implements javax.swing.undo.UndoableEdit
{

    private String name;
    private java.awt.Color color;
    private int thickness;
    private int positionX;
    private int positionY;
    private java.awt.Shape shape;
    private ArrayList<Edge> edges;

    //rajouter des statics pour les paramètres par défaut


    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getThickness() {
        return thickness;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Shape getShape() {
        return shape;
    }

    public Vertex(Color color, int thickness, int positionX, int positionY, Shape shape) {
        this.color = color;
        this.thickness = thickness;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = shape;
        this.edges = new ArrayList<Edge>();
    }

    public Vertex(String name, Color color, int thickness, int positionX, int positionY, Shape shape) {
        this.name = name;
        this.color = color;
        this.thickness = thickness;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = shape;
        this.edges = new ArrayList<Edge>();
    }

    public void move(int vectorX, int vectorY){
        this.positionX+=vectorX;
        this.positionY+=vectorY;
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

}
