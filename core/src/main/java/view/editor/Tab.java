package view.editor;

import controller.Controller;
import data.Graph;
import undoRedo.UndoPanel;
import view.UIElements.CustomUIManager;
import view.editor.display.MiniMap;
import view.editor.display.PropertyPanel;
import view.editor.display.Sheet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by paubreton on 03/03/15.
 * Classe contenant la feuille de dessin et tout ce qui lui est associé, un {@link view.editor.display.PropertyPanel} et un {@link view.editor.display.Sheet}
 */
public class Tab extends JSplitPane {

    private Controller    controller;
    /* Panel de droite */
    private JPanel        boardPanel;
    /* MiniMap dans la boardPanel */
    private MiniMap minimap;
    /* Panel de Propriétés */
    private PropertyPanel properties;
    /* ScrollPane */
    private ScrollPane    scrollPane;
    /* Sheet, feuille de dessin */
    private Sheet sheet;
    /* UndoPanel (non affiché) */
    private UndoPanel     undoredo;

    /**
     * Constructeur de la classe
     * @param graph Le {@link data.Graph} auquel la feuille est associée
     * @param title Le titre de l'onglet
     * @param controller Un lien vers le {@link controller.Controller} pour pouvoir communiquer avec le modèle
     */
    public Tab(Graph graph, String title, Controller controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);

        this.setOneTouchExpandable(false);
        this.setContinuousLayout(false);
        this.setBorder(BorderFactory.createEmptyBorder());

        this.controller = controller;

        initSheet(title, graph);
        initBoardPanel(graph);

        this.setLeftComponent(this.scrollPane);
        this.setRightComponent(this.boardPanel);
        this.setDividerSize(10);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resize();
            }
        });
        setColors();
    }

    public void setColors() {
        this.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                BasicSplitPaneDivider divider = new BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) { }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(CustomUIManager.scrollPaneBorderColor); // couleur du diviseur du JSplitPane
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
                divider.setCursor(Cursor.getDefaultCursor());
                return divider;
            }
        });
    }

    /**
     * Méthode privée appelée à chaque redimensionnement du composant
     */
    private void resize() {
        double dividerLocation = 0.75;
        this.setDividerLocation(dividerLocation);
        int map_size = (int) ((1.0 - dividerLocation) * this.getWidth());
        this.minimap.setPreferredSize(new Dimension(map_size, map_size));
    }

    /**
     * Méthode d'initialisation du {@link Sheet}
     * @param title Le titre de la Sheet
     * @param graph Le {@link data.Graph} auquel est associé la Sheet
     */
    public void initSheet(String title, final Graph graph){
        sheet = new Sheet(this, graph, this.controller);
        graph.addObserver(sheet);

        sheet.setName(title);
        sheet.setBackground(Color.GRAY);
        sheet.setLayout(null);
        sheet.add(new JLabel(title));
        sheet.setPreferredSize(new Dimension(2000, 2000));
        sheet.setMaximumSize(new Dimension(2000, 2000));
        sheet.setMinimumSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());

        scrollPane = CustomUIManager.addScrollPane(new ScrollPane(this, sheet));
        scrollPane.setMinimumSize(new Dimension(500,500));

        sheet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.getState().click(Tab.this, graph, e);
            }

            @Override
            public void mousePressed(MouseEvent e) { controller.getState().pressed(Tab.this, graph, e); }

            @Override
            public void mouseReleased(MouseEvent e) {
                controller.getState().released(Tab.this, graph, e);
            }

        });

        sheet.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { controller.getState().drag(Tab.this, graph, e); }
        });

        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                controller.getState().wheel(Tab.this, e);
            }
        });

        this.add(scrollPane);
    }

    /**
     * Initialisation du BoardPanel, la {@link javax.swing.JPanel} qui contiendra la {@link view.editor.display.MiniMap}, le {@link undoRedo.UndoPanel} et le {@link view.editor.display.PropertyPanel}
     * @param graph Le {@link data.Graph} auquel relatent le {@link view.editor.display.PropertyPanel} et la {@link view.editor.display.MiniMap}
     */
    public void initBoardPanel(Graph graph){
        properties = (PropertyPanel) CustomUIManager.addTab(new PropertyPanel(this.sheet));
        properties.setFocusable(false);

        minimap = new MiniMap(scrollPane, sheet);
        minimap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(minimap);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(minimap);
        graph.addObserver(minimap);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        undoredo   = new UndoPanel(graph);
        boardPanel = new JPanel(new BorderLayout());//new GridLayout(3,1));
        boardPanel.setMinimumSize(new Dimension((int) (0.75*this.getPreferredSize().width), this.getPreferredSize().height));
        boardPanel.add(toolBar);
        boardPanel.add(properties);
        boardPanel.add(minimap, BorderLayout.PAGE_END);
        this.add(boardPanel);
    }


    /**
     * Getter du graphe utilisé dans la feuille de dessin sélectionnée
     * @return Le {@link data.Graph} possédé
     */
    public Graph getGraph() {
        return this.sheet.getGraph();
    }

    /**
     * Getter du ScrollPane
     * @return Le {@link view.editor.ScrollPane}
     */
    public ScrollPane getScrollPane(){
        return this.scrollPane;
    }

    /**
     * Getter de la MiniMap
     * @return La {@link view.editor.display.MiniMap}
     */
    public MiniMap getMiniMap(){
        return this.minimap;
    }

    /**
     * Getter de la Sheet
     * @return La {@link view.editor.display.Sheet}
     */
    public Sheet getSheet(){
        return this.sheet;
    }

    /**
     * Getter d'undoRedo
     * @return undoredo
     */
    public UndoPanel getUndoRedo() {
        return this.undoredo;
    }

    /**
     * Getter des Propriétés
     * @return properties
     */
    public PropertyPanel getProperties() { return this.properties; }
}