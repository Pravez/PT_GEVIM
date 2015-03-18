package view.editor.elements;

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
        g.setColor(new Color(47, 47, 47));
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
        int gap = 5;
        g.setColor(new Color(111, 111, 111));
        if (thumbBounds.width > thumbBounds.height) { // barre horizontale
            g.fillRect(thumbBounds.x, thumbBounds.y + gap, thumbBounds.width, thumbBounds.height - gap);
        } else { // barre verticale
            g.fillRect(thumbBounds.x + gap, thumbBounds.y, thumbBounds.width - gap, thumbBounds.height);
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
