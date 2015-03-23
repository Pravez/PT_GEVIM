package undoRedo.snap;

import java.awt.*;

/**
 *Classe SnapPosition, enregistre la position d'un {@link data.Vertex}. Permet l'undoRedo sur les déplacements
 */
public class SnapPosition {

    int index; // position du Vertex au sein de la liste des Vertices du Graph concerné
    Point position; //position du Vertex au sein du Graph

    /**
     * Constructeur de la classe SnapPosition
     * @param pos position du Vertex au sein du Graph
     * @param ind index du Vertex au sein de la liste des Vertices
     */
    public SnapPosition(Point pos, int ind){
        index=ind;
        position=pos;
    }

    /**
     * Getter de l'index du Vertex associé au SnapPosition
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter de l'index du Vertex associé au SnapPosition
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter de la position du Vertex associé au SnapPosition
     * @return la position du Vertex
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Setter de la position du Vertex associé au SnapPosition
     * @param position la nouvelle position
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
