package view.UIElements;

import view.editor.*;
import view.editor.ScrollPane;
import view.theme.ImageButton;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * Created by Admin on 19/03/2015.
 */
public class CustomUIManager {

    private static ArrayList<JButton>     buttons    = new ArrayList<>();
    private static ArrayList<JMenuItem>   menuItems  = new ArrayList<>();
    private static ArrayList<JSeparator>  separators = new ArrayList<>();
    private static ArrayList<JMenuBar>    menuBars   = new ArrayList<>();
    private static ArrayList<JToolBar>    toolBars   = new ArrayList<>();
    private static ArrayList<ImageButton> imageButtons = new ArrayList<>();
    private static ArrayList<ImageButton> reverseImageButtons = new ArrayList<>();
    private static ArrayList<JTabbedPane> tabs = new ArrayList<>();
    private static ArrayList<ScrollPane>  scrollPanes = new ArrayList<>();
    private static ArrayList<JPanel>      panels = new ArrayList<>();

    /* Couleur des Tab */
    private static Color                 tabsColor;

    /* Couleur des JPanel */
    private static Color                 panelColor;

    /* Couleur des Images */
    private static Color                 imageColor;
    private static Color                 imageHoverColor;

    /* Couleur des JButton */
    private static Color                 buttonBackground;
    private static Color                 buttonHoverBackground;

    /* Couleur des JMenuBar */
    private static Color                 menuBarBackground;

    /* Couleur des Bordures des JMenuBar & JToolBar */
    private static Color                 topBorderColor;
    private static Color                 bottomBorderColor;

    /* Couleur des JToolBar */
    private static Color                 toolBarBackground;

    /* Couleurs des JMenu */
    private static Color                 menuBackground;
    private static Color                 menuForeground;

    /* Couleurs des JSeparator */
    private static Color                 separatorForeground;

    /* Couleurs pour les JTabbedPane */
    public static Color                  selectedTabColor;
    public static Color                  selectedTabBorderColor;
    public static Color                  deselectedTabColor;
    public static Color                  deselectedTabBorderColor;

    /* Couleurs pour les ScrollBars */
    public static Color                  scrollTrackColor;
    public static Color                  scrollThumbColor;

    /* Couleurs pour le JScrollPane */
    public static Color                  scrollPaneColor;
    public static Color                  scrollPaneBorderColor;

    /* Couleurs pour la MiniMap */
    public static Color                  minimapColor;
    public static Color                  selectionColor;
    public static Color                  selectionBorderColor;

    public static Color getButtonHoverBackground() {
        return CustomUIManager.buttonHoverBackground;
    }

    public static JButton addButton(JButton button) {
        CustomUIManager.buttons.add(button);
        colorButton(button);
        return button;
    }

    public static ScrollPane addScrollPane(ScrollPane scrollPane) {
        CustomUIManager.scrollPanes.add(scrollPane);
        colorScrollPane(scrollPane);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    public static JPanel addPanel(JPanel panel) {
        CustomUIManager.panels.add(panel);
        colorPanel(panel);
        return panel;
    }

    public static JTabbedPane addTab(JTabbedPane tabs) {
        CustomUIManager.tabs.add(tabs);
        tabs.setOpaque(true);
        colorTabs(tabs);
        return tabs;
    }

    public static JPanel addTabComponent(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        JLabel label = new JLabel(title);
        colorLabel(label);
        panel.add(label, gbc);
        return panel;
    }

    public static ImageButton addImageButton(ImageButton button) {
        CustomUIManager.imageButtons.add(button);
        colorImageButton(button);
        return button;
    }

    public static ImageButton addReverseImageButton(ImageButton button) {
        CustomUIManager.reverseImageButtons.add(button);
        colorReverseImageButton(button);
        return button;
    }

    public static JMenuBar addMenuBar(JMenuBar menuBar) {
        CustomUIManager.menuBars.add(menuBar);
        colorMenuBar(menuBar);
        return menuBar;
    }

    public static JToolBar addToolBar(JToolBar toolBar) {
        CustomUIManager.toolBars.add(toolBar);
        toolBar.setOpaque(true);
        colorToolBar(toolBar);
        return toolBar;
    }

    public static JMenuItem addMenuItem(JMenuItem menuItem) {
        CustomUIManager.menuItems.add(menuItem);
        menuItem.setOpaque(true);
        colorMenuItem(menuItem);
        return menuItem;
    }

    public static JSeparator addSeparator(JSeparator separator) {
        CustomUIManager.separators.add(separator);
        separator.setPreferredSize(new Dimension(separator.getWidth(), 2));
        colorSeparator(separator);
        return separator;
    }

    public static void setDarkTheme() {
        CustomUIManager.panelColor               = new Color(64, 64, 64);

        CustomUIManager.tabsColor                = new Color(93, 93, 93);

        CustomUIManager.imageColor               = new Color(230, 230, 230);
        CustomUIManager.imageHoverColor          = new Color(124, 124, 124);

        CustomUIManager.buttonBackground         = new Color(93, 93, 93);
        CustomUIManager.buttonHoverBackground    = new Color(72, 72, 72);

        CustomUIManager.menuBarBackground        = new Color(93, 93, 93);

        CustomUIManager.toolBarBackground        = new Color(93, 93, 93);

        CustomUIManager.topBorderColor           = new Color(16, 16, 16);
        CustomUIManager.bottomBorderColor        = new Color(137, 137, 137);

        CustomUIManager.menuBackground           = new Color(93, 93, 93);
        CustomUIManager.menuForeground           = new Color(232, 232, 232);

        CustomUIManager.separatorForeground      = new Color(47, 47, 47);

        CustomUIManager.selectedTabColor         = new Color(93, 93, 93);
        CustomUIManager.selectedTabBorderColor   = new Color(204, 204, 204);
        CustomUIManager.deselectedTabColor       = new Color(65, 65, 65);
        CustomUIManager.deselectedTabBorderColor = new Color(28, 28, 28);

        CustomUIManager.scrollTrackColor         = new Color(47, 47, 47);
        CustomUIManager.scrollThumbColor         = new Color(111, 111, 111);

        CustomUIManager.scrollPaneColor          = new Color(47, 47, 47);
        CustomUIManager.scrollPaneBorderColor    = new Color(93, 93, 93);

        CustomUIManager.minimapColor             = new Color(59, 59, 59);
        CustomUIManager.selectionColor           = new Color(226, 226, 226);
        CustomUIManager.selectionBorderColor     = new Color(155, 155, 155);

        setCommonProperties();
    }

    public static void setLightTheme() {
        CustomUIManager.panelColor               = new Color(230, 230, 230);

        CustomUIManager.tabsColor                = new Color(230, 230, 230);

        CustomUIManager.imageColor               = new Color(0, 0, 0);
        CustomUIManager.imageHoverColor          = new Color(82, 82, 82);

        CustomUIManager.buttonBackground         = new Color(230, 230, 230);
        CustomUIManager.buttonHoverBackground    = new Color(170, 170, 170);

        CustomUIManager.menuBarBackground        = new Color(230, 230, 230);

        CustomUIManager.toolBarBackground        = new Color(230, 230, 230);

        CustomUIManager.topBorderColor           = new Color(91, 91, 91);
        CustomUIManager.bottomBorderColor        = new Color(230, 230, 230);

        CustomUIManager.menuBackground           = new Color(230, 230, 230);
        CustomUIManager.menuForeground           = new Color(8, 8, 8);

        CustomUIManager.separatorForeground      = new Color(142, 142, 142);

        CustomUIManager.selectedTabColor         = new Color(248, 248, 248);
        CustomUIManager.selectedTabBorderColor   = new Color(137, 137, 137);
        CustomUIManager.deselectedTabColor       = new Color(230, 230, 230);
        CustomUIManager.deselectedTabBorderColor = new Color(137, 137, 137);

        CustomUIManager.scrollTrackColor         = new Color(230, 230, 230);
        CustomUIManager.scrollThumbColor         = new Color(111, 111, 111);

        CustomUIManager.scrollPaneColor          = new Color(230, 230, 230);
        CustomUIManager.scrollPaneBorderColor    = new Color(230, 230, 230);

        CustomUIManager.minimapColor             = new Color(230, 230, 230);
        CustomUIManager.selectionColor           = new Color(172, 211, 244);
        CustomUIManager.selectionBorderColor     = new Color(107, 153, 189);

        setCommonProperties();
    }

    private static void setCommonProperties() {
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(CustomUIManager.separatorForeground, 1));
        UIManager.put("TabbedPane.shadow", CustomUIManager.deselectedTabColor);
        UIManager.put("TabbedPane.darkShadow", CustomUIManager.deselectedTabColor);
        UIManager.put("TabbedPane.highlight", CustomUIManager.selectedTabBorderColor);
        UIManager.put("TabbedPane.light", CustomUIManager.deselectedTabColor);

        for (JButton button : CustomUIManager.buttons) {
            colorButton(button);
        }
        for (JMenuItem menuItem : CustomUIManager.menuItems) {
            colorMenuItem(menuItem);
        }
        for (JSeparator separator : CustomUIManager.separators) {
            colorSeparator(separator);
        }
        for (JMenuBar menuBar : CustomUIManager.menuBars) {
            colorMenuBar(menuBar);
        }
        for (JToolBar toolBar : CustomUIManager.toolBars) {
            colorToolBar(toolBar);
        }
        for (ImageButton image : CustomUIManager.imageButtons) {
            colorImageButton(image);
        }
        for (ImageButton image : CustomUIManager.reverseImageButtons) {
            colorReverseImageButton(image);
        }
        for (JTabbedPane tabbedPane : CustomUIManager.tabs) {
            colorTabs(tabbedPane);
        }
        for (JPanel panel : CustomUIManager.panels) {
            colorPanel(panel);
        }
        for (ScrollPane scrollPane : CustomUIManager.scrollPanes) {
            colorScrollPane(scrollPane);
        }
    }

    private static void colorScrollPane(ScrollPane scrollPane) {
        scrollPane.setBackground(CustomUIManager.scrollPaneColor);
    }

    private static void colorPanel(JPanel panel) {
        panel.setBackground(CustomUIManager.panelColor);
    }

    private static void colorLabel(JLabel label) {
        label.setForeground(CustomUIManager.menuForeground);
    }

    private static void colorTabs(JTabbedPane tabs) {
        tabs.setBackground(CustomUIManager.tabsColor);
    }

    private static ImageButton colorImageButton(ImageButton imageButton) {
        colorButton(imageButton);
        BufferedImage image = imageButton.getImage();
        imageButton.setIcon(getColoredIcon(image, CustomUIManager.imageColor, imageButton.getImageSize()));
        imageButton.setRolloverIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
        imageButton.setPressedIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
        return imageButton;
    }

    private static ImageButton colorReverseImageButton(ImageButton imageButton) {
        colorButton(imageButton);
        BufferedImage image = imageButton.getImage();
        imageButton.setIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
        imageButton.setRolloverIcon(getColoredIcon(image, CustomUIManager.imageColor, imageButton.getImageSize()));
        imageButton.setPressedIcon(getColoredIcon(image, CustomUIManager.imageColor, imageButton.getImageSize()));
        return imageButton;
    }

    private static void colorButton(JButton button) {
        button.setBackground(CustomUIManager.buttonBackground);
    }

    private static void colorMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(CustomUIManager.menuBarBackground);
    }

    private static void colorToolBar(JToolBar toolBar) {
        toolBar.setBackground(CustomUIManager.toolBarBackground);
        toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, CustomUIManager.topBorderColor),
                BorderFactory.createMatteBorder(1, 0, 1, 0, CustomUIManager.bottomBorderColor)));
    }

    private static void colorMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(CustomUIManager.menuBackground);
        menuItem.setForeground(CustomUIManager.menuForeground);
    }

    private static void colorSeparator(JSeparator separator) {
        separator.setForeground(CustomUIManager.separatorForeground);
    }

    /**
     * Méthode statique permettant de retourner une image d'icone dont les couleurs ont été changées selon celle spécifiée en paramètre, de la colorier
     * @param image l'image de départ
     * @param color la couleur à affecter
     * @return la nouvelle icone recoloriée
     */
    private static Icon getColoredIcon(BufferedImage image, Color color, int size) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    /**
     * Méthode statique permettant de retourner une image dont les couleurs ont été changées selon celle spécifiée en paramètre, de la colorier
     * @param image l'image de départ
     * @param color la couleur à affecter
     * @return la nouvelle image recoloriée
     */
    public static BufferedImage getColoredImage(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
}
