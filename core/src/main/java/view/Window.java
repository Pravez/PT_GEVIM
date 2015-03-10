package view;

import controller.Controller;
import controller.listeners.ButtonActionListener;
import controller.listeners.KeyActionListener;
import controller.state.State;
import data.Graph;
import files.dot.DotFileManager;
import files.gml.GmlFileManager;
import view.editor.Sheet;
import view.editor.Tab;
import view.frames.ButtonFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Alexis Dufrenne
 * Classe Window gérant la fenêtre principale de l'application. C'est la Vue principale du pattern MVC,
 * elle interagit avec l'utilisateur.
 */
public class Window extends JFrame {

    private static final long serialVersionUID = 1L;
    private static int             tabIndex = 1;
    private Controller             controller;
    private JPanel                 back;

    /* tabs, ensemble des onglets */
    private JTabbedPane            tabs;
    private ArrayList<StateButton> stateButtons;
    private JButton                undoButton;
    private JButton                redoButton;

    private JPanel                 startPanel;

    /**
     * Constructeur de la classe Window
     *
     * @param w          la largeur de la fenêtre
     * @param h          la hauteur de la fenêtre
     * @param controller le Controller qui gère l'application
     */
    public Window(int w, int h, Controller controller) {
        initWindow(w, h, controller);
        initMenu();
        initBackPanel();
        initToolMenuBar();
        initStartPanel();

        tabs.setOpaque(true);
        tabs.setBackground(Color.GRAY);
        tabs.setFocusable(false);

        this.setFocusable(true);
        this.addKeyListener(new KeyActionListener());

        this.setVisible(true);
    }

    /**
     * Méthode permettant d'afficher la fenêtre d'accueil et de masquer les onglets
     */
    public void showStartPanel() {
        this.back.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        this.back.add(this.startPanel, gbc);
        this.tabs.setVisible(false);
        this.startPanel.setVisible(true);
    }

    /**
     * Méthode permettant de masquer la fenêtre d'accueil et d'afficher les onglets
     */
    public void hideStartPanel() {
        this.back.setLayout(new BorderLayout());
        this.back.add(this.tabs, BorderLayout.CENTER);
        this.tabs.setVisible(true);
        this.startPanel.setVisible(false);
    }

    /**
     * Initialise la fenêtre d'accueil avec les boutons Nouveau et Ouvrir
     */
    private void initStartPanel() {
        this.startPanel = new JPanel();
        this.startPanel.setBackground(null);
        this.startPanel.add(ButtonFactory.createImageButton("New", "New", "core/assets/new-very-big.png", "Nouveau graphe", new Color(105, 105, 105), 128));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setPreferredSize(new Dimension(15, 128));
        this.startPanel.add(Box.createHorizontalStrut(5));
        this.startPanel.add(separator);
        this.startPanel.add(Box.createHorizontalStrut(5));
        this.startPanel.add(ButtonFactory.createImageButton("Open", "Open", "core/assets/open-very-big.png", "Ouvrir un graphe", new Color(105, 105, 105), 128));
        showStartPanel();
    }

    /**
     * Initialise le {@link javax.swing.JPanel} de fond, le principal qui va stocker l'ensemble des items de Swing.
     */
    private void initBackPanel() {
        this.back = new JPanel();
        this.back.setBackground(Color.WHITE);
        this.getContentPane().add(this.back);
    }

    /**
     * Initialisation de la {@link view.Window} avec son {@link controller.Controller} associé, sa largeur et sa longueur.
     *
     * @param width la largeur de la Window
     * @param height la hauteur de la Window
     * @param controller le Controller de l'application
     */
    private void initWindow(int width, int height, Controller controller) {
        this.controller = controller;

        this.setTitle("GEVIM");
        this.setSize(width, height);
        this.setMinimumSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.tabs = new JTabbedPane(SwingConstants.TOP);
        this.tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Window.this.controller.notifyTabChanged();
            }
        });

        //Icone de l'application
        //setIconImage(Toolkit.getDefaultToolkit().getImage("appIcon.png"));
    }

    /**
     * Méthode ajoutant à la {@link javax.swing.JMenuBar} principale de la {@link view.Window} un nouveau {@link javax.swing.JMenu}
     *
     * @param menuText Le nom du nouveau menu
     * @return Le menu crée après avoir été ajouté
     */
    private JMenu addMenu(String menuText) {
        JMenu menu = new JMenu(menuText);
        super.getJMenuBar().add(menu);
        return menu;
    }

    /**
     * Adds to a menu a JMenuItem with a listener
     *
     * @param menu  menu to add the item
     * @param label label of the item
     */
    private void addJMenuItem(JMenu menu, String label, String action) {
        JMenuItem item = new JMenuItem(label);
        item.setActionCommand(action);
        menu.add(item);
        item.addActionListener(new ButtonActionListener(item, null, 0));
    }

    /**
     * Method to init the tool menu bar (undo, redo, copy, paste...)
     */
    private void initToolMenuBar() {
        JToolBar toolBar      = new JToolBar();
        toolBar.setFocusable(false);
        this.stateButtons = new ArrayList<StateButton>();
        toolBar.setFloatable(false);

        toolBar.add(ButtonFactory.createToolBarButtonWithImage("New", "New", "core/assets/new.png", "Nouveau graphe"));
        addToolBarStateButton(toolBar, "core/assets/cursor.png", State.Mode.SELECTION.name(), "Mode édition");
        addToolBarStateButton(toolBar, "core/assets/edit.png", State.Mode.CREATION.name(), "Mode création");
        addToolBarStateButton(toolBar, "core/assets/zoom.png", State.Mode.ZOOM.name(), "Zoom");
        toolBar.add(ButtonFactory.createToolBarButtonWithImage("Copy", "Copy", "core/assets/copy.png", "Copier"));
        toolBar.add(ButtonFactory.createToolBarButtonWithImage("Paste", "Paste", "core/assets/paste.png", "Coller"));
        this.undoButton = ButtonFactory.createToolBarButtonWithImage("Undo", "Undo", "core/assets/undo.png", "Annuler");
        this.redoButton = ButtonFactory.createToolBarButtonWithImage("Redo", "Redo", "core/assets/redo.png", "Rétablir");

        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);

        toolBar.add(this.undoButton);
        toolBar.add(this.redoButton);

        super.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Méthode privée pour ajouter un StateButton avec une image dans un toolBar
     *
     * @param toolBar       le toolBar qui va contenir le bouton
     * @param fileName      le nom du fichier de l'image à charger
     * @param actionCommand la commande qui sera appellée lors du clic sur le bouton
     * @param helpMessage   le message d'aide du bouton
     */
    private void addToolBarStateButton(JToolBar toolBar, String fileName, String actionCommand, String helpMessage) {
        StateButton button = new StateButton(fileName, actionCommand, helpMessage);
        this.stateButtons.add(button);
        toolBar.add(button);
    }

    /**
     * Méthode initialisant tous les menus pour utiliser les fonctions du logiciel (enregistrement, algorithmes ...)
     */
    private void initMenu() {
        super.setJMenuBar(new JMenuBar());

        JMenu file = this.addMenu("File");
        JMenu edition = this.addMenu("Edition");

        JMenu open = new JMenu("Open");
        this.addJMenuItem(open, "from GraphML...", "from GraphML...");
        this.addJMenuItem(open, "from GraphViz...", "from GraphViz...");

        JMenu saveas = new JMenu("Save as ...");
        this.addJMenuItem(saveas, "GraphML...", "GraphML...");
        this.addJMenuItem(saveas, "GraphViz...", "GraphViz...");


        this.addJMenuItem(file, "New", "New");
        file.add(open);

        file.addSeparator();
        this.addJMenuItem(file, "Save", "Save");
        file.add(saveas);

        file.addSeparator();
        this.addJMenuItem(file, "Close", "Close");

        this.addJMenuItem(edition, "Undo", "Undo");
        this.addJMenuItem(edition, "Redo", "Redo");
        this.addJMenuItem(edition, "Copy", "Copy");
        this.addJMenuItem(edition, "Paste", "Paste");

        this.addJMenuItem(edition, "Algorithms", "Algorithms");

        /*
        this.addJMenuItem(algorithm, "Random Positioning");
        this.addJMenuItem(algorithm, "Circular Positioning");
        this.addJMenuItem(algorithm, "Vertex Size Coloring");
        this.addJMenuItem(algorithm, "Vertex Number of Edges Coloring ");*/
    }

    /**
     * Méthode ajoutant un nouveau {@link view.editor.Tab}. On associe à ce dernier un nouveau {@link data.Graph} et un titre (nom).
     */
    public void addNewTab(final Graph graph, String title) {
        if (this.tabs.getTabCount() == 0) {
            hideStartPanel();
        }

        this.tabs.addTab(title, new Tab(graph, title, this.controller));
        int index = this.tabs.indexOfTab(title);
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        JButton close = ButtonFactory.createImageButton("Close", "Close", "core/assets/cross.png", "Fermer le graphe", Color.WHITE, 12);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        titlePanel.add(new JLabel(title), gbc);
        titlePanel.add(Box.createHorizontalStrut(25));
        gbc.gridx++;
        gbc.weightx = 0;

        titlePanel.add(ButtonFactory.createBoxContainer(close, new Color(212, 0, 0), 12), gbc);

        close.addActionListener(new ButtonActionListener(close, null, index));
        this.tabs.setTabComponentAt(index, titlePanel);
        Window.tabIndex++;
    }

    /**
     * Méthode permettant de savoir si le Tab portant le nom en paramètre existe déjà
     * @param title le titre du Tab
     * @return le résultat du test
     */
    public boolean tabExists(String title) {
        return (this.tabs.indexOfTab(title) != -1);
    }

    /**
     * Getter du champs statique donnant le nombre tab créés
     *
     * @return tabIndex
     */
    public int getTabIndex() { return Window.tabIndex; }

    /**
     * Getter du nombre de Tab ouverts
     *
     * @return le nombre de Tab
     */
    public int getTabCount() {
        return this.tabs.getTabCount();
    }

    /**
     * Renvoi du {@link view.editor.Tab} couramment sélectionné
     *
     * @return Le {@link view.editor.Tab} sélectionné
     */
    public Tab getCurrentTab() {
        return (Tab)this.tabs.getComponentAt((this.tabs.getSelectedIndex()));
    }

    /**
     * Renvoi de la {@link view.editor.Sheet} du {@link view.editor.Tab} couramment sélectionné
     *
     * @return la {@link view.editor.Sheet} du {@link view.editor.Tab} couramment sélectionné
     */
    public Sheet getCurrentSheet() {
        return getCurrentTab().getSheet();
    }

    public JViewport getCurrentSheetViewPort() {
        return getCurrentTab().getScrollPane().getViewport();
    }

    /**
     * Méthode retournant l'indice du Tab courant de la Window
     *
     * @return l'indice du Tab
     */
    public int getCurrentTabIndex() {
        return this.tabs.getSelectedIndex();
    }

    /**
     * Méthode pour changer le mode de la fenêtre par rapport à celui du Controller : met à jour les boutons de mode
     *
     * @param state l'état / mode du Controller
     */
    public void setState(State state) {
        for (StateButton button : this.stateButtons) {
            button.setSelected(button.getActionCommand().equals(state.getMode()));
        }
    }

    /**
     * Méthode permettant d'activer ou de désactiver le bouton Annuler
     * @param enable l'état d'activation
     */
    public void setUndoEnable(boolean enable) {
        this.undoButton.setEnabled(enable);
    }

    /**
     * Méthode permettant d'activer ou de désactiver le bouton Rétablir
     * @param enable l'état d'activation
     */
    public void setRedoEnable(boolean enable) {
        this.redoButton.setEnabled(enable);
    }

    /**
     * Getter du JTabbedPane contenant les Tab de la Window
     *
     * @return le JTabbedPane tabs
     */
    public JTabbedPane getTabs() {
        return this.tabs;
    }

    public void openGML(File file) throws Exception{
        if (file != null) {

            GmlFileManager gmlFileManager = new GmlFileManager(null, file);
            gmlFileManager.openGraph();

            this.addNewTab(this.controller.addGraph(gmlFileManager.getGraph()), gmlFileManager.getGraph().getName());
            this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
            this.controller.getGraph(this.getCurrentTabIndex()).setChanged();
        }
    }

    public void openDOT(File file) throws Exception{
        if(file != null){
            DotFileManager dotFileManager = new DotFileManager(null, file);
            dotFileManager.openDotFile();

            this.addNewTab(this.controller.addGraph(dotFileManager.getGraph()), dotFileManager.getGraph().getName());
            this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
            this.controller.getGraph(this.getCurrentTabIndex()).setChanged();
        }
    }

    /**
     *
     */
    public void callAlgoToolBox(){
        AlgorithmSelector al = new AlgorithmSelector();
        String selectedAlgorithm = (String)al.getSelectedAlgorithm();
        controller.applyAlgorithm(selectedAlgorithm);
    }
}
