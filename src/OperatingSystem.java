//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

class OperatingSystem extends JFrame {
    public OperatingSystem() {
        this.setTitle("OS - Main Menu");
        this.setSize(1578, 760);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout());
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1578, 760, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        JPanel overlay = new JPanel(new BorderLayout(0, 60)) {
            {
                Objects.requireNonNull(OperatingSystem.this);
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
        overlay.setBorder(BorderFactory.createEmptyBorder(60, 100, 80, 100));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        JLabel title = new JLabel("AF-X OS") {
            {
                Objects.requireNonNull(OperatingSystem.this);
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
        title.setFont(new Font("Segoe UI", 1, 72));
        title.setForeground(new Color(0, 255, 200));
        titlePanel.add(title, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel subtitle = new JLabel("Operating System Control Panel") {
            {
                Objects.requireNonNull(OperatingSystem.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 120));
                g2.setFont(this.getFont());
                g2.drawString(this.getText(), 2, this.getHeight() - 6);
                g2.setColor(this.getForeground());
                g2.drawString(this.getText(), 0, this.getHeight() - 8);
                g2.dispose();
            }
        };
        subtitle.setFont(new Font("Segoe UI", 0, 22));
        subtitle.setForeground(new Color(255, 255, 255, 220));
        titlePanel.add(subtitle, gbc);
        overlay.add(titlePanel, "North");
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 35, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JButton processBtn = this.createModernButton("Process Management", "process.png", new Color(88, 166, 255));
        processBtn.addActionListener((e) -> {
            this.dispose();
            new ProcessManagementMenu();
        });
        buttonsPanel.add(processBtn);
        JButton memorybutton = this.createModernButton("Memory Management", "memory.png", new Color(129, 236, 161));
        memorybutton.addActionListener((e) -> {
            this.dispose();
            new MemoryManagement();
        });
        buttonsPanel.add(memorybutton);
        buttonsPanel.add(this.createModernButton("I/O Management", "io.png", new Color(255, 184, 77)));
        JButton Sychronization = this.createModernButton("Sychronization", "other.png", new Color(168, 140, 255));
        buttonsPanel.add(Sychronization);
        Sychronization.addActionListener((e) -> {
            this.dispose();
            new SemaphoreSynchronization();
        });
        overlay.add(buttonsPanel, "Center");
        JPanel footerPanel = new JPanel(new FlowLayout(1));
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("Version 1.0 | Built with Java Swing");
        footerLabel.setFont(new Font("Segoe UI", 0, 13));
        footerLabel.setForeground(new Color(255, 255, 255, 150));
        footerPanel.add(footerLabel);
        overlay.add(footerPanel, "South");
        this.add(overlay);
        this.setVisible(true);
    }

    private JButton createModernButton(String text, String iconPath, final Color accentColor) {
        ImageIcon icon = null;

        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image img = originalIcon.getImage().getScaledInstance(50, 50, 4);
            icon = new ImageIcon(img);
        } catch (Exception var7) {
            icon = this.createFallbackIcon(accentColor);
        }

        JButton button = new JButton(text, icon) {
            private float hoverProgress;
            private float scaleProgress;
            private Timer animTimer;

            {
                Objects.requireNonNull(OperatingSystem.this);
                this.hoverProgress = 0.0F;
                this.scaleProgress = 0.0F;
                this.addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        animateHover(true);
                    }

                    public void mouseExited(MouseEvent e) {
                        animateHover(false);
                    }

                    public void mousePressed(MouseEvent e) {
                        animateScale(true);
                    }

                    public void mouseReleased(MouseEvent e) {
                        animateScale(false);
                    }
                });
            }

            private void animateHover(boolean entering) {
                if (this.animTimer != null) {
                    this.animTimer.stop();
                }

                this.animTimer = new Timer(16, (evt) -> {
                    if (entering) {
                        this.hoverProgress = Math.min(1.0F, this.hoverProgress + 0.08F);
                    } else {
                        this.hoverProgress = Math.max(0.0F, this.hoverProgress - 0.08F);
                    }

                    this.repaint();
                    if (entering && this.hoverProgress >= 1.0F || !entering && this.hoverProgress <= 0.0F) {
                        this.animTimer.stop();
                    }

                });
                this.animTimer.start();
            }

            private void animateScale(boolean pressing) {
                if (this.animTimer != null) {
                    this.animTimer.stop();
                }

                this.animTimer = new Timer(16, (evt) -> {
                    if (pressing) {
                        this.scaleProgress = Math.min(1.0F, this.scaleProgress + 0.12F);
                    } else {
                        this.scaleProgress = Math.max(0.0F, this.scaleProgress - 0.12F);
                    }

                    this.repaint();
                    if (pressing && this.scaleProgress >= 1.0F || !pressing && this.scaleProgress <= 0.0F) {
                        this.animTimer.stop();
                    }

                });
                this.animTimer.start();
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = this.getWidth();
                int h = this.getHeight();
                float scale = 1.0F - this.scaleProgress * 0.04F;
                int offset = (int)((1.0F - scale) * (float)w / 2.0F);
                g2.translate(offset, offset);
                g2.scale((double)scale, (double)scale);
                int shadowAlpha = (int)(70.0F + this.hoverProgress * 50.0F);
                g2.setColor(new Color(0, 0, 0, shadowAlpha));
                g2.fillRoundRect(5, 5, w - 10, h - 10, 32, 32);
                int baseAlpha = (int)(140.0F + this.hoverProgress * 35.0F);
                g2.setColor(new Color(0, 0, 0, baseAlpha));
                g2.fillRoundRect(0, 0, w, h, 32, 32);
                int accentAlpha = (int)(25.0F + this.hoverProgress * 40.0F);
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), accentAlpha));
                g2.fillRoundRect(0, 0, w, h, 32, 32);
                GradientPaint gloss = new GradientPaint(0.0F, 0.0F, new Color(255, 255, 255, (int)(90.0F + this.hoverProgress * 50.0F)), 0.0F, (float)(h / 2), new Color(255, 255, 255, 0));
                g2.setPaint(gloss);
                g2.fillRoundRect(0, 0, w, h / 2, 32, 32);
                int borderAlpha = (int)(120.0F + this.hoverProgress * 80.0F);
                g2.setColor(new Color(255, 255, 255, borderAlpha));
                g2.setStroke(new BasicStroke(2.5F));
                g2.drawRoundRect(2, 2, w - 5, h - 5, 30, 30);
                if (this.hoverProgress > 0.0F) {
                    g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), (int)(this.hoverProgress * 150.0F)));
                    g2.setStroke(new BasicStroke(3.0F));
                    g2.drawRoundRect(1, 1, w - 3, h - 3, 31, 31);
                }

                g2.scale((double)(1.0F / scale), (double)(1.0F / scale));
                g2.translate(-offset, -offset);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", 1, 19));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        button.setCursor(new Cursor(12));
        button.setHorizontalTextPosition(0);
        button.setVerticalTextPosition(3);
        button.setIconTextGap(15);
        return button;
    }

    private ImageIcon createFallbackIcon(Color color) {
        int size = 50;
        BufferedImage img = new BufferedImage(size, size, 2);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
        g2.fillOval(5, 5, size - 10, size - 10);
        g2.setColor(new Color(255, 255, 255, 150));
        g2.setStroke(new BasicStroke(2.0F));
        g2.drawOval(5, 5, size - 10, size - 10);
        g2.dispose();
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new OperatingSystem();
        });
    }
}
