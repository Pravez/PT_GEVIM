package threading;

import controller.Controller;
import data.Edge;
import data.GraphElement;
import data.Vertex;
import view.editor.elements.EdgeView;
import view.editor.elements.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.ListIterator;


/**
 * Classe faisant l'update à la place du Thread principal, pour le libérer d'une quantité importante de calculs.
 */
public class UpdateThread extends JDialog implements Runnable {
    private ArrayList<GraphElement> elements;
    private ArrayList<VertexView>   vertices;
    private ArrayList<EdgeView>     edges;
    private Controller              controller;
    private JComponent              sheet;

    /**
     * Constructeur de base du thread d'update.
     * @param sheet La sheet qui appelle le thread
     * @param newElements La liste des éléments actuels du graphe
     * @param controller Le controller du programme.
     */
    public UpdateThread(JComponent sheet, ArrayList<GraphElement> newElements, Controller controller) {
        this.sheet      = sheet;
        this.controller = controller;

        this.vertices   = new ArrayList<>();
        this.edges      = new ArrayList<>();
        this.elements   = newElements;

        JProgressBar progress = new JProgressBar(0, 100);
        progress.setIndeterminate(true);

        this.setTitle("Actualisation ...");
        this.setPreferredSize(new Dimension(300, 60));
        this.getContentPane().add(progress);
        this.setLocationRelativeTo(this.controller.getWindow().getTabs());
        this.pack();

        launchUpdate();

        this.setModal(true);
        this.setVisible(true);
    }

    /**
     * Méthode lançant le refresh des éléments. Elle appelle la méthode start qui va lancer un nouveau thread à
     * partir de cette classe.
     */
    public void launchUpdate() {
        Thread t = new Thread(this);
        t.start ();
        //On a crée un nouveau thread, maintenant on récupère le principal...
    }

    /**
     * Méthode de l'interface {@link Runnable}, méthode d'execution du thread crée, c'est elle
     * qui lance à proprement parler le refresh.
     */
    public void run () {
        this.updateView();
        onEnd();
    }

    /**
     * Méthode appellée à la fin du traitement
     */
    private void onEnd() {
        dispose();
    }

    /**
     * Methode executee par le nouveau thread, elle récupère les données et les traite.
     */
    public void updateView() {
        for (GraphElement element : elements) {
            if (element.isVertex()) {
                vertices.add(createVertexView((Vertex) element));
            }
        }

        for (GraphElement element : elements) {
            if (!element.isVertex()) {
                VertexView src = null, dst = null;
                ListIterator<VertexView> search = vertices.listIterator();

                while (search.hasNext() && (src == null || dst == null)) {
                    VertexView tmp = search.next();
                    if (tmp.getVertex().getID() == ((Edge) element).getOrigin().getID()) src = tmp;
                    else if (tmp.getVertex().getID() == ((Edge) element).getDestination().getID()) dst = tmp;
                }
               if (src != null && dst != null){
                   edges.add(createEdgeView((Edge) element, src, dst));
               }
            }
        }
    }

    /**
     * Méthode relative à la méthode de {@link view.editor.display.Sheet}, le but est le même mais transposée.
     * @param vertex Le vertex depuis lequel créer le nouveau vertexView
     * @return le vertex crée
     */
    private VertexView createVertexView(Vertex vertex) {
        final VertexView vertexView = new VertexView(vertex);
        vertexView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { controller.getState().click(vertexView, e); }
            @Override
            public void mousePressed(MouseEvent e) { controller.getState().pressed(vertexView, e); }
            @Override
            public void mouseReleased(MouseEvent e) { controller.getState().released(vertexView, e); }
            @Override
            public void mouseEntered(MouseEvent e) { controller.getState().mouseEntered(vertexView, e); }
            @Override
            public void mouseExited(MouseEvent e) { controller.getState().mouseExited(vertexView, e); }
        });
        vertexView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { controller.getState().drag(vertexView, e); }
        });

        sheet.add(vertexView);
        return vertexView;
    }

    /**
     * Méthode de création des EdgeView récupérée de {@link view.editor.display.Sheet}. Utilisée ici pour soulager le thread principal
     * @param edge L'edge à utiliser pour créer l'EdgeView
     * @param origin l'origine de l'edge
     * @param destination la destination de l'edge
     * @return L'edgeView crée
     */
    private EdgeView createEdgeView(Edge edge, VertexView origin, VertexView destination){
        final EdgeView edgeView = new EdgeView(edge, 1, origin, destination);
        edgeView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.getState().click(edgeView, e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                controller.getState().pressed(edgeView, e);
            }
        });

        sheet.add(edgeView);
        return edgeView;
    }

    /**
     * Getter des {@link data.Edge}
     * @return {@link java.util.ArrayList} de {@link data.Edge}
     */
    public ArrayList<EdgeView> getEdges() {
        return edges;
    }

    /**
     * Getter des {@link data.Vertex}
     * @return {@link java.util.ArrayList} de {@link data.Vertex}
     */
    public ArrayList<VertexView> getVertices() {
        return vertices;
    }
}
