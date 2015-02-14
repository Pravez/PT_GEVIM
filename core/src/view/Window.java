/**
 * Created by quelemonnier on 26/01/15.
 */

package view;

import controller.Controller;
import controller.MenuActionListener;
import controller.TabMouseListener;

import javax.swing.*;

import data.Graph;

import java.awt.*;

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
     * Creates a menu with menu items
     * @param menu
     * @param label
     * @return
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
        JToolBar toolBar = new JToolBar();
        JButton undo = new JButton("Undo");
        JButton redo = new JButton("Redo");
        JButton aFaire = new JButton("A FAIRE...");
        toolBar.add(undo);
        toolBar.add(redo);
        toolBar.add(aFaire);
        super.getContentPane().add(toolBar, BorderLayout.NORTH);
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
    public int getCurrentTab(){
    	return tabs.getSelectedIndex();
    }
    
    public Tab getCurrentTab2(){
    	return (Tab)tabs.getComponentAt(tabs.getSelectedIndex());	
    }
}