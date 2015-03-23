package view;

import controller.Controller;
import controller.listeners.ButtonActionListener;
import controller.listeners.KeyActionListener;
import controller.state.State;
import data.Graph;
import data.Vertex;
import files.dot.DotFileManager;
import files.gml.GmlFileManager;
import undoRedo.snap.SnapVertex;
import view.UIElements.CustomTabbedPaneUI;
import view.UIElements.CustomUIManager;
import view.editor.display.Sheet;
import view.editor.Tab;
import view.frames.AlgorithmSelector;
import view.frames.ButtonFactory;
import view.UIElements.items.StateButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Alexis Dufrenne
 * Classe Window gérant la fenêtre principale de l'application. C'est la Vue principale du pattern MVC,
 * elle interagit avec l'utilisateur.
 */
public class Window extends JFrame {

    private static final long      serialVersionUID = 1L;
    private static int             tabIndex = 1;
    private Controller             controller;
    private JPanel                 back;

    /* L'ensemble des onglets */
    private JTabbedPane            tabs;
    /* La liste des Boutons permettant de changer d'état */
    private ArrayList<StateButton> stateButtons;
    /* Le bouton Undo */
    private JButton                undoButton;
    /* Le bouton Redo */
    private JButton                redoButton;

    /* Le Paneau initial à l'ouverture du programme */
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
        gbc.anchor = GridBagConstraints.NORTH;
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
        this.startPanel.setLayout(new BorderLayout());
        this.startPanel.setBackground(null);
        Color logoColor  = new Color(95, 95, 95);
        Color buttonColor = new Color(205, 205, 205);
        JLabel background = new JLabel("");
        try {
            background.setIcon(new ImageIcon(CustomUIManager.getColoredImage(ImageIO.read(new File("core/assets/Gevim.png")), logoColor)));
        } catch (IOException e) { }
        background.setBounds(0, 0, 915, 379);
        this.startPanel.add(background, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ButtonFactory.createImageButton("New", "New", "core/assets/new-very-big.png", "Nouveau graphe", 128, ""));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(buttonColor);
        separator.setPreferredSize(new Dimension(15, 128));
        buttonPanel.setBackground(null);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(separator);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(ButtonFactory.createImageButton("Open", "Open", "core/assets/open-very-big.png", "Ouvrir un graphe", 128, ""));
        this.startPanel.add(buttonPanel, BorderLayout.CENTER);
        showStartPanel();
    }

    /**
     * Initialise le {@link javax.swing.JPanel} de fond, le principal qui va stocker l'ensemble des items de Swing.
     */
    private void initBackPanel() {
        this.back = CustomUIManager.addPanel(new JPanel());
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

        this.tabs = CustomUIManager.addTab(new JTabbedPane(SwingConstants.TOP));
        this.tabs.setUI(new CustomTabbedPaneUI());
        this.tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Window.this.controller.notifyTabChanged();
            }
        });

        setIconImage(Toolkit.getDefaultToolkit().getImage("core/assets/LogoGevim.png")); //Icone de l'application
    }

    /**
     * Méthode ajoutant à la {@link javax.swing.JMenuBar} principale de la {@link view.Window} un nouveau {@link javax.swing.JMenu}
     *
     * @param menuText Le nom du nouveau menu
     * @return Le menu crée après avoir été ajouté
     */
    private JMenu addMenu(String menuText) {
        JMenu menu = ButtonFactory.createJMenu(menuText);
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
        JMenuItem item = ButtonFactory.createJMenuItem(label, action);
        menu.add(item);
        item.addActionListener(new ButtonActionListener(item, null, ""));
    }

    /**
     * Method to init the tool menu bar (undo, redo, copy, paste...)
     */
    private void initToolMenuBar() {
        int buttonSize = 32;
        JToolBar toolBar  = ButtonFactory.createToolBar();
        toolBar.setFocusable(false);
        this.stateButtons = new ArrayList<>();
        toolBar.setFloatable(false);

        toolBar.add(ButtonFactory.createImageMenuItem("New", "New", "core/assets/new.png", "Nouveau graphe", buttonSize));
        addToolBarStateButton(toolBar, State.Mode.SELECTION.name(), "core/assets/cursor.png", "Mode édition");
        addToolBarStateButton(toolBar, State.Mode.CREATION.name(), "core/assets/edit.png", "Mode création");
        addToolBarStateButton(toolBar, State.Mode.ZOOM.name(), "core/assets/zoom.png",  "Mode Zoom");
        toolBar.add(ButtonFactory.createImageMenuItem("Copy", "Copy", "core/assets/copy.png", "Copier", buttonSize));
        toolBar.add(ButtonFactory.createImageMenuItem("Paste", "Paste", "core/assets/paste.png", "Coller", buttonSize));
        this.undoButton = ButtonFactory.createImageMenuItem("Undo", "Undo", "core/assets/undo.png", "Annuler", buttonSize);
        this.redoButton = ButtonFactory.createImageMenuItem("Redo", "Redo", "core/assets/redo.png", "Rétablir", buttonSize);

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
     * @param actionCommand la commande qui sera appellée lors du clic sur le bouton
     * @param fileName      le nom du fichier de l'image à charger
     * @param helpMessage   le message d'aide du bouton
     */
    private void addToolBarStateButton(JToolBar toolBar, String actionCommand, String fileName, String helpMessage) {
        StateButton button = ButtonFactory.createStateButton(actionCommand, actionCommand, fileName, helpMessage, 32);
        this.stateButtons.add(button);
        toolBar.add(button);
    }

    /**
     * Méthode initialisant tous les menus pour utiliser les fonctions du logiciel (enregistrement, algorithmes ...)
     */
    private void initMenu() {
        super.setJMenuBar(ButtonFactory.createJMenuBar());

        JMenu file        = this.addMenu("Fichier");
        JMenu edition     = this.addMenu("Edition");
        JMenu preferences = this.addMenu("Préférences");

        /* Contenu du Menu file */
        JMenu open        = ButtonFactory.createJMenu("Ouvrir");
        this.addJMenuItem(open, "depuis GraphML...", "from GraphML...");
        this.addJMenuItem(open, "depuis GraphViz...", "from GraphViz...");

        JMenu saveas      = ButtonFactory.createJMenu("Sauvegarder comme ...");
        this.addJMenuItem(saveas, "GraphML...", "GraphML...");
        this.addJMenuItem(saveas, "GraphViz...", "GraphViz...");

        this.addJMenuItem(file, "Nouveau", "New");
        file.add(open);

        file.add(ButtonFactory.createSeparator());
        this.addJMenuItem(file, "Sauvegarder", "Save");
        file.add(saveas);

        file.add(ButtonFactory.createSeparator());
        this.addJMenuItem(file, "Fermer", "Close");

        /* Contenu du Menu edition */
        this.addJMenuItem(edition, "Annuler", "Undo");
        this.addJMenuItem(edition, "Refaire", "Redo");
        edition.add(ButtonFactory.createSeparator());
        this.addJMenuItem(edition, "Copier", "Copy");
        this.addJMenuItem(edition, "Coller", "Paste");
        edition.add(ButtonFactory.createSeparator());
        this.addJMenuItem(edition, "Generer des elements", "generate");
        edition.add(ButtonFactory.createSeparator());
        this.addJMenuItem(edition, "Algorithmes", "Algorithms");

        /* Contenu du Menu preferences */
        this.addJMenuItem(preferences, "Changer de style", "changeStyle");
        this.addJMenuItem(preferences, "Parametres de la feuille", "Properties");
        preferences.add(ButtonFactory.createSeparator());
        this.addJMenuItem(preferences, "A propos", "about");
    }

    /**
     * Méthode ajoutant un nouveau {@link view.editor.Tab}. On associe à ce dernier un nouveau {@link data.Graph} et un titre (nom).
     */
    public void addNewTab(final Graph graph, String title) {
        if (this.tabs.getTabCount() == 0) {
            hideStartPanel();
        }

        //Pour eviter d'avoir plusieurs fois le même nom de Tab
        int i=1;
        for(Tab t : this.getTabsArray()){
            if(t.getSheet().getName().equals(title)){
                i+=1;
            }
        }
        //On rajoute au titre la valeur de i ...
        if(i>1){
            title += " ("+i+")";
        }

        this.tabs.addTab(title, new Tab(graph, title, this.controller));
        JPanel titlePanel = CustomUIManager.addTabComponent("   " + title);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy   = 0;
        gbc.gridx   = 1;
        gbc.weightx = 0;
        titlePanel.add(Box.createHorizontalStrut(25));
        Color crossBackground = new Color(212, 0, 0);
        titlePanel.add(ButtonFactory.createBoxContainer(ButtonFactory.createBasicReverseImageButton("Close", "Close", "core/assets/cross.png", "Fermer le graphe", 12, title), crossBackground, 12), gbc);

        this.tabs.setTabComponentAt(this.tabs.indexOfTab(title), titlePanel);
        Window.tabIndex++;
    }

    /**
     * Méthode permettant de savoir l'index du Tab dont le titre est donné en paramètre
     * @param title le titre de l'onglet
     * @return l'index du Tab dans la liste des Tab
     */
    public int getTabIndexOf(String title) {
        return this.tabs.indexOfTab(title);
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
     * Getter du champs statique donnant le nombre de tab créés
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
     * Méthode permettant de retourner la liste des {@link view.editor.Tab} ouverts
     *
     * @return une ArrayList des {@link view.editor.Tab} ouverts
     */
    public ArrayList<Tab> getTabsArray(){

        ArrayList<Tab> currentTabs = new ArrayList<>();
        for(int i=0;i<getTabCount();i++)
            currentTabs.add((Tab)this.tabs.getComponentAt(i));

        return currentTabs;
    }

    /**
     * Renvoi de la {@link view.editor.display.Sheet} du {@link view.editor.Tab} couramment sélectionné
     *
     * @return la {@link view.editor.display.Sheet} du {@link view.editor.Tab} couramment sélectionné
     */
    public Sheet getCurrentSheet() {
        return getCurrentTab().getSheet();
    }

    /**
     * Renvoi du {@link javax.swing.JViewport} du {@link java.awt.ScrollPane} du {@link view.editor.Tab} couramment sélectionné
     *
     * @return le {@link javax.swing.JViewport} du {@link java.awt.ScrollPane} du {@link view.editor.Tab} couramment sélectionné
     */
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
        for (JButton button : this.stateButtons) {
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

    /**
     * Méthode d'ouverture d'un fichier .graphml appellée par le controller
     * @param file Le fichier contenant le graphe
     * @throws Exception Exception si il survient une erreur lors de la lecture du fichier ou de son ouverture
     */
    public void openGML(File file) throws Exception{
        if (file != null) {

            GmlFileManager gmlFileManager = new GmlFileManager(null, file);
            gmlFileManager.openGraph();

            this.addNewTab(this.controller.addGraph(gmlFileManager.getGraph()), gmlFileManager.getGraph().getName());
            this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
            this.controller.getGraph(this.getCurrentTabIndex()).setChanged();
        }
    }

    /**
     * Méthode pour ouvrir un fichier DOT appellée par le Controller
     * @param file Le fichier à ouvrir
     * @throws Exception Exception lors de la lecture du fichier ou de son ouverture
     */
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
        if(this.tabs.getSelectedIndex() !=-1) {
            AlgorithmSelector al = new AlgorithmSelector(this);
            if (!al.isCancelled()) {
                Object[] selectedAlgorithm = al.getSelectedAlgorithmProperties();
                if (selectedAlgorithm != null) {

                    //Sauvegarde des propriétés de l'ensemble des graphElements

                    if (this.tabs.getSelectedIndex() != -1) {

                        ArrayList<Vertex> elements = getCurrentTab().getGraph().getVertexes();
                        ArrayList<SnapVertex> before = new ArrayList<>();
                        ArrayList<SnapVertex> after = new ArrayList<>();

                        SnapVertex tmp;
                        for (Vertex v : elements) {
                            tmp = new SnapVertex(v, elements.indexOf(v));
                            before.add(tmp);
                        }
                        int vertexWidth = getCurrentSheet().getDefaultVerticesSize();
                        Point     applyPosition = new Point(getCurrentSheetViewPort().getViewPosition().x + vertexWidth/2, getCurrentSheetViewPort().getViewPosition().y + vertexWidth/2);
                        Dimension applyDimension = new Dimension(getCurrentSheet().getMaximumSize().width - vertexWidth, getCurrentSheet().getMaximumSize().height - vertexWidth);
                        this.controller.applyAlgorithm(selectedAlgorithm, applyPosition, applyDimension);
                        elements = getCurrentTab().getGraph().getVertexes();

                        for (Vertex v : elements) {
                            tmp = new SnapVertex(v, elements.indexOf(v));
                            after.add(tmp);
                        }

                        this.getCurrentTab().getUndoRedo().registerAlgoEdit(before, after);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vous devez d'abord ouvrir un graphe.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
