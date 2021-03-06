package view.editor;

import view.UIElements.CustomScrollBarUI;
import view.editor.display.Sheet;

import javax.swing.*;
import java.awt.*;

/**
 * Classe ScrollPane, panneau conteneur de la feuille de dessin au sein du Tab permettant de gérer des JScrollBar
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
        this.horizontalScrollBar.setUI(new CustomScrollBarUI());
        this.verticalScrollBar.setUI(new CustomScrollBarUI());
    }

    /**
     * Méthode pour affecter à la Sheet une position avec les ScrollBars
     * @param originalPosition la position initiale
     * @param actualPosition la nouvelle position
     */
    public void setScrollPosition(Point originalPosition, Point actualPosition) {
        int x = actualPosition.x - originalPosition.x;
        int y = actualPosition.y - originalPosition.y;

        this.horizontalScrollBar.setValue(this.horizontalScrollBar.getValue() - x);
        this.verticalScrollBar.setValue(this.verticalScrollBar.getValue() - y);
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
            if (this.getViewport().getWidth() < this.sheet.getMaximumSize().width * (this.sheet.getScale() - 0.1)) {
                zoom(positionX, positionY, this.sheet.getScale() - 0.1);
            }
        }
    }

    /**
     * Méthode permettant de gérer le zoom selon une valeur d'échelle
     * @param positionX la position en X du curseur
     * @param positionY la position en Y du curseur
     * @param scale la valeur de l'échelle du zoom
     */
    private void zoom(int positionX, int positionY, double scale) {
        int posX = (int) (this.getViewport().getViewPosition().getX() - this.sheet.getScale()*this.getViewport().getWidth()/2 + scale*this.getViewport().getWidth()/2);
        int posY = (int) (this.getViewport().getViewPosition().getY() - this.sheet.getScale()*this.getViewport().getHeight()/2 + scale*this.getViewport().getHeight()/2);

        this.sheet.setScale(scale);
        this.sheet.setPreferredSize(new Dimension((int)(this.sheet.getScale()*this.sheet.getMaximumSize().width), (int) (this.sheet.getScale() * this.sheet.getMaximumSize().height)));
        this.tab.getMiniMap().updateSelectionZone();
        this.sheet.revalidate();
        this.getHorizontalScrollBar().setValue(posX);
        this.getVerticalScrollBar().setValue(posY);
    }
}
