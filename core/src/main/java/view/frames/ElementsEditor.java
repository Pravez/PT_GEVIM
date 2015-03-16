package view.frames;

import data.Edge;
import data.Vertex;
import view.editor.elements.ElementView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ElementsEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField elementsSize;
    private JPanel elementsColor;
    private JTextField elementsName;
    private JCheckBox validNames;
    private JCheckBox validSize;
    private JCheckBox validColor;


    private boolean alreadyValidated;

    private ArrayList<ElementView> elements;

    public ElementsEditor(ArrayList<ElementView> elements) {

        this.setTitle("Editeur d'elements");
        this.elements = elements;
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
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


        validNames.setSelected(true);
        validColor.setSelected(true);
        validSize.setSelected(true);

        elementsColor.setBackground(Color.black);
        elementsSize.setText("15");
        elementsName.setText("element");
        alreadyValidated = false;

        elementsColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(elementsColor);
            }
        });

        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        if(!verifyModifications()) {
            if (!alreadyValidated && Integer.parseInt(this.elementsSize.getText()) < 5) {
                JOptionPane.showMessageDialog(this, "Attention vous editez des noeuds, il se pourrait qu'ils soient trop petits.", "Information", JOptionPane.INFORMATION_MESSAGE);
                alreadyValidated = true;
            } else {
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }

    private void onColor(JPanel j){
        ColorChooser cc = new ColorChooser(j.getBackground());
        j.setBackground(cc.getColor());
    }

    private boolean verifyModifications(){
        boolean mustBeVerified = false;

        for(ElementView ev : elements){
            if(validNames.isSelected()) {
                ev.getGraphElement().setLabel(this.elementsName.getText());
            }
            if(validColor.isSelected()) {
                if(ev.getGraphElement().isVertex())
                    ev.getGraphElement().setColor(this.elementsColor.getBackground());
            }
        }

        if(Integer.parseInt(this.elementsSize.getText())<=0 && validSize.isSelected()){
            JOptionPane.showMessageDialog(this, "La taille doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
            this.elementsSize.setText("15");
            mustBeVerified = true;
        }

        if(!mustBeVerified && validSize.isSelected()){
            for(ElementView ev : elements){
                if(ev.getGraphElement().isVertex()){
                    ((Vertex)ev.getGraphElement()).setSize(Integer.parseInt(this.elementsSize.getText()));
                }else{
                    ((Edge)ev.getGraphElement()).setThickness(Integer.parseInt(this.elementsSize.getText()));
                }
            }
        }

        return mustBeVerified;
    }

    public ArrayList<ElementView> getElements(){
        return this.elements;
    }


    public boolean getColorWasModified () {return validColor.isSelected();}
    public boolean getNameswWereModified () {return validNames.isSelected();}
    public boolean getSizeWasModified () {return validSize.isSelected();}
    public int getNewSize () {return Integer.parseInt(elementsSize.getText());}
    public Color getNewColor () {return elementsColor.getBackground();}
    public String getNewName(){return elementsName.getText();}
    
}
