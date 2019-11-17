/*
 * Mega Impressive Programming Simulator
 * TCSS 372 - Computer Architecture
 * 11-16-2019
 * By:
 * Mariko Briggs
 * Mercedes Chea
 * Thaddaeus Hug
 */

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Panel to visualize register contents
 */
public class DataMemoryPanel extends JPanel{

	private JLabel titleLabel;
	private JTextField[] myMemoryTexts;
	private BitString[] myMemory;
	
	/**
	 * auto-generated serial version UID
	 */
	private static final long serialVersionUID = -2921150969388895792L;

	/**
	 * Create a panel to show a visualization of the current register contents
	 */
	DataMemoryPanel(BitString[] memory) {
		super();
		myMemory = memory;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        myMemoryTexts = new JTextField[128];
		titleLabel = new JLabel("DATA MEMORY");
		this.add(titleLabel);
		this.add(new JLabel("   ")); // padding
		for (int i = 0; i < 128; i++) {
			JPanel tempPanel = new JPanel();
			tempPanel.add(new JLabel(i + ""));
			myMemoryTexts[i] = new JTextField(memory[i].display(true));
			myMemoryTexts[i].addActionListener(theEvent -> {
				for (int j = 0; j < myMemoryTexts.length; j++) {
					if (myMemoryTexts[j] == (JTextField) (theEvent.getSource())) {
						BitString bitString = new BitString();
						bitString.setValue2sComp(Integer.parseInt(myMemoryTexts[j].getText()));
						bitString.setValue2sComp(Integer.parseInt(myMemoryTexts[j].getText()));
						myMemory[j] = bitString;
						myMemoryTexts[j].setText(myMemory[j].display(true));
					}
				}

			});
			tempPanel.add(myMemoryTexts[i], BorderLayout.EAST);
			this.add(tempPanel);
		}
		this.setBorder(new EmptyBorder(15, 15, 15, 15));
	}
	
	public void updateMemory() {
		for (int i = 0; i < myMemory.length; i++) {
			myMemoryTexts[i].setText(myMemory[i].display(true));
		}
	}
	
}
