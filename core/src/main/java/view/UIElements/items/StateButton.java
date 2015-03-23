package view.UIElements.items;

import java.awt.image.BufferedImage;

/**
 * Classe StateButton héritant de la classe ImageMenuItem pour la différencier des Boutons normaux
 */
public class StateButton extends ImageMenuItem {

    /**
     * Constructeur de la classe StateButton
     * @param image l'image de base du StateButton
     * @param size la taille de l'image du StateButton
     */
    public StateButton(BufferedImage image, int size) {
        super(image, size);
    }
}
