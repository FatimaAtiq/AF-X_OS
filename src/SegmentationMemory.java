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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class SegmentationMemory extends JFrame {
    private int[] segmentBase;
    private int[] segmentLimit;
    private int numSegments;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea resultArea;
    private JTextField numSegmentsField;
    private JPanel segmentInputPanel;
    private List<SegmentRow> segmentRows;

    public SegmentationMemory() {
        this.setTitle("Segmentation Memory Management System");
        this.setSize(1100, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1100, 700, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        this.segmentRows = new ArrayList();
        this.showSetupScreen();
    }

    private void showSetupScreen() {
        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setOpaque(false);
        setupPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        JLabel titleLabel = new JLabel("Segmentation Configuration Setup", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.addSectionHeader(formPanel, gbc, 0, "Segment Configuration");
        this.numSegmentsField = this.addInputField(formPanel, gbc, 1, "Number of Segments:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton generateBtn = this.createStyledButton("Generate Segment Fields", new Color(70, 130, 180));
        generateBtn.setPreferredSize(new Dimension(250, 40));
        formPanel.add(generateBtn, gbc);
        gbc.gridy = 3;
        this.addSectionHeader(formPanel, gbc, 3, "Segment Table (Base & Limit)");
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.segmentInputPanel = new JPanel();
        this.segmentInputPanel.setLayout(new BoxLayout(this.segmentInputPanel, 1));
        this.segmentInputPanel.setOpaque(false);
        this.segmentInputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        formPanel.add(this.segmentInputPanel, gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(25, 10, 10, 10);
        JButton initializeBtn = this.createStyledButton("Initialize Segmentation System", new Color(46, 125, 50));
        initializeBtn.setPreferredSize(new Dimension(280, 45));
        formPanel.add(initializeBtn, gbc);
        setupPanel.add(titleLabel, "North");
        setupPanel.add(formPanel, "Center");
        this.getContentPane().removeAll();
        ((JLabel)this.getContentPane()).add(setupPanel);
        this.revalidate();
        this.repaint();
        generateBtn.addActionListener((e) -> this.generateSegmentFields());
        initializeBtn.addActionListener((e) -> this.initializeSystem());
    }

    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, int row, String title) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        JLabel header = new JLabel(title);
        header.setFont(new Font("Segoe UI", 1, 18));
        header.setForeground(new Color(255, 255, 255, 240));
        panel.add(header, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
    }

    private JTextField addInputField(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.4;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", 0, 15));
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", 0, 14));
        field.setPreferredSize(new Dimension(200, 32));
        panel.add(field, gbc);
        return field;
    }

    private void generateSegmentFields() {
        try {
            this.numSegments = Integer.parseInt(this.numSegmentsField.getText().trim());
            if (this.numSegments <= 0) {
                throw new NumberFormatException();
            }

            this.segmentInputPanel.removeAll();
            this.segmentRows.clear();

            for(int i = 0; i < this.numSegments; ++i) {
                SegmentRow row = new SegmentRow(i);
                this.segmentRows.add(row);
                this.segmentInputPanel.add(row.getPanel());
            }

            this.segmentInputPanel.revalidate();
            this.segmentInputPanel.repaint();
        } catch (NumberFormatException var3) {
            this.showError("Please enter a valid number of segments");
        }

    }

    private void initializeSystem() {
        try {
            this.numSegments = Integer.parseInt(this.numSegmentsField.getText().trim());
            if (this.numSegments <= 0) {
                throw new NumberFormatException();
            }

            this.segmentBase = new int[this.numSegments];
            this.segmentLimit = new int[this.numSegments];

            for(int i = 0; i < this.segmentRows.size(); ++i) {
                SegmentRow row = (SegmentRow)this.segmentRows.get(i);
                int base = Integer.parseInt(row.baseField.getText().trim());
                int limit = Integer.parseInt(row.limitField.getText().trim());
                if (base < 0 || limit <= 0) {
                    this.showError("Invalid base or limit for segment " + i);
                    return;
                }

                this.segmentBase[i] = base;
                this.segmentLimit[i] = limit;
            }

            this.showMainScreen();
        } catch (NumberFormatException var5) {
            this.showError("Please fill all fields with valid numbers");
        }

    }

    private void showMainScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        JLabel titleLabel = new JLabel("Segmentation Memory Management", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel infoPanel = new JPanel(new FlowLayout(1, 25, 8));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        infoPanel.add(this.createInfoLabel("Total Segments: " + this.numSegments));
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setOpaque(false);
        JPanel tablePanel = this.createSegmentTablePanel();
        JPanel translationPanel = this.createTranslationPanel();
        contentPanel.add(tablePanel);
        contentPanel.add(translationPanel);
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

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text, 0);
        label.setFont(new Font("Segoe UI", 1, 14));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 120));
        label.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return label;
    }

    private JPanel createSegmentTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        JLabel tableTitle = new JLabel("Segment Table", 0);
        tableTitle.setFont(new Font("Segoe UI", 1, 20));
        tableTitle.setForeground(Color.WHITE);
        String[] columnNames = new String[]{"Segment", "Base", "Limit"};
        this.model = new DefaultTableModel(columnNames, 0) {
            {
                Objects.requireNonNull(SegmentationMemory.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.model);
        this.table.setFont(new Font("Segoe UI", 0, 15));
        this.table.setRowHeight(35);
        this.table.setForeground(Color.WHITE);
        this.table.setBackground(new Color(0, 0, 0, 120));
        this.table.setGridColor(new Color(255, 255, 255, 60));
        this.table.setSelectionBackground(new Color(46, 125, 50, 150));
        this.table.setSelectionForeground(Color.WHITE);
        this.table.getTableHeader().setFont(new Font("Segoe UI", 1, 16));
        this.table.getTableHeader().setForeground(Color.WHITE);
        this.table.getTableHeader().setBackground(new Color(0, 0, 0, 150));
        this.table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        for(int i = 0; i < this.numSegments; ++i) {
            this.model.addRow(new Object[]{i, this.segmentBase[i], this.segmentLimit[i]});
        }

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.add(this.table.getTableHeader(), "North");
        tableContainer.add(this.table, "Center");
        tableContainer.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2));
        panel.add(tableTitle, "North");
        panel.add(tableContainer, "Center");
        return panel;
    }

    private JPanel createTranslationPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        JLabel translationTitle = new JLabel("Address Translation", 0);
        translationTitle.setFont(new Font("Segoe UI", 1, 20));
        translationTitle.setForeground(Color.WHITE);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, 1));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel segmentPanel = new JPanel(new FlowLayout(1, 15, 10));
        segmentPanel.setOpaque(false);
        JLabel segmentLabel = new JLabel("Segment Number:");
        segmentLabel.setFont(new Font("Segoe UI", 1, 16));
        segmentLabel.setForeground(Color.WHITE);
        JTextField segmentField = new JTextField(15);
        segmentField.setFont(new Font("Segoe UI", 0, 16));
        segmentField.setPreferredSize(new Dimension(180, 35));
        segmentPanel.add(segmentLabel);
        segmentPanel.add(segmentField);
        JPanel offsetPanel = new JPanel(new FlowLayout(1, 15, 10));
        offsetPanel.setOpaque(false);
        JLabel offsetLabel = new JLabel("Offset:");
        offsetLabel.setFont(new Font("Segoe UI", 1, 16));
        offsetLabel.setForeground(Color.WHITE);
        JTextField offsetField = new JTextField(15);
        offsetField.setFont(new Font("Segoe UI", 0, 16));
        offsetField.setPreferredSize(new Dimension(180, 35));
        offsetPanel.add(offsetLabel);
        offsetPanel.add(offsetField);
        JPanel buttonPanel = new JPanel(new FlowLayout(1, 15, 10));
        buttonPanel.setOpaque(false);
        JButton translateBtn = this.createStyledButton("Translate", new Color(46, 125, 50));
        translateBtn.setPreferredSize(new Dimension(120, 40));
        JButton clearBtn = this.createStyledButton("Clear", new Color(220, 53, 69));
        clearBtn.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(translateBtn);
        buttonPanel.add(clearBtn);
        inputPanel.add(segmentPanel);
        inputPanel.add(offsetPanel);
        inputPanel.add(buttonPanel);
        this.resultArea = new JTextArea();
        this.resultArea.setEditable(false);
        this.resultArea.setFont(new Font("Consolas", 1, 15));
        this.resultArea.setForeground(Color.WHITE);
        this.resultArea.setBackground(new Color(0, 0, 0, 120));
        this.resultArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        translateBtn.addActionListener((e) -> {
            try {
                int segment = Integer.parseInt(segmentField.getText().trim());
                int offset = Integer.parseInt(offsetField.getText().trim());
                this.translateAddress(segment, offset);
            } catch (NumberFormatException var6) {
                this.resultArea.setText("Error: Invalid input");
            }

        });
        clearBtn.addActionListener((e) -> {
            this.resultArea.setText("");
            segmentField.setText("");
            offsetField.setText("");
        });
        panel.add(translationTitle, "North");
        panel.add(inputPanel, "Center");
        panel.add(this.resultArea, "South");
        return panel;
    }

    private void translateAddress(int segment, int offset) {
        StringBuilder result = new StringBuilder();
        result.append("Segment Number: ").append(segment).append("\n");
        result.append("Offset: ").append(offset).append("\n\n");
        if (segment >= 0 && segment < this.numSegments) {
            if (offset >= 0 && offset < this.segmentLimit[segment]) {
                int physicalAddress = this.segmentBase[segment] + offset;
                result.append("Base Address: ").append(this.segmentBase[segment]).append("\n");
                result.append("Limit: ").append(this.segmentLimit[segment]).append("\n");
                result.append("Physical Address: ").append(physicalAddress).append("\n\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("   Translation SUCCESS!\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━");
                this.resultArea.setText(result.toString());
            } else {
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("   SEGMENTATION FAULT!\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("Offset out of bounds!\n");
                result.append("Segment ").append(segment).append(" limit: ").append(this.segmentLimit[segment]);
                this.resultArea.setText(result.toString());
            }
        } else {
            result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            result.append("   SEGMENTATION FAULT!\n");
            result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            result.append("Invalid segment number!\n");
            result.append("Valid range: 0 to ").append(this.numSegments - 1);
            this.resultArea.setText(result.toString());
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", 1, 14));
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

            SegmentationMemory segmentationMemory = new SegmentationMemory();
            segmentationMemory.setVisible(true);
        });
    }

    class SegmentRow {
        JPanel panel;
        JTextField baseField;
        JTextField limitField;

        SegmentRow(int index) {
            Objects.requireNonNull(SegmentationMemory.this);
            super();
            this.panel = new JPanel(new FlowLayout(0, 15, 8));
            this.panel.setOpaque(false);
            this.panel.setMaximumSize(new Dimension(600, 50));
            JLabel label = new JLabel("Segment " + index + ":");
            label.setFont(new Font("Segoe UI", 1, 14));
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(100, 30));
            JLabel baseLabel = new JLabel("Base:");
            baseLabel.setFont(new Font("Segoe UI", 0, 14));
            baseLabel.setForeground(Color.WHITE);
            this.baseField = new JTextField(10);
            this.baseField.setFont(new Font("Segoe UI", 0, 14));
            this.baseField.setPreferredSize(new Dimension(120, 32));
            JLabel limitLabel = new JLabel("Limit:");
            limitLabel.setFont(new Font("Segoe UI", 0, 14));
            limitLabel.setForeground(Color.WHITE);
            this.limitField = new JTextField(10);
            this.limitField.setFont(new Font("Segoe UI", 0, 14));
            this.limitField.setPreferredSize(new Dimension(120, 32));
            this.panel.add(label);
            this.panel.add(baseLabel);
            this.panel.add(this.baseField);
            this.panel.add(limitLabel);
            this.panel.add(this.limitField);
        }

        JPanel getPanel() {
            return this.panel;
        }
    }
}
