package view;

import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class AlgorithmSelector extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList algorithms;

    public AlgorithmSelector() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Vector<String> algoNames = new Vector<>();
        algoNames.add("Random Positioning");
        algoNames.add("Circular Positioning");
        algoNames.add("Vertex Coloring");
        algorithms.setListData(algoNames);

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
        algorithms.addComponentListener(new ComponentAdapter() {
        });
        algorithms.addFocusListener(new FocusAdapter() {
        });
        algorithms.addMouseListener(new MouseAdapter() {
        });
    }

    private void onOK() {

        dispose();
    }

    public Object getSelectedAlgorithm(){
        return algorithms.getSelectedValue();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
