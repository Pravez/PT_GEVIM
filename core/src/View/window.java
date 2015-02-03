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

        final JButton clic = new JButton("Nouvel onglet");
        JPanel pan1= new JPanel();
        pan1.setBackground(Color.WHITE);
    /* Bar de menu */
        JMenuBar mainMenu = new JMenuBar();
    /* différents menus */
        JMenu menuFichier = new JMenu("Fichier");
        JMenu menuEdition = new JMenu("Edition");
    /* differents choix de chaque menu */
        JMenuItem demarrer = new JMenuItem("Démarrer");
        JMenuItem fin = new JMenuItem("Fin");
        JMenuItem Nouveau = new JMenuItem("Nouveau");
        JMenuItem Enregistrer = new JMenuItem("Enregistrer");
        JMenuItem Charger = new JMenuItem("Charger");
        JMenuItem annuler = new JMenuItem("Annuler");
        JMenuItem copier = new JMenuItem("Copier");
        JMenuItem coller = new JMenuItem("Coller");




        // Création du premier onglet
        JPanel tmpOnglet = addTabs();

        tabs.addTab(tmpOnglet.getName(), tmpOnglet);


        tabs.setOpaque(true);
        pan1.add(tabs);

        this.getContentPane().add(pan1);
        this.setVisible(true);


		/* Ajout de composants aux conteneurs  */
        clic.setEnabled(false);
        pan1.add(clic);
        this.getContentPane().add(pan1);
				/* Ajouter les choix au menu  */
        menuFichier.add(demarrer);
        menuFichier.add(fin);
        menuFichier.add(Nouveau);
        menuFichier.add(Enregistrer);
        menuFichier.add(Charger);

        menuEdition.add(annuler);
        menuEdition.add(copier);
        menuEdition.add(coller);
				/* Ajouter les menu sur la bar de menu */
        mainMenu.add(menuFichier);
        mainMenu.add(menuEdition);
				/* Ajouter la bar du menu à la frame */
        this.setJMenuBar(mainMenu);


		/* Action réaliser par l'ihm */
				/* clic sur le bouton clic */
        clic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel tmpOnglet = addTabs();
                tabs.addTab(tmpOnglet.getName(), tmpOnglet);

            }
        });
				/* clic sur le choix Démarrer du menu fichier */
        demarrer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clic.setEnabled(true);
            }
        });
				/* clic sur le choix Fin du menu fichier */
        fin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clic.setEnabled(false);
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
        onglet.setPreferredSize(new Dimension(width, height - 200));
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
