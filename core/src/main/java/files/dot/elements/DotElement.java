package files.dot.elements;

import java.util.HashMap;

/**
 * Element composant un {@link files.dot.GraphDot} de la même manière que des éléments composent un graphe
 */
public abstract class DotElement {

    private HashMap<String, Object> attributes;
    private Integer id;

    public DotElement(){
        attributes = new HashMap<>();
    }

    /**
     * Getter des attributs de l'element
     * @return Un {@link java.util.HashMap} avec pour clé un nom d'attribut ({@link java.lang.String}) et pour valeur un {@link java.lang.Object} associé
     */
    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Getter de l'ID de l'element
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter de l'ID de l'element
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Méthode d'ajout d'un attribut à l'element
     * @param attributeName Le nom de l'attribut ({@link java.lang.String})
     * @param value La valeur de l'attribut ({@link java.lang.Object})
     */
    public void addAttribute(String attributeName, Object value){
        attributes.put(attributeName, value);
    }
}
