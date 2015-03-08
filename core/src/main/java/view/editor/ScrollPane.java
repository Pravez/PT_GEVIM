package view.editor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aledufrenne on 06/03/2015.
 */
public class ScrollPane extends JScrollPane {

    /* Tab, l'onglet conteneur du ScrollPane */
    private Tab   tab;
    /* Sheet, la feuille de dessin contenu dans le ScrollPane */
    private Sheet sheet;

    /**
     * Constructeur de la classe ScrollPane
     * @param tab l'onglet du ScrollPane
     */
    public ScrollPane(Tab tab, Sheet sheet) {
        super(sheet);
        this.tab   = tab;
        this.sheet = sheet;
        this.setWheelScrollingEnabled(false); // désactiver le scroll des scrollbars --> pour le zoom qui utilise l'événement mouse wheel
    }

    /**
     * Méthode pour
     * @param originalPosition la position initiale
     * @param actualPosition la nouvelle position
     */
    public void setScrollPosition(Point originalPosition, Point actualPosition) {
        int x = actualPosition.x - originalPosition.x;
        int y = actualPosition.y - originalPosition.y;
        this.horizontalScrollBar.setValue(this.horizontalScrollBar.getValue() - x);
        this.verticalScrollBar.setValue(this.verticalScrollBar.getValue() - y);
        this.sheet.revalidate();
    }

    /**
     * Méthode permettant de gérer le zoom de la feuille de dessin
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     */
    public void zoomIn(int positionX, int positionY) {
        if (this.sheet.getPreferredSize().width <= this.sheet.getMaximumSize().width) {
            zoom(positionX, positionY, this.sheet.getScale() + 0.1);
        }
    }

    /**
     * Méthode permettant de gérer le dézoom de la feuille de dessin
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     */
    public void zoomOut(int positionX, int positionY) {
        if (this.getViewport().getWidth() < this.sheet.getPreferredSize().width) {
            zoom(positionX, positionY, this.sheet.getScale() - 0.1);
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
        int width    = this.sheet.getPreferredSize().width;
        int height   = this.sheet.getPreferredSize().height;
        this.sheet.setScale(scale);
        this.sheet.setPreferredSize(new Dimension((int)(this.sheet.getScale()*this.sheet.getMaximumSize().width), (int) (this.sheet.getScale() * this.sheet.getMaximumSize().height)));
        this.tab.getMiniMap().setPosition(new Point(origin_x, origin_y), width, height);
        this.tab.getMiniMap().updateSelectionZone();
        this.sheet.revalidate();
    }
}