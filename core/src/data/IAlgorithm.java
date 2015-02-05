package data;

/**
 * Created by Alexis Dufrenne
 * Interface IAlgorithm. All algorithms which modifies the graph will implements this interface.
 */
public interface IAlgorithm {
	/**
	 * Run the algorithm on a specific graph.
	 * @param graph
	 */
	public abstract void run(Graph graph);
}
