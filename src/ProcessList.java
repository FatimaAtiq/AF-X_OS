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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ProcessList extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel statusLabel;
    private Timer refreshTimer;

    public ProcessList() {
        this.setTitle("Process List");
        this.setSize(900, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        ImageIcon bgIcon = new ImageIcon("operatingSystem/img_1.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(900, 700, 4);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        this.setContentPane(background);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel title = new JLabel("All Processes & Status", 0) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.drawString(this.getText(), 2, this.getHeight() - 8);
                g2.setColor(Color.WHITE);
                g2.drawString(this.getText(), 0, this.getHeight() - 10);
            }
        };
        title.setFont(new Font("Segoe UI", 1, 36));
        title.setForeground(Color.WHITE);
        titlePanel.add(title, "Center");
        background.add(titlePanel, "North");
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        JPanel searchPanel = this.createSearchPanel();
        mainPanel.add(searchPanel, "North");
        JPanel tablePanel = this.createTablePanel();
        mainPanel.add(tablePanel, "Center");
        JPanel buttonPanel = this.createButtonPanel();
        mainPanel.add(buttonPanel, "South");
        background.add(mainPanel, "Center");
        this.loadProcesses();
        this.updateStatusLabel();
        this.startAutoRefresh();
        this.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(0, 10, 5)) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", 1, 14));
        searchLabel.setForeground(Color.WHITE);
        panel.add(searchLabel);
        this.searchField = new JTextField(20);
        this.searchField.setFont(new Font("Segoe UI", 0, 14));
        this.searchField.setBackground(new Color(60, 60, 60));
        this.searchField.setForeground(Color.WHITE);
        this.searchField.setCaretColor(Color.WHITE);
        this.searchField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        this.searchField.addKeyListener(new KeyAdapter() {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            public void keyReleased(KeyEvent e) {
                ProcessList.this.filterTable(ProcessList.this.searchField.getText());
            }
        });
        panel.add(this.searchField);
        this.statusLabel = new JLabel();
        this.statusLabel.setFont(new Font("Segoe UI", 0, 14));
        this.statusLabel.setForeground(new Color(100, 255, 100));
        panel.add(this.statusLabel);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        String[] columns = new String[]{"PID", "Process Name", "Priority", "Arrival Time", "Burst Time", "Status"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.table.setSelectionMode(0);
        this.table.setFillsViewportHeight(true);
        this.table.setFont(new Font("Segoe UI", 0, 15));
        this.table.setRowHeight(32);
        this.table.setForeground(Color.WHITE);
        this.table.setBackground(new Color(19, 18, 18));
        this.table.setGridColor(new Color(124, 123, 123));
        this.table.setSelectionBackground(new Color(63, 137, 198));
        this.table.setSelectionForeground(Color.WHITE);
        this.table.setShowGrid(true);
        this.table.setIntercellSpacing(new Dimension(1, 1));
        this.table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value != null ? value.toString() : "";
                this.setHorizontalAlignment(0);
                this.setFont(new Font("Segoe UI", 1, 14));
                if (!isSelected) {
                    if (status.equals("Ready")) {
                        c.setBackground(new Color(50, 180, 50));
                        c.setForeground(Color.WHITE);
                    } else if (status.equals("Running")) {
                        c.setBackground(new Color(0, 120, 255));
                        c.setForeground(Color.WHITE);
                    } else if (status.equals("Blocked")) {
                        c.setBackground(new Color(220, 50, 50));
                        c.setForeground(Color.WHITE);
                    } else if (status.equals("Suspended")) {
                        c.setBackground(Color.GRAY);
                        c.setForeground(Color.WHITE);
                    } else if (!status.equals("Completed") && !status.equals("Terminated")) {
                        c.setBackground(table.getBackground());
                        c.setForeground(table.getForeground());
                    } else {
                        c.setBackground(new Color(150, 150, 150));
                        c.setForeground(Color.WHITE);
                    }
                }

                return c;
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);
        this.table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        JTableHeader header = this.table.getTableHeader();
        header.setFont(new Font("Segoe UI", 1, 15));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        panel.add(scrollPane, "Center");
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 15, 10)) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton refreshBtn = this.createStyledButton("Refresh Table", new Color(0, 123, 255), new Color(0, 105, 217));
        refreshBtn.addActionListener((e) -> {
            this.loadProcesses();
            this.updateStatusLabel();
            JOptionPane.showMessageDialog(this, "Table refreshed successfully!", "Success", 1);
        });
        panel.add(refreshBtn);
        JButton closeBtn = this.createStyledButton("Close", new Color(108, 117, 125), new Color(90, 98, 104));
        closeBtn.addActionListener((e) -> this.dispose());
        panel.add(closeBtn);
        return panel;
    }

    private JButton createStyledButton(String text, final Color bgColor, final Color hoverColor) {
        JButton button = new JButton(text) {
            {
                Objects.requireNonNull(ProcessList.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (this.getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (this.getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(bgColor);
                }

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
                g2.setColor(this.getForeground());
                g2.setFont(this.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (this.getWidth() - fm.stringWidth(this.getText())) / 2;
                int y = (this.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(this.getText(), x, y);
            }
        };
        button.setFont(new Font("Segoe UI", 1, 15));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(160, 40));
        return button;
    }

    private void loadProcesses() {
        this.tableModel.setRowCount(0);
        if (createprocess.processList != null && !createprocess.processList.isEmpty()) {
            for(Process p : createprocess.processList) {
                this.tableModel.addRow(new Object[]{p.id, p.name, p.priority, p.arrivalTime, p.burstTime, p.status});
            }

        }
    }

    private void filterTable(String query) {
        this.tableModel.setRowCount(0);
        if (createprocess.processList != null) {
            String lowerQuery = query.toLowerCase().trim();

            for(Process p : createprocess.processList) {
                if (lowerQuery.isEmpty() || p.name.toLowerCase().contains(lowerQuery) || p.status.toLowerCase().contains(lowerQuery) || String.valueOf(p.id).contains(lowerQuery)) {
                    this.tableModel.addRow(new Object[]{p.id, p.name, p.priority, p.arrivalTime, p.burstTime, p.status});
                }
            }

            this.updateStatusLabel();
        }
    }

    private void updateStatusLabel() {
        if (createprocess.processList != null && !createprocess.processList.isEmpty()) {
            int total = createprocess.processList.size();
            int ready = 0;
            int running = 0;
            int blocked = 0;
            int suspended = 0;

            for(Process p : createprocess.processList) {
                switch (p.status) {
                    case "Ready":
                        ++ready;
                        break;
                    case "Running":
                        ++running;
                        break;
                    case "Blocked":
                        ++blocked;
                        break;
                    case "Suspended":
                        ++suspended;
                }
            }

            this.statusLabel.setText(String.format("Total: %d | Ready: %d | Running: %d | Blocked: %d | Suspended: %d", total, ready, running, blocked, suspended));
        } else {
            this.statusLabel.setText("No processes available");
        }
    }

    private void startAutoRefresh() {
        this.refreshTimer = new Timer(2000, (e) -> {
            this.loadProcesses();
            this.updateStatusLabel();
        });
        this.refreshTimer.start();
    }

    public void dispose() {
        if (this.refreshTimer != null) {
            this.refreshTimer.stop();
        }

        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProcessList::new);
    }
}
