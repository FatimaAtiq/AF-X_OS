//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class createprocess extends JFrame {
    public static ArrayList<Process> processList = new ArrayList();
    private int totalProcesses;
    private int createdCount = 0;

    public createprocess() {
        String input = JOptionPane.showInputDialog(this, "How many processes do you want to create?", "Process Count", 3);
        if (input == null) {
            this.dispose();
        } else {
            try {
                this.totalProcesses = Integer.parseInt(input);
                if (this.totalProcesses <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException var17) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive number", "Error", 0);
                this.dispose();
                return;
            }

            this.setTitle("Create Process");
            this.setSize(700, 600);
            this.setLocationRelativeTo((Component)null);
            this.setDefaultCloseOperation(2);
            ImageIcon bgIcon = new ImageIcon("main/resources/img_1.png");
            Image bgImage = bgIcon.getImage().getScaledInstance(700, 600, 4);
            JLabel background = new JLabel(new ImageIcon(bgImage));
            background.setLayout(new BorderLayout());
            this.setContentPane(background);
            JPanel main = new JPanel(new BorderLayout());
            main.setOpaque(false);
            main.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
            JLabel title = new JLabel("Create New Process");
            title.setFont(new Font("Segoe UI", 1, 32));
            title.setForeground(Color.WHITE);
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.add(title);
            main.add(titlePanel, "North");
            JPanel glass = new JPanel(new GridBagLayout()) {
                {
                    Objects.requireNonNull(createprocess.this);
                }

                protected void paintComponent(Graphics g) {
                    g.setColor(new Color(0, 0, 0, 190));
                    g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 35, 35);
                }
            };
            glass.setOpaque(false);
            glass.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(12, 10, 12, 10);
            gbc.fill = 2;
            JTextField nameField = this.createTextField();
            JTextField priorityField = this.createNumberField();
            JTextField arrivalField = this.createNumberField();
            JTextField burstField = this.createNumberField();
            gbc.gridx = 0;
            gbc.gridy = 0;
            glass.add(this.createLabel("Process Name:"), gbc);
            gbc.gridx = 1;
            glass.add(nameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            glass.add(this.createLabel("Priority:"), gbc);
            gbc.gridx = 1;
            glass.add(priorityField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            glass.add(this.createLabel("Arrival Time:"), gbc);
            gbc.gridx = 1;
            glass.add(arrivalField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            glass.add(this.createLabel("Burst Time:"), gbc);
            gbc.gridx = 1;
            glass.add(burstField, gbc);
            JButton createBtn = this.createButton("Create Process");
            JButton cancelBtn = this.createButton("Cancel");
            createBtn.addActionListener((e) -> {
                if (!nameField.getText().isEmpty() && !priorityField.getText().isEmpty() && !arrivalField.getText().isEmpty() && !burstField.getText().isEmpty()) {
                    int pid = 1000 + (new Random()).nextInt(9000);
                    Process p = new Process(pid, nameField.getText(), Integer.parseInt(priorityField.getText()), Integer.parseInt(arrivalField.getText()), Integer.parseInt(burstField.getText()), "Ready");
                    processList.add(p);
                    ++this.createdCount;
                    if (this.createdCount < this.totalProcesses) {
                        JOptionPane.showMessageDialog(this, "Process " + this.createdCount + " created.\nEnter next process.");
                        nameField.setText("");
                        priorityField.setText("");
                        arrivalField.setText("");
                        burstField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "All processes created successfully!");
                        this.dispose();
                        new ProcessList();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Fill all fields!");
                }
            });
            cancelBtn.addActionListener((e) -> this.dispose());
            JPanel btnPanel = new JPanel();
            btnPanel.setOpaque(false);
            btnPanel.add(createBtn);
            btnPanel.add(cancelBtn);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            glass.add(btnPanel, gbc);
            main.add(glass, "Center");
            this.add(main);
            this.setVisible(true);
        }
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", 1, 16));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(15);
        tf.setOpaque(false);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", 0, 15));
        tf.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return tf;
    }

    private JTextField createNumberField() {
        JTextField field = this.createTextField();
        ((AbstractDocument)field.getDocument()).setDocumentFilter(new DocumentFilter() {
            {
                Objects.requireNonNull(createprocess.this);
            }

            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.insertString(fb, offset, text, attr);
                }

            }

            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }

            }
        });
        return field;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", 1, 15));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(createprocess::new);
    }
}
