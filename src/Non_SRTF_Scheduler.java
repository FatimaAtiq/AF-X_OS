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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.BorderFactory;
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

class Non_SRTF_Scheduler extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel avgWaitingLabel;
    private JLabel avgTurnaroundLabel;

    public Non_SRTF_Scheduler() {
        if (createprocess.processList != null && !createprocess.processList.isEmpty()) {
            ArrayList<Process> processes = new ArrayList(createprocess.processList);
            int n = processes.size();
            int[] completionTime = new int[n];
            int[] waitingTime = new int[n];
            int[] turnaroundTime = new int[n];
            boolean[] completed = new boolean[n];
            int time = 0;
            int finished = 0;
            ArrayList<GanttBlock> ganttBlocks = new ArrayList();

            while(finished < n) {
                int idx = -1;
                int minBurst = Integer.MAX_VALUE;

                for(int i = 0; i < n; ++i) {
                    Process p = (Process)processes.get(i);
                    if (!completed[i] && p.arrivalTime <= time && p.burstTime < minBurst) {
                        minBurst = p.burstTime;
                        idx = i;
                    }
                }

                if (idx == -1) {
                    ++time;
                } else {
                    Process p = (Process)processes.get(idx);
                    ganttBlocks.add(new GanttBlock(p.name, time, time + p.burstTime, this.getProcessColor(idx)));
                    time += p.burstTime;
                    completionTime[idx] = time;
                    turnaroundTime[idx] = completionTime[idx] - p.arrivalTime;
                    waitingTime[idx] = turnaroundTime[idx] - p.burstTime;
                    completed[idx] = true;
                    ++finished;
                }
            }

            double avgWT = (double)0.0F;
            double avgTAT = (double)0.0F;

            for(int i = 0; i < n; ++i) {
                avgWT += (double)waitingTime[i];
                avgTAT += (double)turnaroundTime[i];
            }

            avgWT /= (double)n;
            avgTAT /= (double)n;
            this.setTitle("Non-Preemptive SJF Scheduling");
            this.setSize(900, 700);
            this.setLocationRelativeTo((Component)null);
            this.setDefaultCloseOperation(2);
            JPanel background = new JPanel(new BorderLayout());
            background.setBackground(Color.BLACK);
            this.setContentPane(background);
            JLabel title = new JLabel("Non-Preemptive SJF Scheduling Results", 0);
            title.setFont(new Font("Segoe UI", 1, 30));
            title.setForeground(Color.WHITE);
            title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            background.add(title, "North");
            JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
            centerPanel.setOpaque(false);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
            centerPanel.add(this.createStatsPanel(avgWT, avgTAT), "North");
            centerPanel.add(this.createTablePanel(processes, completionTime, turnaroundTime, waitingTime), "Center");
            centerPanel.add(this.createGanttChartPanel(ganttBlocks), "South");
            background.add(centerPanel, "Center");
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog((Component)null, "No processes created yet!", "Error", 0);
        }
    }

    private JPanel createStatsPanel(double avgWT, double avgTAT) {
        JPanel panel = new JPanel(new FlowLayout(1, 40, 5));
        panel.setOpaque(false);
        Object[] var10004 = new Object[]{avgWT};
        this.avgWaitingLabel = new JLabel("Avg Waiting Time: " + String.format("%.2f", var10004));
        this.avgWaitingLabel.setForeground(Color.GREEN);
        this.avgWaitingLabel.setFont(new Font("Segoe UI", 1, 16));
        var10004 = new Object[]{avgTAT};
        this.avgTurnaroundLabel = new JLabel("Avg Turnaround Time: " + String.format("%.2f", var10004));
        this.avgTurnaroundLabel.setForeground(Color.CYAN);
        this.avgTurnaroundLabel.setFont(new Font("Segoe UI", 1, 16));
        panel.add(this.avgWaitingLabel);
        panel.add(this.avgTurnaroundLabel);
        return panel;
    }

    private JPanel createTablePanel(ArrayList<Process> processes, int[] ct, int[] tat, int[] wt) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        String[] cols = new String[]{"PID", "Process Name", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time"};
        this.tableModel = new DefaultTableModel(cols, 0) {
            {
                Objects.requireNonNull(Non_SRTF_Scheduler.this);
            }

            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.table.setRowHeight(28);
        this.table.setFont(new Font("Segoe UI", 0, 14));
        this.table.setBackground(new Color(30, 30, 30));
        this.table.setForeground(Color.WHITE);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(0);

        for(int i = 0; i < this.table.getColumnCount(); ++i) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        for(int i = 0; i < processes.size(); ++i) {
            Process p = (Process)processes.get(i);
            this.tableModel.addRow(new Object[]{p.id, p.name, p.arrivalTime, p.burstTime, ct[i], tat[i], wt[i]});
        }

        panel.add(new JScrollPane(this.table), "Center");
        return panel;
    }

    private JPanel createGanttChartPanel(final ArrayList<GanttBlock> blocks) {
        JPanel panel = new JPanel() {
            {
                Objects.requireNonNull(Non_SRTF_Scheduler.this);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!blocks.isEmpty()) {
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int width = this.getWidth() - 40;
                    int x = 20;
                    int y = 40;
                    int barHeight = 40;
                    int totalTime = ((GanttBlock)blocks.get(blocks.size() - 1)).endTime;

                    for(GanttBlock b : blocks) {
                        int blockWidth = (int)((double)(b.endTime - b.startTime) / (double)totalTime * (double)width);
                        g2.setColor(b.color);
                        g2.fillRect(x, y, blockWidth, barHeight);
                        g2.setColor(Color.BLACK);
                        g2.drawRect(x, y, blockWidth, barHeight);
                        g2.setColor(Color.WHITE);
                        g2.drawString(b.processName, x + 5, y + 25);
                        g2.drawString(String.valueOf(b.startTime), x, y + 60);
                        x += blockWidth;
                    }

                    g2.drawString(String.valueOf(totalTime), x - 5, y + 60);
                }
            }
        };
        panel.setPreferredSize(new Dimension(0, 120));
        panel.setOpaque(false);
        return panel;
    }

    private Color getProcessColor(int i) {
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.ORANGE};
        return colors[i % colors.length];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception var1) {
            }

            new Non_SRTF_Scheduler();
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
}
