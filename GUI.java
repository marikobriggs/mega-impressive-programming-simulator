import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * GUI to display running MIPS programs
 */
public class GUI extends JFrame {
	
    /** Auto-generated Serial Version UID */
	private static final long serialVersionUID = 7605948922464541352L;
	
	private JLabel myInputLabel;
	private JPanel myPanel;
	private RegistersPanel myRegistersPanel;
	private DataMemoryPanel myDataMemoryPanel;
	private JTextArea myInputTextArea;
	private JScrollPane myInputScrollPane;
    JButton myAssembleButton;
    JButton myRunButton;
    private JPanel myButtonPanel;
    private Computer myComputer;
	private JScrollPane myRegistersScrollPane;
	private JScrollPane myDataMemoryScrollPane;
    
    /**
     * Create a new GUI to display running MIPS programs
     * @param computer the computer (back-end) used to simulate running MIPS
     */
	private GUI(Computer computer) {
        super("Mega Impressive Programming Simulator");
        myComputer = computer;
        myPanel = new JPanel();
        myRegistersPanel = new RegistersPanel(computer.getRegisters(), computer.getRegisterMappings());
        myDataMemoryPanel = new DataMemoryPanel(computer.getDataMemory());
        myInputLabel = new JLabel("Input MIPS code below");
        myInputTextArea = new JTextArea(50, 20);
		myInputTextArea.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
        myInputScrollPane = new JScrollPane(myInputTextArea);
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
        this.add(myPanel, BorderLayout.WEST);

        // input text area
        myInputScrollPane.setSize(new Dimension(screenSize.width / 4, (int) Math.round(screenSize.height / 1.25)));
        myInputScrollPane.setPreferredSize(new Dimension(screenSize.width / 4, (int) Math.round(screenSize.height / 1.25)));
        myPanel.add(myInputLabel);
        myPanel.add(myInputScrollPane);
        
        // add padding between the text areas
        JPanel paddingPanel = new JPanel();
        paddingPanel.setBorder(new EmptyBorder(10, 0, 0, 10));
        myPanel.add(paddingPanel);

        // buttons + button panel
        myButtonPanel.add(myAssembleButton);
        myButtonPanel.add(myRunButton);
        myPanel.add(myButtonPanel);
        
        // registers and memory panels
        myRegistersScrollPane = new JScrollPane(myRegistersPanel);
        myDataMemoryScrollPane = new JScrollPane(myDataMemoryPanel);
        this.add(myRegistersScrollPane, BorderLayout.EAST);
        this.add(myDataMemoryScrollPane, BorderLayout.CENTER);
        
        this.pack();
        setActionListeners();
    }
    
    /**
     * Set the action listeners for the assemble and run buttons
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
                myComputer.execute();
                myRegistersPanel.updateRegisters(myComputer.getRegisters());
                myDataMemoryPanel.updateMemory();
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