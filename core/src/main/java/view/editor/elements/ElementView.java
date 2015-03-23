package view.editor.elements;

import data.Graph;
import data.GraphElement;
import undoRedo.snap.SnapProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Classe mère de tous les éléments visuels de la feuille de dessin. {@link VertexView} et {@link EdgeView} en héritent.
 */
public abstract class ElementView extends JComponent {

	protected static final long serialVersionUID = 1L;
	protected Color          color;
    protected Point2D.Double scale;

	public ElementView(Color color) {
		this.color      = color;
        this.scale      = new Point2D.Double(1.0, 1.0);
	}

	public ElementView(ElementView element) {
		this.color      = element.color;
        this.scale      = element.scale;
	}

	public abstract void updateHover(boolean isHover);
	public abstract SnapProperties modify(Graph graph);
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

	public abstract boolean isVertexView();

}
