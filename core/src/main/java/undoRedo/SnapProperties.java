package undoRedo;

import data.GraphElement;

import java.awt.*;

/**
 * Created by bendossantos on 16/03/15.
 */
public class SnapProperties {
    int index;
    Color color;
    int size;
    String label;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SnapProperties()
    {
        index=-1;
        color=null;
        size=-1;
        label=null;


    }

    public boolean isSnapVertex(){ return false; }

}
