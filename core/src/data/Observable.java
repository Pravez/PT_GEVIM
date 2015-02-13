package data;

import java.util.ArrayList;

import view.Observer;

public abstract class Observable {
	private ArrayList<Observer> myObservers = new ArrayList<Observer>();
	
	public void addObserver(Observer observer) {
		this.myObservers.add(observer);
	}
	
	public void deleteObservers() {
		this.myObservers.clear();
	}
	
	public void notifyObservers(Object object) {
		for (Observer observer : this.myObservers) {
			observer.update(this, object);
		}
	}
	
	public abstract void setChanged();
	public abstract Object getState();
}
