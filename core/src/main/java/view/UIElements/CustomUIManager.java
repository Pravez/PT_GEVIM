package view.UIElements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Admin on 19/03/2015.
 */
public class CustomUIManager {

    private static ArrayList<JButton>    buttons    = new ArrayList<>();
    private static ArrayList<JMenuItem>  menuItems  = new ArrayList<>();
    private static ArrayList<JSeparator> separators = new ArrayList<>();
    private static ArrayList<JMenuBar>   menuBars   = new ArrayList<>();
    private static ArrayList<JToolBar>   toolBars   = new ArrayList<>();

    /* Couleur des JMenuBar */
    private static Color              menuBarBackground;

    /* Couleur des JToolBar */
    private static Color              toolBarBackground;

    /* Couleurs des JMenu */
    private static Color              menuBackground;
    private static Color              menuForeground;

    /* Couleurs des JSeparator */
    private static Color              separatorBackground;
    private static Color              separatorForeground;


    public static JButton addButton(JButton button) {
        CustomUIManager.buttons.add(button);
        return button;
    }

    public static JMenuBar addMenuBar(JMenuBar menuBar) {
        CustomUIManager.menuBars.add(menuBar);
        colorMenuBar(menuBar);
        return menuBar;
    }

    public static JToolBar addToolBar(JToolBar toolBar) {
        CustomUIManager.toolBars.add(toolBar);
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
        CustomUIManager.menuBarBackground = new Color(93, 93, 93);

        CustomUIManager.toolBarBackground = new Color(93, 93, 93);

        CustomUIManager.menuBackground = new Color(93, 93, 93);
        CustomUIManager.menuForeground = new Color(232, 232, 232);

        CustomUIManager.separatorBackground = new Color(66, 66, 66);
        CustomUIManager.separatorForeground = new Color(47, 47, 47);
    }

    public static void setLightTheme() {
        CustomUIManager.menuBarBackground = new Color(93, 93, 93);

        CustomUIManager.toolBarBackground = new Color(93, 93, 93);

        CustomUIManager.menuBackground = new Color(93, 93, 93);
        CustomUIManager.menuForeground = new Color(232, 232, 232);

        CustomUIManager.separatorBackground = new Color(66, 66, 66);
        CustomUIManager.separatorForeground = new Color(47, 47, 47);
    }

    private static void colorMenuBar(JMenuBar menuBar) {
        System.out.println("Color menu bar in : " + menuBarBackground);
        menuBar.setBackground(CustomUIManager.menuBarBackground);
    }

    private static void colorToolBar(JToolBar toolBar) {
        toolBar.setBackground(CustomUIManager.toolBarBackground);
    }

    private static void colorMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(CustomUIManager.menuBackground);
        menuItem.setForeground(CustomUIManager.menuForeground);
    }

    private static void colorSeparator(JSeparator separator) {
        separator.setBackground(CustomUIManager.separatorBackground);
        separator.setForeground(CustomUIManager.separatorForeground);
    }

}
