package files.dot.elements;

import java.util.ArrayList;

/**
 * Created by paubreton on 08/03/15.
 */
public abstract class DotElement {

    private ArrayList<DotAttribute> attributes;
    private Integer id;

    public DotElement(){
        attributes = new ArrayList<>();
    }

    public ArrayList<DotAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<DotAttribute> attributes) {
        this.attributes = attributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addAttribute(String attributeName, Object value){
        attributes.add(new DotAttribute(attributeName, value));
    }
}
