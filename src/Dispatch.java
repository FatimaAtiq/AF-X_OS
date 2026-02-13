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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

class Dispatch extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField pidField;
    private JTextField searchField;
    private JLabel statusLabel;
    private static final Color TABLE_BG = new Color(30, 30, 30);
    private static final Color TABLE_FG;
    private static final Color TABLE_HEADER_BG;
    private static final Color TABLE_SELECTION_BG;
    private static final Color GRID_COLOR;

    public Dispatch() {
        this.setTitle("Dispatch Process Manager");
        this.setSize(900, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        this.setupBackground();
        this.setupUI();
        this.setVisible(true);
    }

    private void setupBackground() {
        ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
        Image img = bgIcon.getImage().getScaledInstance(900, 700, 4);
        JLabel bg = new JLabel(new ImageIcon(img));
        bg.setLayout(new BorderLayout());
        this.setContentPane(bg);
    }

    private void setupUI() {
        JLabel bg = (JLabel)this.getContentPane();
        bg.add(this.createTitlePanel(), "North");
        bg.add(this.createCenterPanel(), "Center");
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0;
        JLabel title = new JLabel("Dispatch Process Manager");
        title.setFont(new Font("Segoe UI", 1, 34));
        title.setForeground(Color.WHITE);
        gbc.gridy = 0;
        panel.add(title, gbc);
        JLabel pidLabel = new JLabel("Enter Process ID:");
        pidLabel.setFont(new Font("Segoe UI", 1, 16));
        pidLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(pidLabel, gbc);
        this.pidField = this.styledField(15);
        this.pidField.addActionListener((e) -> this.DispatchProcess());
        gbc.gridy = 2;
        panel.add(this.pidField, gbc);
        JButton blockBtn = this.createButton("Dispatch Process", new Color(132, 251, 69));
        blockBtn.addActionListener((e) -> this.DispatchProcess());
        gbc.gridy = 3;
        panel.add(blockBtn, gbc);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        panel.add(this.createSearchPanel(), "North");
        panel.add(this.createTablePanel(), "Center");
        panel.add(this.createBottomPanel(), "South");
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(0, 15, 5));
        panel.setOpaque(false);
        JLabel lbl = new JLabel("Search:");
        lbl.setFont(new Font("Segoe UI", 1, 14));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        this.searchField = this.styledField(20);
        this.searchField.addKeyListener(new KeyAdapter() {
            {
                Objects.requireNonNull(Dispatch.this);
            }

            public void keyReleased(KeyEvent e) {
                Dispatch.this.filterTable(Dispatch.this.searchField.getText());
            }
        });
        panel.add(this.searchField);
        this.statusLabel = new JLabel();
        this.statusLabel.setFont(new Font("Segoe UI", 1, 14));
        this.statusLabel.setForeground(new Color(0, 255, 127));
        panel.add(this.statusLabel);
        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = new String[]{"PID", "Process Name", "Priority", "Arrival", "Burst", "Status"};
        this.tableModel = new DefaultTableModel(cols, 0) {
            {
                Objects.requireNonNull(Dispatch.this);
            }

            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.styleTable();
        JScrollPane sp = new JScrollPane(this.table);
        sp.getViewport().setBackground(TABLE_BG);
        sp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return sp;
    }

    private void styleTable() {
        this.table.setBackground(TABLE_BG);
        this.table.setForeground(TABLE_FG);
        this.table.setRowHeight(32);
        this.table.setFont(new Font("Segoe UI", 0, 14));
        this.table.setGridColor(GRID_COLOR);
        this.table.setSelectionBackground(TABLE_SELECTION_BG);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(TABLE_HEADER_BG);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Segoe UI", 1, 15));
        headerRenderer.setHorizontalAlignment(0);
        headerRenderer.setOpaque(true);
        JTableHeader header = this.table.getTableHeader();
        header.setDefaultRenderer(headerRenderer);
        header.setPreferredSize(new Dimension(100, 40));
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(0);
        center.setBackground(TABLE_BG);
        center.setForeground(Color.WHITE);

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        this.table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            {
                Objects.requireNonNull(Dispatch.this);
            }

            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel lbl = (JLabel)super.getTableCellRendererComponent(t, v, s, f, r, c);
                lbl.setHorizontalAlignment(0);
                if (!s) {
                    lbl.setBackground(Dispatch.TABLE_BG);
                    if ("Ready".equals(v)) {
                        lbl.setForeground(new Color(40, 167, 69));
                    }
                }

                return lbl;
            }
        });
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(false);
        JButton refresh = this.createButton("Refresh", new Color(0, 123, 255));
        refresh.addActionListener((e) -> this.refresh());
        JButton close = this.createButton("Close", new Color(108, 117, 125));
        close.addActionListener((e) -> this.dispose());
        panel.add(refresh);
        panel.add(close);
        return panel;
    }

    private void DispatchProcess() {
        try {
            int pid = Integer.parseInt(this.pidField.getText().trim());
            Process p = this.findProcessById(pid);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "No process available to dispatch.", "Error", 0);
                return;
            }

            for(Process proc : createprocess.processList) {
                if ("Running".equalsIgnoreCase(proc.status)) {
                    JOptionPane.showMessageDialog(this, "One process is already running.\nPlease wait.", "CPU Busy", 2);
                    return;
                }
            }

            Process process = this.findProcessById(pid);
            process.status = "Running";
            JOptionPane.showMessageDialog(this, "Process " + p.name + " dispatched successfully.", "Success", 1);
            this.dispose();
        } catch (Exception var5) {
        }

    }

    private Process findProcessById(int pid) {
        if (createprocess.processList == null) {
            return null;
        } else {
            for(Process p : createprocess.processList) {
                if (p.id == pid) {
                    return p;
                }
            }

            return null;
        }
    }

    private void loadProcesses() {
        this.tableModel.setRowCount(0);
        if (createprocess.processList != null) {
            for(Process p : createprocess.processList) {
                if (p.status == "Ready") {
                    this.tableModel.addRow(new Object[]{p.id, p.name, p.priority, p.arrivalTime, p.burstTime, p.status});
                }
            }

        }
    }

    private void filterTable(String q) {
        q = q.toLowerCase();
        this.tableModel.setRowCount(0);

        for(Process p : createprocess.processList) {
            if (p.name.toLowerCase().contains(q) || p.status.toLowerCase().contains(q) || String.valueOf(p.id).contains(q)) {
                this.tableModel.addRow(new Object[]{p.id, p.name, p.priority, p.arrivalTime, p.burstTime, p.status});
            }
        }

        this.updateStatus();
    }

    private void updateStatus() {
        int r = 0;
        int run = 0;
        int b = 0;

        for(Process p : createprocess.processList) {
            if ("Ready".equals(p.status)) {
                ++r;
            } else if ("Running".equals(p.status)) {
                ++run;
            } else if ("Blocked".equals(p.status)) {
                ++b;
            }
        }

        this.statusLabel.setText("Ready: " + r + " | Running: " + run + " | Blocked: " + b);
    }

    private void refresh() {
        this.loadProcesses();
        this.updateStatus();
    }

    private JTextField styledField(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(new Font("Segoe UI", 0, 14));
        f.setBackground(new Color(45, 45, 45));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setHorizontalAlignment(0);
        return f;
    }

    private JButton createButton(String text, final Color color) {
        final JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", 1, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setCursor(new Cursor(12));
        btn.addMouseListener(new MouseAdapter() {
            {
                Objects.requireNonNull(Dispatch.this);
            }

            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    private void msg(String m, String t, int type) {
        JOptionPane.showMessageDialog(this, m, t, type);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dispatch::new);
    }

    static {
        TABLE_FG = Color.WHITE;
        TABLE_HEADER_BG = new Color(20, 20, 20);
        TABLE_SELECTION_BG = new Color(60, 60, 60);
        GRID_COLOR = new Color(50, 50, 50);
    }
}
