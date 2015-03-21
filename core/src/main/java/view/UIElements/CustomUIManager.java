package view.UIElements;

import view.editor.ScrollPane;
import view.UIElements.items.ImageButton;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * Created by Admin on 19/03/2015.
 */
public class CustomUIManager {

    private static ArrayList<JMenuItem>   menuItems           = new ArrayList<>();
    private static ArrayList<JSeparator>  separators          = new ArrayList<>();
    private static ArrayList<JMenuBar>    menuBars            = new ArrayList<>();
    private static ArrayList<JToolBar>    toolBars            = new ArrayList<>();
    private static ArrayList<ImageButton> imageButtons        = new ArrayList<>();
    private static ArrayList<ImageButton> reverseImageButtons = new ArrayList<>();
    private static ArrayList<JTabbedPane> tabbedPanes         = new ArrayList<>();
    private static ArrayList<ScrollPane>  scrollPanes         = new ArrayList<>();
    private static ArrayList<JPanel>      panels              = new ArrayList<>();
    private static ArrayList<JLabel>      labels              = new ArrayList<>();

    /* Couleur des Tab */
    private static Color                 tabsColor;

    /* Couleur des JPanel */
    private static Color                 panelColor;

    /* Couleur des Images */
    private static Color                 imageColor;
    private static Color                 imageHoverColor;
    private static Color                 reverseImageColor;

    /* Couleur des JButton */
    private static Color                 buttonBackground;
    private static Color                 buttonHoverBackground;

    /* Couleur des JMenuBar */
    private static Color                 menuBarBackground;

    /* Couleur des Bordures des JToolBar */
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

    /**
     * Getter de la couleur au survol des Boutons
     * @return la couleur associée
     */
    public static Color getButtonHoverBackground() {
        return CustomUIManager.buttonHoverBackground;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un ScrollPane pour gérer ses couleurs selon le thème choisi
     * @param scrollPane le ScrollPane
     * @return le ScrollPane modifié
     */
    public static ScrollPane addScrollPane(ScrollPane scrollPane) {
        CustomUIManager.scrollPanes.add(scrollPane);
        colorScrollPane(scrollPane);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JPanel pour gérer ses couleurs selon le thème choisi
     * @param panel le JPanel
     * @return le JPanel modifié
     */
    public static JPanel addPanel(JPanel panel) {
        CustomUIManager.panels.add(panel);
        colorPanel(panel);
        return panel;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JTabbedpane pour gérer ses couleurs selon le thème choisi
     * @param tabbedPane le JTabbedPane
     * @return le JtabbedPane modifié
     */
    public static JTabbedPane addTab(JTabbedPane tabbedPane) {
        CustomUIManager.tabbedPanes.add(tabbedPane);
        tabbedPane.setOpaque(true);
        colorTabbedPane(tabbedPane);
        return tabbedPane;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager le JLabel d'un onglet pour gérer ses couleurs selon le thème choisi
     * @param title le titre de l'onglet et par extension du JLabel
     * @return le JPanel contenant le JLabel
     */
    public static JPanel addTabComponent(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        JLabel label = new JLabel(title);
        CustomUIManager.labels.add(label);
        colorLabel(label);
        panel.add(label, gbc);
        return panel;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager une ImageButton pour gérer ses couleurs selon le thème choisi
     * @param button l'ImageButton
     * @return l'ImageButton modifiée
     */
    public static ImageButton addImageButton(ImageButton button) {
        CustomUIManager.imageButtons.add(button);
        colorImageButton(button);
        return button;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager une ImageButton en mode reverse pour gérer ses couleurs selon le thème choisi
     * @param button l'Imagebutton
     * @return l'Imagebutton modifiée
     */
    public static ImageButton addReverseImageButton(ImageButton button) {
        CustomUIManager.reverseImageButtons.add(button);
        colorReverseImageButton(button);
        return button;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JMenuBar pour gérer ses couleurs selon le thème choisi
     * @param menuBar le JMenuBar
     * @return le JMenuBar modifié
     */
    public static JMenuBar addMenuBar(JMenuBar menuBar) {
        CustomUIManager.menuBars.add(menuBar);
        colorMenuBar(menuBar);
        return menuBar;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JToolBar pour gérer ses couleurs selon le thème choisi
     * @param toolBar le JToolBar
     * @return le JToolBar modifié
     */
    public static JToolBar addToolBar(JToolBar toolBar) {
        CustomUIManager.toolBars.add(toolBar);
        toolBar.setOpaque(true);
        colorToolBar(toolBar);
        return toolBar;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JMenuItem pour gérer ses couleurs selon le thème choisi
     * @param menuItem le JMenuItem
     * @return le JMenuItem modifié
     */
    public static JMenuItem addMenuItem(JMenuItem menuItem) {
        CustomUIManager.menuItems.add(menuItem);
        menuItem.setOpaque(true);
        colorMenuItem(menuItem);
        return menuItem;
    }

    /**
     * Méthode statique permettant d'ajouter au CustomUIManager un JSeparator pour gérer ses couleurs selon le thème choisi
     * @param separator le JSeparator
     * @return le JSeparator modifié
     */
    public static JSeparator addSeparator(JSeparator separator) {
        CustomUIManager.separators.add(separator);
        separator.setPreferredSize(new Dimension(separator.getWidth(), 2));
        colorSeparator(separator);
        return separator;
    }

    /**
     * Méthode statique permettant de passer au thème Dark
     */
    public static void setDarkTheme() {
        CustomUIManager.panelColor               = new Color(64, 64, 64);

        CustomUIManager.tabsColor                = new Color(93, 93, 93);

        CustomUIManager.imageColor               = new Color(230, 230, 230);
        CustomUIManager.imageHoverColor          = new Color(124, 124, 124);
        CustomUIManager.reverseImageColor        = new Color(230, 230, 230);

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

    /**
     * Méthode statique permettant de passer au thème Light
     */
    public static void setLightTheme() {
        CustomUIManager.panelColor               = new Color(230, 230, 230);

        CustomUIManager.tabsColor                = new Color(230, 230, 230);

        CustomUIManager.imageColor               = new Color(0, 0, 0);
        CustomUIManager.imageHoverColor          = new Color(82, 82, 82);
        CustomUIManager.reverseImageColor        = new Color(230, 230, 230);

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

    /**
     * Méthode statique privée permettant de mettre à jour les propriétés communes entre les différents thèmes et de changer les couleurs
     * des éléments en fonction des couleurs prédéfinies dans le thème
     */
    private static void setCommonProperties() {
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(CustomUIManager.separatorForeground, 1));
        UIManager.put("TabbedPane.shadow", CustomUIManager.deselectedTabColor);
        UIManager.put("TabbedPane.darkShadow", CustomUIManager.deselectedTabColor);
        UIManager.put("TabbedPane.highlight", CustomUIManager.selectedTabBorderColor);
        UIManager.put("TabbedPane.light", CustomUIManager.deselectedTabColor);

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
        for (JTabbedPane tabbedPane : CustomUIManager.tabbedPanes) {
            colorTabbedPane(tabbedPane);
        }
        for (JPanel panel : CustomUIManager.panels) {
            colorPanel(panel);
        }
        for (ScrollPane scrollPane : CustomUIManager.scrollPanes) {
            colorScrollPane(scrollPane);
        }
        for (JLabel label : CustomUIManager.labels) {
            colorLabel(label);
        }
    }

    /**
     * Méthode statique permettant d'affecter au ScrollPane les couleurs de l'application
     * @param scrollPane le ScrollPane
     */
    private static void colorScrollPane(ScrollPane scrollPane) {
        scrollPane.setBackground(CustomUIManager.scrollPaneColor);
    }

    /**
     * Méthode statique permettant d'affecter au JPanel les couleurs de l'application
     * @param panel le JPanel
     */
    private static void colorPanel(JPanel panel) {
        panel.setBackground(CustomUIManager.panelColor);
    }

    /**
     * Méthode statique permettant d'affecter au JLabel les couleurs de l'application
     * @param label le JLabel
     */
    private static void colorLabel(JLabel label) {
        label.setForeground(CustomUIManager.menuForeground);
    }

    /**
     * Méthode statique permettant d'affecter au JTabbedPane les couleurs de l'application
     * @param tabbedPane le JTabbedPane
     */
    private static void colorTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(CustomUIManager.tabsColor);
    }

    /**
     * Méthode statique permettant d'affecter à l'ImageButton les couleurs de l'application
     * @param imageButton l'ImageButton
     */
    private static void colorImageButton(ImageButton imageButton) {
        colorButton(imageButton);
        BufferedImage image = imageButton.getImage();
        imageButton.setIcon(getColoredIcon(image, CustomUIManager.imageColor, imageButton.getImageSize()));
        imageButton.setRolloverIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
        imageButton.setPressedIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
    }

    /**
     * Méthode statique permettant d'affecter à l'ImageButton les couleurs de l'application en mode Reverse
     * @param imageButton l'ImageButton
     */
    private static void colorReverseImageButton(ImageButton imageButton) {
        colorButton(imageButton);
        BufferedImage image = imageButton.getImage();
        imageButton.setIcon(getColoredIcon(image, CustomUIManager.imageHoverColor, imageButton.getImageSize()));
        imageButton.setRolloverIcon(getColoredIcon(image, CustomUIManager.reverseImageColor, imageButton.getImageSize()));
        imageButton.setPressedIcon(getColoredIcon(image, CustomUIManager.reverseImageColor, imageButton.getImageSize()));
    }

    /**
     * Méthode statique permettant d'affecter au JButton les couleurs de l'application
     * @param button le JButton
     */
    private static void colorButton(JButton button) {
        button.setBackground(CustomUIManager.buttonBackground);
    }

    /**
     * Méthode statique permettant d'affecter au JMenuBar les couleurs de l'application
     * @param menuBar le JMenuBar
     */
    private static void colorMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(CustomUIManager.menuBarBackground);
    }

    /**
     * Méthode statique permettant d'affecter au JToolBar les couleurs de l'application
     * @param toolBar le JToolBar
     */
    private static void colorToolBar(JToolBar toolBar) {
        toolBar.setBackground(CustomUIManager.toolBarBackground);
        toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, CustomUIManager.topBorderColor),
                BorderFactory.createMatteBorder(1, 0, 1, 0, CustomUIManager.bottomBorderColor)));
    }

    /**
     * Méthode statique permettant d'affecter au JMenuItem les couleurs de l'application
     * @param menuItem le JMenuItem
     */
    private static void colorMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(CustomUIManager.menuBackground);
        menuItem.setForeground(CustomUIManager.menuForeground);
    }

    /**
     * Méthode statique permettant d'affecter au JSeparator les couleurs de l'application
     * @param separator le JSeparator
     */
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
