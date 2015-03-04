package main.java.view.elements;

import main.java.data.Graph;
import main.java.data.GraphElement;

import javax.swing.JComponent;

/**
 * Classe mère de tous les éléments visuels de la feuille de dessin. {@link VertexView} et {@link EdgeView} en héritent.
 */
public abstract class ElementView extends JComponent {

	private static final long serialVersionUID = 1L;
	/** Regrouper color et hoverColor ? **/
	
	public abstract void updateHover(boolean isHover);
	public abstract void modify(Graph graph);
	public abstract GraphElement getGraphElement();
}
