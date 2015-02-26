package data;

import java.awt.*;

/**
 * @author Alexis Dufrenne
 *         Classe GraphElement, les Vertex et les Edge héritent de cette classe qui regroupe les éléments communs
 */
public abstract class GraphElement {

    private String label;
    private int value;
    private Color color;

    /**
     * Constructeur de la classe GraphElement
     *
     * @param label l'étiquette de l'élément
     * @param value la valeur de l'élément
     * @param color la couleur de l'élément
     */
    public GraphElement(String label, int value, Color color) {
        this(value, color);
        this.label = label;
    }

    /**
     * Constructeur de la classe GraphElement
     *
     * @param value la valeur de l'élément
     * @param color la couleur de l'élément
     */
    public GraphElement(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    public GraphElement(GraphElement graphElement) {
        this.label = graphElement.label;
        this.value = graphElement.value;
        this.color = graphElement.color;
    }

    /**
     * Getter de l'étiquette de l'élément
     *
     * @return l'étiquette
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Setter de l'étiquette de l'élément
     *
     * @param label la nouvelle étiquette
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter de la valeur de l'élément
     *
     * @return la valeur
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter de la valeur de l'élément
     *
     * @param value la nouvelle valeur
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter de la couleur de l'élément
     *
     * @return la couleur
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter de la couleur de l'élément
     *
     * @param color la nouvelle couleur
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Méthode pour savoir si le GraphElement est un Vertex ou non
     *
     * @return le résultat sous la forme d'un booléen
     */
    public abstract boolean isVertex();
}
