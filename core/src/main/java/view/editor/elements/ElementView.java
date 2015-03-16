package view.editor.elements;

import data.Graph;
import data.GraphElement;

import javax.swing.*;
import java.awt.*;

/**
 * Classe mère de tous les éléments visuels de la feuille de dessin. {@link VertexView} et {@link EdgeView} en héritent.
 */
public abstract class ElementView extends JComponent {

	protected static final long serialVersionUID = 1L;
	protected Color color;
	protected Color hoverColor;

	public ElementView(Color color, Color hoverColor) {
		this.color      = color;
		this.hoverColor = hoverColor;
	}
	
	public abstract void updateHover(boolean isHover);
	public abstract void modify(Graph graph);
	public abstract GraphElement getGraphElement();

	/**
	 * Getter de la couleur de l'ElementView
	 * @return la couleur de l'ElementView
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Setter de la couleur de l'ElementView
	 * @param color nouvelle couleur du ElementView
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter de la couleur de l'ElementView lorsqu'il est sélectionné
	 * @return la couleur de l'ElementView lorsqu'il est sélectionné
	 */
	public Color getHoverColor() {
		return this.hoverColor;
	}

	/**
	 * Setter de la couleur de l'ElementView lorsqu'il est sélectionné
	 * @param color nouvelle couleur de l'ElementView lorsqu'il est sélectionné
	 */
	public void setHoverColor(Color color) {
		this.hoverColor = color;
	}
}
