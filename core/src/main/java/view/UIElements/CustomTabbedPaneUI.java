package view.UIElements;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Created by Admin on 17/03/2015.
 */
public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

    private Color selectColor;
    private Color borderSelectColor;
    private Color deSelectColor;
    private Color borderDeSelectColor;
    //private int inclTab = 4;
    //private int anchoFocoV = inclTab;
    private int anchoFocoH = 4;
    private int anchoCarpetas = 18;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.selectColor         = new Color(93, 93, 93);
        this.borderSelectColor   = new Color(204, 204, 204);
        this.deSelectColor       = new Color(65, 65, 65);
        this.borderDeSelectColor = new Color(28, 28, 28);
        this.tabAreaInsets.right = this.anchoCarpetas;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        super.paintTabArea(g, tabPlacement, selectedIndex);
        /*if (runCount > 1) {
            int lines[] = new int[runCount];
            for (int i = 0; i < runCount; i++) {
                lines[i] = rects[tabRuns[i]].y + (tabPlacement == TOP ? maxTabHeight : 0);
            }
            Arrays.sort(lines);
            if (tabPlacement == TOP) {
                int fila = runCount;
                for (int i = 0; i < lines.length - 1; i++, fila--) {
                    Polygon carp = new Polygon();
                    carp.addPoint(0, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 2, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + 3);
                    if (i < lines.length - 2) {
                        carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i + 1]);
                        carp.addPoint(0, lines[i + 1]);
                    } else {
                        carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + rects[selectedIndex].height);
                        carp.addPoint(0, lines[i] + rects[selectedIndex].height);
                    }
                    carp.addPoint(0, lines[i]);
                    g.setColor(hazAlfa(fila));
                    g.fillPolygon(carp);
                    g.setColor(darkShadow.darker());
                    g.drawPolygon(carp);
                }
            } else {
                int fila = 0;
                for (int i = 0; i < lines.length - 1; i++, fila++) {
                    Polygon carp = new Polygon();
                    carp.addPoint(0, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i + 1] - 3);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 3, lines[i + 1]);
                    carp.addPoint(0, lines[i + 1]);
                    carp.addPoint(0, lines[i]);
                    g.setColor(hazAlfa(fila + 2));
                    g.fillPolygon(carp);
                    g.setColor(darkShadow.darker());
                    g.drawPolygon(carp);
                }
            }
        }
        super.paintTabArea(g, tabPlacement, selectedIndex);*/
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2D = (Graphics2D) g;
        Polygon shape  = new Polygon(new int[]{x, x + w, x + w, x}, new int[]{y, y, y + h, y + h}, 4);
        g2D.setColor(isSelected ? this.selectColor : this.deSelectColor);
        g2D.fill(shape);
        if (this.runCount > 1) {
            g2D.setColor(hazAlfa(getRunForTab(this.tabPane.getTabCount(), tabIndex) - 1));
            g2D.fill(shape);
        }
        g2D.fill(shape);
    }

    /*@Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        g.setFont(font);
        View v = getTextViewForTab(tabIndex);
        if (v != null) {
            // html
            v.paint(g, textRect);
        } else {
            // plain text
            int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
                g.setColor(Color.WHITE);
                //g.setColor(tabPane.getForegroundAt(tabIndex));
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
            } else { // tab disabled
                g.setColor(Color.WHITE);
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
                //g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
            }
        }
    }*/
    /*protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    g.setFont(font);
    View v = getTextViewForTab(tabIndex);
    if (v != null) {
    // html
    v.paint(g, textRect);
    } else {
    // plain text
    int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

    if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
    Color fg = tabPane.getForegroundAt(tabIndex);
    if (isSelected && (fg instanceof UIResource)) {
    Color selectedFG = UIManager.getNewColor("TabbedPane.selectedForeground");
    if (selectedFG != null) {
    fg = selectedFG;
    }
    }
    g.setColor(fg);
    SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());

    } else { // tab disabled
    //PAY ATTENTION TO HERE
    g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
    SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
    g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
    SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex,
    textRect.x - 1, textRect.y + metrics.getAscent() - 1);
    }
    }
    }*/

    /*@Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return 20 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    }*/

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
        g2D.setColor(isSelected ? this.borderSelectColor : this.borderDeSelectColor);
        Path2D p = new Path2D.Double();
        p.moveTo(x, y + h - 2);
        p.lineTo(x, y);
        p.lineTo(x + w, y);
        p.lineTo(x + w, y + h - 2);
        g2D.draw(p);
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        /*g.setColor(Color.RED);
        g.fillRect(0, 0, 50, 10);*/
        super.paintContentBorder(g, tabPlacement, selectedIndex);
    }

    /*@Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        super.paintFocusIndicator();
        if (tabPane.hasFocus() && isSelected) {
            System.out.println("paintFocusIndicator");
            g.setColor(Color.RED);
            //g.setColor(UIManager.getNewColor("ScrollBar.thumbShadow"));
            g.drawPolygon(shape);
        }
    }*/

    protected Color hazAlfa(int fila) {
        int alfa = 0;
        if (fila >= 0) {
            alfa = 50 + (fila > 7 ? 70 : 10 * fila);
        }
        return new Color(0, 0, 0, alfa);
    }
}
