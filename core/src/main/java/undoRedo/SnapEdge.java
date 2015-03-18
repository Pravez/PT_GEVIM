package undoRedo;

/**
 * Created by bendossantos on 18/03/15.
 */
public class SnapEdge extends SnapProperties {

  private int indexSource;
  private int indexDestination;

    public SnapEdge()
    {
        super();
        indexSource=-1;
        indexDestination=-1;
    }


    public int getIndexDestination() {
        return indexDestination;
    }

    public void setIndexDestination(int indexDestination) {
        this.indexDestination = indexDestination;
    }

    public int getIndexSource() {
        return indexSource;
    }

    public void setIndexSource(int indexSource) {
        this.indexSource = indexSource;
    }

}
