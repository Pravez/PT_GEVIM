package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aledufrenne on 06/03/2015.
 */
public class ScrollPane extends JScrollPane {

    /* Tab, l'onglet contenu dans le ScrollPane */
    private Tab tab;

    /**
     * Constructeur de la classe ScrollPane
     * @param tab l'onglet du ScrollPane
     */
    public ScrollPane(Tab tab) {
        super(tab);
        this.tab = tab;
    }

    /**
     * Méthode permettant de gérer le zoom de la feuille de dessin
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     */
    public void zoomIn(int positionX, int positionY) {
        if (this.tab.getPreferredSize().width <= this.tab.getMaximumSize().width) {
            zoom(positionX, positionY, this.tab.getCurrentScale() + 0.1);
        }
    }

    /**
     * Méthode permettant de gérer le dézoom de la feuille de dessin
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     */
    public void zoomOut(int positionX, int positionY) {
        if (this.getViewport().getWidth() < this.tab.getPreferredSize().width) {
            zoom(positionX, positionY, this.tab.getCurrentScale() - 0.1);
        }
    }

    /**
     * Méthode permettant de gérer le zoom selon une valeur d'échelle
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     * @param scale la valeur de l'échelle du zoom
     */
    private void zoom(int positionX, int positionY, double scale) {
        int origin_x = positionX - this.getViewport().getWidth()/2 < 0 ? 0 : positionX - this.getViewport().getWidth()/2;
        int origin_y = positionY - this.getViewport().getHeight()/2 < 0 ? 0 : positionY - this.getViewport().getHeight()/2;
        int width    = this.tab.getPreferredSize().width;
        int height   = this.tab.getPreferredSize().height;
        this.tab.setCurrentScale(scale);
        this.tab.setPreferredSize(new Dimension((int)(this.tab.getCurrentScale()*this.tab.getMaximumSize().width), (int) (this.tab.getCurrentScale() * this.tab.getMaximumSize().height)));
        this.tab.getMiniMap().setPosition(new Point(origin_x, origin_y), width, height);
        this.tab.getMiniMap().updateSelectionZone();
        this.tab.revalidate();
    }
}
