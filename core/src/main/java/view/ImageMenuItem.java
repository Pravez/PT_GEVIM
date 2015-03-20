package view;

import view.UIElements.CustomUIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Admin on 20/03/2015.
 */
public class ImageMenuItem extends ImageButton {
    public ImageMenuItem(BufferedImage image, int size) {
        super(image, size);
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
}
