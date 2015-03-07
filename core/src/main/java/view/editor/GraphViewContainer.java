package view.editor;

import controller.Controller;
import data.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by paubreton on 03/03/15.
 * Classe contenant la feuille de dessin et tout ce qui lui est associé, un {@link view.editor.PropertyPanel} et un {@link view.editor.Tab}
 */
public class GraphViewContainer extends JSplitPane{

    private PropertyPanel properties;
    private MiniMap minimap;
    private JPanel board;
    private Controller controller;
    private ScrollPane scrollPane;
    private Tab graphTab;


    /**
     * Constructeur de la classe
     * @param graph Le {@link data.Graph} auquel la feuille est associée
     * @param title Le titre de l'onglet
     * @param controller Un lien vers le {@link controller.Controller} pour pouvoir communiquer avec le modèle
     */
    public GraphViewContainer(Graph graph, String title, Controller controller) {

        super(JSplitPane.HORIZONTAL_SPLIT);

        this.setBackground(Color.WHITE);
        this.setOneTouchExpandable(true);
        this.setContinuousLayout(true);

        this.controller = controller;

        initTab(title, graph);
        initPropertyPanel(graph);

        this.setLeftComponent(this.scrollPane);
        this.setRightComponent(this.board);

    }

    /**
     * Méthode d'initialisation du {@link view.editor.Tab}
     * @param title Le titre du tab
     * @param graph Le {@link data.Graph} auquel est associé le Tab
     */
    public void initTab(String title, final Graph graph){

        graphTab = new Tab(graph, this.controller);
        graph.addObserver(graphTab);

        graphTab.setName(title);
        graphTab.setBackground(Color.GRAY);
        graphTab.setLayout(null);
        graphTab.add(new JLabel(title));
        graphTab.setPreferredSize(new Dimension(2000, 2000));


        scrollPane = new ScrollPane(graphTab);
        scrollPane.setMinimumSize(new Dimension(500,500));

        graphTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.getState().click(graphTab, graph, e);
            }

            @Override
            public void mousePressed(MouseEvent e) { controller.getState().pressed(graphTab, graph, e); }

            @Override
            public void mouseReleased(MouseEvent e) {
                controller.getState().released(graphTab, graph, e);
            }

        });

        graphTab.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { controller.getState().drag(graphTab, graph, e); }
        });

        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                controller.getState().wheel(graphTab, e);
            }
        });

        graphTab.setScrollPane(scrollPane);
        this.add(scrollPane);
    }

    public void initPropertyPanel(Graph graph){

        properties = new PropertyPanel(this.graphTab);
        properties.setPreferredSize(new Dimension(300, this.getHeight()));

        minimap = new MiniMap(graphTab.getPreferredSize().width/5, graphTab.getPreferredSize().height/5, scrollPane, graphTab);
        minimap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(minimap);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(minimap);
        graph.addObserver(minimap);
        graphTab.setMiniMap(minimap);

        board = new JPanel(new GridLayout(2,1));
        board.add(properties);
        board.add(minimap);

        this.add(board);
    }


    public Graph getGraph() {
        return this.graphTab.getGraph();
    }

    public JScrollPane getScrollPane(){
        return this.scrollPane;
    }
}
