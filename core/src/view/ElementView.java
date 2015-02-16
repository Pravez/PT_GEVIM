package view;

import javax.swing.JComponent;

public abstract class ElementView extends JComponent {

	private static final long serialVersionUID = 1L;
	/** Regrouper color et hoverColor ? **/
	
	public abstract void updateHover(boolean isHover);
	public abstract void modify();
}
