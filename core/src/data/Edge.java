package data;

import java.awt.*;

/**
 * Created by cordavidenko on 26/01/15.
 */

public class Edge {
    private String         label;
    private Vertex         origin;
    private Vertex         destination;
    private int            thickness;
    private java.awt.Color color;

    //rajouter des statics pour les paramètres par défaut

    /**
     * Edge Constructor
     * @param thickness
     * @param color
     * @param label
     * @param origin
     * @param destination
     */
    public Edge(int thickness, Color color, String label, Vertex origin, Vertex destination) {
        this.thickness   = thickness;
        this.color       = color;
        this.label       = label;
        this.origin      = origin;
        this.destination = destination;
    }

    /**
     * Edge Constructor without label
     * @param thickness
     * @param color
     * @param origin
     * @param destination
     */
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
}
