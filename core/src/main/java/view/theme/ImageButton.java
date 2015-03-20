package view.theme;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by aledufrenne on 20/03/15.
 */
public class ImageButton extends JButton {
    private BufferedImage image;
    private int           size;

    public ImageButton(BufferedImage image, int size) {
        super();
        this.image = image;
        this.size  = size;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getImageSize() {
        return size;
    }

    public void setImageSize(int size) {
        this.size = size;
    }
}
