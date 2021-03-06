package data;

import java.util.ArrayList;

import view.editor.display.Observer;

/**
 * Classe abstraite Observable, classe qui est observée par des {@link view.editor.display.Observer} et les prévient quand elle est modifiée
 */
public abstract class Observable {
	private ArrayList<Observer> myObservers = new ArrayList<Observer>();
	
	/**
	 * Méthode pour ajouter un Observer à la classe
	 * @param observer le nouvel Observer
	 */
	public void addObserver(Observer observer) {
		this.myObservers.add(observer);
	}
	
	/**
	 * Méthode pour prévenir les Observer de la classe qu'elle a été modifiée
	 * @param object l'objet qui a été modifié
	 */
	public void notifyObservers(Object object) {
		for (Observer observer : this.myObservers) {
			observer.update(this, object);
		}
	}
	
	/**
	 * Méthode appelée lorsque la classe a été modifiée
	 */
	public abstract void setChanged();
	
	/**
	 * Méthode appelée pour récupérer l'état actuel de la classe
	 * @return l'objet qui peut être modifié
	 */
	public abstract Object getState();
}
