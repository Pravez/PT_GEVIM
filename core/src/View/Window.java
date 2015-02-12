/**
 * Created by quelemonnier on 26/01/15.
 */

package View;

import controller.Controller;
import controller.MenuActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Alexis Dufrenne
 * Class window handling the main frame. It is the view from the MVC pattern. Window interacts
 * with the user.
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private int               width;
    private int               height;
    private Controller        controller;
    private JPanel            back;
    private JTabbedPane       tabs; // ensemble des onglets
    private JPopupMenu        contextMenu; //Menu contextuel au clic droit
    
    public Window(int w, int h, Controller controller) {
        initWindow(w, h, controller);
        initMenu();
        initBackPanel();
        initContextMenu();

        //addNewTab(); // Création du premier onglet
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

    private void initContextMenu(){
        contextMenu = new JPopupMenu();

        contextMenu.add("Edit");
        contextMenu.add("Delete");
    }

    /**
     * Adds a new tab to the current JPanel. The tab is another JPanel
     */
    public void addNewTab() {
        Tab tab = new Tab(controller.addNewGraph());
        String title = "Tab " + tabs.getTabCount();
        
        title = JOptionPane.showInputDialog("Saisissez le nom du nouveau graphe :", title);

    	tab.setName(title);
    	tab.setBackground(Color.WHITE);
        tab.add(new JLabel(title));

        tab.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                switch(mouseEvent.getButton()) {
                    case MouseEvent.BUTTON1: // Clic gauche
                        controller.addVertex(controller.getCurrentGraph(), mouseEvent.getX(), mouseEvent.getY());
                        repaint();
                        break;

                    case MouseEvent.BUTTON3: // clic droit
                        if(((Tab)tabs.getSelectedComponent()).onVertex(mouseEvent) != null) {
                            contextMenu.show(tabs.getSelectedComponent(), mouseEvent.getX(), mouseEvent.getY());
                        }
                        break;
                }
            }

            public void mousePressed(MouseEvent mouseEvent) {
            }

            public void mouseReleased(MouseEvent mouseEvent) {
            }

            public void mouseEntered(MouseEvent mouseEvent) {
            }

            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

    	this.tabs.addTab(title, tab);
    }

    public int getCurrentTab(){
        return tabs.getSelectedIndex();
    }

}
