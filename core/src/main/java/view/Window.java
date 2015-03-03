/**
 * Created by quelemonnier on 26/01/15.
 */

package view;

import controller.*;
import controller.state.State;
import data.Graph;
import files.GmlFileManager;
import undoRedo.UndoPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Alexis Dufrenne
 * Classe Window gérant la fenêtre principale de l'application. C'est la Vue principale du pattern MVC, 
 * elle interagit avec l'utilisateur.
 */
public class Window extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    private Controller controller;
    private JPanel back;
    private UndoPanel undoRedo;

    private JTabbedPane tabs; // ensemble des onglets
    private JToolBar toolBar;

    private JPanel initialButtons;
    //private PropertyPanel properties;
    private JPanel startPanel;

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

        //Partie undo-redo, en cours d'implémentation

        this.setVisible(true);
    }

    private void initStartPanel() {
        this.startPanel = new JPanel();
        this.startPanel.setBackground(null);
        addImageButtonToPanel(this.startPanel, "New", "core/assets/new-big.png", "Nouveau graphe");
        addImageButtonToPanel(this.startPanel, "Open", "core/assets/open-big.png", "Ouvrir un graphe");
        this.back.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;

        this.back.add(this.startPanel, gbc); // gbc is containing the GridBagConstraints
    }

    /**
     * Initialise le {@link javax.swing.JPanel} de fond, le principal qui va stocker l'ensemble des items de Swing.
     */
    private void initBackPanel() {
        this.back = new JPanel();
        this.back.setBackground(Color.GRAY);
        this.back.setLayout(new BorderLayout());
        this.getContentPane().add(this.back);
        this.getContentPane().setBackground(Color.GRAY);
    }

    /**
     * Initialisation de la {@link view.Window} avec son {@link controller.Controller} associé, sa largeur et sa longueur.
     *
     * @param width
     * @param height
     * @param controller
     */
    private void initWindow(int width, int height, Controller controller) {
        this.width = width;
        this.height = height;
        this.controller = controller;

        this.setTitle("PT - Modélisation de graphe");
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.tabs = new JTabbedPane(SwingConstants.TOP);

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
     * @return the JMenuItem created
     */
    private JMenuItem addJMenuItem(JMenu menu, String label) {
        JMenuItem item = new JMenuItem(label);
        menu.add(item);
        item.addActionListener(new MenuActionListener(item, this.controller));
        return item;
    }

    /**
     * Method to init the tool menu bar (undo, redo, copy, paste...)
     */
    private void initToolMenuBar() {
        undoRedo = new UndoPanel();
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        addToolBarButtonWithImage(toolBar, "New", "core/assets/new.png", "Nouveau graphe");
        addToolBarImageButtonWithAction(toolBar, "core/assets/cursor.png", State.Mode.SELECTION.name(), true, "Mode édition");
        addToolBarImageButtonWithAction(toolBar, "core/assets/edit.png", State.Mode.CREATION.name(), false, "Mode création");
        addToolBarImageButtonWithAction(toolBar, "core/assets/zoom.png", State.Mode.ZOOM.name(), false, "Zoom");
        addToolBarButtonWithImage(toolBar, "Zoom", "core/assets/zoom.png", "Zoom");
        addToolBarButtonWithImage(toolBar, "Copy", "core/assets/copy.png", "Copier");
        addToolBarButtonWithImage(toolBar, "Paste", "core/assets/paste.png", "Coller");

        toolBar.add(undoRedo.getUndo());
        toolBar.add(undoRedo.getRedo());
        super.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Méthode privée pour ajouter un bouton avec une image dans un toolBar
     *
     * @param toolBar       le toolBar qui va contenir le bouton
     * @param fileName      le nom du fichier de l'image à charger
     * @param actionCommand la commande qui sera appellée lors du clic sur le bouton
     * @param selectedState l'état du bouton, s'il est sélectionné
     * @param helpMessage   le message d'aide du bouton
     */
    private void addToolBarImageButtonWithAction(JToolBar toolBar, String fileName, String actionCommand, boolean selectedState, String helpMessage) {
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton imageButton = new JButton();
        imageButton.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        imageButton.setBounds(0, 0, 20, 20);
        imageButton.setMargin(new Insets(0, 0, 0, 0));
        imageButton.setBorder(null);
        imageButton.setSelected(selectedState);
        imageButton.setActionCommand(actionCommand);
        imageButton.addActionListener(new ToolBarContextActionListener(this.controller, imageButton));
        imageButton.setToolTipText(helpMessage);
        toolBar.add(imageButton);
    }

    /**
     * Méthode privée pour ajouter un bouton avec du texte dans un toolBar
     *
     * @param toolBar    le toolBar qui va contenir le bouton
     * @param buttonName le nom du bouton
     */
    private void addToolBarButton(JToolBar toolBar, String buttonName) {
        JButton button = new JButton(buttonName);
        button.addActionListener(new ToolBarButtonActionListener(this.controller, button));
        toolBar.add(button);
    }

    /**
     * Méthode privée pour ajouter un bouton n'influant pas sur les états d'édition, avec une image
     *
     * @param toolBar     le JToolBar à qui ajouter le bouton
     * @param buttonName  Le nom du bouton
     * @param fileName    Le lien vers l'image du bouton
     * @param helpMessage le message d'aide du bouton
     */
    private void addToolBarButtonWithImage(JToolBar toolBar, String buttonName, String fileName, String helpMessage) {
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton button = new JButton();
        button.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBounds(0, 0, 20, 20);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.addActionListener(new ToolBarButtonActionListener(this.controller, button));
        button.setName(buttonName);
        button.setSelected(false);
        button.setToolTipText(helpMessage);
        this.toolBar.add(button);
    }

    /**
     * Méthode privée pour ajouter un bouton avec une image à une JPanel
     *
     * @param panel       le Conteneur
     * @param buttonName  le nom du bouton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     */
    private void addImageButtonToPanel(JPanel panel, String buttonName, String fileName, String helpMessage) {
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton button = new JButton();
        button.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        button.setMargin(new Insets(20, 20, 20, 20));
        //button.setBorder(BorderFactory.createEtchedBorder());
        button.addActionListener(new ToolBarButtonActionListener(this.controller, button));
        button.setName(buttonName);
        //button.setBorderPainted(false);
        //button.setContentAreaFilled(false);
        button.setSelected(false);
        button.setToolTipText(helpMessage);
        panel.add(button);
    }

    /**
     * Méthode initialisant tous les menus pour utiliser les fonctions du logiciel (enregistrement, algorithmes ...)
     */
    private void initMenu() {
        super.setJMenuBar(new JMenuBar());

        JMenu file = this.addMenu("File");
        JMenu edition = this.addMenu("Edition");
        JMenu algorithm = this.addMenu("algorithm");

        JMenu save = new JMenu("Save");
        this.addJMenuItem(save, "to GraphML...");
        this.addJMenuItem(save, "to GraphViz...");

        JMenu open = new JMenu("Open");
        this.addJMenuItem(open, "from GraphML...");
        this.addJMenuItem(open, "from GraphViz...");


        this.addJMenuItem(file, "New");
        file.add(open);

        file.addSeparator();
        file.add(save);

        file.addSeparator();
        this.addJMenuItem(file, "Close");

        this.addJMenuItem(edition, "Undo");
        this.addJMenuItem(edition, "Redo");
        this.addJMenuItem(edition, "Copy");
        this.addJMenuItem(edition, "Paste");

        this.addJMenuItem(algorithm, "random positioning");
        this.addJMenuItem(algorithm, "circular positioning");
        this.addJMenuItem(algorithm, "vertex Coloring size");
        this.addJMenuItem(algorithm, "vertex Coloring edge number");
    }

    /**
     * Méthode ajoutant un nouveau {@link view.Tab}. On associe à ce dernier un nouveau {@link data.Graph} et un titre (nom).
     */
    public void addNewTab(Graph graph, String title) {

        if (this.tabs.getTabCount() == 0) {
            this.back.setLayout(new BorderLayout());
            this.back.add(this.tabs, BorderLayout.CENTER);
            this.startPanel.setVisible(false);
        }

        Tab tab = new Tab(graph, this.controller);
        graph.addObserver(tab);

        tab.setName(title);
        tab.setBackground(Color.GRAY);
        tab.setLayout(null);
        tab.add(new JLabel(title));
        tab.setPreferredSize(new Dimension(2000, 2000));
        tab.setSize(500, 500);

        TabMouseListener listener = new TabMouseListener(this.controller, tab, graph);
        tab.addMouseListener(listener);
        tab.addMouseMotionListener(listener);

        JScrollPane pane = new JScrollPane(tab);

        this.tabs.addTab(title, pane);
        undoRedo.setGraph(tab.getGraph());

        /*properties = new PropertyPanel(graph);
        properties.setPreferredSize(new Dimension(200, this.getHeight()));
        this.back.add(properties,BorderLayout.EAST);*/

    }

    /**
     * Getter du nombre de Tab ouverts
     *
     * @return le nombre de Tab
     */
    public int getTabCount() {
        return this.tabs.getTabCount();
    }

    /**
     * Renvoi du {@link view.Tab} couramment sélectionné
     *
     * @return Le {@link view.Tab} sélectionné
     */
    public Tab getCurrentTab() {
        return (Tab) ((JScrollPane) this.tabs.getComponentAt(this.tabs.getSelectedIndex())).getViewport().getComponent(0);
    }

    /**
     * Getter d'undoRedo
     *
     * @return undoredo
     */
    public UndoPanel getUndoRedo() {
        return undoRedo;
    }

    public JViewport getCurrentTabViewPort() {
        return ((JScrollPane) this.tabs.getComponentAt(this.tabs.getSelectedIndex())).getViewport();
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
        for (Component c : this.toolBar.getComponents()) {
            if (((JButton) c).getActionCommand() == state.getMode()) {
                ((JButton) c).setSelected(true);
            } else {
                ((JButton) c).setSelected(false);
            }
        }
    }

    /**
     * Getter du JTabbedPane contenant les Tab de la Window
     *
     * @return le JTabbedPane tabs
     */
    public JTabbedPane getTabs() {
        return this.tabs;
    }

    public void openGML(File file) {
        if (file != null) {
            GmlFileManager gmlFileManager = new GmlFileManager(null, file);
            gmlFileManager.openGraph();

            this.addNewTab(this.controller.addGraph(gmlFileManager.getGraph()), "Tab " + this.getTabCount());
            this.controller.getGraph(this.getCurrentTabIndex()).setChanged();
        }
    }

    public JPanel getStartPanel() {
        return startPanel;
    }
}