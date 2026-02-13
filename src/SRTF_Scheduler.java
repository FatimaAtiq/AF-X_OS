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
import java.awt.Image;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class SRTF_Scheduler extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel avgWaitingLabel;
    private JLabel avgTurnaroundLabel;

    public SRTF_Scheduler() {
        if (createprocess.processList != null && !createprocess.processList.isEmpty()) {
            ArrayList<Process> processes = new ArrayList(createprocess.processList);
            int n = processes.size();
            int[] remainingTime = new int[n];
            int[] completionTime = new int[n];
            int[] waitingTime = new int[n];
            int[] turnAround = new int[n];

            for(int i = 0; i < n; ++i) {
                remainingTime[i] = ((Process)processes.get(i)).burstTime;
            }

            int completed = 0;
            int time = 0;

            ArrayList<String> cpuTimeline;
            for(cpuTimeline = new ArrayList(); completed < n; ++time) {
                int tik = -1;

                for(int i = 0; i < n; ++i) {
                    Process p = (Process)processes.get(i);
                    if (p.arrivalTime <= time && remainingTime[i] > 0 && (tik == -1 || remainingTime[i] < remainingTime[tik])) {
                        tik = i;
                    }
                }

                if (tik != -1) {
                    int var10002 = remainingTime[tik]--;
                    cpuTimeline.add("Time " + time + ": Executing " + ((Process)processes.get(tik)).name);
                    if (remainingTime[tik] == 0) {
                        completionTime[tik] = time + 1;
                        ++completed;
                    }
                } else {
                    cpuTimeline.add("Time " + time + ": CPU is idle");
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

            this.setTitle("SRTF Scheduling Results");
            this.setSize(900, 700);
            this.setLocationRelativeTo((Component)null);
            this.setDefaultCloseOperation(2);
            ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
            Image bgImage = bgIcon.getImage().getScaledInstance(900, 700, 4);
            JLabel background = new JLabel(new ImageIcon(bgImage));
            background.setLayout(new BorderLayout());
            this.setContentPane(background);
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);
            titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
            JLabel title = new JLabel("SRTF Scheduling Results", 0);
            title.setFont(new Font("Segoe UI", 1, 32));
            title.setForeground(Color.WHITE);
            titlePanel.add(title, "Center");
            background.add(titlePanel, "North");
            JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
            mainPanel.setOpaque(false);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
            JPanel statsPanel = this.createStatsPanel(totalWaiting / (double)n, totalTurnaround / (double)n);
            mainPanel.add(statsPanel, "North");
            JPanel tablePanel = this.createTablePanel(processes, completionTime, turnAround, waitingTime);
            mainPanel.add(tablePanel, "Center");
            JPanel idlePanel = this.createIdleTimePanel(cpuTimeline);
            mainPanel.add(idlePanel, "South");
            background.add(mainPanel, "Center");
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog((Component)null, "No processes created yet!", "Error", 0);
        }
    }

    private JPanel createStatsPanel(double avgWaiting, double avgTurnaround) {
        JPanel panel = new JPanel(new FlowLayout(1, 30, 5));
        panel.setOpaque(false);
        this.avgWaitingLabel = new JLabel(String.format("Avg Waiting Time: %.2f", avgWaiting));
        this.avgWaitingLabel.setFont(new Font("Segoe UI", 1, 16));
        this.avgWaitingLabel.setForeground(new Color(100, 255, 100));
        panel.add(this.avgWaitingLabel);
        this.avgTurnaroundLabel = new JLabel(String.format("Avg Turnaround Time: %.2f", avgTurnaround));
        this.avgTurnaroundLabel.setFont(new Font("Segoe UI", 1, 16));
        this.avgTurnaroundLabel.setForeground(new Color(100, 200, 255));
        panel.add(this.avgTurnaroundLabel);
        return panel;
    }

    private JPanel createTablePanel(ArrayList<Process> processes, int[] completionTime, int[] turnAround, int[] waitingTime) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        String[] columns = new String[]{"PID", "Process Name", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            {
                Objects.requireNonNull(SRTF_Scheduler.this);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.table.setSelectionMode(0);
        this.table.setFillsViewportHeight(true);
        this.table.setFont(new Font("Segoe UI", 0, 14));
        this.table.setRowHeight(30);
        this.table.setForeground(Color.WHITE);
        this.table.setBackground(new Color(19, 18, 18));
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
        panel.add(scrollPane, "Center");
        return panel;
    }

    private JPanel createIdleTimePanel(ArrayList<String> cpuTimeline) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.YELLOW);
        textArea.setBackground(new Color(19, 18, 18, 150));
        textArea.setFont(new Font("Segoe UI", 0, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(0, 120));
        StringBuilder sb = new StringBuilder("CPU Timeline (Idle + Execution):\n");

        for(String s : cpuTimeline) {
            sb.append(s).append("\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, "Center");
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new SRTF_Scheduler();
        });
    }
}
