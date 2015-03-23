package view.UIElements.items;

import view.UIElements.CustomUIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe ImageMenuItem héritant de la classe ImageButton pour les JMenuItem
 */
public class ImageMenuItem extends ImageButton {

    /**
     * Constructeur de la classe ImageMenuItem
     * @param image l'image de base de l'ImageMenuItem
     * @param size la taille de l'image de l'ImageMenuItem
     */
    public ImageMenuItem(BufferedImage image, int size) {
        super(image, size);
    }

    /**
     * Override de la méthode paintComponent pour afficher l'image en mode hover selon le CustomUIManager
     */
    @Override
    protected void paintComponent(Graphics g) {
        ButtonModel m = getModel();
        if (m.isPressed() || m.isRollover() || m.isSelected()) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(CustomUIManager.getButtonHoverBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        }
        super.paintComponent(g);
    }
}
