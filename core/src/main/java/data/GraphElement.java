package data;

import java.awt.*;

/**
 * @author Alexis Dufrenne
 * Classe GraphElement, les Vertex et les Edge héritent de cette classe qui regroupe les éléments communs
 */
public abstract class GraphElement {

    private static int CURRENT_ID = 0;

    private String label;
	private int    value;
    private int    id;
	private Color  color;

	/**
	 * Constructeur de la classe GraphElement
	 * @param label l'étiquette de l'élément
	 */
	public GraphElement(String label, Color color) {
		this(CURRENT_ID);
        if(label == "edge" || label == "node"){
            this.label = label+ CURRENT_ID;
        }else {
            this.label = label;
        }
		this.color = color;
        CURRENT_ID++;
	}
	
	/**
	 * Constructeur de la classe GraphElement
	 * @param id l'id de l'élément
	 */
	public GraphElement(int id) {
		this.id    = id;
        this.value = 1;
	}

	public GraphElement(GraphElement graphElement) {
		this.label = graphElement.label;
		this.value = graphElement.value;
		this.color = graphElement.color;
	}

	/**
	 * Getter de l'étiquette de l'élément
	 * @return l'étiquette
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Setter de l'étiquette de l'élément
	 * @param label la nouvelle étiquette
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Getter de l'id de l'élément
	 * @return l'id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Setter de l'id de l'élément
	 * @param id le nouvel id
	 */
	public void setID(int id) {
		this.id = id;
	}

    /**
     * Getter de la valeur de l'élément
     * @return la valeur
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter de la valeur de l'élément
     * @param value la nouvelle valeur
     */
    public void setValue(int value) {
        this.value = value;
    }

	/**
	 * Méthode pour savoir si le GraphElement est un Vertex ou non
	 * @return le résultat sous la forme d'un booléen
	 */
	public abstract boolean isVertex();

	/**
	 * Méthode pour récupérer la couleur du GraphElement
	 * @return la couleur du GraphElement
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Méthode pour récupérer la couleur du GraphElement
	 * @param color la couleur du GraphElement
	 */
	public void setColor(Color color){
		this.color = color;
	}
}
