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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class Scheduling extends JFrame {
    public Scheduling() {
        this.setTitle("Scheduling Manager");
        this.setSize(550, 450);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(550, 450, 4);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        this.setContentPane(background);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel title = new JLabel("Process Scheduler", 0) {
            {
                Objects.requireNonNull(Scheduling.this);
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
        title.setFont(new Font("Segoe UI", 1, 32));
        title.setForeground(Color.WHITE);
        titlePanel.add(title, "Center");
        background.add(titlePanel, "North");
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            {
                Objects.requireNonNull(Scheduling.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = 2;
        JButton fcfsBtn = this.createStyledButton("FCFS", new Color(115, 174, 220), new Color(108, 179, 225));
        fcfsBtn.addActionListener((e) -> new FCFS_Scheduler());
        gbc.gridy = 0;
        mainPanel.add(fcfsBtn, gbc);
        JButton preemptiveSJFBtn = this.createStyledButton("Preemptive SJF", new Color(112, 234, 160), new Color(112, 234, 160));
        preemptiveSJFBtn.addActionListener((e) -> new SRTF_Scheduler());
        gbc.gridy = 1;
        mainPanel.add(preemptiveSJFBtn, gbc);
        JButton nonPreemptiveSJFBtn = this.createStyledButton("Non-Preemptive SJF", new Color(202, 149, 227), new Color(211, 136, 244));
        nonPreemptiveSJFBtn.addActionListener((e) -> new Non_SRTF_Scheduler());
        JButton RoundRobinBtn = this.createStyledButton("Round Robin", new Color(124, 123, 123), new Color(124, 123, 123));
        RoundRobinBtn.addActionListener((e) -> new RR_Scheduler());
        gbc.gridy = 2;
        mainPanel.add(nonPreemptiveSJFBtn, gbc);
        gbc.gridy = 3;
        mainPanel.add(RoundRobinBtn, gbc);
        background.add(mainPanel, "Center");
        JPanel buttonPanel = new JPanel(new FlowLayout(1, 15, 10)) {
            {
                Objects.requireNonNull(Scheduling.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }
        };
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        JButton closeBtn = this.createSmallButton("Close", new Color(108, 117, 125), new Color(90, 98, 104));
        closeBtn.addActionListener((e) -> this.dispose());
        buttonPanel.add(closeBtn);
        background.add(buttonPanel, "South");
        this.setVisible(true);
    }

    private JButton createStyledButton(String text, final Color bgColor, final Color hoverColor) {
        JButton button = new JButton(text) {
            {
                Objects.requireNonNull(Scheduling.this);
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

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
                g2.setColor(this.getForeground());
                g2.setFont(this.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (this.getWidth() - fm.stringWidth(this.getText())) / 2;
                int y = (this.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(this.getText(), x, y);
            }
        };
        button.setFont(new Font("Segoe UI", 1, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(280, 50));
        return button;
    }

    private JButton createSmallButton(String text, final Color bgColor, final Color hoverColor) {
        JButton button = new JButton(text) {
            {
                Objects.requireNonNull(Scheduling.this);
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
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Scheduling();
        });
    }
}
