import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.awt.datatransfer.StringSelection;

public class PasswordGenerator extends JFrame {

    private JTextField lengthField;
    private JTextField resultField;
    private JCheckBox useUpper, useLower, useNumbers, useSymbols;

    public PasswordGenerator() {
        setTitle("Random Password Generator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        // Length input
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Password Length:"));
        lengthField = new JTextField(5);
        panel1.add(lengthField);
        add(panel1);

        // Character type checkboxes
        useUpper = new JCheckBox("Include Uppercase (A-Z)");
        useLower = new JCheckBox("Include Lowercase (a-z)");
        useNumbers = new JCheckBox("Include Numbers (0-9)");
        useSymbols = new JCheckBox("Include Symbols (@#$%&*)");

        add(useUpper);
        add(useLower);
        add(useNumbers);
        add(useSymbols);

        // Generate button
        JButton generateBtn = new JButton("Generate Password");
        generateBtn.addActionListener(e -> generatePassword());
        add(generateBtn);

        // Output panel with copy
        JPanel panel2 = new JPanel();
        resultField = new JTextField(20);
        resultField.setEditable(false);
        panel2.add(resultField);

        JButton copyBtn = new JButton("Copy");
        copyBtn.addActionListener(e -> {
            StringSelection selection = new StringSelection(resultField.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard!");
        });
        panel2.add(copyBtn);
        add(panel2);
    }

    private void generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "@#$%&*";

        StringBuilder charPool = new StringBuilder();
        if (useUpper.isSelected()) charPool.append(upper);
        if (useLower.isSelected()) charPool.append(lower);
        if (useNumbers.isSelected()) charPool.append(numbers);
        if (useSymbols.isSelected()) charPool.append(symbols);

        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return;
        }

        if (charPool.length() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one character type.");
            return;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            password.append(charPool.charAt(index));
        }

        resultField.setText(password.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGenerator().setVisible(true));
    }
}

