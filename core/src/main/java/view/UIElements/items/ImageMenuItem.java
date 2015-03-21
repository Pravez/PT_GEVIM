package view.UIElements.items;

import view.UIElements.CustomUIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by aledufrenne on 20/03/2015.
 */
public class ImageMenuItem extends ImageButton {
    public ImageMenuItem(BufferedImage image, int size) {
        super(image, size);
    }

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
