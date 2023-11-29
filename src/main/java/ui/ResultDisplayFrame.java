package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultDisplayFrame extends JFrame {
    private JTable resultTable;

    public ResultDisplayFrame() {
        setTitle("Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayResults(List<String[]> results) {
        String[] columnNames = results.get(0);
        String[][] data = results.subList(1, results.size()).toArray(new String[0][]);
        resultTable.setModel(new DefaultTableModel(data, columnNames));
        pack();
    }
}