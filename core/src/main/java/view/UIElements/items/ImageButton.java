package view.UIElements.items;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Classe ImageButton héritant de la classe JButton permettant de gérer une BufferedImage
 */
public class ImageButton extends JButton {
    /* La BufferredImage de base de l'ImageButton */
    private BufferedImage image;
    /* La taille de l'image de l'ImageButton */
    private int           size;

    /**
     * Constructeur de la classe ImageButton
     * @param image l'image de l'ImageButton
     * @param size la taille de l'image de l'ImageButton
     */
    public ImageButton(BufferedImage image, int size) {
        super();
        this.image = image;
        this.size  = size;
    }

    /**
     * Getter de l'image de l'ImageButton
     * @return la BufferedImage de l'ImageButton
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Setter de l'image de l'ImageButton
     * @param image la nouvelle BufferedImage de l'ImageButton
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Getter de la taille de l'image de l'ImageButton
     * @return la taille de l'image de l'ImageButton
     */
    public int getImageSize() {
        return size;
    }

    /**
     * Setter de la taille de l'image de l'ImageButton
     * @param size la nouvelle taille de l'image de l'ImageButton
     */
    public void setImageSize(int size) {
        this.size = size;
    }
}
