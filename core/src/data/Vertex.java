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
public class Vertex
{
    private String          name;
    private java.awt.Color  color;
    private int             thickness;
    private int             width;
    private int             positionX;
    private int             positionY;
    private java.awt.Shape  shape;
    private ArrayList<Edge> edges;

    //rajouter des statics pour les paramètres par défaut

    /**
     * Vertex constructor
     * @param name
     * @param color
     * @param thickness
     * @param width
     * @param positionX
     * @param positionY
     * @param shape
     */
    public Vertex(String name, Color color, int thickness, int width, int positionX, int positionY, Shape shape) {
        this.name      = name;
        this.color     = color;
        this.thickness = thickness;
        this.width     = width;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape     = shape;
        this.edges     = new ArrayList<Edge>();
    }


    /**
     * Vertex constructor
     * @param color
     * @param thickness
     * @param width
     * @param positionX
     * @param positionY
     * @param shape
     */
    public Vertex(Color color, int thickness, int width, int positionX, int positionY, Shape shape) {
        this.color     = color;
        this.thickness = thickness;
        this.width     = width;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape     = shape;
        this.edges     = new ArrayList<Edge>();
    }
    
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
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
    
    public void setWidth(int width) {
        this.width = width;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getThickness() {
        return this.thickness;
    }
    
    public int getWidth() {
        return this.width;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public Shape getShape() {
        return this.shape;
    }

    /**
     * move the Vertex in function of the given vector value
     * @param vectorX
     * @param vectorY
     */
    public void move(int vectorX, int vectorY) {
        this.positionX += vectorX;
        this.positionY += vectorY;
    }
}
