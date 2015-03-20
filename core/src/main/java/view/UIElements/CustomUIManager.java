package view.UIElements;

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

    private static ArrayList<JLabel>      labels    = new ArrayList<>();
    private static ArrayList<JButton>     buttons    = new ArrayList<>();
    private static ArrayList<JMenuItem>   menuItems  = new ArrayList<>();
    private static ArrayList<JSeparator>  separators = new ArrayList<>();
    private static ArrayList<JMenuBar>    menuBars   = new ArrayList<>();
    private static ArrayList<JToolBar>    toolBars   = new ArrayList<>();
    private static ArrayList<ImageButton> imageButtons = new ArrayList<>();
    private static ArrayList<ImageButton> reverseImageButtons = new ArrayList<>();
    private static ArrayList<JTabbedPane> tabs = new ArrayList<>();

    /* Couleur des Tab */
    private static Color                 tabsColor;

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
    private static Color                 separatorBackground;
    private static Color                 separatorForeground;

    public static Color getButtonHoverBackground() {
        return CustomUIManager.buttonHoverBackground;
    }

    public static JButton addButton(JButton button) {
        CustomUIManager.buttons.add(button);
        colorButton(button);
        return button;
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
        colorSeparator(separator);
        return separator;
    }

    public static void setDarkTheme() {
        CustomUIManager.tabsColor        = new Color(93, 93, 93);

        CustomUIManager.imageColor       = new Color(230, 230, 230);
        CustomUIManager.imageHoverColor       = new Color(124, 124, 124);

        CustomUIManager.buttonBackground = new Color(93, 93, 93);
        CustomUIManager.buttonHoverBackground = new Color(72, 72, 72);

        CustomUIManager.menuBarBackground = new Color(93, 93, 93);

        CustomUIManager.toolBarBackground = new Color(93, 93, 93);

        CustomUIManager.topBorderColor    = new Color(16, 16, 16);
        CustomUIManager.bottomBorderColor    = new Color(137, 137, 137);

        CustomUIManager.menuBackground = new Color(93, 93, 93);
        CustomUIManager.menuForeground = new Color(232, 232, 232);

        CustomUIManager.separatorBackground = CustomUIManager.menuBackground;//new Color(66, 66, 66);
        CustomUIManager.separatorForeground = new Color(47, 47, 47);

        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(CustomUIManager.separatorForeground, 1));
    }

    public static void setLightTheme() {
        CustomUIManager.tabsColor        = new Color(93, 93, 93);

        CustomUIManager.imageColor       = new Color(230, 230, 230);
        CustomUIManager.imageHoverColor       = new Color(124, 124, 124);

        CustomUIManager.buttonBackground = new Color(93, 93, 93);
        CustomUIManager.buttonHoverBackground = new Color(72, 72, 72);

        CustomUIManager.menuBarBackground = new Color(93, 93, 93);

        CustomUIManager.toolBarBackground = new Color(93, 93, 93);

        CustomUIManager.topBorderColor    = new Color(16, 16, 16);
        CustomUIManager.bottomBorderColor    = new Color(137, 137, 137);

        CustomUIManager.menuBackground = new Color(93, 93, 93);
        CustomUIManager.menuForeground = new Color(232, 232, 232);

        CustomUIManager.separatorBackground = new Color(0, 0, 0, 0);//new Color(66, 66, 66);
        CustomUIManager.separatorForeground = new Color(47, 47, 47);

        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(CustomUIManager.separatorForeground, 1));
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
        separator.setBackground(CustomUIManager.separatorBackground);
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
