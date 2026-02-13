//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class LRUPageReplacement extends JFrame {
    private int numFrames;
    private String[] pageReferences;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea resultArea;
    private LinkedHashSet<Integer> frameSet;
    private List<String[]> executionSteps;
    private JTextField numFramesField;
    private JTextField pageReferencesField;

    public LRUPageReplacement() {
        this.setTitle("LRU Page Replacement Algorithm");
        this.setSize(1100, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1100, 700, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        this.executionSteps = new ArrayList();
        this.frameSet = new LinkedHashSet();
        this.showSetupScreen();
    }

    private void showSetupScreen() {
        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setOpaque(false);
        setupPanel.setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 150));
        JLabel titleLabel = new JLabel("LRU Configuration Setup", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        JLabel framesLabel = new JLabel("Number of Frames:");
        framesLabel.setFont(new Font("Segoe UI", 0, 18));
        framesLabel.setForeground(Color.WHITE);
        formPanel.add(framesLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        this.numFramesField = new JTextField(15);
        this.numFramesField.setFont(new Font("Segoe UI", 0, 16));
        this.numFramesField.setPreferredSize(new Dimension(250, 40));
        formPanel.add(this.numFramesField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        JLabel referencesLabel = new JLabel("Page Reference String:");
        referencesLabel.setFont(new Font("Segoe UI", 0, 18));
        referencesLabel.setForeground(Color.WHITE);
        formPanel.add(referencesLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        this.pageReferencesField = new JTextField(15);
        this.pageReferencesField.setFont(new Font("Segoe UI", 0, 16));
        this.pageReferencesField.setPreferredSize(new Dimension(250, 40));
        formPanel.add(this.pageReferencesField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 15, 15);
        JButton startBtn = this.createStyledButton("Start LRU Simulation", new Color(156, 39, 176));
        startBtn.setPreferredSize(new Dimension(280, 50));
        formPanel.add(startBtn, gbc);
        setupPanel.add(titleLabel, "North");
        setupPanel.add(formPanel, "Center");
        this.getContentPane().removeAll();
        ((JLabel)this.getContentPane()).add(setupPanel);
        this.revalidate();
        this.repaint();
        startBtn.addActionListener((e) -> this.initializeSystem());
    }

    private void initializeSystem() {
        try {
            this.numFrames = Integer.parseInt(this.numFramesField.getText().trim());
            if (this.numFrames <= 0) {
                throw new NumberFormatException();
            }

            String refString = this.pageReferencesField.getText().trim();
            this.pageReferences = refString.split(",");
            if (this.pageReferences.length == 0) {
                this.showError("Please enter page references");
                return;
            }

            for(String ref : this.pageReferences) {
                Integer.parseInt(ref.trim());
            }

            this.executeLRU();
            this.showMainScreen();
        } catch (NumberFormatException var6) {
            this.showError("Please enter valid numbers");
        }

    }

    private void executeLRU() {
        this.executionSteps.clear();
        this.frameSet.clear();
        int pageFaults = 0;
        int pageHits = 0;

        for(String pageStr : this.pageReferences) {
            int page = Integer.parseInt(pageStr.trim());
            String[] step = new String[this.numFrames + 3];
            step[0] = String.valueOf(page);
            if (this.frameSet.contains(page)) {
                this.frameSet.remove(page);
                this.frameSet.add(page);
                step[this.numFrames + 1] = "HIT";
                step[this.numFrames + 2] = "✓";
                ++pageHits;
            } else {
                if (this.frameSet.size() == this.numFrames) {
                    Iterator<Integer> it = this.frameSet.iterator();
                    it.next();
                    it.remove();
                }

                this.frameSet.add(page);
                step[this.numFrames + 1] = "FAULT";
                step[this.numFrames + 2] = "✗";
                ++pageFaults;
            }

            int frameIndex = 0;

            for(Integer p : this.frameSet) {
                step[frameIndex + 1] = String.valueOf(p);
                ++frameIndex;
            }

            for(int i = frameIndex + 1; i <= this.numFrames; ++i) {
                step[i] = "-";
            }

            this.executionSteps.add(step);
        }

    }

    private void showMainScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        JLabel titleLabel = new JLabel("LRU Page Replacement Algorithm", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel infoPanel = this.createInfoPanel();
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        JPanel tablePanel = this.createExecutionTablePanel();
        JPanel resultPanel = this.createResultPanel();
        contentPanel.add(tablePanel, "Center");
        contentPanel.add(resultPanel, "South");
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, "North");
        topPanel.add(infoPanel, "Center");
        mainPanel.add(topPanel, "North");
        mainPanel.add(contentPanel, "Center");
        this.getContentPane().removeAll();
        ((JLabel)this.getContentPane()).add(mainPanel);
        this.revalidate();
        this.repaint();
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(1, 25, 8));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        infoPanel.add(this.createInfoLabel("Frames: " + this.numFrames));
        infoPanel.add(this.createInfoLabel("References: " + this.pageReferences.length));
        int faults = 0;
        int hits = 0;

        for(String[] step : this.executionSteps) {
            if (step[this.numFrames + 1].equals("FAULT")) {
                ++faults;
            } else {
                ++hits;
            }
        }

        infoPanel.add(this.createInfoLabel("Page Faults: " + faults));
        infoPanel.add(this.createInfoLabel("Page Hits: " + hits));
        double hitRatio = (double)hits * (double)100.0F / (double)this.pageReferences.length;
        infoPanel.add(this.createInfoLabel(String.format("Hit Ratio: %.2f%%", hitRatio)));
        return infoPanel;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text, 0);
        label.setFont(new Font("Segoe UI", 1, 14));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 120));
        label.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return label;
    }

    private JPanel createExecutionTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        JLabel tableTitle = new JLabel("Execution Steps", 0);
        tableTitle.setFont(new Font("Segoe UI", 1, 20));
        tableTitle.setForeground(Color.WHITE);
        String[] columnNames = new String[this.numFrames + 3];
        columnNames[0] = "Page";

        for(int i = 0; i < this.numFrames; ++i) {
            columnNames[i + 1] = "F" + i;
        }

        columnNames[this.numFrames + 1] = "Status";
        columnNames[this.numFrames + 2] = "";
        this.model = new DefaultTableModel(columnNames, 0) {
            {
                Objects.requireNonNull(LRUPageReplacement.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.model);
        this.table.setFont(new Font("Segoe UI", 0, 14));
        this.table.setRowHeight(32);
        this.table.setForeground(Color.WHITE);
        this.table.setBackground(new Color(0, 0, 0, 120));
        this.table.setGridColor(new Color(255, 255, 255, 60));
        this.table.setSelectionBackground(new Color(156, 39, 176, 150));
        this.table.setSelectionForeground(Color.WHITE);
        this.table.getTableHeader().setFont(new Font("Segoe UI", 1, 15));
        this.table.getTableHeader().setForeground(Color.WHITE);
        this.table.getTableHeader().setBackground(new Color(0, 0, 0, 150));
        this.table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            {
                Objects.requireNonNull(LRUPageReplacement.this);
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                this.setHorizontalAlignment(0);
                if (column == LRUPageReplacement.this.numFrames + 1 && value != null) {
                    if (value.equals("FAULT")) {
                        this.setForeground(new Color(255, 100, 100));
                    } else if (value.equals("HIT")) {
                        this.setForeground(new Color(100, 255, 100));
                    }
                } else {
                    this.setForeground(Color.WHITE);
                }

                return c;
            }
        };

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        for(String[] step : this.executionSteps) {
            this.model.addRow(step);
        }

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2));
        scrollPane.setPreferredSize(new Dimension(0, 350));
        panel.add(tableTitle, "North");
        panel.add(scrollPane, "Center");
        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        JLabel resultTitle = new JLabel("Summary", 0);
        resultTitle.setFont(new Font("Segoe UI", 1, 18));
        resultTitle.setForeground(Color.WHITE);
        this.resultArea = new JTextArea();
        this.resultArea.setEditable(false);
        this.resultArea.setFont(new Font("Consolas", 1, 14));
        this.resultArea.setForeground(Color.WHITE);
        this.resultArea.setBackground(new Color(0, 0, 0, 120));
        this.resultArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2), BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        int faults = 0;
        int hits = 0;

        for(String[] step : this.executionSteps) {
            if (step[this.numFrames + 1].equals("FAULT")) {
                ++faults;
            } else {
                ++hits;
            }
        }

        double hitRatio = (double)hits * (double)100.0F / (double)this.pageReferences.length;
        double faultRatio = (double)faults * (double)100.0F / (double)this.pageReferences.length;
        StringBuilder summary = new StringBuilder();
        summary.append("Page Reference String: ").append(String.join(", ", this.pageReferences)).append("\n");
        summary.append("Number of Frames: ").append(this.numFrames).append("\n");
        summary.append("Total References: ").append(this.pageReferences.length).append("\n\n");
        summary.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        summary.append("Total Page Faults: ").append(faults).append("\n");
        summary.append("Total Page Hits: ").append(hits).append("\n");
        summary.append(String.format("Hit Ratio: %.2f%%\n", hitRatio));
        summary.append(String.format("Fault Ratio: %.2f%%\n", faultRatio));
        summary.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        this.resultArea.setText(summary.toString());
        this.resultArea.setPreferredSize(new Dimension(0, 150));
        panel.add(resultTitle, "North");
        panel.add(this.resultArea, "Center");
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", 1, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(12));
        return button;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LRUPageReplacement lruPageReplacement = new LRUPageReplacement();
            lruPageReplacement.setVisible(true);
        });
    }
}
