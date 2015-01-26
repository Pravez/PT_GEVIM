/**
 * Created by quelemonnier on 26/01/15.
 */

package View;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class window {
    private int width;
    private int height;
    private JFrame mainWindow;


    public window(int w, int h) {
        /* Création des composants */
        mainWindow = new JFrame("PT-Modélisation de graphe");










    }

    final JButton clic = new JButton("Cliquer");
    JPanel panel1= new JPanel();
    /* Bar de menu */
    JMenuBar menu_bar1 = new JMenuBar();
    /* différents menus */
    JMenu menu1 = new JMenu("Fichier");
    JMenu menu2 = new JMenu("Edition");
    /* differents choix de chaque menu */
    JMenuItem demarrer = new JMenuItem("Démarrer");
    JMenuItem fin = new JMenuItem("Fin");
    JMenuItem annuler = new JMenuItem("Annuler");
    JMenuItem copier = new JMenuItem("Copier");
    JMenuItem coller = new JMenuItem("Coller");

		/* Ajout de composants aux conteneurs  */
    clic.setEnabled(false);
    panel1.add(clic);
    frame1.getContentPane().add(panel1,"South");
				/* Ajouter les choix au menu  */
    menu1.add(demarrer);
    menu1.add(fin);
    menu2.add(annuler);
    menu2.add(copier);
    menu2.add(coller);
				/* Ajouter les menu sur la bar de menu */
    menu_bar1.add(menu1);
    menu_bar1.add(menu2);
				/* Ajouter la bar du menu à la frame */
    frame1.setJMenuBar(menu_bar1);

		/* Action réaliser par l'ihm */
				/* clic sur le bouton clic */
    clic.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("1 clic");
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

    frame1.setSize(200,200);
    frame1.show();
}
}

}
