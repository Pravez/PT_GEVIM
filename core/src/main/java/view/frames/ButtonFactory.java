package view.frames;

import controller.listeners.ButtonActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by aledufrenne on 09/03/15.
 */
public class ButtonFactory {

    public static JButton createStateButton(String fileName, String actionCommand, String helpMessage) {
        JButton button = new JButton();
        Image img = Toolkit.getDefaultToolkit().getImage(fileName);
        button.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBounds(0, 0, 20, 20);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.setFocusable(false);
        button.setActionCommand(actionCommand);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setToolTipText(helpMessage);
        return button;
    }

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
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setFocusable(false);
        return button;
    }

    /**
     * Méthode statique pour créer un bouton avec une image
     *
     * @param buttonName  Le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    Le lien vers l'image du bouton
     * @param helpMessage le message d'aide du bouton
     * @return le JButton créé
     */
    public static JButton createToolBarButtonWithImage(String buttonName, String actionName, String fileName, String helpMessage) {
        Image   img    = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton button = new JButton();
        button.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBounds(0, 0, 20, 20);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorder(null);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setName(buttonName);
        button.setActionCommand(actionName);
        button.setSelected(false);
        button.setToolTipText(helpMessage);
        button.setFocusable(false);
        return button;
    }

    /**
     * Méthode statique pour créer un bouton avec une image (64*64)
     *
     * @param buttonName  le nom du bouton
     * @param actionName l'action associé au buton
     * @param fileName    le nom du fichier de l'image
     * @param helpMessage le message d'aide du bouton
     */
    public static JButton createImageButton(String buttonName, String actionName, String fileName, String helpMessage) {
        //Image   img    = Toolkit.getDefaultToolkit().getImage(fileName);
        JButton button = new JButton();
        //button.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        /** **/
        try {
            BufferedImage bi = ImageIO.read(new File(fileName));
            bi.getGraphics().setColor(Color.WHITE);
            bi.flush();
            button.setIcon(new ImageIcon(bi.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** **/
        button.setBorder(null);
        button.addActionListener(new ButtonActionListener(button, null, 0));
        button.setName(buttonName);
        button.setActionCommand(actionName);
        button.setContentAreaFilled(false);
        button.setToolTipText(helpMessage);
        button.setFocusable(false);
        return button;
    }
}
