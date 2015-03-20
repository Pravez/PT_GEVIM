package view;

import view.UIElements.CustomUIManager;

import javax.swing.*;
import java.awt.*;
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

    @Override
    protected void paintComponent(Graphics g) {
        ButtonModel m = getModel();
        if (m.isPressed() || m.isRollover() || m.isSelected()) {
            g.setColor(CustomUIManager.getButtonHoverBackground());
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        super.paintComponent(g);
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
