package files.dot;

import files.dot.elements.DotElement;
import files.dot.elements.EdgeDot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by paubreton on 08/03/15.
 * Classe permettant de parser un fichier .dot
 */
public class DotFileParser extends FileWriter {

    /**
     * Constructeur de la classe
     * @param file Le {@link java.io.File} associé
     * @throws IOException Si le fichier n'existe pas ou n'est pas trouvé
     */
    public DotFileParser(File file) throws IOException {
        super(file);
    }

    /**
     * Méthode d'écriture d'un élément dans le {@link java.io.File}
     * @param dotElement Le {@link files.dot.elements.DotElement} devant être écrit
     * @param indent L'indentation correspondante (en nombre d'espaces)
     * @throws IOException
     */
    public void write(DotElement dotElement, int indent) throws IOException {

        StringBuilder elementWriter = new StringBuilder();

        for(int i=0;i<indent;i++){
            elementWriter.append("    ");
        }
        elementWriter.append(dotElement.getId());
        elementWriter.append(" ");
        elementWriter.append(convertAttributes(dotElement));
        elementWriter.append(";\n");

        this.write(elementWriter.toString());

    }

    /**
     * Méthode d'écriture d'un {@link files.dot.elements.EdgeDot} dans un fichier
     * @param edgeElement Le {@link files.dot.elements.EdgeDot} qui sera écrit
     * @param indent L'indentation correspondante (en nombre d'espaces)
     * @throws IOException
     */
    public void write(EdgeDot edgeElement, int indent) throws IOException{

        StringBuilder elementWriter = new StringBuilder();

        for(int i=0;i<indent;i++){
            elementWriter.append("    ");
        }

        elementWriter.append(edgeElement.getOrigin().getId());
        elementWriter.append(" -- ");
        elementWriter.append(edgeElement.getDestination().getId());
        elementWriter.append(" ");
        elementWriter.append(convertAttributes(edgeElement));
        elementWriter.append(";\n");

        this.write(elementWriter.toString());
    }

    /**
     * Méthode de conversion et d'écriture des attributs d'un {@link files.dot.elements.DotElement} dans un fichier
     * @param dotElement Le {@link files.dot.elements.DotElement} concerné
     * @return
     */
    public String convertAttributes(DotElement dotElement){
        StringBuilder dotAttributes = new StringBuilder();

        HashMap<String, Object> attributes = dotElement.getAttributes();

        dotAttributes.append("[");

        for(String s : attributes.keySet()){
            dotAttributes.append(s);
            dotAttributes.append("=");
            dotAttributes.append(String.valueOf(attributes.get(s)));
            dotAttributes.append(",");
        }

        dotAttributes.append("]");
        dotAttributes.substring(dotAttributes.lastIndexOf(","), dotAttributes.lastIndexOf(","));

        return dotAttributes.toString();
    }
}
