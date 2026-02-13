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

class PagingMemoryFinal extends JFrame {
    private int logicalMemory;
    private int physicalMemory;
    private int pageSize;
    private int numPages;
    private int numFrames;
    private int[] pageTable;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea resultArea;
    private JTextField logicalMemField;
    private JTextField physicalMemField;
    private JTextField pageSizeField;
    private JTextField mappingsField;
    private JPanel mappingInputPanel;
    private List<MappingRow> mappingRows;

    public PagingMemoryFinal() {
        this.setTitle("Paging Memory Management System");
        this.setSize(1100, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1100, 700, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        this.mappingRows = new ArrayList();
        this.showSetupScreen();
    }

    private void showSetupScreen() {
        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setOpaque(false);
        setupPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        JLabel titleLabel = new JLabel("Memory Configuration Setup", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.addSectionHeader(formPanel, gbc, 0, "Memory Configuration");
        this.logicalMemField = this.addInputField(formPanel, gbc, 1, "Logical Memory Size:");
        this.physicalMemField = this.addInputField(formPanel, gbc, 2, "Physical Memory Size:");
        this.pageSizeField = this.addInputField(formPanel, gbc, 3, "Page Size:");
        this.mappingsField = this.addInputField(formPanel, gbc, 4, "Number of Mappings:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton generateBtn = this.createStyledButton("Generate Mapping Fields", new Color(70, 130, 180));
        generateBtn.setPreferredSize(new Dimension(250, 40));
        formPanel.add(generateBtn, gbc);
        gbc.gridy = 6;
        this.addSectionHeader(formPanel, gbc, 6, "Page-to-Frame Mappings");
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        this.mappingInputPanel = new JPanel();
        this.mappingInputPanel.setLayout(new BoxLayout(this.mappingInputPanel, 1));
        this.mappingInputPanel.setOpaque(false);
        this.mappingInputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        formPanel.add(this.mappingInputPanel, gbc);
        gbc.gridy = 8;
        gbc.insets = new Insets(25, 10, 10, 10);
        JButton initializeBtn = this.createStyledButton("Initialize Memory System", new Color(46, 125, 50));
        initializeBtn.setPreferredSize(new Dimension(250, 45));
        formPanel.add(initializeBtn, gbc);
        setupPanel.add(titleLabel, "North");
        setupPanel.add(formPanel, "Center");
        this.getContentPane().removeAll();
        ((JLabel)this.getContentPane()).add(setupPanel);
        this.revalidate();
        this.repaint();
        generateBtn.addActionListener((e) -> this.generateMappingFields());
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

    private void generateMappingFields() {
        try {
            int numMappings = Integer.parseInt(this.mappingsField.getText().trim());
            if (numMappings <= 0) {
                throw new NumberFormatException();
            }

            this.mappingInputPanel.removeAll();
            this.mappingRows.clear();

            for(int i = 0; i < numMappings; ++i) {
                MappingRow row = new MappingRow(i);
                this.mappingRows.add(row);
                this.mappingInputPanel.add(row.getPanel());
            }

            this.mappingInputPanel.revalidate();
            this.mappingInputPanel.repaint();
        } catch (NumberFormatException var4) {
            this.showError("Please enter a valid number of mappings");
        }

    }

    private void initializeSystem() {
        try {
            this.logicalMemory = Integer.parseInt(this.logicalMemField.getText().trim());
            this.physicalMemory = Integer.parseInt(this.physicalMemField.getText().trim());
            this.pageSize = Integer.parseInt(this.pageSizeField.getText().trim());
            if (this.logicalMemory <= 0 || this.physicalMemory <= 0 || this.pageSize <= 0) {
                throw new NumberFormatException();
            }

            this.numPages = this.logicalMemory / this.pageSize;
            this.numFrames = this.physicalMemory / this.pageSize;
            this.pageTable = new int[this.numPages];

            for(int i = 0; i < this.numPages; ++i) {
                this.pageTable[i] = -1;
            }

            for(MappingRow row : this.mappingRows) {
                int page = Integer.parseInt(row.pageField.getText().trim());
                int frame = Integer.parseInt(row.frameField.getText().trim());
                if (page < 0 || page >= this.numPages || frame < 0 || frame >= this.numFrames) {
                    this.showError("Invalid page " + page + " or frame " + frame);
                    return;
                }

                this.pageTable[page] = frame;
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
        JLabel titleLabel = new JLabel("Paging Memory Management", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel infoPanel = new JPanel(new FlowLayout(1, 25, 8));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        infoPanel.add(this.createInfoLabel("Logical: " + this.logicalMemory));
        infoPanel.add(this.createInfoLabel("Physical: " + this.physicalMemory));
        infoPanel.add(this.createInfoLabel("Page Size: " + this.pageSize));
        infoPanel.add(this.createInfoLabel("Pages: " + this.numPages));
        infoPanel.add(this.createInfoLabel("Frames: " + this.numFrames));
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setOpaque(false);
        JPanel tablePanel = this.createMappedTablePanel();
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

    private JPanel createMappedTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        JLabel tableTitle = new JLabel("Mapped Pages", 0);
        tableTitle.setFont(new Font("Segoe UI", 1, 20));
        tableTitle.setForeground(Color.WHITE);
        String[] columnNames = new String[]{"Page", "Frame"};
        this.model = new DefaultTableModel(columnNames, 0) {
            {
                Objects.requireNonNull(PagingMemoryFinal.this);
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
        this.table.setSelectionBackground(new Color(70, 130, 180, 150));
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

        for(int i = 0; i < this.numPages; ++i) {
            if (this.pageTable[i] != -1) {
                this.model.addRow(new Object[]{i, this.pageTable[i]});
            }
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
        JPanel fieldPanel = new JPanel(new FlowLayout(1, 15, 10));
        fieldPanel.setOpaque(false);
        JLabel addressLabel = new JLabel("Logical Address:");
        addressLabel.setFont(new Font("Segoe UI", 1, 16));
        addressLabel.setForeground(Color.WHITE);
        JTextField addressField = new JTextField(15);
        addressField.setFont(new Font("Segoe UI", 0, 16));
        addressField.setPreferredSize(new Dimension(180, 35));
        fieldPanel.add(addressLabel);
        fieldPanel.add(addressField);
        JPanel buttonPanel = new JPanel(new FlowLayout(1, 15, 10));
        buttonPanel.setOpaque(false);
        JButton translateBtn = this.createStyledButton("Translate", new Color(70, 130, 180));
        translateBtn.setPreferredSize(new Dimension(120, 40));
        JButton clearBtn = this.createStyledButton("Clear", new Color(220, 53, 69));
        clearBtn.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(translateBtn);
        buttonPanel.add(clearBtn);
        inputPanel.add(fieldPanel);
        inputPanel.add(buttonPanel);
        this.resultArea = new JTextArea();
        this.resultArea.setEditable(false);
        this.resultArea.setFont(new Font("Consolas", 1, 15));
        this.resultArea.setForeground(Color.WHITE);
        this.resultArea.setBackground(new Color(0, 0, 0, 120));
        this.resultArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        translateBtn.addActionListener((e) -> {
            try {
                int logicalAddr = Integer.parseInt(addressField.getText().trim());
                this.translateAddress(logicalAddr);
            } catch (NumberFormatException var4) {
                this.resultArea.setText("Error: Invalid input");
            }

        });
        clearBtn.addActionListener((e) -> {
            this.resultArea.setText("");
            addressField.setText("");
        });
        panel.add(translationTitle, "North");
        panel.add(inputPanel, "Center");
        panel.add(this.resultArea, "South");
        return panel;
    }

    private void translateAddress(int logicalAddr) {
        StringBuilder result = new StringBuilder();
        result.append("Logical Address: ").append(logicalAddr).append("\n\n");
        if (logicalAddr >= 0 && logicalAddr < this.logicalMemory) {
            int page = logicalAddr / this.pageSize;
            int offset = logicalAddr % this.pageSize;
            result.append("Page Number: ").append(page).append("\n");
            result.append("Offset: ").append(offset).append("\n\n");
            if (this.pageTable[page] == -1) {
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("     PAGE FAULT!\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("Page ").append(page).append(" is not mapped.");
            } else {
                int frame = this.pageTable[page];
                int physicalAddr = frame * this.pageSize + offset;
                result.append("Frame Number: ").append(frame).append("\n");
                result.append("Physical Address: ").append(physicalAddr).append("\n\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                result.append("   Translation SUCCESS!\n");
                result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }

            this.resultArea.setText(result.toString());
        } else {
            result.append("ERROR: Invalid logical address!\n");
            result.append("Valid range: 0 to ").append(this.logicalMemory - 1);
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

            PagingMemoryFinal pagingMemoryFinal = new PagingMemoryFinal();
            pagingMemoryFinal.setVisible(true);
        });
    }

    class MappingRow {
        JPanel panel;
        JTextField pageField;
        JTextField frameField;

        MappingRow(int index) {
            Objects.requireNonNull(PagingMemoryFinal.this);
            super();
            this.panel = new JPanel(new FlowLayout(0, 15, 8));
            this.panel.setOpaque(false);
            this.panel.setMaximumSize(new Dimension(600, 50));
            JLabel label = new JLabel("Mapping " + index + ":");
            label.setFont(new Font("Segoe UI", 1, 14));
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(100, 30));
            JLabel pageLabel = new JLabel("Page:");
            pageLabel.setFont(new Font("Segoe UI", 0, 14));
            pageLabel.setForeground(Color.WHITE);
            this.pageField = new JTextField(10);
            this.pageField.setFont(new Font("Segoe UI", 0, 14));
            this.pageField.setPreferredSize(new Dimension(120, 32));
            JLabel frameLabel = new JLabel("Frame:");
            frameLabel.setFont(new Font("Segoe UI", 0, 14));
            frameLabel.setForeground(Color.WHITE);
            this.frameField = new JTextField(10);
            this.frameField.setFont(new Font("Segoe UI", 0, 14));
            this.frameField.setPreferredSize(new Dimension(120, 32));
            this.panel.add(label);
            this.panel.add(pageLabel);
            this.panel.add(this.pageField);
            this.panel.add(frameLabel);
            this.panel.add(this.frameField);
        }

        JPanel getPanel() {
            return this.panel;
        }
    }
}
