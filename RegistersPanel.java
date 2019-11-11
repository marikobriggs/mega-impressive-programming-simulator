import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel to visualize register contents
 */
public class RegistersPanel extends JPanel{

	JLabel titleLabel;
	JLabel[] myRegisterLabels;
	
	/**
	 * auto-generated serial version UID
	 */
	private static final long serialVersionUID = -6467125365746605181L;

	/**
	 * Create a panel to show a visualization of the current register contents
	 */
	RegistersPanel(BitString[] registers, Map<String, Integer> registerMappings) {
		super();
		// reverse the register mappings for displaying register titles
		Map<Integer, String> intToStringRegisterMap = new HashMap<Integer, String>();
		for (Map.Entry<String, Integer> e : registerMappings.entrySet()) {
			intToStringRegisterMap.put(e.getValue(), e.getKey());
		}
		
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		myRegisterLabels = new JLabel[32];
		titleLabel = new JLabel("REGISTERS");
		this.add(titleLabel);
		this.add(new JLabel("   ")); // padding
		
		for (int i = 0; i < 32; i++) {
			
			myRegisterLabels[i] = new JLabel(registers[i].display(true));
			JPanel tempPanel = new JPanel();
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


			tempPanel.add(myRegisterLabels[i], BorderLayout.EAST);
			this.add(tempPanel);
		}
		this.setBorder(new EmptyBorder(15, 15, 15, 15));
	}
	
	public void updateRegisters(BitString[] registers) {
		for (int i = 0; i < registers.length; i++) {
			myRegisterLabels[i].setText(registers[i].display(true));
		}
	}
	
}
