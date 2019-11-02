package gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;

public class GUI extends JFrame {

    public GUI() {
        super("Mega Impressive Programming Simulator");

    }

    public void start() {
        // init stuff
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLayout(new BorderLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel init
        JPanel myPanel = new JPanel();
        myPanel.setBounds(300, 300, 400, 400);
        add(myPanel);

        // input text area
        JTextArea inputText = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(inputText);
        inputText.setSize(screenSize.width / 2, screenSize.height / 2);
        myPanel.add(inputText, BorderLayout.NORTH);

        // output text area
        JLabel outputText = new JLabel();

        myPanel.add(outputText, BorderLayout.SOUTH);

        // buttons + button panel
        JButton compileButton = new JButton("Compile");
        JButton runButton = new JButton("Run");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(compileButton);
        buttonPanel.add(runButton);
        myPanel.add(buttonPanel, BorderLayout.SOUTH);

        // action listeners
        compileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                ArrayList<String> inputArr = new ArrayList<String>();
                Scanner sc = new Scanner(input);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    inputArr.add(line);

                }
                sc.close();
            }
        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputText.setText("hello");
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.start();
            }
        });
    }
}