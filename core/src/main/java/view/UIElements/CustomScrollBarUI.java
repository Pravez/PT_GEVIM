package view.UIElements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Created by aledufrenne on 18/03/15.
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

    /**
     * Dessiner le cadre de la ScrollBar
     * @param g
     * @param c
     * @param trackBounds
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(CustomUIManager.scrollTrackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    /**
     * Dessiner la barre de la ScrollBar
     * @param g
     * @param c
     * @param thumbBounds
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int gap    = 6;
        int border = 3;
        int x = thumbBounds.x;
        int y = thumbBounds.y;
        int w = thumbBounds.width;
        int h = thumbBounds.height;
        g.setColor(CustomUIManager.scrollThumbColor);
        if (w > h) { // barre horizontale
            h -= 4;
            int[] xpoints = new int[] {x, x + border, x + border, x + w - border, x + w - border, x + w, x + w, x };
            int[] ypoints = new int[] {y + gap + border, y + gap + border, y + gap, y + gap, y + gap + border, y + gap + border, y + gap + h, y + gap + h};
            g.fillPolygon(xpoints, ypoints, xpoints.length);
            g.fillOval(x, y + gap, border*2, border*2);
            g.fillOval(x + w - border*2, y + gap, border*2, border*2);
        } else { // barre verticale
            w--;
            int[] xpoints = new int[] {x + gap, x + gap + border, x + gap + border, x + w, x + w, x + gap + border, x + gap + border, x + gap };
            int[] ypoints = new int[] {y + border, y + border, y, y, y + h, y + h, y + h - border, y + h - border};
            g.fillPolygon(xpoints, ypoints, xpoints.length);
            g.fillOval(x + gap, y, border*2, border*2);
            g.fillOval(x + gap, y + h - border*2, border*2, border*2);
        }
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }
}
