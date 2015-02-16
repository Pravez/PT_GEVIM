/**
 * Created by quelemonnier on 26/01/15.
 */

package view;

import controller.Controller;
import controller.MenuActionListener;
import controller.TabMouseListener;
import controller.ToolBarButtonActionListener;
import controller.ToolBarContextActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import data.Graph;

import java.awt.*;
import java.io.IOException;

/**
 * @author Alexis Dufrenne
 * Classe Window gérant la fenêtre principale de l'application. C'est la Vue principale du pattern MVC, 
 * elle interagit avec l'utilisateur
 */
public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	private int               width;
    private int               height;
    private Controller        controller;
    private JPanel            back;
    private JTabbedPane       tabs; // ensemble des onglets
    private JToolBar          toolBar;
    
    /**
     * Constructeur de la classe Window
     * @param w la largeur de la fenêtre
     * @param h la hauteur de la fenêtre
     * @param controller le Controller qui gère l'application
     */
    public Window(int w, int h, Controller controller) {
        initWindow(w, h, controller);
        initMenu();
        initToolMenuBar();
        initBackPanel();

        tabs.setOpaque(true);
        tabs.setBackground(Color.GRAY);

        this.setVisible(true);
    }

    /**
     * Initialize the background panel : creates a new JPanel
     */
    private void initBackPanel() {
    	back = new JPanel();
    	back.setBackground(Color.GRAY);
    	back.setLayout(new BorderLayout());
    	back.add(this.tabs, BorderLayout.CENTER);
    	this.getContentPane().add(back);
    	this.getContentPane().setBackground(Color.GRAY);
    }

    /**
     * Creates the window associated with a width, a height and a controller
     * @param width
     * @param height
     * @param controller
     */
    private void initWindow(int width, int height, Controller controller) {
    	this.width      = width;
        this.height     = height;
        this.controller = controller;
        this.setTitle("PT - Modélisation de graphe");
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tabs = new JTabbedPane(SwingConstants.TOP);
    }

    /**
     * Method to create a menu from just a string
     * @param menuText
     * @return
     */
	private JMenu addMenu(String menuText) {
		JMenu menu = new JMenu(menuText);
		super.getJMenuBar().add(menu);
		return menu;
	}

    /**
     * Adds to a menu a JMenuItem with a listener
     * @param menu menu to add the item
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
    private void initToolMenuBar(){
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addToolBarImageButton(toolBar, "cursor.png", Controller.State.SELECTION.name(), true);
        addToolBarImageButton(toolBar, "zoom.png", Controller.State.ZOOM_IN.name(), false);
        addToolBarButton(toolBar, "Undo");
        addToolBarButton(toolBar, "Redo");
        addToolBarButton(toolBar, "A FAIRE...");
        super.getContentPane().add(toolBar, BorderLayout.NORTH);
    }
    
    /**
     * Méthode privée pour ajouter un bouton avec une image dans un toolBar
     * @param toolBar le toolBar qui va contenir le bouton
     * @param fileName le nom du fichier de l'image à charger
     * @param selectedState l'état du bouton, s'il est sélectionné
     */
    private void addToolBarImageButton(JToolBar toolBar, String fileName, String actionCommand, boolean selectedState) {
    	Image img = Toolkit.getDefaultToolkit().getImage(fileName);
    	JButton imageButton = new JButton();
    	imageButton.setIcon(new ImageIcon(img.getScaledInstance(20,  20, Image.SCALE_SMOOTH)));
    	imageButton.setBounds(0, 0, 20, 20);
    	imageButton.setMargin(new Insets(0, 0, 0, 0));
    	imageButton.setBorder(null);
    	imageButton.setSelected(selectedState);
    	imageButton.setActionCommand(actionCommand);
    	imageButton.addActionListener(new ToolBarContextActionListener(this.controller, imageButton));
    	toolBar.add(imageButton);
    }
    
    /**
     * Méthode privée pour ajouter un bouton avec du texte dans un toolBar
     * @param toolBar le toolBar qui va contenir le bouton
     * @param buttonName le nom du bouton
     */
    private void addToolBarButton(JToolBar toolBar, String buttonName) {
    	JButton button = new JButton(buttonName);
    	button.addActionListener(new ToolBarButtonActionListener(this.controller, button));
    	toolBar.add(button);
    }

    /**
     * Method to create different menus to navigate
     */
    private void initMenu() {
    	super.setJMenuBar(new JMenuBar());
    	
    	JMenu file = this.addMenu("File");
    	JMenu edition = this.addMenu("Edition");
    	
    	this.addJMenuItem(file, "New");
    	this.addJMenuItem(file, "Save");
    	this.addJMenuItem(file, "Load");
    	this.addJMenuItem(file, "Close");
    	
    	this.addJMenuItem(edition, "Undo");
    	this.addJMenuItem(edition, "Redo");
    	this.addJMenuItem(edition, "Copy");
    	this.addJMenuItem(edition, "Paste");
    }

    /**
     * Adds a new tab to the current JPanel. The tab is another JPanel
     */
    public void addNewTab(Graph graph) {
        Tab tab = new Tab(graph, this.controller);
        graph.addObserver(tab);
        String title = "Tab " + tabs.getTabCount();
        
        title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);

    	tab.setName(title);
    	tab.setBackground(Color.GRAY);
    	tab.setLayout(null);
        tab.add(new JLabel(title));
        
        tab.addMouseListener(new TabMouseListener(this.controller, tab, graph));
               
    	this.tabs.addTab(title, tab);
    }

    /**
     * Method to get the current used tab
     * @return the index of the current tab
     */
    
    public Tab getCurrentTab(){
    	return (Tab)tabs.getComponentAt(tabs.getSelectedIndex());	
    }

    public int getCurrentTabIndex(){ return tabs.getSelectedIndex(); }

	public void setState(Controller.State state, JButton button) {
		System.out.println(state);
		for (Component c : this.toolBar.getComponents()) {
			if (((JButton) c).getActionCommand() == state.name()) {
				((JButton) c).setSelected(true);
				System.out.println("selected !");
			} else {
				((JButton) c).setSelected(false);
			}
		}
	}
}