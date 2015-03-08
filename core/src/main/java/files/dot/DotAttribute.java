package files.dot;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotAttribute {

    private String attributeName;
    private Object attribute;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getAttribute() {
        return attribute;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    public DotAttribute(String attributeName, Object attribute) {
        this.attributeName = attributeName;
        this.attribute = attribute;
    }
}
