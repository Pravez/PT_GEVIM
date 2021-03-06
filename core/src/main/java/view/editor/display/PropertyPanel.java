package view.editor.display;

import data.*;
import view.UIElements.CustomTabbedPaneUI;
import view.UIElements.CustomUIManager;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Classe d'édition rapide et de vue d'ensemble textuelle du contenu d'un graphe, faite à aprtir d'un {@link javax.swing.JTabbedPane}
 * possédant des {@link javax.swing.JTable}
 */
public class PropertyPanel extends JTabbedPane implements Observer {

    /* Le Graph associé au PropertyPanel */
    private Graph                  graph;
    /* La feuille de desisn */
    private Sheet                  sheet;
    /* Le scrollPane des Vertex */
    private JScrollPane            vertexScrollPane;
    /* Le scrollPane des Edge */
    private JScrollPane            edgeScrollPane;
    /* Le JTable des propriétés des Vertex */
    private JTable                 vertexPropertyTable;
    /* Le JTable des propriétés des Edge */
    private JTable                 edgePropertyTable;

    private Vector<Vector<String>> vertexDatas;
    private Vector<Vector<String>> edgeDatas;
    private Vector<String>         columnVertexNames;
    private Vector<String>         columnEdgeNames;

    /**
     * Constructeur de la classe initialisant les données du {@link javax.swing.JTable} à partir d'un {@link Sheet} et de son {@link data.Graph} associé
     * @param sheet Le {@link Sheet} correspondant
     */
    public PropertyPanel(final Sheet sheet){
        this.setUI(new CustomTabbedPaneUI());

        this.sheet = sheet;
        this.graph = sheet.getGraph();
        graph.addObserver(this);

        columnVertexNames = new Vector<>();
        columnVertexNames.add("Nom");
        columnVertexNames.add("Taille");
        columnVertexNames.add("Indice");

        columnEdgeNames = new Vector<>();
        columnEdgeNames.add("Nom");
        columnEdgeNames.add("Taille");

        vertexDatas = new Vector<>();
        edgeDatas = new Vector<>();


        vertexPropertyTable = new JTable(vertexDatas, columnVertexNames);
        edgePropertyTable = new JTable(edgeDatas, columnEdgeNames);

        vertexPropertyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        edgePropertyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        vertexPropertyTable.setFillsViewportHeight(true);
        edgePropertyTable.setFillsViewportHeight(true);

        vertexPropertyTable.setFocusable(false);
        edgePropertyTable.setFocusable(false);

        vertexScrollPane = new JScrollPane(vertexPropertyTable);
        vertexScrollPane.setPreferredSize(new Dimension(200, this.getHeight()));
        vertexScrollPane.setFocusable(false);
        edgeScrollPane = new JScrollPane(edgePropertyTable);
        edgeScrollPane.setPreferredSize(new Dimension(200, this.getHeight()));
        edgeScrollPane.setFocusable(false);

        vertexPropertyTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                modifyGraphVertices(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow());
                vertexPropertyTable.getSelectionModel().clearSelection();
            }
        });

        edgePropertyTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                modifyGraphEdges(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow());
                edgePropertyTable.getSelectionModel().clearSelection();
            }
        });

        vertexPropertyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addVerticesToSelectedElements(vertexPropertyTable.getSelectedRows());
            }
        });

        edgePropertyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addEdgesToSelectedElements(edgePropertyTable.getSelectedRows());
            }
        });

        addColoredTab("Noeuds", vertexScrollPane);
        addColoredTab("Arêtes", edgeScrollPane);
    }

    /**
     * Méthode permettant d'ajouter un Tab selon le thème du CustomUIManager
     * @param title le titre du Tab
     * @param component le composant du Tab
     */
    private void addColoredTab(String title, JComponent component) {
        super.addTab(title, component);
        super.setTabComponentAt(super.indexOfTab(title), CustomUIManager.addTabComponent(title));
    }

    /**
     * Méthode permettant de sélectionner des {@link view.editor.elements.VertexView} à partir du {@link javax.swing.JTable}
     * @param selectedRows La liste des éléments du {@link javax.swing.JTable} sélectionnés
     */
    private void addVerticesToSelectedElements(int[] selectedRows) {
        sheet.clearSelectedElements();
        for(int i : selectedRows){
            int vertexID = Integer.parseInt(vertexDatas.get(i).get(3));
            sheet.selectElement(sheet.getVertices().get(sheet.getVertexPositionFromID(vertexID)));
        }
    }

    /**
     * Méthode d'ajout de {@link view.editor.elements.EdgeView} à partir du {@link javax.swing.JTable}
     * @param selectedRows La liste des éléments du {@link javax.swing.JTable} sélectionnés
     */
    private void addEdgesToSelectedElements(int[] selectedRows) {
        sheet.clearSelectedElements();
        for(int i : selectedRows){
            int edgeID = Integer.parseInt(edgeDatas.get(i).get(2));
            sheet.selectElement(sheet.getEdges().get(sheet.getEdgePositionFromID(edgeID)));
        }
    }

    /**
     * Méthode de modification directe des {@link view.editor.elements.VertexView} du {@link data.Graph} à partir des données du {@link javax.swing.JTable}
     * @param firstRow La première ligne du {@link javax.swing.JTable} concernée
     * @param lastRow La dernière ligne du {@link javax.swing.JTable} concernée
     */
    private void modifyGraphEdges(int firstRow, int lastRow) {
        for(int i=firstRow;i<=lastRow;i++){
            String newLabel = (String) edgePropertyTable.getModel().getValueAt(i,0);
            int newThickness = Integer.parseInt((String) edgePropertyTable.getModel().getValueAt(i, 1));
            int id = Integer.parseInt(edgeDatas.get(i).get(2));

            this.graph.getFromID(id).setLabel(newLabel);

            if(mustVerifyIntegerDatas(newThickness)){
                int previousThickness = ((Edge)this.graph.getFromID(id)).getThickness();
                edgePropertyTable.getModel().setValueAt(String.valueOf(previousThickness), i, 1);
            }else {
                ((Edge)this.graph.getFromID(id)).setThickness(newThickness);
            }
        }

        this.graph.setChanged();
    }

    /**
     * Méthode de modification directe des {@link view.editor.elements.EdgeView} du {@link data.Graph} à partir des données du {@link javax.swing.JTable}
     * @param firstRow La première ligne du {@link javax.swing.JTable} concernée
     * @param lastRow La dernière ligne du {@link javax.swing.JTable} concernée
     */
    private void modifyGraphVertices(int firstRow, int lastRow) {
        for(int i=firstRow;i<=lastRow;i++){
            String newLabel = (String) vertexPropertyTable.getModel().getValueAt(i,0);
            int newSize = Integer.parseInt((String) vertexPropertyTable.getModel().getValueAt(i, 1));
            int newValue = Integer.parseInt((String)vertexPropertyTable.getModel().getValueAt(i, 2));
            int id = Integer.parseInt(vertexDatas.get(i).get(3));

            this.graph.getFromID(id).setLabel(newLabel);

            if(mustVerifyIntegerDatas(newSize)){
                int previousSize = ((Vertex) this.graph.getFromID(id)).getSize();
                vertexPropertyTable.getModel().setValueAt(String.valueOf(previousSize), i, 1);
            } else {
                ((Vertex)this.graph.getFromID(id)).setSize(newSize);
            }

            if(mustVerifyIntegerDatas(newValue)){
                int previousValue = (this.graph.getFromID(id)).getValue();
                vertexPropertyTable.getModel().setValueAt(String.valueOf(previousValue), i, 2);
            } else {
                (this.graph.getFromID(id)).setValue(newValue);
            }

        }

        this.graph.setChanged();
    }

    /**
     * Méthode d'update overridée de l'interface {@link Observer}. Elle fait un "refresh" des données du {@link javax.swing.JTable}
     * @param observable la classe observée
     * @param object l'objet de la classe observée ayant été modifié
     */
    @Override
    public void update(Observable observable, Object object) {
        vertexDatas.clear();
        edgeDatas.clear();

        for (GraphElement element : (ArrayList<GraphElement>)object) {
            Vector<String> newData = new Vector<>();
            if (element.isVertex()) {
                newData.add(element.getLabel());
                newData.add(String.valueOf(((Vertex) element).getSize()));
                newData.add(String.valueOf(element.getValue()));
                newData.add(String.valueOf(element.getID()));
                vertexDatas.add(newData);
            } else {
                newData.add(element.getLabel());
                newData.add(String.valueOf(((Edge) element).getThickness()));
                newData.add(String.valueOf(element.getID()));
                edgeDatas.add(newData);
            }
        }

        vertexPropertyTable.updateUI();
        edgePropertyTable.updateUI();
    }

    /**
     * Méthode de vérification d'une donnée entière, pour éviter d'avoir des entiers inférieurs à 0.
     * @param data L'entier devant être vérifié
     * @return True s'il doit être vérifié, false sinon
     */
    private boolean mustVerifyIntegerDatas(int data){

        if(data <= 0){
            JOptionPane.showMessageDialog(null, "La donnée doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return true;
        }else{
            return false;
        }
    }
}
