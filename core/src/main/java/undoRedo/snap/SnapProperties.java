package undoRedo.snap;

import data.GraphElement;

import java.awt.*;

/**
 *Classe SnapProperties, enregistre l'ensemble des propriétés associées aux {@link data.GraphElement}.
 *Permet l'undoRedo lorsque un ensemble mélangé de {@link data.Vertex} et de {@link data.Edge} est édité
 */
public class SnapProperties {
    int index; //Index du GraphElement au sein de la liste de GraphElement du Graph associé
    Color color; //Couleur du GraphElement associé au SnapProperties
    int size;//Taille du GraphElement associé au SnapProperties
    String label;//Label du GraphElement associé au SnapProperties
    int value;//Valeur du GraphElement associé au SnapProperties

    /**
     * Constructeur par défaut de la classe SnapProperties. Affecte des valeurs par défaut permettant par la suite de détecter les modifications à prendre en compte
     */
    public SnapProperties()
    {
        index=-1;
        color=null;
        size=-1;
        label=null;
        value=-1;

    }

    /**
     * Getter de l'index du GraphElement associé au SnapProperties
     * @return l'index du GraphElement
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter de la valeur du GraphElement associé au SnapProperties
     * @return la valeur du GraphElement
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter de la valeur du GraphElement associé au SnapProperties
     * @param value la nouvelle valeur
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Setter de l'index du GraphElement associé au SnapProperties
     * @param index le nouvel index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter de la taille du GraphElement associé au SnapProperties
     * @return la taille du GraphElement
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter de la couleur du GraphElement associé au SnapProperties
     * @return la couleur du GraphElement
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter de la couleur du GraphElement associé au SnapProperties
     * @param color la nouvelle couleur
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Setter de l'index du GraphElement associé au SnapProperties
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Getter du label du GraphElement associé au SnapProperties
     * @return le label du GraphElement
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter du label du GraphElement associé au SnapProperties
     * @param label le nouveau label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Méthode permettant de distinguer les {@link undoRedo.snap.SnapEdge} des {@link undoRedo.snap.SnapVertex}
     * @return
     */
    public boolean isSnapVertex(){ return false; }

}
