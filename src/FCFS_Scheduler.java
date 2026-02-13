//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Comparator;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class FCFS_Scheduler extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel avgWaitingLabel;
    private JLabel avgTurnaroundLabel;

    public FCFS_Scheduler() {
        if (createprocess.processList != null && !createprocess.processList.isEmpty()) {
            ArrayList<Process> processes = new ArrayList(createprocess.processList);
            processes.sort(Comparator.comparingInt((px) -> px.arrivalTime));
            int n = processes.size();
            int[] completionTime = new int[n];
            int[] turnAround = new int[n];
            int[] waitingTime = new int[n];
            completionTime[0] = ((Process)processes.get(0)).arrivalTime + ((Process)processes.get(0)).burstTime;

            for(int i = 1; i < n; ++i) {
                if (((Process)processes.get(i)).arrivalTime > completionTime[i - 1]) {
                    completionTime[i] = ((Process)processes.get(i)).arrivalTime + ((Process)processes.get(i)).burstTime;
                } else {
                    completionTime[i] = completionTime[i - 1] + ((Process)processes.get(i)).burstTime;
                }
            }

            double totalWaiting = (double)0.0F;
            double totalTurnaround = (double)0.0F;

            for(int i = 0; i < n; ++i) {
                turnAround[i] = completionTime[i] - ((Process)processes.get(i)).arrivalTime;
                waitingTime[i] = turnAround[i] - ((Process)processes.get(i)).burstTime;
                totalWaiting += (double)waitingTime[i];
                totalTurnaround += (double)turnAround[i];
            }

            ArrayList<String> cpuTimeline = new ArrayList();
            ArrayList<GanttBlock> ganttBlocks = new ArrayList();
            int lastCompletion = 0;

            for(int i = 0; i < n; ++i) {
                Process p = (Process)processes.get(i);
                if (i == 0 && p.arrivalTime > 0) {
                    cpuTimeline.add("Idle: 0 - " + p.arrivalTime);
                    ganttBlocks.add(new GanttBlock("Idle", 0, p.arrivalTime, Color.GRAY));
                }

                int start = Math.max(p.arrivalTime, lastCompletion);
                if (start > lastCompletion) {
                    cpuTimeline.add("Idle: " + lastCompletion + " - " + start);
                    ganttBlocks.add(new GanttBlock("Idle", lastCompletion, start, Color.GRAY));
                }

                lastCompletion = start + p.burstTime;
                cpuTimeline.add(p.name + ": " + start + " - " + lastCompletion);
                ganttBlocks.add(new GanttBlock(p.name, start, lastCompletion, this.getProcessColor(i)));
            }

            this.setTitle("FCFS Scheduling Results");
            this.setSize(900, 800);
            this.setLocationRelativeTo((Component)null);
            this.setDefaultCloseOperation(2);
            ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
            Image bgImage = bgIcon.getImage().getScaledInstance(900, 800, 4);
            JLabel background = new JLabel(new ImageIcon(bgImage));
            background.setLayout(new BorderLayout());
            this.setContentPane(background);
            JLabel title = new JLabel("FCFS Scheduling Results", 0);
            title.setFont(new Font("Segoe UI", 1, 32));
            title.setForeground(Color.WHITE);
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
            titlePanel.add(title, "Center");
            background.add(titlePanel, "North");
            JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
            mainPanel.setOpaque(false);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
            mainPanel.add(this.createStatsPanel(totalWaiting / (double)n, totalTurnaround / (double)n), "North");
            JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
            centerPanel.setOpaque(false);
            centerPanel.add(this.createTablePanel(processes, completionTime, turnAround, waitingTime), "Center");
            centerPanel.add(this.createGanttChartPanel(ganttBlocks), "South");
            mainPanel.add(centerPanel, "Center");
            JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
            bottomPanel.setOpaque(false);
            bottomPanel.add(this.createButtonPanel(), "South");
            mainPanel.add(bottomPanel, "South");
            background.add(mainPanel, "Center");
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog((Component)null, "No processes created yet!", "Error", 0);
        }
    }

    private Color getProcessColor(int index) {
        Color[] colors = new Color[]{new Color(255, 99, 132), new Color(54, 162, 235), new Color(255, 206, 86), new Color(75, 192, 192), new Color(153, 102, 255), new Color(255, 159, 64), new Color(201, 203, 207), new Color(100, 255, 100)};
        return colors[index % colors.length];
    }

    private JPanel createGanttChartPanel(ArrayList<GanttBlock> ganttBlocks) {
        JPanel outerPanel = new JPanel(new BorderLayout()) {
            {
                Objects.requireNonNull(FCFS_Scheduler.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        outerPanel.setOpaque(false);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel ganttTitle = new JLabel("Gantt Chart", 0);
        ganttTitle.setFont(new Font("Segoe UI", 1, 18));
        ganttTitle.setForeground(Color.WHITE);
        ganttTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        outerPanel.add(ganttTitle, "North");
        GanttChartPanel ganttChart = new GanttChartPanel(ganttBlocks);
        ganttChart.setPreferredSize(new Dimension(0, 120));
        outerPanel.add(ganttChart, "Center");
        return outerPanel;
    }

    private JPanel createStatsPanel(double avgWaiting, double avgTurnaround) {
        JPanel panel = new JPanel(new FlowLayout(1, 30, 5)) {
            {
                Objects.requireNonNull(FCFS_Scheduler.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        this.avgWaitingLabel = new JLabel(String.format("Avg Waiting Time: %.2f", avgWaiting));
        this.avgWaitingLabel.setFont(new Font("Segoe UI", 1, 16));
        this.avgWaitingLabel.setForeground(new Color(100, 255, 100));
        this.avgTurnaroundLabel = new JLabel(String.format("Avg Turnaround Time: %.2f", avgTurnaround));
        this.avgTurnaroundLabel.setFont(new Font("Segoe UI", 1, 16));
        this.avgTurnaroundLabel.setForeground(new Color(100, 200, 255));
        panel.add(this.avgWaitingLabel);
        panel.add(this.avgTurnaroundLabel);
        return panel;
    }

    private JPanel createTablePanel(ArrayList<Process> processes, int[] completionTime, int[] turnAround, int[] waitingTime) {
        JPanel panel = new JPanel(new BorderLayout()) {
            {
                Objects.requireNonNull(FCFS_Scheduler.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        panel.setOpaque(false);
        String[] columns = new String[]{"PID", "Process Name", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            {
                Objects.requireNonNull(FCFS_Scheduler.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.table.setFillsViewportHeight(true);
        this.table.setRowHeight(30);
        this.table.setForeground(Color.WHITE);
        this.table.setBackground(new Color(19, 18, 18));
        this.table.setGridColor(new Color(124, 123, 123));
        this.table.setSelectionBackground(new Color(63, 137, 198));
        this.table.setSelectionForeground(Color.WHITE);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        for(int i = 0; i < processes.size(); ++i) {
            Process p = (Process)processes.get(i);
            this.tableModel.addRow(new Object[]{p.id, p.name, p.arrivalTime, p.burstTime, completionTime[i], turnAround[i], waitingTime[i]});
        }

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, "Center");
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 15, 10));
        panel.setOpaque(false);
        JButton closeBtn = new JButton("Close");
        closeBtn.setPreferredSize(new Dimension(120, 40));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Segoe UI", 1, 15));
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener((e) -> this.dispose());
        panel.add(closeBtn);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new FCFS_Scheduler();
        });
    }

    static class GanttBlock {
        String processName;
        int startTime;
        int endTime;
        Color color;

        GanttBlock(String processName, int startTime, int endTime, Color color) {
            this.processName = processName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.color = color;
        }
    }

    class GanttChartPanel extends JPanel {
        private ArrayList<GanttBlock> blocks;

        GanttChartPanel(ArrayList<GanttBlock> blocks) {
            Objects.requireNonNull(FCFS_Scheduler.this);
            super();
            this.blocks = blocks;
            this.setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (!this.blocks.isEmpty()) {
                int maxTime = ((GanttBlock)this.blocks.get(this.blocks.size() - 1)).endTime;
                int width = this.getWidth() - 40;
                int height = this.getHeight() - 40;
                int barHeight = 50;
                int yPos = (height - barHeight) / 2 + 20;
                g2.setColor(Color.WHITE);
                g2.drawLine(20, yPos + barHeight + 10, width + 20, yPos + barHeight + 10);

                for(GanttBlock block : this.blocks) {
                    int blockStart = 20 + (int)((double)block.startTime / (double)maxTime * (double)width);
                    int blockWidth = (int)((double)(block.endTime - block.startTime) / (double)maxTime * (double)width);
                    g2.setColor(block.color);
                    g2.fillRect(blockStart, yPos, blockWidth, barHeight);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(blockStart, yPos, blockWidth, barHeight);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", 1, 12));
                    FontMetrics fm = g2.getFontMetrics();
                    String label = block.processName;
                    int labelWidth = fm.stringWidth(label);
                    if (labelWidth < blockWidth - 4) {
                        g2.drawString(label, blockStart + (blockWidth - labelWidth) / 2, yPos + barHeight / 2 + 5);
                    }

                    g2.setColor(Color.LIGHT_GRAY);
                    g2.setFont(new Font("Segoe UI", 0, 11));
                    g2.drawString(String.valueOf(block.startTime), blockStart, yPos + barHeight + 25);
                }

            }
        }
    }
}
