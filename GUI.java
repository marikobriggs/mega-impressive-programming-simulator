import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * GUI to display running MIPS programs
 */
public class GUI extends JFrame {
	
    /** Auto-generated Serial Version UID */
	private static final long serialVersionUID = 7605948922464541352L;
	
	private JLabel myInputLabel;
	private JLabel myOutputLabel;
	private JPanel myPanel;
	private RegistersPanel myRegistersPanel;
	private JTextArea myInputTextArea;
	private JScrollPane myInputScrollPane;
	private JTextArea myOutputTextArea;
	private JScrollPane myOutputScrollPane;
    JButton myAssembleButton;
    JButton myRunButton;
    private JPanel myButtonPanel;
    private Computer myComputer;
    
    /**
     * Create a new GUI to display running MIPS programs
     * @param computer the computer (back-end) used to simulate running MIPS
     */
	private GUI(Computer computer) {
        super("Mega Impressive Programming Simulator");
        myPanel = new JPanel();
        myRegistersPanel = new RegistersPanel();
        myInputLabel = new JLabel("Input MIPS code below");
        myOutputLabel = new JLabel("The output appears here");
        myInputTextArea = new JTextArea(5, 20);
        myInputScrollPane = new JScrollPane(myInputTextArea);
        myOutputTextArea = new JTextArea(5, 20);
        myOutputScrollPane = new JScrollPane(myOutputTextArea);
        myAssembleButton = new JButton("Assemble");
        myRunButton = new JButton("Run");
        myButtonPanel = new JPanel();
        start();

    }
	
	/**
	 * Initialize GUI components
	 */
    private void start() {
        // init stuff
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setVisible(true);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel init
        add(myPanel);

        // input text area
        myInputScrollPane.setSize(new Dimension(screenSize.width / 3, screenSize.height / 3));
        myInputScrollPane.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 3));
        myPanel.add(myInputLabel);
        myPanel.add(myInputScrollPane);
        
        // output text area
        myPanel.add(myOutputLabel);
        myOutputScrollPane.setSize(new Dimension(screenSize.width / 3, screenSize.height / 3));
        myOutputScrollPane.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 3));
        myPanel.add(myOutputScrollPane);

        // buttons + button panel
        myButtonPanel.add(myAssembleButton);
        myButtonPanel.add(myRunButton);
        myPanel.add(myButtonPanel);
        
        // myPanel.add(myRegistersPanel);
        
        this.pack();
        setActionListeners();
    }
    
    /**
     * Set the action listeners for the compile and run buttons
     */
    private void setActionListeners() {
        myAssembleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = myInputTextArea.getText();
                ArrayList<String> inputLines = new ArrayList<String>();
                Scanner sc = new Scanner(input);
                while (sc.hasNextLine()) {
                	inputLines.add(sc.nextLine());
                }
                sc.close();
                try {
					myComputer.assemble(inputLines);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });

        myRunButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	myOutputTextArea.append("hello");
            	
                List<String> outputLines = myComputer.execute();
                for (String s : outputLines) {
                	myOutputTextArea.append(s);
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI(new Computer());
            }
        });
    }
}