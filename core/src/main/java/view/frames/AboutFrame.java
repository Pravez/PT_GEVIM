package view.frames;

import view.UIElements.CustomUIManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class AboutFrame extends JDialog {


    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel imgLabel;

    private ImageIcon gevimImage;

    public AboutFrame() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        try {

            Color logoColor  = new Color(95, 95, 95);
            gevimImage = new ImageIcon(CustomUIManager.getColoredImage(ImageIO.read(new File("core/assets/Gevim.png")), logoColor));

            //On reajuste l'image ...!
            Image scaledImage = gevimImage.getImage();
            scaledImage = scaledImage.getScaledInstance(400,160,java.awt.Image.SCALE_SMOOTH);
            gevimImage.setImage(scaledImage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        imgLabel.setIcon(gevimImage);


        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        this.setLocation(screenSize.width/2-this.getPreferredSize().width/2, screenSize.height/2-this.getPreferredSize().height/2);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        dispose();
    }

}
