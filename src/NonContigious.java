//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.UIManager;

class NonContigious extends JFrame {
    public NonContigious() {
        this.setTitle("Memory Management System");
        this.setSize(800, 600);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(800, 600, 4);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        JLabel titleLabel = new JLabel("Memory Management System");
        titleLabel.setFont(new Font("Segoe UI", 1, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);
        JLabel subtitleLabel = new JLabel("Select Memory Management Technique");
        subtitleLabel.setFont(new Font("Segoe UI", 0, 18));
        subtitleLabel.setForeground(new Color(255, 255, 255, 230));
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);
        JButton pagingButton = this.createMenuButton("PAGING", new Color(70, 130, 180));
        gbc.gridy = 2;
        mainPanel.add(pagingButton, gbc);
        JButton segmentationButton = this.createMenuButton("SEGMENTATION", new Color(46, 125, 50));
        gbc.gridy = 3;
        mainPanel.add(segmentationButton, gbc);
        JButton LRUButton = this.createMenuButton("lRU", new Color(211, 123, 255, 132));
        gbc.gridy = 4;
        mainPanel.add(LRUButton, gbc);
        this.add(mainPanel);
        pagingButton.addActionListener((e) -> {
            this.dispose();
            PagingMemoryFinal pagingMemoryFinal = new PagingMemoryFinal();
            pagingMemoryFinal.setVisible(true);
        });
        segmentationButton.addActionListener((e) -> {
            this.dispose();
            SegmentationMemory segmentationMemory = new SegmentationMemory();
            segmentationMemory.setVisible(true);
        });
        LRUButton.addActionListener((e) -> {
            this.dispose();
            LRUPageReplacement LRUPageReplacement = new LRUPageReplacement();
            LRUPageReplacement.setVisible(true);
        });
        this.setVisible(true);
    }

    private JButton createMenuButton(String text, final Color bgColor) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", 1, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(350, 80));
        button.addMouseListener(new MouseAdapter() {
            {
                Objects.requireNonNull(NonContigious.this);
            }

            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new NonContigious();
        });
    }
}
