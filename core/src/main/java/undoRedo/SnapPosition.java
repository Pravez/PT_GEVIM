package undoRedo;

import java.awt.*;

/**
 * Created by bendossantos on 20/03/15.
 */
public class SnapPosition {

    int index;
    Point position;

    public SnapPosition(Point pos, int ind){
        index=ind;
        position=pos;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
