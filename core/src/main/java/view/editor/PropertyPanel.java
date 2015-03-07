package view.editor;

import data.*;
import view.Observer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by paubreton on 02/03/15.
 */
public class PropertyPanel extends JTabbedPane implements Observer {

    private Graph graph;
    private Tab tab;
    private JScrollPane vertexScrollPane;
    private JScrollPane edgeScrollPane;
    private JTable vertexPropertyTable;
    private JTable edgePropertyTable;

    private Vector<Vector<String>> vertexDatas;
    private Vector<Vector<String>> edgeDatas;
    private Vector<String> columnVertexNames;
    private Vector<String> columnEdgeNames;

    public PropertyPanel(Tab tab){

        this.tab = tab;
        this.graph = tab.getGraph();
        graph.addObserver(this);

        columnVertexNames = new Vector<>();
        columnVertexNames.add("Label");
        columnVertexNames.add("Size");

        columnEdgeNames = new Vector<>();
        columnEdgeNames.add("Label");
        columnEdgeNames.add("Size");

        vertexDatas = new Vector<>();
        edgeDatas = new Vector<>();


        vertexPropertyTable = new JTable(vertexDatas, columnVertexNames);
        edgePropertyTable = new JTable(edgeDatas, columnEdgeNames);

        vertexPropertyTable.setFillsViewportHeight(true);
        edgePropertyTable.setFillsViewportHeight(true);

        vertexScrollPane = new JScrollPane(vertexPropertyTable);
        vertexScrollPane.setPreferredSize(new Dimension(200, this.getHeight()));
        edgeScrollPane = new JScrollPane(edgePropertyTable);
        edgeScrollPane.setPreferredSize(new Dimension(200, this.getHeight()));

        vertexPropertyTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                modifyGraphVertices(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow());
            }
        });

        edgePropertyTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                modifyGraphEdges(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow());
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

        this.addTab("Vertices", vertexScrollPane);
        this.addTab("Edges", edgeScrollPane);
    }

    private void addVerticesToSelectedElements(int[] selectedRows) {
        tab.clearSelectedElements();
        for(int i : selectedRows){
            tab.selectElement(tab.getVertexes().get(i));
        }
    }

    private void addEdgesToSelectedElements(int[] selectedRows) {
        tab.clearSelectedElements();
        for(int i : selectedRows){
            tab.selectElement(tab.getEdges().get(i));
        }
    }

    private void modifyGraphEdges(int firstRow, int lastRow) {
        for(int i=firstRow;i<=lastRow;i++){
            String newLabel = (String) edgePropertyTable.getModel().getValueAt(i,0);
            int newThickness = Integer.parseInt((String) edgePropertyTable.getModel().getValueAt(i, 1));
            int id = Integer.parseInt(edgeDatas.get(i).get(2));
            (this.graph.getEdges().get(this.graph.getElementIndexWithId(id))).setThickness(newThickness);
            (this.graph.getEdges().get(this.graph.getElementIndexWithId(id))).setLabel(newLabel);
        }

        this.graph.setChanged();
    }

    private void modifyGraphVertices(int firstRow, int lastRow) {
        for(int i=firstRow;i<=lastRow;i++){
            String newLabel = (String) vertexPropertyTable.getModel().getValueAt(i,0);
            int newSize = Integer.parseInt((String) vertexPropertyTable.getModel().getValueAt(i, 1));
            int id = Integer.parseInt(vertexDatas.get(i).get(2));
            (this.graph.getVertexes().get(this.graph.getElementIndexWithId(id))).setSize(newSize);
            (this.graph.getVertexes().get(this.graph.getElementIndexWithId(id))).setLabel(newLabel);
        }

        this.graph.setChanged();
    }

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
                vertexDatas.add(newData);
            } else {
                newData.add(element.getLabel());
                newData.add(String.valueOf(((Edge) element).getThickness()));
                newData.add(String.valueOf(element.getValue()));
                edgeDatas.add(newData);
            }
        }

        vertexPropertyTable.updateUI();
        edgePropertyTable.updateUI();
    }
}
