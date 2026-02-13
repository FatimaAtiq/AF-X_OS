//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

class SemaphoreSynchronization extends JFrame {
    private final Semaphore semaphore = new Semaphore(1);
    private JTextArea logArea;
    private JPanel threadPanel;

    public SemaphoreSynchronization() {
        this.setTitle("Semaphore Synchronization");
        this.setSize(900, 700);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(900, 700, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        JPanel overlay = new JPanel(new BorderLayout(0, 20)) {
            {
                Objects.requireNonNull(SemaphoreSynchronization.this);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 45, 45);
                GradientPaint gp = new GradientPaint(0.0F, 0.0F, new Color(255, 255, 255, 15), 0.0F, (float)(this.getHeight() / 3), new Color(255, 255, 255, 0));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight() / 3, 45, 45);
                g2.setColor(new Color(255, 255, 255, 100));
                g2.setStroke(new BasicStroke(2.5F));
                g2.drawRoundRect(2, 2, this.getWidth() - 5, this.getHeight() - 5, 43, 43);
                g2.dispose();
            }
        };
        overlay.setOpaque(false);
        overlay.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Semaphore Synchronization") {
            {
                Objects.requireNonNull(SemaphoreSynchronization.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(0, 255, 200, 60));
                g2.setFont(this.getFont());

                for(int i = 3; i > 0; --i) {
                    g2.drawString(this.getText(), i, this.getHeight() - 15 - i);
                }

                g2.setColor(new Color(0, 0, 0, 150));
                g2.drawString(this.getText(), 3, this.getHeight() - 12);
                g2.setColor(this.getForeground());
                g2.drawString(this.getText(), 0, this.getHeight() - 15);
                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Segoe UI", 1, 40));
        titleLabel.setForeground(new Color(0, 255, 200));
        titlePanel.add(titleLabel, gbc);
        JPanel controlPanel = new JPanel(new FlowLayout(1, 20, 10));
        controlPanel.setOpaque(false);
        JLabel processLabel = new JLabel("Number of Processes:");
        processLabel.setFont(new Font("Segoe UI", 1, 14));
        processLabel.setForeground(Color.WHITE);
        JSpinner processSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        processSpinner.setFont(new Font("Segoe UI", 0, 14));
        JButton startButton = new JButton("Start Simulation");
        startButton.setFont(new Font("Segoe UI", 1, 14));
        startButton.setBackground(new Color(88, 166, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        JButton clearButton = new JButton("Clear Log");
        clearButton.setFont(new Font("Segoe UI", 1, 14));
        clearButton.setBackground(new Color(255, 100, 100));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        controlPanel.add(processLabel);
        controlPanel.add(processSpinner);
        controlPanel.add(startButton);
        controlPanel.add(clearButton);
        this.threadPanel = new JPanel();
        this.threadPanel.setLayout(new BoxLayout(this.threadPanel, 1));
        this.threadPanel.setOpaque(false);
        JScrollPane threadScroll = new JScrollPane(this.threadPanel);
        threadScroll.setOpaque(false);
        threadScroll.getViewport().setOpaque(false);
        threadScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 255, 200), 2), "Process Status", 0, 0, new Font("Segoe UI", 1, 14), Color.WHITE));
        threadScroll.setPreferredSize(new Dimension(750, 180));
        this.logArea = new JTextArea(10, 60);
        this.logArea.setEditable(false);
        this.logArea.setFont(new Font("Consolas", 0, 12));
        this.logArea.setBackground(new Color(20, 20, 30));
        this.logArea.setForeground(new Color(0, 255, 200));
        JScrollPane logScroll = new JScrollPane(this.logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 255, 200), 2), "Execution Log", 0, 0, new Font("Segoe UI", 1, 14), Color.WHITE));
        startButton.addActionListener((e) -> {
            this.threadPanel.removeAll();
            this.logArea.setText("");
            this.log("═══════════════════════════════════════");
            this.log("  SIMULATION STARTED");
            this.log("═══════════════════════════════════════\n");
            int processCount = (Integer)processSpinner.getValue();

            for(int i = 1; i <= processCount; ++i) {
                JLabel statusLabel = new JLabel("Process " + i + ": CREATED");
                statusLabel.setOpaque(true);
                statusLabel.setBackground(new Color(255, 200, 0));
                statusLabel.setForeground(Color.BLACK);
                statusLabel.setFont(new Font("Segoe UI", 1, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                this.threadPanel.add(statusLabel);
                this.threadPanel.add(Box.createVerticalStrut(5));
                (new ProcessThread("Process " + i, this.semaphore, this.logArea, statusLabel)).start();
            }

            this.threadPanel.revalidate();
            this.threadPanel.repaint();
        });
        clearButton.addActionListener((e) -> this.logArea.setText(""));
        JPanel topSection = new JPanel(new BorderLayout(0, 15));
        topSection.setOpaque(false);
        topSection.add(titlePanel, "North");
        topSection.add(controlPanel, "Center");
        JPanel centerSection = new JPanel(new BorderLayout(0, 10));
        centerSection.setOpaque(false);
        centerSection.add(threadScroll, "North");
        centerSection.add(logScroll, "Center");
        overlay.add(topSection, "North");
        overlay.add(centerSection, "Center");
        this.add(overlay);
        this.setVisible(true);
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            this.logArea.append(message + "\n");
            this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SemaphoreSynchronization::new);
    }

    class ProcessThread extends Thread {
        private final String name;
        private final Semaphore semaphore;
        private final JTextArea logArea;
        private final JLabel statusLabel;

        public ProcessThread(String name, Semaphore semaphore, JTextArea logArea, JLabel statusLabel) {
            Objects.requireNonNull(SemaphoreSynchronization.this);
            super();
            this.name = name;
            this.semaphore = semaphore;
            this.logArea = logArea;
            this.statusLabel = statusLabel;
        }

        public void run() {
            this.updateStatus("WAITING", new Color(255, 150, 0));
            this.log(this.name + " is waiting to enter critical section...");

            try {
                Thread.sleep((long)((int)(Math.random() * (double)1000.0F) + 500));
                this.semaphore.acquire();
                this.updateStatus("IN CRITICAL SECTION", new Color(0, 200, 100));
                this.log(this.name + " ENTERED critical section ✅");
                Thread.sleep(2000L);
                this.updateStatus("EXITED", new Color(150, 150, 150));
                this.log(this.name + " EXITED critical section ❌");
            } catch (InterruptedException var5) {
                this.log(this.name + " was interrupted.");
            } finally {
                this.semaphore.release();
                this.log(this.name + " released semaphore\n");
            }

        }

        private void log(String message) {
            SwingUtilities.invokeLater(() -> {
                this.logArea.append(message + "\n");
                this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
            });
        }

        private void updateStatus(String status, Color color) {
            SwingUtilities.invokeLater(() -> {
                this.statusLabel.setText(this.name + ": " + status);
                this.statusLabel.setBackground(color);
            });
        }
    }
}
