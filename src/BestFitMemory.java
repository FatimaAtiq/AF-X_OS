//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class BestFitMemory extends JFrame {
    private int[] blocks;
    private int[] processes;
    private JTable table;
    private DefaultTableModel model;

    public BestFitMemory(int[] blocks, int[] processes) {
        this.blocks = blocks;
        this.processes = processes;
        this.setTitle("Best Fit Memory Allocation");
        this.setSize(1000, 600);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        this.setLayout(new BorderLayout());
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1000, 600, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 80, 100));
        String[] columnNames = new String[]{"Process", "Process Size", "Block Allocated", "Block Size", "Fragmentation"};
        this.model = new DefaultTableModel(columnNames, 0) {
            {
                Objects.requireNonNull(BestFitMemory.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.model);
        this.table.setFont(new Font("Segoe UI", 0, 16));
        this.table.setRowHeight(40);
        this.table.setForeground(new Color(255, 255, 255, 250));
        this.table.setBackground(new Color(0, 0, 0, 120));
        this.table.setGridColor(new Color(255, 255, 255, 60));
        this.table.setSelectionBackground(new Color(107, 255, 166, 100));
        this.table.setSelectionForeground(Color.WHITE);
        this.table.setShowGrid(true);
        this.table.getTableHeader().setFont(new Font("Segoe UI", 1, 17));
        this.table.getTableHeader().setForeground(new Color(255, 255, 255, 250));
        this.table.getTableHeader().setBackground(new Color(0, 0, 0, 180));
        this.table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            {
                Objects.requireNonNull(BestFitMemory.this);
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    this.setBackground(new Color(0, 0, 0, 0));
                }

                this.setHorizontalAlignment(0);
                this.setForeground(new Color(255, 255, 255, 250));
                this.setFont(new Font("Segoe UI", 0, 16));
                return c;
            }
        };

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2));
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        tablePanel.add(scrollPane, "Center");
        this.add(tablePanel);
        this.allocate();
        this.setVisible(true);
    }

    private void allocate() {
        this.model.setRowCount(0);
        int[] blockCopy = (int[])this.blocks.clone();
        boolean[] blockUsed = new boolean[this.blocks.length];
        int[] allocation = new int[this.processes.length];
        int[] fragmentation = new int[this.processes.length];

        for(int i = 0; i < this.processes.length; ++i) {
            allocation[i] = -1;
            fragmentation[i] = 0;
            int best = -1;

            for(int j = 0; j < blockCopy.length; ++j) {
                if (!blockUsed[j] && this.processes[i] <= blockCopy[j] && (best == -1 || blockCopy[j] - this.processes[i] < blockCopy[best] - this.processes[i])) {
                    best = j;
                }
            }

            if (best != -1) {
                allocation[i] = best;
                fragmentation[i] = blockCopy[best] - this.processes[i];
                blockUsed[best] = true;
            }
        }

        for(int i = 0; i < this.processes.length; ++i) {
            if (allocation[i] != -1) {
                this.model.addRow(new Object[]{"P" + (i + 1), this.processes[i], "Block " + (allocation[i] + 1), this.blocks[allocation[i]], fragmentation[i]});
            } else {
                this.model.addRow(new Object[]{"P" + (i + 1), this.processes[i], "Not Allocated", "-", "-"});
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int[] blocks = new int[]{100, 500, 200, 300, 600};
            int[] processes = new int[]{212, 417, 112, 426};
            new BestFitMemory(blocks, processes);
        });
    }
}
