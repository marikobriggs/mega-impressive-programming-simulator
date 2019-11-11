import java.awt.BorderLayout;

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
	RegistersPanel(BitString[] registers) {
		super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		myRegisterLabels = new JLabel[32];
		titleLabel = new JLabel("REGISTERS");
		this.add(titleLabel);
		this.add(new JLabel("   ")); // padding
		
		for (int i = 0; i < 32; i++) {
			myRegisterLabels[i] = new JLabel(registers[i].display(true));
			JPanel tempPanel = new JPanel();
			if (i < 10) {
				tempPanel.add(new JLabel(i + ": "), BorderLayout.WEST);
			} else {
				tempPanel.add(new JLabel(i + ":"), BorderLayout.WEST);
			}
			tempPanel.add(myRegisterLabels[i], BorderLayout.EAST);
			this.add(tempPanel);
		}
		this.setBorder(new EmptyBorder(30, 30, 30, 30));
	}
	
	
	public void updateRegisters(BitString[] registers) {
		for (int i = 0; i < registers.length; i++) {
			myRegisterLabels[i].setText(registers[i].display(true));
		}
	}
	
}
