package calculatorapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorFrame extends JFrame implements ActionListener {

    private JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public CalculatorFrame() {
        // Window Setup
        setTitle("Event-Driven Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // Display TextField
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Button Panel with Grid Layout (4 rows, 4 columns)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        // Button labels in order
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            
            // Style special buttons
            if (text.equals("=")) {
                btn.setBackground(new Color(100, 200, 100)); // Green
            } else if (text.equals("C")) {
                btn.setBackground(new Color(255, 150, 150)); // Red
            }

            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();

        // Check if button is a number
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9')) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } 
        // Check if button is Clear (C)
        else if (command.equals("C")) {
            display.setText("0");
            firstOperand = 0;
            operator = "";
            startNewNumber = true;
        } 
        // Check if button is Equals (=)
        else if (command.equals("=")) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+": result = firstOperand + secondOperand; break;
                case "-": result = firstOperand - secondOperand; break;
                case "*": result = firstOperand * secondOperand; break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        startNewNumber = true;
                        return;
                    }
                    break;
                default: return;
            }

            display.setText(String.valueOf(result));
            startNewNumber = true;
        } 
        // Operator buttons (+, -, *, /)
        else {
            firstOperand = Double.parseDouble(display.getText());
            operator = command;
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorFrame().setVisible(true);
        });
    }
}