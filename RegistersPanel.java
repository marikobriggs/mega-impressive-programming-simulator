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
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Panel to visualize register contents
 */
public class RegistersPanel extends JPanel{

	private JLabel titleLabel;
	private JTextField[] myRegisterFields;
	private BitString[] myRegisters;
	
	/**
	 * auto-generated serial version UID
	 */
	private static final long serialVersionUID = -6467125365746605181L;

	/**
	 * Create a panel to show a visualization of the current register contents
	 */
	RegistersPanel(BitString[] registers, Map<String, Integer> registerMappings) {
		super();
		myRegisters = registers;
		// reverse the register mappings for displaying register titles
		Map<Integer, String> intToStringRegisterMap = new HashMap<Integer, String>();
		for (Map.Entry<String, Integer> e : registerMappings.entrySet()) {
			intToStringRegisterMap.put(e.getValue(), e.getKey());
		}
		
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		myRegisterFields = new JTextField[32];
		titleLabel = new JLabel("REGISTERS");
		this.add(titleLabel);
		this.add(new JLabel("   ")); // padding
		
		for (int i = 0; i < 32; i++) {
			JPanel tempPanel = new JPanel();
			myRegisterFields[i] = new JTextField(registers[i].display(true));
			if (i == 0) {
				JLabel label = new JLabel("0 ($zero): "); 
				label.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
				tempPanel.add(label, BorderLayout.WEST);
			} else if (i < 10) {
				String regLabelString = i + "   (" + intToStringRegisterMap.get(i) + "): ";
				JLabel label = new JLabel(regLabelString);
				label.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
				tempPanel.add(label, BorderLayout.WEST);
			} else {
				String regLabelString = i + "  (" + intToStringRegisterMap.get(i) + "): ";
				JLabel label = new JLabel(regLabelString);
				label.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
				tempPanel.add(label, BorderLayout.WEST);
			}


			tempPanel.add(myRegisterFields[i], BorderLayout.EAST);
			this.add(tempPanel);
			
			myRegisterFields[i].addActionListener(theEvent -> {
				for (int j = 0; j < myRegisterFields.length; j++) {
					if (myRegisterFields[j] == (JTextField) (theEvent.getSource())) {
						BitString bitString = new BitString();
						bitString.setValue2sComp(Integer.parseInt(myRegisterFields[j].getText()));
						bitString.setValue2sComp(Integer.parseInt(myRegisterFields[j].getText()));
						myRegisters[j] = bitString;
						myRegisterFields[j].setText(myRegisters[j].display(true));
					}
				}

			});
		}
		
		this.setBorder(new EmptyBorder(15, 15, 15, 15));
	}
	
	public void updateRegisters(BitString[] registers) {
		for (int i = 0; i < registers.length; i++) {
			myRegisterFields[i].setText(registers[i].display(true));
		}
	}
	
}
