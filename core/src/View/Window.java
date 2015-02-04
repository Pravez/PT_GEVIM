/**
 * Created by quelemonnier on 26/01/15.
 */

package View;

import java.awt.*;

import javax.swing.*;

import controller.Controller;
import controller.MenuActionListener;

import java.awt.event.*;

public class Window extends JFrame {
    private int width;
    private int height;
    private Controller controller;
    private JTabbedPane tabs = new JTabbedPane(SwingConstants.TOP); // ensemble des onglets
    
    public Window(int w, int h, Controller controller) {
        initWindow(w, h, controller);
        initMenu();
        initBackPanel();

        addNewTab(); // Création du premier onglet
        tabs.setOpaque(true);

        this.setVisible(true);
    }
    
    private void initBackPanel() {
    	JPanel back = new JPanel();
    	back.setBackground(Color.WHITE);
    	back.add(this.tabs);
    	this.getContentPane().add(back);
    }
    
    private void initWindow(int width, int height, Controller controller) {
    	this.width = width;
        this.height = height;
        this.controller = controller;
        this.setTitle("PT - Modélisation de graphe");
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
	private JMenu addMenu(String menuText) {
		JMenu menu = new JMenu(menuText);
		super.getJMenuBar().add(menu);
		return menu;
	}
   
    private JMenuItem addJMenuItem(JMenu menu, String label) {
    	JMenuItem item = new JMenuItem(label);
    	menu.add(item);
    	item.addActionListener(new MenuActionListener(item, this.controller));
    	return item;
    }
    
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
    
    public void addNewTab() {
    	JPanel tab = new JPanel();
    	String title = "Tab " + tabs.getTabCount();
    	tab.setName(title);
    	JLabel tabTitle = new JLabel(title);
        tab.add(tabTitle);
        tab.setPreferredSize(new Dimension(this.width, this.height-100));
        tab.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
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
}
