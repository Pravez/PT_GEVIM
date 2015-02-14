package data;

/**
 * @author Alexis Dufrenne
 * Interface IAlgorithm, tous les algorithmes qui modifieront un Graph implémenterons cette interface
 */
public interface IAlgorithm {
	/**
	 * Méthode pour faire tourner l'algorithme sur un Graph
	 * @param graph le Graph sur lequel faire tourner l'algorithme
	 */
	public abstract void run(Graph graph);
}
