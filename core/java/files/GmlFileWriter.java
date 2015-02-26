package files;

import data.Edge;
import data.Graph;
import data.Vertex;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by paubreton on 23/02/15.
 * Classe permettant de gérer l'utilisation de l'enregistrement de graphes au format GML.
 * Elle est héritée de {@link org.jdom2.Document}. Elle possède des méthodes créant le document GML,
 * puis l'enregistrant dans un fichier.
 * Le document n'est pas un GML officiel, mais un réalisé à la main aussi proche que possible du réel de manière à
 * ce qu'il soit lu partout.
 */
public class GmlFileWriter extends Document {

    private Graph graph;
    private Element rootElement;
    private File fileAssociated;

    /**
     * Constructeur de base
     *
     * @param graph          {@link data.Graph} associé au document
     * @param fileAssociated {@link java.io.File} associé au document
     */
    public GmlFileWriter(Graph graph, File fileAssociated) {

        super();

        this.graph = graph;
        this.fileAssociated = fileAssociated;

        initDocument();
    }

    /**
     * Initialise le document GML en créant l'{@link org.jdom2.Element} principal.
     */
    private void initDocument() {

        //Document namespace, à fix
        /*Element graphml = new Element("graphml");
        Namespace namespace = Namespace.getNamespace(JDOMConstants.NS_PREFIX_XMLNS, JDOMConstants.NS_URI_XMLNS);
        graphml.addNamespaceDeclaration(namespace);*/

        rootElement = new Element("graph");
        rootElement.setAttribute("id", "0");
        rootElement.setAttribute("edgedefault", "undirected");

        //this.addContent(graphml);
        this.setRootElement(rootElement);
    }

    /**
     * Méthode créant le contenu du document à partir des données du {@link data.Graph} associé
     */
    public void createDocumentContent() {

        for (Vertex v : this.graph.getVertexes()) {
            this.rootElement.addContent(createVertexElement(v));
        }

        for (Edge e : this.graph.getEdges()) {
            this.rootElement.addContent(createEdgeElement(e));
        }
    }

    /**
     * Fonction permettant de passer d'un Vertex à un {@link org.jdom2.Element}
     *
     * @param v Le vertex à "convertir"
     * @return L'Element créee
     */
    private Element createVertexElement(Vertex v) {

        Element createdElement = new Element("node");
        createdElement.setAttribute("id", String.valueOf(v.getValue()));

        Element name = new Element("data");
        name.setAttribute("key", "name");
        Element color = new Element("data");
        color.setAttribute("key", "color");
        Element size = new Element("data");
        size.setAttribute("key", "size");
        Element positionX = new Element("data");
        positionX.setAttribute("key", "positionX");
        Element positionY = new Element("data");
        positionY.setAttribute("key", "positionY");
        Element shape = new Element("data");
        shape.setAttribute("key", "shape");

        name.setText(v.getLabel());
        color.setText(String.valueOf(v.getColor().getRGB()));
        positionX.setText(String.valueOf(v.getPosition().x));
        positionY.setText(String.valueOf(v.getPosition().y));
        shape.setText(v.getShape().toString());

        createdElement.addContent(name);
        createdElement.addContent(color);
        createdElement.addContent(size);
        createdElement.addContent(positionX);
        createdElement.addContent(positionY);
        createdElement.addContent(shape);

        return createdElement;
    }


    /**
     * Fonction permettant de passer d'un Edge à un {@link org.jdom2.Element}
     *
     * @param e L'edge à transcrire
     * @return L'Element crée
     */
    private Element createEdgeElement(Edge e) {

        Element createdElement = new Element("edge");
        createdElement.setAttribute("id", String.valueOf(e.getValue()));

        Element origin = new Element("data");
        origin.setAttribute("key", "origin");
        Element destination = new Element("data");
        destination.setAttribute("key", "destination");
        Element thickness = new Element("data");
        thickness.setAttribute("key", "thickness");

        origin.setText(String.valueOf(e.getOrigin().getValue()));
        destination.setText(String.valueOf(e.getDestination().getValue()));
        thickness.setText(String.valueOf(e.getThickness()));


        createdElement.addContent(origin);
        createdElement.addContent(destination);
        createdElement.addContent(thickness);

        return createdElement;
    }

    /**
     * Méthode donnant le résultat de la création du document sur la sortie out du System.
     */
    public void showDocument() {

        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

        try {
            out.output(this, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode sauvegarde le document créee dans le {@link java.io.File} associé à ce dernier.
     */
    public void saveDocument() {
        try {
            //On modifie le format d'enregistrement (affichage)
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

            out.output(this, new FileOutputStream(fileAssociated));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
