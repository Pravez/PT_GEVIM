package view.frames;

import data.Vertex;
import view.UIElements.CustomUIManager;
import view.editor.display.Sheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    private int          edgeThickness;
    private int          vertexSize;
    private Color        edgeColor;
    private Color        vertexColor;
    private Color        elementHoverColor;
    private Vertex.Shape vertexShape;

    private boolean      cancelled;

    private Sheet        sheet;

    public SheetPropertiesEditor(Sheet sheet) {

        this.sheet = sheet;

        initComponents();

        this.setTitle("Proprietes par defaut");
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(sheet.getParent());
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

    private void initComponents() {
        cancelled = false;

        this.vertexColor       = this.sheet.getDefaultVerticesColor();
        this.vertexShape       = this.sheet.getDefaultVerticesShape();
        this.vertexSize        = this.sheet.getDefaultVerticesSize();
        this.edgeColor         = this.sheet.getDefaultEdgesColor();
        this.edgeThickness     = this.sheet.getDefaultEdgesThickness();
        this.elementHoverColor = CustomUIManager.getHoverColor();

        this.verticesDefaultColor.setBackground(this.vertexColor);
        this.edgesDefaultColor.setBackground(this.edgeColor);
        this.elementDefaultHoverColor.setBackground(this.elementHoverColor);

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

        this.edgesDefaultColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(edgesDefaultColor);
            }
        });

        this.verticesDefaultColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(verticesDefaultColor);
            }
        });

        this.elementDefaultHoverColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(elementDefaultHoverColor);
            }
        });
    }

    private void onOK() {
        if(!validateChanges()) {
            dispose();
        }
    }

    private void onCancel() {
        cancelled = true;
        dispose();
    }

    /**
     * Permet d'ouvrir une nouvelle fenêtre d'édition de couleurs.
     */
    private void onColor(JPanel j){
        ColorChooser cc = new ColorChooser(j.getBackground());
        j.setBackground(cc.getColor());
    }

    public boolean validateChanges(){

        boolean mustBeVerified = false;

        this.edgeColor         = edgesDefaultColor.getBackground();
        this.vertexColor       = verticesDefaultColor.getBackground();
        this.elementHoverColor = elementDefaultHoverColor.getBackground();

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
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(this, "Merci de rentrer des valeurs entieres.", "Attention", JOptionPane.ERROR_MESSAGE);
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

    public boolean isCancelled() {
        return cancelled;
    }
}
