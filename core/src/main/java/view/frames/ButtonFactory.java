package view.frames;

import controller.listeners.ButtonActionListener;
import view.theme.ImageButton;
import view.theme.ImageMenuItem;
import view.theme.StateButton;
import view.UIElements.CustomUIManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by aledufrenne on 09/03/15.
 */
public class ButtonFactory {

    /**
     * Méthode statique pour créer un bouton avec du texte destiné à un ToolBar
     *
     * @param buttonName le nom du bouton
     * @param actionName l'action associé au buton
     * @param helpMessage le message d'aide du bouton
     * @return le JButton créé
     */
    public static JButton createToolBarButton(String buttonName, String actionName, String helpMessage) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(actionName);
        button.setToolTipText(helpMessage);
        button.addActionListener(new ButtonActionListener(button, null, ""));
        button.setFocusable(false);
        return button;
    } /**** A virer ??? *****/

    /**
     * Méthode statique pour créer une JToolBar
     *
     * @return la JToolBar créée
     */
    public static JToolBar createToolBar() {
        JToolBar toolBar = CustomUIManager.addToolBar(new JToolBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(this.getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        return toolBar;
    }

    /**
     * Méthode statique pour créer un JMenuBar
     *
     * @return le JMenuBar créé
     */
    public static JMenuBar createJMenuBar() {
        JMenuBar menuBar = CustomUIManager.addMenuBar(new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(this.getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        menuBar.setBorderPainted(false);
        return menuBar;
    }

    /**
     * Méthode statique pour créer un bouton de menu JMenu
     *
     * @param menuText le texte du bouton
     * @return le JMenu créé
     */
    public static JMenu createJMenu(String menuText) {
        return (JMenu) CustomUIManager.addMenuItem(new JMenu(menuText));
    }

    /**
     * Méthode statique pour créer un item de Menu JMenuItem
     *
     * @param label le texte du bouton
     * @param actionCommand l'action associée au bouton
     * @return
     */
    public static JMenuItem createJMenuItem(String label, String actionCommand) {
        JMenuItem menuItem = CustomUIManager.addMenuItem(new JMenuItem(label));
        menuItem.setActionCommand(actionCommand);
        return menuItem;
    }

    /**
     * Méthode statique pour créer un séparateur dans les menus (popup)
     *
     * @return le JSeparator créé
     */
    public static JSeparator createSeparator() {
        return CustomUIManager.addSeparator(new JSeparator(SwingConstants.HORIZONTAL){
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getForeground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
    }

    /**
     * Méthode statique privée permettant de créer une ImageButton avec les paramètres prédéfinis
     *
     * @param buttonName le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @param tabTitle le titre de l'onglet où l'action du bouton va être lancée
     * @return l'ImageButton créée
     */
    private static ImageButton createSimpleImageButton(String buttonName, String actionName, String fileName, String helpMessage, int size, String tabTitle) {
        ImageButton button = null;
        try {
            button = new ImageButton(ImageIO.read(new File(fileName)), size);
            button.setPreferredSize(new Dimension(size, size));
            setCommonProperties(button, buttonName, actionName, helpMessage, size, tabTitle);
        } catch (IOException ignored) { }
        return button;
    }

    /**
     * Méthode statique permettant de créer un StateButton avec des paramètres prédéfinis
     *
     * @param buttonName le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @return le StateButton créé
     */
    public static StateButton createStateButton(String buttonName, String actionName, String fileName, String helpMessage, int size) {
        StateButton button = null;
        try {
            button = new StateButton(ImageIO.read(new File(fileName)), size);
            button.setPreferredSize(new Dimension(size, size));
            setCommonProperties(button, buttonName, actionName, helpMessage, size, "");
        } catch (IOException ignored) { }
        CustomUIManager.addImageButton(button);
        if (button != null) {
            button.setContentAreaFilled(false);
        }
        return button;
    }

    /**
     * Méthode statique pour créer un bouton avec une image pour les items de menu
     *
     * @param buttonName  le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @return l'ImageMenuItem créé
     */
    public static ImageMenuItem createImageMenuItem(String buttonName, String actionName, String fileName, String helpMessage, int size) {
        ImageMenuItem item = null;
        try {
            item = new ImageMenuItem(ImageIO.read(new File(fileName)), size);
            item.setPreferredSize(new Dimension(size, size));
            setCommonProperties(item, buttonName, actionName, helpMessage, size, "");
        } catch (IOException ignored) { }
        CustomUIManager.addImageButton(item);
        if (item != null) {
            item.setContentAreaFilled(false);
        }
        return item;
    }

    /**
     * Méthode statique pour créer un bouton avec une image sans peindre la zone du bouton
     *
     * @param buttonName  le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @param tabTitle le titre de l'onglet où l'action du bouton va être lancée
     * @return l'ImageButton créée
     */
    public static ImageButton createImageButton(String buttonName, String actionName, String fileName, String helpMessage, int size, String tabTitle) {
        ImageButton button = createSimpleImageButton(buttonName, actionName, fileName, helpMessage, size, tabTitle);
        CustomUIManager.addImageButton(button);
        button.setContentAreaFilled(false);
        return button;
    }

    /**
     * Méthode statique pour créer un bouton avec une image sans peindre la zone du bouton mais en ayant les couleurs inversées
     *
     * @param buttonName  le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @param tabTitle le titre de l'onglet où l'action du bouton va être lancée
     * @return l'ImageButton créée
     */
    public static ImageButton createBasicReverseImageButton(String buttonName, String actionName, String fileName, String helpMessage, int size, String tabTitle) {
        ImageButton button = createSimpleImageButton(buttonName, actionName, fileName, helpMessage, size, tabTitle);
        CustomUIManager.addReverseImageButton(button);
        button.setContentAreaFilled(false);
        return button;
    }

    /**
     * Méthode statique permettant de créer une boite conteneur d'un AbstractButton en ayant une couleur au survol spécifique
     * @param button le bouton qui est contenu dans la boite
     * @param color la couleur de la boite au survol
     * @param size la dimension de la boite
     * @return la boite créée
     */
    public static Box createBoxContainer(AbstractButton button, final Color color, int size) {
        final Box box = new Box(0);
        box.setPreferredSize(new Dimension(size, size));
        box.setBounds(0, 0, size, size);
        box.setBackground(color);
        box.setOpaque(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) { box.setOpaque(true); }
            @Override
            public void mouseExited(MouseEvent mouseEvent) { box.setOpaque(false); }
        });
        box.add(button);
        return box;
    }

    /**
     * Méthode statique privée permettant d'affecter à l'AbstractButton les propriétés par défaut de l'application
     * @param button l'AbstractButton à modifier
     * @param buttonName  le nom du bouton
     * @param actionName l'action associé au buton
     * @param helpMessage le message d'aide du bouton
     * @param size la taille de l'image
     * @param tabTitle le titre de l'onglet où l'action du bouton va être lancée
     */
    private static void setCommonProperties(AbstractButton button, String buttonName, String actionName, String helpMessage, int size, String tabTitle) {
        button.setBounds(0, 0, size, size);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.addActionListener(new ButtonActionListener(button, null, tabTitle));
        button.setName(buttonName);
        button.setActionCommand(actionName);
        button.setSelected(false);
        button.setToolTipText(helpMessage);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }
}
