/**
 * Created by quelemonnier on 26/01/15.
 */

package View;

import sun.rmi.server.Activation;

import java.awt.*;
import java.lang.*;
import javax.swing.*;
import java.awt.event.*;

public class window extends JFrame {
    private int width;
    private int height;
    private JTabbedPane tabs = new JTabbedPane(SwingConstants.TOP); // ensemble des onglets

    public window(int w, int h) {
    /* Création des composants */
        width = w;
        height = h;
        this.setTitle("PT - Modélisation de graphe");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //final JButton clic = new JButton("Nouvel onglet");
        JPanel pan1= new JPanel();
        pan1.setBackground(Color.WHITE);
    /* Bar de menu */
        JMenuBar mainMenu = new JMenuBar();
    /* différents menus */
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
    /* differents choix de chaque menu */
        JMenuItem New = new JMenuItem("New");
        JMenuItem Save = new JMenuItem("Save");
        JMenuItem Load = new JMenuItem("Load");
        JMenuItem Close = new JMenuItem("Close");
        JMenuItem Undo = new JMenuItem("Undo");
        JMenuItem Redo = new JMenuItem("Redo");
        JMenuItem Copy = new JMenuItem("Copy");
        JMenuItem Paste = new JMenuItem("Paste");




        // Création du premier onglet
        JPanel tmpOnglet = addTabs();

        tabs.addTab(tmpOnglet.getName(), tmpOnglet);


        tabs.setOpaque(true);
        pan1.add(tabs);

        this.getContentPane().add(pan1);
        this.setVisible(true);


		/* Ajout de composants aux conteneurs  */
        this.getContentPane().add(pan1);

				/* Ajouter les choix au menu  */
        fileMenu.add(New);
        fileMenu.add(Save);
        fileMenu.add(Load);
        fileMenu.add(Close);

        editMenu.add(Undo);
        editMenu.add(Redo);
        editMenu.add(Copy);
        editMenu.add(Paste);
				/* Ajouter les menu sur la bar de menu */
        mainMenu.add(fileMenu);
        mainMenu.add(editMenu);
				/* Ajouter la bar du menu à la frame */
        this.setJMenuBar(mainMenu);


		/* Action réaliser par l'ihm */
				/* clic sur le bouton clic */
        New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel tmpOnglet = addTabs();
                tabs.addTab(tmpOnglet.getName(), tmpOnglet);
            }
        });

        Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });



        this.setVisible(true);
    }


    private JPanel addTabs()
    {

        JPanel onglet = new JPanel();
        onglet.setName("Onglet" + tabs.getTabCount());
        JLabel titreOnglet = new JLabel("Onglet" + tabs.getTabCount());
        onglet.add(titreOnglet);
        onglet.setPreferredSize(new Dimension(width, height-100));
        onglet.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        return onglet;

    }
}
