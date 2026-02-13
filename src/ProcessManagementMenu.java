//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class ProcessManagementMenu extends JFrame {
    public ProcessManagementMenu() {
        this.setTitle("Process Management");
        this.setSize(1578, 760);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout());
        ImageIcon bgIcon = new ImageIcon("main/resources/img.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1578, 760, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(BorderFactory.createEmptyBorder(50, 180, 70, 180));
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JLabel titleLabel = new JLabel("Process Management") {
            {
                Objects.requireNonNull(ProcessManagementMenu.this);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 100));
                g2.setFont(this.getFont());
                g2.drawString(this.getText(), 3, this.getHeight() - 13);
                g2.setColor(this.getForeground());
                g2.drawString(this.getText(), 0, this.getHeight() - 16);
                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Segoe UI", 1, 42));
        titleLabel.setForeground(new Color(255, 255, 255, 240));
        topBar.add(titleLabel, "West");
        JButton backBtn = this.createBackButton();
        topBar.add(backBtn, "East");
        overlay.add(topBar, "North");
        JPanel glassContainer = new JPanel(new BorderLayout()) {
            {
                Objects.requireNonNull(ProcessManagementMenu.this);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 40, 40);
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(2.0F));
                g2.drawRoundRect(1, 1, this.getWidth() - 3, this.getHeight() - 3, 38, 38);
                g2.dispose();
            }
        };
        glassContainer.setOpaque(false);
        glassContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.add(this.createProcessButton("Create Process", "➕", new Color(88, 166, 255), (e) -> new createprocess()));
        gridPanel.add(this.createProcessButton("Destroy Process", "\ud83d\uddd1", new Color(255, 107, 107), (e) -> new DestroyProcess()));
        gridPanel.add(this.createProcessButton("Suspend Process", "⏸", new Color(255, 184, 77), (e) -> new SuspendProcess()));
        gridPanel.add(this.createProcessButton("Resume Process", "▶", new Color(129, 236, 161), (e) -> new Resume()));
        gridPanel.add(this.createProcessButton("Block Process", "\ud83d\udeab", new Color(255, 118, 164), (e) -> new BlockProcess()));
        gridPanel.add(this.createProcessButton("Wakeup Process", "⏰", new Color(168, 140, 255), (e) -> new Wakeup()));
        gridPanel.add(this.createProcessButton("Dispatch Process", "\ud83d\ude80", new Color(102, 217, 232), (e) -> new Dispatch()));
        gridPanel.add(this.createProcessButton("Scheduling", "⏱", new Color(173, 216, 230), (e) -> new Scheduling()));
        glassContainer.add(gridPanel, "Center");
        overlay.add(glassContainer, "Center");
        this.add(overlay);
        this.setVisible(true);
    }

    private JButton createBackButton() {
        JButton button = new JButton("← Back") {
            private float hoverProgress;
            private Timer hoverTimer;

            {
                Objects.requireNonNull(ProcessManagementMenu.this);
                this.hoverProgress = 0.0F;
                this.addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        animateHover(true);
                    }

                    public void mouseExited(MouseEvent e) {
                        animateHover(false);
                    }
                });
            }

            private void animateHover(boolean entering) {
                if (this.hoverTimer != null) {
                    this.hoverTimer.stop();
                }

                this.hoverTimer = new Timer(16, (evt) -> {
                    if (entering) {
                        this.hoverProgress = Math.min(1.0F, this.hoverProgress + 0.1F);
                    } else {
                        this.hoverProgress = Math.max(0.0F, this.hoverProgress - 0.1F);
                    }

                    this.repaint();
                    if (entering && this.hoverProgress >= 1.0F || !entering && this.hoverProgress <= 0.0F) {
                        this.hoverTimer.stop();
                    }

                });
                this.hoverTimer.start();
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int alpha = (int)(180.0F + this.hoverProgress * 40.0F);
                g2.setColor(new Color(255, 255, 255, alpha));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
                g2.setColor(new Color(255, 255, 255, 120));
                g2.setStroke(new BasicStroke(2.0F));
                g2.drawRoundRect(1, 1, this.getWidth() - 3, this.getHeight() - 3, 23, 23);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.addActionListener((e) -> {
            this.dispose();
            new OperatingSystem();
        });
        button.setPreferredSize(new Dimension(140, 50));
        button.setFont(new Font("Segoe UI", 1, 18));
        button.setForeground(new Color(40, 40, 40));
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        return button;
    }

    private JButton createProcessButton(String text, String icon, Color accentColor) {
        return this.createProcessButton(text, icon, accentColor, (ActionListener)null);
    }

    private JButton createProcessButton(final String text, final String icon, final Color accentColor, ActionListener action) {
        JButton button = new JButton() {
            private float hoverProgress;
            private float pressProgress;
            private Timer animTimer;

            {
                Objects.requireNonNull(ProcessManagementMenu.this);
                this.hoverProgress = 0.0F;
                this.pressProgress = 0.0F;
                this.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        animateHover(true);
                    }

                    public void mouseExited(MouseEvent e) {
                        animateHover(false);
                    }

                    public void mousePressed(MouseEvent e) {
                        animatePress(true);
                    }

                    public void mouseReleased(MouseEvent e) {
                        animatePress(false);
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

            private void animatePress(boolean pressing) {
                if (this.animTimer != null) {
                    this.animTimer.stop();
                }

                this.animTimer = new Timer(16, (evt) -> {
                    if (pressing) {
                        this.pressProgress = Math.min(1.0F, this.pressProgress + 0.15F);
                    } else {
                        this.pressProgress = Math.max(0.0F, this.pressProgress - 0.15F);
                    }

                    this.repaint();
                    if (pressing && this.pressProgress >= 1.0F || !pressing && this.pressProgress <= 0.0F) {
                        this.animTimer.stop();
                    }

                });
                this.animTimer.start();
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                int w = this.getWidth();
                int h = this.getHeight();
                float scale = 1.0F - this.pressProgress * 0.05F;
                int offset = (int)((1.0F - scale) * (float)w / 2.0F);
                g2.translate(offset, offset);
                g2.scale((double)scale, (double)scale);
                g2.setColor(new Color(0, 0, 0, (int)(60.0F + this.hoverProgress * 40.0F)));
                g2.fillRoundRect(4, 4, w - 8, h - 8, 28, 28);
                int baseAlpha = (int)(140.0F + this.hoverProgress * 30.0F);
                g2.setColor(new Color(0, 0, 0, baseAlpha));
                g2.fillRoundRect(0, 0, w, h, 28, 28);
                int accentAlpha = (int)(20.0F + this.hoverProgress * 30.0F);
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), accentAlpha));
                g2.fillRoundRect(0, 0, w, h, 28, 28);
                GradientPaint gp = new GradientPaint(0.0F, 0.0F, new Color(255, 255, 255, (int)(100.0F + this.hoverProgress * 50.0F)), 0.0F, (float)(h / 2), new Color(255, 255, 255, 0));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, w, h / 2, 28, 28);
                g2.setColor(new Color(255, 255, 255, (int)(150.0F + this.hoverProgress * 50.0F)));
                g2.setStroke(new BasicStroke(2.5F));
                g2.drawRoundRect(2, 2, w - 5, h - 5, 26, 26);
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), (int)(this.hoverProgress * 180.0F)));
                g2.setStroke(new BasicStroke(3.0F));
                g2.drawRoundRect(1, 1, w - 3, h - 3, 27, 27);
                g2.scale((double)(1.0F / scale), (double)(1.0F / scale));
                g2.translate(-offset, -offset);
                g2.setFont(new Font("Segoe UI Emoji", 0, 36));
                g2.setColor(new Color(255, 255, 255, 220));
                FontMetrics fmIcon = g2.getFontMetrics();
                int iconX = (w - fmIcon.stringWidth(icon)) / 2;
                g2.drawString(icon, iconX, h / 2 - 10);
                g2.setFont(new Font("Segoe UI", 1, 16));
                g2.setColor(new Color(255, 255, 255, 250));
                FontMetrics fmText = g2.getFontMetrics();
                int textX = (w - fmText.stringWidth(text)) / 2;
                g2.drawString(text, textX, h / 2 + 30);
                g2.dispose();
            }
        };
        if (action != null) {
            button.addActionListener(action);
        } else {
            button.addActionListener((e) -> {
            });
        }

        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new ProcessManagementMenu();
        });
    }
}
