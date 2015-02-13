package view;

import data.Observable;

public interface Observer {
	void update(Observable observable, Object object);
}
