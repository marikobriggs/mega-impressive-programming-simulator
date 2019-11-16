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
public class DataMemoryPanel extends JPanel{

	JLabel titleLabel;
	JTextField[] myMemoryCells;
	
	/**
	 * auto-generated serial version UID
	 */
	private static final long serialVersionUID = -6467125365746605181L;

	/**
	 * Create a panel to show a visualization of the current register contents
	 */
	DataMemoryPanel(BitString[] memory) {
		super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		myMemoryCells = new JTextField[32];
		titleLabel = new JLabel("MEMORY");
		this.add(titleLabel);
		this.add(new JLabel("   ")); // padding
		
		for (int i = 0; i < 32; i++) {
			myMemoryCells[i] = new JTextField("asdf");
			this.add(myMemoryCells[i]);
		}
		this.setBorder(new EmptyBorder(15, 15, 15, 15));
	}
	
	public void updateMemory(BitString[] memory) {
		for (int i = 0; i < memory.length; i++) {
			myMemoryCells[i].setText(memory[i].display(true));
		}
	}
	
}
