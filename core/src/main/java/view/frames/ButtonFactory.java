package view.frames;

import controller.listeners.ButtonActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
        button.addActionListener(new ButtonActionListener(button, null, ""));
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
        button.addActionListener(new ButtonActionListener(button, null, ""));
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
        button.addActionListener(new ButtonActionListener(button, null, ""));
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
     * @param color la couleur de l'image au survol du bouton
     * @param size la taille de l'image
     * @param tabTitle le titre de l'onglet où l'action du bouton va être lancée
     */
    public static JButton createImageButton(String buttonName, String actionName, String fileName, String helpMessage, Color color, int size, String tabTitle) {
        Image   img    = Toolkit.getDefaultToolkit().getImage(fileName);
        final JButton button = new JButton();
        button.setIcon(new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
        try {
            BufferedImage bi = getColoredImage(ImageIO.read(new File(fileName)), color);//new Color(105, 105, 105));
            button.setRolloverIcon(new ImageIcon(bi.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
            button.setPressedIcon(new ImageIcon(bi.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setPreferredSize(new Dimension(size, size));
        button.setBorder(null);
        button.setBounds(0, 0, size, size);
        button.addActionListener(new ButtonActionListener(button, null, tabTitle));
        button.setName(buttonName);
        button.setActionCommand(actionName);
        button.setContentAreaFilled(false);
        button.setToolTipText(helpMessage);
        button.setFocusable(false);
        return button;
    }

    private static BufferedImage getColoredImage(BufferedImage image, Color color) {
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
}
