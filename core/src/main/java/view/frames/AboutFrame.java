package view.frames;

import view.UIElements.CustomUIManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by sowipheur on 21/03/15.
 */
public class AboutFrame extends JDialog {
    private JPanel panel1;
    private JLabel logo;

    private void createUIComponents() {
        System.out.println("hey");
        this.logo = new JLabel("");
        Color logoColor  = new Color(95, 95, 95);
        try {
            this.logo.setIcon(new ImageIcon(CustomUIManager.getColoredImage(ImageIO.read(new File("core/assets/Gevim.png")), logoColor)));
        } catch (IOException e) { }
        this.logo.setBounds(0, 0, 915, 379);
        setVisible(true);
        setPreferredSize(new Dimension(300, 300));
    }
}
