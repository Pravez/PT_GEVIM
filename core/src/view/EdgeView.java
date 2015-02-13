package view;

import java.awt.*;

/**
 * Created by cordavidenko on 26/01/15.
 */

public class EdgeView {
    private String         label;
    private VertexView     origin;
    private VertexView     destination;
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
    public EdgeView(int thickness, Color color, String label, VertexView origin, VertexView destination) {
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
    public EdgeView(int thickness, Color color, VertexView origin, VertexView destination) {
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

    public VertexView getOrigin() {
        return this.origin;
    }

    public void setOrigin(VertexView origin) {
        this.origin = origin;
    }

    public VertexView getDestination() {
        return this.destination;
    }

    public void setDestination(VertexView destination) {
        this.destination = destination;
    }
}
