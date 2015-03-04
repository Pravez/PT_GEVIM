package main.java.view;

import main.java.data.Observable;

/**
 * @author Alexis Dufrenne
 * Interface Observer, classe permettant d'observer une classe Observable et d'être prévenue de ses modifications
 */
public interface Observer {
	
	/**
	 * Méthode appelée par la classe observée lorsqu'elle est modifiée
	 * @param observable la classe observée
	 * @param object l'objet de la classe observée ayant été modifié
	 */
	void update(Observable observable, Object object);
}
