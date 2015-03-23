package view.frames;

import data.Vertex;
import view.UIElements.CustomUIManager;
import view.editor.display.Sheet;
import view.editor.elements.ElementView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SheetPropertiesEditor extends JDialog {
    private JPanel       contentPane;
    private JButton      buttonOK;
    private JButton      buttonCancel;

    private JTextField   verticesDefaultSize;
    private JTextField   edgesDefaultThickness;
    private JComboBox    verticesDefaultShape;
    private JPanel       verticesDefaultColor;
    private JPanel       edgesDefaultColor;
    private JPanel       elementDefaultHoverColor;
    private JSlider      sheetSize;
    private JCheckBox    showLabels;
    private JPanel       sheetDefaultColor;

    private int          edgeThickness;
    private int          vertexSize;
    private Color        sheetColor;
    private Color        edgeColor;
    private Color        vertexColor;
    private Color        elementHoverColor;
    private Vertex.Shape vertexShape;
    private Dimension    newSize;
    private Dimension    minSize;

    private boolean      cancelled;

    private Sheet        sheet;

    public SheetPropertiesEditor(Sheet sheet) {
        this.sheet = sheet;

        initComponents();

        this.setTitle("Proprietes de la feuille de dessin");
        setContentPane(contentPane);
        setModal(true);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2 - this.getPreferredSize().width / 2, screenSize.height / 2 - this.getPreferredSize().height / 2);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode permettant d'initialiser les différents éléments de la fenêtre de propriétés de la feuille de dessin en fonction de la feuille
     */
    private void initComponents() {
        cancelled = false;

        this.sheetColor          = this.sheet.getDefaultSheetColor();
        this.vertexColor         = this.sheet.getDefaultVerticesColor();
        this.vertexShape         = this.sheet.getDefaultVerticesShape();
        this.vertexSize          = this.sheet.getDefaultVerticesSize();
        this.edgeColor           = this.sheet.getDefaultEdgesColor();
        this.edgeThickness       = this.sheet.getDefaultEdgesThickness();
        this.elementHoverColor   = CustomUIManager.getHoverColor();
        this.newSize             = this.sheet.getPreferredSize();

        Rectangle verticesBounds = this.sheet.getVerticesBounds(this.sheet.getVertices());
        this.minSize             = new Dimension(verticesBounds.x + verticesBounds.width, verticesBounds.y + verticesBounds.height);

        this.showLabels.setSelected(this.sheet.isPaintingLabels());

        this.sheetDefaultColor.setBackground(this.sheetColor);
        this.verticesDefaultColor.setBackground(this.vertexColor);
        this.edgesDefaultColor.setBackground(this.edgeColor);
        this.elementDefaultHoverColor.setBackground(this.elementHoverColor);
        this.sheetSize.setMinimum(this.sheet.getMinimumSize().width);
        this.sheetSize.setPaintTicks(true);
        this.sheetSize.setPaintLabels(true);
        this.sheetSize.setMajorTickSpacing(500);
        this.sheetSize.setMinorTickSpacing(100);
        this.sheetSize.setSnapToTicks(true);
        this.sheetSize.setMaximum(5000);

        this.verticesDefaultSize.setText(String.valueOf(this.vertexSize));
        this.edgesDefaultThickness.setText(String.valueOf(this.edgeThickness));

        switch(this.vertexShape){
            case SQUARE:
                this.verticesDefaultShape.setSelectedIndex(0);
                break;
            case CIRCLE:
                this.verticesDefaultShape.setSelectedIndex(1);
                break;
            case TRIANGLE:
                this.verticesDefaultShape.setSelectedIndex(2);
                break;
            case CROSS:
                this.verticesDefaultShape.setSelectedIndex(3);
                break;
        }

        addColorChangingListener(this.sheetDefaultColor);
        addColorChangingListener(this.edgesDefaultColor);
        addColorChangingListener(this.verticesDefaultColor);
        addColorChangingListener(this.elementDefaultHoverColor);

        this.sheetSize.setValue(newSize.width);
    }

    /**
     * Méthode permettant d'ajouter au JPanel permettant de changer de couleur un Listener qui appelle la méthode onColor lorsque l'on clique
     * sur le JPanel
     * @param colorPanel le JPanel dont on doit rajouter le Listener des événements souris
     */
    private void addColorChangingListener(final JPanel colorPanel) {
        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(colorPanel);
            }
        });
    }

    /**
     * Méthode appelée lorsque l'on clique sur le Bouton OK, avant de valider, vérifie si les valeurs entrées par l'utilisateur sont valables
     */
    private void onOK() {
        if(!validateChanges()) {
            dispose();
        }
    }

    /**
     * Méthode appelée lorsque l'on clique sur le Bouton Annuler
     */
    private void onCancel() {
        cancelled = true;
        dispose();
    }

    /**
     * Permet d'ouvrir une nouvelle fenêtre d'édition de couleurs.
     */
    private void onColor(JPanel colorPanel){
        //ColorChooser colorChooser = new ColorChooser(colorPanel.getBackground());
        colorPanel.setBackground(new ColorChooser(colorPanel.getBackground()).getColor());
    }

    public boolean validateChanges(){
        boolean mustBeVerified = false;

        this.sheetColor        = this.sheetDefaultColor.getBackground();
        this.edgeColor         = this.edgesDefaultColor.getBackground();
        this.vertexColor       = this.verticesDefaultColor.getBackground();
        this.elementHoverColor = this.elementDefaultHoverColor.getBackground();
        this.newSize           = new Dimension(this.sheetSize.getValue(), this.sheetSize.getValue());

        switch((String)this.verticesDefaultShape.getSelectedItem()){
            case "Carré":
                this.vertexShape = Vertex.Shape.SQUARE;
                break;
            case "Cercle":
                this.vertexShape = Vertex.Shape.CIRCLE;
                break;
            case "Croix":
                this.vertexShape = Vertex.Shape.CROSS;
                break;
            case "Triangle":
                this.vertexShape = Vertex.Shape.TRIANGLE;
                break;
        }

        try {
            if (Integer.parseInt(this.verticesDefaultSize.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "La taille ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
                this.verticesDefaultSize.setText(String.valueOf(this.vertexSize));
                mustBeVerified = true;
            } else {
                this.vertexSize = (Integer.parseInt(this.verticesDefaultSize.getText()));
            }

            if (Integer.parseInt(this.edgesDefaultThickness.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "La taille ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
                this.edgesDefaultThickness.setText(String.valueOf(this.edgeThickness));
                mustBeVerified = true;
            } else {
                this.edgeThickness = (Integer.parseInt(this.edgesDefaultThickness.getText()));
            }
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Merci de rentrer des valeurs entieres.", "Attention", JOptionPane.ERROR_MESSAGE);
            mustBeVerified = true;
        }

        if (this.newSize.width < this.minSize.width || this.newSize.height < this.minSize.height) {
            JOptionPane.showMessageDialog(this, "La taille de la feuille de dessin ne peut pas être inférieure à : " + this.minSize.width, "Attention", JOptionPane.ERROR_MESSAGE);
            mustBeVerified = true;
        }

        return mustBeVerified;

    }

    public int getEdgeThickness() {
        return edgeThickness;
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public Color getSheetColor() { return sheetColor; }

    public Color getEdgeColor() {
        return edgeColor;
    }

    public Color getVertexColor() {
        return vertexColor;
    }

    public Color getHoverColor() {
        return elementHoverColor;
    }

    public Vertex.Shape getVertexShape() {
        return vertexShape;
    }

    public Dimension getNewSize() {
        return newSize;
    }

    public boolean isPaintingLabels() { return showLabels.isSelected(); }

    public boolean isCancelled() {
        return cancelled;
    }
}
