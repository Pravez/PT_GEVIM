package files.dot.elements;

import java.util.HashMap;

/**
 * Created by paubreton on 08/03/15.
 */
public abstract class DotElement {

    private HashMap<String, Object> attributes;
    private Integer id;

    public DotElement(){
        attributes = new HashMap<>();
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addAttribute(String attributeName, Object value){
        attributes.put(attributeName, value);
    }
}
