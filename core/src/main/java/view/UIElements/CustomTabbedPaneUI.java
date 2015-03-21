package view.UIElements;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Created by Admin on 17/03/2015.
 */
public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

    private int anchoFocoH = 4;
    private int anchoCarpetas = 18;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.tabAreaInsets.right = this.anchoCarpetas;
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2D = (Graphics2D) g;
        Polygon shape  = new Polygon(new int[]{x, x + w, x + w, x}, new int[]{y, y, y + h, y + h}, 4);
        g2D.setColor(isSelected ? CustomUIManager.selectedTabColor : CustomUIManager.deselectedTabColor);
        g2D.fill(shape);
        if (this.runCount > 1) {
            g2D.setColor(hazAlfa(getRunForTab(this.tabPane.getTabCount(), tabIndex) - 1));
            g2D.fill(shape);
        }
        g2D.fill(shape);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        if (tabPlacement == LEFT || tabPlacement == RIGHT) {
            return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        } else {
            return anchoFocoH + super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(isSelected ? CustomUIManager.selectedTabBorderColor : CustomUIManager.deselectedTabBorderColor);
        Path2D p = new Path2D.Double();
        p.moveTo(x, y + h - 1);
        p.lineTo(x, y);
        p.lineTo(x + w, y);
        p.lineTo(x + w, y + h - 1);
        g2D.draw(p);
    }

    protected Color hazAlfa(int fila) {
        int alfa = 0;
        if (fila >= 0) {
            alfa = 50 + (fila > 7 ? 70 : 10 * fila);
        }
        return new Color(0, 0, 0, alfa);
    }
}